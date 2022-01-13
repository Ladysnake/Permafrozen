package net.ladysnake.permafrozen.registry;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.block.SpectralCapBlockEntity;
import net.ladysnake.permafrozen.entity.living.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenEntities {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	public static final EntityType<NudifaeEntity> NUDIFAE = createEntity("nudifae", NudifaeEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, NudifaeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
	public static final EntityType<LunarKoiEntity> LUNAR_KOI = createEntity("lunar_koi", LunarKoiEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LunarKoiEntity::new).dimensions(EntityDimensions.fixed(1.0F, 0.4F)).build());
	public static final EntityType<FatfishEntity> FATFISH = createEntity("fatfish", FatfishEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, FatfishEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.2F)).build());
	public static final EntityType<AuroraFaeEntity> AURORA_FAE = createEntity("aurora_fae", AuroraFaeEntity.createFaeAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AuroraFaeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
	public static final EntityType<PuffbooEntity> PUFFBOO = createEntity("puffboo", PuffbooEntity.createBooAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PuffbooEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.4f)).build());
	public static final EntityType<LesserFiddlesnoutEntity> LESSER_FIDDLESNOUT = createEntity("fiddlesnout", LesserFiddlesnoutEntity.createFiddlesnoutAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LesserFiddlesnoutEntity::new).dimensions(EntityDimensions.changing(0.8f, 0.5f)).build());
	public static final EntityType<BurrowGrubEntity> BURROW_GRUB = createEntity("burrow_grub", BurrowGrubEntity.createBurrowGrubAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BurrowGrubEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.2F)).build());
	public static       BlockEntityType<SpectralCapBlockEntity> SPECTRAL_CAP_TYPE;

	private static <T extends Entity> EntityType<T> createEntity(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, new Identifier(Permafrozen.MOD_ID, name));
		return type;
	}

	private static <T extends LivingEntity> EntityType<T> createEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		ENTITY_TYPES.put(type, new Identifier(Permafrozen.MOD_ID, name));
		return type;
	}
	
	public static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
		SPECTRAL_CAP_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Permafrozen.MOD_ID + "spectral_cap",
				FabricBlockEntityTypeBuilder.create(SpectralCapBlockEntity::new, PermafrozenBlocks.SPECTRAL_CAP).build(null));
	}
}
