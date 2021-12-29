package net.ladysnake.permafrozen.worldgen;


import net.ladysnake.permafrozen.worldgen.feature.PermafrozenFeature;
import net.minecraft.world.gen.feature.*;

public class PermafrozenConfiguredFeatures {
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> SPIRESHROOM = ConfiguredFeatures.register("spireshroom", PermafrozenFeature.SPIRESHROOM.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> AURORA_CORAL = ConfiguredFeatures.register("aurora_coral", PermafrozenFeature.AURORA_CORAL.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> PRISMARINE_SPIKE = ConfiguredFeatures.register("prismarine_spike", PermafrozenFeature.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT));
}
