package ladysnake.permafrozen.registry;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.statuseffect.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenStatusEffects {
	private static final Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();
	public static final StatusEffect FRAGRANT = create("fragrant", new FragrantStatusEffect(StatusEffectCategory.BENEFICIAL, 0xde7a28));
	public static final StatusEffect BURROWED = create("burrowed", new BurrowedStatusEffect(StatusEffectCategory.BENEFICIAL, 0x48c260));
	public static final StatusEffect SPECTRAL_DAZE = create("spectral_daze", new SpectralDazeStatusEffect(StatusEffectCategory.BENEFICIAL, 0x00bbd4));
	public static final StatusEffect WRAITHWRATH = create("wraithwrath", new WraithwrathStatusEffect(StatusEffectCategory.BENEFICIAL, 0x8c0f2e).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "AF8B6E1F-3328-4C0A-AA76-5BA2BB9DBEF3", 0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "AF8B6E1F-3328-4C0A-AA76-5BA2BB9DBEF3", -0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "55FCED69-E92A-486E-9800-B47F202C4387", -0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final StatusEffect GUIDANCE = create("guidance", new GuidanceStatusEffect(StatusEffectCategory.BENEFICIAL, 0xa5fdfe));


	private static <T extends StatusEffect> T create(String name, T effect) {
		EFFECTS.put(effect, new Identifier(Permafrozen.MOD_ID, name));
		return effect;
	}
	
	public static void init() {
		EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, EFFECTS.get(effect), effect));
	}
}
