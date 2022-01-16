package ladysnake.permafrozen.entity.living;

import ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BurrowGrubEntity extends HostileEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public BurrowGrubEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.6));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, true, livingEntity -> (!(livingEntity instanceof BurrowGrubEntity) && !livingEntity.hasStatusEffect(PermafrozenStatusEffects.BURROWED))));
    }
    public double getHeightOffset() {
        return 0.1;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.13F;
    }

    public static DefaultAttributeContainer.Builder createBurrowGrubAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        float g = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if(target instanceof LivingEntity) {
            boolean bl = target.damage(DamageSource.mob(this), f);
            if (bl) {
                if (g > 0.0F) {
                    ((LivingEntity)target)
                            .takeKnockback(
                                    (double)(g * 0.5F),
                                    (double) MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)),
                                    (double)(-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)))
                            );
                    this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
                }
                this.applyDamageEffects(this, target);
                this.onAttacking(target);
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(PermafrozenStatusEffects.BURROWED, 2400, 0), this);
                this.remove(RemovalReason.DISCARDED);
            }
            return bl;
        }
        return super.tryAttack(target);
    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 3, animationEvent -> {
            AnimationBuilder anim = animationEvent.isMoving() ? new AnimationBuilder().addAnimation("move") : new AnimationBuilder().addAnimation("idle");
            animationEvent.getController().setAnimation(anim);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
