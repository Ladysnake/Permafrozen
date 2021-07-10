package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.living.LunarKoiEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class LunarKoiEntityModel extends AnimatedGeoModel<LunarKoiEntity> {
	private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/entity/lunar_koi.png");
	private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/koi.geo.json");
	private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/koi.animation.json");
	
	@Override
	public Identifier getTextureLocation(LunarKoiEntity entity) {
		return TEXTURE_IDENTIFIER;
	}
	
	@Override
	public Identifier getModelLocation(LunarKoiEntity entity) {
		return MODEL_IDENTIFIER;
	}
	
	@Override
	public Identifier getAnimationFileLocation(LunarKoiEntity entity) {
		return ANIMATION_IDENTIFIER;
	}
}
