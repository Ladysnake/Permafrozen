package ladysnake.permafrozen.util;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.client.melonslisestuff.init.IzzyTextures;
import ladysnake.permafrozen.registry.PermafrozenStatusEffects;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.lwjgl.opengl.GL12;
import org.lwjgl.system.MemoryUtil;

public class ShaderHandler {
    public static final ManagedShaderEffect SPECTRAL_DAZE = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze.json"));
    public static final ManagedShaderEffect SPECTRAL_DAZE_DOS = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze_2.json"));
    public static final ManagedShaderEffect SPECTRAL_DAZE_TRES = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze_wobble.json"));
    public static final ManagedShaderEffect FRIGID_FOG = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/fog.json"));
    private static final Uniform3f SPECTR_PHOSPHOR = SPECTRAL_DAZE.findUniform3f("Phosphor");
    public static int cornerX, cornerZ;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ShaderHandler::onEndTick);
        ShaderEffectRenderCallback.EVENT.register(ShaderHandler::renderShaderEffects);
    }
    public static void updateHeightmapTexture()
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.isPaused())
        {
            return;
        }

        var chunks = mc.worldRenderer.chunkInfos;

        if (chunks.size() <= 0)
        {
            return;
        }

        BlockPos pos = chunks.get(0).chunk.getOrigin();

        int minX = pos.getX();
        int minZ = pos.getZ();
        int maxX = minX;
        int maxZ = minZ;

        for (WorldRenderer.ChunkInfo chunk : chunks)
        {
            pos = chunk.chunk.getOrigin();
            int x = pos.getX();
            int z = pos.getZ();
            if (x > maxX)
            {
                maxX = x;
            }
            else if (x < minX)
            {
                minX = x;
            }
            if (z > maxZ)
            {
                maxZ = z;
            }
            else if (z < minZ)
            {
                minZ = z;
            }
        }

        maxX += 16;
        maxZ += 16;

        int deltaX = maxX - minX;
        int deltaZ = maxZ - minZ;

        long ptr = MemoryUtil.nmemAlloc(deltaX * deltaZ * 2); // 2 bytes per short

        for(int z = 0; z < deltaZ; ++z)
        {
            for(int x = 0; x < deltaX; ++x)
            {
                // ptr offset * 2 because short = 2 bytes
                MemoryUtil.memPutShort(ptr + (x + z * deltaX) * 2, (short) ((short) mc.world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, minX + x, minZ + z) - 1));
            }
        }

        IzzyTextures.INSTANCE.heightmapTexture.set(deltaX, deltaZ, GL12.GL_CLAMP_TO_EDGE);
        IzzyTextures.INSTANCE.heightmapTexture.upload(0, 0, deltaX, deltaZ, ptr);

        MemoryUtil.nmemFree(ptr);

        cornerX = minX;
        cornerZ = minZ;
    }

    private static void renderShaderEffects(float v) {
        if (MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player.hasStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE)) {
                SPECTRAL_DAZE.render(v);
                SPECTRAL_DAZE_DOS.render(v);
                if(player.getStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE).getDuration() <= 2400) {
                    SPECTRAL_DAZE_TRES.render(v);
                }
            } else if (player.hasStatusEffect(PermafrozenStatusEffects.WRAITHWRATH)) {
                SPECTRAL_DAZE.render(v);
            }
        }
    }

    private static void onEndTick(MinecraftClient client) {
        //courtesy of Izzy my bolved
        updateHeightmapTexture();
        if (client.player != null) {
            if (client.player.hasStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE)
                    && client.player.getStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE).getDuration() <= 2400) {
                SPECTR_PHOSPHOR.set(0.7F, 0.7F, 0.95F);
            } else if (client.player.hasStatusEffect(PermafrozenStatusEffects.WRAITHWRATH)) {
                SPECTR_PHOSPHOR.set(0.9F, 0.8F, 0.8F);
            } else {
                SPECTR_PHOSPHOR.set(0.6F, 0.6F, 0.8F);
            }

        }
    }
}
