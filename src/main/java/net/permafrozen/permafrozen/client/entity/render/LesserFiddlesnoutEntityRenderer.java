package net.permafrozen.permafrozen.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.client.entity.model.LesserFiddlesnoutEntityModel;
import net.permafrozen.permafrozen.entity.living.LesserFiddlesnoutEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class LesserFiddlesnoutEntityRenderer extends GeoEntityRenderer<LesserFiddlesnoutEntity> {
	public LesserFiddlesnoutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LesserFiddlesnoutEntityModel());
		this.shadowRadius = 0.5F;
		this.shadowOpacity = 1;
	}
	
	@Override
	public Identifier getTexture(LesserFiddlesnoutEntity a) {
		return this.getTextureLocation(a);
	}
	
	@Override
	public RenderLayer getRenderType(LesserFiddlesnoutEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
	}

}
