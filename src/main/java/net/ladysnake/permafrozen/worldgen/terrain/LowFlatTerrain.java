package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.util.GridPoint;
import net.ladysnake.permafrozen.util.JitteredGrid;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class LowFlatTerrain extends Terrain {
	protected LowFlatTerrain(Biome biome, Random random, boolean usePingos) {
		super(biome);
		this.pingosNoise = usePingos ? new OpenSimplexNoise(random) : null;
		this.localHillsNoise = new OpenSimplexNoise(random);
		this.pingoJitteredGridSeed = random.nextInt();
	}

	// https://en.wikipedia.org/wiki/Pingo#/media/File:Closed_pingos_diagram.jpg Could later add water or ice to make it more realistic
	private final OpenSimplexNoise pingosNoise; // https://sciencing.com/landforms-tundra-7575771.html
	private final OpenSimplexNoise localHillsNoise;
	private final int pingoJitteredGridSeed;

	@Override
	public double sampleHeight(int x, int z) {
		double pingo = 0;

		if (this.pingosNoise != null) {
			pingo = clampMap(this.pingosNoise.sample(x * 0.002, z * 0.002), -0.014, 0.014, 0.0, 1.0);

			if (pingo > 0) {
				int gridPointX = x >> 6;
				int gridPointZ = z >> 6;
				double gridPointTrueX = (double) x / 64.0;
				double gridPointTrueZ = (double) z / 64.0;
				double pingoHeight = 0;

				// sample potentially nearby points on the jittered grid
				for (int dx = -1; dx <= 1; ++dx) {
					for (int dz = -1; dz <= 1; ++dz) {
						double value = 0.2 - JitteredGrid.sampleJitteredGrid(dx + gridPointX, dz + gridPointZ, this.pingoJitteredGridSeed, 0.3).centreSqrDist(gridPointTrueX, gridPointTrueZ);
						value = 18 * 5 * MathHelper.clamp(value, 0.0, 0.2);

						if (value > pingoHeight) {
							pingoHeight = value;
						}
					}
				}

				pingo *= pingoHeight;
			}
		}

		return pingo + this.localHillsNoise.sample(x * 0.033, z * 0.033) * 2 + 63.5;
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		buildDefaultSurface(chunk, x, z, height, seaLevel, PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState());
	}
}
