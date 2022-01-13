package net.ladysnake.permafrozen.entity.living;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.registry.PermafrozenItems;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class FatfishEntity extends SchoolingFishEntity implements IAnimatable {
	public static final AnimationBuilder FLOP = new AnimationBuilder().addAnimation("flop");
	public static final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public FatfishEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
	}
	
	@Override
	public ItemStack getBucketItem() {
		return new ItemStack(PermafrozenItems.FATFISH_BUCKET);
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
	public int getMaxGroupSize() {
		return 8;
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
			AnimationBuilder anim = isInWater ? SWIM : FLOP;
			animationEvent.getController().setAnimation(anim);
			return PlayState.CONTINUE;
		}));
	}
	
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
