package ladysnake.permafrozen.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.*;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.block.*;
import ladysnake.permafrozen.block.util.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

@SuppressWarnings("unused")
public class PermafrozenBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
//permaforst
	public static final Block MOSSY_PERMAFROST = create("mossy_permafrost", new MossyPermafrostBlock(copyOf(Blocks.GRASS_BLOCK)), true);
	public static final Block PERMAFROST = create("permafrost", new Block(copyOf(Blocks.DIRT)), true);
	public static final Block COARSE_PERMAFROST = create("coarse_permafrost", new Block(copyOf(Blocks.COARSE_DIRT)), true);
	public static final Block THAWING_PERMAFROST = create("thawing_permafrost", new Block(copyOf(Blocks.CLAY)), true);

	//peat
	public static final Block QUAGMIRE = create("quagmire", new QuagmireBlock(copyOf(Blocks.DIRT)), true);
	public static final Block PEAT = create("peat", new Block(copyOf(Blocks.COARSE_DIRT)), true);
	public static final Block DRIED_PEAT_BRICKS = create("peat_bricks", new Block(copyOf(Blocks.BRICKS)), true);
	public static final Block DRIED_PEAT_SHINGLES = create("peat_shingles", new Block(copyOf(Blocks.BRICKS)), true);
	public static final Block DRIED_PEAT_SHINGLE_SLAB = create("peat_shingles_slab", new SlabBlock(copyOf(Blocks.BRICKS)), true);
	public static final Block DRIED_PEAT_SHINGLE_STAIRS = create("peat_shingles_stairs", new TerraformStairsBlock(DRIED_PEAT_SHINGLES, copyOf(Blocks.BRICKS)), true);
	public static final Block DRIED_PEAT_TILE = create("peat_tile", new Block(copyOf(Blocks.BRICKS)), true);

	//deadwood
	public static final Block STRIPPED_DEADWOOD_LOG = create("stripped_deadwood_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), true);
	public static final Block STRIPPED_DEADWOOD_WOOD = create("stripped_deadwood_wood", new PillarBlock(copyOf(STRIPPED_DEADWOOD_LOG)), true);
	public static final Block DEADWOOD_LOG = create("deadwood_log", new StrippableDeadwoodBlock(() -> STRIPPED_DEADWOOD_LOG, MapColor.BROWN, copyOf(STRIPPED_DEADWOOD_LOG)), true);
	public static final Block DEADWOOD_WOOD = create("deadwood_wood", new StrippableDeadwoodBlock(() -> STRIPPED_DEADWOOD_WOOD, MapColor.BROWN, copyOf(STRIPPED_DEADWOOD_LOG)), true);
	public static final Block DEADWOOD_PLANKS = create("deadwood_planks", new Block(copyOf(Blocks.OAK_PLANKS)), true);
	public static final Block DEADWOOD_STAIRS = create("deadwood_stairs", new TerraformStairsBlock(DEADWOOD_PLANKS, copyOf(Blocks.OAK_STAIRS)), true);
	public static final Block DEADWOOD_SLAB = create("deadwood_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)), true);
	public static final Block DEADWOOD_THORN = create("deadwood_thorn", new PrismarineCrystalClusterBlock(7, 3, FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).nonOpaque().breakByTool(FabricToolTags.AXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.WOOD).strength(1.5f)), true);
	public static final Block DEADWOOD_FENCE = create("deadwood_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)), true);
	public static final Block DEADWOOD_FENCE_GATE = create("deadwood_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE)), true);
	public static final Block DEADWOOD_PRESSURE_PLATE = create("deadwood_pressure_plate", new TerraformPressurePlateBlock(copyOf(Blocks.OAK_PRESSURE_PLATE)), true);
	public static final Block DEADWOOD_BUTTON = create("deadwood_button", new TerraformButtonBlock(copyOf(Blocks.OAK_BUTTON)), true);
	public static final Block DEADWOOD_TRAPDOOR = create("deadwood_trapdoor", new TerraformTrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR)), true);
	public static final Block DEADWOOD_DOOR = create("deadwood_door", new TerraformDoorBlock(copyOf(Blocks.OAK_DOOR)), false);
	private static final Identifier DEADWOOD_SIGN_TEXTURE = new Identifier(Permafrozen.MOD_ID, "entity/sign/deadwood");
	public static final TerraformSignBlock DEADWOOD_SIGN = create("deadwood_sign", new TerraformSignBlock(DEADWOOD_SIGN_TEXTURE, copyOf(Blocks.OAK_SIGN)), false);
	public static final Block DEADWOOD_WALL_SIGN = create("deadwood_wall_sign", new TerraformWallSignBlock(DEADWOOD_SIGN_TEXTURE, copyOf(Blocks.OAK_WALL_SIGN)), false);
	//spireshroom
	public static final Block STRIPPED_SPIRESHROOM_LOG = create("stripped_spireshroom_log", new PillarBlock(copyOf(Blocks.WARPED_HYPHAE)), true);
	public static final Block STRIPPED_SPIRESHROOM_WOOD = create("stripped_spireshroom_wood", new PillarBlock(copyOf(STRIPPED_SPIRESHROOM_LOG)), true);
	public static final Block SPIRESHROOM_LOG = create("spireshroom_log", new StrippableLogBlock(() -> STRIPPED_SPIRESHROOM_LOG, MapColor.CYAN, copyOf(STRIPPED_SPIRESHROOM_LOG)), true);
	public static final Block SPIRESHROOM_WOOD = create("spireshroom_wood", new StrippableLogBlock(() -> STRIPPED_SPIRESHROOM_WOOD, MapColor.CYAN, copyOf(STRIPPED_SPIRESHROOM_LOG)), true);
	public static final Block SPIRESHROOM_PLANKS = create("spireshroom_planks", new Block(copyOf(Blocks.WARPED_PLANKS)), true);


	//snad
	public static final Block SAPPHIRE_SAND = create("sapphire_sand", new GravelBlock(copyOf(Blocks.SAND)), true);
	public static final Block SAPPHIRE_SANDSTONE = create("sapphire_sandstone", new Block(copyOf(Blocks.SANDSTONE)), true);
	public static final Block CHISELED_SAPPHIRE_SANDSTONE = create("chiseled_sapphire_sandstone", new Block(copyOf(Blocks.CHISELED_SANDSTONE)), true);
	public static final Block SAPPHIRE_SANDSTONE_WALL = create("sapphire_sandstone_wall", new WallBlock(copyOf(Blocks.SANDSTONE_WALL)), true);
	public static final Block SAPPHIRE_SANDSTONE_SLAB = create("sapphire_sandstone_slab", new SlabBlock(copyOf(Blocks.SANDSTONE_SLAB)), true);
	public static final Block SAPPHIRE_SANDSTONE_STAIRS = create("sapphire_sandstone_stairs", new TerraformStairsBlock(SAPPHIRE_SANDSTONE, copyOf(Blocks.SANDSTONE_STAIRS)), true);
	public static final Block CUT_SAPPHIRE_SANDSTONE = create("cut_sapphire_sandstone", new Block(copyOf(Blocks.CUT_SANDSTONE)), true);
	public static final Block CUT_SAPPHIRE_SANDSTONE_SLAB = create("cut_sapphire_sandstone_slab", new SlabBlock(copyOf(Blocks.CUT_SANDSTONE_SLAB)), true);
	public static final Block SMOOTH_SAPPHIRE_SANDSTONE = create("smooth_sapphire_sandstone", new Block(copyOf(Blocks.SMOOTH_SANDSTONE)), true);
	public static final Block SMOOTH_SAPPHIRE_SANDSTONE_SLAB = create("smooth_sapphire_sandstone_slab", new SlabBlock(copyOf(Blocks.SMOOTH_SANDSTONE_SLAB)), true);
	public static final Block SMOOTH_SAPPHIRE_SANDSTONE_STAIRS = create("smooth_sapphire_sandstone_stairs", new TerraformStairsBlock(SMOOTH_SAPPHIRE_SANDSTONE, copyOf(Blocks.SMOOTH_SANDSTONE_STAIRS)), true);
//stone
	public static final Block SHIVERSLATE = create("shiverslate", new ShiverslateBlock(copyOf(Blocks.DEEPSLATE)), true);
	public static final Block SHIVERSLATE_STAIRS = create("shiverslate_stairs", new TerraformStairsBlock(SHIVERSLATE, copyOf(Blocks.DEEPSLATE_BRICK_STAIRS)), true);
	public static final Block SHIVERSLATE_SLAB = create("shiverslate_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block SHIVERSLATE_BUTTON = create("shiverslate_button", new PermafrozenStoneButtonBlock(copyOf(Blocks.DEEPSLATE)), true);
	public static final Block SHIVERSLATE_PRESSURE_PLATE = create("shiverslate_pressure_plate", new TerraformPressurePlateBlock(copyOf(Blocks.DEEPSLATE)), true);
	public static final Block COBBLED_SHIVERSLATE = create("cobbled_shiverslate", new ShiverslateBlock(copyOf(Blocks.COBBLED_DEEPSLATE)), true);
	public static final Block COBBLED_SHIVERSLATE_STAIRS = create("cobbled_shiverslate_stairs", new TerraformStairsBlock(COBBLED_SHIVERSLATE, copyOf(Blocks.DEEPSLATE_BRICK_STAIRS)), true);
	public static final Block COBBLED_SHIVERSLATE_SLAB = create("cobbled_shiverslate_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block COBBLED_SHIVERSLATE_WALL = create("cobbled_shiverslate_wall", new WallBlock(copyOf(Blocks.DEEPSLATE_BRICK_WALL)), true);
	public static final Block MOSSY_COBBLED_SHIVERSLATE = create("mossy_cobbled_shiverslate", new ShiverslateBlock(copyOf(Blocks.COBBLED_DEEPSLATE)), true);
	public static final Block MOSSY_COBBLED_SHIVERSLATE_STAIRS = create("mossy_cobbled_shiverslate_stairs", new TerraformStairsBlock(MOSSY_COBBLED_SHIVERSLATE, copyOf(Blocks.DEEPSLATE_BRICK_STAIRS)), true);
	public static final Block MOSSY_COBBLED_SHIVERSLATE_SLAB = create("mossy_cobbled_shiverslate_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block MOSSY_COBBLED_SHIVERSLATE_WALL = create("mossy_cobbled_shiverslate_wall", new WallBlock(copyOf(Blocks.DEEPSLATE_BRICK_WALL)), true);
	public static final Block SMOOTH_SHIVERSLATE = create("smooth_shiverslate", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block SMOOTH_SHIVERSLATE_SLAB = create("smooth_shiverslate_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block SMOOTH_SHIVERSLATE_BRICKS = create("smooth_shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block CRACKED_SMOOTH_SHIVERSLATE_BRICKS = create("cracked_smooth_shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block CHISELED_SMOOTH_SHIVERSLATE_BRICKS = create("chiseled_smooth_shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block SMOOTH_SHIVERSLATE_BRICK_STAIRS = create("smooth_shiverslate_brick_stairs", new TerraformStairsBlock(SMOOTH_SHIVERSLATE_BRICKS, copyOf(Blocks.DEEPSLATE_BRICK_STAIRS)), true);
	public static final Block SMOOTH_SHIVERSLATE_BRICK_SLAB = create("smooth_shiverslate_brick_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block SMOOTH_SHIVERSLATE_BRICK_WALL = create("smooth_shiverslate_brick_wall", new WallBlock(copyOf(Blocks.DEEPSLATE_BRICK_WALL)), true);
	public static final Block MOSSY_SMOOTH_SHIVERSLATE_BRICKS = create("mossy_smooth_shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block MOSSY_SMOOTH_SHIVERSLATE_BRICK_STAIRS = create("mossy_smooth_shiverslate_brick_stairs", new TerraformStairsBlock(MOSSY_SMOOTH_SHIVERSLATE_BRICKS, copyOf(Blocks.DEEPSLATE_BRICK_STAIRS)), true);
	public static final Block MOSSY_SMOOTH_SHIVERSLATE_BRICK_SLAB = create("mossy_smooth_shiverslate_brick_slab", new SlabBlock(copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
	public static final Block MOSSY_SMOOTH_SHIVERSLATE_BRICK_WALL = create("mossy_smooth_shiverslate_brick_wall", new WallBlock(copyOf(Blocks.DEEPSLATE_BRICK_WALL)), true);

//wulfram
	public static final Block WULFRAM_BLOCK = create("wulfram_block", new PillarBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.LIGHT_BLUE_GRAY).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)), true);
	public static final Block WULFRAM_ORE = create("wulfram_ore", new OreBlock(copyOf(Blocks.DEEPSLATE_IRON_ORE), UniformIntProvider.create(0, 2)), true);
	public static final Block RAW_WULFRAM_BLOCK = create("raw_wulfram_block", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.CYAN).requiresTool().strength(5.0f, 6.0f)), true);

//vegetation
	public static final Block GLAUCA_GRASS = create("glauca_grass", new PermafrozenPlantBlock(copyOf(Blocks.GRASS)), true);

//crymstals
	public static final Block PRISMARINE_CLUSTER = create("prismarine_cluster", new PrismarineCrystalClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 12)), true);
	public static final Block LARGE_PRISMARINE_BUD = create("large_prismarine_bud", new PrismarineCrystalClusterBlock(5, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 9)), true);
	public static final Block MEDIUM_PRISMARINE_BUD = create("medium_prismarine_bud", new PrismarineCrystalClusterBlock(4, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 7)), true);
	public static final Block SMALL_PRISMARINE_BUD = create("small_prismarine_bud", new PrismarineCrystalClusterBlock(3, 4, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 5)), true);
	public static final Block BUDDING_PRISMARINE = create("budding_prismarine", new PrismarineCrystalBlock(AbstractBlock.Settings.of(Material.AMETHYST).ticksRandomly().strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance((blockState) -> 14)), true);

//spectral cap
	public static final Block SPECTRAL_CAP = create("spectral_cap", new SpectralCapBlock(AbstractBlock.Settings.of(Material.PLANT, MapColor.LAPIS_BLUE).ticksRandomly().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS).luminance((state) -> 10)), true);
	public static final Block SPECTRAL_CAP_BLOCK = create("spectral_cap_block", new MushroomBlock(copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance((state) -> 10)), true);
	public static final Block SPECTRAL_CAP_STEM = create("spectral_cap_stem", new PillarBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD).strength(2.0f).sounds(BlockSoundGroup.STEM)), true);

