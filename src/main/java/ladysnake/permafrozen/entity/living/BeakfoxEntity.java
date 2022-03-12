package ladysnake.permafrozen.entity.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
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
    private final AnimationFactory factory = new AnimationFactory(this);
    public BeakfoxEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset, float movementSpeedMultiplier) {
        int directionI = direction ? 1 : -1;
        double t = directionI * circleFrame * 0.5 * speed / radius + offset;
        Vec3d movePos = target.getPos().add(radius * Math.cos(t), 0, radius * Math.sin(t));
        this.getNavigation().startMovingTo(movePos.getX(), movePos.getY(), movePos.getZ(), speed * movementSpeedMultiplier);
    }
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder animationBuilder = new AnimationBuilder();
        if(!this.hasVehicle() && event.isMoving()) {
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
