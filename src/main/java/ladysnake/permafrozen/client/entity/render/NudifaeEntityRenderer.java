package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.entity.living.NudifaeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import ladysnake.permafrozen.client.entity.model.NudifaeEntityModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class NudifaeEntityRenderer extends GeoEntityRenderer<NudifaeEntity> {
	public NudifaeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new NudifaeEntityModel());
		this.shadowRadius = 0.3F;
	}
	
	@Override
	public void render(NudifaeEntity nudifaeEntity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider provider, int packedLightIn) {
		super.render(nudifaeEntity, entityYaw, partialTicks, stack, provider, packedLightIn);
	}
	
	@Override
	public RenderLayer getRenderType(NudifaeEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
	}
	
	@Override
	public Identifier getTexture(NudifaeEntity nudifaeEntity) {
		return this.getTextureLocation(nudifaeEntity);
	}
}
