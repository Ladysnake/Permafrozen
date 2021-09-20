package permafrozen.item.tools;

import net.minecraft.item.AxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class PermafrozenAxe extends AxeItem {

    public PermafrozenAxe(PermafrozenItemTier itemTier) {

        super(itemTier, 6, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
