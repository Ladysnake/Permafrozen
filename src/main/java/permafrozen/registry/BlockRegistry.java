package permafrozen.registry;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenProperty;

import java.util.function.Supplier;


public class BlockRegistry {

    public static final DeferredRegister<Block> blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Permafrozen.MOD_ID);
    public static final DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Permafrozen.MOD_ID);


    // Declare all blocks in the mod                                                                                    you can never have too many spaces
    public static final RegistryObject<Block> COBALT_ORE                         = createBlock("cobalt_ore",                         () -> new Block(PermafrozenProperty.ore(2)));
    public static final RegistryObject<Block> COBALT_BLOCK                       = createBlock("cobalt_block",                       () -> new Block(PermafrozenProperty.METAL_BLOCK));

    public static final RegistryObject<Block> WULFRAM_ORE                        = createBlock("wulfram_ore",                        () -> new Block(PermafrozenProperty.ore(1)));
    public static final RegistryObject<Block> WULFRAM_BLOCK                      = createBlock("wulfram_block",                      () -> new Block(PermafrozenProperty.METAL_BLOCK));
    public static final RegistryObject<Block> FROZEN_DEBRIS                      = createBlock("frozen_debris",                      () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.ANCIENT_DEBRIS)));

    public static final RegistryObject<Block> CRYORITE_BLOCK                     = createBlock("cryorite_block",                     () -> new Block(PermafrozenProperty.METAL_BLOCK));

    public static final RegistryObject<Block> HERTZSTONE                         = createBlock("hertzstone",                         () -> new Block(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> HERTZSTONE_SLAB                    = createBlock("hertzstone_slab",                    () -> new SlabBlock(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> HERTZSTONE_STAIRS                  = createBlock("hertzstone_stairs",                  () -> new StairsBlock(() -> HERTZSTONE.get().getDefaultState(),                PermafrozenProperty.STONE)); // idk dont ask
    public static final RegistryObject<Block> HERTZSTONE_WALL                    = createBlock("hertzstone_wall",                    () -> new WallBlock(PermafrozenProperty.STONE));

    public static final RegistryObject<Block> POLISHED_HERTZSTONE                = createBlock("polished_hertzstone",                () -> new Block(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_SLAB           = createBlock("polished_hertzstone_slab",           () -> new SlabBlock(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_STAIRS         = createBlock("polished_hertzstone_stairs",         () -> new StairsBlock(() -> POLISHED_HERTZSTONE.get().getDefaultState(),        PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_WALL           = createBlock("polished_hertzstone_wall",           () -> new WallBlock(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_BUTTON         = createBlock("polished_hertzstone_button",         () -> new StoneButtonBlock(PermafrozenProperty.STONE_BUTTON));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_PRESSURE_PLATE = createBlock("polished_hertzstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, PermafrozenProperty.STONE_BUTTON));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_BRICKS         = createBlock("polished_hertzstone_bricks",         () -> new Block(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_BRICK_SLAB     = createBlock("polished_hertzstone_brick_slab",     () -> new SlabBlock(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_BRICK_STAIRS   = createBlock("polished_hertzstone_brick_stairs",   () -> new StairsBlock(() -> POLISHED_HERTZSTONE_BRICKS.get().getDefaultState(), PermafrozenProperty.STONE));
    public static final RegistryObject<Block> POLISHED_HERTZSTONE_BRICK_WALL     = createBlock("polished_hertzstone_brick_wall",     () -> new WallBlock(PermafrozenProperty.STONE) );

    public static final RegistryObject<Block> CHISELED_POLISHED_HERTZSTONE       = createBlock("chiseled_polished_hertzstone",       () -> new Block(PermafrozenProperty.STONE));
    public static final RegistryObject<Block> GILDED_HERTZSTONE                  = createBlock("gilded_hertzstone",                  () -> new Block(PermafrozenProperty.STONE));

    public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {

        RegistryObject<B> block = blockRegister.register(name, supplier);
        itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(Permafrozen.ITEM_GROUP)));

        return block;

    }

    public static void register(IEventBus eventBus) {

        blockRegister.register(eventBus);
        itemRegister.register(eventBus);

    }
}
