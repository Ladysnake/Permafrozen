package ladysnake.permafrozen.registry;

import ladysnake.permafrozen.Permafrozen;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ladysnake.permafrozen.item.PermafrozenAxeItem;
import ladysnake.permafrozen.item.PermafrozenHoeItem;
import ladysnake.permafrozen.item.PermafrozenPickaxeItem;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class PermafrozenItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	
	public static final Item WULFRAM_INGOT = create("wulfram_ingot", new Item(new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_SWORD = create("wulfram_sword", new SwordItem(PermafrozenMaterials.WULFRAM, 3, -2.4f, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_SHOVEL = create("wulfram_shovel", new ShovelItem(PermafrozenMaterials.WULFRAM, 0.5f, -3, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_AXE = create("wulfram_axe", new PermafrozenAxeItem(PermafrozenMaterials.WULFRAM, 4.5f, -3, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_PICKAXE = create("wulfram_pickaxe", new PermafrozenPickaxeItem(PermafrozenMaterials.WULFRAM, 1, -3, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_HOE = create("wulfram_hoe", new PermafrozenHoeItem(PermafrozenMaterials.WULFRAM, -1, 0, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item THAWING_PERMAFROST_CLUMP = create("thawing_permafrost_clump", new Item(new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item LUNAR_KOI_BUCKET = create("lunar_koi_bucket", new EntityBucketItem(PermafrozenEntities.LUNAR_KOI, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP).maxCount(1)));
	public static final Item LUNAR_KOI = create("lunar_koi", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(FoodComponents.SALMON)));
	public static final Item FATFISH_BUCKET = create("fatfish_bucket", new EntityBucketItem(PermafrozenEntities.FATFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP).maxCount(1)));
	public static final Item FATFISH = create("fatfish", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(FoodComponents.COD)));
	public static final Item COOKED_FATFISH = create("cooked_fatfish", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(FoodComponents.COOKED_COD)));
	public static final Item FIR_PINECONE = create("fir_cone", new Item(new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item DEADWOOD_DOOR_ITEM = create("deadwood_door", new TallBlockItem(PermafrozenBlocks.DEADWOOD_DOOR, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item DEADWOOD_SIGN_ITEM = create("deadwood_sign", new SignItem(new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP).maxCount(16), PermafrozenBlocks.DEADWOOD_SIGN, PermafrozenBlocks.DEADWOOD_WALL_SIGN));
	public static final Item NUDIFAE_BUCKET = create("nudifae_bucket", new EntityBucketItem(PermafrozenEntities.NUDIFAE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP).maxCount(1)));
	public static final Item NUDIFAE_SPAWN_EGG = create("nudifae_spawn_egg", new SpawnEggItem(PermafrozenEntities.NUDIFAE, 0xb3e5fc, 0x0090ea, (new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item LUNAR_KOI_SPAWN_EGG = create("lunar_koi_spawn_egg", new SpawnEggItem(PermafrozenEntities.LUNAR_KOI, 0x25dbe4, 0xdefafc, (new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item FATFISH_SPAWN_EGG = create("fatfish_spawn_egg", new SpawnEggItem(PermafrozenEntities.FATFISH, 0x58705f, 0x655b7c, (new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item AURORA_FAE_SPAWN_EGG = create("aurora_fae_spawn_egg", new SpawnEggItem(PermafrozenEntities.AURORA_FAE, 0x1de9b6, 0xd400f9, (new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item PUFFBOO_SPAWN_EGG = create("puffboo_spawn_egg", new SpawnEggItem(PermafrozenEntities.PUFFBOO, 0x232226, 0xf34f18, (new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_HELMET = create("wulfram_helmet", new ArmorItem(PermafrozenArmorMaterials.WULFRAM_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_CHESTPLATE = create("wulfram_chestplate", new ArmorItem(PermafrozenArmorMaterials.WULFRAM_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_LEGGINGS = create("wulfram_leggings", new ArmorItem(PermafrozenArmorMaterials.WULFRAM_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item WULFRAM_BOOTS = create("wulfram_boots", new ArmorItem(PermafrozenArmorMaterials.WULFRAM_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item PUFFBOO_FEATHER = create("puffboo_feather", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP)));
	public static final Item PUFFBOO_MEAT = create("raw_puffboo", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(FoodComponents.CHICKEN)));
	public static final Item COOKED_PUFFBOO_MEAT = create("cooked_puffboo", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(FoodComponents.COOKED_CHICKEN)));
	public static final FoodComponent SPECTRAL_STUFF = new FoodComponent.Builder().alwaysEdible().hunger(5).statusEffect(new StatusEffectInstance(PermafrozenStatusEffects.SPECTRAL_DAZE, 2400, 0), 1.0f).build();
	public static final Item SPECTRAL_DUST = create("spectral_dust", new Item((new Item.Settings().food(SPECTRAL_STUFF).group(Permafrozen.PERMAFROZEN_GROUP))));
	public static final FoodComponent BURROW_GRUB_FOOD = new FoodComponent.Builder().alwaysEdible().hunger(2).statusEffect(new StatusEffectInstance(PermafrozenStatusEffects.BURROWED, 2400, 0), 0.9f).build();
	public static final Item BURROW_GRUB = create("burrow_grub", new Item((new Item.Settings()).group(Permafrozen.PERMAFROZEN_GROUP).food(BURROW_GRUB_FOOD)));

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
		return item;
	}
	
	public static void init() {
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(FIR_PINECONE, 0.6f);
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
