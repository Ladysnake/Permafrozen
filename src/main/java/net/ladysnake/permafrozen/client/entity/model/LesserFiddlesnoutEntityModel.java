package net.ladysnake.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.entity.living.LesserFiddlesnoutEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class LesserFiddlesnoutEntityModel extends AnimatedGeoModel<LesserFiddlesnoutEntity> {
	private static final Identifier TEXTURE_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "textures/entity/fiddlesnout.png");
	private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/fiddlesnout.geo.json");
	private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/fiddlesnout.animation.json");
	
	@Override
	public Identifier getTextureLocation(LesserFiddlesnoutEntity entity) {
		return TEXTURE_IDENTIFIER;
	}
	
	@Override
	public Identifier getModelLocation(LesserFiddlesnoutEntity entity) {
		return MODEL_IDENTIFIER;
	}
	
	@Override
	public Identifier getAnimationFileLocation(LesserFiddlesnoutEntity entity) {
		return ANIMATION_IDENTIFIER;
	}

	@Override
	public void setLivingAnimations(LesserFiddlesnoutEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		if (entity.isInsideWaterOrBubbleColumn() || entity.getLunging()) {
			IBone body = this.getAnimationProcessor().getBone("body");
			if (body != null) {
				body.setRotationX((float) entity.getPitch() * -0.017453292F);
			}
		}
	}
}
