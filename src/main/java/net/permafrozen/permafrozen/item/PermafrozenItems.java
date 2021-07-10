package net.permafrozen.permafrozen.item;

import com.terraformersmc.terraform.boat.TerraformBoatItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final Item CRYORITE_INGOT = create("cryorite_ingot", new Item(new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_SWORD = create("cryorite_sword", new SwordItem(PermafrozenMaterials.CRYORITE, 5, -2.4f, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_SHOVEL = create("cryorite_shovel", new ShovelItem(PermafrozenMaterials.CRYORITE, 2.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_AXE = create("cryorite_axe", new PermafrozenAxeItem(PermafrozenMaterials.CRYORITE, 6.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_PICKAXE = create("cryorite_pickaxe", new PermafrozenPickaxeItem(PermafrozenMaterials.CRYORITE, 3, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_HOE = create("cryorite_hoe", new PermafrozenHoeItem(PermafrozenMaterials.CRYORITE, -2, 0, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI_BUCKET = create("lunar_koi_bucket", new EntityBucketItem(PermafrozenEntities.LUNAR_KOI, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI = create("lunar_koi", new Item((new Item.Settings()).group(Permafrozen.GROUP).food(FoodComponents.SALMON)));
	public static final Item FAT_FUCK_BUCKET = create("fatfish_bucket", new EntityBucketItem(PermafrozenEntities.FATFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FATFISH = create("fatfish", new Item((new Item.Settings()).group(Permafrozen.GROUP).food(FoodComponents.COD)));
	public static final Item FIR_PINECONE = create("fir_cone", new Item(new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FIR_DOOR_ITEM = create("fir_door", new TallBlockItem(PermafrozenBlocks.FIR_DOOR, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FIR_SIGN_ITEM = create("fir_sign", new SignItem(new Item.Settings().group(Permafrozen.GROUP).maxCount(16), PermafrozenBlocks.FIR_SIGN, PermafrozenBlocks.FIR_WALL_SIGN));
	public static final TerraformBoatItem FIR_BOAT = create("fir_boat", new TerraformBoatItem(() -> PermafrozenEntities.FIR_BOAT, new Item.Settings().group(Permafrozen.GROUP).maxCount(1)));
	public static final Item NUDIFAE_SPAWN_EGG = create("nudifae_spawn_egg", new SpawnEggItem(PermafrozenEntities.NUDIFAE, 0xb3e5fc, 0x0090ea, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI_SPAWN_EGG = create("lunar_koi_spawn_egg", new SpawnEggItem(PermafrozenEntities.LUNAR_KOI, 0x25dbe4, 0xdefafc, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item FATFISH_SPAWN_EGG = create("fatfish_spawn_egg", new SpawnEggItem(PermafrozenEntities.FATFISH, 0x58705f, 0x655b7c, (new Item.Settings()).group(Permafrozen.GROUP)));
	
	//why the fuck is axe & pickaxe protected mojang
	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
		return item;
	}
	
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
