package net.permafrozen.permafrozen.client.entity.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.client.particle.aurora.AuroraParticleEffect;
import net.permafrozen.permafrozen.registry.PermafrozenEntities;

import java.util.Random;

public class AltarBlockEntity extends BlockEntity implements ClientPlayerTickable {
    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(PermafrozenEntities.ALTAR_ENTITY, pos, state);
    }

    @Override
    public void tick() {
        for (int i = 0; i < (360); i++) {
            assert this.getWorld() != null;
            this.getWorld().addParticle( new AuroraParticleEffect(0.1f, 1, 0, -0.1f, -0.01f, 0.1f), this.getPos().getX() - 0.5 + Math.cos(i), this.getPos().getY(), this.getPos().getZ() - 0.5 + Math.sin(i), 0, 0.4, 0);
        }
    }

}
