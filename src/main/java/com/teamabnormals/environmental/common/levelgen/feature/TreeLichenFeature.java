package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.TreeLichenConfiguration;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class TreeLichenFeature extends Feature<TreeLichenConfiguration> {

	public TreeLichenFeature(Codec<TreeLichenConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeLichenConfiguration> context) {
		WorldGenLevel level = context.level();
		TreeLichenConfiguration config = context.config();
		PositionProbabilityFunction positionProbabilityFunction;
		if (config.useCedarShadeNoise()) {
			NormalNoise shadeNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(level.getLevel());
			positionProbabilityFunction = (x, y, z) -> {
				double shade = shadeNoise.getValue(0.25D * x, 0.0D, 0.25D * z);
				return shade < 0.0D ? 0.35D : 0.4D + shade * 0.525D;
			};
		} else {
			DensityFunction vegetationFunction = level.getLevel().getChunkSource().randomState().router().vegetation();
			positionProbabilityFunction = (x, y, z) -> computeVegetationDensity(vegetationFunction.compute(new DensityFunction.SinglePointContext(x, 0, z)));
		}
		BlockPos origin = context.origin();
		int originX = origin.getX();
		int originZ = origin.getZ();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		RandomSource random = context.random();
		float probability = config.probability();
		for (int x = 0; x < 16; x++) {
			pos.setX(originX + x);
			for (int z = 0; z < 16; z++) {
				pos.setZ(originZ + z);
				int height = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
				pos.setY(height);
				BlockState state = level.getBlockState(pos);
				boolean amplified;
				if (state.is(EnvironmentalBlockTags.CEDAR_LOGS)) {
					amplified = random.nextInt(7) != 0;
				} else if (state.is(BlockTags.LOGS)) {
					amplified = random.nextInt(7) == 0;
				} else continue;
				double density = positionProbabilityFunction.sample(pos.getX(), height, pos.getZ());
				if (random.nextDouble() > density * probability) continue;
				int lichenExtraHeight = random.nextInt(5) + 3;
				if (density > 0.35D && random.nextInt(4) != 0)
					lichenExtraHeight += Mth.clamp((int) (density / 0.25D), 1, 3);
				if (amplified) {
					if (random.nextBoolean()) {
						var randomHorizontals = Direction.Plane.HORIZONTAL.shuffledCopy(random);
						for (Direction side : randomHorizontals) {
							BlockPos.MutableBlockPos posCopy = pos.mutable().move(side);
							if (!level.getBlockState(posCopy).isAir()) continue;
							posCopy = posCopy.move(Direction.UP);
							state = level.getBlockState(posCopy);
							if (!canAttach(state)) continue;
							for (int h = 0; h < lichenExtraHeight; h++) {
								posCopy = posCopy.move(Direction.UP);
								state = level.getBlockState(posCopy);
								if (!canAttach(state)) {
									lichenExtraHeight -= (lichenExtraHeight - h);
									break;
								}
							}
							posCopy = pos.mutable().move(side);
							int cwHeight = shortenedHeight(random, lichenExtraHeight);
							int ccwHeight = shortenedHeight(random, lichenExtraHeight);
							growLichenSide(level, random, posCopy, side, lichenExtraHeight);
							growLichenSide(level, random, pos.mutable().move(side.getClockWise()), side.getClockWise(), cwHeight);
							growLichenSide(level, random, pos.mutable().move(side.getCounterClockWise()), side.getCounterClockWise(), ccwHeight);
							break;
						}
					} else {
						for (Direction side : Direction.Plane.HORIZONTAL) {
							int sideHeight = Math.max(1, lichenExtraHeight + random.nextInt(6) - 4);
							growLichenSide(level, random, pos.mutable().move(side), side, sideHeight);
						}
					}
				} else {
					for (Direction side : Direction.Plane.HORIZONTAL) {
						int sideHeight = lichenExtraHeight + random.nextInt(5) - 4;
						if (sideHeight <= 0) continue;
						growSparseLichenSide(level, random, pos.mutable().move(side), side, sideHeight);
					}
				}
			}
		}
		return true;
	}

	private static double computeVegetationDensity(double vegetation) {
		if (vegetation < 0.0D) return 0.0D;
		if (vegetation >= 1.0D) return 0.9D;
		if (vegetation <= 0.3D) return 0.25D * smoothStep(vegetation / 0.3D);
		// Segment 2: map x in [0.3, 1.0] to t2 in [0, 1]
		double t2 = (vegetation - 0.3D) / 0.7D;
		// Weight ramps from 0 to 1 after boostStart within the segment
		double w = smoothStep((t2 - 0.7D) / (1.0D - 0.7D));
		// Blend a gentler early warp with a stronger late warp
		double u = (1.0D - w) * Math.pow(t2, 1.6D) + w * Math.pow(t2, 4.0D);
		// Smooth endpoints and scale to [0.25, 0.9]
		return 0.25D + 0.65D * smoothStep(u);
	}

	private static double smoothStep(double vegetation) {
		vegetation = Math.clamp(vegetation, 0.0D, 1.0D);
		return vegetation * vegetation * (3.0D - 2.0D * vegetation);
	}

	private static BlockState lichenFacing(Direction side) {
		return EnvironmentalBlocks.TREE_LICHEN.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(side), true);
	}

	private static void setLichenWithAge(WorldGenLevel level, BlockPos pos, BlockState lichenState, int age) {
		if (!level.getBlockState(pos).isAir()) return;
		level.setBlock(pos, lichenState.setValue(BlockStateProperties.AGE_3, age), 2);
	}

	private static boolean canAttach(BlockState state) {
		return state.isAir() || state.is(BlockTags.LOGS) || state.is(BlockTags.REPLACEABLE_BY_TREES);
	}

	private static int shortenedHeight(RandomSource random, int baseHeight) {
		// subtract 1-5, but must be at least 1 tall
		return Math.max(1, baseHeight - (1 + random.nextInt(5)));
	}

	private static void placeLichen(WorldGenLevel level, BlockPos.MutableBlockPos upPos, Direction mainSide, int age) {
		BlockState blockState = level.getBlockState(upPos);
		Direction opposite = mainSide.getOpposite();
		if (blockState.isAir()) {
			level.setBlock(upPos, lichenFacing(opposite).setValue(BlockStateProperties.AGE_3, age), 2);
			return;
		} else if (blockState.is(EnvironmentalBlocks.TREE_LICHEN.get())) {
			level.setBlock(upPos, blockState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(opposite), true).setValue(BlockStateProperties.AGE_3, age), 2);
			return;
		} else if (!blockState.is(BlockTags.LEAVES) && blockState.is(BlockTags.REPLACEABLE_BY_TREES)) {
			// Destroy plant blocking the path
			level.setBlock(upPos, lichenFacing(opposite).setValue(BlockStateProperties.AGE_3, age), 2);
			int originalY = upPos.getY();
			for (int y = 1; y < 10; y++) {
				blockState = level.getBlockState(upPos.setY(originalY + y));
				if (blockState.is(BlockTags.LEAVES) || !blockState.is(BlockTags.REPLACEABLE_BY_TREES)) break;
				level.setBlock(upPos, Blocks.AIR.defaultBlockState(), 2);
			}
			upPos.setY(originalY);
			return;
		} else if (!blockState.is(BlockTags.LOGS)) return;
		Direction cw = opposite.getClockWise();
		Direction ccw = opposite.getCounterClockWise();
		BlockState cwLichen = lichenFacing(cw);
		BlockState ccwLichen = lichenFacing(ccw);
		BlockState upLichen = EnvironmentalBlocks.TREE_LICHEN.get().defaultBlockState().setValue(PipeBlock.DOWN, true);
		setLichenWithAge(level, upPos.relative(mainSide), lichenFacing(opposite), age);
		setLichenWithAge(level, upPos.relative(cw), ccwLichen, age);
		setLichenWithAge(level, upPos.relative(ccw), cwLichen, age);
		setLichenWithAge(level, upPos.above(), upLichen, age);
		BlockPos below = upPos.below();
		BlockState belowState = level.getBlockState(below);
		if (belowState.isAir()) {
			level.setBlock(below, EnvironmentalBlocks.TREE_LICHEN.get().defaultBlockState().setValue(PipeBlock.UP, true).setValue(BlockStateProperties.AGE_3, age), 2);
		} else if (belowState.is(EnvironmentalBlocks.TREE_LICHEN.get())) {
			level.setBlock(below, belowState.setValue(PipeBlock.UP, true).setValue(BlockStateProperties.AGE_3, age), 2);
		}
	}

	private static int computeAge(RandomSource random, int h, int totalHeight) {
		int maxAge = Mth.clamp((totalHeight - 1) / 2, 0, 3);
		float t = totalHeight <= 2 ? 1.0F : (h / (float) (totalHeight - 1));
		int age = Mth.clamp(Mth.floor((1.0F - t) * maxAge), 0, 3);
		if (random.nextFloat() < 0.20F) age = Math.min(3, age + 1);
		return age;
	}

	private static void growLichenSide(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, Direction side, int height) {
		int h = 0;
		while (true) {
			if (level.getBlockState(pos.move(side.getOpposite())).is(BlockTags.LOGS)) {
				pos.move(side);
				placeLichen(level, pos, side, computeAge(random, h, height));
			} else pos.move(side);
			if (++h == height) break;
			pos.move(Direction.UP);
		}
	}

	private static void growSparseLichenSide(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, Direction side, int height) {
		int h = 0;
		while (true) {
			if (level.getBlockState(pos.move(side.getOpposite())).is(BlockTags.LOGS) && (h < 3 || random.nextBoolean())) {
				pos.move(side);
				placeLichen(level, pos, side, random.nextBoolean() ? (random.nextInt(3) == 0 ? 2 : 1) : 0);
			} else pos.move(side);
			if (++h == height) break;
			pos.move(Direction.UP);
		}
	}

	@FunctionalInterface
	private interface PositionProbabilityFunction {
		double sample(int x, int y, int z);
	}
}
