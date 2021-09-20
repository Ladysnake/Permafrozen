package permafrozen.item.tools;

import net.minecraft.item.HoeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class PermafrozenHoe extends HoeItem {

    public PermafrozenHoe(PermafrozenItemTier itemTier) {

        super(itemTier, 0, 0, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
