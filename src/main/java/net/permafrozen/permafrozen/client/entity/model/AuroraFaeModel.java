package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.living.AuroraFaeEntity;
import net.permafrozen.permafrozen.entity.living.NudifaeEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class AuroraFaeModel extends AnimatedGeoModel<AuroraFaeEntity> {
    private static Identifier[] TEXTURE_IDENTIFIERS;
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/aurora_fae.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/aurora_fae.animation.json");

    @Override
    public Identifier getTextureLocation(AuroraFaeEntity entity) {
        if (TEXTURE_IDENTIFIERS == null) {
            TEXTURE_IDENTIFIERS = new Identifier[AuroraFaeEntity.getTypes()];
            for (int i = 0; i < AuroraFaeEntity.getTypes(); i++) {
                TEXTURE_IDENTIFIERS[i] = new Identifier(Permafrozen.MOD_ID, "textures/entity/aurora_fae_" + i + ".png");
            }
        }
        return TEXTURE_IDENTIFIERS[entity.getDataTracker().get(AuroraFaeEntity.TYPE)];
    }

    @Override
    public Identifier getModelLocation(AuroraFaeEntity entity) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(AuroraFaeEntity entity) {
        return ANIMATION_IDENTIFIER;
    }

    @Override
    public void setLivingAnimations(AuroraFaeEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
    }
}
