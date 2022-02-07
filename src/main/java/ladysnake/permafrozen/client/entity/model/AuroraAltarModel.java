package ladysnake.permafrozen.client.entity.model;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.block.entity.AuroraAltarBlockEntity;
import ladysnake.permafrozen.block.entity.SpectralCapBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AuroraAltarModel extends AnimatedGeoModel<AuroraAltarBlockEntity> {
    private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/block/aurora_altar.png");
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/aurora_altar.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/aurora_altar.animation.json");

    @Override
    public Identifier getTextureLocation(AuroraAltarBlockEntity entity) {
        return TEXTURE_IDENTIFIER;
    }

    @Override
    public Identifier getModelLocation(AuroraAltarBlockEntity entity) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(AuroraAltarBlockEntity entity) {
        return ANIMATION_IDENTIFIER;
    }

}

