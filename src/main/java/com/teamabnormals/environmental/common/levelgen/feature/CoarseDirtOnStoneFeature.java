package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoarseDirtOnStoneFeature extends Feature<NoneFeatureConfiguration> {

    public CoarseDirtOnStoneFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos blockpos$mutable = pos.mutable();
        double r = Math.pow(random.nextDouble(), 2) + 1;
        int r1 = Mth.ceil(r);
        double xOffset = random.nextDouble() - 0.5D;
        double zOffset = random.nextDouble() - 0.5D;
        for (int x = -r1; x <= r1; ++x) {
            for (int z = -r1; z <= r1; ++z) {
                double d = Math.pow(x - xOffset, 2) + Math.pow(z - zOffset, 2);
                if (d <= r * r) {
                    int x1 = x + pos.getX();
                    int z1 = z + pos.getZ();
                    blockpos$mutable.set(x1, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x1, z1) - 1, z1);
                    if (level.getBlockState(blockpos$mutable).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        level.setBlock(blockpos$mutable, Blocks.COARSE_DIRT.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}