package net.ladysnake.permafrozen.worldgen;


import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.worldgen.feature.PermafrozenFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.feature.*;

public class PermafrozenConfiguredFeatures {
	// configured features
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> SPIRESHROOM = register("spireshroom", PermafrozenFeature.SPIRESHROOM.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> AURORA_CORAL = register("aurora_coral", PermafrozenFeature.AURORA_CORAL.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> PRISMARINE_SPIKE = register("prismarine_spike", PermafrozenFeature.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT));

	// placed features
	public static final PlacedFeature NATURAL_SPIRESHROOMS = register("natural_spireshrooms", SPIRESHROOM.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(2, 0.2f, 2))));

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), configuredFeature);
	}

	private static PlacedFeature register(String id, PlacedFeature feature) {
		return Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), feature);
	}
}
