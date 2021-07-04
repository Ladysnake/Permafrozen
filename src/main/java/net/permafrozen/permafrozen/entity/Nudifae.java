package net.permafrozen.permafrozen.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.entity.nudifae.NudifaeType;
import net.permafrozen.permafrozen.pain.PermafrozenDataHandlers;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class Nudifae extends TameableEntity implements IAnimatable {
    public static final TrackedData<NudifaeType> TYPE = DataTracker.registerData(Nudifae.class, PermafrozenDataHandlers.NUDIFAE_TYPE);
    private final AnimationFactory factory = new AnimationFactory(this);

    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    public static final AnimationBuilder FLOAT = new AnimationBuilder().addAnimation("float");
    public static AnimationBuilder SLEEP = new AnimationBuilder().addAnimation("sleep");
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
    public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");

    public Nudifae(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
        this.moveControl = new AquaticMoveControl(this, 0, 0, 0.285f, 0.285f, true);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);

        this.experiencePoints = 0;

    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.285f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }


    public NudifaeType getNudifaeType() {
        return this.dataTracker.get(TYPE);
    }

    public void setNudifaeType(NudifaeType type) {
        this.dataTracker.set(TYPE, type);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        int type = NudifaeType.getRandom().id;

        if (entityData instanceof Nudifae.NudifaeData) {
            type = ((Nudifae.NudifaeData) entityData).typeData;
        } else {
            entityData = new Nudifae.NudifaeData(type);
        }

        this.setNudifaeType(NudifaeType.getTypeById(type));

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        Nudifae child = PermafrozenEntities.NUDIFAE.create(world);
        if (child != null) {
            child.initialize(world, world.getLocalDifficulty(getBlockPos()), SpawnReason.BREEDING, null, null);
            UUID owner = getOwnerUuid();
            if (owner != null) {
                child.setOwnerUuid(owner);
                child.setTamed(true);
            }
                int type = NudifaeType.getRandom().id;
                child.setNudifaeType(NudifaeType.getTypeById(type));

        }
        return child;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        boolean isInWater = isInsideWaterOrBubbleColumn();
        boolean isMoving = isInWater ? !(handSwingProgress > -0.02) || !(handSwingProgress < 0.02) : !(handSwingProgress > -0.10F) || !(handSwingProgress < 0.10F);
        AnimationBuilder anime = isInWater ? FLOAT : IDLE;
        if (isMoving) {
            anime = isInWater ? SWIM : WALK;
        }
        event.getController().setAnimation(anime);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 2, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new Nudifae.PlayerTemptGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(10, new AnimalMateGoal(this, 0.8D));
        this.goalSelector.add(11, new Nudifae.WanderGoal(this, 1.0D));
        this.goalSelector.add(12, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(12, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));

    }


    @Override
    protected EntityNavigation createNavigation(World world) {
        super.createNavigation(world);
        return new Nudifae.Navigation(this, world);
    }


    // Data stuff
    static class NudifaeData implements EntityData {
        public final int typeData;

        public NudifaeData(int type) {
            this.typeData = type;
        }
    }

    @Override
    protected void initDataTracker() {

        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, NudifaeType.BLUE);

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putInt("NudifaeType", this.getNudifaeType().id);

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.setNudifaeType(NudifaeType.getTypeById(compound.getInt("NudifaeType")));

    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BlockTags.CORAL_BLOCKS.contains(Block.getBlockFromItem(stack.getItem()));
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    static class Navigation extends SwimNavigation {

        Navigation(Nudifae nudifae, World world) {
            super(nudifae, world);
        }

        protected boolean isAtValidPosition() {
            return true;
        }

        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.nodeMaker = new AmphibiousPathNodeMaker(false);
            return new PathNodeNavigator(this.nodeMaker, range);
        }

        public boolean isValidPosition(BlockPos pos) {
            return !this.world.getBlockState(pos.down()).isAir();
        }

    }

    static class WanderGoal extends WanderAroundFarGoal {
        private final Nudifae nudifae;

        private WanderGoal(Nudifae nudifae, double speedIn) {
            super(nudifae, speedIn);
            this.nudifae = nudifae;
        }

        public boolean shouldContinue() {
            return !this.nudifae.isInsideWaterOrBubbleColumn() && super.shouldContinue();
        }
    }

    static class PlayerTemptGoal extends Goal {
        private final Nudifae nudifae;
        private final double speed;
        private PlayerEntity tempter;
        private final Tag<Block> temptItems;

        PlayerTemptGoal(Nudifae nudifae, double speedIn) {
            this.nudifae = nudifae;
            this.speed = speedIn;
            this.temptItems = BlockTags.CORAL_BLOCKS;
        }

        public boolean shouldExecute() {
            this.tempter = this.nudifae.world.getClosestPlayer(this.nudifae, 30);
            if (this.tempter == null) {
                return false;
            } else {
                return this.isTemptedBy(this.tempter.getMainHandStack()) || this.isTemptedBy(this.tempter.getOffHandStack());
            }

        }

        private boolean isTemptedBy(ItemStack item) {
            return this.temptItems.contains(Block.getBlockFromItem(item.getItem()));
        }

        public boolean shouldContinue() {
            return this.shouldExecute();
        }

        public void resetGoal() {
            this.tempter = null;
            this.nudifae.getNavigation().recalculatePath();
        }

        @Override
        public boolean canStart() {
            return this.shouldExecute();
        }

        public void tick() {
            this.nudifae.getLookControl().lookAt(this.tempter, (float)(this.nudifae.getLookYawSpeed() + 20), (float)this.nudifae.getLookPitchSpeed());
            if (this.nudifae.squaredDistanceTo(this.tempter) < 6.25D) {
                this.nudifae.getNavigation().recalculatePath();
            } else {
                this.nudifae.getNavigation().startMovingTo(this.tempter, this.speed);
            }

        }
    }
}
