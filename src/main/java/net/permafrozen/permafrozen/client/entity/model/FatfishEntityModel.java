package net.permafrozen.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.phishe.FatfishEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class FatfishEntityModel extends AnimatedGeoModel<FatfishEntity> {
	@Override
	public Identifier getModelLocation(FatfishEntity fishe) {
		return new Identifier(Permafrozen.MOD_ID, "geo/fatfish.geo.json");
	}
	
	@Override
	public Identifier getTextureLocation(FatfishEntity fishe) {
		return new Identifier(Permafrozen.MOD_ID, "textures/entity/fatfish.png");
	}
	
	@Override
	public Identifier getAnimationFileLocation(FatfishEntity fishe) {
		return new Identifier(Permafrozen.MOD_ID, "animations/fatfish.animation.json");
	}
}
