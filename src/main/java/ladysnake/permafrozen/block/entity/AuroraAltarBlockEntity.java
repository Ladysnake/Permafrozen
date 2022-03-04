package ladysnake.permafrozen.block.entity;

import ladysnake.permafrozen.Permafrozen;
import ladysnake.permafrozen.PermafrozenClient;
import ladysnake.permafrozen.block.AuroraAltarBlock;
import ladysnake.permafrozen.client.particle.aurora.AuroraParticleEffect;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import software.bernie.example.block.BotariumBlock;
import software.bernie.example.block.tile.BotariumTileEntity;
import software.bernie.example.block.tile.FertilizerTileEntity;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class AuroraAltarBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public int teleportTicks = 0;

    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    private static final int ANIM_ACTIVATE = 0;

    public AuroraAltarBlockEntity(BlockPos pos, BlockState state) {
        super(PermafrozenEntities.AURORA_ALTAR_TYPE, pos, state);
    }
    private boolean isActive() {
        return this.getCachedState().get(AuroraAltarBlock.LIT);
    }

    public static void tick(World tickerWorld, BlockPos pos, BlockState tickerState, AuroraAltarBlockEntity blockEntity) {
        if(blockEntity.isActive() && tickerState.get(AuroraAltarBlock.LIT)) {
            tickerWorld.addParticle(new AuroraParticleEffect(0.1f, 1.0f, 0.3f, 0.02f, -0.08f, 0.08f), pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5, 0, 1, 0);
            blockEntity.teleportTicks++;
            for(PlayerEntity player : tickerWorld.getEntitiesByClass(PlayerEntity.class, new Box(blockEntity.pos).expand(10), LivingEntity::isAlive)) {
                player.setVelocity(Vec3d.ZERO);
                Vec3d targetVec = new Vec3d(pos.getX() + 0.5 - player.getX(), pos.getY() - 0.2 - player.getY(), pos.getZ() + 0.5 - player.getZ()).normalize();
                double g = Math.sqrt(targetVec.x * targetVec.x + targetVec.z * targetVec.z);
                player.setYaw(MathHelper.wrapDegrees((float)(MathHelper.atan2(targetVec.z, targetVec.x) * 57.2957763671875) - 90.0f));
                player.setPitch(MathHelper.wrapDegrees((float)(-(MathHelper.atan2(targetVec.y, g) * 57.2957763671875))));
                if(player.canUsePortals() && !player.hasVehicle() && !player.hasPassengers()&& player.getServer() != null && player.getServer().getWorld(Permafrozen.WORLD_KEY) != null) {
                    ServerWorld world = player.world.getRegistryKey().equals(Permafrozen.WORLD_KEY) ? player.getServer().getOverworld() : player.getServer().getWorld(Permafrozen.WORLD_KEY);
                    ServerWorld current = player.world.getRegistryKey().equals(Permafrozen.WORLD_KEY) ? player.getServer().getWorld(Permafrozen.WORLD_KEY) : player.getServer().getOverworld();
                    int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, MathHelper.ceil((player.getX() / current.getDimension().getCoordinateScale() * world.getDimension().getCoordinateScale())), MathHelper.ceil((player.getZ()) / current.getDimension().getCoordinateScale() * world.getDimension().getCoordinateScale()));
                    if(blockEntity.teleportTicks >= 160) {
                        FabricDimensions.teleport(player, world, new TeleportTarget(new Vec3d((player.getX() / current.getDimension().getCoordinateScale() * world.getDimension().getCoordinateScale()), topY < 0 ? 200 : topY, (player.getZ()) / current.getDimension().getCoordinateScale() * world.getDimension().getCoordinateScale()), Vec3d.ZERO, player.getYaw(), player.getPitch()));
                        if (topY < 0) {
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 150, 9));
                        }

                    }
                }
                tickerWorld.addParticle(new AuroraParticleEffect(0.1f, 1.0f, 0.3f, 0.02f, -0.08f, 0.08f), player.getX(), player.getY() + 2, player.getZ(), 0, 1, 0);
            }
            if(blockEntity.teleportTicks >= 160) {
                blockEntity.teleportTicks = 0;
                tickerWorld.setBlockState(pos, tickerState.with(AuroraAltarBlock.LIT, false));
            }
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, animationEvent -> {
            AnimationBuilder anime = new AnimationBuilder().clearAnimations();
            animationEvent.getController().getAnimationState();
            anime = IDLE;
            if(isActive()) {
                animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("open", false).addAnimation("active", true));
            } else {
                animationEvent.getController().setAnimation(anime);
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        teleportTicks = nbt.getInt("tpTicks");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("tpTicks", teleportTicks);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
