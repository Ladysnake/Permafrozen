package ladysnake.permafrozen.client.entity.render;

import ladysnake.permafrozen.block.AuroraAltarBlock;
import ladysnake.permafrozen.block.entity.AuroraAltarBlockEntity;
import ladysnake.permafrozen.block.entity.SpectralCapBlockEntity;
import ladysnake.permafrozen.client.entity.model.AuroraAltarModel;
import ladysnake.permafrozen.client.entity.model.SpectralCapModel;
import ladysnake.permafrozen.registry.PermafrozenItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AuroraAltarRenderer extends GeoBlockRenderer<AuroraAltarBlockEntity> {
    private VertexConsumerProvider rtb;
    private Identifier whTexture;
    private boolean isActive;
    public AuroraAltarRenderer() {
        super(new AuroraAltarModel());
    }

    @Override
    public RenderLayer getRenderType(AuroraAltarBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }


    @Override
    public void renderEarly(AuroraAltarBlockEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        BlockPos pos = animatable.getPos();
        this.rtb = renderTypeBuffer;
        this.whTexture = this.getTextureLocation(animatable);
        this.isActive = animatable.getCachedState().get(AuroraAltarBlock.LIT);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }
    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("soup") && isActive) {
            stack.push();
            stack.translate(0, 1.15D, 0);
            stack.scale(0.7f, 0.7f, 0.7f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(PermafrozenItems.DEFERVESCENCE_ORB.getDefaultStack(), ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, 15728880, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
