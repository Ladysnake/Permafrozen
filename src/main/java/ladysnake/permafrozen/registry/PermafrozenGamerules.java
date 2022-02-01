package ladysnake.permafrozen.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class PermafrozenGamerules {
    public static GameRules.Key<GameRules.BooleanRule> ALWAYS_SNOWSTORM;

    public static void init() {
        ALWAYS_SNOWSTORM = registerGamerule("alwaysActivisionBlizzard", GameRuleFactory.createBooleanRule(false));
    }

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, GameRules.Category.SPAWNING, type);
    }
}
