package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.util.JitteredGrid;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class ChillingCanyonsTerrain extends Terrain {
	protected ChillingCanyonsTerrain(Biome biome, Random random) {
		super(biome);
		this.topHillsNoise = new OpenSimplexNoise(random);
		this.bottomHillsNoise = new OpenSimplexNoise(random);
		this.canyonsNoise = new RidgedNoise(random);
	}

	// https://en.wikipedia.org/wiki/Pingo#/media/File:Closed_pingos_diagram.jpg Could later add water or ice to make it more realistic
	private final OpenSimplexNoise topHillsNoise;
	private final OpenSimplexNoise bottomHillsNoise;
	private final RidgedNoise canyonsNoise;

	@Override
	public double sampleHeight(int x, int z) {
		double bottomSample = 3 * this.bottomHillsNoise.sample(x * 0.03, z * 0.03) + 6 * this.bottomHillsNoise.sample(x * 0.008, z * 0.008) + 75;
		double topSample = this.topHillsNoise.sample(x * 0.01, z * 0.01) * 4 + 150;
		double canyonsSample = this.canyonsNoise.sample(x * 0.014, z * 0.014);
		return clampMap(canyonsSample, 0.9, 1.0, bottomSample, topSample);
	}

	@Override
	public double modifyWeight(double original, double maxWeight) {
		return clampMap(original, maxWeight / 2, maxWeight, 0, maxWeight * 2);
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		if (height > 100) {
			BlockPos.Mutable pos = new BlockPos.Mutable();

			for (int y = chunk.getTopY(); y >= chunk.getBottomY(); --y) { // still doing the CC compat stuff by using chunk heights.
				pos.set(x, y, z);

				if (y >= height) {
					chunk.setBlockState(pos, PermafrozenBlocks.SHIVERSLATE.getDefaultState(), false);
				}
			}
		} else {
			buildDefaultSurface(chunk, x, z, height, seaLevel, PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState());
		}
	}
}
