package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SnowstormWorldComponent implements AutoSyncedComponent {
    private int ticksSinceSnowstorm;
    private int ticksSinceNoSnowstorm;
    private boolean isSnowstorming;
    private final World world;
    public SnowstormWorldComponent(World newWorld) {
        world = newWorld;
    }
    public void tick() {
        if(this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            if(isSnowstorming) {
                ticksSinceNoSnowstorm++;
                if (ticksSinceNoSnowstorm >= 6000 && world.getRandom().nextInt(420) == 69) {
                    ticksSinceNoSnowstorm = 0;
                    isSnowstorming = false;
                }
            } else{
                ticksSinceSnowstorm++;
                if (ticksSinceSnowstorm >= 12000 && world.getRandom().nextInt(690) == 69) {
                    ticksSinceSnowstorm = 0;
                    isSnowstorming = true;
                }
            }
        }
        PermafrozenComponents.SNOWSTORM.sync(world);
    }

    public boolean isSnowstorming() {
        return true;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        tag.putInt("snowstormTicks", ticksSinceSnowstorm);
        tag.putInt("noSnowstormTicks", ticksSinceNoSnowstorm);
        tag.putBoolean("isSnowstorming", isSnowstorming);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        ticksSinceSnowstorm = tag.getInt("snowstormTicks");
        ticksSinceNoSnowstorm = tag.getInt("noSnowstormTicks");
        isSnowstorming = tag.getBoolean("isSnowstorming");
    }
}
