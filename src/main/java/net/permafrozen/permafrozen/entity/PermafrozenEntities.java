package net.permafrozen.permafrozen.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

public class PermafrozenEntities {
    public static EntityType<Nudifae> NUDIFAE;

    public static void init() {
        NUDIFAE = register("nudifae", EntityType.Builder.create(Nudifae::new, SpawnGroup.WATER_CREATURE).setDimensions(0.5F, 0.4F).maxTrackingRange(10));
        FabricDefaultAttributeRegistry.register(NUDIFAE, Nudifae.createAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return (EntityType)Registry.register(Registry.ENTITY_TYPE, id, type.build(id));
    }
}
