package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.util.SimpleObjectCache;
import net.ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
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
		this.cache = new SimpleObjectCache(512, Terrain[]::new, this::pickTerrain);

		this.tundraTerrain = new TundraTerrain(biomeRegistry.get(PermafrozenBiomes.TUNDRA), random, true);
		this.shrumnalSpiresTerrain = new TundraTerrain(biomeRegistry.get(PermafrozenBiomes.SHRUMNAL_SPIRES), random, false);
	}

	private final Terrain tundraTerrain;
	private final Terrain shrumnalSpiresTerrain;

	private final SimpleObjectCache<Terrain> cache;

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;

	private Terrain pickTerrain(int x, int z) {
		double moisture = this.moistureNoise.sample( x * 0.0014, z * 0.0014);
		double mtns = this.moistureNoise.sample( x * 0.0014, z * 0.0014);
		return moisture > 0 ? this.shrumnalSpiresTerrain : this.tundraTerrain;
	}

	public Terrain sample(int x, int z) {
		return this.cache.sample(x, z);
	}
}
