package permafrozen.entity.fish.fatfish;

import net.minecraft.util.ResourceLocation;
import permafrozen.Permafrozen;
import permafrozen.entity.fish.FatFish;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FatFishModel extends AnimatedGeoModel<FatFish> {

	@Override
	public ResourceLocation getModelLocation(FatFish fish) {

		return new ResourceLocation(Permafrozen.MOD_ID, "geo/fatfish.geo.json");

	}

	@Override
	public ResourceLocation getTextureLocation(FatFish fish) {

		return new ResourceLocation(Permafrozen.MOD_ID, String.format("textures/entity/fatfish.png"));

	}

	@Override
	public ResourceLocation getAnimationFileLocation(FatFish fish) {

		return new ResourceLocation(Permafrozen.MOD_ID, "animations/fatfish.animation.json");

	}

}