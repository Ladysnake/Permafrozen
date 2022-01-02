package net.ladysnake.permafrozen.worldgen.terrain;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Random;

public class TerrainSampler {
	public TerrainSampler(Registry<Biome> biomeRegistry, long seed) {
		Random random = new Random(seed);
		this.mtnNoise = new OpenSimplexNoise(random);
		this.moistureNoise = new OpenSimplexNoise(random);

		this.test1 = new TestTerrainType(biomeRegistry.get(BiomeKeys.TAIGA), random, 80);
		this.test2 = new TestTerrainType(biomeRegistry.get(BiomeKeys.FROZEN_OCEAN), random, 40);
	}

	private final TerrainType test1, test2;

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;

	public TerrainType sample(int x, int z) {
		return this.moistureNoise.sample(x * 0.001, z * 0.001) > 0 ? this.test2 : this.test1;
	}
}
