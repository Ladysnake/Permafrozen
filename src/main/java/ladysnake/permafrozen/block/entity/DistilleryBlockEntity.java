package ladysnake.permafrozen.block.entity;

import ladysnake.permafrozen.registry.PermafrozenBlocks;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import ladysnake.permafrozen.registry.PermafrozenItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Iterator;

public class DistilleryBlockEntity extends LootableContainerBlockEntity {
    private static int cookingTicks = 0;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private ViewerCountManager stateManager = new ViewerCountManager() {
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            DistilleryBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
        }

        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            DistilleryBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
        }

        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                return inventory == DistilleryBlockEntity.this;
            } else {
                return false;
            }
        }
    };
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }

    }

    public int size() {
        return 9;
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }
    public static void tick(World world, BlockPos pos, BlockState state, DistilleryBlockEntity blockEntity) {
        BlockState targetState = world.getBlockState(pos.offset(state.get(Properties.HORIZONTAL_FACING).getOpposite()).down());
        BlockState downState = world.getBlockState(pos.down());

        if (downState.isIn(BlockTags.FIRE) || downState.isIn(BlockTags.CAMPFIRES) || downState.isIn(BlockTags.CANDLES) || downState.isIn(BlockTags.STRIDER_WARM_BLOCKS) || (downState.getBlock() instanceof AbstractFurnaceBlock && downState.get(AbstractFurnaceBlock.LIT)) || (downState.getBlock() instanceof TorchBlock)) {
            world.addParticle(ParticleTypes.SMOKE, true, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5,  0, 0, 0);
            if(cookingTicks > 0) {
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5,  0, 0, 0);
            }
        }
        boolean isCooking = false;
        if(targetState.getBlock() instanceof BarrelBlock || targetState.getBlock() instanceof ChestBlock) {
            for (ItemStack stack : blockEntity.inventory) {
                if (stack.isOf(PermafrozenBlocks.SPECTRAL_CAP.asItem())) {
                    isCooking = true;
                    if (cookingTicks >= 600) {
                        BlockEntity entity = world.getBlockEntity(pos.offset(state.get(Properties.HORIZONTAL_FACING).getOpposite()).down());
                        if (entity != null) {
                            if (entity instanceof BarrelBlockEntity barrel) {
                                for (int i = 0; i < barrel.size(); ++i) {
                                    if (barrel.getStack(i).isEmpty()) {
                                        barrel.setStack(i, PermafrozenItems.SPECTRAL_DUST.getDefaultStack());
                                        stack.decrement(1);
                                        break;
                                    }
                                    if (barrel.getStack(i).isOf(PermafrozenItems.SPECTRAL_DUST) && !(barrel.getStack(i).getCount() >= barrel.getStack(i).getMaxCount())) {
                                        barrel.getStack(i).increment(1);
                                        stack.decrement(1);
                                        break;
                                    }
                                }
                            }
                            if (entity instanceof ChestBlockEntity chest) {
                                for (int i = 0; i < chest.size(); ++i) {
                                    if (chest.getStack(i).isEmpty()) {
                                        chest.setStack(i, PermafrozenItems.SPECTRAL_DUST.getDefaultStack());
                                        stack.decrement(1);
                                        break;
                                    }
                                    if (chest.getStack(i).isOf(PermafrozenItems.SPECTRAL_DUST) && !(chest.getStack(i).getCount() >= chest.getStack(i).getMaxCount())) {
                                        chest.getStack(i).increment(1);
                                        stack.decrement(1);
                                        break;
                                    }
                                }
                            }
                        }
                        cookingTicks = 0;
                    }

                }

            }
        }
        if(isCooking) {
            cookingTicks++;
        }

    }

    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    protected Text getContainerName() {
        return new TranslatableText("container.distillery");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, 1);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    public DistilleryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PermafrozenEntities.DISTILLERY_TYPE, blockPos, blockState);
    }
    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(Properties.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
}
