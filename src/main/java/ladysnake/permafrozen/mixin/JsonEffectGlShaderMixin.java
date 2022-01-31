package ladysnake.permafrozen.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.permafrozen.client.melonslisestuff.util.GLHelper;
import net.minecraft.client.gl.JsonEffectGlShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.IntSupplier;

@Mixin(JsonEffectGlShader.class)
public class JsonEffectGlShaderMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;bindTexture(I)V"), method = "enable", locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectApplyBeforeBindTexture(CallbackInfo c, int i, String s, IntSupplier texId, int j)
    {
        if(s.endsWith("Volume"))
        {
            GLHelper.bind3d(j);
        }
        else
        {
            RenderSystem.bindTexture(j);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;bindTexture(I)V"), method = "enable")
    private void redirectApplyBindTexture(int texId)
    {

    }
}
