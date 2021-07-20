package net.permafrozen.permafrozen.worldgen.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.mixin.LayerAccessor;
import net.permafrozen.permafrozen.registry.PermafrozenBiomes;

import java.util.List;
import java.util.function.LongFunction;

public class PermafrozenBiomeSource extends BiomeSource {
	public static final Codec<PermafrozenBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.LONG.fieldOf("seed").stable().forGetter((permafrozenBiomeSource) -> permafrozenBiomeSource.seed), RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((permafrozenBiomeSource) -> permafrozenBiomeSource.biomeRegistry)).apply(instance, instance.stable(PermafrozenBiomeSource::new)));
	private final BiomeLayerSampler biomeSampler;
	private static final List<RegistryKey<Biome>> PERMAFROZEN_BIOMES;
	private final long seed;
	private final Registry<Biome> biomeRegistry;
	
	public PermafrozenBiomeSource(long seed, Registry<Biome> biomeRegistry) {
		super(PERMAFROZEN_BIOMES.stream().map((key) -> () -> (Biome) biomeRegistry.getOrThrow(key)));
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;
		this.biomeSampler = buildWorldProcedure(seed, 3, biomeRegistry);
	}
	
	public static BiomeLayerSampler buildWorldProcedure(long seed, int biomeSize, Registry<Biome> biomeRegistry) {
		LayerFactory<CachingLayerSampler> layerFactory = build((salt) -> new CachingLayerContext(25, seed, salt), biomeSize, seed, biomeRegistry);
		return new BiomeLayerSampler(layerFactory);
	}
	
	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}
	
	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		int resultBiomeID = ((LayerAccessor) this.biomeSampler).getSampler().sample(biomeX, biomeZ);
		Biome biome = biomeRegistry.get(resultBiomeID);
		if (biome == null) {
			if (SharedConstants.isDevelopment) {
				throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + resultBiomeID));
			}
			else {
				RegistryKey<Biome> backupBiomeKey = BiomeKeys.TAIGA;
				return biomeRegistry.get(backupBiomeKey);
			}
		}
		else {
			return biome;
		}
	}
	
	public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LongFunction<C> contextFactory, int biomeSize, long seed, Registry<Biome> biomeRegistry) {
		LayerFactory<T> layer = (new PermafrozenBiomeLayer(seed, biomeRegistry)).create(contextFactory.apply(200L));
		for (int currentExtraZoom = 0; currentExtraZoom < biomeSize; currentExtraZoom++) {
			if ((currentExtraZoom + 2) % 3 != 0) {
				layer = ScaleLayer.NORMAL.create(contextFactory.apply(2001L + currentExtraZoom), layer);
			}
			else {
				layer = ScaleLayer.FUZZY.create(contextFactory.apply(2000L + (currentExtraZoom * 31L)), layer);
			}
		}
		return layer;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = parent;
		for (int i = 0; i < count; ++i) {
			layerFactory = layer.create(contextProvider.apply(seed + (long) i), layerFactory);
		}
		return layerFactory;
	}
	
	static {
		PERMAFROZEN_BIOMES = ImmutableList.of(PermafrozenBiomes.BOREAL_FOREST_KEY);
	}
	
	public static final Identifier BOREAS = new Identifier(Permafrozen.MOD_ID, "boreal_forest");
	public static final Identifier GLACIAS = new Identifier(Permafrozen.MOD_ID, "glacial_ocean");
	
	@Override
	@Environment(EnvType.CLIENT)
	public BiomeSource withSeed(long seed) {
		return new PermafrozenBiomeSource(seed, this.biomeRegistry);
	}
}
