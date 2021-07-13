package net.permafrozen.permafrozen.registry;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.permafrozen.permafrozen.item.PermafrozenArmorMaterial;

public class PermafrozenArmorMaterials {
    public static final ArmorMaterial WULFRAM_ARMOR_MATERIAL = new PermafrozenArmorMaterial("wulfram", 14, new int[] {2, 6, 5, 2}, 0, 15, Ingredient.ofItems(PermafrozenItems.WULFRAM_INGOT));
    public static final ArmorMaterial CRYORITE_ARMOR_MATERIAL = new PermafrozenArmorMaterial("cryorite", 18, new int[] {3, 8, 7, 4}, 1, 27, Ingredient.ofItems(PermafrozenItems.CRYORITE_INGOT));

}
