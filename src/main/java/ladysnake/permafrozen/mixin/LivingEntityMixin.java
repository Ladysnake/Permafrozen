package ladysnake.permafrozen.mixin;

import ladysnake.permafrozen.registry.PermafrozenEntities;
import ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import ladysnake.permafrozen.entity.living.BurrowGrubEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract Random getRandom();

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    public void totem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(PermafrozenStatusEffects.BURROWED) && !source.isFire() && !source.isOutOfWorld() && !source.isExplosive()) {
            this.setHealth(1.0F);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 2));
            cir.setReturnValue(true);
        } else {
            if (this.hasStatusEffect(PermafrozenStatusEffects.BURROWED)) {
                BurrowGrubEntity grub = PermafrozenEntities.BURROW_GRUB.create(this.world);
                grub.updatePosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(grub);
            }
        }
    }
}
