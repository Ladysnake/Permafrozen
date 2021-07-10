package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.phishe.LunarKoiEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class LunarKoiEntityModel extends AnimatedGeoModel<LunarKoiEntity> {
	@Override
	public Identifier getModelLocation(LunarKoiEntity koi) {
		return new Identifier(Permafrozen.MOD_ID, "geo/koi.geo.json");
	}
	
	@Override
	public Identifier getTextureLocation(LunarKoiEntity koi) {
		return new Identifier(Permafrozen.MOD_ID, "textures/entity/lunar_koi.png");
	}
	
	@Override
	public Identifier getAnimationFileLocation(LunarKoiEntity koi) {
		return new Identifier(Permafrozen.MOD_ID, "animations/koi.animation.json");
	}
}
