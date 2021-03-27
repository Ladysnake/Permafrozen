package permafrozen.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;

public class ChilloriteSword extends SwordItem {

    public ChilloriteSword() {
        super(ItemTier.NETHERITE, 8, 1.8f, new Properties().maxDamage(2200));
    }

}
