package ladysnake.permafrozen.client.entity.model;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.item.AuroraArmourItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AuroraArmourModel extends AnimatedGeoModel<AuroraArmourItem> {
    private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/item/armor/aurora_armour.png");
    private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/aurora_armour.geo.json");
    private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/aurora_armour.animation.json");
    @Override
    public Identifier getModelLocation(AuroraArmourItem object) {
        return MODEL_IDENTIFIER;
    }

    @Override
    public Identifier getTextureLocation(AuroraArmourItem object) {
        return TEXTURE_IDENTIFIER;
    }

    @Override
    public Identifier getAnimationFileLocation(AuroraArmourItem animatable) {
        return ANIMATION_IDENTIFIER;
    }
}
