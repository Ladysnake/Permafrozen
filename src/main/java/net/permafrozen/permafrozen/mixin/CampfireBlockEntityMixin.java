package net.permafrozen.permafrozen.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.registry.PermafrozenItems;
import net.permafrozen.permafrozen.registry.PermafrozenStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin {
	@Inject(method = "litServerTick", at = @At("TAIL"))
	private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
		for (int i = 0; i < campfire.itemsBeingCooked.size(); ++i) {
			ItemStack itemStack = campfire.itemsBeingCooked.get(i);
			if (!itemStack.isEmpty() && itemStack.getItem().equals(PermafrozenItems.FIR_PINECONE)) {
				List<LivingEntity> entities = Objects.requireNonNull(campfire.getWorld()).getEntitiesByClass(LivingEntity.class, new Box(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3), (LivingEntity) -> true);
				for (LivingEntity nearbyEntity : entities) {
					if (nearbyEntity instanceof PlayerEntity) {
						nearbyEntity.addStatusEffect(new StatusEffectInstance(PermafrozenStatusEffects.FRAGRANT, 1000, 0));
					}
				}
			}
		}
	}
}
