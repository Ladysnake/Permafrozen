package net.permafrozen.permafrozen;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.registry.*;
import net.permafrozen.permafrozen.worldgen.feature.PermafrozenConfiguredFeatures;
import software.bernie.geckolib3.GeckoLib;

public class Permafrozen implements ModInitializer {
	public static final String MOD_ID = "permafrozen";
	
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(PermafrozenItems.LUNAR_KOI));
	
	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		PermafrozenEntities.init();
		PermafrozenBlocks.init();
		PermafrozenItems.init();
		PermafrozenConfiguredFeatures.init();
		PermafrozenBiomes.init();
		PermafrozenStatusEffects.init();
		PermafrozenSoundEvents.init();
	}
}
