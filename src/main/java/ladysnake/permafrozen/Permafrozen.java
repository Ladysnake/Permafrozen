package ladysnake.permafrozen;

import ladysnake.permafrozen.registry.*;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.mixin.structure.StructureFeatureAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import software.bernie.geckolib3.GeckoLib;

public class Permafrozen implements ModInitializer {
	public static final String MOD_ID = "permafrozen";
	public static final RegistryKey<World> WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "permafrozen"));
	
	public static final ItemGroup PERMAFROZEN_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(PermafrozenItems.LUNAR_KOI));

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		PermafrozenEntities.init();
		PermafrozenBlocks.init();
		PermafrozenItems.init();
		PermafrozenBoatTypes.init();
		PermafrozenBiomes.init();
		PermafrozenStatusEffects.init();
		PermafrozenSoundEvents.init();
		PermafrozenGamerules.init();
	}
}
