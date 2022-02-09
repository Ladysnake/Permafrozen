package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SnowstormWorldComponent implements AutoSyncedComponent {
    private int ticksSinceSnowstorm;
    private int ticksSinceNoSnowstorm;
    private boolean isSnowstorming = false;
    private final World world;
    public SnowstormWorldComponent(World newWorld) {
        world = newWorld;
    }
    public void setSnowstorming(boolean snow) {
        isSnowstorming = snow;
        PermafrozenComponents.SNOWSTORM.sync(world);
    }

    public boolean isSnowstorming() {
        return this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY) && isSnowstorming;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        tag.putInt("snowstormTicks", ticksSinceSnowstorm);
        tag.putInt("noSnowstormTicks", ticksSinceNoSnowstorm);
        tag.putBoolean("snowstorm", isSnowstorming);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        ticksSinceSnowstorm = tag.getInt("snowstormTicks");
        ticksSinceNoSnowstorm = tag.getInt("noSnowstormTicks");
        isSnowstorming = tag.getBoolean("snowstorm");
    }

}
