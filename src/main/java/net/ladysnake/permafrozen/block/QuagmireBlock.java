package net.ladysnake.permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Optional;

public class QuagmireBlock extends Block {
    public QuagmireBlock(Settings settings) {
        super(settings);
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.8999999761581421D, 1.5D, 0.8999999761581421D));
        }

    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext) {
            EntityShapeContext entityShapeContext = (EntityShapeContext)context;
            Optional<Entity> optional = entityShapeContext.getEntity();
            if (optional.isPresent()) {
                Entity entity = (Entity)optional.get();
                if (entity.fallDistance > 2.5F) {
                    return VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.8999999761581421D, 1.0D);
                }

                boolean bl = entity instanceof FallingBlockEntity;
                if (bl && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }

        return VoxelShapes.empty();
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }
}
