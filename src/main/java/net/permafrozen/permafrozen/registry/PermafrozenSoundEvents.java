package net.permafrozen.permafrozen.registry;

import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenSoundEvents {
	private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();
	
	public static final SoundEvent ENTITY_AURORA_FAE_AMBIENT = create("entity.aurora_fae.ambient");
	public static final SoundEvent ENTITY_AURORA_FAE_HURT = create("entity.aurora_fae.hurt");
	public static final SoundEvent ENTITY_AURORA_FAE_DEATH = create("entity.aurora_fae.death");
	public static final SoundEvent ENTITY_PUFFBOO_DEATH = create("entity.puffboo.death");
	public static final SoundEvent ENTITY_PUFFBOO_HURT = create("entity.puffboo.hurt");
	public static final SoundEvent ENTITY_PUFFBOO_AMBIENT = create("entity.puffboo.ambient");
	public static final SoundEvent ENTITY_NUDIFAE_DEATH = create("entity.nudifae.death");
	public static final SoundEvent ENTITY_NUDIFAE_HURT = create("entity.nudifae.hurt");
	public static final SoundEvent ENTITY_NUDIFAE_AMBIENT = create("entity.nudifae.ambient");
	public static final SoundEvent BOREAL_AMBIENCE = create("ambient.boreal_forest");
	private static SoundEvent create(String name) {
		Identifier id = new Identifier(Permafrozen.MOD_ID, name);
		SoundEvent soundEvent = new SoundEvent(id);
		SOUND_EVENTS.put(soundEvent, id);
		return soundEvent;
	}
	
	public static void init() {
		SOUND_EVENTS.keySet().forEach(effect -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(effect), effect));
	}
}
