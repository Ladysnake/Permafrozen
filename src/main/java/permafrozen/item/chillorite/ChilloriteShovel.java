package permafrozen.item.chillorite;

import net.minecraft.item.ShovelItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class ChilloriteShovel extends ShovelItem {

    public ChilloriteShovel() {

        super(PermafrozenItemTier.COBALT, 1.5f, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
