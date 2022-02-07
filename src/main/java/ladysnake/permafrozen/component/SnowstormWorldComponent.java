package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import ladysnake.permafrozen.registry.PermafrozenGamerules;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;

public class SnowstormWorldComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    private int ticksSinceSnowstorm;
    private int ticksSinceNoSnowstorm;
    private boolean isSnowstorming;
    private final World world;
    public SnowstormWorldComponent(World newWorld) {
        world = newWorld;
        isSnowstorming = false;
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
    }

    public boolean isSnowstorming() {
        return this.world.getRegistryKey().equals(Permafrozen.WORLD_KEY) && isSnowstorming;
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

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        AutoSyncedComponent.super.applySyncPacket(buf);
    }

    @Override
    public void serverTick() {
        tick();
    }

    @Override
    public void clientTick() {
        tick();
    }
}
