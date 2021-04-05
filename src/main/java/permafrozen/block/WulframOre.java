package permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class WulframOre extends Block {

    public WulframOre() {

        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(3F, 3F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.STONE)
                .setRequiresTool()
        );

    }

}
