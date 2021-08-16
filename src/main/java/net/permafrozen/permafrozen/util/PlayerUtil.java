package net.permafrozen.permafrozen.util;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {
    public static boolean isWarmBlockNearby(LivingEntity livingEntity) {
        for (int i = -3; i < 3 + 1; i++) {
            for (int u = -3; u < 3 + 1; u++) {
                for (int k = -3 - 1; k < 3; k++) {
                    BlockPos pos = new BlockPos(livingEntity.getBlockPos().getX() + i, livingEntity.getBlockPos().getY() + k, livingEntity.getBlockPos().getZ() + u);
                    if (livingEntity.world.getBlockState(pos).isIn(BlockTags.FIRE) || livingEntity.world.getBlockState(pos).isIn(BlockTags.CAMPFIRES) || livingEntity.world.getBlockState(pos).isIn(BlockTags.CANDLES) || livingEntity.world.getBlockState(pos).isIn(BlockTags.STRIDER_WARM_BLOCKS)
                            || (livingEntity.world.getBlockState(pos).getBlock() instanceof AbstractFurnaceBlock && livingEntity.world.getBlockState(pos).get(AbstractFurnaceBlock.LIT)) || (livingEntity.world.getBlockState(pos).getBlock() instanceof TorchBlock)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
