package permafrozen.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import permafrozen.entity.Nudifae;
import permafrozen.util.SpawnEggDispenserBehavior;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class NudifaeSpawnEgg extends SpawnEggItem {

    private static final String ENTITY_TAG = "EntityTag";
    private static final String ENTITY_ID_TAG = "id";
    private final Supplier<EntityType<?>> entityType;

    public NudifaeSpawnEgg() {
        super(null,-1, 1605609, new Properties());
        this.entityType = null;
        DispenserBlock.registerDispenseBehavior(this, new SpawnEggDispenserBehavior());
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
