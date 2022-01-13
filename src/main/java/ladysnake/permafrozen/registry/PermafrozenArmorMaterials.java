package ladysnake.permafrozen.registry;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import ladysnake.permafrozen.item.PermafrozenArmorMaterial;

public class PermafrozenArmorMaterials {
    public static final ArmorMaterial WULFRAM_ARMOR_MATERIAL = new PermafrozenArmorMaterial("wulfram", 14, new int[] {2, 6, 5, 2}, 0, 15, Ingredient.ofItems(PermafrozenItems.WULFRAM_INGOT));
}
