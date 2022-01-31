package ladysnake.permafrozen.mixin;

import ladysnake.permafrozen.client.melonslisestuff.extension.IExtendedCompiledChunk;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public class RebuildTaskMixin {
    @Unique
    private BlockPos originTemp;

    @Unique
    private IExtendedCompiledChunk compiledChunkTemp;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;add(III)Lnet/minecraft/util/math/BlockPos;"), method = "render", locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectRenderBeforeOffset(float cameraX, float cameraY, float cameraZ, ChunkBuilder.ChunkData data, BlockBufferBuilderStorage buffers, CallbackInfoReturnable<Set<BlockEntity>> cir, int i, BlockPos blockPos) {
        this.compiledChunkTemp = (IExtendedCompiledChunk) data;
        this.originTemp = blockPos;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkRendererRegion;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private BlockState redirectCompileGetBlockState(ChunkRendererRegion instance, BlockPos pos)
    {
        long ptr = this.compiledChunkTemp.getSkylightBuffer();

        int xOff = pos.getX() - this.originTemp.getX();
        int yOffFull = pos.getY() - this.originTemp.getY();
        int zOff = pos.getZ() - this.originTemp.getZ();

        // Because we're packing 4 blocks vertically into 1 pixel
        int yOff = yOffFull / 4;
        int yOff2 = yOffFull % 4;

        long ptrOff = xOff + yOff * 16 + zOff * 4 * 16;

        int skylight = instance.getLightLevel(LightType.SKY, pos);

        if(skylight == 0)
        {
            return instance.getBlockState(pos);
        }

        int value = yOff2 == 0 ? 0 : MemoryUtil.memGetByte(ptr + ptrOff);
        value |= skylight << yOff2;
        MemoryUtil.memPutByte(ptr + ptrOff, (byte) value);

        return instance.getBlockState(pos);
    }
}
