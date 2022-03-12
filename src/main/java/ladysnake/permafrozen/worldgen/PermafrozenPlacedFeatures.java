package ladysnake.permafrozen.worldgen;


import com.google.common.collect.ImmutableList;
import ladysnake.permafrozen.Permafrozen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PermafrozenPlacedFeatures {
	// placed features
	public static final PlacedFeature SHRUMAL_SPIRES_VEGETATION = register("shrumnal_spires_vegetation", PermafrozenConfiguredFeatures.SHRUMNAL_SPIRES_VEGETATION.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.1f, 1))));
	public static final PlacedFeature FRIGID_FEN_VEGETATION = register("frigid_fen_vegetation", PermafrozenConfiguredFeatures.DEADWOOD_TREE.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1))));
	public static final PlacedFeature GLAUCA_PATCHES = register("glauca_patches", PermafrozenConfiguredFeatures.GLAUCA_PATCH.withPlacement(VegetationPlacedFeatures.modifiers(6)));
	public static final PlacedFeature SPECTRAL_CAP_PATCHES = register("spectral_cap_patches", PermafrozenConfiguredFeatures.SPECTRAL_CAP.withPlacement(modifiersWithChance(3, null)));
	public static final PlacedFeature SHIVERSLATE_ROCK = register("shiverslate_rock", PermafrozenConfiguredFeatures.SHIVERSLATE_ROCK.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
	public static final PlacedFeature PRISMARINE_GEODE = register("prismarine_geode", PermafrozenConfiguredFeatures.PRISMARINE_GEODE.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)), BiomePlacementModifier.of()));
	public static final PlacedFeature FREEZE_TOP_FEN_LAYER = PlacedFeatures.register("freeze_top_fen_layer", PermafrozenConfiguredFeatures.FREEZE_TOP_FEN_LAYER.withPlacement(BiomePlacementModifier.of()));

	//ores
	public static final PlacedFeature ORE_COAL_UPPER = register("ore_coal_upper", PermafrozenConfiguredFeatures.ORE_COAL.withPlacement(modifiersWithCount(70, HeightRangePlacementModifier.uniform(YOffset.fixed(136), YOffset.getTop()))));
	public static final PlacedFeature ORE_COAL_LOWER = register("ore_coal_lower", PermafrozenConfiguredFeatures.ORE_COAL_BURIED.withPlacement(modifiersWithCount(40, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(192)))));
	public static final PlacedFeature ORE_WULFRAM_UPPER = register("ore_iron_upper", PermafrozenConfiguredFeatures.ORE_WULFRAM.withPlacement(modifiersWithCount(25, HeightRangePlacementModifier.trapezoid(YOffset.fixed(80), YOffset.fixed(384)))));
	public static final PlacedFeature ORE_WULFRAM_MIDDLE = register("ore_iron_middle", PermafrozenConfiguredFeatures.ORE_WULFRAM.withPlacement(modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-24), YOffset.fixed(56)))));
	public static final PlacedFeature ORE_WULFRAM_SMALL = register("ore_iron_small", PermafrozenConfiguredFeatures.ORE_WULFRAM_SMALL.withPlacement(modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(72)))));
	public static final PlacedFeature ORE_DIAMOND = register("ore_diamond", PermafrozenConfiguredFeatures.ORE_DIAMOND_SMALL.withPlacement(modifiersWithCount(13, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80)))));
	public static final PlacedFeature ORE_DIAMOND_LARGE = register("ore_diamond_large", PermafrozenConfiguredFeatures.ORE_DIAMOND_LARGE.withPlacement(modifiersWithRarity(20, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80)))));
	public static final PlacedFeature ORE_DIAMOND_BURIED = register("ore_diamond_buried", PermafrozenConfiguredFeatures.ORE_DIAMOND_BURIED.withPlacement(modifiersWithCount(24, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80)))));



	private static PlacedFeature register(String id, PlacedFeature feature) {
		return Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Permafrozen.MOD_ID, id), feature);
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
