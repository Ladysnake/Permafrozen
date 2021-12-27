package net.ladysnake.permafrozen.worldgen.tree;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.ladysnake.permafrozen.worldgen.feature.PermafrozenConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FirSaplingGenerator extends LargeTreeSaplingGenerator {
	@Nullable
	@Override
	protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
		return TreeConfiguredFeatures.AZALEA_TREE;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
		return TreeConfiguredFeatures.AZALEA_TREE;
	}
//	@Nullable
//	@Override
//	protected ConfiguredFeature<TreeFeatureConfig, ?> getLargeTreeFeature(Random random) {
//		return random.nextBoolean() ? PermafrozenConfiguredFeatures.MEGA_FIR : PermafrozenConfiguredFeatures.MEGA_FIR_DOS;
//	}
//
//	@Nullable
//	@Override
//	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
//		return PermafrozenConfiguredFeatures.FIR;
//	}
}
