package ladysnake.permafrozen.block.entity;

import ladysnake.permafrozen.registry.PermafrozenEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SpectralCapBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public static final AnimationBuilder BOUNCE = new AnimationBuilder().addAnimation("bounce");
    public SpectralCapBlockEntity(BlockPos pos, BlockState state) {
        super(PermafrozenEntities.SPECTRAL_CAP_TYPE, pos, state);
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
            AnimationBuilder anime = animationEvent.getAnimatable().world.getEntitiesByClass(Entity.class, new Box(animationEvent.getAnimatable().pos).contract(0.1), entity -> true).isEmpty() ? new AnimationBuilder().clearAnimations() : BOUNCE;
            animationEvent.getController().setAnimation(anime);
            return animationEvent.getAnimatable().world.getEntitiesByClass(Entity.class, new Box(animationEvent.getAnimatable().pos).contract(0.1), entity -> true).isEmpty() ? PlayState.STOP : PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
