package permafrozen.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import permafrozen.util.PermafrozenSpawnEggDispenserBehavior;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PermafrozenSpawnEgg extends SpawnEggItem {

    private static final String ENTITY_TAG = "EntityTag";
    private static final String ENTITY_ID_TAG = "id";
    private final Supplier<EntityType<?>> entityType;

    public PermafrozenSpawnEgg(EntityType entity, int color1, int color2) {
        super(entity, color1, color2, new Properties());
        this.entityType = () -> entity;
        DispenserBlock.registerDispenseBehavior(this, new PermafrozenSpawnEggDispenserBehavior());
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT compound) {
        if (compound != null && compound.contains(ENTITY_TAG, 10)) {
            CompoundNBT entityTag = compound.getCompound(ENTITY_TAG);
            if (entityTag.contains(ENTITY_ID_TAG, 8)) {
                return EntityType.byKey(entityTag.getString(ENTITY_ID_TAG)).orElse(this.entityType.get());
            }
        }
        return this.entityType.get();
    }
}
