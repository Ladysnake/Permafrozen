package permafrozen.item.cobalt;

import net.minecraft.item.PickaxeItem;
import permafrozen.Permafrozen;
import permafrozen.util.PermafrozenItemTier;

public class CobaltPickaxe extends PickaxeItem {

    public CobaltPickaxe() {

        super(PermafrozenItemTier.COBALT, 1, -2.8f, new Properties().group(Permafrozen.ITEM_GROUP));

    }

}
