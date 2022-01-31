package ladysnake.permafrozen.client.melonslisestuff.renderer.shader;

import com.google.gson.JsonSyntaxException;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.JsonEffectGlShader;
import net.minecraft.client.gl.PostProcessShader;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/renderer/shader/ExtendedPostChain.java
 * **/
public class ExtendedShaderEffect extends ShaderEffect {
    public ExtendedShaderEffect(String domain, String name) throws IOException, JsonSyntaxException
    {
        super(MinecraftClient.getInstance().getTextureManager(), MinecraftClient.getInstance().getResourceManager(), MinecraftClient.getInstance().getFramebuffer(), new Identifier(domain, "shaders/post/" + name + ".json"));
        this.setupDimensions(MinecraftClient.getInstance().getWindow().getWidth(), MinecraftClient.getInstance().getWindow().getHeight());
    }

    public void upload(Consumer<JsonEffectGlShader> action)
    {
        for(PostProcessShader pass : this.passes)
        {
            action.accept(pass.getProgram());
        }
    }

    @Override
    public void render(float tickDelta) {
        super.render(tickDelta);
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }
}
