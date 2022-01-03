package net.ladysnake.permafrozen.worldgen;


import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.block.util.PermafrozenPlantBlock;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.worldgen.feature.PermafrozenFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class PermafrozenConfiguredFeatures {
	// configured features
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> SPIRESHROOM = register("spireshroom", PermafrozenFeature.SPIRESHROOM.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<RandomPatchFeatureConfig, ?> GLAUCA_PATCH = register("glauca_patch", Feature.RANDOM_PATCH.configure(createRandomPatchConfig(BlockStateProvider.of(PermafrozenBlocks.GLAUCA_GRASS.getDefaultState().with(PermafrozenPlantBlock.SNOWY, true)), 32)));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> AURORA_CORAL = register("aurora_coral", PermafrozenFeature.AURORA_CORAL.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> PRISMARINE_SPIKE = register("prismarine_spike", PermafrozenFeature.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT));

	// placed features
	public static final PlacedFeature NATURAL_SPIRESHROOMS = register("natural_spireshrooms", SPIRESHROOM.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.1f, 1))));
	public static final PlacedFeature GLAUCA_PATCHES = register("glauca_patches", GLAUCA_PATCH.withPlacement(VegetationPlacedFeatures.modifiers(6)));

	private static RandomPatchFeatureConfig createRandomPatchConfig(BlockStateProvider block, int tries) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(block)).withInAirFilter());
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), configuredFeature);
	}

	private static PlacedFeature register(String id, PlacedFeature feature) {
		return Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), feature);
	}
}
