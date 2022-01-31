package ladysnake.permafrozen.client.melonslisestuff.init;

import com.google.gson.JsonSyntaxException;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.client.melonslisestuff.renderer.shader.ExtendedShaderEffect;
import ladysnake.permafrozen.client.melonslisestuff.util.ReloadableResourceRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/init/NWShaders.java
 * **/
@Environment(EnvType.CLIENT)
public class IzzyShaders extends ReloadableResourceRegistry<ShaderEffect> {
    public static final IzzyShaders INSTANCE = new IzzyShaders(2);
    public ExtendedShaderEffect fog;

    private IzzyShaders(int expectedSize) {
        super(expectedSize);
    }

    public void load(ResourceManager resourceManager) throws JsonSyntaxException, IOException {
        this.elements.add(this.fog = new ExtendedShaderEffect(Permafrozen.MOD_ID, "izzy_fog"));
    }

    public void resize(int width, int height) {
        this.elements.forEach(p -> p.setupDimensions(width, height));
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(Permafrozen.MOD_ID, "izzy_shaders");
    }
}
