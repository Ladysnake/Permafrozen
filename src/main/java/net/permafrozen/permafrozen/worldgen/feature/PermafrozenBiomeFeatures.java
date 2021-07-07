package net.permafrozen.permafrozen.worldgen.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class PermafrozenBiomeFeatures {
    public static void addFirTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenConfiguredFeatures.TREES_FIR);
    }
}
