package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.entity.living.BeakfoxEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BeakfoxEyeRenderLayer extends GeoLayerRenderer<BeakfoxEntity> {
    public BeakfoxEyeRenderLayer(IGeoRenderer<BeakfoxEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, BeakfoxEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Identifier location = new Identifier(Permafrozen.MOD_ID, "textures/entity/beakfox/eyes.png");
        RenderLayer armor = RenderLayer.getEyes(location);
        this.getRenderer().render(this.getEntityModel().getModel(this.getEntityModel().getModelLocation(entitylivingbaseIn)), entitylivingbaseIn, partialTicks, armor, matrixStackIn, bufferIn, bufferIn.getBuffer(armor), packedLightIn, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
