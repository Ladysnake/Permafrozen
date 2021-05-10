package permafrozen.entity.nudifae;

import net.minecraft.util.ResourceLocation;
import permafrozen.Permafrozen;
import permafrozen.entity.Nudifae;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Nudifae entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
	}

}