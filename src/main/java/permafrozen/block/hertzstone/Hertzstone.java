package permafrozen.block.hertzstone;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class Hertzstone extends Block {

    public Hertzstone() {

        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.STONE)
                .setRequiresTool()
        );

    }

}