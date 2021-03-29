package permafrozen.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import permafrozen.Permafrozen;
import permafrozen.block.*;

import java.lang.reflect.Field;


public class BlockRegistry {

    // Declare all blocks in the mod
    public static final Block COBALT_ORE = new CobaltOre();
    public static final Block COBALT_BLOCK = new CobaltBlock();
    public static final Block WULFRUM_BLOCK = new WulfrumBlock();
    public static final Block TANZANITE_BLOCK = new TanzaniteBlock();
    public static final Block FROZEN_DEBRIS = new FrozenDebris();

    public static final Block HERTZSTONE = new Hertzstone();
    public static final Block HERTZSTONE_SLAB = new HertzstoneSlab();
    public static final Block HERTZSTONE_STAIRS = new HertzstoneStairs(() -> HERTZSTONE.getDefaultState()); // idk dont ask
    public static final Block HERTZSTONE_WALL = new HertzstoneWall();

    public static final Block POLISHED_HERTZSTONE = new PolishedHertzstone();
    public static final Block POLISHED_HERTZSTONE_SLAB = new PolishedHertzstoneSlab();
    public static final Block POLISHED_HERTZSTONE_STAIRS = new PolishedHertzstoneStairs(() -> POLISHED_HERTZSTONE.getDefaultState());
    public static final Block POLISHED_HERTZSTONE_WALL = new PolishedHertzstoneWall();
    public static final Block POLISHED_HERTZSTONE_BUTTON = new PolishedHertzstoneButton();
    public static final Block POLISHED_HERTZSTONE_PRESSURE_PLATE = new PolishedHertzstonePressurePlate();

    public static final Block POLISHED_HERTZSTONE_BRICKS = new PolishedHertzstoneBricks();
    public static final Block POLISHED_HERTZSTONE_BRICK_SLAB = new PolishedHertzstoneBrickSlab();
    public static final Block POLISHED_HERTZSTONE_BRICK_STAIRS = new PolishedHertzstoneBrickStairs(() -> POLISHED_HERTZSTONE_BRICKS.getDefaultState());
    public static final Block POLISHED_HERTZSTONE_BRICK_WALL = new PolishedHertzstoneBrickWall();

    public static final Block CHISELED_POLISHED_HERTZSTONE = new ChiseledPolishedHertzstone();
    public static final Block GILDED_HERTZSTONE = new GildedHertzstone();

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
