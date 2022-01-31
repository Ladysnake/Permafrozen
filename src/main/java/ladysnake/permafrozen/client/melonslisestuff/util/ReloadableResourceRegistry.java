package ladysnake.permafrozen.client.melonslisestuff.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author Melonslise
 * https://github.com/Melonslise/NuclearWinter/blob/main/src/main/java/melonslise/nwinter/client/util/ReloadableResourceRegistry.java
 * **/
@Environment(EnvType.CLIENT)
public abstract class ReloadableResourceRegistry<T extends AutoCloseable> implements IdentifiableResourceReloadListener, SynchronousResourceReloader
{
    protected final List<T> elements;

    protected ReloadableResourceRegistry(int expectedSize)
    {
        this.elements = new ArrayList<>(expectedSize);
    }

    protected abstract void load(ResourceManager resourceManager) throws Exception;

    protected void clear() throws Exception
    {
        for (T element : this.elements)
        {
            element.close();
        }
        this.elements.clear();
    }

//    @Override
//    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
//        try
//        {
//            this.clear();
//            this.load(manager);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return new CompletableFuture<Void>();
//    }

    @Override
    public void reload(ResourceManager manager) {
        try
        {
            this.clear();
            this.load(manager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
