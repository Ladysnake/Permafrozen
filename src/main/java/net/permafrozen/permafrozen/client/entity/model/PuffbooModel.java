package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.living.PuffbooEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class PuffbooModel extends AnimatedGeoModel<PuffbooEntity> {
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/puffboo.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/puffboo.animation.json");

    @Override
    public Identifier getTextureLocation(PuffbooEntity entity) {
        return new Identifier(Permafrozen.MOD_ID, "textures/entity/puffboo_" + entity.getPuffbooType().toString().toLowerCase() + ".png");
    }

    @Override
    public Identifier getModelLocation(PuffbooEntity entity) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(PuffbooEntity entity) {
        return ANIMATION_IDENTIFIER;
    }

    @Override
    public void setLivingAnimations(PuffbooEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
    }
}
