package ladysnake.permafrozen.block;

import ladysnake.permafrozen.registry.PermafrozenEntities;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

public class PrismarineCrystalClusterBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    protected final VoxelShape NORTH_SHAPE;
    protected final VoxelShape SOUTH_SHAPE;
    protected final VoxelShape EAST_SHAPE;
    protected final VoxelShape WEST_SHAPE;
    protected final VoxelShape UP_SHAPE;
    protected final VoxelShape DOWN_SHAPE;

    public PrismarineCrystalClusterBlock(int height, int xzOffset, Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
        this.UP_SHAPE = Block.createCuboidShape(xzOffset, 0.0D, xzOffset, 16 - xzOffset, height, 16 - xzOffset);
        this.DOWN_SHAPE = Block.createCuboidShape(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0D, 16 - xzOffset);
        this.NORTH_SHAPE = Block.createCuboidShape(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0D);
        this.SOUTH_SHAPE = Block.createCuboidShape(xzOffset, xzOffset, 0.0D, 16 - xzOffset, 16 - xzOffset, height);
        this.EAST_SHAPE = Block.createCuboidShape(0.0D, xzOffset, xzOffset, height, 16 - xzOffset, 16 - xzOffset);
        this.WEST_SHAPE = Block.createCuboidShape(16 - height, xzOffset, xzOffset, 16.0D, 16 - xzOffset, 16 - xzOffset);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch(direction) {
            case NORTH:
                return this.NORTH_SHAPE;
            case SOUTH:
                return this.SOUTH_SHAPE;
            case EAST:
                return this.EAST_SHAPE;
            case WEST:
                return this.WEST_SHAPE;
            case DOWN:
                return this.DOWN_SHAPE;
            case UP:
            default:
                return this.UP_SHAPE;
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }

        return direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(FACING, ctx.getSide());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = Properties.FACING;
    }
}
