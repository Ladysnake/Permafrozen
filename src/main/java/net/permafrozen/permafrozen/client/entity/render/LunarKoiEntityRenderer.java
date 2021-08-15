package net.permafrozen.permafrozen.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.permafrozen.permafrozen.client.entity.model.LunarKoiEntityModel;
import net.permafrozen.permafrozen.entity.living.LunarKoiEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class LunarKoiEntityRenderer extends GeoEntityRenderer<LunarKoiEntity> {
	public LunarKoiEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LunarKoiEntityModel());
		this.shadowRadius = 0.5F;
		this.shadowOpacity = 1;
	}
	
	@Override
	public Identifier getTexture(LunarKoiEntity koi) {
		return this.getTextureLocation(koi);
	}
	
	@Override
	public RenderLayer getRenderType(LunarKoiEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
	}

}
