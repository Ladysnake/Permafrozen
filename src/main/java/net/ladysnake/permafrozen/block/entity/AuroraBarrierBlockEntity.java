package net.ladysnake.permafrozen.block.entity;

import net.ladysnake.permafrozen.block.AuroraBarrierBlock;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AuroraBarrierBlockEntity extends BlockEntity {
    private static HashSet<BlockPos> barrierList;
    private static HashSet<BlockPos> opposingList;
    public AuroraBarrierBlockEntity(BlockPos pos, BlockState state) {
        super(PermafrozenEntities.AURORA_BARRIER_TYPE, pos, state);
        barrierList = new HashSet<>();
        opposingList = new HashSet<>();
    }
    private static HashSet<BlockPos> getBarrierPositions(World world, BlockPos pos, HashSet<BlockPos> omitList) {
        HashSet<BlockPos> list = new HashSet<>();
        BlockPos pos2 = pos;
        for (Direction direction : Arrays.stream(Direction.values()).toList()) {
            for (int i = 0; i < (96); i++) {
                pos2 = pos2.offset(direction);
                BlockState state = world.getBlockState(pos2);
                if (!(state.getBlock() instanceof AirBlock) && !(state.getBlock() instanceof FluidBlock)) {
                    if (state.getBlock() instanceof AuroraBarrierBlock && !omitList.contains(pos2)) {
                        if(!list.contains(pos2)) {
                        list.add(pos2);
                        list.addAll(getBarrierPositions(world, pos2, list));
                        }
                    }
                    break;
                }
            }
        }
        return list;
    }

    public static void tick(World tickerWorld, BlockPos pos, BlockState tickerState, AuroraBarrierBlockEntity blockEntity) {
        //prep
        barrierList = new HashSet<>();
        if(AuroraBarrierBlock.isPowered(tickerState)) {
            barrierList = getBarrierPositions(tickerWorld, pos, new HashSet<>());
            if (!barrierList.isEmpty()) {
                for (BlockPos barrierPos : barrierList) {
                    if (barrierPos.getX() == pos.getX()) {
                        if (tickerWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), barrierPos.getZ())).getBlock() instanceof AuroraBarrierBlock && tickerWorld.getBlockState(new BlockPos(pos.getX(), barrierPos.getY(), pos.getZ())).getBlock() instanceof AuroraBarrierBlock) {
                            opposingList.add(barrierPos);
                        }
                    }
                    if (barrierPos.getY() == pos.getY()) {
                        if (tickerWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), barrierPos.getZ())).getBlock() instanceof AuroraBarrierBlock && tickerWorld.getBlockState(new BlockPos(barrierPos.getX(), pos.getY(), pos.getZ())).getBlock() instanceof AuroraBarrierBlock) {
                            opposingList.add(barrierPos);
                        }
                    }
                    if (barrierPos.getZ() == pos.getZ()) {
                        if (tickerWorld.getBlockState(new BlockPos(barrierPos.getX(), pos.getY(), pos.getZ())).getBlock() instanceof AuroraBarrierBlock && tickerWorld.getBlockState(new BlockPos(pos.getX(), barrierPos.getY(), pos.getZ())).getBlock() instanceof AuroraBarrierBlock) {
                            opposingList.add(barrierPos);
                        }
                    }
                }
            }
            List<Box> boxList = new ArrayList<>();
            for (BlockPos oppositePos : opposingList) {
                Box box = new Box(pos, oppositePos);
                boxList.add(box);
            }
            //the blocking
            for (Box box : boxList) {
                List<Entity> list = tickerWorld.getEntitiesByClass(Entity.class, box, (entity) -> entity.getVelocity().getX() != 0 || entity.getVelocity().getZ() != 0);
                for (Entity entity : list) {
                    entity.setVelocity(-entity.getVelocity().x, entity.getVelocity().y, -entity.getVelocity().z);
                }
            }
        }
    }
}
