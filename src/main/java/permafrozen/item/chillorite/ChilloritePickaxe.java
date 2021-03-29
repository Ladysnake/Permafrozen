package permafrozen.item.chillorite;

import net.minecraft.item.PickaxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class ChilloritePickaxe extends PickaxeItem {

    public ChilloritePickaxe() {

        super(PermafrozenItemTier.CHILLORITE, 1, -2.8f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
