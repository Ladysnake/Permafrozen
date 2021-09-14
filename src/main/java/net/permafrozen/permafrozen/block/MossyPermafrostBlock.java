package net.permafrozen.permafrozen.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.FlowerFeature;
import net.permafrozen.permafrozen.block.util.PermafrozenSpreadableBlock;

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
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = Blocks.GRASS.getDefaultState();

        label48:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!world.getBlockState(blockPos2.down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) {
                    continue label48;
                }
            }

            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.isOf(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((Fertilizable)blockState.getBlock()).grow(world, random, blockPos2, blockState2);
            }

            if (blockState2.isAir()) {
                BlockState blockState4;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = world.getBiome(blockPos2).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    blockState4 = getFlowerState(random, blockPos2, (ConfiguredFeature)list.get(0));
                } else {
                    blockState4 = blockState;
                }

                if (blockState4.canPlaceAt(world, blockPos2)) {
                    world.setBlockState(blockPos2, blockState4, 3);
                }
            }
        }

    }

    private static <U extends FeatureConfig> BlockState getFlowerState(Random random, BlockPos pos, ConfiguredFeature<U, ?> flowerFeature) {
        FlowerFeature<U> flowerFeature2 = (FlowerFeature)flowerFeature.feature;
        return flowerFeature2.getFlowerState(random, pos, flowerFeature.getConfig());
    }
}
