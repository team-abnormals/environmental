package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class PineBarrensStoneRaiserFeature extends Feature<NoneFeatureConfiguration> {

    public PineBarrensStoneRaiserFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Registry<NormalNoise.NoiseParameters> registry = level.registryAccess().registryOrThrow(Registry.NOISE_REGISTRY);

        RandomSource random1 = new XoroshiroRandomSource(level.getSeed()).forkPositional().fromHashOf(Noises.SURFACE.location());
        RandomSource random2 = new XoroshiroRandomSource(level.getSeed()).forkPositional().fromHashOf(EnvironmentalNoiseParameters.PINE_BARRENS_STONE.getId());

        NormalNoise noise1 = NormalNoise.create(random1, registry.getHolderOrThrow(Noises.SURFACE).value());
        NormalNoise noise2 = NormalNoise.create(random2, EnvironmentalNoiseParameters.PINE_BARRENS_STONE.get());

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        // Calculates the amount to raise the stone in each part of the chunk.
        Raise[][] raises = new Raise[18][18];
        for (int x = -1; x < 17; ++x) {
            for (int z = -1; z < 17; ++z) {
                int posX = origin.getX() + x;
                int posZ = origin.getZ() + z;
                int posY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, posX, posZ);
                int height = 0;
                mutable.set(posX, posY, posZ);

                if (level.getBiome(mutable.below()).is(EnvironmentalBiomes.PINE_BARRENS.getKey())) {
                    double d0 = noise1.getValue(posX, 0.0D, posZ);
                    double d1 = noise2.getValue(posX, 0.0D, posZ);
                    double d2 = Math.max(d0 - 2.0D / 8.25D, 0);
                    double d3 = d1 < 0 ? Math.max(-d1 - 2.0D / 8.25D, 0) : Math.max(d1 - 1.5D / 8.25D, 0);

                    double d4 = Math.min(d2, d3);
                    int i = d4 > 1.8D / 8.25D ? 2 : d4 > 0.5D / 8.25D ? 1 : 0;

                    for (int j = 0; j < i; ++j) {
                        if (!level.getBlockState(mutable).getMaterial().isReplaceable())
                            break;
                        height++;
                    }
                }

                raises[x + 1][z + 1] = new Raise(posY, posY + height);
            }
        }

        // Smoothens the raised stone.
        Raise[][] newraises = new Raise[16][16];
        for (int x = 1; x < 17; ++x) {
            for (int z = 1; z < 17; ++z) {
                int minY = raises[x][z].minY;
                int maxY = raises[x][z].maxY;
                int i = 0;

                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    Vec3i normal = direction.getNormal();
                    int maxY2 = raises[x + normal.getX()][z + normal.getZ()].maxY - maxY;

                    if (maxY2 > 0)
                        i += 1;
                    else if (maxY2 < 0)
                        i -= 1;
                }

                if (i >= 3)
                    maxY++;
                else if (i <= -3)
                    maxY = Math.max(maxY - 1, minY);

                newraises[x - 1][z - 1] = new Raise(minY, maxY);
            }
        }

        // Actually generates the raised stone.
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                Raise raise = newraises[x][z];
                for (int y = raise.minY; y < raise.maxY; ++y) {
                    mutable.set(origin.getX() + x, y, origin.getZ() + z);
                    level.setBlock(mutable, Blocks.STONE.defaultBlockState(), 2);
                }
            }
        }

        return true;
    }

    private record Raise(int minY, int maxY) {
    }
}