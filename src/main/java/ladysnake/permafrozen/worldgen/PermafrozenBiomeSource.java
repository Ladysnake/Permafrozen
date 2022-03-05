package ladysnake.permafrozen.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import ladysnake.permafrozen.worldgen.terrain.TerrainSampler;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.List;

public class PermafrozenBiomeSource extends BiomeSource {
	public static final Codec<PermafrozenBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
							RegistryOps.createRegistryCodec(Registry.BIOME_KEY).forGetter(s -> s.biomeRegistry),
							Codec.LONG.fieldOf("seed").stable().forGetter(s -> s.seed))
					.apply(instance, instance.stable(PermafrozenBiomeSource::new)));

	public PermafrozenBiomeSource(Registry<Biome> biomeRegistry, long seed) {
		super(List.of(
				RegistryEntry.of(biomeRegistry.getOrThrow(PermafrozenBiomes.TUNDRA)),
				RegistryEntry.of(biomeRegistry.getOrThrow(PermafrozenBiomes.SHRUMAL_SPIRES)),
				RegistryEntry.of(biomeRegistry.getOrThrow(PermafrozenBiomes.FRIGID_FEN)),
				RegistryEntry.of(biomeRegistry.getOrThrow(PermafrozenBiomes.CHILLING_CANYON))
		));

		this.biomeRegistry = biomeRegistry;
		this.seed = seed;
		this.terrainSampler = new TerrainSampler(biomeRegistry, seed);
	}

	private final Registry<Biome> biomeRegistry;
	private final long seed;
	final TerrainSampler terrainSampler;

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new PermafrozenBiomeSource(this.biomeRegistry, seed);
	}

	@Override
	public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
		return RegistryEntry.of(this.terrainSampler.sample(BiomeCoords.toBlock(x), BiomeCoords.toBlock(z)).getBiome());
	}
}
