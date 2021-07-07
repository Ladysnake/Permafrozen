package net.permafrozen.permafrozen.worldgen.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenConfiguredFeatures {
    private static final Map<ConfiguredFeature<?, ?>, Identifier> CONFIGURED_STUFF = new LinkedHashMap<>();

    public static ConfiguredFeature<TreeFeatureConfig, ?> FIR = register("fir",Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new StraightTrunkPlacer(5, 2, 1), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build()));
    public static ConfiguredFeature<TreeFeatureConfig, ?> MEGA_FIR = register("big_fir", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new GiantTrunkPlacer(13, 2, 14), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState())))).build()));
    public static ConfiguredFeature<TreeFeatureConfig, ?> MEGA_FIR_DOS = register("slightly_less_big_fir", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LOG.getDefaultState()), new GiantTrunkPlacer(13, 2, 14), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_LEAVES.getDefaultState()), new SimpleBlockStateProvider(PermafrozenBlocks.FIR_SAPLING.getDefaultState()), new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(3, 17)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState())))).build()));

    public static <BEE extends FeatureConfig> ConfiguredFeature<BEE, ?> register(String id, ConfiguredFeature<BEE, ?> feature) {
        CONFIGURED_STUFF.put(feature, new Identifier(Permafrozen.MOD_ID, id));
        return feature;
    }
    public static final ConfiguredFeature<?, ?> TREES_FIR = register(
            "trees_" + "fir",
            Feature.RANDOM_SELECTOR.configure(
                    new RandomFeatureConfig(
                            ImmutableList.of(MEGA_FIR.withChance(0.1F), MEGA_FIR_DOS.withChance(0.1F)),
                            FIR
                    )
            )
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                    .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1)))
    );

    public static void commenceForth() {
        CONFIGURED_STUFF.keySet().forEach(configuredFeature -> Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, CONFIGURED_STUFF.get(configuredFeature), configuredFeature));
    }
}
