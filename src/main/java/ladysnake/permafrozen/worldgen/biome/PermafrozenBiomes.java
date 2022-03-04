package ladysnake.permafrozen.worldgen.biome;

import ladysnake.permafrozen.entity.living.LunarKoiEntity;
import ladysnake.permafrozen.entity.living.NudifaeEntity;
import ladysnake.permafrozen.registry.PermafrozenSoundEvents;
import ladysnake.permafrozen.worldgen.PermafrozenChunkGenerator;
import ladysnake.permafrozen.worldgen.PermafrozenPlacedFeatures;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import ladysnake.permafrozen.worldgen.PermafrozenBiomeSource;
import ladysnake.permafrozen.worldgen.feature.SnowUnderTreeFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.List;

public class PermafrozenBiomes extends OverworldBiomeCreator {
	private static final Feature<DefaultFeatureConfig> SNOWY_TREES_FEATURE = new SnowUnderTreeFeature(DefaultFeatureConfig.CODEC);
	public static final ConfiguredFeature<?, ?> SNOWY_TREES_CONFIGURED = SNOWY_TREES_FEATURE.configure(FeatureConfig.DEFAULT);

	private static List<Identifier> biomesToAddTo = new ArrayList<>();
	private static final int DEFAULT_PERMAROZEN_FOG_COLOUR = 0xEFFFFF; // change this to whatever you want. Overworld is 0xC0D8FF

	public static final RegistryKey<Biome> TUNDRA = createKey("tundra");
	public static final RegistryKey<Biome> SHRUMAL_SPIRES = createKey("shrumal_spires");
	public static final RegistryKey<Biome> FRIGID_FEN = createKey("frigid_fen");
	public static final RegistryKey<Biome> CHILLING_CANYON = createKey("chilling_canyon");
	public static final RegistryKey<Biome> GLACIER_OCEAN = createKey("glacier_ocean");
	public static final RegistryKey<Biome> HOTSPRINGS = createKey("hotsprings");

	public static void init() {
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(Permafrozen.MOD_ID, "permafrozen"), PermafrozenChunkGenerator.CODEC);
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Permafrozen.MOD_ID, "permafrozen"), PermafrozenBiomeSource.CODEC);

		registerBiome(TUNDRA, createTundra());
		registerBiome(SHRUMAL_SPIRES, createShrumalSpires());
		registerBiome(FRIGID_FEN, createFrigidFen());
		registerBiome(CHILLING_CANYON, createChillingCanyon());

		Registry.register(Registry.FEATURE, new Identifier("permafrozen", "snowundertrees"), SNOWY_TREES_FEATURE);
		RegistryKey<ConfiguredFeature<?, ?>> snowUnderTrees = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("permafrozen", "snowundertrees"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, snowUnderTrees.getValue(), SNOWY_TREES_CONFIGURED);
		PlacedFeature SNOWY_TREES_PLACED_FEATURE = SNOWY_TREES_CONFIGURED.withPlacement(BiomePlacementModifier.of());
		RegistryKey<PlacedFeature> placedSnowUnderTrees = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("permafrozen", "snowundertrees"));
		Registry.register(BuiltinRegistries.PLACED_FEATURE, snowUnderTrees.getValue(), SNOWY_TREES_PLACED_FEATURE);

		BiomeModifications.addFeature(b -> shouldAddSnow(b.getBiome()), GenerationStep.Feature.TOP_LAYER_MODIFICATION, placedSnowUnderTrees);
		SpawnRestriction.register(PermafrozenEntities.LUNAR_KOI, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LunarKoiEntity::canSpawn);
		SpawnRestriction.register(PermafrozenEntities.NUDIFAE, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NudifaeEntity::canSpawn);
	}
	private static boolean shouldAddSnow(Biome biome) {
		return biome.getPrecipitation() == Biome.Precipitation.SNOW;
	}

	public static void addSnowUnderTrees(Biome biome) {
		Identifier id = BuiltinRegistries.BIOME.getId(biome);
		if (!biomesToAddTo.contains(id))
			biomesToAddTo.add(id);
	}

	private static Biome createTundra() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();

		return createPermafrozenBiome(
				Biome.Category.PLAINS,
				generationSettings,
				new SpawnSettings.Builder()
						.creatureSpawnProbability(0.07f) // default is 0.1f, snowy plains uses 0.07f
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.NUDIFAE, 1, 1, 3))
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.LUNAR_KOI, 1, 1, 3))
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 10, 5, 7))
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 3))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}

	private static Biome createShrumalSpires() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.SHRUMAL_SPIRES_VEGETATION)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.GLAUCA_PATCHES)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.SPECTRAL_CAP_PATCHES)
				.feature(GenerationStep.Feature.SURFACE_STRUCTURES, PermafrozenPlacedFeatures.SHIVERSLATE_ROCK);

		return createPermafrozenBiome(
				Biome.Category.FOREST,
				generationSettings,
				new SpawnSettings.Builder()
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.NUDIFAE, 1, 1, 3))
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.LUNAR_KOI, 1, 1, 3))
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.AURORA_FAE, 2, 1, 2))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}


	private static Biome createFrigidFen() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.FRIGID_FEN_VEGETATION)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.GLAUCA_PATCHES)
				.feature(GenerationStep.Feature.SURFACE_STRUCTURES, PermafrozenPlacedFeatures.SHIVERSLATE_ROCK);

		return createPermafrozenBiome(
				Biome.Category.SWAMP,
				generationSettings,
				new SpawnSettings.Builder()
						//.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 100, 2, 4))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}

	private static Biome createChillingCanyon() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();

		return createPermafrozenBiome(
				Biome.Category.MOUNTAIN,
				generationSettings,
				new SpawnSettings.Builder()
						.creatureSpawnProbability(0.04f)
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.NUDIFAE, 1, 1, 3))
						.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.LUNAR_KOI, 1, 1, 3))
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.PUFFBOO, 2, 1, 4))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}

	private static Biome createPermafrozenBiome(Biome.Category category, GenerationSettings.Builder generationSettings, SpawnSettings spawnSettings, int fogColour) {
		generationSettings
				.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, PermafrozenPlacedFeatures.PRISMARINE_GEODE)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);

		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_COAL_UPPER);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_COAL_LOWER);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_WULFRAM_UPPER);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_WULFRAM_MIDDLE);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_WULFRAM_SMALL);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_DIAMOND);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_DIAMOND_LARGE);
		generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, PermafrozenPlacedFeatures.ORE_DIAMOND_BURIED);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

		return new Biome.Builder()
				.category(category)
				.temperature(0.0f)
				.downfall(0.5f)
				.precipitation(Biome.Precipitation.SNOW)
				.generationSettings(generationSettings.build())
				.spawnSettings(spawnSettings)
				.effects(new BiomeEffects.Builder()
						.fogColor(fogColour)
						.waterColor(category.equals(Biome.Category.SWAMP) ? 0x212121 : 0x44BFEF)
						.waterFogColor(category.equals(Biome.Category.SWAMP) ? 0x212121 : 0x00A4A4)
						.skyColor(category.equals(Biome.Category.SWAMP) ? 0x212121 : 0x0d0e40)
						.moodSound(BiomeMoodSound.CAVE)
						.music(PermafrozenSoundEvents.SHRUMAL_SPIRES_MUSIC)
						.build())
				.build();
	}

	private static void registerBiome(RegistryKey<Biome> key, Biome biome) {
		Registry.register(BuiltinRegistries.BIOME, key, biome);
	}

	private static RegistryKey<Biome> createKey(String id) {
		return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, id));
	}
}
