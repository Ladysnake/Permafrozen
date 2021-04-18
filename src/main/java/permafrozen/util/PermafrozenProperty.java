package permafrozen.util;


import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class PermafrozenProperty {

    public static final Properties METAL_BLOCK = Properties
            .create(Material.IRON)
            .hardnessAndResistance(5F, 6F)
            .harvestTool(ToolType.PICKAXE).harvestLevel(1)
            .sound(SoundType.METAL)
            .setRequiresTool();

    public static final Properties STONE = Properties
            .create(Material.ROCK)
            .hardnessAndResistance(1.5F, 6F)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(1)
            .sound(SoundType.STONE)
            .setRequiresTool();

    public static final Properties STONE_BUTTON = Properties.create(Material.MISCELLANEOUS)
            .doesNotBlockMovement()
            .hardnessAndResistance(0.5F)
            .sound(SoundType.STONE);

    public static Properties ore(int miningLevel) {
        return Properties
                .create(Material.ROCK)
                .hardnessAndResistance(3F, 3F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(miningLevel)
                .sound(SoundType.STONE)
                .setRequiresTool();
    }
}
