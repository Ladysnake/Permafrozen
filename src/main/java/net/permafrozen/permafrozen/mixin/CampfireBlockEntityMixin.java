package net.permafrozen.permafrozen.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.item.PermafrozenItems;
import net.permafrozen.permafrozen.mob_effect.PermafrozenEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin extends BlockEntity implements Clearable {
	@Mutable
	@Shadow
	@Final
	public final DefaultedList<ItemStack> itemsBeingCooked;
	
	public CampfireBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.itemsBeingCooked = DefaultedList.ofSize(4, ItemStack.EMPTY);
	}
	
	@Inject(method = "litServerTick", at = @At("TAIL"))
	private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
		for (int i = 0; i < campfire.itemsBeingCooked.size(); ++i) {
			ItemStack itemStack = campfire.itemsBeingCooked.get(i);
			if (!itemStack.isEmpty() && itemStack.getItem().equals(PermafrozenItems.FIR_PINECONE)) {
				List<LivingEntity> entities = Objects.requireNonNull(campfire.getWorld()).getEntitiesByClass(LivingEntity.class, new Box(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3), (LivingEntity) -> true);
				for (LivingEntity nearbyEntity : entities) {
					if (nearbyEntity instanceof PlayerEntity) {
						nearbyEntity.addStatusEffect(new StatusEffectInstance(PermafrozenEffects.FRAGRANT, 1000, 0));
					}
				}
			}
		}
	}
	
	@Override
	public void clear() {
		this.itemsBeingCooked.clear();
	}
}
