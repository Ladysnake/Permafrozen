package permafrozen.entity.fish.fatfish;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import permafrozen.entity.fish.FatFish;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)

public class FatFishRenderer extends GeoEntityRenderer<FatFish> {

    public FatFishRenderer(EntityRendererManager manager) {

        super(manager, new FatFishModel());
        this.shadowSize = 0.2F;

    }

    @Override
    public ResourceLocation getEntityTexture(FatFish entity) {

        return this.getTextureLocation(entity);

    }

    @Override
    public RenderType getRenderType(FatFish animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {

        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));

    }

    @Override
    protected void applyRotations(FatFish fish, MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(fish, matrixStack, ageInTicks, rotationYaw, partialTicks);
        //float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        //matrixStack.rotate(Vector3f.YP.rotationDegrees(f));
        if (!fish.isInWater()) {
            //matrixStack.translate(0.1F, 0.1F, -0.1F);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }


}
