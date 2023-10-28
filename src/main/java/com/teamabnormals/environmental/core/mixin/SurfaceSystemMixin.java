package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin {
    private NormalNoise pineBarrensStoneNoise;
    private int[][][] pineBarrensStoneRaises;
    private boolean raisePineBarrensStone;

    @Shadow
    @Final
    private BlockState defaultBlock;

    @Shadow
    @Final
    private NormalNoise surfaceNoise;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void SurfaceSystem(RandomState randomState, BlockState defaultBlock, int seaLevel, PositionalRandomFactory noiseRandom, CallbackInfo ci) {
        this.pineBarrensStoneNoise = randomState.getOrCreateNoise(EnvironmentalNoiseParameters.PINE_BARRENS_STONE.getKey());
        this.pineBarrensStoneRaises = new int[16][16][3];
    }

    @Inject(method = "buildSurface", at = @At("HEAD"))
    private void buildSurface(RandomState randomState, BiomeManager biomeManager, Registry<Biome> registry, boolean useLegacyRandomSource, WorldGenerationContext context, final ChunkAccess chunkAccess, NoiseChunk noiseChunk, SurfaceRules.RuleSource ruleSource, CallbackInfo ci) {
        this.raisePineBarrensStone = false;
    }

    @Inject(method = "buildSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/BiomeManager;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;", shift = At.Shift.BEFORE, ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void collectRaises(RandomState randomState, BiomeManager biomeManager, Registry<Biome> registry, boolean useLegacyRandomSource, WorldGenerationContext context, final ChunkAccess chunkAccess, NoiseChunk noiseChunk, SurfaceRules.RuleSource ruleSource, CallbackInfo ci, final BlockPos.MutableBlockPos mutable, ChunkPos chunkPos, int i, int j, BlockColumn blockColumn, SurfaceRules.Context surfaceRulesContext, SurfaceRules.SurfaceRule surfaceRule, BlockPos.MutableBlockPos mutable1, int k, int l, int i1, int j1) {
        int y = chunkAccess.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, k, l) + 1;
        if (biomeManager.getBiome(mutable1.set(i1, y - 1, j1)).is(EnvironmentalBiomes.PINE_BARRENS.getKey())) {
            double noise = this.getNoiseAt(i1, j1);
            boolean flag = noise > 0.0D;
            this.pineBarrensStoneRaises[k][l] = new int[]{y, y + getRaise(noise), flag ? 1 : 0};
            if (flag)
                this.raisePineBarrensStone = true;
        } else {
            this.pineBarrensStoneRaises[k][l] = null;
        }
    }

    @Inject(method = "buildSurface", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void generateRaisedStone(RandomState randomState, BiomeManager biomeManager, Registry<Biome> registry, boolean useLegacyRandomSource, WorldGenerationContext context, final ChunkAccess chunkAccess, NoiseChunk noiseChunk, SurfaceRules.RuleSource ruleSource, CallbackInfo ci, final BlockPos.MutableBlockPos mutable, ChunkPos chunkPos, int i, int j, BlockColumn blockColumn) {
        if (this.raisePineBarrensStone) {
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    int[] raise = this.pineBarrensStoneRaises[x][z];
                    if (raise == null || raise[2] == 0)
                        continue;

                    int y = raise[0];
                    int y1 = raise[1];
                    int m = 0;
                    mutable.setX(x + i).setZ(z + j);

                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        int x1 = x + direction.getStepX();
                        int z1 = z + direction.getStepZ();
                        if (x1 >= 0 && x1 < 16 && z1 >= 0 && z1 < 16) {
                            int[] raise1 = this.pineBarrensStoneRaises[x1][z1];

                            if (raise1 == null) {
                                m = 0;
                                y1--;
                                break;
                            }

                            if (raise1[1] > y1) {
                                m++;
                            } else if (raise1[1] < y1) {
                                m--;
                            }
                        }
                    }

                    if (m >= 3)
                        y1++;
                    else if (m <= -3)
                        y1--;

                    for (int n = y; n < y1; ++n) {
                        blockColumn.setBlock(n, Blocks.STONE.defaultBlockState());
                    }
                }
            }
        }
    }

    private double getNoiseAt(int x, int y) {
        double d0 = this.surfaceNoise.getValue(x, 0.0D, y);
        double d1 = this.pineBarrensStoneNoise.getValue(x, 0.0D, y);
        double d2 = Math.max(d0 - 2.0D / 8.25D, 0);
        double d3 = d1 < 0 ? Math.max(-d1 - 2.0D / 8.25D, 0) : Math.max(d1 - 1.5D / 8.25D, 0);
        return Math.min(d2, d3);
    }

    private static int getRaise(double noise) {
        return noise < 0.5D / 8.25D ? 0 : noise < 1.8D / 8.25D ? 1 : 2;
    }
}