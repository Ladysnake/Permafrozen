package net.permafrozen.permafrozen.worldgen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.permafrozen.permafrozen.registry.PermafrozenBlocks;

import java.util.Random;
import java.util.stream.IntStream;

public class GlacialOceanSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
        protected static final BlockState PACKED_ICE;
        protected static final BlockState SNOW_BLOCK;
        private static final BlockState AIR;
        private static final BlockState SNAD;
        private static final BlockState ICE;
        private OctaveSimplexNoiseSampler icebergNoise;
        private OctaveSimplexNoiseSampler icebergCutoffNoise;
        private long seed;

        public GlacialOceanSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
            super(codec);
        }

        public void generate(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState blockState, BlockState blockState2, int l, int m, long n, TernarySurfaceConfig ternarySurfaceConfig) {
            double e = 0.0D;
            double f = 0.0D;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            float g = biome.getTemperature(mutable.set(i, 63, j));
            double h = Math.min(Math.abs(d), this.icebergNoise.sample((double)i * 0.1D, (double)j * 0.1D, false) * 15.0D);
            if (h > 1.8D) {
                double o = 0.09765625D;
                double p = Math.abs(this.icebergCutoffNoise.sample((double)i * 0.09765625D, (double)j * 0.09765625D, false));
                e = h * h * 1.2D;
                double q = Math.ceil(p * 40.0D) + 14.0D;
                if (e > q) {
                    e = q;
                }

                if (g > 0.1F) {
                    e -= 2.0D;
                }

                if (e > 2.0D) {
                    f = (double)l - e - 7.0D;
                    e += (double)l;
                } else {
                    e = 0.0D;
                }
            }

            int r = i & 15;
            int s = j & 15;
            SurfaceConfig surfaceConfig = biome.getGenerationSettings().getSurfaceConfig();
            BlockState blockState3 = surfaceConfig.getUnderMaterial();
            BlockState blockState4 = surfaceConfig.getTopMaterial();
            BlockState blockState5 = blockState3;
            BlockState blockState6 = blockState4;
            int t = (int)(d / 3.0D + 3.0D + random.nextDouble() * 0.25D);
            int u = -1;
            int v = 0;
            int w = 2 + random.nextInt(4);
            int x = l + 18 + random.nextInt(10);

            for(int y = Math.max(k, (int)e + 1); y >= m; --y) {
                mutable.set(r, y, s);
                if (chunk.getBlockState(mutable).isAir() && y < (int)e && random.nextDouble() > 0.01D) {
                    chunk.setBlockState(mutable, PACKED_ICE, false);
                } else if (chunk.getBlockState(mutable).getMaterial() == Material.WATER && y > (int)f && y < l && f != 0.0D && random.nextDouble() > 0.15D) {
                    chunk.setBlockState(mutable, PACKED_ICE, false);
                }

                BlockState blockState7 = chunk.getBlockState(mutable);
                if (blockState7.isAir()) {
                    u = -1;
                } else if (!blockState7.isOf(blockState.getBlock())) {
                    if (blockState7.isOf(Blocks.PACKED_ICE) && v <= w && y > x) {
                        chunk.setBlockState(mutable, SNOW_BLOCK, false);
                        ++v;
                    }
                } else if (u == -1) {
                    if (t <= 0) {
                        blockState6 = AIR;
                        blockState5 = blockState;
                    } else if (y >= l - 4 && y <= l + 1) {
                        blockState6 = blockState4;
                        blockState5 = blockState3;
                    }

                    if (y < l && (blockState6 == null || blockState6.isAir())) {
                        if (biome.getTemperature(mutable.set(i, y, j)) < 0.15F) {
                            blockState6 = ICE;
                        } else {
                            blockState6 = blockState2;
                        }
                    }

                    u = t;
                    if (y >= l - 1) {
                        chunk.setBlockState(mutable, blockState6, false);
                    } else if (y < l - 7 - t) {
                        blockState6 = AIR;
                        blockState5 = blockState;
                        chunk.setBlockState(mutable, SNAD, false);
                    } else {
                        chunk.setBlockState(mutable, blockState5, false);
                    }
                } else if (u > 0) {
                    --u;
                    chunk.setBlockState(mutable, blockState5, false);
                    if (u == 0 && blockState5.isOf(Blocks.SAND) && t > 1) {
                        u = random.nextInt(4) + Math.max(0, y - 63);
                        blockState5 = blockState5.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }

        }

        public void initSeed(long seed) {
            if (this.seed != seed || this.icebergNoise == null || this.icebergCutoffNoise == null) {
                ChunkRandom chunkRandom = new ChunkRandom(seed);
                this.icebergNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.rangeClosed(-3, 0));
                this.icebergCutoffNoise = new OctaveSimplexNoiseSampler(chunkRandom, ImmutableList.of(0));
            }

            this.seed = seed;
        }

        static {
            PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
            SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
            AIR = Blocks.AIR.getDefaultState();
            SNAD = PermafrozenBlocks.SAPPHIRE_SAND.getDefaultState();
            ICE = Blocks.ICE.getDefaultState();
        }
    }

