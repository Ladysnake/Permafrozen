package permafrozen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import permafrozen.registry.BlockRegistry;
import permafrozen.registry.EntityRegistry;
import permafrozen.registry.ItemGroupRegistry;
import permafrozen.registry.ItemRegistry;
import permafrozen.util.DataSerializers;
import software.bernie.geckolib3.GeckoLib;

// this mess should tell you exactly my skill level for modding
@Mod(Permafrozen.MOD_ID)
public class Permafrozen {

    public static final String MOD_ID = "permafrozen";
    public static final String MOD_NAME = "Permafrozen";
    public static final ItemGroupRegistry ITEM_GROUP = new ItemGroupRegistry("permafrozen", () -> ItemRegistry.CHILLORITE_INGOT);


    public Permafrozen() {

        GeckoLib.initialize();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //modEventBus.addListener(this::setup);

        modEventBus.register(BlockRegistry.class);
        modEventBus.register(ItemRegistry.class);
        modEventBus.register(EntityRegistry.class);
        DataSerializers.SERIALIZERS.register(modEventBus); //get consistency later

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(EventPriority.LOWEST, this::setupClient));

    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            //EntityRegistry.setAttributes();
        });
    }


    private void setupClient(final FMLClientSetupEvent event) {
        EntityRegistry.registerRenderers();
    }

}
