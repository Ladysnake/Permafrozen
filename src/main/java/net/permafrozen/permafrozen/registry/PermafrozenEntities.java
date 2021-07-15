package net.permafrozen.permafrozen.registry;

import com.terraformersmc.terraform.boat.TerraformBoat;
import com.terraformersmc.terraform.boat.TerraformBoatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.living.AuroraFaeEntity;
import net.permafrozen.permafrozen.entity.living.FatfishEntity;
import net.permafrozen.permafrozen.entity.living.LunarKoiEntity;
import net.permafrozen.permafrozen.entity.living.NudifaeEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenEntities {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();
	
	public static final EntityType<TerraformBoatEntity> FIR_BOAT = createEntity("fir_boat", FabricEntityTypeBuilder.<TerraformBoatEntity>create(SpawnGroup.MISC, (type, world) -> new TerraformBoatEntity(type, world, new TerraformBoat(PermafrozenItems.FIR_BOAT, PermafrozenBlocks.FIR_PLANKS.asItem(), new Identifier(Permafrozen.MOD_ID, "textures/entity/boat/fir_boat.png")))).dimensions(EntityType.BOAT.getDimensions()).build());
	
	public static final EntityType<NudifaeEntity> NUDIFAE = createEntity("nudifae", NudifaeEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, NudifaeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
	public static final EntityType<LunarKoiEntity> LUNAR_KOI = createEntity("lunar_koi", LunarKoiEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LunarKoiEntity::new).dimensions(EntityDimensions.fixed(1.0F, 0.4F)).build());
	public static final EntityType<FatfishEntity> FATFISH = createEntity("fatfish", FatfishEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, FatfishEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.2F)).build());
	public static final EntityType<AuroraFaeEntity> AURORA_FAE = createEntity("aurora_fae", AuroraFaeEntity.createFaeAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AuroraFaeEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
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
	}
}
