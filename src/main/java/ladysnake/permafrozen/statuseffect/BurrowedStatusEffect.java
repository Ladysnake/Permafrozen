package ladysnake.permafrozen.statuseffect;

import ladysnake.permafrozen.entity.living.BurrowGrubEntity;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class BurrowedStatusEffect extends StatusEffect {
    public BurrowedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        for(int i = 0 ; i < 3 + entity.getRandom().nextInt(4); i++) {
            BurrowGrubEntity grub = PermafrozenEntities.BURROW_GRUB.create(entity.world);
            grub.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            entity.world.spawnEntity(grub);
        }
        entity.damage(DamageSource.STARVE, entity.getHealth() + 1);
        super.applyUpdateEffect(entity, amplifier);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 3;
    }
}
