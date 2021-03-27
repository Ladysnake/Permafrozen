package permafrozen.block;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import permafrozen.Permafrozen;

public class FrozenDebris extends RotatedPillarBlock {

    public FrozenDebris() {

        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(30F, 12000F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(3)
                .sound(SoundType.ANCIENT_DEBRIS)
        );

        setRegistryName(Permafrozen.MODID, "frozen_debris");

    }
}
