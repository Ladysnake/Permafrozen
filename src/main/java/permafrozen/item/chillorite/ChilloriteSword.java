package permafrozen.item.chillorite;

import net.minecraft.item.SwordItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class ChilloriteSword extends SwordItem {

    public ChilloriteSword() {

        super(PermafrozenItemTier.CHILLORITE, 3, -2.4f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
