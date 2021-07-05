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
import net.permafrozen.permafrozen.client.entity.model.FatfishModel;
import net.permafrozen.permafrozen.entity.phishe.Fatfish;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class FatfishRenderer extends GeoEntityRenderer<Fatfish> {


    public FatfishRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FatfishModel());
    }

    public Identifier getEntityTexture(Fatfish entity) {

        return this.getTextureLocation(entity);

    }

    @Override
    public RenderLayer getRenderType(Fatfish animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {

        return RenderLayer.getEntityTranslucent(this.getEntityTexture(animatable));

    }

    @Override
    protected void applyRotations(Fatfish fish, MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(fish, matrixStack, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
        if (!fish.isInsideWaterOrBubbleColumn()) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }
    }


}
