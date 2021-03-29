package permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import permafrozen.Permafrozen;

public class CobaltBlock extends Block {

    public CobaltBlock() {

        super(Properties
                .create(Material.IRON)
                .hardnessAndResistance(5F, 6F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.METAL)
        );

    }

}
