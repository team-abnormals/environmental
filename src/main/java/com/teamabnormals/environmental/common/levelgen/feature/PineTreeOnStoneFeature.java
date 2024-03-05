package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class PineTreeOnStoneFeature extends PineTreeFeature {

	public PineTreeOnStoneFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		boolean flag = super.place(context);
		if (flag) {
			WorldGenLevel level = context.level();
			RandomSource random = context.random();
			BlockPos origin = context.origin();

			if (origin.getY() != level.getHeight(Types.WORLD_SURFACE_WG, origin.getX(), origin.getZ())) {
				level.setBlock(origin.below(), Blocks.COARSE_DIRT.defaultBlockState(), 2);

				MutableBlockPos mutable = new MutableBlockPos();
				for (int x = -1; x <= 1; ++x) {
					for (int z = -1; z <= 1; ++z) {
						for (int y = 0; y >= -2; --y) {
							mutable.setWithOffset(origin, x, y, z);
							BlockState blockstate = level.getBlockState(mutable);
							BlockState abovestate = level.getBlockState(mutable.above());

							if (abovestate.isAir() && (blockstate.is(BlockTags.BASE_STONE_OVERWORLD) || blockstate.is(Blocks.GRAVEL))) {
								if (x == 0 || z == 0 || random.nextInt(3) == 0) {
									level.setBlock(mutable, Blocks.COARSE_DIRT.defaultBlockState(), 2);
								}
								break;
							}
						}
					}
				}
			} else {
				EnvironmentalConfiguredFeatures.COARSE_DIRT_ON_STONE.get().place(level, context.chunkGenerator(), random, origin);
			}
		}
		return flag;
	}

	@Override
	public boolean canSurvive(WorldGenLevel level, BlockPos pos) {
		BlockState state = level.getBlockState(pos.below());
		return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(Blocks.GRAVEL);
	}
}