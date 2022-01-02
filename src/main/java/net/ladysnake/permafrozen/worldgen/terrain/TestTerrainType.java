package net.ladysnake.permafrozen.worldgen.terrain;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class TestTerrainType extends TerrainType {
	protected TestTerrainType(Biome biome, Random random, int baseHeight) {
		super(biome);
		this.noise = new OpenSimplexNoise(random);
		this.baseHeight = baseHeight;
	}

	private final OpenSimplexNoise noise;
	private final int baseHeight;

	@Override
	public double sampleHeight(int x, int z) {
		return this.noise.sample(x * 0.03, z * 0.03) + this.baseHeight;
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		buildDefaultSurface(chunk, x, z, height, seaLevel, Blocks.SNOW.getDefaultState(), Blocks.STONE.getDefaultState());
	}
}
