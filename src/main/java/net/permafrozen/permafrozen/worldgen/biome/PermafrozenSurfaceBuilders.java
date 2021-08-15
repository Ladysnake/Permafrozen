package net.permafrozen.permafrozen.worldgen.biome;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.permafrozen.permafrozen.registry.PermafrozenBlocks;
import net.permafrozen.permafrozen.worldgen.surfacebuilder.GlacialOceanSurfaceBuilder;
import net.permafrozen.permafrozen.worldgen.surfacebuilder.PermafrostSurfaceBuilder;

public class PermafrozenSurfaceBuilders {
    public static final SurfaceBuilder<TernarySurfaceConfig> GLACIAL_BUILMDER = register("glacial_ocean", new GlacialOceanSurfaceBuilder(TernarySurfaceConfig.CODEC));
    public static final SurfaceBuilder<TernarySurfaceConfig> PERMAFROST_BUILDER = register("permafrost", new PermafrostSurfaceBuilder(TernarySurfaceConfig.CODEC));

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> GLACIAL_OCEAN = create("glacial_ocean", GLACIAL_BUILMDER.withConfig(SurfaceBuilder.GRASS_CONFIG));
    public static final TernarySurfaceConfig PERMAFROST_CONFIG = new TernarySurfaceConfig(PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState(), PermafrozenBlocks.COARSE_PERMAFROST.getDefaultState());
    public static final TernarySurfaceConfig THAWING_PERMAFROST_CONFIG = new TernarySurfaceConfig(PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState(), PermafrozenBlocks.THAWING_PERMAFROST.getDefaultState());
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> PERMAFROST = create("permaforst", PERMAFROST_BUILDER.withConfig(PermafrozenSurfaceBuilders.PERMAFROST_CONFIG));


    private static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String id, F surfaceBuilder) {
        return (F) Registry.register(Registry.SURFACE_BUILDER, id, surfaceBuilder);
    }
    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> create(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return (ConfiguredSurfaceBuilder) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, id, configuredSurfaceBuilder);
    }
}
