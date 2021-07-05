package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.nudifae.Nudifae;
import net.permafrozen.permafrozen.entity.phishe.LunarKoi;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class LunarKoiModel extends AnimatedGeoModel<LunarKoi>  {
    @Override
    public Identifier getModelLocation(LunarKoi koi) {

        return new Identifier(Permafrozen.MOD_ID, "geo/koi.geo.json");

    }

    @Override
    public Identifier getTextureLocation(LunarKoi koi) {

        return new Identifier(Permafrozen.MOD_ID, "textures/entity/lunar_koi.png");

    }

    @Override
    public Identifier getAnimationFileLocation(LunarKoi koi) {

        return new Identifier(Permafrozen.MOD_ID, "animations/koi.animation.json");

    }
}
