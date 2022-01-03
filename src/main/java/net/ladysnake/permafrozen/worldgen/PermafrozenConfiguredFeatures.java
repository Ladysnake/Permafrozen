package net.ladysnake.permafrozen.worldgen;


import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.block.util.PermafrozenPlantBlock;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.worldgen.feature.PermafrozenFeatures;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class PermafrozenConfiguredFeatures {
	// configured features
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> SPIRESHROOM = register("spireshroom", PermafrozenFeatures.SPIRESHROOM.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<HugeMushroomFeatureConfig, ?> SPECTRAL_CAP = register("spectral_cap", Feature.HUGE_RED_MUSHROOM.configure(new HugeMushroomFeatureConfig(
    		BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
		    BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_STEM.getDefaultState()),
		    3)));

    public static final ConfiguredFeature<RandomPatchFeatureConfig, ?> GLAUCA_PATCH = register("glauca_patch", Feature.RANDOM_PATCH.configure(grassRandomPatch(BlockStateProvider.of(PermafrozenBlocks.GLAUCA_GRASS.getDefaultState().with(PermafrozenPlantBlock.SNOWY, true)), 32)));
    public static final ConfiguredFeature<RandomPatchFeatureConfig, ?> SPECTRAL_CAP_PATCH = register("spectral_cap_patch", Feature.RANDOM_PATCH.configure(shroomRandomPatch(BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP))));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> AURORA_CORAL = register("aurora_coral", PermafrozenFeatures.AURORA_CORAL.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> PRISMARINE_SPIKE = register("prismarine_spike", PermafrozenFeatures.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT));

    // random selectors
	public static final ConfiguredFeature<RandomFeatureConfig, ?> SHRUMNAL_SPIRES_VEGETATION = register("shrumnal_spires_vegetation", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
		    List.of(new RandomFeatureEntry(SPECTRAL_CAP.withPlacement(), 0.0625f)),
		    SPIRESHROOM.withPlacement()
    )));

	private static RandomPatchFeatureConfig grassRandomPatch(BlockStateProvider block, int tries) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(block)).withInAirFilter());
	}

	private static RandomPatchFeatureConfig shroomRandomPatch(BlockStateProvider block) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(block)));
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), configuredFeature);
	}
}
