package ladysnake.permafrozen.client.melonslisestuff.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/util/GLHelper.java
 * **/
@Environment(EnvType.CLIENT)
public final class GLHelper {
    private GLHelper() {}

    public static void bind3d(int id)
    {
        RenderSystem.assertOnRenderThreadOrInit();
        if (id != GlStateManager.TEXTURES[GlStateManager.activeTexture].boundTexture)
        {
            GlStateManager.TEXTURES[GlStateManager.activeTexture].boundTexture = id;
            GL11.glBindTexture(GL12.GL_TEXTURE_3D, id);
        }
    }
}
