package net.permafrozen.permafrozen.item;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenItems {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item CRYORITE_INGOT = create("cryorite_ingot", new Item( new Item.Settings().group(ItemGroup.MISC)));
    public static final Item CRYORITE_SWORD = create("cryorite_sword", new SwordItem(PermafrozenMaterials.CRYORITE, 5, -2.4f, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item CRYORITE_SHOVEL = create("cryorite_shovel", new ShovelItem(PermafrozenMaterials.CRYORITE, 2.5f, -3, new Item.Settings().group(ItemGroup.TOOLS)));
    public static final Item NUDIFAE_SPAWN_EGG = create("nudifae_spawn_egg", new SpawnEggItem(PermafrozenEntities.NUDIFAE, 0xb3e5fc, 0x0090ea, (new Item.Settings()).group(ItemGroup.MISC)));
    public static final Item LUNAR_KOI_BUCKET = create("lunar_koi_bucket", new EntityBucketItem(PermafrozenEntities.LUNAR_KOI, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item LUNAR_KOI = create("lunar_koi", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.SALMON)));
    public static final Item FAT_FUCK_BUCKET = create("fatfish_bucket", new EntityBucketItem(PermafrozenEntities.FAT_FUCK, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item FAT_FUCK = create("fatfish", new Item((new Item.Settings()).group(ItemGroup.FOOD).food(FoodComponents.COD)));
    //why the fuck is axe & pickaxe protected mojang
    private static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
        return item;
    }
    public static void innit() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
