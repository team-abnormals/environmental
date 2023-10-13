package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class PinelandsCoarseDirtFeature extends Feature<ProbabilityFeatureConfiguration> {

    public PinelandsCoarseDirtFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        ProbabilityFeatureConfiguration config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos blockpos$mutable = pos.mutable();
        if (random.nextFloat() < config.probability) {
            int r = random.nextInt(4) + 1;
            int r1 = Math.max(r - 2, 0);
            for (int x = -r; x <= r; ++x) {
                for (int y = -r; y <= r; ++y) {
                    int d = x * x + y * y;
                    if (d <= r1 * r1 || (d <= r * r && random.nextInt(3) > 0)) {
                        int x1 = x + pos.getX();
                        int y1 = y + pos.getZ();
                        blockpos$mutable.set(x1, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x1, y1) - 1, y1);
                        if (isDirt(level.getBlockState(blockpos$mutable))) {
                            level.setBlock(blockpos$mutable, Blocks.COARSE_DIRT.defaultBlockState(), 2);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}