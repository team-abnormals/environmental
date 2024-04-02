package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class LargeBluebellPatchFeature extends Feature<NoneFeatureConfiguration> {
	private static final NormalNoise NOISE = NormalNoise.create(new WorldgenRandom(new LegacyRandomSource(2345L)), new NormalNoise.NoiseParameters(0, 1.0D));

	public LargeBluebellPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		RandomSource random = context.random();
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();

		int flowers = 0;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int x = -16; x <= 16; ++x) {
			for (int z = -16; z <= 16; ++z) {
				mutable.set(x + origin.getX(), level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x + origin.getX(), z + origin.getZ()), z + origin.getZ());
				double d0 = getNoiseAtPos(origin, mutable);

				if (random.nextInt(10) > 0 && mutable.distSqr(origin) < 324.0D && (d0 > 0.5D || (d0 > 0.05D && random.nextInt(4) == 0))) {
					BlockState blockstate = EnvironmentalBlocks.BLUEBELL.get().defaultBlockState();
					if (random.nextInt(6) == 0)
						blockstate = random.nextInt(3) > 0 ? Blocks.GRASS.defaultBlockState() : Blocks.FERN.defaultBlockState();

					if (level.isEmptyBlock(mutable) && blockstate.canSurvive(level, mutable)) {
						level.setBlock(mutable, blockstate, 2);
						++flowers;
					}
				}
			}
		}

		return flowers > 0;
	}

	private static double getNoiseAtPos(BlockPos origin, BlockPos pos) {
		double d0 = NOISE.getValue(pos.getX() * 0.05D, 0.0D, pos.getZ() * 0.05D);
		return (1.0D - pos.distSqr(origin) / 256.0D) + d0 * 1.35D;
	}
}