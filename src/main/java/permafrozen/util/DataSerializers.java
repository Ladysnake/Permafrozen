package permafrozen.util;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import permafrozen.Permafrozen;
import permafrozen.entity.nudifae.NudifaeType;

public class DataSerializers {
    
    public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, Permafrozen.MOD_ID);

    public static final IDataSerializer<NudifaeType> NUDIFAE_TYPE = new IDataSerializer<NudifaeType>() {
        public void write(PacketBuffer buf, NudifaeType value) {
            buf.writeEnumValue(value);
        }

        public NudifaeType read(PacketBuffer buf) {
            return buf.readEnumValue(NudifaeType.class);
        }

        public NudifaeType copyValue(NudifaeType type) {
            return type;
        }
    };

    static {
        SERIALIZERS.register("nudifae_type", () -> new DataSerializerEntry(NUDIFAE_TYPE));
    }
}