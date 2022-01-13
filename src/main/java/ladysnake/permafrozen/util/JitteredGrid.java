package ladysnake.permafrozen.util;

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