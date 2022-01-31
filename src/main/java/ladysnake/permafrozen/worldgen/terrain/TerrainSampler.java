package ladysnake.permafrozen.worldgen.terrain;

import ladysnake.permafrozen.util.SimpleObjectCache;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

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

		this.tundraTerrain = new LowFlatTerrain(biomeRegistry.get(PermafrozenBiomes.TUNDRA), random, true);
		this.shrumalSpiresTerrain = new LowFlatTerrain(biomeRegistry.get(PermafrozenBiomes.SHRUMAL_SPIRES), random, false);
		this.frigidFenTerrain = new SlightlyLowerFlatTerrain(biomeRegistry.get(PermafrozenBiomes.FRIGID_FEN), random, false);
		this.chillingCanyonsTerrain = new ChillingCanyonsTerrain(biomeRegistry.get(PermafrozenBiomes.CHILLING_CANYON), random);
	}

	private final Terrain tundraTerrain;
	private final Terrain shrumalSpiresTerrain;
	private final Terrain frigidFenTerrain;
	private final Terrain chillingCanyonsTerrain;

	private final SimpleObjectCache<Terrain> cache;

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;

	private Terrain pickTerrain(int x, int z) {
		double moisture = this.moistureNoise.sample( x * 0.0014, z * 0.0014);
		double mtns = this.mtnNoise.sample( x * 0.0014, z * 0.0014);

		if (mtns > 0.2) {
			return this.chillingCanyonsTerrain;
		} else if (moisture > -0.1) {
			return mtns < -0.2 && moisture > 0.2 ? this.frigidFenTerrain : this.shrumalSpiresTerrain;
		} else {
			return this.tundraTerrain;
		}
	}

	public Terrain sample(int x, int z) {
		return this.cache.sample(x, z);
	}
}
