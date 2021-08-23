package net.permafrozen.permafrozen.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import net.permafrozen.permafrozen.client.particle.aurora.AuroraParticleEffect;

public class AuroraBlasterBlock extends FacingBlock {
    private static final BooleanProperty POWERED = Properties.POWERED;
    public AuroraBlasterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(POWERED, false));
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            this.tryFire(world, pos, state);
        }
        if (world.isClient) {
            this.tryFireClient(world, pos, state);
        }

    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection())).with(POWERED, false);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            this.tryFire(world, pos, state);
        }
        if (world.isClient) {
            this.tryFireClient(world, pos, state);
        }

    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            if (!world.isClient && world.getBlockEntity(pos) == null) {
                this.tryFire(world, pos, state);
            }
            if (world.isClient) {
                this.tryFireClient(world, pos, state);
            }

        }
    }

    private void tryFire(World world, BlockPos pos, BlockState state) {
        if (world.isReceivingRedstonePower(pos)) {
            blast(world, pos, state.get(FACING));
            world.setBlockState(pos, state.with(POWERED, true));
        } else {
            world.setBlockState(pos, state.with(POWERED, false));
        }
    }
    private void tryFireClient(World world, BlockPos pos, BlockState state) {
        if (world.isReceivingRedstonePower(pos) && world.isClient()) {
            switch((Direction)state.get(FACING)) {
                case DOWN:

                case UP:
                default:
                    world.addParticle(new AuroraParticleEffect(0.0f, 1.0f, 0.1f, 0.01f, -0.09f, 0.09f), pos.getX(), pos.getY() + 0.875, pos.getZ() -0.5, 0.1, 0, 0);
                    world.addParticle(new AuroraParticleEffect(0.0f, 1.0f, 0.1f, 0.01f, -0.09f, 0.09f), pos.getX() - 1, pos.getY() + 0.875, pos.getZ() -0.5, -0.1, 0, 0);
                    world.addParticle(new AuroraParticleEffect(0.0f, 1.0f, 0.1f, 0.01f, -0.09f, 0.09f), pos.getX() - 0.5, pos.getY() + 0.875, pos.getZ(), 0, 0, 0.1);
                    world.addParticle(new AuroraParticleEffect(0.0f, 1.0f, 0.1f, 0.01f, -0.09f, 0.09f), pos.getX() - 0.5, pos.getY() + 0.875, pos.getZ() - 1, 0, 0, -0.1);
                case NORTH:

                case SOUTH:

                case WEST:

                case EAST:

            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
        builder.add(POWERED);
    }

    private void blast(World world, BlockPos pos, Direction direction) {
        boolean boom = true;
        BlockPos pos2 = pos;
        for (int i = 0; i < (96); i++) {
            if (boom) {
                pos2 = pos2.offset(direction);
                BlockState state = world.getBlockState(pos2);
                if (!(state.getBlock() instanceof AirBlock) && !(state.getBlock() instanceof FluidBlock)) {
                    world.createExplosion(null, pos2.getX(), pos2.getY(), pos2.getZ(), 4, Explosion.DestructionType.BREAK);
                    boom = false;
                }
            }
        }
    }

}
