package permafrozen.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import permafrozen.Permafrozen;

public class TungstenNugget extends Item {

    public TungstenNugget() {

        super(new Item.Properties().group(Permafrozen.ITEM_GROUP));
        setRegistryName("tungsten_nugget");

    }

}
