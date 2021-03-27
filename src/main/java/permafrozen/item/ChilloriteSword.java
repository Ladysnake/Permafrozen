package permafrozen.item;

import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import permafrozen.Permafrozen;

public class ChilloriteSword extends SwordItem {

    public ChilloriteSword() {

        super(ItemTier.NETHERITE, 3, -2.4f, new Properties().maxDamage(2200).group(Permafrozen.ITEM_GROUP));
        setRegistryName("chillorite_sword");

    }

}
