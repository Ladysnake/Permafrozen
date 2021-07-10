package net.permafrozen.permafrozen.pain;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;
import net.permafrozen.permafrozen.entity.nudifae.NudifaeType;

import static net.minecraft.entity.data.TrackedDataHandlerRegistry.register;

public class PermafrozenDataHandlers {
    public static final TrackedDataHandler<NudifaeType> NUDIFAE_TYPE = new TrackedDataHandler<>() {
        public void write(PacketByteBuf packetByteBuf, NudifaeType nudifaeType) {
            packetByteBuf.writeEnumConstant(nudifaeType);
        }
    
        public NudifaeType read(PacketByteBuf packetByteBuf) {
            return packetByteBuf.readEnumConstant(NudifaeType.class);
        }
    
        public NudifaeType copy(NudifaeType flonshed) {
            return flonshed;
        }
    };

    static {
        register(NUDIFAE_TYPE);
    }
}
