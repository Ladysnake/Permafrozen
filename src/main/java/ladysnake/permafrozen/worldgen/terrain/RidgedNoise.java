package ladysnake.permafrozen.worldgen.terrain;

import java.util.Random;

public class RidgedNoise extends OpenSimplexNoise {
	public RidgedNoise(Random rand) {
		super(rand);
	}

	@Override
	public double sample(double x) {
		return 1 - Math.abs(super.sample(x)) * 2;
	}

	@Override
	public double sample(double x, double y) {
		return 1 - Math.abs(super.sample(x, y)) * 2;
	}
}