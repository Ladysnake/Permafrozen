package ladysnake.permafrozen.worldgen.feature;

import ladysnake.permafrozen.Permafrozen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class PermafrozenFeatures {
    public static final Feature<DefaultFeatureConfig> SPIRESHROOM = register("spireshroom", new SpireshroomFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> DEADWOOD_TREE = register("deadwood_tree", new DeadwoodTreeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SHIVERSLATE_ROCK = register("shiverslate_rock", new ShiverslateRockFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SNOW_UNDER_TREES_FEATURE = register("snow_under_trees", new SnowUnderTreeFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, new Identifier(Permafrozen.MOD_ID, name), feature);
    }
}
