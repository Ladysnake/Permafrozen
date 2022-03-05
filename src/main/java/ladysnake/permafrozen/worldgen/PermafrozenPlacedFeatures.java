package ladysnake.permafrozen.worldgen;


import com.google.common.collect.ImmutableList;
import ladysnake.permafrozen.Permafrozen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PermafrozenPlacedFeatures {
	// placed features
	public static final RegistryEntry<PlacedFeature> SHRUMAL_SPIRES_VEGETATION = register("shrumnal_spires_vegetation", PermafrozenConfiguredFeatures.SHRUMNAL_SPIRES_VEGETATION, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.1f, 1)));
	public static final RegistryEntry<PlacedFeature> FRIGID_FEN_VEGETATION = register("frigid_fen_vegetation", PermafrozenConfiguredFeatures.DEADWOOD_TREE, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1)));
	public static final RegistryEntry<PlacedFeature> SNOW_UNDER_TREES = register("snow_under_trees", PermafrozenConfiguredFeatures.SNOWY_TREES_CONFIGURED, BiomePlacementModifier.of());
	public static final RegistryEntry<PlacedFeature> GLAUCA_PATCHES = register("glauca_patches", PermafrozenConfiguredFeatures.GLAUCA_PATCH, VegetationPlacedFeatures.modifiers(6));
	public static final RegistryEntry<PlacedFeature> SPECTRAL_CAP_PATCHES = register("spectral_cap_patches", PermafrozenConfiguredFeatures.SPECTRAL_CAP, modifiersWithChance(3, null));
	public static final RegistryEntry<PlacedFeature> SHIVERSLATE_ROCK = register("shiverslate_rock", PermafrozenConfiguredFeatures.SHIVERSLATE_ROCK, CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
	public static final RegistryEntry<PlacedFeature> PRISMARINE_GEODE = register("prismarine_geode", PermafrozenConfiguredFeatures.PRISMARINE_GEODE, RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)), BiomePlacementModifier.of());

	//ores
	public static final RegistryEntry<PlacedFeature> ORE_COAL_UPPER = register("ore_coal_upper", PermafrozenConfiguredFeatures.ORE_COAL, modifiersWithCount(70, HeightRangePlacementModifier.uniform(YOffset.fixed(136), YOffset.getTop())));
	public static final RegistryEntry<PlacedFeature> ORE_COAL_LOWER = register("ore_coal_lower", PermafrozenConfiguredFeatures.ORE_COAL_BURIED, modifiersWithCount(40, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(192))));
	public static final RegistryEntry<PlacedFeature> ORE_WULFRAM_UPPER = register("ore_iron_upper", PermafrozenConfiguredFeatures.ORE_WULFRAM, modifiersWithCount(25, HeightRangePlacementModifier.trapezoid(YOffset.fixed(80), YOffset.fixed(384))));
	public static final RegistryEntry<PlacedFeature> ORE_WULFRAM_MIDDLE = register("ore_iron_middle", PermafrozenConfiguredFeatures.ORE_WULFRAM, modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-24), YOffset.fixed(56))));
	public static final RegistryEntry<PlacedFeature> ORE_WULFRAM_SMALL = register("ore_iron_small", PermafrozenConfiguredFeatures.ORE_WULFRAM_SMALL, modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(72))));
	public static final RegistryEntry<PlacedFeature> ORE_DIAMOND = register("ore_diamond", PermafrozenConfiguredFeatures.ORE_DIAMOND_SMALL, modifiersWithCount(13, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
	public static final RegistryEntry<PlacedFeature> ORE_DIAMOND_LARGE = register("ore_diamond_large", PermafrozenConfiguredFeatures.ORE_DIAMOND_LARGE, modifiersWithRarity(20, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
	public static final RegistryEntry<PlacedFeature> ORE_DIAMOND_BURIED = register("ore_diamond_buried", PermafrozenConfiguredFeatures.ORE_DIAMOND_BURIED, modifiersWithCount(24, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));


	public static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, PlacementModifier ... modifiers) {
		return register(id, registryEntry, List.of(modifiers));
	}
	private static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers) {
		return RegistryEntry.of(Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), new PlacedFeature(RegistryEntry.upcast(registryEntry), List.copyOf(modifiers))));
	}

	private static List<PlacementModifier> modifiersWithChance(int chance, @Nullable PlacementModifier modifier) {
		ImmutableList.Builder<PlacementModifier> builder = ImmutableList.builder();

		if (modifier != null) {
			builder.add(modifier);
		}

		if (chance != 0) {
			builder.add(RarityFilterPlacementModifier.of(chance));
		}

		builder.add(SquarePlacementModifier.of());
		builder.add(PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP);
		builder.add(BiomePlacementModifier.of());
		return builder.build();
	}
	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
	}

	private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
		return modifiers(CountPlacementModifier.of(count), heightModifier);
	}

	private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
		return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
	}
}
