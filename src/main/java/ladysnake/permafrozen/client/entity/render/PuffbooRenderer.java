package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.entity.living.PuffbooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import ladysnake.permafrozen.client.entity.model.PuffbooModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class PuffbooRenderer extends GeoEntityRenderer<PuffbooEntity> {
    public PuffbooRenderer(EntityRendererFactory.Context context) {
        super(context, new PuffbooModel());
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(PuffbooEntity faeEntity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider provider, int packedLightIn) {
        super.render(faeEntity, entityYaw, partialTicks, stack, provider, packedLightIn);
    }

    @Override
    public RenderLayer getRenderType(PuffbooEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    public Identifier getTexture(PuffbooEntity faeEntity) {
        return this.getTextureLocation(faeEntity);
    }
}
