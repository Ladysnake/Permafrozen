package net.ladysnake.permafrozen.block;

import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class StrippableDeadwoodBlock extends StrippableLogBlock {
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
    private Supplier<Block> stripped;
    public StrippableDeadwoodBlock(Supplier<Block> stripped, MapColor top, Settings settings) {
        super(stripped, top, settings);
        this.stripped = stripped;
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(PERSISTENT, false).with(StrippableLogBlock.AXIS, Direction.Axis.Y)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getEquippedStack(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

        if(heldStack.isEmpty()) {
            return ActionResult.FAIL;
        }

        Item held = heldStack.getItem();
        if(!(held instanceof MiningToolItem)) {
            return ActionResult.FAIL;
        }

        MiningToolItem tool = (MiningToolItem) held;

        if(stripped != null && (tool.getMiningSpeedMultiplier(heldStack, state) > 1.0F)) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if(!world.isClient) {
                BlockState target = stripped.get().getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));

                world.setBlockState(pos, target);
                if(state.get(PERSISTENT) && player.getRandom().nextInt(10) == 9) {
                    Entity entity = PermafrozenEntities.BURROW_GRUB.create(world);
                    entity.setPos(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(entity);
                }

                heldStack.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        if(state.get(PERSISTENT) && player.getRandom().nextInt(10) == 9) {
            Entity entity = PermafrozenEntities.BURROW_GRUB.create((World) world);
            entity.setPos(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(entity);
        }
        super.afterBreak(world, player, pos, state, blockEntity, stack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
        super.appendProperties(builder);
    }

}
