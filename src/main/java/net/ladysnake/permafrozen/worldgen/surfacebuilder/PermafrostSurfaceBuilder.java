package net.ladysnake.permafrozen.worldgen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.ladysnake.permafrozen.worldgen.biome.PermafrozenSurfaceBuilders;

import java.util.Random;

public class PermafrostSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public PermafrostSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    public void generate(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState blockState, BlockState blockState2, int l, int m, long n, TernarySurfaceConfig ternarySurfaceConfig) {
        if (d > 1.75D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, i, j, k, d, blockState, blockState2, l, m, n, PermafrozenSurfaceBuilders.THAWING_PERMAFROST_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, i, j, k, d, blockState, blockState2, l, m, n, PermafrozenSurfaceBuilders.PERMAFROST_CONFIG);
        }

    }
}
