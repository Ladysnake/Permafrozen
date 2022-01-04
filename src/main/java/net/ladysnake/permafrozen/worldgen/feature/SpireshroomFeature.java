package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class SpireshroomFeature extends Feature<DefaultFeatureConfig> {
    public SpireshroomFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos.Mutable pos = new BlockPos.Mutable().set(context.getOrigin());

        // don't generate on ice
        if (world.getBlockState(pos.down()).isOf(Blocks.ICE)) {
        	return false;
        }

        int heightGenerated = 0;
        Random random = context.getRandom();
        BlockState spireshroom = PermafrozenBlocks.SPIRESHROOM_LOG.getDefaultState();
        BlockState spireshroomTop = PermafrozenBlocks.SPIRESHROOM_WOOD.getDefaultState();

        int height = 7 + random.nextInt(18);
	    boolean thick = random.nextInt(9) == 0;
        int bendHeight = (!thick && height > 7) ? height/3 + random.nextInt(2) : 100; // arbitrary high number otherwise

	    if (thick) {
	    	height *= 1.8;
	    }

        for (int y = 0; y <= height; ++y) {
            if (world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos).isOf(Blocks.AIR)) {
            	if (thick) {
            	    BlockState toSet = y == height ? spireshroomTop : spireshroom;
		            world.setBlockState(pos, toSet, Block.NOTIFY_LISTENERS);
		            pos.move(Direction.NORTH);
		            world.setBlockState(pos, toSet, Block.NOTIFY_LISTENERS);
		            pos.move(Direction.EAST);
		            world.setBlockState(pos, toSet, Block.NOTIFY_LISTENERS);
		            pos.move(Direction.SOUTH);
		            world.setBlockState(pos, toSet, Block.NOTIFY_LISTENERS);
		            pos.move(Direction.WEST);
	            } else {
		            // normal gen
		            if (y == height) {
			            world.setBlockState(pos, spireshroomTop, Block.NOTIFY_LISTENERS);
			            ++heightGenerated;
		            } else if (y == bendHeight) { // the bend, without having a weird diagonal jump
			            world.setBlockState(pos, spireshroomTop, Block.NOTIFY_LISTENERS);
			            pos.move(Direction.fromHorizontal(random.nextInt(4)));
			            world.setBlockState(pos, spireshroomTop, Block.NOTIFY_LISTENERS);
		            } else {
			            world.setBlockState(pos, spireshroom, Block.NOTIFY_LISTENERS);
		            }
	            }
            } else if (y > 0) {
            	// if it runs out of room just cap it
                BlockPos blockPos3 = pos.down();
                if (!spireshroom.canPlaceAt(world, blockPos3) || world.getBlockState(blockPos3.down()).isOf(PermafrozenBlocks.SPIRESHROOM_LOG)) break;
                world.setBlockState(blockPos3, spireshroomTop, Block.NOTIFY_LISTENERS);
                ++heightGenerated;
                break;
            }

            pos.move(Direction.UP);
        }
        return heightGenerated > 0;
    }
}
