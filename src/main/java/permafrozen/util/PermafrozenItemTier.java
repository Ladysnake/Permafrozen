package permafrozen.util;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import permafrozen.registry.ItemRegistry;

import java.util.function.Supplier;

public enum PermafrozenItemTier implements IItemTier {

    WULFRUM(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.fromItems(new IItemProvider[]{ItemRegistry.WULFRUM_INGOT});
    }),
    TANZANITE(3, 1561, 8.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(new IItemProvider[]{Items.DIAMOND});
    }),
    COBALT(0, 190, 12.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(new IItemProvider[]{ItemRegistry.COBALT_INGOT});
    }),
    CHILLORITE(4, 2200, 9.0F, 4.0F, 15, () -> {
        return Ingredient.fromItems(new IItemProvider[]{ItemRegistry.CHILLORITE_INGOT});
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    PermafrozenItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}