package net.ladysnake.permafrozen.worldgen.feature;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PermafrozenFeature {
    public static final Feature<DefaultFeatureConfig> PRISMARINE_SPIKE = register("prismarine_spike", new PrismarineSpikeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> AURORA_CORAL = register("aurora_coral", new AuroraCoralFeature(DefaultFeatureConfig.CODEC));
    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, name, feature);
    }
}
