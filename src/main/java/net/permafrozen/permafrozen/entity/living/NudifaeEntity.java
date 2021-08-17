package net.permafrozen.permafrozen.entity.living;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.registry.PermafrozenEntities;
import net.permafrozen.permafrozen.registry.PermafrozenItems;
import net.permafrozen.permafrozen.registry.PermafrozenSoundEvents;
import net.permafrozen.permafrozen.registry.PermafrozenStatusEffects;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class NudifaeEntity extends TameableEntity implements IAnimatable, Bucketable {
	public static final TrackedData<Integer> TYPE = DataTracker.registerData(NudifaeEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(NudifaeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
	public static final AnimationBuilder FLOAT = new AnimationBuilder().addAnimation("float");
	public static final AnimationBuilder SLEEP = new AnimationBuilder().addAnimation("sleep");
	public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
	public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
	
	public NudifaeEntity(EntityType<? extends TameableEntity> type, World world) {
		super(type, world);
		this.moveControl = new AquaticMoveControl(this, 85, 10, 0.1F, 0.5F, false);		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.experiencePoints = 0;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.isTamed() && isBreedingItem(player.getStackInHand(hand))) {
			if (!player.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}

			if (!this.isSilent()) {
				this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), PermafrozenSoundEvents.ENTITY_NUDIFAE_AMBIENT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
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
		} else if (this.isTamed() && this.isOwner(player)) {
			if (!this.world.isClient) {
				this.setSitting(!this.isSitting());
			}

			return ActionResult.success(this.world.isClient);
		} else {
			return super.interactMob(player, hand);
		}
	}

	@Override
	public SoundEvent getAmbientSound() {
		return PermafrozenSoundEvents.ENTITY_NUDIFAE_AMBIENT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return PermafrozenSoundEvents.ENTITY_NUDIFAE_DEATH;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return PermafrozenSoundEvents.ENTITY_NUDIFAE_HURT;
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
		this.goalSelector.add(0, new MoveIntoWaterGoal(this));
		this.goalSelector.add(1, new LookAroundGoal(this));
		this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 0.1F, 10.0F, true));
		this.goalSelector.add(2, new AnimalMateGoal(this, 0.8D));
		this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(0, new SwimAroundGoal(this, 1.0D, 10));
		this.goalSelector.add(8, new ChaseBoatGoal(this));
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
		this.dataTracker.startTracking(FROM_BUCKET, false);
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound compound) {
		super.writeCustomDataToNbt(compound);
		compound.putInt("NudifaeType", dataTracker.get(TYPE));
		compound.putBoolean("FromBucket", this.isFromBucket());
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound compound) {
		super.readCustomDataFromNbt(compound);
		dataTracker.set(TYPE, compound.getInt("NudifaeType"));
		this.setFromBucket(compound.getBoolean("FromBucket"));
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
			if(isSitting()) {
				anime = SLEEP;
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
	
	@Override
	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9D));
			if (this.getTarget() == null) {
				this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
			}
		}
		else {
			super.travel(movementInput);
		}
	}
	
	public static int getTypes() {
		return 6;
	}

	@Override
	public boolean isFromBucket() {
		return this.dataTracker.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean fromBucket) {
		this.dataTracker.set(FROM_BUCKET, fromBucket);
	}

	@Override
	public void copyDataToStack(ItemStack stack) {
		Bucketable.copyDataToStack(this, stack);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putInt("type", this.dataTracker.get(TYPE));
		nbtCompound.putInt("Age", this.getBreedingAge());
	}

	@Override
	public void copyDataFromNbt(NbtCompound nbt) {
		Bucketable.copyDataFromNbt(this, nbt);
		this.dataTracker.set(TYPE, nbt.getInt("type"));
		if (nbt.contains("Age")) {
			this.setBreedingAge(nbt.getInt("Age"));
		}
	}

	@Override
	public ItemStack getBucketItem() {
		return PermafrozenItems.NUDIFAE_BUCKET.getDefaultStack();
	}

	@Override
	public SoundEvent getBucketedSound() {
		return SoundEvents.ITEM_BUCKET_FILL_AXOLOTL;
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
		
		public void reset() {
			this.tempter = null;
			this.nudifaeEntity.getNavigation().recalculatePath();
		}
		
		private boolean isTemptedBy(ItemStack item) {
			return this.temptItems.contains(Block.getBlockFromItem(item.getItem()));
		}
	}
}
