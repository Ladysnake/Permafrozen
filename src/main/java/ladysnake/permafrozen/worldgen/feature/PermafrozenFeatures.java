package ladysnake.permafrozen.worldgen.feature;

import ladysnake.permafrozen.Permafrozen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class PermafrozenFeatures {
    public static final Feature<GeodeFeatureConfig> PRISMARINE_SPIKE = register("prismarine_geode", new PrismarineGeodeFeature(GeodeFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> AURORA_CORAL = register("aurora_coral", new AuroraCoralFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SPIRESHROOM = register("spireshroom", new SpireshroomFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> DEADWOOD_TREE = register("deadwood_tree", new DeadwoodTreeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SHIVERSLATE_ROCK = register("shiverslate_rock", new ShiverslateRockFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, new Identifier(Permafrozen.MOD_ID, name), feature);
    }
}
