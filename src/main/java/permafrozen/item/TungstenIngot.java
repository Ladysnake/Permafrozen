package permafrozen.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import permafrozen.Permafrozen;

public class TungstenIngot extends Item {

    public TungstenIngot() {

        super(new Item.Properties().group(Permafrozen.ITEM_GROUP));
        setRegistryName("tungsten_ingot");

    }

}
