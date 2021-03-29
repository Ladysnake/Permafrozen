package permafrozen.item.cobalt;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import permafrozen.Permafrozen;

import javax.annotation.Nonnull;

public class CobaltArmor extends ArmorItem {

    private String texture;

    public CobaltArmor(IArmorMaterial armorMaterial, EquipmentSlotType equipmentSlot) {
        super(armorMaterial, equipmentSlot, new Item.Properties().group(Permafrozen.ITEM_GROUP));
    }

    public Item setArmorTexture(String string) {
        this.texture = string;
        return this;
    }

    @Override
    public String getArmorTexture(@Nonnull ItemStack stack, Entity entity, EquipmentSlotType slot, String layer) {
        return "permafrozen:textures/armor/" + this.texture + ".png";
    }

}
