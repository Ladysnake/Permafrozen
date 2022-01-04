package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.util.MutableVec3d;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DeadwoodTreeFeature extends Feature<DefaultFeatureConfig> {
	public DeadwoodTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(context.getOrigin());

		// don't generate on ice
		if (world.getBlockState(pos.down()).isOf(Blocks.ICE)) {
			pos.offset(Direction.DOWN);

			if (!world.getBlockState(pos.down()).isOf(PermafrozenBlocks.MOSSY_PERMAFROST)) {
				return false;
			}
		}

		Random random = context.getRandom();

		final int baseTrunkHeight = 6 + random.nextInt(3);

		// generate base trunk
		for (int i = 0; i <= baseTrunkHeight; ++i) {
			this.setBlockState(world, pos, LOG);
			pos.move(Direction.UP);
		}

		this.setBlockState(world, pos, LOG);

		// generate branches
		double baseX = 1.0;
		double baseZ = random.nextDouble();

		// 4 mostly horizontal branches
		this.branch(world, random, pos, baseX, -0.333, baseZ, baseTrunkHeight - 2);
		this.branch(world, random, pos, baseZ, -0.333, -baseX, baseTrunkHeight - 2);
		this.branch(world, random, pos, -baseX, -0.333, -baseZ, baseTrunkHeight - 2);
		this.branch(world, random, pos, -baseZ, -0.333, baseX, baseTrunkHeight - 2);

		Direction topDir = Direction.fromHorizontal(random.nextInt(4));
		BlockPos nextOrigin = this.branch(world, random, pos, topDir.getOffsetX(), 1, topDir.getOffsetZ(), baseTrunkHeight / 2, baseTrunkHeight / 3);

		this.branch(world, random, nextOrigin, -topDir.getOffsetX(), 1, -topDir.getOffsetZ(), baseTrunkHeight / 2);
		return true;
	}

	private void branch(StructureWorldAccess world, Random rand, BlockPos origin, double angleX, double angleY, double angleZ, int length) {
		this.branch(world, rand, origin, angleX,angleY, angleZ, length, length);
	}

	@Nullable
	private BlockPos branch(StructureWorldAccess world, Random rand, BlockPos origin, double angleX, double angleY, double angleZ, int length, int requestedResult) {
		BlockPos.Mutable blockpos = new BlockPos.Mutable();
		MutableVec3d pos = new MutableVec3d(origin);
		BlockPos result = null;

		for (int i = 0; i <= length; ++i) {
			pos.move(angleX, angleY, angleZ);
			pos.transferTo(blockpos);

			this.setBlockState(world, blockpos, LOG);

			// chance to grow an extra block up or down at any point along
			if (rand.nextDouble() < Math.abs(pos.getY())) {
				this.setBlockState(world, blockpos.offset(pos.getY() > 0 ? Direction.UP : Direction.DOWN), LOG);
			}

			if (i == requestedResult) {
				result = new BlockPos(blockpos);
			}
		}

		return result;
	}

	private static final BlockState LOG = PermafrozenBlocks.DEADWOOD_LOG.getDefaultState();
}
