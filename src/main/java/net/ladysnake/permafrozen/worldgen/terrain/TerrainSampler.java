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
		this.riverWidthNoise = new OpenSimplexNoise(random);
		this.riverOffsetNoise = new OpenSimplexNoise(random);
		this.riverVoronoiSeed = random.nextInt();
	}

	private final OpenSimplexNoise mtnNoise;
	private final OpenSimplexNoise moistureNoise;
	private final OpenSimplexNoise riverWidthNoise;
	private final OpenSimplexNoise riverOffsetNoise;
	private final int riverVoronoiSeed;

	public TerrainType sample(int x, int z) {
		return null; // TODO IMPLEMENT THIS
	}

	/**
	 * Samples the strength of the "river" modifier at this position.
	 * @param x the x position to sample at
	 * @param z the z position to sample at
	 * @return the river strength from -1.0 to 1.0
	 */
	public double sampleRiver(int x, int z) {
		final double cutoff = 0.16 + 0.022 * this.riverWidthNoise.sample(x * 0.0008 + 1, z * 0.00081);
		final double normaliser = 1 / cutoff;

		double scalex = x * 0.0012;
		double scalez = z * 0.0012;

		double sampleX = scalex + 0.5 * this.riverOffsetNoise.sample(scalex * 2, scalez * 2);
		double sampleZ = scalez + 0.5 * this.riverOffsetNoise.sample(100 + scalex * 2, scalez * 2);

		double worley = JitteredGrid.sampleD1D2SquaredWorley(x, z, this.riverVoronoiSeed);
		worley = cutoff - worley;

		return Math.max(normaliser * worley, 0.0);
	}
}
