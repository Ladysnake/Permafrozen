package permafrozen.item.tools;

import net.minecraft.item.PickaxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class PermafrozenPickaxe extends PickaxeItem {

    public PermafrozenPickaxe(PermafrozenItemTier itemTier) {

        super(itemTier, 1, -2.8f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
