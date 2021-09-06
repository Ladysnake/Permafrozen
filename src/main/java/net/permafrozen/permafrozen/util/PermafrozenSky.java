package net.permafrozen.permafrozen.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.permafrozen.permafrozen.PermafrozenClient;

public class PermafrozenSky {
    public static void renderPFSky(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, Runnable runnable, ClientWorld world, MinecraftClient client, VertexBuffer lightSkyBuffer, VertexBuffer darkSkyBuffer, VertexBuffer starsBuffer) {
        RenderSystem.disableTexture();
        Vec3d vec3d = world.method_23777(client.gameRenderer.getCamera().getPos(), tickDelta);
        float g = (float)vec3d.x;
        float h = (float)vec3d.y;
        float i = (float)vec3d.z;
        BackgroundRenderer.setFogBlack();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(g, h, i, 1.0F);
        Shader shader = RenderSystem.getShader();
        lightSkyBuffer.setShader(matrices.peek().getModel(), matrix4f, shader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] fs = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
        float s;
        float t;
        float p;
        float q;
        float r;
        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            s = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            float k = fs[0];
            t = fs[1];
            float m = fs[2];
            Matrix4f matrix4f2 = matrices.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f2, 0.0F, 100.0F, 0.0F).color(k, t, m, fs[3]).next();
            //int n = true;
            //what the fuck was that, mojank

            for(int o = 0; o <= 16; ++o) {
                p = (float)o * 6.2831855F / 16.0F;
                q = MathHelper.sin(p);
                r = MathHelper.cos(p);
                bufferBuilder.vertex(matrix4f2, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
            }

            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            matrices.pop();
        }

        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        matrices.push();
        s = 1.0F - world.getRainGradient(tickDelta);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, s);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(tickDelta) * 360.0F));
        Matrix4f matrix4f3 = matrices.peek().getModel();
        t = 30.0F;
        RenderSystem.setShader(PermafrozenClient.AURORA::getProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix4f3, -t, 100.0F, -t).texture(0.0F, 0.0F).next();
        bufferBuilder.vertex(matrix4f3, t, 100.0F, -t).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(matrix4f3, t, 100.0F, t).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(matrix4f3, -t, 100.0F, t).texture(0.0F, 1.0F).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.disableTexture();
        float ab = world.method_23787(tickDelta) * s;
        if (ab > 0.0F) {
            RenderSystem.setShaderColor(ab, ab, ab, ab);
            BackgroundRenderer.method_23792();
            starsBuffer.setShader(matrices.peek().getModel(), matrix4f, GameRenderer.getPositionShader());
            runnable.run();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        matrices.pop();
        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
        double d = client.player.getCameraPosVec(tickDelta).y - world.getLevelProperties().getSkyDarknessHeight(world);
        if (d < 0.0D) {
            matrices.push();
            matrices.translate(0.0D, 12.0D, 0.0D);
            darkSkyBuffer.setShader(matrices.peek().getModel(), matrix4f, shader);
            matrices.pop();
        }

        if (world.getSkyProperties().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(g * 0.2F + 0.04F, h * 0.2F + 0.04F, i * 0.6F + 0.1F, 1.0F);
        } else {
            RenderSystem.setShaderColor(g, h, i, 1.0F);
        }


        RenderSystem.enableTexture();

        RenderSystem.depthMask(true);

    }
}
