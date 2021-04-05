package permafrozen.item.wulfram;

import net.minecraft.item.PickaxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class WulframPickaxe extends PickaxeItem {

    public WulframPickaxe() {

        super(PermafrozenItemTier.WULFRAM, 1, -2.8f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
