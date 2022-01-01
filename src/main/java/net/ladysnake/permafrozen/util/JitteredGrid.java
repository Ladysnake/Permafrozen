package net.ladysnake.permafrozen.util;

import net.minecraft.util.math.MathHelper;

public final class JitteredGrid {
	/**
	 * Samples a centre position from the jittered grid.
	 * @param x the x position on the grid
	 * @param y the y position on the grid
	 * @param seed the seed to use for the jittered grid random algorithm
	 * @param gridAlignment how much to align the points to the grid (from 0.0f=none to 1.0f=on grid)
	 * @return the centre position for this cell in the grid.
	 */
	public static GridPoint sampleJitteredGrid(int x, int y, int seed, double gridAlignment) {
		double unGridAlignment = 1.0f - gridAlignment;
		double vx = x + gridAlignment * 0.5f + unGridAlignment * randomdouble(x, y, seed);
		double vy = y + gridAlignment * 0.5f + unGridAlignment * randomdouble(x, y, seed + 1);
		return new GridPoint(vx, vy);
	}

	/**
	 * Samples worley noise on the jittered grid.
	 * @param seed the seed to use for the jittered grid random algorithm
	 * @return the sampled worley value.
	 */
	public static double sampleD1D2SquaredWorley(double x, double y, int seed) {
		final int baseX = MathHelper.floor(x);
		final int baseY = MathHelper.floor(y);
		double rdist2 = 1000;
		double rdist = 1000;

		for (int xo = -1; xo <= 1; ++xo) {
			int gridX = baseX + xo;

			for (int yo = -1; yo <= 1; ++yo) {
				int gridY = baseY + yo;

				double vx = gridX + randomdouble(gridX, gridY, seed);
				double vy = gridY + randomdouble(gridX, gridY, seed + 1);
				double vdist = squaredDist(x, y, vx, vy);

				if (vdist < rdist) {
					rdist2 = rdist;
					rdist = vdist;
				} else if (vdist < rdist2) {
					rdist2 = vdist;
				}
			}
		}

		return rdist2 - rdist;
	}

	private static double squaredDist(double x0, double y0, double x1, double y1) {
		double dx = x1 - x0;
		double dy = y1 - y0;
		return dx * dx + dy * dy;
	}

	public static double randomdouble(int x, int y, int seed) {
		return (double) random(x, y, seed, 0xFFFF) / (double) 0xFFFF;
	}

	private static int random(int x, int y, int seed, int mask) {
		seed *= 375462423 * seed + 672456235;
		seed += x;
		seed *= 375462423 * seed + 672456235;
		seed += y;
		seed *= 375462423 * seed + 672456235;
		seed += x;
		seed *= 375462423 * seed + 672456235;
		seed += y;

		return seed & mask;
	}
}