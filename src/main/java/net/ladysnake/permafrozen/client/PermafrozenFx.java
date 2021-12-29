package net.ladysnake.permafrozen.client;

import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import net.ladysnake.permafrozen.PermafrozenClient;
import net.minecraft.client.MinecraftClient;

public class PermafrozenFx implements ShaderEffectRenderCallback, ClientTickEvents.EndTick {
    /**
     * Incremented every tick for animations
     */
    private int ticks = 0;
    @Override
    public void renderShaderEffects(float tickDelta) {
        //System.out.println(PermafrozenClient.AURORA.getProgram().gameTime.getFloatData().get());
        PermafrozenClient.AURORA.getProgram().gameTime.set((ticks + tickDelta) / 20f);
    }
    public void registerCallbacks() {
        ShaderEffectRenderCallback.EVENT.register(this);
        ClientTickEvents.END_CLIENT_TICK.register(this);
    }

    @Override
    public void onEndTick(MinecraftClient client) {
        ++ticks;
    }
}
