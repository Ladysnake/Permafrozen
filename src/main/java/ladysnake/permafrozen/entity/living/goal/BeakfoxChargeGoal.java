package ladysnake.permafrozen.entity.living.goal;

import ladysnake.permafrozen.entity.living.BeakfoxEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class BeakfoxChargeGoal extends Goal {
    private final BeakfoxEntity obj;
    private Vec3d chargeVec;
    public BeakfoxChargeGoal(BeakfoxEntity entity) {
        this.obj = entity;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        return this.obj.getAttackState() == 1 && this.obj.isAlive() && obj.getTarget() != null;
    }

    @Override
    public void start() {
        super.start();
        LivingEntity target = obj.getTarget();
        Vec3d vec3d = this.obj.getVelocity();
        Vec3d vec3d2 = new Vec3d(target.getX() - obj.getX(), 0.0, target.getZ() - this.obj.getZ());
        chargeVec = vec3d2.normalize().multiply(1).add(vec3d);
    }

    @Override
    public void tick() {
        int ticks = obj.chargeTicks;
        LivingEntity target = obj.getTarget();
        this.obj.lookAtEntity(this.obj.getTarget(), 360, 360);
        if(ticks % 2 == 0) {
            if(chargeVec == null) {
                Vec3d vec3d = this.obj.getVelocity();
                Vec3d vec3d2 = new Vec3d(target.getX() - obj.getX(), 0.0, target.getZ() - this.obj.getZ());
                chargeVec = vec3d2.normalize().multiply(1).add(vec3d);
            }

            this.obj.setVelocity(chargeVec.x, target.getY() - this.obj.getY(), chargeVec.z);
            this.obj.getNavigation().startMovingTo(obj.getTarget(), 1);
        }
            List<LivingEntity> entities = obj.getWorld().getEntitiesByClass(LivingEntity.class, obj.getBoundingBox().expand(4, 3, 4).offset(obj.getRotationVector()), livingEntity -> livingEntity != obj && obj.distanceTo(livingEntity) <= 4 + livingEntity.getWidth() / 2 && livingEntity.getY() <= obj.getY() + 3);
            for(LivingEntity entity: entities) {
                Vec3d vec = entity.getPos().subtract(obj.getPos()).normalize().negate();
                entity.takeKnockback(1.2, vec.x, vec.z);
                entity.damage(DamageSource.mob(obj), 4);
            }

    }
}