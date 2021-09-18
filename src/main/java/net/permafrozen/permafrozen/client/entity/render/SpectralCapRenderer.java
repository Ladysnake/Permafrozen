package net.permafrozen.permafrozen.client.entity.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.block.SpectralCapBlockEntity;
import net.permafrozen.permafrozen.client.entity.model.SpectralCapModel;
import software.bernie.example.block.tile.FertilizerTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SpectralCapRenderer extends GeoBlockRenderer<SpectralCapBlockEntity> {
    public SpectralCapRenderer() {super(new SpectralCapModel());}

    @Override
    public RenderLayer getRenderType(SpectralCapBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
            return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
