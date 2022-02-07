package ladysnake.permafrozen.registry;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.statuseffect.BurrowedStatusEffect;
import ladysnake.permafrozen.statuseffect.GuidanceStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ladysnake.permafrozen.statuseffect.FragrantStatusEffect;
import ladysnake.permafrozen.statuseffect.SpectralDazeStatusEffect;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenStatusEffects {
	private static final Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();
	public static final StatusEffect FRAGRANT = create("fragrant", new FragrantStatusEffect(StatusEffectCategory.BENEFICIAL, 0xde7a28));
	public static final StatusEffect BURROWED = create("burrowed", new BurrowedStatusEffect(StatusEffectCategory.BENEFICIAL, 0x48c260));
	public static final StatusEffect SPECTRAL_DAZE = create("spectral_daze", new SpectralDazeStatusEffect(StatusEffectCategory.BENEFICIAL, 0x00bbd4));
	public static final StatusEffect GUIDANCE = create("guidance", new GuidanceStatusEffect(StatusEffectCategory.BENEFICIAL, 0xa5fdfe));


	private static <T extends StatusEffect> T create(String name, T effect) {
		EFFECTS.put(effect, new Identifier(Permafrozen.MOD_ID, name));
		return effect;
	}
	
	public static void init() {
		EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, EFFECTS.get(effect), effect));
	}
}
