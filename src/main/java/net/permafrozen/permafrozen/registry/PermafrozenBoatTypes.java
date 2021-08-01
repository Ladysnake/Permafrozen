package net.permafrozen.permafrozen.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

public class PermafrozenBoatTypes {
	public static TerraformBoatType fir;
	
	public static void init() {
		Item item = TerraformBoatItemHelper.registerBoatItem(new Identifier(Permafrozen.MOD_ID, "fir_boat"), () -> fir, Permafrozen.PERMAFROZEN_GROUP);
		fir = new TerraformBoatType.Builder().item(item).build();
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Permafrozen.MOD_ID, "fir"), fir);
	}
}
