package net.permafrozen.permafrozen.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

public class FragrantStatusEffect extends StatusEffect {
    protected FragrantStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        Vec3d pos = entity.getPos();
        List<LivingEntity> entities = Objects.requireNonNull(entity.getEntityWorld()).getEntitiesByClass(
                LivingEntity.class,
                new Box(
                        pos.getX() - 16, pos.getY() - 16, pos.getZ() - 16,
                        pos.getX() + 16, pos.getY() + 16, pos.getZ() + 16
                ), (LivingEntity) -> true
        );
        for (LivingEntity nearbyEntity : entities) {
            if (nearbyEntity instanceof AnimalEntity && entity instanceof PlayerEntity) {
                if(nearbyEntity.distanceTo(entity) > 6.0f) {
                    //this is over-the-top but I want to make sure it works
                    Path path = ((AnimalEntity) nearbyEntity).getNavigation().findPathTo(entity, 10);
                    ((AnimalEntity) nearbyEntity).getNavigation().startMovingAlong(path, 1);
                    ((AnimalEntity) nearbyEntity).getNavigation().startMovingTo(entity, 1);
                    ((AnimalEntity) nearbyEntity).setPositionTarget(entity.getBlockPos(), 10);
                }

            }
        }
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
