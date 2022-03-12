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
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

public class PermafrozenConfiguredFeatures {
	public static final List<OreFeatureConfig.Target> WULFRAM_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.WULFRAM_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE.getDefaultState()));
	public static final List<OreFeatureConfig.Target> DIAMOND_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.SHIVERSLATE_DIAMOND_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState()));
	public static final List<OreFeatureConfig.Target> COAL_ORES = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, PermafrozenBlocks.SHIVERSLATE_COAL_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COAL_ORE.getDefaultState()));
	// configured features
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> SPIRESHROOM = register("spireshroom", PermafrozenFeatures.SPIRESHROOM.configure(FeatureConfig.DEFAULT));
	public static final ConfiguredFeature<DefaultFeatureConfig, ?> SHIVERSLATE_ROCK = register("shiverslate_rock", PermafrozenFeatures.SHIVERSLATE_ROCK.configure(FeatureConfig.DEFAULT));
	public static final ConfiguredFeature<DefaultFeatureConfig, ?> DEADWOOD_TREE = register("deadwood_tree", PermafrozenFeatures.DEADWOOD_TREE.configure(FeatureConfig.DEFAULT));
    public static final ConfiguredFeature<HugeMushroomFeatureConfig, ?> HUGE_SPECTRAL_CAP = register("huge_spectral_cap", Feature.HUGE_RED_MUSHROOM.configure(new HugeMushroomFeatureConfig(
    		BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
		    BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP_STEM.getDefaultState()),
		    3)));
	public static final ConfiguredFeature<?, ?> FREEZE_TOP_FEN_LAYER = register("freeze_top_fen_layer", PermafrozenFeatures.FREEZE_TOP_FEN_LAYER.configure(FeatureConfig.DEFAULT));

    public static final ConfiguredFeature<RandomPatchFeatureConfig, ?> GLAUCA_PATCH = register("glauca_patch", Feature.RANDOM_PATCH.configure(randomPatchConfig(BlockStateProvider.of(PermafrozenBlocks.GLAUCA_GRASS.getDefaultState().with(PermafrozenPlantBlock.SNOWY, true)), 32)));
    public static final ConfiguredFeature<SimpleBlockFeatureConfig, ?> SPECTRAL_CAP = register("spectral_cap", Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(PermafrozenBlocks.SPECTRAL_CAP))));
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> AURORA_CORAL = register("aurora_coral", PermafrozenFeatures.AURORA_CORAL.configure(FeatureConfig.DEFAULT));
	public static final ConfiguredFeature<GeodeFeatureConfig, ?> PRISMARINE_GEODE = register("prismarine_geode", Feature.GEODE.configure(new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.WATER), BlockStateProvider.of(PermafrozenBlocks.BUDDING_PRISMARINE), BlockStateProvider.of(PermafrozenBlocks.BUDDING_PRISMARINE), BlockStateProvider.of(PermafrozenBlocks.SAPPHIRE_SAND), BlockStateProvider.of(PermafrozenBlocks.COBBLED_SHIVERSLATE), List.of(PermafrozenBlocks.SMALL_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.MEDIUM_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.LARGE_PRISMARINE_BUD.getDefaultState(), PermafrozenBlocks.PRISMARINE_CLUSTER.getDefaultState()), BlockTags.FEATURES_CANNOT_REPLACE.getId(), BlockTags.GEODE_INVALID_BLOCKS.getId()), new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2), new GeodeCrackConfig(0.95, 2.0, 2), 0.35, 0.083, true, UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4), UniformIntProvider.create(1, 2), -16, 16, 0.05, 1)));
	public static final ConfiguredFeature<?, ?> ORE_COAL = register("ore_coal", Feature.ORE.configure(new OreFeatureConfig(COAL_ORES, 21)));
	public static final ConfiguredFeature<?, ?> ORE_COAL_BURIED = register("ore_coal_buried", Feature.ORE.configure(new OreFeatureConfig(COAL_ORES, 25, 0.5f)));
	public static final ConfiguredFeature<?, ?> ORE_WULFRAM = register("ore_wulfram", Feature.ORE.configure(new OreFeatureConfig(WULFRAM_ORES, 8)));
	public static final ConfiguredFeature<?, ?> ORE_WULFRAM_SMALL = register("ore_wulfram_small", Feature.ORE.configure(new OreFeatureConfig(WULFRAM_ORES, 4)));
	public static final ConfiguredFeature<?, ?> ORE_DIAMOND_SMALL = register("ore_diamond_small", Feature.ORE.configure(new OreFeatureConfig(DIAMOND_ORES, 8, 0.5f)));
	public static final ConfiguredFeature<?, ?> ORE_DIAMOND_LARGE = register("ore_diamond_large", Feature.ORE.configure(new OreFeatureConfig(DIAMOND_ORES, 20, 0.7f)));
	public static final ConfiguredFeature<?, ?> ORE_DIAMOND_BURIED = register("ore_diamond_buried", Feature.ORE.configure(new OreFeatureConfig(DIAMOND_ORES, 20, 1.0f)));



	// random selectors
	public static final ConfiguredFeature<RandomFeatureConfig, ?> SHRUMNAL_SPIRES_VEGETATION = register("shrumnal_spires_vegetation", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
		    List.of(new RandomFeatureEntry(HUGE_SPECTRAL_CAP.withPlacement(), 0.0625f)),
		    SPIRESHROOM.withPlacement()
    )));

	private static RandomPatchFeatureConfig randomPatchConfig(BlockStateProvider block, int tries) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(block)).withInAirFilter());
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), configuredFeature);
	}
}
