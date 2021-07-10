package net.permafrozen.permafrozen.block;


import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.pain.SignTypeHelper;
import net.permafrozen.permafrozen.worldgen.tree.FirSaplingGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenBlocks {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final SignType FIR_ST = SignTypeHelper.register(new MojangWHYSignType("fir"));
    public static final Block FIR_PLANKS = create("fir_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_LOG = create("fir_log", createLogBlock(MapColor.SPRUCE_BROWN, MapColor.BROWN), Permafrozen.GROUP);
    public static final Block FIR_SAPLING = create("fir_sapling", new MojangWHYSaplingBlock(new FirSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), Permafrozen.GROUP);
    public static final Block STRIPPED_FIR_LOG = create("stripped_fir_log", createLogBlock(MapColor.SPRUCE_BROWN, MapColor.SPRUCE_BROWN), Permafrozen.GROUP);
    public static final Block STRIPPED_FIR_WOOD = create("stripped_fir_wood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_WOOD = create("fir_wood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_LEAVES = create("fir_leaves", createLeavesBlock(BlockSoundGroup.GRASS), Permafrozen.GROUP);
    //public static final Block FIR_SIGN = create("fir_sign", new SignBlock(AbstractBlock.Settings.of(Material.WOOD, FIR_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), FIR_ST), Permafrozen.GROUP);
    public static final Block FIR_PRESSURE_PLATE = create("fir_pressure_plate", new MojangWHYPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, FIR_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_TRAPDOOR = create("fir_trapdoor", new MojangWHYTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(PermafrozenBlocks::never)), Permafrozen.GROUP);
    public static final Block FIR_FENCE_GATE= create("fir_fence_gate", new FenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, FIR_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_FENCE = create("fir_fence", new FenceBlock(AbstractBlock.Settings.of(Material.WOOD, FIR_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_STAIRS = create("fir_stairs", new MojangWHYStairsBlock(FIR_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(FIR_PLANKS)), Permafrozen.GROUP);
    public static final Block FIR_SLAB = create("fir_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block POTTED_FIR_SAPLING = create("potted_fir_sapling", new FlowerPotBlock(FIR_SAPLING, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque()), Permafrozen.GROUP);
    public static final Block FIR_BUTTON = create("fir_button", new MojangWHYWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), Permafrozen.GROUP);
    public static final Block FIR_DOOR = create("fir_door", new MojangWHYDoorBlock(AbstractBlock.Settings.of(Material.WOOD, FIR_PLANKS.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()), Permafrozen.GROUP);


    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }
    private static LeavesBlock createLeavesBlock(BlockSoundGroup soundGroup) {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(PermafrozenBlocks::canSpawnOnLeaves).suffocates(PermafrozenBlocks::never).blockVision(PermafrozenBlocks::never));
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }

    private static PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }
    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
    private static <T extends Block> T create(String name, T block) {
        BLOCKS.put(block, new Identifier(Permafrozen.MOD_ID, name));
        return block;
    }
    private static <B extends Block> B create(String name, B block, ItemGroup tab) {
        return create(name, block, new BlockItem(block, new Item.Settings().group(tab)));
    }
    private static <B extends Block> B create(String name, B block, BlockItem item) {
        create(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
        }
        return block;
    }
    private static <I extends BlockItem> I create(String name, I item) {
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
        return item;
    }
    public static void innit() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }

}
