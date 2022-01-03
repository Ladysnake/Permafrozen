package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.util.SimpleObjectCache;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Random;

/**
 * The terrain sampler, which determines the biomes, and possible terrains for each biome.
 * Biomes must also be added in the biome source to generate their features correctly.
 */
public class TerrainSampler {
	public TerrainSampler(Registry<Biome> biomeRegistry, long seed) {
		Random random = new Random(seed);
		this.mtnNoise = new OpenSimplexNoise(random);
		this.moistureNoise = new OpenSimplexNoise(random);
		this.cache = new SimpleObjectCache(512, TerrainType[]::new, this::pickTerrain);

		this.test1 = new TestTerrainType(biomeRegistry.get(BiomeKeys.SNOWY_TAIGA), random, 80);
		this.test2 = new TestTerrainType(biomeRegistry.get(BiomeKeys.FROZEN_OCEAN), random, 40);
	}

	private final SimpleObjectCache<TerrainType> cache;
	private final TerrainType test1, test2;

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;

	private TerrainType pickTerrain(int x, int z) {
		return this.moistureNoise.sample(x * 0.001, z * 0.001) > 0 ? this.test2 : this.test1;
	}

	public TerrainType sample(int x, int z) {
		return this.cache.sample(x, z);
	}
}
