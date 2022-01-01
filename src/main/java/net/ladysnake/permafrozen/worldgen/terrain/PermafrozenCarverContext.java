package net.ladysnake.permafrozen.worldgen.terrain;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import java.util.Optional;
import java.util.function.Function;

public class PermafrozenCarverContext extends CarverContext {
	public PermafrozenCarverContext(ChunkGenerator chunkGenerator, NoiseChunkGenerator ncg, DynamicRegistryManager registryManager, HeightLimitView world) {
		super(ncg, registryManager, world, null);
		this.generator = chunkGenerator;
		this.minY = Math.max(world.getBottomY(), this.generator.getMinimumY());
		this.height = Math.min(world.getHeight(), this.generator.getWorldHeight());
	}

	private ChunkGenerator generator;
	private final int minY;
	private final int height;

	@Override
	public int getMinY() {
		return this.minY;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public Optional<BlockState> applyMaterialRule(Function<BlockPos, Biome> posToBiome, Chunk chunk, BlockPos pos, boolean hasFluid) {
		return Optional.empty();
	}
}
