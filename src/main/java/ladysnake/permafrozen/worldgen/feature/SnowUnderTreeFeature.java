package ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import ladysnake.permafrozen.block.StrippableDeadwoodBlock;
import ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SnowyBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.EnumSet;
import java.util.Set;

public class SnowUnderTreeFeature extends Feature<DefaultFeatureConfig> {
    public SnowUnderTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos pos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        BlockPos.Mutable mPos = new BlockPos.Mutable();

        for(int xi = 0; xi < 16; xi++) {
            for(int zi = 0; zi < 16; zi++) {
                int x = pos.getX() + xi;
                int z = pos.getZ() + zi;

                mPos.set(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z) - 1, z);

                if(world.getBlockState(mPos).getBlock() instanceof LeavesBlock || world.getBlockState(mPos).getBlock() instanceof StrippableDeadwoodBlock || world.getBlockState(mPos).getBlock().equals(PermafrozenBlocks.SPIRESHROOM_LOG) || world.getBlockState(mPos).getBlock().equals(PermafrozenBlocks.SPIRESHROOM_WOOD)) {
                    mPos.set(x, getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z, world) + 1, z);

                    //if(world.getBiome(mPos)) {
                        BlockState stateBelow;

                        world.setBlockState(mPos, Blocks.SNOW.getDefaultState(), 2);
                        mPos.move(Direction.DOWN);
                        stateBelow = world.getBlockState(mPos);

                        if(stateBelow.contains(SnowyBlock.SNOWY))
                            world.setBlockState(mPos, stateBelow.with(SnowyBlock.SNOWY, true), 2);
                    //}
                }
            }
        }
        return true;
    }
    public int getTopY(Heightmap.Type heightmap, int x, int z, ServerWorldAccess world) {
        return (x < -30000000 || z < -30000000 || x >= 30000000 || z >= 30000000) ? world.getSeaLevel() + 1 : (world.isChunkLoaded(ChunkSectionPos.getSectionCoord(x), ChunkSectionPos.getSectionCoord(z)) ? sampleChunkHeightmap(world.getChunk(ChunkSectionPos.getSectionCoord(x), ChunkSectionPos.getSectionCoord(z)), heightmap, x, z) : world.getBottomY());
    }
    private int sampleChunkHeightmap(Chunk chunk, Heightmap.Type type, int x, int z) {
        Heightmap heightmap = chunk.getHeightmap(type);
        if (heightmap == null) {
            customPopulateHeightmaps(chunk, EnumSet.of(type));
            heightmap = chunk.getHeightmap(type);
        }
        return heightmap.get(x & 0xF, z & 0xF) - 1;
    }
    private void customPopulateHeightmaps(Chunk chunk, Set<Heightmap.Type> types) {
        int i = types.size();
        ObjectArrayList objectList = new ObjectArrayList(i);
        ObjectListIterator objectListIterator = objectList.iterator();
        int j = chunk.getHighestNonEmptySectionYOffset() + 16;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int k = 0; k < 16; ++k) {
            block1: for (int l = 0; l < 16; ++l) {
                for (Heightmap.Type type : types) {
                    objectList.add(chunk.getHeightmap(type));
                }
                for (int m = j - 1; m >= chunk.getBottomY(); --m) {
                    mutable.set(k, m, l);
                    BlockState blockState = chunk.getBlockState(mutable);
                    if (blockState.isOf(Blocks.AIR)) continue;
                    while (objectListIterator.hasNext()) {
                        Heightmap heightmap = (Heightmap)objectListIterator.next();
                        if (!heightmap.blockPredicate.test(blockState) || (blockState.getBlock() instanceof StrippableDeadwoodBlock)|| blockState.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_LOG) || blockState.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_WOOD) || blockState.getBlock().equals(PermafrozenBlocks.SPECTRAL_CAP_BLOCK)) continue;
                        heightmap.set(k, l, m + 1);
                        objectListIterator.remove();
                    }
                    if (objectList.isEmpty()) continue block1;
                    objectListIterator.back(i);
                }
            }
        }
    }
}
