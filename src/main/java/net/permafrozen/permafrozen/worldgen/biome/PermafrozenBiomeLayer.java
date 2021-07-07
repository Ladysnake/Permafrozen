package net.permafrozen.permafrozen.worldgen.biome;

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

    public PermafrozenBiomeLayer(long seed, Registry<Biome> dynamicRegistry){
        this.dynamicRegistry = dynamicRegistry;

        if (perlinGen == null)
        {
            ChunkRandom sharedseedrandom = new ChunkRandom(seed);
            perlinGen = new OctaveSimplexNoiseSampler(sharedseedrandom, IntStream.rangeClosed(0, 0));
        }
    }

    public int sample(LayerRandomnessSource noise, int x, int z) {
        double perlinNoise = perlinGen.sample(x * 0.055D, z * 0.055D, false);

            return this.dynamicRegistry.getRawId(this.dynamicRegistry.get(PermafrozenBiomeSource.BOREAS));

    }

    public static void setSeed(long seed) {
        ChunkRandom sharedseedrandom = new ChunkRandom(seed);
    }
}
