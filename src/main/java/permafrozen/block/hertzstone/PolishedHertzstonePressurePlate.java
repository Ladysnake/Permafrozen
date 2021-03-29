package permafrozen.block.hertzstone;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class PolishedHertzstonePressurePlate extends PressurePlateBlock {

    public PolishedHertzstonePressurePlate() {

        super(Sensitivity.EVERYTHING, Block.Properties.create(Material.MISCELLANEOUS)
                .doesNotBlockMovement()
                .hardnessAndResistance(0.5F)
                .sound(SoundType.STONE)
                .setRequiresTool()
        );

    }

}
