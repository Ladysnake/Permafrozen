package net.permafrozen.permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CryingObsidianBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.permafrozen.permafrozen.PermafrozenClient;
import net.permafrozen.permafrozen.client.entity.block.AltarBlockEntity;
import net.permafrozen.permafrozen.client.particle.aurora.AuroraParticleEffect;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AuroraApexAltarBlock extends Block implements BlockEntityProvider {
    public static BooleanProperty ACTIVE;
    public AuroraApexAltarBlock(Settings settings) {
        super(settings);
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }
}
