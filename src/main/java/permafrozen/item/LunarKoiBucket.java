package permafrozen.item;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import permafrozen.Permafrozen;
import permafrozen.registry.EntityRegistry;

public class LunarKoiBucket extends FishBucketItem {

    public LunarKoiBucket() {

        super(() -> EntityRegistry.LUNAR_KOI, () -> Fluids.WATER,new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
