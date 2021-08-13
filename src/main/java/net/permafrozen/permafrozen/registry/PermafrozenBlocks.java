package net.permafrozen.permafrozen.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.*;
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
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.PrismarineCrystalBlock;
import net.permafrozen.permafrozen.block.PrismarineCrystalClusterBlock;
import net.permafrozen.permafrozen.block.util.PermafrozenDeadCoralBlock;
import net.permafrozen.permafrozen.block.util.PermafrozenKelpPlantBlock;
import net.permafrozen.permafrozen.block.util.PermafrozenPlantBlock;
import net.permafrozen.permafrozen.block.util.PermafrozenSaplingBlock;
import net.permafrozen.permafrozen.mixin.BlocksAccessor;
import net.permafrozen.permafrozen.worldgen.tree.FirSaplingGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class PermafrozenBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final Block STRIPPED_FIR_LOG = create("stripped_fir_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), true);
	public static final Block STRIPPED_FIR_WOOD = create("stripped_fir_wood", new PillarBlock(copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_LOG = create("fir_log", new StrippableLogBlock(() -> STRIPPED_FIR_LOG, MapColor.BROWN, copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_WOOD = create("fir_wood", new StrippableLogBlock(() -> STRIPPED_FIR_WOOD, MapColor.BROWN, copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_LEAVES = create("fir_leaves", BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS), true);
	public static final Block FIR_SAPLING = create("fir_sapling", new PermafrozenSaplingBlock(new FirSaplingGenerator(), copyOf(Blocks.OAK_SAPLING)), true);
	public static final Block POTTED_FIR_SAPLING = create("potted_fir_sapling", new FlowerPotBlock(FIR_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)), false);
	public static final Block FIR_PLANKS = create("fir_planks", new Block(copyOf(Blocks.OAK_PLANKS)), true);
	public static final Block FIR_STAIRS = create("fir_stairs", new TerraformStairsBlock(FIR_PLANKS, copyOf(Blocks.OAK_STAIRS)), true);
	public static final Block FIR_SLAB = create("fir_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)), true);
	public static final Block FIR_FENCE = create("fir_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)), true);
	public static final Block FIR_FENCE_GATE = create("fir_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE)), true);
	public static final Block FIR_PRESSURE_PLATE = create("fir_pressure_plate", new TerraformPressurePlateBlock(copyOf(Blocks.OAK_PRESSURE_PLATE)), true);
	public static final Block FIR_BUTTON = create("fir_button", new TerraformButtonBlock(copyOf(Blocks.OAK_BUTTON)), true);
	public static final Block FIR_TRAPDOOR = create("fir_trapdoor", new TerraformTrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR)), true);
	public static final Block FIR_DOOR = create("fir_door", new TerraformDoorBlock(copyOf(Blocks.OAK_DOOR)), false);
	private static final Identifier FIR_SIGN_TEXTURE = new Identifier(Permafrozen.MOD_ID, "entity/sign/fir");
	public static final TerraformSignBlock FIR_SIGN = create("fir_sign", new TerraformSignBlock(FIR_SIGN_TEXTURE, copyOf(Blocks.OAK_SIGN)), false);
	public static final Block FIR_WALL_SIGN = create("fir_wall_sign", new TerraformWallSignBlock(FIR_SIGN_TEXTURE, copyOf(Blocks.OAK_WALL_SIGN)), false);

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

	public static final Block SHIVERSLATE = create("shiverslate", new Block(copyOf(Blocks.DEEPSLATE)), true);
	public static final Block COBBLED_SHIVERSLATE = create("cobbled_shiverslate", new Block(copyOf(Blocks.COBBLED_DEEPSLATE)), true);
	public static final Block SHIVERSLATE_BRICKS = create("shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);
	public static final Block CHISELLED_SHIVERSLATE_BRICKS = create("chiselled_shiverslate_bricks", new Block(copyOf(Blocks.DEEPSLATE_BRICKS)), true);

    public static final Block SPECTRAL_CAP = create("spectral_cap", new PermafrozenPlantBlock(AbstractBlock.Settings.of(Material.PLANT, MapColor.LAPIS_BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).luminance((state) -> 11)), true);
	public static final Block GLACIAL_KELP = create("glacial_kelp", new PermafrozenKelpPlantBlock(copyOf(Blocks.KELP_PLANT)), true);

	public static final Block PRISMARINE_CLUSTER = create("prismarine_cluster", new PrismarineCrystalClusterBlock(7, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 12)), true);
	public static final Block LARGE_PRISMARINE_BUD = create("large_prismarine_bud", new PrismarineCrystalClusterBlock(5, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 9)), true);
	public static final Block MEDIUM_PRISMARINE_BUD = create("medium_prismarine_bud", new PrismarineCrystalClusterBlock(4, 3, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 7)), true);
	public static final Block SMALL_PRISMARINE_BUD = create("small_prismarine_bud", new PrismarineCrystalClusterBlock(3, 4, FabricBlockSettings.of(Material.AMETHYST, MapColor.LIGHT_BLUE_GRAY).nonOpaque().breakByTool(FabricToolTags.PICKAXES).requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance((blockState) -> 5)), true);
	public static final Block BUDDING_PRISMARINE = create("budding_prismarine", new PrismarineCrystalBlock(AbstractBlock.Settings.of(Material.AMETHYST).ticksRandomly().strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance((blockState) -> 14)), true);

	public static final Block DEAD_AURORA_CORAL_BLOCK = create("dead_aurora_coral_block", new PermafrozenDeadCoralBlock(AbstractBlock.Settings.of(Material.STONE)), true);
	public static final Block AURORA_CORAL_BLOCK = create("aurora_coral_block", new CoralBlockBlock(DEAD_AURORA_CORAL_BLOCK, AbstractBlock.Settings.of(Material.STONE, MapColor.PURPLE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.CORAL).luminance((blockState) -> 15)), true);
	
	
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
		fuelRegistry.add(FIR_FENCE, 300);
		fuelRegistry.add(FIR_FENCE_GATE, 300);
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(STRIPPED_FIR_LOG, 5, 5);
		flammableRegistry.add(STRIPPED_FIR_WOOD, 5, 5);
		flammableRegistry.add(FIR_LOG, 5, 5);
		flammableRegistry.add(FIR_WOOD, 5, 5);
		flammableRegistry.add(FIR_LEAVES, 30, 60);
		flammableRegistry.add(FIR_PLANKS, 5, 20);
		flammableRegistry.add(FIR_STAIRS, 5, 20);
		flammableRegistry.add(FIR_SLAB, 5, 20);
		flammableRegistry.add(FIR_FENCE, 5, 20);
		flammableRegistry.add(FIR_FENCE_GATE, 5, 20);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(FIR_LEAVES, 0.3f);
		compostRegistry.add(FIR_SAPLING, 0.3f);
	}
}
