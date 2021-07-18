package net.permafrozen.permafrozen.registry;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.worldgen.biome.PermafrozenBiomeSource;
import net.permafrozen.permafrozen.worldgen.feature.PermafrozenBiomeFeatures;

public class PermafrozenBiomes {
	public static final Biome BOREAL_FOREST = createBoreal();
	public static final RegistryKey<Biome> BOREAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "boreal_forest"));
	
	private static Biome createBoreal() {
		// We specify what entities spawn and what features generate in the biome.
		// Aside from some structures, trees, rocks, plants and
		//   custom entities, these are mostly the same for each biome.
		// Vanilla configured features for biomes are defined in DefaultBiomeFeatures.
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		DefaultBiomeFeatures.addSweetBerryBushesSnowy(generationSettings);
		DefaultBiomeFeatures.addForestGrass(generationSettings);
		DefaultBiomeFeatures.addMossyRocks(generationSettings);
		PermafrozenBiomeFeatures.addFirTrees(generationSettings);
		PermafrozenBiomeFeatures.addSpectralCaps(generationSettings);
		DefaultBiomeFeatures.addLargeFerns(generationSettings);
		DefaultBiomeFeatures.addDefaultLakes(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(0.1F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.6F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
	}
	
	private static int getSkyColor(float temperature) {
		float f = temperature / 3.0F;
		f = MathHelper.clamp(f, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
	}
	
	public static void init() {
		Registry.register(BuiltinRegistries.BIOME, BOREAL_FOREST_KEY.getValue(), BOREAL_FOREST);
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Permafrozen.MOD_ID, "biome_source"), PermafrozenBiomeSource.CODEC);
	}
}
