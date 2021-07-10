package net.permafrozen.permafrozen.entity.living;

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
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.registry.PermafrozenEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class NudifaeEntity extends TameableEntity implements IAnimatable {
	public static final TrackedData<Integer> TYPE = DataTracker.registerData(NudifaeEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
	public static final AnimationBuilder FLOAT = new AnimationBuilder().addAnimation("float");
	public static final AnimationBuilder SLEEP = new AnimationBuilder().addAnimation("sleep");
	public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
	public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
	
	public NudifaeEntity(EntityType<? extends TameableEntity> type, World world) {
		super(type, world);
		this.moveControl = new AquaticMoveControl(this, 0, 0, 0.285f, 0.285f, true);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.experiencePoints = 0;
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.285f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
	}
	
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
		WeightedList<Integer> possibleRarityTypes = Util.make(new WeightedList<>(), list -> list.add(0, 35).add(1, 30).add(2, 25).add(3, 10).add(4, 1).add(5, 1));
		dataTracker.set(TYPE, possibleRarityTypes.shuffle().stream().findFirst().orElse(0));
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}
	
	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		NudifaeEntity child = PermafrozenEntities.NUDIFAE.create(world);
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
	
	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new NudifaeEntity.PlayerTemptGoal(this, 1.0D));
		this.goalSelector.add(1, new LookAroundGoal(this));
		this.goalSelector.add(2, new AnimalMateGoal(this, 0.8D));
		this.goalSelector.add(3, new NudifaeEntity.WanderGoal(this, 1.0D));
		this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.add(0, new SwimAroundGoal(this, 1.0D, 40));
	}
	
	@Override
	protected EntityNavigation createNavigation(World world) {
		super.createNavigation(world);
		return new NudifaeEntity.Navigation(this, world);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TYPE, 0);
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound compound) {
		super.writeCustomDataToNbt(compound);
		compound.putInt("NudifaeType", dataTracker.get(TYPE));
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound compound) {
		super.readCustomDataFromNbt(compound);
		dataTracker.set(TYPE, compound.getInt("NudifaeType"));
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
			boolean isInWater = isInsideWaterOrBubbleColumn();
			boolean isMoving = isInWater ? !(handSwingProgress > -0.02) || !(handSwingProgress < 0.02) : !(handSwingProgress > -0.10F) || !(handSwingProgress < 0.10F);
			AnimationBuilder anime = isInWater ? FLOAT : IDLE;
			if (isMoving) {
				anime = isInWater ? SWIM : WALK;
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
	public boolean isBreedingItem(ItemStack stack) {
		return BlockTags.CORAL_BLOCKS.contains(Block.getBlockFromItem(stack.getItem()));
	}
	
	@Override
	public boolean canBreatheInWater() {
		return true;
	}
	
	@Override
	public boolean isPushedByFluids() {
		return false;
	}
	
	public static int getTypes() {
		return 6;
	}
	
	static class Navigation extends SwimNavigation {
		Navigation(NudifaeEntity nudifaeEntity, World world) {
			super(nudifaeEntity, world);
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
		private final NudifaeEntity nudifaeEntity;
		
		private WanderGoal(NudifaeEntity nudifaeEntity, double speedIn) {
			super(nudifaeEntity, speedIn);
			this.nudifaeEntity = nudifaeEntity;
		}
		
		public boolean shouldContinue() {
			return !this.nudifaeEntity.isInsideWaterOrBubbleColumn() && super.shouldContinue();
		}
	}
	
	static class PlayerTemptGoal extends Goal {
		private final NudifaeEntity nudifaeEntity;
		private final double speed;
		private PlayerEntity tempter;
		private final Tag<Block> temptItems;
		
		PlayerTemptGoal(NudifaeEntity nudifaeEntity, double speedIn) {
			this.nudifaeEntity = nudifaeEntity;
			this.speed = speedIn;
			this.temptItems = BlockTags.CORAL_BLOCKS;
		}
		
		@Override
		public boolean shouldContinue() {
			return this.canStart();
		}
		
		@Override
		public boolean canStart() {
			this.tempter = this.nudifaeEntity.world.getClosestPlayer(this.nudifaeEntity, 30);
			if (this.tempter == null) {
				return false;
			}
			else {
				return this.isTemptedBy(this.tempter.getMainHandStack()) || this.isTemptedBy(this.tempter.getOffHandStack());
			}
		}
		
		@Override
		public void tick() {
			this.nudifaeEntity.getLookControl().lookAt(this.tempter, (float) (this.nudifaeEntity.getLookYawSpeed() + 20), (float) this.nudifaeEntity.getLookPitchSpeed());
			if (this.nudifaeEntity.squaredDistanceTo(this.tempter) < 6.25D) {
				this.nudifaeEntity.getNavigation().recalculatePath();
			}
			else {
				this.nudifaeEntity.getNavigation().startMovingTo(this.tempter, this.speed);
			}
		}
		
		public void resetGoal() {
			this.tempter = null;
			this.nudifaeEntity.getNavigation().recalculatePath();
		}
		
		private boolean isTemptedBy(ItemStack item) {
			return this.temptItems.contains(Block.getBlockFromItem(item.getItem()));
		}
	}
}
