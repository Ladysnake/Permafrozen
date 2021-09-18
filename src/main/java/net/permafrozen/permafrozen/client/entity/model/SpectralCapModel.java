package net.permafrozen.permafrozen.client.entity.model;

import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.SpectralCapBlockEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpectralCapModel extends AnimatedGeoModel<SpectralCapBlockEntity> {
    private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/block/spectral_cap.png");
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/spectral_cap.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/spectral_cap.animation.json");

    @Override
    public Identifier getTextureLocation(SpectralCapBlockEntity entity) {
        return TEXTURE_IDENTIFIER;
    }

    @Override
    public Identifier getModelLocation(SpectralCapBlockEntity entity) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(SpectralCapBlockEntity entity) {
        return ANIMATION_IDENTIFIER;
    }
}
