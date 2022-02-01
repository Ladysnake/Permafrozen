package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import ladysnake.permafrozen.registry.PermafrozenGamerules;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Objects;

public class SnowstormWorldComponent implements AutoSyncedComponent {
    private int ticksSinceSnowstorm;
    private int ticksSinceNoSnowstorm;
    private boolean isSnowstorming;
    private final World world;
    public SnowstormWorldComponent(World newWorld) {
        world = newWorld;
    }
    public void tick() {
        SnowstormWorldComponent component = PermafrozenComponents.SNOWSTORM.get(world);
        if(world.getGameRules().get(PermafrozenGamerules.ALWAYS_SNOWSTORM).get()) {
            component.setSnowstorming(true);
        }
        if(this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY)) {
            if(isSnowstorming) {
                ticksSinceNoSnowstorm++;
                if (ticksSinceNoSnowstorm >= 6000 && world.getRandom().nextInt(420) == 69) {
                    ticksSinceNoSnowstorm = 0;
                    component.setSnowstorming(false);
                }
            } else{
                ticksSinceSnowstorm++;
                if (ticksSinceSnowstorm >= 12000 && world.getRandom().nextInt(690) == 69) {
                    ticksSinceSnowstorm = 0;
                    component.setSnowstorming(true);
                }
            }
        }
        PermafrozenComponents.SNOWSTORM.sync(world);
    }
    public void setSnowstorming(boolean snow) {
        isSnowstorming = snow;
        PermafrozenComponents.SNOWSTORM.sync(world);
    }

    public boolean isSnowstorming() {
        return isSnowstorming;
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
