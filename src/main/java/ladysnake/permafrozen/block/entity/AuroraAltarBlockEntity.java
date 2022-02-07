package ladysnake.permafrozen.block.entity;

import ladysnake.permafrozen.block.AuroraAltarBlock;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
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

public class AuroraAltarBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static float yaw;
    public static float prevYaw;

    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    public static final AnimationBuilder ACTIVE = new AnimationBuilder().addAnimation("active");
    public static final AnimationBuilder ACTIVATE = new AnimationBuilder().addAnimation("open");
    private static final int ANIM_ACTIVATE = 0;

    public AuroraAltarBlockEntity(BlockPos pos, BlockState state) {
        super(PermafrozenEntities.AURORA_ALTAR_TYPE, pos, state);
        yaw = 0;
    }
    private boolean isActive() {
        return this.getCachedState().get(AuroraAltarBlock.LIT);
    }
    public static void tick(World tickerWorld, BlockPos pos, BlockState tickerState, AuroraAltarBlockEntity blockEntity) {
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
    public AnimationFactory getFactory() {
        return factory;
    }
}
