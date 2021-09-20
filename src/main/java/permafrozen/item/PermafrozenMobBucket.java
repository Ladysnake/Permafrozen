package permafrozen.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import permafrozen.Permafrozen;

import java.util.function.Supplier;

public class PermafrozenMobBucket extends FishBucketItem {

    public PermafrozenMobBucket(Supplier<EntityType<? extends Entity>> entity) {

        super(entity, () -> Fluids.WATER, new Properties().group(Permafrozen.ITEM_GROUP));

    }

    public PermafrozenMobBucket(Supplier<EntityType<? extends Entity>> entity, Supplier<? extends Fluid> fluid) {

        super(entity, fluid, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
