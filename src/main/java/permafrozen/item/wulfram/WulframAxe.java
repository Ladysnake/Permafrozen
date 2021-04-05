package permafrozen.item.wulfram;

import net.minecraft.item.AxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class WulframAxe extends AxeItem {

    public WulframAxe() {

        super(PermafrozenItemTier.WULFRAM, 6, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
