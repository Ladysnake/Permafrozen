package net.ladysnake.permafrozen.worldgen.biome;

import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.ladysnake.permafrozen.worldgen.PermafrozenBiomeSource;
import net.ladysnake.permafrozen.worldgen.PermafrozenChunkGenerator;
import net.ladysnake.permafrozen.worldgen.PermafrozenConfiguredFeatures;
import net.ladysnake.permafrozen.worldgen.PermafrozenPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class PermafrozenBiomes extends OverworldBiomeCreator {
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
	}

	private static Biome createTundra() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();

		return createPermafrozenBiome(
				Biome.Category.PLAINS,
				generationSettings,
				new SpawnSettings.Builder()
						.creatureSpawnProbability(0.07f) // default is 0.1f, snowy plains uses 0.07f
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 10, 5, 7))
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 1, 3))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}

	private static Biome createShrumalSpires() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.SHRUMAL_SPIRES_VEGETATION)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.GLAUCA_PATCHES)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.SPECTRAL_CAP_PATCHES);

		return createPermafrozenBiome(
				Biome.Category.FOREST,
				generationSettings,
				new SpawnSettings.Builder()
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.AURORA_FAE, 2, 1, 2))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}


	private static Biome createFrigidFen() {
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.FRIGID_FEN_VEGETATION)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenPlacedFeatures.GLAUCA_PATCHES);

		return createPermafrozenBiome(
				Biome.Category.SWAMP,
				generationSettings,
				new SpawnSettings.Builder()
						.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 100, 2, 4))
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
						.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 10, 5, 7))
						.build(),
				DEFAULT_PERMAROZEN_FOG_COLOUR);
	}

	private static Biome createPermafrozenBiome(Biome.Category category, GenerationSettings.Builder generationSettings, SpawnSettings spawnSettings, int fogColour) {
		generationSettings
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);

		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		DefaultBiomeFeatures.addDefaultOres(generationSettings);

		return new Biome.Builder()
				.category(category)
				.temperature(0.0f)
				.downfall(0.5f)
				.precipitation(Biome.Precipitation.SNOW)
				.generationSettings(generationSettings.build())
				.spawnSettings(spawnSettings)
				.effects(new BiomeEffects.Builder()
						.fogColor(fogColour)
						.waterColor(0x44BFEF)
						.waterFogColor(0x00A4A4)
						.skyColor(getSkyColor(0.5f))
						.moodSound(BiomeMoodSound.CAVE)
						.music(null) // vanilla music
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
