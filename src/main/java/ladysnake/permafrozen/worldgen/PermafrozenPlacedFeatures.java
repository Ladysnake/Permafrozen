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
}
