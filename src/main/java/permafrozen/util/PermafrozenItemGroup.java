package permafrozen.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PermafrozenItemGroup extends net.minecraft.item.ItemGroup {

    private IItemProvider icon;

    public PermafrozenItemGroup(String label, IItemProvider icon) {
        super(label);
        this.icon = icon;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon);
    }
}