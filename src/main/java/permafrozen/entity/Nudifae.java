package permafrozen.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import permafrozen.entity.nudifae.NudifaeType;
import permafrozen.util.DataSerializers;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import java.util.EnumSet;

import static net.minecraft.entity.ai.attributes.Attributes.*;

public class Nudifae extends TameableEntity implements IAnimatable {

    public static final DataParameter<NudifaeType> TYPE = EntityDataManager.createKey(Nudifae.class, DataSerializers.NUDIFAE_TYPE);
    private AnimationFactory factory = new AnimationFactory(this);

    public static AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle");
    public static AnimationBuilder FLOAT = new AnimationBuilder().addAnimation("float");
    public static AnimationBuilder SLEEP = new AnimationBuilder().addAnimation("sleep");
    public static AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");
    public static AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");

    public Nudifae(EntityType<? extends Nudifae> type, World worldIn) {

        super(type, worldIn);

        //this.moveController = new Nudifae.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);

        this.experienceValue = 0;

    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {

        return MobEntity.func_233666_p_()
                .createMutableAttribute(MAX_HEALTH, 10)
                .createMutableAttribute(MOVEMENT_SPEED, 0.285)
                .createMutableAttribute(ATTACK_DAMAGE, 2);

    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public NudifaeType getNudifaeType() {
        return this.dataManager.get(TYPE);
    }

    public void setNudifaeType(NudifaeType type) {
        this.dataManager.set(TYPE, type);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int type = NudifaeType.getRandom(this.rand).id;

        if (spawnDataIn instanceof Nudifae.NudifaeData) {
            type = ((Nudifae.NudifaeData) spawnDataIn).typeData;
        } else {
            spawnDataIn = new Nudifae.NudifaeData(type);
        }

        this.setNudifaeType(NudifaeType.getTypeById(type));
        return spawnDataIn;


    }

    // Animation things
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        //String animname = event.getController().getCurrentAnimation() != null ? event.getController().getCurrentAnimation().animationName : "";
        boolean isInWater = isInWater();
        boolean isMoving = isInWater ? !(limbSwingAmount > -0.02) || !(limbSwingAmount < 0.02) : !(limbSwingAmount > -0.10F) || !(limbSwingAmount < 0.10F);
        AnimationBuilder anim = isInWater ? FLOAT : IDLE;
        if (isMoving) {
            anim = isInWater ? SWIM : WALK;
        }
        event.getController().setAnimation(anim);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController controller = new AnimationController(this, "controller", 2, this::predicate);
        /*controller.registerSoundListener(this::soundListener);
        controller.registerParticleListener(this::particleListener);*/
        animationData.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    // AI
    protected void registerGoals() {
        //this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new Nudifae.PlayerTemptGoal(this, 1.0D, new ResourceLocation("minecraft", "coral_blocks")));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        //this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(10, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(11, new Nudifae.WanderGoal(this, 1.0D));
        this.goalSelector.addGoal(12, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(12, new LookAtGoal(this, PlayerEntity.class, 10.0F));

    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return 1.5F;
        }
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new Nudifae.Navigator(this, world);
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    // Data stuff
    static class NudifaeData implements ILivingEntityData {
        public final int typeData;

        public NudifaeData(int type) {
            this.typeData = type;
        }
    }

    @Override
    protected void registerData() {

        super.registerData();
        this.dataManager.register(TYPE, NudifaeType.BLUE);

    }

    @Override
    public void writeAdditional(CompoundNBT compound) {

        super.writeAdditional(compound);
        compound.putInt("NudifaeType", this.getNudifaeType().id);

    }

    @Override
    public void readAdditional(CompoundNBT compound) {

        super.readAdditional(compound);
        this.setNudifaeType(NudifaeType.getTypeById(compound.getInt("NudifaeType")));

    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.6F;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return new EntitySize(0.5F, 0.4F, true);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BlockTags.getCollection().get(new ResourceLocation("minecraft", "coral_blocks")).contains(Block.getBlockFromItem(stack.getItem()));
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    static class Navigator extends SwimmerPathNavigator {

        Navigator(Nudifae nudifae, World worldIn) {
            super(nudifae, worldIn);
        }

        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof Nudifae) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
            else return !this.world.getBlockState(pos.down()).isAir();
        }

    }

    static class WanderGoal extends RandomWalkingGoal {
        private final Nudifae nudifae;

        private WanderGoal(Nudifae nudifae, double speedIn) {
            super(nudifae, speedIn);
            this.nudifae = nudifae;
        }

        public boolean shouldExecute() {
            return !this.creature.isInWater() && super.shouldExecute();
        }
    }

    static class PlayerTemptGoal extends Goal {
        private static final EntityPredicate field_220834_a = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable();
        private final Nudifae nudifae;
        private final double speed;
        private PlayerEntity tempter;
        private final ITag<Block> temptItems;

        PlayerTemptGoal(Nudifae nudifae, double speedIn, ResourceLocation temptItemTypes) {
            this.nudifae = nudifae;
            this.speed = speedIn;
            this.temptItems = BlockTags.getCollection().get(temptItemTypes);
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean shouldExecute() {
            this.tempter = this.nudifae.world.getClosestPlayer(field_220834_a, this.nudifae);
            if (this.tempter == null) {
                return false;
            } else {
                return this.isTemptedBy(this.tempter.getHeldItemMainhand()) || this.isTemptedBy(this.tempter.getHeldItemOffhand());
            }

        }

        private boolean isTemptedBy(ItemStack item) {
            return this.temptItems.contains(Block.getBlockFromItem(item.getItem()));
        }

        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        public void resetTask() {
            this.tempter = null;
            this.nudifae.getNavigator().clearPath();
        }

        public void tick() {
            this.nudifae.getLookController().setLookPositionWithEntity(this.tempter, (float)(this.nudifae.getHorizontalFaceSpeed() + 20), (float)this.nudifae.getVerticalFaceSpeed());
            if (this.nudifae.getDistanceSq(this.tempter) < 6.25D) {
                this.nudifae.getNavigator().clearPath();
            } else {
                this.nudifae.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }

        }
    }
}
