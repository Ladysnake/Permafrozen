package ladysnake.permafrozen.client.melonslisestuff.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import ladysnake.permafrozen.client.melonslisestuff.util.GLHelper;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/renderer/ByteVolume.java
 * **/
public class ByteVolume implements AutoCloseable
{
    private Format pixelFormat;
    private int id = -1, width, height, depth;

    // private ByteBuffer pixelBuffer;

    // https://stackoverflow.com/questions/3114935/convert-from-short-to-byte-and-viceversa-in-java
    private static int fromBytes(byte[] b)
    {
        return (b[1] << 8) + (b[0] & 0xFF);
    }

    public static ByteVolume read(ResourceManager resourceManager, Identifier path, int resizeMode, int repeatMode) throws IOException
    {
        Resource res = resourceManager.getResource(path);
        InputStream stream = res.getInputStream();

        int width = fromBytes(stream.readNBytes(2));
        int height = fromBytes(stream.readNBytes(2));
        int depth = fromBytes(stream.readNBytes(2));
        int byteDepth = stream.read(); // bytes per channel
        int channels = stream.read(); // amount of channels

        int byteVolume = width * height * depth * channels;

        ByteVolume volume = new ByteVolume();
        volume.set(ByteVolume.Format.RED, width, height, depth, resizeMode, repeatMode); // FIXME automatically detect format from file

        ByteBuffer buf = BufferUtils.createByteBuffer(byteVolume);
        byte[] data = new byte[byteVolume];
        stream.read(data);
        buf.put(data);
        buf.rewind();

        volume.upload(0, 0, 0, width, height, depth, buf);

        return volume;
    }

	/*
	public static ByteVolume readAndCleanup(ResourceManager resourceManager, ResourceLocation path, int resizeMode, int repeatMode) throws IOException
	{
		ByteVolume volume = read(resourceManager, path, resizeMode, repeatMode);
		volume.clearPixelBuffer();
		return volume;
	}
	 */

    public void set(Format format, int width, int height, int depth, int resizeMode, int repeatMode)
    {
        this.pixelFormat = format;
        this.width = width;
        this.height = height;
        this.depth = depth;
        // this.pixelBuffer = BufferUtils.createByteBuffer(width * height * depth * format.channels);

        this.bind();
        GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MIN_FILTER, resizeMode);
        GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MAG_FILTER, resizeMode);
        GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_S, repeatMode);
        GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_T, repeatMode);
        GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL12.GL_TEXTURE_WRAP_R, repeatMode);
        GL12.glTexImage3D(GL12.GL_TEXTURE_3D, 0, this.pixelFormat.glInternalFormat, this.width, this.height, this.depth, 0, this.pixelFormat.glFormat, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
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

    public Format getPixelFormat()
    {
        return this.pixelFormat;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getDepth()
    {
        return this.depth;
    }

	/*
	public void clearPixelBuffer()
	{
		this.pixelBuffer = null;
	}
	public void setPixel(int x, int y, int z, byte value)
	{
		if(this.pixelBuffer == null)
		{
			return;
		}
		this.pixelBuffer.position(x + y * this.width + z * this.width * this.height);
		this.pixelBuffer.put(value);
	}
	 */

    public void bind()
    {
        GLHelper.bind3d(this.getId());
    }

	/*
	public void upload()
	{
		this.bind();
		GL12.glTexSubImage3D(GL12.GL_TEXTURE_3D, 0, 0, 0, 0, this.width, this.height, this.depth, this.pixelFormat.glFormat, GL11.GL_UNSIGNED_BYTE, this.pixelBuffer);
	}
	*/

    public void upload(int x, int y, int z, int width, int height, int depth, ByteBuffer pixelBuffer)
    {
        this.bind();
        GL12.glTexSubImage3D(GL12.GL_TEXTURE_3D, 0, x, y, z, width, height, depth, this.pixelFormat.glFormat, GL11.GL_UNSIGNED_BYTE, pixelBuffer);
    }

    public void upload(int x, int y, int z, int width, int height, int depth, long ptr)
    {
        this.bind();
        GL12.glTexSubImage3D(GL12.GL_TEXTURE_3D, 0, x, y, z, width, height, depth, this.pixelFormat.glFormat, GL11.GL_UNSIGNED_BYTE, ptr);
    }

    @Override
    public void close()
    {
        this.releaseId();
        // this.clearPixelBuffer();
    }

    public static enum Format
    {
        RED(1, GL30.GL_R8, GL11.GL_RED);

        public final int channels, glInternalFormat, glFormat;

        private Format(int channels, int internalFormat, int format)
        {
            this.channels = channels;
            this.glInternalFormat = internalFormat;
            this.glFormat = format;
        }
    }
}
