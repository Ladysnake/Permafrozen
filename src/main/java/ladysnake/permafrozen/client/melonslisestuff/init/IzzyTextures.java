package ladysnake.permafrozen.client.melonslisestuff.init;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.client.melonslisestuff.renderer.ByteVolume;
import ladysnake.permafrozen.client.melonslisestuff.renderer.ShortTexture;
import ladysnake.permafrozen.client.melonslisestuff.util.ReloadableResourceRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.io.IOException;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/init/NWTextures.java
 * **/
@Environment(EnvType.CLIENT)
public class IzzyTextures extends ReloadableResourceRegistry<AutoCloseable>
{
    public static final IzzyTextures INSTANCE = new IzzyTextures(2);

    public ByteVolume noiseVolume;
    public ShortTexture heightmapTexture;

    private IzzyTextures(int expectedSize)
    {
        super(expectedSize);
    }
    @Override
    public Identifier getFabricId() {
        return new Identifier(Permafrozen.MOD_ID, "izzy_textures");
    }

    @Override
    protected void load(ResourceManager resourceManager) throws IOException
    {
        this.elements.add(noiseVolume = ByteVolume.read(resourceManager, new Identifier(Permafrozen.MOD_ID, "textures/effect/noise.vol"), GL11.GL_LINEAR, GL14.GL_MIRRORED_REPEAT));
        this.elements.add(heightmapTexture = new ShortTexture());
    }
}
