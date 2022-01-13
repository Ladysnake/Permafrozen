package ladysnake.permafrozen.client.entity.model;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.entity.living.BurrowGrubEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BurrowGrubModel extends AnimatedGeoModel<BurrowGrubEntity> {
    private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/entity/burrow_grub.png");
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/burrow_grub.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/burrow_grub.animation.json");
    @Override
    public Identifier getModelLocation(BurrowGrubEntity object) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getTextureLocation(BurrowGrubEntity object) {
        return TEXTURE_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(BurrowGrubEntity animatable) {
        return ANIMATION_IDENTIFIER;
    }
}
