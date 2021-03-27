package permafrozen;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import permafrozen.registry.BlockRegistry;
import permafrozen.registry.ItemGroupRegistry;
import permafrozen.registry.ItemRegistry;

@Mod(Permafrozen.MODID)
public class Permafrozen {

    public static final String MODID = "permafrozen";
    public static final String MOD_NAME = "Permafrozen";
    public static final ItemGroupRegistry ITEM_GROUP = new ItemGroupRegistry("permafrozen", () -> ModItems.chillorite_ingot);

    @ObjectHolder(Permafrozen.MODID)
    public static class ModItems {
        public static final Item chillorite_ingot = null;
    }


    public Permafrozen() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        registerCommonEvents(modEventBus);

        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(EventPriority.LOWEST, this::registerClientEvents));
    }

    public void registerCommonEvents(IEventBus eventBus) {
        eventBus.register(BlockRegistry.class);
        eventBus.register(ItemRegistry.class);
    }

    public void registerClientEvents(IEventBus eventBus) {


    }

}
