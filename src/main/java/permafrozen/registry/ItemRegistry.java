package permafrozen.registry;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import permafrozen.item.*;
import permafrozen.item.wulfram.*;
import permafrozen.item.cobalt.*;
import permafrozen.item.chillorite.*;
import permafrozen.util.PermafrozenArmorMaterial;

import java.lang.reflect.Field;

public class ItemRegistry {

    // Declare all items in the mod
    public static final Item NUDIFAE_SPAWN_EGG = new NudifaeSpawnEgg().setRegistryName("nudifae_spawn_egg");

    public static final Item WULFRAM_INGOT = new WulframIngot().setRegistryName("wulfram_ingot");
    public static final Item WULFRAM_NUGGET = new WulframNugget().setRegistryName("wulfram_nugget");
    public static final Item WULFRAM_SWORD = new WulframSword().setRegistryName("wulfram_sword");
    public static final Item WULFRAM_PICKAXE = new WulframPickaxe().setRegistryName("wulfram_pickaxe");
    public static final Item WULFRAM_AXE = new WulframAxe().setRegistryName("wulfram_axe");
    public static final Item WULFRAM_SHOVEL = new WulframShovel().setRegistryName("wulfram_shovel");
    public static final Item WULFRAM_HOE = new WulframShovel().setRegistryName("wulfram_hoe");
    public static final Item WULFRAM_HELMET = new WulframArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.HEAD).setArmorTexture("wulfram_layer_1").setRegistryName("wulfram_helmet");
    public static final Item WULFRAM_CHESTPLATE = new WulframArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.CHEST).setArmorTexture("wulfram_layer_1").setRegistryName("wulfram_chestplate");
    public static final Item WULFRAM_LEGS = new WulframArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.LEGS).setArmorTexture("wulfram_layer_2").setRegistryName("wulfram_leggings");
    public static final Item WULFRAM_BOOTS = new WulframArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.FEET).setArmorTexture("wulfram_layer_1").setRegistryName("wulfram_boots");

    public static final Item FROZEN_SCRAPS = new FrozenScraps().setRegistryName("frozen_scraps");
    public static final Item CHILLORITE_INGOT = new ChilloriteIngot().setRegistryName("chillorite_ingot");
    public static final Item CHILLORITE_SWORD = new ChilloriteSword().setRegistryName("chillorite_sword");
    public static final Item CHILLORITE_PICKAXE = new ChilloritePickaxe().setRegistryName("chillorite_pickaxe");
    public static final Item CHILLORITE_AXE = new ChilloriteAxe().setRegistryName("chillorite_axe");
    public static final Item CHILLORITE_SHOVEL = new ChilloriteShovel().setRegistryName("chillorite_shovel");
    public static final Item CHILLORITE_HOE = new ChilloriteShovel().setRegistryName("chillorite_hoe");

    public static final Item COBALT_NUGGET = new CobaltNugget().setRegistryName("cobalt_nugget");
    public static final Item COBALT_INGOT = new CobaltIngot().setRegistryName("cobalt_ingot");

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
