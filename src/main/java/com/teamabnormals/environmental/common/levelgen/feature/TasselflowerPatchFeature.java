package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;

public class TasselflowerPatchFeature extends Feature<NoneFeatureConfiguration> {

	public TasselflowerPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		RandomSource random = context.random();
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		BlockState blockstate = EnvironmentalBlocks.TASSELFLOWER.get().defaultBlockState();

		if (!isGrassOrDirt(level, pos.below()) || !level.getBlockState(pos).getMaterial().isReplaceable() || !isNearWater(level, pos))
			return false;

		int flowers = 0;
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

		for(int i = 0; i < 128; ++i) {
			float f = random.nextFloat() * 4.0F;
			float f1 = random.nextFloat() * 4.0F;
			f *= random.nextBoolean() ? -f : f;
			f1 *= random.nextBoolean() ? -f1 : f1;
			int x = pos.getX() + Math.round(f);
			int z = pos.getZ() + Math.round(f1);

			blockpos.set(x, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);
			if (level.isEmptyBlock(blockpos) && blockstate.canSurvive(level, blockpos) && !isNextToTasselflower(level, blockpos)) {
				level.setBlock(blockpos, blockstate, 2);
				++flowers;
			}
		}

		return flowers > 0;
	}

	private static boolean isNextToTasselflower(WorldGenLevel level, BlockPos pos) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (level.getBlockState(pos.relative(direction)).is(EnvironmentalBlocks.TASSELFLOWER.get()))
				return true;
		}
		return false;
	}

	private static boolean isNearWater(WorldGenLevel level, BlockPos pos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
			FluidState ifluidstate = level.getFluidState(blockpos);
			if (ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8)
				return true;
		}

		return false;
	}
}