package ladysnake.permafrozen.client.entity.model;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.entity.living.FatfishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class FatfishEntityModel extends AnimatedGeoModel<FatfishEntity> {
	private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/entity/fatfish.png");
	private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/fatfish.geo.json");
	private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/fatfish.animation.json");
	
	@Override
	public Identifier getTextureLocation(FatfishEntity entity) {
		return TEXTURE_IDENTIFIER;
	}
	
	@Override
	public Identifier getModelLocation(FatfishEntity entity) {
		return MODEL_IDENTIFIER;
	}
	
	@Override
	public Identifier getAnimationFileLocation(FatfishEntity entity) {
		return ANIMATION_IDENTIFIER;
	}
}
