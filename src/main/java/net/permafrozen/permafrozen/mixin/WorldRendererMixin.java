package net.permafrozen.permafrozen.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.util.PermafrozenSky;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private VertexBuffer starsBuffer;
    @Shadow
    private VertexBuffer lightSkyBuffer;
    @Shadow
    private VertexBuffer darkSkyBuffer;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ClientWorld world;

    @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER), method = "renderSky", cancellable = true)
    private void renderPFSky(MatrixStack matrices, Matrix4f skyObjectMatrix, float tickDelta, Runnable runnable, CallbackInfo info) {
        if (this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            PermafrozenSky.renderPFSky(matrices, skyObjectMatrix, tickDelta, runnable, world, client, lightSkyBuffer, darkSkyBuffer, starsBuffer);
            info.cancel();
        }
    }
}
