package ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ForestRockFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class ShiverslateRockFeature extends Feature<DefaultFeatureConfig> {
    public ShiverslateRockFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockState blockState;
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        while (blockPos.getY() > structureWorldAccess.getBottomY() + 3 && (structureWorldAccess.isAir(blockPos.down()) || !ForestRockFeature.isSoil(blockState = structureWorldAccess.getBlockState(blockPos.down())) && !ForestRockFeature.isStone(blockState))) {
            blockPos = blockPos.down();
        }
        if (blockPos.getY() <= structureWorldAccess.getBottomY() + 3) {
            return false;
        }
        for (int blockState2 = 0; blockState2 < 3; ++blockState2) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);
            int k = random.nextInt(4);
            float f = (float)(i + j + k) * 0.333f + 0.5f;
            for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -j, -k), blockPos.add(i, j, k))) {
                if (!(blockPos2.getSquaredDistance(blockPos) <= (double)(f * f))) continue;
                structureWorldAccess.setBlockState(blockPos2, structureWorldAccess.getBiome(blockPos2).getCategory() == Biome.Category.FOREST ? PermafrozenBlocks.MOSSY_COBBLED_SHIVERSLATE.getDefaultState() : PermafrozenBlocks.COBBLED_SHIVERSLATE.getDefaultState(), Block.NO_REDRAW);
            }
            blockPos = blockPos.add(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
        }
        return true;
    }
}
