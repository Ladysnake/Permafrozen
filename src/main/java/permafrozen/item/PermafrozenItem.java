package permafrozen.item;

import net.minecraft.item.Item;
import permafrozen.Permafrozen;

public class PermafrozenItem extends Item {

    public PermafrozenItem() {

        super(new Item.Properties().group(Permafrozen.ITEM_GROUP));

    }

    public PermafrozenItem(Properties properties) {

        super(properties.group(Permafrozen.ITEM_GROUP));

    }

}
