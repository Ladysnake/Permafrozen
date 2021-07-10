package net.permafrozen.permafrozen.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.nudifae.NudifaeEntity;
import net.permafrozen.permafrozen.entity.phishe.FatfishEntity;
import net.permafrozen.permafrozen.entity.phishe.LunarKoiEntity;

public class PermafrozenEntities {
	public static final EntityType<NudifaeEntity> NUDIFAE = createEntity("nudifae", NudifaeEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, NudifaeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
	public static final EntityType<LunarKoiEntity> LUNAR_KOI = createEntity("lunar_koi", LunarKoiEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LunarKoiEntity::new).dimensions(EntityDimensions.fixed(1.0F, 0.4F)).build());
	public static final EntityType<FatfishEntity> FAT_FUCK = createEntity("fat_fuck", FatfishEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, FatfishEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.2F)).build());
	
	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "nudifae"), NUDIFAE);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "lunar_koi"), LUNAR_KOI);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "fatfish"), FAT_FUCK);
	}
	
	private static <T extends LivingEntity> EntityType<T> createEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		return type;
	}
}
