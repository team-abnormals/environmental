package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class SmoothcapMossFeature extends Feature<NoneFeatureConfiguration> {

	public SmoothcapMossFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		if (!level.getFluidState(pos.below()).isEmpty()) return false;
		int originX = pos.getX();
		int originY = pos.getY();
		int originZ = pos.getZ();
		RandomSource random = context.random();
		// Radius affects the size of the moss patch
		NormalNoise radiusNoise = EnvironmentalNoiseParameters.SMOOTHCAP_MOSS_RADIUS_RECEIVER.get(level.getLevel());
		int fullRadius = getFullRadius(radiusNoise.getValue(originX, originY, originZ));
		int fullRadiusSq = fullRadius * fullRadius;
		int guaranteedRadiusSq = Mth.square(fullRadius - 1);
		// Cover is like how much more the age of the moss is
		NormalNoise coverNoise = EnvironmentalNoiseParameters.SMOOTHCAP_MOSS_COVER_RECEIVER.get(level.getLevel());
		double centerCoverValue = coverNoise.getValue(originX, originY, originZ);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int maxY = originY + 5;
		int minY = originY - 5;
		boolean placed = false;
		for (int x = -fullRadius; x <= fullRadius; x++) {
			mutable.setX(originX + x);
			int localX = mutable.getX();
			for (int z = -fullRadius; z <= fullRadius; z++) {
				mutable.setZ(originZ + z);
				int localZ = mutable.getZ();
				int distanceSquared = x * x + z * z;
				if (distanceSquared > fullRadiusSq) continue;
				boolean onEdge = distanceSquared > guaranteedRadiusSq;
				if (onEdge && random.nextBoolean()) continue;
				int age = getAgeForPosition(fullRadius, distanceSquared, x, z, centerCoverValue, random);
				for (int y = maxY; y >= minY; y--) {
					int belowY = y - 1;
					mutable.setY(belowY);
					BlockState state = level.getBlockState(mutable);
					if (!shouldPlaceMossOn(state)) continue;
					mutable.setY(y);
					if (age == 2 && random.nextBoolean()) age = 3;
					if (!tryToPlaceSmoothcapMoss(level, mutable, age, BlockStateProperties.DOWN)) continue;
					if (age == 3) {
						for (Direction side : Direction.Plane.HORIZONTAL) {
							mutable.move(side);
							for (int columnOffset = 1; columnOffset > -3; columnOffset--) {
								int columnY = y + columnOffset;
								mutable.setY(columnY);
								state = level.getBlockState(mutable);
								if (!shouldPlaceMossOn(state)) continue;
								if (!tryToPlaceSmoothcapMoss(level, mutable.setY(columnY + 1), 2, BlockStateProperties.DOWN))
									continue;
								mutable.setY(columnY);
								for (Direction face : Direction.Plane.HORIZONTAL)
									tryToPlaceSmoothcapMossSide(level, random, mutable, age, face);
							}
							mutable.setX(localX);
							mutable.setZ(localZ);
						}
						mutable.setY(y);
					}
					if (onEdge && random.nextBoolean()) {
						mutable.move(random.nextBoolean() ? 1 : -1, -1, random.nextBoolean() ? 1 : -1);
						state = level.getBlockState(mutable);
						mutable.setY(y);
						if (shouldPlaceMossOn(state))
							tryToPlaceSmoothcapMoss(level, mutable, 0, BlockStateProperties.DOWN);
						mutable.setX(localX);
						mutable.setZ(localZ);
					}
					placed = true;
					mutable.setY(belowY);
					for (Direction side : Direction.Plane.HORIZONTAL)
						tryToPlaceSmoothcapMossSide(level, random, mutable, age, side);
					y--;
				}
			}
		}
		return placed;
	}

	private static int getFullRadius(double noise) {
		if (noise < -0.25D) return 1;
		if (noise < 0.5D) return 2;
		return 3;
	}

	private static int getAgeForPosition(int fullRadius, int distanceSquared, int x, int z, double coverValue, RandomSource random) {
		if (x == 0 && z == 0) {
			if (coverValue > 0.25D) return 3;
			if (coverValue > 0.0D) return random.nextBoolean() ? 3 : 2;
		}
		int age;
		if (coverValue <= 0.0D) {
			age = fullRadius < 2 ? (random.nextBoolean() ? 2 : 1) : 2;
		} else age = 3;
		// Every threshold 0^2, 1^2, 2^2, ... crossed outward from the center
		// attempts to decrement age, but cover noise can cancel that decrement.
		double reductionChance = Math.max(0.0D, Math.min(coverValue, 1.0D)) * 0.75D;
		for (int i = 0; i < fullRadius; i++) {
			int threshold = i * i;
			if (distanceSquared > threshold) {
				if (random.nextDouble() >= reductionChance) {
					age--;
				}
			} else break;
		}
		return Math.max(age, 0);
	}

	private static boolean shouldPlaceMossOn(BlockState state) {
		return state.is(BlockTags.DIRT) || state.is(BlockTags.LOGS);
	}

	private static void tryToPlaceSmoothcapMossSide(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, int age, Direction side) {
		if (age > 2) {
			age = random.nextInt(4) != 0 ? 2 : 1;
		} else if (random.nextBoolean() && --age < 0) return;
		pos.move(side);
		Direction opposite = side.getOpposite();
		tryToPlaceSmoothcapMoss(level, pos, age, MultifaceBlock.getFaceProperty(opposite));
		pos.move(opposite);
	}

	private static boolean tryToPlaceSmoothcapMoss(WorldGenLevel level, BlockPos pos, int age, BooleanProperty side) {
		BlockState state = level.getBlockState(pos);
		if (state.is(EnvironmentalBlocks.SMOOTHCAP_MOSS.get())) {
			level.setBlock(pos, state.setValue(BlockStateProperties.AGE_3, Math.max(age, state.getValue(BlockStateProperties.AGE_3))).setValue(side, true), 2);
			return true;
		} else if (state.is(EnvironmentalBlocks.TREE_LICHEN.get())) {
			BlockState lichenReplacement = EnvironmentalBlocks.SMOOTHCAP_MOSS.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, Math.min(age, 2)).setValue(side, true);
			for (Direction direction : Direction.values()) {
				if (direction == Direction.UP) continue;
				BooleanProperty directionProperty = MultifaceBlock.getFaceProperty(direction);
				if (directionProperty == side || !state.getValue(directionProperty)) continue;
				lichenReplacement = lichenReplacement.setValue(directionProperty, true);
			}
			lichenReplacement = lichenReplacement.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));
			level.setBlock(pos, lichenReplacement, 2);
			return true;
		}
		boolean waterLogged;
		if (state.isAir()) {
			waterLogged = false;
		} else if (level.getFluidState(pos).is(FluidTags.WATER)) {
			waterLogged = true;
		} else return false;
		level.setBlock(pos, EnvironmentalBlocks.SMOOTHCAP_MOSS.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, age).setValue(side, true).setValue(BlockStateProperties.WATERLOGGED, waterLogged), 2);
		return true;
	}
}