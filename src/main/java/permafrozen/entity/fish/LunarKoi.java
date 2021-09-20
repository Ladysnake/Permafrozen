package permafrozen.entity.fish;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.FollowSchoolLeaderGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import permafrozen.registry.ItemRegistry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static net.minecraft.entity.ai.attributes.Attributes.*;

public class LunarKoi extends AbstractFishEntity implements IAnimatable {

    public static AnimationBuilder FLOP = new AnimationBuilder().addAnimation("flop");
    public static AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
    private AnimationFactory factory = new AnimationFactory(this);

    public LunarKoi (EntityType<? extends LunarKoi> p_i50279_1_, World p_i50279_2_) {
        super(p_i50279_1_, p_i50279_2_);
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(ItemRegistry.LUNAR_KOI_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(5, new RunAwayGoal(this));
    }
    @Override
    public EntitySize getSize(Pose poseIn) {
        return new EntitySize(1.0F, 0.4F, true);
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {

        return MobEntity.func_233666_p_()
                .createMutableAttribute(MAX_HEALTH, 8)
                .createMutableAttribute(MOVEMENT_SPEED, 0.285);

    }

    // Animation things
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        //String animname = event.getController().getCurrentAnimation() != null ? event.getController().getCurrentAnimation().animationName : "";
        boolean isInWater = isInWater();
        //boolean isMoving = isInWater ? !(limbSwingAmount > -0.02) || !(limbSwingAmount < 0.02) : !(limbSwingAmount > -0.10F) || !(limbSwingAmount < 0.10F);
        AnimationBuilder anim = isInWater ? SWIM : FLOP;
        event.getController().setAnimation(anim);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

        AnimationController controller = new AnimationController(this, "controller", 2, this::predicate);
        /*controller.registerSoundListener(this::soundListener);
        controller.registerParticleListener(this::particleListener);*/
        animationData.addAnimationController(controller);

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
