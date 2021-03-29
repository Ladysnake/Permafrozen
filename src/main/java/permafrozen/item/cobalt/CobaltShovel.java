package permafrozen.item.cobalt;

import net.minecraft.item.ShovelItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class CobaltShovel extends ShovelItem {

    public CobaltShovel() {

        super(PermafrozenItemTier.COBALT, 1.5f, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
