package net.permafrozen.permafrozen.mixin;

import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;
import net.minecraft.item.Item;
import net.permafrozen.permafrozen.registry.PermafrozenItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
@Mixin(TerraformBoatTypeImpl.class)
public class TerraformBoatTypeImplMixin {
	@Inject(method = "getItem", at = @At("HEAD"), remap = false, cancellable = true)
	private void getItem(CallbackInfoReturnable<Item> callbackInfo) {
		if (this == ((TerraformBoatItemMixin) PermafrozenItems.FIR_BOAT).pf_getSupplier().get()) {
			callbackInfo.setReturnValue(PermafrozenItems.FIR_BOAT);
		}
	}
}
