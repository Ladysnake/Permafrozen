package net.ladysnake.permafrozen.worldgen.terrain;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

public abstract class TerrainType {
	protected TerrainType(Biome biome) {
		this.biome = biome;
	}

	protected Biome biome;

	public Biome getBiome() {
		return this.biome;
	}

	abstract public double sampleHeight(int x, int z);

	/**
	 * @return the weight on fading out rivers. 0.0 expresses intent for terrain with rivers, whereas 1.0 expresses intent to have no rivers.
	 */
	public double getRiverFadeModifier() {
		return 0.0;
	}

	protected final void buildDefaultSurface(Chunk chunk, int x, int z, int height, int seaLevel, BlockState top, BlockState filler) {
		// Use chunk y levels for CC Compat
		// Since we use a heightmap this can be a bit simpler than it is in vanilla
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockState existing;

		for (int y = chunk.getTopY(); y >= chunk.getBottomY(); --y) {
			pos.set(x, y, z);
			existing = chunk.getBlockState(pos);

			if (existing.isOf(Blocks.STONE) && y >= height - 3) {
				chunk.setBlockState(pos, y == height - 1 && y >= seaLevel ? top : filler, false);
			}
		}
	}

	abstract public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel);
}