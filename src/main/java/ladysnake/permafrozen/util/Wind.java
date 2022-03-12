package ladysnake.permafrozen.util;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Simulates wind.
 * <p>
 * All credits go to Fourmisain who wrote this code for Falling Leaves, and LambdAurora who re-adapted it.
 * https://github.com/RandomMcSomethin/fallingleaves/blob/main/src/main/java/randommcsomethin/fallingleaves/util/Wind.java
 */
public class Wind {
    public static final long WIND_SEED = 0xa4505a;
    private static final Random RANDOM = new Random(WIND_SEED);
    private static Wind INSTANCE = new Wind();

    private float windX;
    private float windZ;
    private float prevWindX;
    private float prevWindZ;
    private SmoothNoise velocityNoise;
    private SmoothNoise directionTrendNoise;
    private SmoothNoise directionNoise;

    private boolean wasRaining;
    private boolean wasThundering;
    private State state;
    private State originalState;
    private int stateDuration; // Ticks

    public static Wind get() {
        return INSTANCE;
    }

    public static void use(Wind wind) {
        INSTANCE = wind;
    }

    protected Wind() {
        this.reset();
    }

    public void reset() {
        this.state = State.CALM;
        this.stateDuration = 0;

        this.wasRaining = this.wasThundering = false;

        this.windX = this.windZ = 0;

        this.velocityNoise = new SmoothNoise(2 * 20, 0, old -> this.state.getVelocityDistribution().sample());
        this.directionTrendNoise = new SmoothNoise(30 * 60 * 20, RANDOM.nextFloat() * MathHelper.TAU,
                old -> RANDOM.nextFloat() * MathHelper.TAU
        );
        this.directionNoise = new SmoothNoise(10 * 20, 0, old -> (2f * RANDOM.nextFloat() - 1f) * MathHelper.TAU / 8f);
    }

    public float getWindX() {
        return this.windX;
    }

    public float getWindZ() {
        return this.windZ;
    }
    public float getPrevWindX() {
        return this.prevWindX;
    }

    public float getPrevWindZ() {
        return this.prevWindZ;
    }

    public State getState() {
        return this.state;
    }

    private void tickState(World world) {
        this.stateDuration--;

        DimensionType dimensionType = world.getDimension();

        if (!dimensionType.isNatural() || dimensionType.hasCeiling()) {
            this.originalState = this.state;

            if (dimensionType.isUltrawarm()) {
                // Nether-like
                this.state = State.WINDY;
            } else {
                // There's no wind
                this.state = State.CALM;
            }

            return;
        }

        if (this.originalState != null) {
            this.state = this.originalState;
            this.originalState = null;
        }

        boolean raining = world.getLevelProperties().isRaining();
        raining = false;
        boolean thundering = world.isThundering();
        thundering = false;
        boolean weatherChanged = this.wasRaining != raining || this.wasThundering != thundering;

        if (weatherChanged || this.stateDuration <= 0) {
            if (thundering) {
                state = State.STORMY;
            } else {
                // Windy and stormy when raining, calm and windy otherwise.
                int index = RANDOM.nextInt(2);
                state = State.VALUES.get(raining ? index + 1 : index);
            }

            this.stateDuration = 6 * 60 * 20; // Change state every 6 minutes.
        }

        this.wasRaining = false;
        this.wasThundering = false;
        this.state = State.CALM;
    }

    public void tick(World world) {
        this.tickState(world);

        this.velocityNoise.tick();
        this.directionTrendNoise.tick();
        this.directionNoise.tick();

        float strength = this.velocityNoise.getNoise();
        float direction = this.directionNoise.getLerp() + this.directionNoise.getNoise();
        this.prevWindX = this.windX;
        this.prevWindZ = this.windZ;
        this.windX = strength * MathHelper.cos(direction);
        this.windZ = strength * MathHelper.sin(direction);
    }

    public enum State {
        CALM(0.0005f, 0.0005f, 0.002f),
        WINDY(0.05f, 0.3f, 0.7f),
        STORMY(0.05f, 0.6f, 1.1f);

        public static final List<State> VALUES = List.of(values());

        private final TriangularDistribution velocityDistribution;

        State(float minSpeed, float likelySpeed, float maxSpeed) {
            this.velocityDistribution = new TriangularDistribution(minSpeed, maxSpeed, likelySpeed, RANDOM);
        }

        public TriangularDistribution getVelocityDistribution() {
            return this.velocityDistribution;
        }
    }
    private static final class TriangularDistribution {
        private final float a, b, c;
        private final Random random;
        private final float f;

        public TriangularDistribution(float a, float b, float c, @Nullable Random random) {
            if (!(a < b) || !(a <= c && c <= b))
                throw new IllegalArgumentException("Parameters should be a <= b <= c, had a=" + a + " b=" + b + " c=" + c);

            this.a = a;
            this.b = b;
            this.c = c;
            this.f = (c - a) / (b - a);
            this.random = random == null ? new Random() : random;
        }

        public float sample() {
            float u = this.random.nextFloat();
            if (u < this.f) return this.a + (float) Math.sqrt(u * (this.b - this.a) * (this.c - this.a));
            return this.b - (float) Math.sqrt((1 - u) * (this.b - this.a) * (this.b - this.c));
        }
    }

    private static final class SmoothNoise {
        private final int tickInterval;
        private final Float2FloatFunction nextNoise;
        private float leftNoise;
        private float rightNoise;
        private int ticks = 0;
        private float t;

        /**
         * Smoothly goes from 0 to 1 when t increases from 0 and 1. Defined for t in [0, 1].
         */
        public static float smoothStep(float t) {
            return t * t * (3 - 2 * t);
        }

        public SmoothNoise(int tickInterval, float initial, Float2FloatFunction nextNoise) {
            if (tickInterval < 1)
                throw new IllegalArgumentException("Tick interval must be greater or equal to 1.");

            this.tickInterval = tickInterval;
            this.nextNoise = nextNoise;
            this.leftNoise = initial;
            this.rightNoise = nextNoise.apply(this.leftNoise);
        }

        /**
         * {@return the smooth interpolation between left and right noise values}
         */
        public float getNoise() {
            return this.leftNoise + smoothStep(this.t) * (this.rightNoise - this.leftNoise);
        }

        /**
         * {@return the linear interpolation between left and right noise values}
         */
        public float getLerp() {
            return this.leftNoise + this.t * (this.rightNoise - this.leftNoise);
        }

        public void tick() {
            this.ticks++;

            if (this.ticks == this.tickInterval) {
                this.ticks = 0;
                this.leftNoise = this.rightNoise;
                this.rightNoise = this.nextNoise.apply(this.leftNoise);
            }

            this.t = this.ticks / (float) this.tickInterval;
        }
    }
}
