package permafrozen.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import permafrozen.item.*;
import permafrozen.item.wulfrum.*;
import permafrozen.item.cobalt.*;
import permafrozen.item.chillorite.*;

import java.lang.reflect.Field;

public class ItemRegistry {

    // Declare all items in the mod
    public static final Item WULFRUM_INGOT = new WulfrumIngot().setRegistryName("wulfrum_ingot");
    public static final Item WULFRUM_NUGGET = new WulfrumNugget().setRegistryName("wulfrum_nugget");

    public static final Item FROZEN_SCRAPS = new FrozenScraps().setRegistryName("frozen_scraps");
    public static final Item CHILLORITE_INGOT = new ChilloriteIngot().setRegistryName("chillorite_ingot");
    public static final Item CHILLORITE_SWORD = new ChilloriteSword().setRegistryName("chillorite_sword");
    public static final Item CHILLORITE_PICKAXE = new ChilloritePickaxe().setRegistryName("chillorite_pickaxe");
    public static final Item CHILLORITE_AXE = new ChilloriteAxe().setRegistryName("chillorite_axe");
    public static final Item CHILLORITE_SHOVEL = new ChilloriteShovel().setRegistryName("chillorite_shovel");
    public static final Item CHILLORITE_HOE = new ChilloriteShovel().setRegistryName("chillorite_hoe");

    public static final Item COBALT_NUGGET = new CobaltNugget().setRegistryName("cobalt_nugget");
    public static final Item COBALT_INGOT = new CobaltIngot().setRegistryName("cobalt_ingot");
    public static final Item COBALT_SWORD = new CobaltSword().setRegistryName("cobalt_sword");
    public static final Item COBALT_PICKAXE = new CobaltPickaxe().setRegistryName("cobalt_pickaxe");
    public static final Item COBALT_AXE = new CobaltAxe().setRegistryName("cobalt_axe");
    public static final Item COBALT_SHOVEL = new CobaltShovel().setRegistryName("cobalt_shovel");
    public static final Item COBALT_HOE = new CobaltShovel().setRegistryName("cobalt_hoe");

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> e) {

        try {

            // Go through declared vars, check if they're items, then register
            for (Field f : ItemRegistry.class.getDeclaredFields()) {

                Object obj = f.get(null);
                if (obj instanceof Item) {

                    e.getRegistry().register((Item) obj);

                }

            }

        } catch (IllegalAccessException err) {

            throw new RuntimeException(err);

        }

    }

}
