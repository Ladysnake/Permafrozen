package ladysnake.permafrozen.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.component.SnowstormWorldComponent;
import net.minecraft.util.Identifier;

public class PermafrozenComponents implements WorldComponentInitializer {
    public static final ComponentKey<SnowstormWorldComponent> SNOWSTORM = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Permafrozen.MOD_ID, "snowstorm"), SnowstormWorldComponent.class);
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(SNOWSTORM, SnowstormWorldComponent::new);
    }
}
