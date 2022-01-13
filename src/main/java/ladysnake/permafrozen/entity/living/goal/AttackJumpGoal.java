package ladysnake.permafrozen.entity.living.goal;

import ladysnake.permafrozen.entity.living.intface.Lunger;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class AttackJumpGoal extends MeleeAttackGoal {
    private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
    private final WaterCreatureEntity creature;
    private boolean inWater;

    public AttackJumpGoal(WaterCreatureEntity creature, double speed, boolean pauseWhenMobIdle) {
        super(creature, speed, pauseWhenMobIdle);
        this.creature = creature;
        this.setControls(EnumSet.of(Control.MOVE, Control.JUMP));
    }

    public boolean canStart() {
        if(creature.getTarget() != null && (creature.getRandom().nextInt(7) == 0)) {
            Direction direction = this.creature.getMovementDirection();
            int i = direction.getOffsetX();
            int j = direction.getOffsetZ();
            BlockPos blockPos = this.creature.getBlockPos();

            for (int k : OFFSET_MULTIPLIERS) {
                if (!this.isWater(blockPos, i, j, k) || !this.isAirAbove(blockPos, i, j, k)) {
                    return creature.squaredDistanceTo(creature.getTarget()) > 4.0D;
                }
            }

            return true;
        } else {
            return false;
        }

    }

    private boolean isWater(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        BlockPos blockPos = pos.add(offsetX * multiplier, 0, offsetZ * multiplier);
        return this.creature.world.getFluidState(blockPos).isIn(FluidTags.WATER) && !this.creature.world.getBlockState(blockPos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        return this.creature.world.getBlockState(pos.add(offsetX * multiplier, 1, offsetZ * multiplier)).isAir() && this.creature.world.getBlockState(pos.add(offsetX * multiplier, 2, offsetZ * multiplier)).isAir();
    }

    public boolean shouldContinue() {
        double d = this.creature.getVelocity().y;
        return (!(d * d < 0.029999999329447746D) || this.creature.getPitch() == 0.0F || !(Math.abs(this.creature.getPitch()) < 10.0F) || !this.creature.isTouchingWater()) && !this.creature.isOnGround() && ((Lunger) creature).getLunging();
    }

    public boolean canStop() {
        return false;
    }

    public void start() {
        LivingEntity livingEntity = creature.getTarget();
        assert livingEntity != null;
        Vec3d vec3d = livingEntity.getEyePos();
        creature.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        creature.bodyYaw = (float) Math.atan2(livingEntity.getX() - creature.getX(), livingEntity.getZ() - creature.getZ());
        Direction direction = this.creature.getMovementDirection();
        this.creature.setVelocity(this.creature.getVelocity().add((double)direction.getOffsetX() * 0.6D, 0.7D, (double)direction.getOffsetZ() * 0.6D));
        this.creature.getNavigation().stop();
        ((Lunger) creature).setLunging(true);
    }

    public void stop() {
        ((Lunger) creature).setLunging(false);
    }

    public void tick() {
        boolean bl = this.inWater;
        if (!bl) {
            FluidState fluidState = this.creature.world.getFluidState(this.creature.getBlockPos());
            this.inWater = fluidState.isIn(FluidTags.WATER);
        }

        LivingEntity livingEntity = creature.getTarget();
        assert livingEntity != null;
        if (creature.getTarget() != null && creature.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
            creature.tryAttack(livingEntity);
        }

        Vec3d vec3d = this.creature.getVelocity();
        if (vec3d.y * vec3d.y < 0.029999999329447746D && this.creature.getPitch() != 0.0F) {
            this.creature.setPitch(MathHelper.lerpAngle(this.creature.getPitch(), 0.0F, 0.2F));
        } else if (vec3d.length() > 9.999999747378752E-6D) {
            double d = vec3d.horizontalLength();
            double e = Math.atan2(-vec3d.y, d) * 57.2957763671875D;
            this.creature.setPitch((float)e);
        }

    }
}
