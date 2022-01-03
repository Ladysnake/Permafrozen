package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

public abstract class Terrain {
	protected Terrain(Biome biome) {
		this.biome = biome;
	}

	protected Biome biome;

	public Biome getBiome() {
		return this.biome;
	}

	abstract public double sampleHeight(int x, int z);

	/**
	 * @return the weight of this terrain type.
	 */
	public double modifyWeight(double original) {
		return original;
	}

	abstract public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel);

	protected static final void buildDefaultSurface(Chunk chunk, int x, int z, int height, int seaLevel, BlockState top, BlockState filler) {
		// Use chunk y levels for CC Compat
		// Since we use a heightmap this can be a bit simpler than it is in vanilla
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockState existing;

		for (int y = chunk.getTopY(); y >= chunk.getBottomY(); --y) {
			pos.set(x, y, z);
			existing = chunk.getBlockState(pos);

			if (existing.isOf(PermafrozenBlocks.SHIVERSLATE) && y >= height - 3) {
				chunk.setBlockState(pos, y == height - 1 && y >= seaLevel - 1? top : filler, false);
			}
		}
	}

	protected static double map(double value, double min, double max, double newmin, double newmax) {
		value -= min;
		value /= (max - min);
		return newmin + value * (newmax - newmin);
	}
}
