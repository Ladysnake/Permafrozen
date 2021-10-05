package net.ladysnake.permafrozen.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.statuseffect.FragrantStatusEffect;
import net.ladysnake.permafrozen.statuseffect.SpectralDazeStatusEffect;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenStatusEffects {
	private static final Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();
	public static final StatusEffect FRAGRANT = create("fragrant", new FragrantStatusEffect(StatusEffectType.BENEFICIAL, 0xde7a28));
	public static final StatusEffect SPECTRAL_DAZE = create("spectral_daze", new SpectralDazeStatusEffect(StatusEffectType.BENEFICIAL, 0x00bbd4));


	private static <T extends StatusEffect> T create(String name, T effect) {
		EFFECTS.put(effect, new Identifier(Permafrozen.MOD_ID, name));
		return effect;
	}
	
	public static void init() {
		EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, EFFECTS.get(effect), effect));
	}
}
