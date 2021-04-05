package permafrozen.item.wulfram;

import net.minecraft.item.SwordItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class WulframSword extends SwordItem {

    public WulframSword() {

        super(PermafrozenItemTier.WULFRAM, 3, -2.4f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
