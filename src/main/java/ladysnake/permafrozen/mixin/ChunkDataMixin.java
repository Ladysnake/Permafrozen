package ladysnake.permafrozen.mixin;

import ladysnake.permafrozen.client.melonslisestuff.extension.IExtendedCompiledChunk;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkBuilder.ChunkData.class)
public class ChunkDataMixin implements IExtendedCompiledChunk {
    @Unique
    private long skylightBufferPtr = MemoryUtil.nmemAlloc(16 * 4 * 16);

    @Override
    public long getSkylightBuffer()
    {
        return this.skylightBufferPtr;
    }

    @Override
    public void freeSkylightBuffer()
    {
        MemoryUtil.nmemFree(this.skylightBufferPtr);
        this.skylightBufferPtr = 0L;
    }
}
