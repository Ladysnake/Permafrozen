package net.ladysnake.permafrozen.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ladysnake.permafrozen.client.entity.model.AuroraFaeModel;
import net.ladysnake.permafrozen.entity.living.AuroraFaeEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class AuroraFaeRenderer extends GeoEntityRenderer<AuroraFaeEntity> {
    public AuroraFaeRenderer(EntityRendererFactory.Context context) {
        super(context, new AuroraFaeModel());
    }

    @Override
    public void render(AuroraFaeEntity faeEntity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider provider, int packedLightIn) {
        super.render(faeEntity, entityYaw, partialTicks, stack, provider, 15728880);
    }

    @Override
    public RenderLayer getRenderType(AuroraFaeEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    public Identifier getTexture(AuroraFaeEntity faeEntity) {
        return this.getTextureLocation(faeEntity);
    }
}
