package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class TundraTerrain extends Terrain {
	protected TundraTerrain(Biome biome, Random random, boolean usePingos) {
		super(biome);
		this.pingosNoise = usePingos ? new OpenSimplexNoise(random) : null;
		this.localHillsNoise = new OpenSimplexNoise(random);
	}

	private final OpenSimplexNoise pingosNoise; // https://sciencing.com/landforms-tundra-7575771.html
	private final OpenSimplexNoise localHillsNoise;

	@Override
	public double sampleHeight(int x, int z) {
		double pingo = this.pingosNoise == null ? 0 : 30 * MathHelper.clamp(2 * (this.pingosNoise.sample(x * 0.0022, z * 0.0022) - 0.5), 0, 1);
		return pingo + this.localHillsNoise.sample(x * 0.02, z * 0.02) * 2 + 64;
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		buildDefaultSurface(chunk, x, z, height, seaLevel, PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState());
	}
}
