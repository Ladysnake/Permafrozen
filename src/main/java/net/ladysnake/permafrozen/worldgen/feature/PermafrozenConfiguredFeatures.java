package net.ladysnake.permafrozen.worldgen.feature;

public class PermafrozenConfiguredFeatures {
	/*private static final Map<ConfiguredFeature<?, ?>, Identifier> CONFIGURED_STUFF = new LinkedHashMap<>();

	public static final ConfiguredFeature<TreeFeatureConfig, ?> FIR = register("fir", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new StraightTrunkPlacer(10, 7, 9), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(7, 9)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().dirtProvider(new SimpleBlockStateProvider(PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState())).build()));
	public static final ConfiguredFeature<TreeFeatureConfig, ?> MEGA_FIR = register("big_fir", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new GiantTrunkPlacer(18, 11, 15), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)), new TwoLayersFeatureSize(1, 1, 2))).dirtProvider(new SimpleBlockStateProvider(PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState())).build()));
	public static final ConfiguredFeature<TreeFeatureConfig, ?> MEGA_FIR_DOS = register("slightly_less_big_fir", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new GiantTrunkPlacer(18, 6, 15), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(9, 17)), new TwoLayersFeatureSize(1, 1, 2))).build()));
	public static final ConfiguredFeature<?, ?> PATCH_SPECTRAL_CAP = register("patch_spectral_cap", Feature.RANDOM_PATCH.configure((new net.minecraft.world.gen.feature.RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.SPECTRAL_CAP.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(3).build()));
	public static final ConfiguredFeature<?, ?> PRISMARINE_SPIKE = register("prismarine_spike", (ConfiguredFeature)PermafrozenFeature.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP).repeat(3));
	public static final ConfiguredFeature<?, ?> AURORA_CORAL = register("aurora_coral", (ConfiguredFeature)PermafrozenFeature.PRISMARINE_SPIKE.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_TOP_SOLID_HEIGHTMAP).repeat(1));
	public static final ConfiguredFeature<?, ?> PATCH_GLAUCA = register("patch_glauca", Feature.RANDOM_PATCH.configure((new net.minecraft.world.gen.feature.RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.GLAUCA_GRASS.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(64).build()));

	public static <BEE extends FeatureConfig> ConfiguredFeature<BEE, ?> register(String id, ConfiguredFeature<BEE, ?> feature) {
		CONFIGURED_STUFF.put(feature, new Identifier(Permafrozen.MOD_ID, id));
		return feature;
	}
	public static final ConfiguredFeature<?, ?> TREES_FIR = register("trees_" + "fir", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(MEGA_FIR.withChance(0.1F), MEGA_FIR_DOS.withChance(0.1F)), FIR)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

	public static final ConfiguredFeature<?, ?> TREES_FIR_SPARSE = register("trees_" + "fir_sparse", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(FIR.withChance(0.02f)), FIR)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.01F, 0))));

	public static void init() {
		CONFIGURED_STUFF.keySet().forEach(configuredFeature -> Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, CONFIGURED_STUFF.get(configuredFeature), configuredFeature));
	}*/
}
