package ladysnake.permafrozen.client.melonslisestuff.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import java.nio.ShortBuffer;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/renderer/ShortTexture.java
 * **/
@Environment(EnvType.CLIENT)
public class ShortTexture implements AutoCloseable
{
    private int id = -1, width, height;

    public void set(int width, int height, int repeatMode)
    {
        this.width = width;
        this.height = height;

        this.bind();
        GlStateManager._texParameter(GL12.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GlStateManager._texParameter(GL12.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager._texParameter(GL12.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeatMode);
        GlStateManager._texParameter(GL12.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeatMode);
        GL12.glTexImage2D(GL12.GL_TEXTURE_2D, 0, GL30.GL_R16I, this.width, this.height, 0, GL30.GL_RED_INTEGER, GL11.GL_SHORT, (ShortBuffer) null);
    }

    public int getId()
    {
        if (this.id == -1)
        {
            this.id = TextureUtil.generateTextureId();
        }

        return this.id;
    }

    public void releaseId()
    {
        TextureUtil.releaseTextureId(this.id);
        this.id = -1;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void bind()
    {
        GlStateManager._bindTexture(this.getId());
    }

    public void upload(int x, int y, int width, int height, long ptr)
    {
        this.bind();

        // mag and min filter

        // FIXME not sure what all of this is for
        GlStateManager._pixelStore(GL11.GL_UNPACK_ROW_LENGTH, width == this.getWidth() ? 0 : this.getWidth());

        GlStateManager._pixelStore(GL11.GL_UNPACK_SKIP_ROWS , 0);
        GlStateManager._pixelStore(GL11.GL_UNPACK_SKIP_PIXELS , 0);
        GlStateManager._pixelStore(GL11.GL_UNPACK_ALIGNMENT , 1);

        GL12.glTexSubImage2D(GL12.GL_TEXTURE_2D, 0, x, y, width, height, GL30.GL_RED_INTEGER, GL11.GL_SHORT, ptr);
    }

    @Override
    public void close() throws Exception
    {
        this.releaseId();
    }
}
