package permafrozen.entity.fish.lunarkoi;

import net.minecraft.util.ResourceLocation;
import permafrozen.Permafrozen;
import permafrozen.entity.fish.LunarKoi;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LunarKoiModel extends AnimatedGeoModel<LunarKoi> {

	@Override
	public ResourceLocation getModelLocation(LunarKoi koi) {

		return new ResourceLocation(Permafrozen.MOD_ID, "geo/koi.geo.json");

	}

	@Override
	public ResourceLocation getTextureLocation(LunarKoi koi) {

		return new ResourceLocation(Permafrozen.MOD_ID, String.format("textures/entity/lunar_koi.png"));

	}

	@Override
	public ResourceLocation getAnimationFileLocation(LunarKoi koi) {

		return new ResourceLocation(Permafrozen.MOD_ID, "animations/koi.animation.json");

	}

}