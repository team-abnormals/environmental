package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
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
        int r = random.nextInt(2) + 1;
        for (int x = -r; x <= r; ++x) {
            for (int y = -r; y <= r; ++y) {
                int d = x * x + y * y;
                if (d <= r * r && ((Math.abs(x) != r && Math.abs(y) != r) || random.nextInt(7) > 0)) {
                    int x1 = x + pos.getX();
                    int y1 = y + pos.getZ();
                    blockpos$mutable.set(x1, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x1, y1) - 1, y1);
                    if (level.getBlockState(blockpos$mutable).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        level.setBlock(blockpos$mutable, Blocks.COARSE_DIRT.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}