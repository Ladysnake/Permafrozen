package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
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
        int i = 0;
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        BlockState spireshroom = PermafrozenBlocks.SPIRESHROOM_LOG.getDefaultState();
        BlockState spireshroomTop = PermafrozenBlocks.SPIRESHROOM_WOOD.getDefaultState();
        int k = 4 + random.nextInt(10);
        for (int l = 0; l <= k; ++l) {
            if(structureWorldAccess.getBlockState(blockPos).isOf(Blocks.WATER) || structureWorldAccess.getBlockState(blockPos).isOf(Blocks.AIR)) {
                if (l == k) {
                    structureWorldAccess.setBlockState(blockPos, (BlockState)spireshroomTop, Block.NOTIFY_LISTENERS);
                    ++i;
                } else {
                    structureWorldAccess.setBlockState(blockPos, spireshroom, Block.NOTIFY_LISTENERS);
                }
            } else if (l > 0) {
                BlockPos blockPos3 = blockPos.down();
                if (!spireshroom.canPlaceAt(structureWorldAccess, blockPos3) || structureWorldAccess.getBlockState(blockPos3.down()).isOf(PermafrozenBlocks.SPIRESHROOM_LOG)) break;
                structureWorldAccess.setBlockState(blockPos3, (BlockState)spireshroomTop, Block.NOTIFY_LISTENERS);
                ++i;
                break;
            }
            blockPos = blockPos.up();
        }
        return i > 0;
    }
}
