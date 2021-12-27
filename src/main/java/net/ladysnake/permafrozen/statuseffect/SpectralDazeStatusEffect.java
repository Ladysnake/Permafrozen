package net.ladysnake.permafrozen.statuseffect;

import net.ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Objects;

public class SpectralDazeStatusEffect extends StatusEffect {
    public SpectralDazeStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(PermafrozenStatusEffects.SPECTRAL_DAZE, 1000, Objects.requireNonNull(entity.getStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE)).getAmplifier() + 1));
        super.onApplied(entity, attributes, amplifier);
    }
}
