package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SnowstormWorldComponent implements AutoSyncedComponent, ServerTickingComponent {
    private boolean isSnowstorming;
    private int timeLeft;
    private int transitionTicks;
    private World world;
    public SnowstormWorldComponent(World constrWorld) {
        world = constrWorld;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
        PermafrozenComponents.SNOWSTORM.sync(world);
    }

    public boolean isSnowstorming() {
        return isSnowstorming;
    }

    public void startSnowstorm(World world, int duration) {
        if (!this.isSnowstorming) {
            this.isSnowstorming = true;
            this.transitionTicks = 0;
            this.timeLeft = duration;
            this.world = world;
            PermafrozenComponents.SNOWSTORM.sync(world);
        }
    }

    public void stopSnowstorm(World world) {
        if (this.isSnowstorming) {
            this.isSnowstorming = false;
            this.timeLeft = 0;
            this.world = world;
            PermafrozenComponents.SNOWSTORM.sync(world);
        }
    }

    @Override
    public void readFromNbt(NbtCompound compoundTag) {
        this.isSnowstorming = compoundTag.getBoolean("SnowstormOngoing");
        setTimeLeft(compoundTag.getInt("SnowstormTimeLeft"));
        setTransitionTicks(compoundTag.getInt("TransTicks"));
    }

    @Override
    public void writeToNbt(NbtCompound compoundTag) {
        compoundTag.putBoolean("SnowstormOngoing", this.isSnowstorming);
        compoundTag.putInt("SnowstormTimeLeft", this.timeLeft);
        compoundTag.putInt("TransTicks", this.transitionTicks);
    }

    public int getTransitionTicks() {
        return transitionTicks;
    }

    public void setTransitionTicks(int transitionTicks) {
        this.transitionTicks = transitionTicks;
        PermafrozenComponents.SNOWSTORM.sync(world);
    }

    @Override
    public void serverTick() {
        if(isSnowstorming() && getTransitionTicks() < 120) {
            setTransitionTicks(getTransitionTicks() + 1);
        }
        if(getTimeLeft() > 0) {
            setTimeLeft(getTimeLeft() - 1);
        }
        if(isSnowstorming() && getTimeLeft() <= 0){
            setTransitionTicks(getTransitionTicks() - 2);
            System.out.println(getTransitionTicks());
            if(getTransitionTicks() <= 0) {
                stopSnowstorm(world);
            }
        }
    }
}
