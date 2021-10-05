package net.ladysnake.permafrozen.entity.living;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.entity.living.goal.AttackJumpGoal;
import net.ladysnake.permafrozen.entity.living.intface.Lunger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LesserFiddlesnoutEntity extends WaterCreatureEntity implements IAnimatable, Lunger {
    protected static final TrackedData<Byte> FIDDLE_FLAGS = DataTracker.registerData(AuroraFaeEntity.class, TrackedDataHandlerRegistry.BYTE);
    private final AnimationFactory factory = new AnimationFactory(this);

    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    public static final AnimationBuilder LUNGE = new AnimationBuilder().addAnimation("lunge");
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
    public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");

    public LesserFiddlesnoutEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new AquaticLookControl(this, 10);
    }

    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    public static DefaultAttributeContainer.Builder createFiddlesnoutAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2000000476837158D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new BreatheAirGoal(this));
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(2, new AttackJumpGoal(this, 1.2000000476837158D, true));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2000000476837158D, true));
        this.goalSelector.add(8, new ChaseBoatGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, 10, true, false, livingEntity -> true));
        this.targetSelector.add(2, new FollowTargetGoal(this, FishEntity.class, 10, true, false, livingEntity -> true));
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FIDDLE_FLAGS, (byte)0);
    }

    public boolean canBreatheInWater() {
        return false;
    }

    protected void tickWaterBreathingAir(int air) {
    }
    public int getMaxAir() {
        return 12800;
    }

    protected int getNextAirOnLand(int air) {
        return this.getMaxAir();
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.3F;
    }

    public int getLookPitchSpeed() {
        return 1;
    }

    public int getBodyYawSpeed() {
        return 1;
    }

    private boolean areFlagsSet(int mask) {
        int i = (Byte)this.dataTracker.get(FIDDLE_FLAGS);
        return (i & mask) != 0;
    }

    private void setFiddleFlag(int mask, boolean value) {
        int i = (Byte)this.dataTracker.get(FIDDLE_FLAGS);
        if (value) {
            i = i | mask;
        } else {
            i = i & ~mask;
        }

        this.dataTracker.set(FIDDLE_FLAGS, (byte)(i & 255));
    }

    public void tick() {
        super.tick();
        if (this.isAiDisabled()) {
            this.setAir(this.getMaxAir());
        } else {
            if (this.onGround) {
                this.setVelocity(this.getVelocity().add((double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
                this.setYaw(this.random.nextFloat() * 360.0F);
                this.onGround = false;
                this.velocityDirty = true;
            }
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
            boolean isMoving = isInsideWaterOrBubbleColumn() ? !(handSwingProgress > -0.02) || !(handSwingProgress < 0.02) : !(handSwingProgress > -0.10F) || !(handSwingProgress < 0.10F);
            AnimationBuilder anime = isInsideWaterOrBubbleColumn() ? SWIM : IDLE;
            if (isMoving) {
                anime = isInsideWaterOrBubbleColumn() ? SWIM : WALK;

            }
            if (getLunging() || (this.getTarget() != null && this.squaredDistanceTo(this.getTarget()) < 4.0D)) {
                anime = LUNGE;
            }
            animationEvent.getController().setAnimation(anime);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void setLunging(boolean lunging) {
        this.setFiddleFlag(1, lunging);
    }

    @Override
    public boolean getLunging() {
        return this.areFlagsSet(1);
    }
}
