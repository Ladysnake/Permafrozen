package permafrozen.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import permafrozen.Permafrozen;
import permafrozen.block.*;
import permafrozen.block.hertzstone.*;

import java.lang.reflect.Field;


public class BlockRegistry {

    // Declare all blocks in the mod
    public static final Block COBALT_ORE = new CobaltOre().setRegistryName("cobalt_ore");
    public static final Block COBALT_BLOCK = new CobaltBlock().setRegistryName("cobalt_block");
    public static final Block WULFRUM_BLOCK = new WulfrumBlock().setRegistryName("wulfrum_block");
    public static final Block TANZANITE_BLOCK = new TanzaniteBlock().setRegistryName("tanzanite_block");
    public static final Block FROZEN_DEBRIS = new FrozenDebris().setRegistryName("frozen_debris");
    public static final Block CHILLORITE_BLOCK = new ChilloriteBlock().setRegistryName("chillorite_block");

    public static final Block HERTZSTONE = new Hertzstone().setRegistryName("hertzstone");
    public static final Block HERTZSTONE_SLAB = new HertzstoneSlab().setRegistryName("hertzstone_slab");
    public static final Block HERTZSTONE_STAIRS = new HertzstoneStairs(() -> HERTZSTONE.getDefaultState()).setRegistryName("hertzstone_stairs"); // idk dont ask
    public static final Block HERTZSTONE_WALL = new HertzstoneWall().setRegistryName("hertzstone_wall");

    public static final Block POLISHED_HERTZSTONE = new PolishedHertzstone().setRegistryName("polished_hertzstone");
    public static final Block POLISHED_HERTZSTONE_SLAB = new PolishedHertzstoneSlab().setRegistryName("polished_hertzstone_slab");
    public static final Block POLISHED_HERTZSTONE_STAIRS = new PolishedHertzstoneStairs(() -> POLISHED_HERTZSTONE.getDefaultState()).setRegistryName("polished_hertzstone_stairs");
    public static final Block POLISHED_HERTZSTONE_WALL = new PolishedHertzstoneWall().setRegistryName("polished_hertzstone_wall");
    public static final Block POLISHED_HERTZSTONE_BUTTON = new PolishedHertzstoneButton().setRegistryName("polished_hertzstone_button");
    public static final Block POLISHED_HERTZSTONE_PRESSURE_PLATE = new PolishedHertzstonePressurePlate().setRegistryName("polished_hertzstone_pressure_plate");
    public static final Block POLISHED_HERTZSTONE_BRICKS = new PolishedHertzstoneBricks().setRegistryName("polished_hertzstone_bricks");
    public static final Block POLISHED_HERTZSTONE_BRICK_SLAB = new PolishedHertzstoneBrickSlab().setRegistryName("polished_hertzstone_brick_slab");
    public static final Block POLISHED_HERTZSTONE_BRICK_STAIRS = new PolishedHertzstoneBrickStairs(() -> POLISHED_HERTZSTONE_BRICKS.getDefaultState()).setRegistryName("polished_hertzstone_brick_stairs");
    public static final Block POLISHED_HERTZSTONE_BRICK_WALL = new PolishedHertzstoneBrickWall().setRegistryName("polished_hertzstone_brick_wall");

    public static final Block CHISELED_POLISHED_HERTZSTONE = new ChiseledPolishedHertzstone().setRegistryName("chiseled_polished_hertzstone");
    public static final Block GILDED_HERTZSTONE = new GildedHertzstone().setRegistryName("gilded_hertzstone");

    @SubscribeEvent
    public static void onBlocksRegistration(final RegistryEvent.Register<Block> e) {

        try {

            // Get all the vars declared in this class and register them if they're blocks
            for (Field f : BlockRegistry.class.getDeclaredFields()) {

                Object obj = f.get(null);
                if (obj instanceof Block) {
                    e.getRegistry().register((Block) obj);
                }

            }

        } catch (IllegalAccessException err) {

            throw new RuntimeException(err);

        }

    }

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> e) {

        // same thing but for block items
        try {

            for (Field f : BlockRegistry.class.getDeclaredFields()) {

                Object obj = f.get(null);

                if (obj instanceof Block) {

                    BlockItem itemBlock = new BlockItem((Block) obj, new Item.Properties().group(Permafrozen.ITEM_GROUP));

                    itemBlock.setRegistryName(((Block) obj).getRegistryName());
                    e.getRegistry().register(itemBlock);

                }

            }

        } catch (IllegalAccessException err) {

            throw new RuntimeException(err);

        }

    }

}
