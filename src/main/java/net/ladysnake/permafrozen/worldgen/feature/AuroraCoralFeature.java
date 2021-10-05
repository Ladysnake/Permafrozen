package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;

import java.util.Iterator;
import java.util.Random;

public class AuroraCoralFeature extends Feature<DefaultFeatureConfig> {
    public AuroraCoralFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockState blockState = PermafrozenBlocks.PRISMATIC_CORAL_BLOCK.getDefaultState();
        return this.generateCoral(structureWorldAccess, random, blockPos, blockState);
    }

    protected boolean generateCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        int i = random.nextInt(3) + 3;
        int j = random.nextInt(3) + 3;
        int k = random.nextInt(3) + 3;
        int l = random.nextInt(3) + 1;
        BlockPos.Mutable mutable = pos.mutableCopy();

        for(int m = 0; m <= j; ++m) {
            for(int n = 0; n <= i; ++n) {
                for(int o = 0; o <= k; ++o) {
                    mutable.set(m + pos.getX(), n + pos.getY(), o + pos.getZ());
                    mutable.move(Direction.DOWN, l);
                    if ((m != 0 && m != j || n != 0 && n != i) && (o != 0 && o != k || n != 0 && n != i) && (m != 0 && m != j || o != 0 && o != k) && (m == 0 || m == j || n == 0 || n == i || o == 0 || o == k) && !(random.nextFloat() < 0.1F) && !this.generateCoralPiece(world, random, mutable, state)) {
                    }
                }
            }
        }

        return true;
    }
    protected boolean generateCoralPiece(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(pos);
        if ((blockState.isOf(Blocks.WATER) || blockState.isOf(PermafrozenBlocks.PRISMATIC_CORAL_BLOCK)) && world.getBlockState(blockPos).isOf(Blocks.WATER)) {
            world.setBlockState(pos, state, 3);
            if (random.nextFloat() < 0.25F) {
              //  world.setBlockState(blockPos, ((Block)BlockTags.CORALS.getRandom(random)).getDefaultState(), 2);
            } else if (random.nextFloat() < 0.05F) {
             //   world.setBlockState(blockPos, (BlockState)Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, random.nextInt(4) + 1), 2);
            }

            Iterator var7 = Direction.Type.HORIZONTAL.iterator();

            while(var7.hasNext()) {
                Direction direction = (Direction)var7.next();
                if (random.nextFloat() < 0.2F) {
                    BlockPos blockPos2 = pos.offset(direction);
                    if (world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                        BlockState blockState2 = ((Block)BlockTags.WALL_CORALS.getRandom(random)).getDefaultState();
                      //  if (blockState2.contains(DeadCoralWallFanBlock.FACING)) {
                           // blockState2 = (BlockState)blockState2.with(DeadCoralWallFanBlock.FACING, direction);
                      //  }

                       // world.setBlockState(blockPos2, blockState2, 2);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
