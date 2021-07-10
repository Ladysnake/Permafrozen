package net.permafrozen.permafrozen.mob_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenEffects {
	private static final Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();
	public static final StatusEffect FRAGRANT = create("fragrant", new FragrantStatusEffect(StatusEffectType.BENEFICIAL, 0xde7a28));
	
	private static <T extends StatusEffect> T create(String name, T effect) {
		EFFECTS.put(effect, new Identifier(Permafrozen.MOD_ID, name));
		return effect;
	}
	
	public static void init() {
		EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, EFFECTS.get(effect), effect));
	}
}
