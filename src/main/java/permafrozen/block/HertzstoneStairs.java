package permafrozen.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import permafrozen.Permafrozen;

import java.util.function.Supplier;

import static net.minecraft.state.properties.BlockStateProperties.POWERED;

public class HertzstoneStairs extends StairsBlock {

    public HertzstoneStairs(Supplier<BlockState> state) {

        super(state, Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.STONE)
        );

        setRegistryName(Permafrozen.MODID, "hertzstone_stairs");

    }

}
