package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Arrays;
import java.util.List;

public class FallenPineTreeFeature extends Feature<NoneFeatureConfiguration> {

	public FallenPineTreeFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
		int length = 10 + random.nextInt(5);

		int xOffset = direction.getAxis() == Axis.X ? length / 2 : 0;
		int zOffset = direction.getAxis() == Axis.Z ? length / 2 : 0;

		for (int i = 0; i < 4; i++) {
			BlockPos pos = origin.offset(random.nextInt(4) - random.nextInt(4) - xOffset, random.nextInt(2) - random.nextInt(2), random.nextInt(4) - random.nextInt(4) - zOffset);
			if (canGenerateAt(level, pos, direction, length)) {
				List<Direction> branchdirections = Arrays.stream(Direction.values()).filter(direction1 -> direction1.getAxis() != direction.getAxis()).toList();

				for (int j = 0; j < length; j++) {
					this.createLog(level, pos.relative(direction, j), direction);
				}

				int j = length - 2;
				while (j > 0) {
					if (random.nextInt(6) == 0) {
						Direction branchdirection = branchdirections.get(random.nextInt(4));
						BlockPos branchpos = pos.relative(direction, j).relative(branchdirection);
						if (isReplaceable(level, branchpos))
							this.createLog(level, branchpos, branchdirection);
					}

					j -= 2 + random.nextInt(2);
				}

				return true;
			}
		}

		return false;
	}

	private void createLog(WorldGenLevel level, BlockPos pos, Direction direction) {
		level.setBlock(pos, EnvironmentalBlocks.PINE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS, direction.getAxis()), 2);
		if (level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK))
			level.setBlock(pos.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 2);
	}

	private static boolean canGenerateAt(WorldGenLevel level, BlockPos pos, Direction direction, int length) {
		MutableBlockPos mutable = pos.mutable();
		int supportblocks = 0;
		boolean hasdirtbelow = false;

		for (int i = 0; i < length; i++) {
			if (isReplaceable(level, mutable)) {
				BlockPos belowpos = mutable.below();
				BlockState belowstate = level.getBlockState(belowpos);
				if (belowstate.isFaceSturdy(level, belowpos, Direction.UP))
					supportblocks++;
				if (isDirt(belowstate))
					hasdirtbelow = true;
			} else {
				return false;
			}

			mutable.move(direction);
		}

		return hasdirtbelow && supportblocks > length * 0.75F;
	}

	private static boolean isReplaceable(WorldGenLevel level, BlockPos pos) {
		Material material = level.getBlockState(pos).getMaterial();
		return level.isEmptyBlock(pos) || material == Material.REPLACEABLE_PLANT || material == Material.REPLACEABLE_WATER_PLANT || material == Material.REPLACEABLE_FIREPROOF_PLANT;
	}
}