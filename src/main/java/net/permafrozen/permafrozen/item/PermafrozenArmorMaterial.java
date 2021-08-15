package net.permafrozen.permafrozen.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PermafrozenArmorMaterial implements ArmorMaterial {

    private final String id;
    private final int enchant;
    private final int[] prot;
    private final int dura;
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private final Ingredient ingr;
    private final int tough;
    public PermafrozenArmorMaterial(String name, int enchantability, int[] protectionValues, int toughness, int durability, Ingredient repairIngredient) {
        id = name;
        enchant = enchantability;
        prot = protectionValues;
        dura = durability;
        ingr = repairIngredient;
        tough = toughness;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * dura;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return prot[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return enchant;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return ingr;
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public float getToughness() {
        return tough;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
