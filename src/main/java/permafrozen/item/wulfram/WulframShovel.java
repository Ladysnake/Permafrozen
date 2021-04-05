package permafrozen.item.wulfram;

import net.minecraft.item.ShovelItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class WulframShovel extends ShovelItem {

    public WulframShovel() {

        super(PermafrozenItemTier.WULFRAM, 1.5f, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
