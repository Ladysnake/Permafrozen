package ladysnake.permafrozen.worldgen.terrain;

import ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class ChillingCanyonsTerrain extends Terrain {
	protected ChillingCanyonsTerrain(Biome biome, Random random) {
		super(biome);
		this.topHillsNoise = new OpenSimplexNoise(random);
		this.bottomHillsNoise = new OpenSimplexNoise(random);
		this.canyonsNoise = new RidgedNoise(random);
	}

	private final OpenSimplexNoise topHillsNoise;
	private final OpenSimplexNoise bottomHillsNoise;
	private final RidgedNoise canyonsNoise;

	@Override
	public double sampleHeight(int x, int z) {
		double bottomSample = 3 * this.bottomHillsNoise.sample(x * 0.03, z * 0.03) + 6 * this.bottomHillsNoise.sample(x * 0.008, z * 0.008) + 75;
		double topSample = this.topHillsNoise.sample(x * 0.01, z * 0.01) * 4 + 150;
		double canyonsSample = -this.canyonsNoise.sample(x * 0.008, z * 0.008);
		double temp = clampMap(canyonsSample, -0.95, -0.75, bottomSample, topSample);
		if(temp < 140) {
			temp += this.bottomHillsNoise.sample(x * 0.1, z * 0.1) * 3;
		}
		return temp;
	}

	@Override
	public double modifyWeight(double original, double maxWeight) {
		return clampMap(original, maxWeight / 2, maxWeight, 0, maxWeight * 2);
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		if (height <= 100) {
			buildDefaultSurface(chunk, x, z, height, seaLevel, PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.COARSE_PERMAFROST.getDefaultState());
		}
		if (height >= 146) {
			buildDefaultSurface(chunk, x, z, height, seaLevel, Blocks.SNOW_BLOCK.getDefaultState(),  Blocks.SNOW_BLOCK.getDefaultState());
		}
	}
}
