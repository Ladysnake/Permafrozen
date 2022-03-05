package ladysnake.permafrozen.client.particle.snowflake;

import ladysnake.permafrozen.util.Wind;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SnowflakeParticle extends SpriteBillboardParticle {
    private static final int FADE_DURATION = 16; // Ticks
    private static final float WATER_FRICTION = 0.05f;

    protected final float windCoefficient; // To emulate drag/lift
    protected final float maxRotateSpeed; // Rotations / tick
    protected final int maxRotateTime;
    protected int rotateTime;
    protected boolean stuckInGround = false;

    protected SnowflakeParticle(ClientWorld clientWorld, double x, double y, double z,
                                double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);

        this.gravityStrength = 0.04f + random.nextFloat() * 0.02f;
        this.windCoefficient = 0.6f + random.nextFloat() * 0.4f;

        this.velocityX = Wind.get().getWindX() * 0.2f;
        this.velocityY *= 0.35f;
        this.velocityZ = Wind.get().getWindZ() * 0.2f;

        this.maxAge = 200;

        this.maxRotateTime = (3 + this.random.nextInt(5)) * 20;
        this.maxRotateSpeed = (this.random.nextBoolean() ? -1 : 1) * (0.1f + 2.4f * this.random.nextFloat()) * MathHelper.TAU / 20f;

        this.angle = this.prevAngle = this.random.nextFloat() * MathHelper.TAU;

        this.scale *= this.random.nextFloat() * 0.4f + 0.7f;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.prevAngle = angle;

        var pos = new BlockPos(this.x, this.y, this.z);

        this.age++;

        // fade-out animation
        if (this.age >= this.maxAge + 1 - FADE_DURATION) {
            this.alpha -= 1F / FADE_DURATION;
        }

        if (this.age >= this.maxAge) {
            this.markDead();
            return;
        }

        if (this.world.getFluidState(pos).isIn(FluidTags.WATER)) {
            // Float on water.
            this.velocityY = 0.0;
            this.rotateTime = 0;

            this.velocityX *= (1 - WATER_FRICTION);
            this.velocityZ *= (1 - WATER_FRICTION);
        } else {
            // Apply gravity
            this.velocityY -= 0.04 * this.gravityStrength;

            if (!this.onGround) {
                // Spin when in the air
                this.rotateTime = Math.min(this.rotateTime + 1, this.maxRotateTime);
                this.angle += (this.rotateTime / (float) this.maxRotateTime) * this.maxRotateSpeed;
            } else {
                this.rotateTime = 0;

                // velocityX *= (1 - FRICTION);
                // velocityZ *= (1 - FRICTION);
            }

            // Approach the target wind velocity over time via vel += (target - vel) * f, where f is in (0, 1)
            // after n ticks, the distance closes to a factor of 1 - (1 - f)^n.
            // for f = 1 / 2, it would only take 4 ticks to close the distance by 90%
            // for f = 1 / 60, it takes ~2 seconds to halve the distance, ~5 seconds to reach 80%
            //
            // the wind coefficient is just another factor in (0, 1) to add some variance between leaves.
            // this implementation lags behind the actual wind speed and will never reach it fully,
            // so wind speeds needs to be adjusted accordingly
            this.velocityX += (Wind.get().getWindX() - this.velocityX) * this.windCoefficient / 32.f;
            this.velocityZ += (Wind.get().getWindZ() - this.velocityZ) * this.windCoefficient / 32.f;
        }

        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    @Override
    public void move(double dx, double dy, double dz) {
        if (dx == 0.0 && dy == 0.0 && dz == 0.0) return;

        double oldDx = dx;
        double oldDy = dy;
        double oldDz = dz;

        Vec3d vec3d = Entity.adjustMovementForCollisions(null, new Vec3d(dx, dy, dz), this.getBoundingBox(), this.world, List.of());
        dx = vec3d.x;
        dy = vec3d.y;
        dz = vec3d.z;

        // Loose horizontal velocity on collision.
        if (oldDx != dx) this.velocityX = 0.0;
        if (oldDz != dz) this.velocityZ = 0.0;

        this.onGround = oldDy != dy && oldDy < 0.0;

        if (!this.onGround) {
            this.stuckInGround = false;
        } else {
            // Get stuck if slow enough.
            if (!this.stuckInGround && Math.abs(dy) < 1E-5) {
                this.stuckInGround = true;
            }
        }

        if (this.stuckInGround) {
            // Don't accumulate speed over time.
            this.velocityX = 0.0;
            this.velocityY = 0.0;
            this.velocityZ = 0.0;

            // Don't move.
            return;
        }

        if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld clientWorld, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            var random = clientWorld.random;
            var particle = new SnowflakeParticle(clientWorld, x, y, z, 0.f, random.nextDouble(), 0.f);
            particle.setSprite(this.spriteProvider());
            return particle;
        }
    }
}
