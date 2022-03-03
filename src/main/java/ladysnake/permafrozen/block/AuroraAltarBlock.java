package ladysnake.permafrozen.block;

import ladysnake.permafrozen.block.entity.AuroraAltarBlockEntity;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import ladysnake.permafrozen.registry.PermafrozenItems;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AuroraAltarBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty LIT = Properties.LIT;
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return createCuboidShape(1, 0, 1, 15, 10, 15);
    }

    public AuroraAltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(LIT, false);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).isEmpty()) {
            world.setBlockState(pos, state.with(LIT, false));
            PermafrozenComponents.SNOWSTORM.get(world).setTimeLeft(0);
        }
        if(player.getStackInHand(hand).getItem().equals(PermafrozenItems.DEFERVESCENCE_ORB)) {
            world.setBlockState(pos, state.with(LIT, true));
            if(!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            PermafrozenComponents.SNOWSTORM.get(world).startSnowstorm(world, 20000);
            return ActionResult.CONSUME;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> AuroraAltarBlockEntity.tick(tickerWorld, pos, tickerState, (AuroraAltarBlockEntity) blockEntity);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AuroraAltarBlockEntity(pos, state);
    }
}
