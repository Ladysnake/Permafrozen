package net.ladysnake.permafrozen.entity.living;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.ladysnake.permafrozen.registry.PermafrozenSoundEvents;
import net.ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.UUID;

public class AuroraFaeEntity extends TameableEntity implements Flutterer, IAnimatable {
    public static final TrackedData<Integer> TYPE = DataTracker.registerData(NudifaeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimationFactory factory = new AnimationFactory(this);
    protected static final TrackedData<Byte> AURORA_FAE_FLAGS;

    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    public static final AnimationBuilder SIT = new AnimationBuilder().addAnimation("sit");
    public static final AnimationBuilder FLY = new AnimationBuilder().addAnimation("fly");
    public static final AnimationBuilder FLOAT = new AnimationBuilder().addAnimation("hover");

    public AuroraFaeEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createFaeAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 2.6).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 16);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
        this.dataTracker.startTracking(AURORA_FAE_FLAGS, (byte)0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putInt("AuroraFaeType", dataTracker.get(TYPE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        dataTracker.set(TYPE, compound.getInt("AuroraFaeType"));
    }

    @Override
    public boolean isInAir() {
        return !isOnGround();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        AuroraFaeEntity child = PermafrozenEntities.AURORA_FAE.create(world);
        if (child != null) {
            child.initialize(world, world.getLocalDifficulty(getBlockPos()), SpawnReason.BREEDING, null, null);
            UUID owner = getOwnerUuid();
            if (owner != null) {
                child.setOwnerUuid(owner);
                child.setTamed(true);
            }
            child.dataTracker.set(TYPE, random.nextBoolean() ? dataTracker.get(TYPE) : entity.getDataTracker().get(TYPE));
        }
        return child;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTamed() && isBreedingItem(player.getStackInHand(hand)) && player.hasStatusEffect(PermafrozenStatusEffects.FRAGRANT) ) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            if (!this.isSilent()) {
                this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), PermafrozenSoundEvents.ENTITY_AURORA_FAE_AMBIENT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.world.isClient) {
                if (this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }
            }

            return ActionResult.success(this.world.isClient);
        } else if (this.isOnGround() && this.isTamed() && this.isOwner(player)) {
            if (!this.world.isClient) {
                this.setSitting(!this.isSitting());
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.setSitting(false);
            return super.damage(source, amount);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return PermafrozenSoundEvents.ENTITY_AURORA_FAE_AMBIENT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return PermafrozenSoundEvents.ENTITY_AURORA_FAE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return PermafrozenSoundEvents.ENTITY_AURORA_FAE_HURT;
    }

    private boolean areFlagsSet(int mask) {
        int i = (Byte)this.dataTracker.get(AURORA_FAE_FLAGS);
        return (i & mask) != 0;
    }

    private void setAuroraFaeFlag(int mask, boolean value) {
        int i = (Byte)this.dataTracker.get(AURORA_FAE_FLAGS);
        if (value) {
            i = i | mask;
        } else {
            i = i & ~mask;
        }

        this.dataTracker.set(AURORA_FAE_FLAGS, (byte)(i & 255));
    }

    public boolean isCharging() {
        return this.areFlagsSet(1);
    }

    public void setCharging(boolean charging) {
        this.setAuroraFaeFlag(1, charging);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 2, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 2, 10, 2, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1));
        this.goalSelector.add(2, new FlyGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        this.goalSelector.add(2, new AuroraFaeEntity.ChargeTargetGoal());

        this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        //TODO: decide what should aurora fae be tamed with
        return false;
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        WeightedList<Integer> possibleRarityTypes = Util.make(new WeightedList<>(), list -> list.add(1, 1).add(2, 1));
        if (spawnReason == SpawnReason.BUCKET) {
            return entityData;
        } else {
            dataTracker.set(TYPE, possibleRarityTypes.shuffle().stream().findFirst().orElse(0));

            return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
        }
    }
    public static int getTypes() {
        return 3;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
            boolean isMoving = isInAir() ? !(handSwingProgress > -0.02) || !(handSwingProgress < 0.02) : !(handSwingProgress > -0.10F) || !(handSwingProgress < 0.10F);
            AnimationBuilder anime = isInAir() ? FLOAT : IDLE;
            if (isMoving) {
                anime = isInAir() ? FLY : IDLE;
            }
            if (isSitting()) {
                anime = SIT;
            }
            animationEvent.getController().setAnimation(anime);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (AuroraFaeEntity.this.getTarget() != null && !AuroraFaeEntity.this.getMoveControl().isMoving() && AuroraFaeEntity.this.random.nextInt(7) == 0) {
                return AuroraFaeEntity.this.squaredDistanceTo(AuroraFaeEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        public boolean shouldContinue() {
            return AuroraFaeEntity.this.getMoveControl().isMoving() && AuroraFaeEntity.this.isCharging() && AuroraFaeEntity.this.getTarget() != null && AuroraFaeEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingEntity = AuroraFaeEntity.this.getTarget();
            Vec3d vec3d = livingEntity.getEyePos();
            AuroraFaeEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            AuroraFaeEntity.this.setCharging(true);
        }

        public void stop() {
            AuroraFaeEntity.this.setCharging(false);
        }

        public void tick() {
            LivingEntity livingEntity = AuroraFaeEntity.this.getTarget();
            if (AuroraFaeEntity.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                AuroraFaeEntity.this.tryAttack(livingEntity);
                AuroraFaeEntity.this.setCharging(false);
            } else {
                double d = AuroraFaeEntity.this.squaredDistanceTo(livingEntity);
                if (d < 9.0D) {
                    Vec3d vec3d = livingEntity.getEyePos();
                    AuroraFaeEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                }
            }

        }
    }
    static {
        AURORA_FAE_FLAGS = DataTracker.registerData(AuroraFaeEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

}
