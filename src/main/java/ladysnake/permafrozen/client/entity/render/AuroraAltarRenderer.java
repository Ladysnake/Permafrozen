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
    public AuroraAltarRenderer() {super(new AuroraAltarModel());}

    @Override
    public RenderLayer getRenderType(AuroraAltarBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(AuroraAltarBlockEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        BlockPos pos = animatable.getPos();
        IBone eye = this.getGeoModelProvider().getBone("soup");
        PlayerEntity player = animatable.getWorld().getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15, false);
        if(player != null && animatable.getCachedState().get(AuroraAltarBlock.LIT)) {
            Vector3d target = new Vector3d(player.getX() - pos.getX(), player.getY() - pos.getY(), player.getZ() - pos.getZ());
            eye.setRotationY((float) MathHelper.lerp(eye.getRotationY(), (((float) Math.atan2(target.x, target.z))), 0.5f));
        }
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }
    //    @Override
//    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        if (bone.getName().equals("root")) {
//            stack.push();
//            // You'll need to play around with these to get item to render in the correct orientation
//            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
//            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
//            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
//            // You'll need to play around with this to render the item in the correct spot.
//            stack.translate(0.4D, 0.3D, 0.6D);
//            // Sets the scaling of the item.
//            stack.scale(1.0f, 1.0f, 1.0f);
//            // Change mainHand to predefined Itemstack and Mode to what transform you would want to use.
//            MinecraftClient.getInstance().getItemRenderer().renderItem(PermafrozenItems.DEFERVESCENCE_ORB.getDefaultStack(), ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, 157774, packedOverlayIn, stack, this.);
//            stack.pop();
//        }
//        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
}
