package ladysnake.permafrozen.item;

import ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import ladysnake.permafrozen.statuseffect.BurrowedStatusEffect;
import ladysnake.permafrozen.statuseffect.SpectralDazeStatusEffect;
import ladysnake.permafrozen.statuseffect.WraithwrathStatusEffect;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class PermafrozenBottleItem extends Item {
    private final StatusEffect effect;
    private static final int MAX_USE_TIME = 40;

    public PermafrozenBottleItem(StatusEffect shiz, Item.Settings settings) {
        super(settings);
        effect = shiz;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Object serverPlayerEntity;
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            serverPlayerEntity = user;
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)serverPlayerEntity, stack);
            ((PlayerEntity)serverPlayerEntity).incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(effect, 6000, 0, false, true));
            if(effect instanceof WraithwrathStatusEffect) {
                user.removeStatusEffect(PermafrozenStatusEffects.BURROWED);
            }
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity playerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            serverPlayerEntity = new ItemStack(Items.GLASS_BOTTLE);
            if (!playerEntity.getInventory().insertStack((ItemStack)serverPlayerEntity)) {
                playerEntity.dropItem((ItemStack)serverPlayerEntity, false);
            }
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
