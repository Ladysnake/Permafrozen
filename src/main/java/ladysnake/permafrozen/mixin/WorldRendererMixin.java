package ladysnake.permafrozen.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.client.melonslisestuff.init.IzzyShaders;
import ladysnake.permafrozen.client.melonslisestuff.init.IzzyTextures;
import ladysnake.permafrozen.client.melonslisestuff.renderer.shader.ExtendedShaderEffect;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import ladysnake.permafrozen.util.PermafrozenSky;
import ladysnake.permafrozen.util.ShaderHandler;
import ladysnake.permafrozen.util.Wind;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ladysnake.permafrozen.util.ShaderHandler.cornerX;
import static ladysnake.permafrozen.util.ShaderHandler.cornerZ;

@Mixin(value = WorldRenderer.class, priority = 1011)
public class WorldRendererMixin {
    private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
    private static final Matrix4f VIEW_INVERSE = new Matrix4f();

    @Shadow
    private VertexBuffer starsBuffer;
    @Shadow
    private VertexBuffer lightSkyBuffer;
    @Shadow
    private VertexBuffer darkSkyBuffer;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "onResized")
    private void injectResizeTail(int width, int height, CallbackInfo c)
    {
        IzzyShaders.INSTANCE.resize(width, height);
    }

    @Shadow
    private ClientWorld world;
    @Unique
    private Framebuffer depthCopy;
    @Shadow
    private ShaderEffect transparencyShader;


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderEffect;render(F)V", ordinal = 1), method = "render")
    private void renderLevelPreTransparency(MatrixStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager light, Matrix4f projMat, CallbackInfo ci)
    {
        // mojang what the fuck
        MinecraftClient mc = MinecraftClient.getInstance();
        Framebuffer main = mc.getFramebuffer();

        if(this.depthCopy == null)
        {
            this.depthCopy = new SimpleFramebuffer(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, MinecraftClient.IS_SYSTEM_MAC);
        }
        else if(this.depthCopy.textureWidth != main.textureWidth || this.depthCopy.textureHeight != main.textureHeight)
        {
            this.depthCopy.resize(main.textureWidth, main.textureHeight, false);
        }

        this.depthCopy.setClearColor(0f, 0f, 0f, 0f);
        this.depthCopy.copyDepthFrom(main);
    }
    @Inject(method = "render", at = @At("TAIL"))
    private void renderFog(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        MinecraftClient mcClient = MinecraftClient.getInstance();

        //courtesy of Izzy also known as Melonslise
        if(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming()) {
            PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
            PROJECTION_INVERSE.invert();

            VIEW_INVERSE.load(matrices.peek().getPositionMatrix());
            VIEW_INVERSE.invert();

            Vec3d camPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();

            ExtendedShaderEffect postChain = IzzyShaders.INSTANCE.fog;

            if (postChain == null) {
                return;
            }

            postChain.upload(effect ->
            {
                effect.getUniformByNameOrDummy("ProjInverseMat").set(PROJECTION_INVERSE);
                effect.getUniformByNameOrDummy("ViewInverseMat").set(VIEW_INVERSE);
                effect.getUniformByNameOrDummy("CameraPosition").set((float) camPos.x, (float) camPos.y, (float) camPos.z);
                effect.getUniformByNameOrDummy("STime").set(world.getTime() + tickDelta);
                effect.getUniformByNameOrDummy("Opacity").set((float) Math.pow(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).getTransitionTicks() / 120f, 2));
                Vec3f vec = new Vec3f(MathHelper.lerp(tickDelta, Wind.get().getPrevWindX(), Wind.get().getWindX()), 0.1f, MathHelper.lerp(tickDelta, Wind.get().getPrevWindZ(), Wind.get().getWindZ()));
                effect.getUniformByNameOrDummy("WindDirection").set(vec.getX() / 8 + 2, vec.getY(), vec.getZ() / 8 - 2);
                effect.getUniformByNameOrDummy("HeightmapCorner").set(cornerX, cornerZ);
                effect.bindSampler("NoiseVolume", () -> IzzyTextures.INSTANCE.noiseVolume.getId());
                effect.bindSampler("HeightmapTexture", () -> IzzyTextures.INSTANCE.heightmapTexture.getId());
            });
            if(this.transparencyShader != null)
            {
                mcClient.getFramebuffer().copyDepthFrom(depthCopy);
            }

            postChain.render(tickDelta);
        }
        // fog proper
        assert mcClient.player != null;
        if(!(mcClient.player.getWorld() != null && !(PermafrozenComponents.PLAYER.get(mcClient.player).getFenTicks(tickDelta/80f) > 0.0)) || PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming()) {

            ManagedShaderEffect shader = ShaderHandler.FRIGID_FOG;

            if (shader != null) {
                if(!PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming()) {
                    PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
                    PROJECTION_INVERSE.invert();

                    VIEW_INVERSE.load(matrices.peek().getPositionMatrix());
                    VIEW_INVERSE.invert();
                }

                shader.findUniformMat4("ProjInverseMat").set(PROJECTION_INVERSE);
                shader.findUniformMat4("ViewInverseMat").set(VIEW_INVERSE);
                shader.findUniform1f("Darkness").set(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming() ? 0.0f : 1.0f);
                shader.findUniform1f("Opacity").set(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming() ? (float) Math.pow(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).getTransitionTicks() / 120f, 2) * ((PermafrozenComponents.PLAYER.get(mcClient.player).getOutsideTicks(tickDelta) + 20f + mcClient.world.getLightLevel(LightType.SKY, mcClient.player.getBlockPos()) * 4)/ (100f + mcClient.world.getLightLevel(LightType.SKY, mcClient.player.getBlockPos()) * 4)) : (float) Math.pow(PermafrozenComponents.PLAYER.get(mcClient.player).getFenTicks(tickDelta) / 100, 2));
                shader.findUniform1f("Thickness").set(PermafrozenComponents.SNOWSTORM.get(mcClient.player.getWorld()).isSnowstorming() ? (mcClient.player.hasStatusEffect(PermafrozenStatusEffects.GUIDANCE) ? 36.0f : 20.0f) : 25f);

                if(this.transparencyShader != null) {
                    mcClient.getFramebuffer().copyDepthFrom(depthCopy);
                }

                shader.render(tickDelta);
                mcClient.getFramebuffer().beginWrite(false);
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLjava/lang/Runnable;)V", cancellable = true)
    private void renderPFSky(MatrixStack matrices, Matrix4f skyObjectMatrix, float tickDelta, Runnable runnable, CallbackInfo info) {
        if (this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY) && MinecraftClient.getInstance().options.graphicsMode.getId() > 0) {
            PermafrozenSky.renderPFSky(matrices, skyObjectMatrix, tickDelta, runnable, world, client, lightSkyBuffer, darkSkyBuffer, starsBuffer);
            info.cancel();
        }
    }
    @Inject(at = @At(value = "HEAD"), method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", cancellable = true)
    private void renderPFClouds(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double d, double e, double f, CallbackInfo ci) {
        if (this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            ci.cancel();
        }
    }
    @Inject(at = @At(value = "HEAD"), method = "renderClouds(Lnet/minecraft/client/render/BufferBuilder;DDDLnet/minecraft/util/math/Vec3d;)V", cancellable = true)
    private void renderPFCloudsBuilder(BufferBuilder builder, double x, double y, double z, Vec3d color, CallbackInfo ci) {
        if (this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            ci.cancel();
        }
    }
    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void renderPFWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo ci) {
        if (this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            ci.cancel();
        }
    }
}
