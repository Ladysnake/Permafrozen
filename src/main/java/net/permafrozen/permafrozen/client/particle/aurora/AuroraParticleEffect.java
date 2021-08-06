package net.permafrozen.permafrozen.client.particle.aurora;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.PermafrozenClient;

import java.util.Locale;

public class AuroraParticleEffect implements ParticleEffect {
    public static final Codec<AuroraParticleEffect> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.FLOAT.fieldOf("r").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.red;
        }), Codec.FLOAT.fieldOf("g").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.green;
        }), Codec.FLOAT.fieldOf("b").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.blue;
        }), Codec.FLOAT.fieldOf("re").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.redEvolution;
        }), Codec.FLOAT.fieldOf("ge").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.greenEvolution;
        }), Codec.FLOAT.fieldOf("be").forGetter((wispTrailParticleEffect) -> {
            return wispTrailParticleEffect.blueEvolution;
        })).apply(instance, AuroraParticleEffect::new);
    });
    public static final ParticleEffect.Factory<AuroraParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<AuroraParticleEffect>() {
        public AuroraParticleEffect read(ParticleType<AuroraParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float r = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float g = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float b = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float re = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float ge = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float be = (float) stringReader.readDouble();
            return new AuroraParticleEffect(r, g, b, re, ge, be);
        }

        public AuroraParticleEffect read(ParticleType<AuroraParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new AuroraParticleEffect(packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float redEvolution;
    private final float greenEvolution;
    private final float blueEvolution;

    public AuroraParticleEffect(float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.redEvolution = redEvolution;
        this.greenEvolution = greenEvolution;
        this.blueEvolution = blueEvolution;
    }

    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.redEvolution);
        buf.writeFloat(this.greenEvolution);
        buf.writeFloat(this.blueEvolution);
    }

    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
    }

    public ParticleType<AuroraParticleEffect> getType() {
        return PermafrozenClient.AURORA_SMOL;
    }

    @Environment(EnvType.CLIENT)
    public float getRed() {
        return this.red;
    }

    @Environment(EnvType.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @Environment(EnvType.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @Environment(EnvType.CLIENT)
    public float getRedEvolution() {
        return redEvolution;
    }

    @Environment(EnvType.CLIENT)
    public float getGreenEvolution() {
        return greenEvolution;
    }

    @Environment(EnvType.CLIENT)
    public float getBlueEvolution() {
        return blueEvolution;
    }
}
