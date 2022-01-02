package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.util.JitteredGrid;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class TerrainSampler {
	public TerrainSampler(Registry<Biome> biomeRegistry, long seed) {
		Random random = new Random(seed);
		this.mtnNoise = new OpenSimplexNoise(random);
		this.moistureNoise = new OpenSimplexNoise(random);
		this.riverVoronoiSeed = random.nextInt();
	}

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;
	private final int riverVoronoiSeed;

	public TerrainType sample(int x, int z) {
		return null; // TODO IMPLEMENT THIS
	}
}
