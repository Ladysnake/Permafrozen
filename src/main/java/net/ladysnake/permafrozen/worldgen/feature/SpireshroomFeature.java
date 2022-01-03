package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class SpireshroomFeature extends Feature<DefaultFeatureConfig> {
    public SpireshroomFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int heightGenerated = 0;
        StructureWorldAccess world = context.getWorld();
        BlockPos.Mutable pos = new BlockPos.Mutable().set(context.getOrigin());
        Random random = context.getRandom();
        BlockState spireshroom = PermafrozenBlocks.SPIRESHROOM_LOG.getDefaultState();
        BlockState spireshroomTop = PermafrozenBlocks.SPIRESHROOM_WOOD.getDefaultState();
        int height = 4 + random.nextInt(10);

        for (int y = 0; y <= height; ++y) {
            if (world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos).isOf(Blocks.AIR)) {
                if (y == height) {
                    world.setBlockState(pos, spireshroomTop, Block.NOTIFY_LISTENERS);
                    ++heightGenerated;
                } else {
                    world.setBlockState(pos, spireshroom, Block.NOTIFY_LISTENERS);
                }
            } else if (y > 0) {
                BlockPos blockPos3 = pos.down();
                if (!spireshroom.canPlaceAt(world, blockPos3) || world.getBlockState(blockPos3.down()).isOf(PermafrozenBlocks.SPIRESHROOM_LOG)) break;
                world.setBlockState(blockPos3, spireshroomTop, Block.NOTIFY_LISTENERS);
                ++heightGenerated;
                break;
            }

            pos.move(Direction.UP);
        }
        return heightGenerated > 0;
    }
}
