package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaTreeUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class WisteriaTreeFeature extends Feature<TreeConfiguration> {
	private Supplier<BlockState> VINE_UPPER;
	private Supplier<BlockState> VINE_LOWER;
	private Set<BlockPos> logPosSet;

	public WisteriaTreeFeature(Codec<TreeConfiguration> configFactoryIn) {
		super(configFactoryIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		WorldGenLevel world = context.level();
		Random random = context.random();
		BlockPos pos = context.origin();
		TreeConfiguration config = context.config();

		this.logPosSet = Sets.newHashSet();
		if (config.foliageProvider.getState(random, pos) == EnvironmentalFeatures.States.BLUE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatures.States.BLUE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatures.States.BLUE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.foliageProvider.getState(random, pos) == EnvironmentalFeatures.States.PINK_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatures.States.PINK_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatures.States.PINK_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.foliageProvider.getState(random, pos) == EnvironmentalFeatures.States.PURPLE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatures.States.PURPLE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatures.States.PURPLE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.foliageProvider.getState(random, pos) == EnvironmentalFeatures.States.WHITE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatures.States.WHITE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatures.States.WHITE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}

		int height = random.nextInt(7) + 5;
		boolean flag = true;
		if (pos.getY() >= 1 && pos.getY() + height + 1 <= world.getMaxBuildHeight()) {
			for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == pos.getY()) {
					k = 0;
				}
				if (j >= pos.getY() + 1 + height - 2) {
					k = 2;
				}
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
					for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < world.getMaxBuildHeight()) {
							if (!TreeUtil.isAirOrLeaves(world, blockpos$mutableblockpos.set(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}
			if (!flag) {
				return false;
			} else if (TreeUtil.isValidGround(world, pos.below(), (SaplingBlock) EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get()) && pos.getY() < world.getMaxBuildHeight() - height - 1) {
				TreeUtil.setDirtAt(world, pos.below());
				for (int y = 4; y > -4; --y) {
					for (int x = 4; x > -4; --x) {
						for (int z = 4; z > -4; --z) {
							if (Math.sqrt((x * x) + (y > 0 ? (y * y) : 0) + (z * z)) <= 4) {
								BlockPos leafPos = pos.offset(x, y + height, z);
								boolean place = true;
								if (y < 0) {
									place = TreeUtil.isLeaves(world, leafPos.offset(0, 1, 0));
									if (place && random.nextInt(Math.abs(y) + 1) != 0) {
										place = false;
										if (random.nextInt(4) == 0 && !TreeUtil.isLog(world, leafPos)) {
											WisteriaTreeUtil.placeVines(world, random, leafPos, config.foliageProvider.getState(random, pos), VINE_LOWER.get(), VINE_UPPER.get());
										}
									}
								}
								if (place) {
									WisteriaTreeUtil.placeLeafAt(world, leafPos, config.foliageProvider.getState(random, pos));
								}
							}
						}
					}
				}
				for (int i2 = 0; i2 < height; ++i2) {
					if (TreeUtil.isAirOrLeaves(world, pos.above(i2))) {
						TreeUtil.placeLogAt(world, pos.above(i2), random, config);
						logPosSet.add(pos.immutable());
					}
				}
				placeBranch(world, random, pos.below(), pos.above(height).getY(), config);
				if (random.nextInt(4) == 0) placeBranch(world, random, pos.below(), pos.above(height).getY(), config);

				BlockPos startPos = pos.above(height);

				for (BlockPos blockpos : BlockPos.betweenClosed(startPos.getX() - 10, startPos.getY() - 10, startPos.getZ() - 10, startPos.getX() + 10, startPos.getY() + 10, startPos.getZ() + 10)) {
					if (Feature.isAir(world, blockpos) && isLeaves(world, blockpos.above(), config, random) && random.nextInt(4) == 0) {
						if (Feature.isAir(world, blockpos))
							TreeUtil.setForcedState(world, blockpos, VINE_UPPER.get());
						if (Feature.isAir(world, blockpos.below()) && random.nextInt(2) == 0)
							TreeUtil.setForcedState(world, blockpos.below(), VINE_LOWER.get());
					}
				}

				TreeUtil.updateLeaves(world, this.logPosSet);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLeaves(LevelSimulatedReader worldIn, BlockPos pos, TreeConfiguration config, Random random) {
		if (worldIn instanceof net.minecraft.world.level.LevelReader) // FORGE: Redirect to state method when possible
			return worldIn.isStateAtPosition(pos, state -> state == config.foliageProvider.getState(random, pos));
		return worldIn.isStateAtPosition(pos, (p_227223_0_) -> {
			return config.foliageProvider.getState(random, pos) == p_227223_0_;
		});
	}

	private void placeBranch(LevelSimulatedRW world, Random random, BlockPos pos, int treeHeight, TreeConfiguration config) {
		int heightOffset = random.nextInt(3);
		BlockPos[] startPositions = new BlockPos[]{
				new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ()),
				new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ()),
				new BlockPos(pos.getX(), treeHeight - heightOffset, pos.getZ() - 1),
				new BlockPos(pos.getX(), treeHeight - heightOffset, pos.getZ() + 1),
				new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ() - 1),
				new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ() - 1),
				new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ() + 1),
				new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ() + 1)
		};
		BlockPos startPos = startPositions[random.nextInt(8)];
		if (WisteriaTreeUtil.isAirOrLeavesOrReplaceable(world, startPos)) {
			boolean vines = random.nextInt(6) != 5;
			BlockPos placePos = startPos;
			for (int y = (treeHeight - heightOffset); y <= treeHeight; ++y) {
				placePos = new BlockPos(startPos.getX(), y, startPos.getZ());
				if (WisteriaTreeUtil.isAirOrLeavesOrReplaceable(world, placePos)) {
					TreeUtil.placeLogAt(world, placePos, random, config);
					logPosSet.add(pos.immutable());
				}
			}
			WisteriaTreeUtil.placeLeafAt(world, placePos.above(), config.foliageProvider.getState(random, pos));
			if (vines)
				WisteriaTreeUtil.placeVines(world, random, startPos.below(), config.foliageProvider.getState(random, pos), VINE_LOWER.get(), VINE_UPPER.get());
		}
	}
}