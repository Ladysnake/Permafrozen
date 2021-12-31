package net.ladysnake.permafrozen.worldgen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.ladysnake.permafrozen.util.SimpleIntCache;
import net.ladysnake.permafrozen.util.JitteredGrid;
import net.ladysnake.permafrozen.util.GridPoint;
import net.ladysnake.permafrozen.worldgen.terrain.TerrainSampler;
import net.ladysnake.permafrozen.worldgen.terrain.TerrainType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.random.AbstractRandom;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

// TODO noise caves
public class PermafrozenChunkGenerator extends ChunkGenerator {
	public PermafrozenChunkGenerator(Registry<Biome> biomeRegistry, BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
		super(biomeSource, biomeSource, settings.get().getStructuresConfig(), seed);

		this.biomeRegistry = biomeRegistry;
		this.seed = seed;
		this.voronoiSeed = (int) seed;
		this.settings = settings;
		this.terrainSampler = new TerrainSampler(biomeRegistry);
		this.terrainHeightSampler = new SimpleIntCache(512, this::calculateTerrainHeight);
	}

	private final Registry<Biome> biomeRegistry;
	private final long seed;
	private final int voronoiSeed;
	private final Supplier<ChunkGeneratorSettings> settings;
	private final TerrainSampler terrainSampler;
	private final SimpleIntCache terrainHeightSampler;

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new PermafrozenChunkGenerator(this.biomeRegistry, this.populationSource.withSeed(seed), seed, this.settings);
	}

	@Override
	public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;
		AbstractRandom rand = new ChunkRandom(new AtomicSimpleRandom(region.getSeed())).createRandomDeriver().createRandom(chunkX, 0, chunkZ);

		int startX = chunkPos.getStartX();
		int startZ = chunkPos.getStartZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(int xo = 0; xo < 16; ++xo) {
			for(int zo = 0; zo < 16; ++zo) {
				int x = startX + xo;
				int z = startZ + zo;
				int height = this.terrainHeightSampler.sample(x, z);

				this.terrainSampler.sample(startX, startZ + zo).buildSurface(chunk, rand, x, z, height, this.getSeaLevel());
			}
		}

		//this.buildBedrock(chunk, chunkRandom); TODO bedrock
	}

	@Override
	public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor accessor, Chunk chunk) {
		ChunkPos pos = chunk.getPos();
		int seaLevel = this.getSeaLevel();
		BlockPos.Mutable setPos = new BlockPos.Mutable();
		Heightmap oceanFloor = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap surface = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		final int startX = pos.getStartX();
		final int startZ = pos.getStartZ();

		// CC Compat
		int[] chunkHeights = new int[17 * 17];
		int[] terrainHeights = new int[16 * 16];

		for (int x = 0; x < 17; ++x) {
			int totalX = startX + x;

			for (int z = 0; z < 17; ++z) {
				int totalZ = startZ + z;
				int sample = this.terrainHeightSampler.sample(totalX, totalZ);

				if (z < 16 && x < 16) {
					terrainHeights[(x * 16) + z] = sample;
				}

				chunkHeights[(x * 17) + z] = Math.min(chunk.getTopY() - 1, sample);
			}
		}

		for (int x = 0; x < 16; ++x) {
			int totalX = startX + x;
			setPos.setX(x);

			for (int z = 0; z < 16; ++z) {
				int totalZ = startZ + z;
				setPos.setZ(z);

				int height = chunkHeights[(x * 17) + z];
				int deepslateHeight = MathHelper.floor(3 * MathHelper.sin(totalX * 0.01f) + 3 * MathHelper.sin(totalZ * 0.01f));
				BlockState state;

				for (int y = chunk.getBottomY(); y < height; ++y) {
					try {
						state = y < deepslateHeight ? DEEPSLATE : STONE;
						setPos.setY(y);
						chunk.setBlockState(setPos, state, false);
					} catch (RuntimeException e) {
						System.out.println("=========== DEBUG INFO ================");
						System.out.println("worldHeight: " + this.getWorldHeight());
						System.out.println("minY: " + this.getMinimumY());
						System.out.println("chunkMinY: " + chunk.getBottomY());
						throw e;
					}
				}

				oceanFloor.trackUpdate(x, height - 1, z, STONE);
				surface.trackUpdate(x, height - 1, z, STONE);

				int trueHeight = terrainHeights[(x * 16) + z];

				if (trueHeight < seaLevel && height < chunk.getTopY()) { // Second Check: CC Compat
					int cap = Math.min(seaLevel, chunk.getTopY()); // CC Compat

					for (int y = height; y < cap; ++y) {
						setPos.setY(y);
						chunk.setBlockState(setPos, WATER, false);
					}
				}

				oceanFloor.trackUpdate(x, seaLevel - 1, z, WATER); // for oceanFloor probably not necessary
				surface.trackUpdate(x, seaLevel - 1, z, WATER);
			}
		}

		return CompletableFuture.completedFuture(chunk);
	}

	private int calculateTerrainHeight(int x, int z) {
		double height = 0.0;
		double totalWeight = 0.0;
		double riverFadeModifier = 0.0; // for non-mountains to specify river fades
		final double maxSquareRadius = 9.0; // 3.0 * 3.0;

		// Sample Relevant Voronoi in 5x5 area around the player for smoothing
		// This is not optimised

		int calcX = (x >> 4);
		int calcZ = (z >> 4);
		GridPoint cell = new GridPoint((double) x * 0.0625, (double) z * 0.0625);

		int sampleX;
		int sampleZ;

		// 9x9 sample because that is the possible range of the circle
		for (int xo = -4; xo <= 4; ++xo) {
			sampleX = xo + calcX;

			for (int zo = -4; zo <= 4; ++zo) {
				sampleZ = zo + calcZ;

				GridPoint point = JitteredGrid.sampleJitteredGrid(sampleX, sampleZ, this.voronoiSeed, 0.1);
				double sqrDist = point.centreSqrDist(cell);
				double weight = maxSquareRadius - sqrDist;

				// this is kept square-weighted because sqrt is a trash cringe operation and is slower than the hare from aesop's fables
				if (weight > 0) {
					TerrainType type = this.terrainSampler.sample(MathHelper.floor(point.getX() * 16.0), MathHelper.floor(point.getY() * 16.0));

					totalWeight += weight;
					height += weight * type.sampleHeight(x, z);
					riverFadeModifier += weight * type.getRiverFadeModifier();
				}
			}
		}

		// Complete the average
		height = height / totalWeight;
		riverFadeModifier = riverFadeModifier / totalWeight;

		double riverGen = ((TerrainBiomeProvider) this.biomeSource).sampleRiver(x, z);
		riverGen = Math.max(riverGen, 0.0) * riverFadeModifier;

		return (int) (MathHelper.lerp(riverGen, height, RIVER_HEIGHT));
	}

	@Override
	public int getHeight(int x, int z, Type heightmap, HeightLimitView world) {
		// Lazy Implementation from Naturverbunden. It works fine from experience.
		int height = this.terrainHeightSampler.sample(x, z);
		int seaLevel = this.getSeaLevel();

		if (height < seaLevel && heightmap.getBlockPredicate().test(WATER)) {
			return seaLevel - 1;
		}

		return height;
	}

	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {

		BlockState[] states = new BlockState[world.getHeight()];
		int height = this.terrainHeightSampler.sample(x, z);

		int i = 0;
		int y;

		for (y = world.getBottomY(); y < height; ++y) {
			states[i++] = STONE;
		}

		int seaLevel = this.getSeaLevel();

		while (y++ < seaLevel) {
			states[i++] = WATER;
		}

		while (i < states.length) {
			states[i++] = AIR;
		}

		return new VerticalBlockSample(world.getBottomY(), states);
	}

	@Override
	public void populateEntities(ChunkRegion region) {
		if (!(this.settings.get()).isMobGenerationDisabled()) {
			ChunkPos chunkPos = region.getCenterPos();
			Biome biome = region.getBiome(chunkPos.getStartPos());
			ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(region.getSeed()));
			chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
			SpawnHelper.populateEntities(region, biome, chunkPos, chunkRandom);
		}
	}

	@Override
	public int getWorldHeight() {
		return this.settings.get().getGenerationShapeConfig().height();
	}

	@Override
	public int getSeaLevel() {
		return this.settings.get().getSeaLevel();
	}

	@Override
	public int getMinimumY() {
		return this.settings.get().getGenerationShapeConfig().minimumY();
	}
//
//	public static TerrainChunkGenerator create(Registry<Biome> biomeReg, Registry<ChunkGeneratorSettings> settingsReg, long seed) {
//		return new TerrainChunkGenerator(new TerrainBiomeProvider(biomeReg, seed), seed, () -> settingsReg.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
//	}
//
//	public static final Codec<TerrainChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
//			instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(chunkGenerator -> chunkGenerator.populationSource),
//							Codec.LONG.fieldOf("seed").stable().forGetter(chunkGenerator -> chunkGenerator.seed),
//							ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(chunkGenerator -> () -> chunkGenerator.settings))
//					.apply(instance, TerrainChunkGenerator::new));

	public static final BlockState DEEPSLATE = Blocks.DEEPSLATE.getDefaultState();
	public static final BlockState STONE = Blocks.STONE.getDefaultState();
	public static final BlockState AIR = Blocks.AIR.getDefaultState();
	public static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	public static final BlockState WATER = Blocks.WATER.getDefaultState();
	public static final double RIVER_HEIGHT = 61.0;
}