//aurora tech
	public static final Block AURORA_BLASTER = create("aurora_blaster", new AuroraBlasterBlock(copyOf(Blocks.OBSIDIAN)), true);
	public static final Block AURORA_BARRIER = create("aurora_barrier", new AuroraBarrierBlock(copyOf(Blocks.OBSIDIAN)), true);
	public static final Block AURORA_ALTAR = create("aurora_altar", new AuroraAltarBlock(copyOf(Blocks.OBSIDIAN).nonOpaque()), true);

//gyrehorn
	public static final Block FLEECE = create("fleece", new Block(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOL)), true);

//other shit
	public static final Block DISTILLERY = create("distillery", new DistilleryBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL)), true);
	public static final Block DEAD_PRISMATIC_CORAL_BLOCK = create("dead_prismatic_coral_block", new PermafrozenDeadCoralBlock(AbstractBlock.Settings.of(Material.STONE)), true);
	public static final Block PRISMATIC_CORAL_BLOCK = create("prismatic_coral_block", new CoralBlockBlock(DEAD_PRISMATIC_CORAL_BLOCK, AbstractBlock.Settings.of(Material.STONE, MapColor.PURPLE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.CORAL).luminance((blockState) -> 8)), true);
	public static final Block DEAD_PRISMATIC_CORAL = create("dead_prismatic_coral", new PermafrozenDeadCoral(AbstractBlock.Settings.of(Material.STONE)), true);
	public static final Block PRISMATIC_CORAL = create("prismatic_coral", new PermafrozenCoral(DEAD_PRISMATIC_CORAL, AbstractBlock.Settings.of(Material.STONE, MapColor.PURPLE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.CORAL).luminance((blockState) -> 8)), true);
	public static final Block GLAUCA_BUNDLE = create("glauca_bundle", new PermafrozenFacingBlock(copyOf(Blocks.HAY_BLOCK)), true);
	public static final Block FEN_ICE = create("fen_ice", new FenIceBlock(AbstractBlock.Settings.of(Material.DENSE_ICE)), true);

	private static <T extends Block> T create(String name, T block, boolean createItem) {
		BLOCKS.put(block, new Identifier(Permafrozen.MOD_ID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, new Item.Settings().group(Permafrozen.PERMAFROZEN_GROUP)), BLOCKS.get(block));
		}
		return block;
	}
	
	public static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(DEADWOOD_FENCE, 300);
		fuelRegistry.add(DEADWOOD_FENCE_GATE, 300);
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(STRIPPED_DEADWOOD_LOG, 5, 5);
		flammableRegistry.add(STRIPPED_DEADWOOD_WOOD, 5, 5);
		flammableRegistry.add(DEADWOOD_LOG, 5, 5);
		flammableRegistry.add(DEADWOOD_WOOD, 5, 5);
		flammableRegistry.add(DEADWOOD_PLANKS, 5, 20);
		flammableRegistry.add(DEADWOOD_STAIRS, 5, 20);
		flammableRegistry.add(DEADWOOD_SLAB, 5, 20);
		flammableRegistry.add(DEADWOOD_FENCE, 5, 20);
		flammableRegistry.add(DEADWOOD_FENCE_GATE, 5, 20);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(SPECTRAL_CAP, 0.6f);
		compostRegistry.add(GLAUCA_GRASS, 0.6f);
	}
}
