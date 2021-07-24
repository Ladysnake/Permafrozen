package net.permafrozen.permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.permafrozen.permafrozen.block.PrismarineCrystalClusterBlock;
import net.permafrozen.permafrozen.registry.PermafrozenBlocks;

import java.util.Random;

public class PrismarineCrystalBlock extends Block {
    private static final Direction[] DIRECTIONS = Direction.values();
    public PrismarineCrystalBlock(Settings settings) {
        super(settings);
    }

    protected static boolean isInWater(BlockView world, BlockPos pos) {
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            FluidState fluidState = world.getFluidState(pos.offset(direction));
            if (fluidState.isIn(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState) && isInWater(world, pos)) {
                block = PermafrozenBlocks.SMALL_PRISMARINE_BUD;
            } else if (blockState.isOf(PermafrozenBlocks.SMALL_PRISMARINE_BUD) && blockState.get(PrismarineCrystalClusterBlock.FACING) == direction && isInWater(world, pos)) {
                block = PermafrozenBlocks.MEDIUM_PRISMARINE_BUD;
            } else if (blockState.isOf(PermafrozenBlocks.MEDIUM_PRISMARINE_BUD) && blockState.get(PrismarineCrystalClusterBlock.FACING) == direction && isInWater(world, pos)) {
                block = PermafrozenBlocks.LARGE_PRISMARINE_BUD;
            } else if (blockState.isOf(PermafrozenBlocks.LARGE_PRISMARINE_BUD) && blockState.get(PrismarineCrystalClusterBlock.FACING) == direction && isInWater(world, pos)) {
                block = PermafrozenBlocks.PRISMARINE_CLUSTER;
            }

            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(PrismarineCrystalClusterBlock.FACING, direction)).with(PrismarineCrystalClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(blockPos, blockState2);
            }

        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}
