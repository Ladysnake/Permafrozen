package net.ladysnake.permafrozen.client.entity.render;

import net.ladysnake.permafrozen.client.entity.model.BurrowGrubModel;
import net.ladysnake.permafrozen.entity.living.AuroraFaeEntity;
import net.ladysnake.permafrozen.entity.living.BurrowGrubEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BurrowGrubRenderer extends GeoEntityRenderer<BurrowGrubEntity> {
    public BurrowGrubRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BurrowGrubModel());
    }
    @Override
    public RenderLayer getRenderType(BurrowGrubEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    public Identifier getTexture(BurrowGrubEntity faeEntity) {
        return this.getTextureLocation(faeEntity);
    }
}
