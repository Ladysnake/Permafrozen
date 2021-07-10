package net.permafrozen.permafrozen.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.entity.nudifae.Nudifae;
import net.permafrozen.permafrozen.entity.phishe.Fatfish;
import net.permafrozen.permafrozen.entity.phishe.LunarKoi;

import java.util.function.Supplier;

public class PermafrozenEntities {
    public static final EntityType<Nudifae> NUDIFAE = createEntity("nudifae", Nudifae.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, Nudifae::new).dimensions(EntityDimensions.fixed(0.5F, 0.4F)).build());
    public static final EntityType<LunarKoi> LUNAR_KOI = createEntity("lunar_koi", LunarKoi.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LunarKoi::new).dimensions(EntityDimensions.fixed(1.0F, 0.4F)).build());
    public static final EntityType<Fatfish> FAT_FUCK = createEntity("fat_fuck", Fatfish.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, Fatfish::new).dimensions(EntityDimensions.fixed(0.4F, 0.2F)).build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(NUDIFAE, Nudifae.createAttributes());
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "nudifae"), NUDIFAE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "lunar_koi"), LUNAR_KOI);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Permafrozen.MOD_ID, "fatfish"), FAT_FUCK);
    }

    private static <T extends LivingEntity> EntityType<T> createEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        return type;
    }
}
