package ladysnake.permafrozen.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AuroraArmourItem extends ArmorItem implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private int openTicks = 0;
    private boolean closed = true;
    private boolean shouldSwitch = false;

    public AuroraArmourItem(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings)  {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        stack.getOrCreateNbt().putBoolean("closed", closed);
        stack.getOrCreateNbt().putBoolean("shouldSwitch", shouldSwitch);
        stack.getOrCreateNbt().putInt("openTicks", openTicks);
        openTicks = stack.getOrCreateNbt().getInt("openTicks");
        shouldSwitch = stack.getOrCreateNbt().getBoolean("shouldSwitch");
        closed = stack.getOrCreateNbt().getBoolean("closed");
        if(entity.isSneaking() && !entity.isOnGround() && openTicks == 0) {
            shouldSwitch = true;
        }
        if(shouldSwitch) {
            openTicks++;
        }
        if(openTicks >= 41) {
            shouldSwitch = false;
            openTicks = 0;
            closed = !closed;
        }
        stack.getOrCreateNbt().putBoolean("closed", closed);
        stack.getOrCreateNbt().putBoolean("shouldSwitch", shouldSwitch);
        stack.getOrCreateNbt().putInt("openTicks", openTicks);

    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.getOrCreateNbt().putBoolean("Unbreakable", true);
        return stack;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);
        if (livingEntity instanceof ArmorStandEntity) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idleClosed", true));
            return PlayState.CONTINUE;
        } else if (livingEntity instanceof PlayerEntity player) {
            if(player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getBoolean("closed")) {
                if(player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getInt("openTicks") > 0) {
                    System.out.println(player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getInt("openTicks"));
                    event.getController().markNeedsReload();
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("open", true));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("idleClosed", true));
                }
            } else {
                if(player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getInt("openTicks") > 0) {
                    System.out.println(player.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getInt("openTicks"));
                    event.getController().markNeedsReload();
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("close", true));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("idleOpen", true));
                }
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
