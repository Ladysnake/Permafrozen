package net.ladysnake.permafrozen.mixin;

import com.mojang.serialization.Codec;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(Carver.class)
public class CarverMixin {
	@Shadow
	private Set<Block> alwaysCarvableBlocks;

	/**
	 * For modifying the hardcoded list of blocks caves and ravines can carve into.
	 */
	@Inject(at = @At("RETURN"), method = "<init>")
	private void addCustomBlocks(Codec configCodec, CallbackInfo ci) {
		this.alwaysCarvableBlocks = new HashSet<>(this.alwaysCarvableBlocks); // make it mutable
		this.alwaysCarvableBlocks.add(PermafrozenBlocks.PERMAFROST);
		this.alwaysCarvableBlocks.add(PermafrozenBlocks.MOSSY_PERMAFROST);
		this.alwaysCarvableBlocks.add(PermafrozenBlocks.SHIVERSLATE);
	}
}
