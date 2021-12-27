package net.ladysnake.permafrozen.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.*;
import net.ladysnake.permafrozen.block.util.PermafrozenSpreadableBlock;

import java.util.List;
import java.util.Random;

public class MossyPermafrostBlock extends PermafrozenSpreadableBlock implements Fertilizable {
    public MossyPermafrostBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? (BlockState)state.with(SNOWY, isSnow(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = Blocks.GRASS.getDefaultState();
        block0: for (int i = 0; i < 128; ++i) {
            PlacedFeature placedFeature;
            BlockPos blockPos2 = blockPos;
            for (int j = 0; j < i / 16; ++j) {
                if (!world.getBlockState((blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1)).down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) continue block0;
            }
            BlockState j = world.getBlockState(blockPos2);
            if (j.isOf(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((Fertilizable)((Object)blockState.getBlock())).grow(world, random, blockPos2, j);
            }
            if (!j.isAir()) continue;
            if (random.nextInt(8) == 0) {
                List<ConfiguredFeature<?, ?>> list = world.getBiome(blockPos2).getGenerationSettings().getFlowerFeatures();
                if (list.isEmpty()) continue;
                placedFeature = ((RandomPatchFeatureConfig)list.get(0).getConfig()).feature().get();
            } else {
                placedFeature = VegetationPlacedFeatures.GRASS_BONEMEAL;
            }
            placedFeature.generateUnregistered(world, world.getChunkManager().getChunkGenerator(), random, blockPos2);
        }

    }

    private static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }
}
