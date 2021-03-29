package permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class CobaltOre extends Block {

    public CobaltOre() {

        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(3F, 3F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .sound(SoundType.STONE)
                .setRequiresTool()
        );

    }

}
