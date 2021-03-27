package permafrozen.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import permafrozen.item.*;

import java.lang.reflect.Field;

public class ItemRegistry {

    // Declare all items in the mod
    public static final Item FROZEN_SCRAPS = new FrozenScraps();
    public static final Item COBALT_INGOT = new CobaltIngot();
    public static final Item CHILLORITE_INGOT = new ChilloriteIngot();
    public static final Item TUNGSTEN_INGOT = new TungstenIngot();
    public static final Item TUNGSTEN_NUGGET = new TungstenNugget();

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
