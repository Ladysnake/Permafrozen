package net.permafrozen.permafrozen.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.permafrozen.permafrozen.block.util.PermafrozenLeavesBlock;

public class Blomcks {
    public static PermafrozenLeavesBlock createLeavesBlock(BlockSoundGroup soundGroup) {
        return new PermafrozenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(Blomcks::canSpawnOnLeaves).suffocates(Blomcks::never).blockVision(Blomcks::never));
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }
    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
}
