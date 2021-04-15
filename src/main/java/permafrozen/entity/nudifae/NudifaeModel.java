package permafrozen.entity.nudifae;

import net.minecraft.util.ResourceLocation;
import permafrozen.Permafrozen;
import permafrozen.entity.Nudifae;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NudifaeModel extends AnimatedGeoModel<Nudifae> {

	@Override
	public ResourceLocation getModelLocation(Nudifae nudifae) {

		return new ResourceLocation(Permafrozen.MOD_ID, "geo/nudifae.geo.json");

	}

	@Override
	public ResourceLocation getTextureLocation(Nudifae nudifae) {

		return new ResourceLocation(Permafrozen.MOD_ID, String.format("textures/entity/nudifae/nudifae_%s.png", nudifae.getNudifaeType().id));

	}

	@Override
	public ResourceLocation getAnimationFileLocation(Nudifae nudifae) {

		return new ResourceLocation(Permafrozen.MOD_ID, "animations/nudifae.animation.json");

	}

}