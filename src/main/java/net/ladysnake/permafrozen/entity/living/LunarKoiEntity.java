package net.ladysnake.permafrozen.entity.living;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.registry.PermafrozenItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LunarKoiEntity extends FishEntity implements IAnimatable {
	public static final AnimationBuilder FLOP = new AnimationBuilder().addAnimation("flop");
	public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public LunarKoiEntity(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
		this.lookControl = new YawAdjustingLookControl(this, 10);
	}

	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new MoveIntoWaterGoal(this));
		this.goalSelector.add(4, new SwimAroundGoal(this, 1.0D, 10));
		this.goalSelector.add(4, new LookAroundGoal(this));
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2000000476837158D, true));
		this.goalSelector.add(8, new ChaseBoatGoal(this));
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
	}

	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		this.setPitch(0.0F);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	
	@Override
	public ItemStack getBucketItem() {
		return new ItemStack(PermafrozenItems.LUNAR_KOI_BUCKET);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COD_AMBIENT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COD_DEATH;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COD_HURT;
	}
	
	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.ENTITY_COD_FLOP;
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
			boolean isInWater = isInsideWaterOrBubbleColumn();
			AnimationBuilder anime = isInWater ? SWIM : FLOP;
			animationEvent.getController().setAnimation(anime);
			return PlayState.CONTINUE;
		}));
	}

	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9D));
			if (this.getTarget() == null) {
				this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(movementInput);
		}

	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
