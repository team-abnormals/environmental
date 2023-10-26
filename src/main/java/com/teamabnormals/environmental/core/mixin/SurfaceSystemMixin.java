package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin {
    private NormalNoise pineBarrensStoneNoise;

    @Shadow
    @Final
    private BlockState defaultBlock;

    @Shadow
    @Final
    private NormalNoise surfaceNoise;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void SurfaceSystem(RandomState randomState, BlockState defaultBlock, int seaLevel, PositionalRandomFactory noiseRandom, CallbackInfo ci) {
        this.pineBarrensStoneNoise = randomState.getOrCreateNoise(EnvironmentalNoiseParameters.PINE_BARRENS_STONE.getKey());
    }

    @Inject(method = "buildSurface", at = @At("HEAD"))
    private void buildSurface(RandomState randomState, BiomeManager biomeManager, Registry<Biome> registry, boolean useLegacyRandomSource, WorldGenerationContext context, final ChunkAccess chunkAccess, NoiseChunk noiseChunk, SurfaceRules.RuleSource ruleSource, CallbackInfo ci) {
        final ChunkPos chunkpos = chunkAccess.getPos();
        LevelHeightAccessor levelheightaccessor = chunkAccess.getHeightAccessorForGeneration();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int[][][] raises = new int[16][16][3];
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int posX = chunkX + x;
                int posZ = chunkZ + z;
                int posY = chunkAccess.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) + 1;
                int height = 0;
                int raisestate = biomeManager.getBiome(mutable.set(posX, posY - 1, posZ)).is(EnvironmentalBiomes.PINE_BARRENS.getKey()) ? 0 : 1;

                if (raisestate == 0) {
                    double d0 = this.surfaceNoise.getValue(posX, 0.0D, posZ);
                    double d1 = this.pineBarrensStoneNoise.getValue(posX, 0.0D, posZ);
                    double d2 = Math.max(d0 - 2.0D / 8.25D, 0);
                    double d3 = d1 < 0 ? Math.max(-d1 - 2.0D / 8.25D, 0) : Math.max(d1 - 1.5D / 8.25D, 0);

                    double d4 = Math.min(d2, d3);
                    if (d4 > 0.0D)
                        height = d4 > 1.8D / 8.25D ? 2 : d4 > 0.5D / 8.25D ? 1 : 0;
                    else
                        raisestate = 2;
                }

                raises[x][z] = new int[]{posY, posY + height, raisestate};
            }
        }

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                if (raises[x][z][2] != 0)
                    continue;

                int minY = raises[x][z][0];
                int maxY = raises[x][z][1];
                int i = 0;
                boolean flag = false;

                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    Vec3i normal = direction.getNormal();
                    int x1 = x + normal.getX();
                    int z1 = z + normal.getZ();

                    if (x1 >= 0 && x1 < 16 && z1 >= 0 && z1 < 16) {
                        int maxY1 = raises[x1][z1][1] - maxY;

                        if (maxY1 > 0)
                            i += 1;
                        else if (maxY1 < 0)
                            i -= 1;

                        if (raises[x1][z1][2] == 1)
                            flag = true;
                    }
                }

                if (i >= 3)
                    maxY++;
                else if (i <= -3)
                    maxY = Math.max(maxY - 1, minY);

                if (flag)
                    maxY = Math.max(maxY - 1, minY);

                for (int y = minY; y < maxY; ++y) {
                    if (y >= levelheightaccessor.getMinBuildHeight() && y < levelheightaccessor.getMaxBuildHeight()) {
                        chunkAccess.setBlockState(mutable.set(x, y, z), this.defaultBlock, false);
                        if (!this.defaultBlock.getFluidState().isEmpty()) {
                            chunkAccess.markPosForPostprocessing(mutable);
                        }
                    }
                }
            }
        }
    }
}