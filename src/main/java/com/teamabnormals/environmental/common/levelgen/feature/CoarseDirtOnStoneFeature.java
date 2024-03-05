package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
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
        MutableBlockPos mutable = new MutableBlockPos();

        double radius = this.getRadius(random);
        int intradius = Mth.ceil(radius);
        double xoffset = random.nextDouble() - 0.5D;
        double yoffset = random.nextDouble() - 0.5D;

        int diameter = 2 * intradius;
        int[][] heights = new int[diameter + 1][diameter + 1];
        for (int i = 0; i <= diameter; ++i) {
            for (int j = 0; j <= diameter; ++j) {
                int x = i - intradius + pos.getX();
                int z = j - intradius + pos.getZ();
                heights[i][j] = level.getHeight(Types.WORLD_SURFACE_WG, x, z);
            }
        }

        for (int i = 0; i <= diameter; ++i) {
            for (int j = 0; j <= diameter; ++j) {
                int a = i - intradius;
                int b = j - intradius;
                int x = a + pos.getX();
                int z = b + pos.getZ();

                int maxy = heights[i][j] - 1;
                int miny = maxy;
                for (Direction direction : Plane.HORIZONTAL) {
                    int x1 = i + direction.getStepX();
                    int z1 = j + direction.getStepZ();
                    if (x1 >= 0 && x1 <= diameter && z1 >= 0 && z1 <= diameter) {
                        miny = Math.min(miny, heights[x1][z1]);
                    }
                }

                for (int y = maxy; y >= miny; --y) {
                    double d = Math.pow(a - xoffset, 2) + Math.pow(y - pos.getY() + 1, 2) + Math.pow(b - yoffset, 2);
                    if (d <= radius * radius) {
                        mutable.set(x, y, z);
                        BlockState state = level.getBlockState(mutable);
                        if (state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(Blocks.GRAVEL)) {
                            level.setBlock(mutable, Blocks.COARSE_DIRT.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        return true;
    }

    protected double getRadius(RandomSource random) {
        return Math.pow(random.nextDouble(), 2) + 1.5D;
    }
}