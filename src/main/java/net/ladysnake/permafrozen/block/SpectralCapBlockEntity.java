package net.ladysnake.permafrozen.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
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
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
