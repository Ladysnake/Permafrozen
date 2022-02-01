package ladysnake.permafrozen.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.registry.PermafrozenComponents;
import ladysnake.permafrozen.util.PlayerUtil;
import ladysnake.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Objects;

public class PFPlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity obj;
    private int fenTicks;
    private int outsideTicks;
    private int prevFenTicks;
    private int prevOutsideTicks;
    private int ticks;
    private int temperature;
    public PFPlayerComponent(PlayerEntity obj) {
        this.obj = obj;
    }
    @Override
    public void readFromNbt(NbtCompound tag) {
        temperature = tag.getInt("temp");
        outsideTicks = tag.getInt("outside");
        fenTicks = tag.getInt("fen");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("temp", getTemperature());
        tag.putInt("outside", outsideTicks);
        tag.putInt("fen", fenTicks);
    }
    public void tick() {
        if (!obj.world.isClient && obj.getEntityWorld() == Objects.requireNonNull(obj.getEntityWorld().getServer()).getWorld(Permafrozen.WORLD_KEY)) {
            if(!obj.isCreative() && !obj.isSpectator() && obj.isAlive()) {
                ticks++;
                if (ticks >= 20) {
                    if ((PlayerUtil.isWarmBlockNearby(obj) || obj.isOnFire()) && temperature < 36) {
                        temperature++;
                    } else if (outsideTicks >= 10) {
                        temperature--;
                    } else {
                        if (obj.getRandom().nextInt(200) == 69) {
                            temperature--;
                        }
                    }
                    if (temperature < 2 && !obj.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !obj.getEquippedStack(EquipmentSlot.CHEST).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !obj.getEquippedStack(EquipmentSlot.LEGS).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !obj.getEquippedStack(EquipmentSlot.FEET).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES)) {
                        obj.setFrozenTicks(Math.min(obj.getMinFreezeDamageTicks() * 4, obj.getFrozenTicks() - temperature * 4));
                    }
                    ticks = 0;
                }
            }
            prevFenTicks = fenTicks;
            if(obj.getWorld().getBiomeKey(obj.getBlockPos()).isPresent() && obj.getWorld().getBiomeKey(obj.getBlockPos()).get() == PermafrozenBiomes.FRIGID_FEN) {
                fenTicks++;
            } else {
                fenTicks--;
            }
            prevOutsideTicks = outsideTicks;
            if(isOutside(obj, obj.getWorld())) {
                outsideTicks++;
            } else {
                outsideTicks--;
            }

            temperature = MathHelper.clamp(temperature, -40, 36);
            outsideTicks = MathHelper.clamp(outsideTicks, 0, 80);
            fenTicks = MathHelper.clamp(fenTicks, 0, 80);
        }

    }

    @Override
    public void serverTick() {
       tick();
    }
    public boolean isOutside(PlayerEntity entity, World world) {
        boolean canSeeSky = false;
        for(int i = 0; i < 5; i++) {
            for(int k = 0; k < 5; k++) {
                boolean temp = world.isSkyVisible(entity.getBlockPos().add(-2, 1, -2).add(i, 0, k));
                if(temp) {
                    canSeeSky = true;
                    break;
                }
            }
        }
        return canSeeSky;
    }

    public float getFenTicks(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevFenTicks, fenTicks);
    }

    public float getOutsideTicks(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevOutsideTicks, outsideTicks);
    }

    public int getTemperature() {
        return temperature;
    }
}
