package net.ladysnake.permafrozen.client.entity.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ladysnake.permafrozen.block.entity.SpectralCapBlockEntity;
import net.ladysnake.permafrozen.client.entity.model.SpectralCapModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SpectralCapRenderer extends GeoBlockRenderer<SpectralCapBlockEntity> {
    public SpectralCapRenderer() {super(new SpectralCapModel());}

    @Override
    public RenderLayer getRenderType(SpectralCapBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
            return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
