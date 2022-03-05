package ladysnake.permafrozen.worldgen;


import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.block.util.PermafrozenPlantBlock;
import ladysnake.permafrozen.registry.PermafrozenBlocks;
import ladysnake.permafrozen.worldgen.feature.PermafrozenFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

public class PermafrozenConfiguredFeatures {
	public static final List<OreFeatureConfig.Target> WULFRAM_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.WULFRAM_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE.getDefaultState()));
	public static final List<OreFeatureConfig.Target> DIAMOND_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.SHIVERSLATE_DIAMOND_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState()));
	public static final List<OreFeatureConfig.Target> COAL_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.SHIVERSLATE_COAL_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COAL_ORE.getDefaultState()));
	// configured features
	public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SNOWY_TREES_CONFIGURED = register("snow_under_trees", PermafrozenFeatures.SNOW_UNDER_TREES_FEATURE, FeatureConfig.DEFAULT);
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SPIRESHROOM = register("spireshroom", PermafrozenFeatures.SPIRESHROOM, FeatureConfig.DEFAULT);
	public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> SHIVERSLATE_ROCK = register("shiverslate_rock", PermafrozenFeatures.SHIVERSLATE_ROCK, FeatureConfig.DEFAULT);
	public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> DEADWOOD_TREE = register("deadwood_tree", PermafrozenFeatures.DEADWOOD_TREE, FeatureConfig.DEFAULT);
    public static final RegistryEntry<ConfiguredFeature<HugeMushroomFeatureConfig, ?>> HUGE_SPECTRAL_CAP = register("huge_spectral_cap", Feature.HUGE_RED_MUSHROOM, new HugeMushroomFeatureConfig(
    		BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
		    BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_STEM.getDefaultState()),
		    3));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> GLAUCA_PATCH = register("glauca_patch", Feature.RANDOM_PATCH, randomPatchConfig(BlockStateProvider.of(PermafrozenBlocks.GLAUCA_GRASS.getDefaultState().with(PermafrozenPlantBlock.SNOWY, true)), 32));
    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> SPECTRAL_CAP = register("spectral_cap", Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP)));
	public static final RegistryEntry<ConfiguredFeature<GeodeFeatureConfig, ?>> PRISMARINE_GEODE = register("prismarine_geode", Feature.GEODE, new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.WATER), BlockStateProvider.of(PermafrozenBlocks.BUDDING_PRISMARINE), BlockStateProvider.of(PermafrozenBlocks.BUDDING_PRISMARINE), BlockStateProvider.of(PermafrozenBlocks.SAPPHIRE_SAND), BlockStateProvider.of(PermafrozenBlocks.COBBLED_SHIVERSLATE), List.of(PermafrozenBlocks.SMALL_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.MEDIUM_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.LARGE_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.PRISMARINE_CLUSTER.getDefaultState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2), new GeodeCrackConfig(0.95, 2.0, 2), 0.35, 0.083, true, UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4), UniformIntProvider.create(1, 2), -16, 16, 0.05, 1));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_COAL = register("ore_coal", Feature.ORE, new OreFeatureConfig(COAL_ORES, 21));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_COAL_BURIED = register("ore_coal_buried", Feature.ORE, new OreFeatureConfig(COAL_ORES, 25, 0.5f));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_WULFRAM = register("ore_wulfram", Feature.ORE, new OreFeatureConfig(WULFRAM_ORES, 8));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_WULFRAM_SMALL = register("ore_wulfram_small", Feature.ORE, new OreFeatureConfig(WULFRAM_ORES, 4));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_DIAMOND_SMALL = register("ore_diamond_small", Feature.ORE, new OreFeatureConfig(DIAMOND_ORES, 8, 0.5f));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_DIAMOND_LARGE = register("ore_diamond_large", Feature.ORE, new OreFeatureConfig(DIAMOND_ORES, 20, 0.7f));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_DIAMOND_BURIED = register("ore_diamond_buried", Feature.ORE, new OreFeatureConfig(DIAMOND_ORES, 20, 1.0f));



	// random selectors
	public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> SHRUMNAL_SPIRES_VEGETATION = register("shrumnal_spires_vegetation", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(
		    List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(HUGE_SPECTRAL_CAP, new PlacementModifier[0]), 0.025f)),
		    PlacedFeatures.createEntry(SPIRESHROOM, new PlacementModifier[0]
    )));

	private static RandomPatchFeatureConfig randomPatchConfig(BlockStateProvider block, int tries) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
	}

	private static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String id, F feature, FC config) {
		return RegistryEntry.of(Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), new ConfiguredFeature<FC, F>(feature, config)));
	}
}
