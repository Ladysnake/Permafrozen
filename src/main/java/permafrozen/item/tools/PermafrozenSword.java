package permafrozen.item.tools;

import net.minecraft.item.SwordItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class PermafrozenSword extends SwordItem {

    public PermafrozenSword(PermafrozenItemTier itemTier) {

        super(itemTier, 3, -2.4f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
