package ladysnake.permafrozen.mixin;

import ladysnake.permafrozen.registry.PermafrozenSoundEvents;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public ClientWorld world;

    @Environment(EnvType.CLIENT)
    @Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
    private void musimcType(CallbackInfoReturnable<MusicSound> ci) {
        if (this.player != null) {
            if (this.world.getBiome(player.getBlockPos()).getKey().get().equals(PermafrozenBiomes.SHRUMAL_SPIRES)) {
                ci.setReturnValue(new MusicSound(PermafrozenSoundEvents.MUSIC_SHRUMAL_SPIRES, 3000, 8000, true));
            }
        }
    }

}
