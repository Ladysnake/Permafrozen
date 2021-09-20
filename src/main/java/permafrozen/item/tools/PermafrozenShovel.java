package permafrozen.item.tools;

import net.minecraft.item.ShovelItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class PermafrozenShovel extends ShovelItem {

    public PermafrozenShovel(PermafrozenItemTier itemTier) {

        super(itemTier, 1.5f, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
