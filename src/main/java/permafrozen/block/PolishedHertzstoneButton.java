package permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenButton;

public class PolishedHertzstoneButton extends PermafrozenButton {

    public PolishedHertzstoneButton() {

        super(false, Block.Properties.create(Material.MISCELLANEOUS)
                .doesNotBlockMovement()
                .hardnessAndResistance(0.5F)
                .sound(SoundType.STONE)
        );

        setRegistryName(Permafrozen.MODID, "polished_hertzstone_button");

    }

}
