package permafrozen.block.hertzstone;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import permafrozen.util.PermafrozenButton;

public class PolishedHertzstoneButton extends PermafrozenButton {

    public PolishedHertzstoneButton() {

        super(false, Block.Properties.create(Material.MISCELLANEOUS)
                .doesNotBlockMovement()
                .hardnessAndResistance(0.5F)
                .sound(SoundType.STONE)
        );

    }

}
