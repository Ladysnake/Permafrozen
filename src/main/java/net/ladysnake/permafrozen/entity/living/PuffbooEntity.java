package net.ladysnake.permafrozen.entity.living;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.ladysnake.permafrozen.registry.PermafrozenItems;
import net.ladysnake.permafrozen.registry.PermafrozenSoundEvents;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;

public class PuffbooEntity extends TameableEntity implements IAnimatable, Flutterer {
    private static final TrackedData<String> TYPE = DataTracker.registerData(PuffbooEntity.class, TrackedDataHandlerRegistry.STRING);
    private final AnimationFactory factory = new AnimationFactory(this);

    public static final AnimationBuilder SIT = new AnimationBuilder().addAnimation("idle");
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
    public static final AnimationBuilder FLY = new AnimationBuilder().addAnimation("fly");

    public PuffbooEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 15, false);
    }
    public static DefaultAttributeContainer.Builder createBooAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return PermafrozenEntities.PUFFBOO.create(world);
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    public Type getPuffbooType() {
        return Type.valueOf(this.dataTracker.get(TYPE));
    }
    public void setPuffbooType(Type type) {
        this.dataTracker.set(TYPE, type.toString());
    }
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(TYPE, Type.NORMAL.toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);

        if (tag.contains("Type")) {
            this.setPuffbooType(Type.valueOf(tag.getString("Type")));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);

        tag.putString("Type", this.getPuffbooType().toString());
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (this.hasCustomName()) {
            if (Objects.requireNonNull(this.getCustomName()).getString().equalsIgnoreCase("ranboo") || this.getCustomName().getString().equalsIgnoreCase("gender man") ) {
                this.setPuffbooType(Type.RANBOO);
            } else if (this.getCustomName().getString().equalsIgnoreCase("coda") || this.getCustomName().getString().equalsIgnoreCase("chod")) {
                this.setPuffbooType(Type.CODA);
            }
        }

    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTamed() && isBreedingItem(player.getStackInHand(hand)) ) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            if (!this.isSilent()) {
                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.world.isClient) {
                if (this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }
            }

            return ActionResult.success(this.world.isClient);
        } else if (!this.isInAir() && this.isTamed() && this.isOwner(player)) {
            if (!this.world.isClient) {
                this.setSitting(!this.isSitting());
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1, 10, 2, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1));
        this.goalSelector.add(2, new FlyOntoTreeGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8));

        this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public SoundEvent getAmbientSound() {
        return PermafrozenSoundEvents.ENTITY_PUFFBOO_AMBIENT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return PermafrozenSoundEvents.ENTITY_PUFFBOO_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return PermafrozenSoundEvents.ENTITY_PUFFBOO_HURT;
    }

    @Override
    public boolean isInAir() {
        return !isOnGround();
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
            boolean isMoving = isInAir() ? !(handSwingProgress > -0.02) || !(handSwingProgress < 0.02) : !(handSwingProgress > -0.10F) || !(handSwingProgress < 0.10F);
            AnimationBuilder anime = isInAir() ? FLY : SIT;
            if (isMoving) {
                anime = isInAir() ? FLY : WALK;
            }
            if (isSitting()) {
                anime = SIT;
            }
            animationEvent.getController().setAnimation(anime);
            return PlayState.CONTINUE;
        }));
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
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(PermafrozenItems.FATFISH);
    }

    public enum Type {
        NORMAL,
        RANBOO,
        CODA
    }

}
