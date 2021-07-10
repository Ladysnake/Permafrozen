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
import net.permafrozen.permafrozen.client.entity.model.LunarKoiModel;
import net.permafrozen.permafrozen.entity.phishe.LunarKoi;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class LunarKoiRenderer extends GeoEntityRenderer<LunarKoi> {

    public LunarKoiRenderer(EntityRendererFactory.Context ctx) {

        super(ctx, new LunarKoiModel());
        this.shadowRadius = 0.5F;
        this.shadowOpacity = 1;

    }

    public Identifier getTexture(LunarKoi koi) {

        return this.getTextureLocation(koi);

    }

    @Override
    public RenderLayer getRenderType(LunarKoi animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    protected void applyRotations(LunarKoi koi, MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(koi, matrixStack, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));
        if (!koi.isInsideWaterOrBubbleColumn()) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }
    }


}
