package permafrozen.item.chillorite;

import net.minecraft.item.AxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class ChilloriteAxe extends AxeItem {

    public ChilloriteAxe() {

        super(PermafrozenItemTier.CHILLORITE, 5, -3f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
