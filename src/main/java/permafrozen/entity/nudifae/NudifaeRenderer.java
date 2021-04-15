package permafrozen.entity.nudifae;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import permafrozen.Permafrozen;
import permafrozen.entity.Nudifae;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NudifaeRenderer extends GeoEntityRenderer<Nudifae> {

    public NudifaeRenderer(EntityRendererManager manager) {

        super(manager, new NudifaeModel());
        this.shadowSize = 0.3F;
        //this.addLayer(new NudifaeEmissiveLayer<>(this));

    }

    public void render(Nudifae entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    @Override
    public ResourceLocation getEntityTexture(Nudifae nudifae) {

        return new ResourceLocation(Permafrozen.MOD_ID, String.format("textures/entity/nudifae/nudifae_%s.png", nudifae.getNudifaeType().id));

    }

    @Override
    public RenderType getRenderType(Nudifae animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {

        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));

    }

}
