package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.phishe.Fatfish;
import net.permafrozen.permafrozen.entity.phishe.LunarKoi;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class FatfishModel extends AnimatedGeoModel<Fatfish> {


    @Override
    public Identifier getModelLocation(Fatfish fishe) {

        return new Identifier(Permafrozen.MOD_ID, "geo/fatfish.geo.json");

    }
    @Override
    public Identifier getTextureLocation(Fatfish fishe) {

        return new Identifier(Permafrozen.MOD_ID, "textures/entity/fatfish.png");

    }

    @Override
    public Identifier getAnimationFileLocation(Fatfish fishe) {

        return new Identifier(Permafrozen.MOD_ID, "animations/fatfish.animation.json");

    }

}
