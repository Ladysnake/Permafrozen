package net.permafrozen.permafrozen.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

public class PermafrozenEntities {
    public static EntityType<Nudifae> NUDIFAE;

    public static void init() {
        NUDIFAE = createEntity("nudifae", Nudifae.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, Nudifae::new).dimensions(EntityDimensions.fixed(0.5f, 0.4f)).build());

    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, Permafrozen.MOD_ID + ":" + s, entityType);
    }
    private static <T extends LivingEntity> EntityType<T> createEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, name), type);
    }
}
