package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.client.entity.model.BeakfoxEntityModel;
import ladysnake.permafrozen.entity.living.BeakfoxEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BeakfoxEntityRenderer extends GeoEntityRenderer<BeakfoxEntity> {
    public BeakfoxEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BeakfoxEntityModel());
        this.addLayer(new BeakfoxEyeRenderLayer(this));
    }

    @Override
    public RenderLayer getRenderType(BeakfoxEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }
}

