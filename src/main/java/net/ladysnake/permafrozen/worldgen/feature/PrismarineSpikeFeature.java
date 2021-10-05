package net.ladysnake.permafrozen.worldgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static net.ladysnake.permafrozen.block.PrismarineCrystalClusterBlock.FACING;
import static net.ladysnake.permafrozen.block.PrismarineCrystalClusterBlock.WATERLOGGED;

public class PrismarineSpikeFeature extends Feature<DefaultFeatureConfig> {
    public PrismarineSpikeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockState blockState = PermafrozenBlocks.BUDDING_PRISMARINE.getDefaultState();
        return this.generateSpike(structureWorldAccess, random, blockPos, blockState);
    }

    protected boolean generateSpike(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        if (random.nextBoolean()) {
            BlockPos.Mutable mutable = pos.mutableCopy();
            int i = random.nextInt(3) + 1;

            for(int j = 0; j < i; ++j) {
                if (!this.generatePiece(world, random, mutable, state)) {
                    return true;
                }

                mutable.move(Direction.UP);
            }

            BlockPos blockPos = mutable.toImmutable();
            int k = random.nextInt(3) + 2;
            List<Direction> list = Lists.newArrayList(Direction.Type.HORIZONTAL);
            Collections.shuffle(list, random);
            List<Direction> list2 = list.subList(0, k);
            Iterator var11 = list2.iterator();

            while(var11.hasNext()) {
                Direction direction = (Direction)var11.next();
                mutable.set(blockPos);
                mutable.move(direction);
                int l = random.nextInt(5) + 2;
                int m = 0;

                for(int n = 0; n < l && this.generatePiece(world, random, mutable, state); ++n) {
                    ++m;
                    mutable.move(Direction.UP);
                    if (n == 0 || m >= 2 && random.nextFloat() < 0.25F) {
                        mutable.move(direction);
                        m = 0;
                    }
                }
            }

            return true;
        }
        else {
            if (!this.generatePiece(world, random, pos, state)) {
                return false;
            } else {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                int i = random.nextInt(2) + 2;
                List<Direction> list = Lists.newArrayList(new Direction[]{direction, direction.rotateYClockwise(), direction.rotateYCounterclockwise()});
                Collections.shuffle(list, random);
                List<Direction> list2 = list.subList(0, i);
                Iterator var9 = list2.iterator();

                while (var9.hasNext()) {
                    Direction direction2 = (Direction) var9.next();
                    BlockPos.Mutable mutable = pos.mutableCopy();
                    int j = random.nextInt(2) + 1;
                    mutable.move(direction2);
                    int l;
                    Direction direction4;
                    if (direction2 == direction) {
                        direction4 = direction;
                        l = random.nextInt(3) + 2;
                    } else {
                        mutable.move(Direction.UP);
                        Direction[] directions = new Direction[]{direction2, Direction.UP};
                        direction4 = (Direction) Util.getRandom(directions, random);
                        l = random.nextInt(3) + 3;
                    }

                    int n;
                    for (n = 0; n < j && this.generatePiece(world, random, mutable, state); ++n) {
                        mutable.move(direction4);
                    }

                    mutable.move(direction4.getOpposite());
                    mutable.move(Direction.UP);

                    for (n = 0; n < l; ++n) {
                        mutable.move(direction);
                        if (!this.generatePiece(world, random, mutable, state)) {
                            break;
                        }

                        if (random.nextFloat() < 0.25F) {
                            mutable.move(Direction.UP);
                        }
                    }
                }

                return true;
            }
        }
    }
    protected boolean generatePiece(WorldAccess world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(pos);

        if ((blockState.isOf(Blocks.WATER) || blockState.isOf(PermafrozenBlocks.BUDDING_PRISMARINE) || blockState.isOf(PermafrozenBlocks.SMALL_PRISMARINE_BUD) || blockState.isOf(PermafrozenBlocks.MEDIUM_PRISMARINE_BUD) || blockState.isOf(PermafrozenBlocks.LARGE_PRISMARINE_BUD) || blockState.isOf(PermafrozenBlocks.PRISMARINE_CLUSTER)) && world.getBlockState(blockPos).isOf(Blocks.WATER)) {
            world.setBlockState(pos, state, 3);
            if (random.nextFloat() < 0.25F) {
                world.setBlockState(blockPos, (PermafrozenBlocks.PRISMARINE_CLUSTER).getDefaultState().with(WATERLOGGED, true), 2);
            } else if (random.nextFloat() < 0.05F) {
                world.setBlockState(blockPos, (PermafrozenBlocks.LARGE_PRISMARINE_BUD).getDefaultState().with(WATERLOGGED, true), 2);
            }

            Iterator var7 = Direction.Type.HORIZONTAL.iterator();

            while(var7.hasNext()) {
                Direction direction = (Direction)var7.next();
                if (random.nextFloat() < 0.2F) {
                    BlockPos blockPos2 = pos.offset(direction);
                    if (world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                        BlockState blockState2 = (PermafrozenBlocks.LARGE_PRISMARINE_BUD).getDefaultState().with(WATERLOGGED, true).with(FACING, direction);

                        world.setBlockState(blockPos2, blockState2, 2);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
