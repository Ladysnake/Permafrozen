package ladysnake.permafrozen.entity.living;

import ladysnake.permafrozen.entity.living.goal.BeakfoxChargeGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BeakfoxEntity extends HostileEntity implements IAnimatable, IAnimationTickable {
    public static final TrackedData<Integer> ATTACK_STATE = DataTracker.registerData(BeakfoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean circleDirection = true;
    protected int circleTicks = 0;
    public int chargeTicks = 0;
    private int attackCountdown = 0;
    protected boolean attacking = false;
    public BeakfoxEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleTicks, float offset, float movementSpeedMultiplier) {
        int dirInt = direction ? 1 : -1;
        double t = dirInt * circleTicks * 0.5 * speed / radius + offset;
        Vec3d movePos = target.getPos().add(radius * Math.cos(t), 0, radius * Math.sin(t));
        //this.lookAtEntity(target, 60, 10);
        this.setHeadYaw(direction ? bodyYaw + 80 : bodyYaw - 80);
        this.getNavigation().startMovingTo(movePos.getX(), movePos.getY(), movePos.getZ(), speed * movementSpeedMultiplier);
    }

    public static DefaultAttributeContainer.Builder createBeakfoxAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8).add(EntityAttributes.GENERIC_ARMOR, 10).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACK_STATE, 0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BeakfoxChargeGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.5f));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, true, LivingEntity::isAlive));
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }
    public int getAttackState() {
        return this.dataTracker.get(ATTACK_STATE);
    }

    public void setAttackState(int state) {
        this.dataTracker.set(ATTACK_STATE, state);
    }


    protected void updateCircling() {
        LivingEntity target = this.getTarget();
        if (target != null && getAttackState() != 1) {
            if (random.nextInt(220) == 0) {
                circleDirection = !circleDirection;
            }
            if (circleDirection) {
                circleTicks++;
            } else {
                circleTicks--;
            }
            if (!attacking && this.distanceTo(target) < 5) {
                circleEntity(target, 7, 0.3f, true, circleTicks, 0, 1.75f);
            } else {
                circleEntity(target, 7, 0.3f, true, circleTicks, 0, 1);
            }
            attacking = false;
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("circleTicks", circleTicks);
        nbt.putInt("attackCountdown", attackCountdown);
        nbt.putInt("chargeTicks", chargeTicks);
        nbt.putInt("AttackState", getAttackState());
        nbt.putBoolean("attacking", attacking);
        nbt.putBoolean("circleDirection", circleDirection);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        circleTicks = nbt.getInt("circleTicks");
        attackCountdown = nbt.getInt("attackCountdown");
        chargeTicks = nbt.getInt("chargeTicks");
        setAttackState(nbt.getInt("AttackState"));
        attacking = nbt.getBoolean("attacking");
        circleDirection = nbt.getBoolean("circleDirection");

    }

    @Override
    protected void mobTick() {
        if (!world.isClient && this.getTarget() != null && !this.getTarget().isAlive()) setTarget(null);

        if(this.getAttackState() == 1 && this.age % 2 == 0) {
            chargeTicks++;
        }
        if(chargeTicks >= 70) {
            setAttackState(0);
            chargeTicks = 0;
        }
        if (attackCountdown < 80) {
            attackCountdown++;
        }
        if (getTarget() != null) {
            if (this.distanceTo(this.getTarget()) > 9) {
                getNavigation().startMovingTo(this.getTarget(), 0.6);
            } else {
                if (!attacking) {
                    updateCircling();
                }
            }
            if (random.nextInt(40) == 0 && attackCountdown >= 80 && this.canSee(this.getTarget())) {
                attacking = true;
            }
            if (attacking && this.canSee(this.getTarget())) {
                attacking = false;
                attackCountdown = 0;
                setAttackState(1);
            }
        } else {
            attacking = false;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder animationBuilder = new AnimationBuilder();
        if(this.getAttackState() == 1) {
            animationBuilder.addAnimation("charge", true);
        } else if(!this.hasVehicle() && event.isMoving()) {
            animationBuilder.addAnimation("walk", true);
        } else if(!this.hasVehicle()) {
            animationBuilder.addAnimation("idle", true);
        }

        if(!animationBuilder.getRawAnimationList().isEmpty()) {
            event.getController().setAnimation(animationBuilder);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int tickTimer() {
        return age;
    }
}
