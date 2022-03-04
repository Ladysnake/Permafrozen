package ladysnake.permafrozen.mixin;

import ladysnake.permafrozen.block.StrippableDeadwoodBlock;
import ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Heightmap.class)
public class HeightmapMixin {
    @Inject(method = "trackUpdate", at = @At("HEAD"), cancellable = true)
    private void prettifyInject(int x, int y, int z, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        int i = ((Heightmap)(Object)this).get(x, z);
        if (y <= i - 2) {
            cir.setReturnValue(false);
        }
        if (((Heightmap)(Object)this).blockPredicate.test(state) && (((Heightmap)(Object)this).blockPredicate.test(Blocks.OAK_LEAVES.getDefaultState()) || (!((Heightmap)(Object)this).blockPredicate.test(Blocks.OAK_LEAVES.getDefaultState()) && !((state.getBlock() instanceof StrippableDeadwoodBlock) || state.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_LOG) || state.getBlock().equals(PermafrozenBlocks.DEADWOOD_THORN) || state.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_WOOD) || state.getBlock().equals(PermafrozenBlocks.SPECTRAL_CAP_BLOCK))))) {
            if (y >= i) {
                ((Heightmap)(Object)this).set(x, z, y + 1);
                cir.setReturnValue(true);
            }
        } else if (i - 1 == y) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int j = y - 1; j >= ((Heightmap)(Object)this).chunk.getBottomY(); --j) {
                mutable.set(x, j, z);
                if (!((Heightmap)(Object)this).blockPredicate.test(((Heightmap)(Object)this).chunk.getBlockState(mutable)) && (((Heightmap)(Object)this).blockPredicate.test(Blocks.OAK_LEAVES.getDefaultState()) || (!((Heightmap)(Object)this).blockPredicate.test(Blocks.OAK_LEAVES.getDefaultState()) && ((state.getBlock() instanceof StrippableDeadwoodBlock)|| state.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_LOG) || state.getBlock().equals(PermafrozenBlocks.DEADWOOD_THORN) || state.getBlock().equals(PermafrozenBlocks.SPIRESHROOM_WOOD) || state.getBlock().equals(PermafrozenBlocks.SPECTRAL_CAP_BLOCK))))) continue;
                ((Heightmap)(Object)this).set(x, z, j + 1);
                cir.setReturnValue(true);
            }
            ((Heightmap)(Object)this).set(x, z, ((Heightmap)(Object)this).chunk.getBottomY());
            cir.setReturnValue(true);
        }
        cir.setReturnValue(false);
    }
}
