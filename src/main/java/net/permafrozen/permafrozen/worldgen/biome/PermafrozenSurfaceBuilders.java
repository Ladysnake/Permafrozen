package net.permafrozen.permafrozen.worldgen.biome;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class PermafrozenSurfaceBuilders {
    public static final SurfaceBuilder<TernarySurfaceConfig> GLACIAL_BUILMDER = register("glacial_ocean", new GlacialOceanSurfaceBuilder(TernarySurfaceConfig.CODEC));
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> GLACIAL_OCEAN = create("glacial_ocean", GLACIAL_BUILMDER.withConfig(SurfaceBuilder.GRASS_CONFIG));

    private static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String id, F surfaceBuilder) {
        return (F) Registry.register(Registry.SURFACE_BUILDER, id, surfaceBuilder);
    }
    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> create(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return (ConfiguredSurfaceBuilder) BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, id, configuredSurfaceBuilder);
    }
}
