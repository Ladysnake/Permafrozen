package permafrozen.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import permafrozen.Permafrozen;

public class CobaltIngot extends Item {

    public CobaltIngot() {

        super(new Item.Properties().group(Permafrozen.ITEM_GROUP));
        setRegistryName("cobalt_ingot");

    }

}
