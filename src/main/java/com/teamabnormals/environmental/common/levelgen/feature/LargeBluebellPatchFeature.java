package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LargeBluebellPatchFeature extends Feature<NoneFeatureConfiguration> {

	public LargeBluebellPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		RandomSource random = context.random();
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();

		int flowers = 0;
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

		for (int x = -10; x <= 10; ++x) {
			for (int z = -10; z <= 10; ++z) {
				blockpos.set(x + pos.getX(), level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x + pos.getX(), z + pos.getZ()), z + pos.getZ());
				double r = blockpos.distSqr(pos);
				if (random.nextInt(10) > 0 && (r <= 49.0D || (r <= 100.0D && random.nextInt(4) == 0))) {
					BlockState blockstate = EnvironmentalBlocks.BLUEBELL.get().defaultBlockState();
					if (random.nextInt(6) == 0)
						blockstate = random.nextInt(3) > 0 ? Blocks.GRASS.defaultBlockState() : Blocks.FERN.defaultBlockState();

					if (level.isEmptyBlock(blockpos) && blockstate.canSurvive(level, blockpos)) {
						level.setBlock(blockpos, blockstate, 2);
						++flowers;
					}
				}
			}
		}

		return flowers > 0;
	}
}