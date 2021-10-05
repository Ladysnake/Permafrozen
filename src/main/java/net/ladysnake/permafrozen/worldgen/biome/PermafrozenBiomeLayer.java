package net.ladysnake.permafrozen.worldgen.biome;

import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.gen.ChunkRandom;

import java.util.stream.IntStream;

public class PermafrozenBiomeLayer implements InitLayer {
	private final Registry<Biome> dynamicRegistry;
	private static OctaveSimplexNoiseSampler perlinGen;
	
	public PermafrozenBiomeLayer(long seed, Registry<Biome> dynamicRegistry) {
		this.dynamicRegistry = dynamicRegistry;
		if (perlinGen == null) {
			ChunkRandom sharedseedrandom = new ChunkRandom(seed);
			perlinGen = new OctaveSimplexNoiseSampler(sharedseedrandom, IntStream.rangeClosed(0, 0));
		}
	}
	
	@Override
	public int sample(LayerRandomnessSource noise, int x, int z) {
		double depthNoise = perlinGen.sample(x * 0.055D, z * 0.055D, false);
		double differentNoise = noise.getNoiseSampler().sample(x * 0.055D, 60, z * 0.055D);
		if (depthNoise > 0.15) {
			if (differentNoise > 0.30) {
				return this.dynamicRegistry.getRawId(this.dynamicRegistry.get(PermafrozenBiomeSource.BOREAS_GLADE));
			} else {
				return this.dynamicRegistry.getRawId(this.dynamicRegistry.get(PermafrozenBiomeSource.BOREAS));
			}
		}
		else if (depthNoise > 0.05) {
			return this.dynamicRegistry.getRawId(this.dynamicRegistry.get(PermafrozenBiomeSource.GLACIAS_SHORES));
		}
		else {
			return this.dynamicRegistry.getRawId(this.dynamicRegistry.get(PermafrozenBiomeSource.GLACIAS));
		}

	}
}
