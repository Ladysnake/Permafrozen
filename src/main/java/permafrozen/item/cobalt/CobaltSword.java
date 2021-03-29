package permafrozen.item.cobalt;

import net.minecraft.item.SwordItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class CobaltSword extends SwordItem {

    public CobaltSword() {

        super(PermafrozenItemTier.COBALT, 3, -2.4f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
