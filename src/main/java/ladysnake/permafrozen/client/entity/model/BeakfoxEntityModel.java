package ladysnake.permafrozen.client.entity.model;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.entity.living.BeakfoxEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BeakfoxEntityModel extends AnimatedTickingGeoModel<BeakfoxEntity> {
    private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/entity/beakfox/beakfox.png");
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/beakfox.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/beakfox.animation.json");
    @Override
    public Identifier getModelLocation(BeakfoxEntity object) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getTextureLocation(BeakfoxEntity object) {
        return TEXTURE_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(BeakfoxEntity animatable) {
        return ANIMATION_IDENTIFIER;
    }

    @Override
    public void codeAnimations(BeakfoxEntity entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
        super.codeAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
        IBone root = this.getAnimationProcessor().getBone("Root");
        if (root != null) {
            root.setScaleX(1.6f);
            root.setScaleY(1.6f);
            root.setScaleZ(1.6f);
//            root.setPositionY(-0.1F);
        }
    }
}
