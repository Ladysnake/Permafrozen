package net.permafrozen.permafrozen;

import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;
import net.permafrozen.permafrozen.item.PermafrozenItems;
import net.fabricmc.api.ModInitializer;
import net.permafrozen.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.permafrozen.permafrozen.worldgen.feature.PermafrozenConfiguredFeatures;
import software.bernie.geckolib3.GeckoLib;


public class Permafrozen implements ModInitializer {
	public static final String MOD_ID = "permafrozen";
	@Override
	public void onInitialize() {
		PermafrozenItems.innit();
		GeckoLib.initialize();
		PermafrozenEntities.init();
		PermafrozenBlocks.innit();
		PermafrozenConfiguredFeatures.commenceForth();
		PermafrozenBiomes.innit();
	}

}
