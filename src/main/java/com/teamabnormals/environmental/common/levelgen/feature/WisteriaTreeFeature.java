package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import com.teamabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaTreeUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import java.util.Set;
import java.util.function.BiConsumer;

public class WisteriaTreeFeature extends Feature<TreeConfiguration> {
	private Set<BlockPos> logPosSet;

	public WisteriaTreeFeature(Codec<TreeConfiguration> configFactoryIn) {
		super(configFactoryIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		WorldGenLevel world = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();
		TreeConfiguration config = context.config();

		if (random.nextInt(5) == 0 && EnvironmentalFeatures.BIG_WISTERIA_TREE.get().place(context)) {
			return true;
		}

		BlockState vine = getHangingWisteriaLeavesState(random, pos, config);
		this.logPosSet = Sets.newHashSet();
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
											WisteriaTreeUtil.placeVines(world, random, leafPos, config.foliageProvider.getState(random, pos));
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
						logPosSet.add(pos.above(i2).immutable());
					}
				}
				placeBranch(world, random, pos.below(), pos.above(height).getY(), config);
				if (random.nextInt(4) == 0) placeBranch(world, random, pos.below(), pos.above(height).getY(), config);

				BlockPos startPos = pos.above(height);

				for (BlockPos blockpos : BlockPos.betweenClosed(startPos.getX() - 10, startPos.getY() - 10, startPos.getZ() - 10, startPos.getX() + 10, startPos.getY() + 10, startPos.getZ() + 10)) {
					if (world.getBlockState(blockpos).isAir() && isLeaves(world, blockpos.above(), config, random) && random.nextInt(4) == 0) {
						if (world.getBlockState(blockpos).isAir())
							TreeUtil.setForcedState(world, blockpos, vine.setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER));
						if (world.getBlockState(blockpos.below()).isAir() && random.nextInt(2) == 0)
							TreeUtil.setForcedState(world, blockpos.below(), vine.setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER));
					}
				}

				WisteriaTreeUtil.updateLeaves(world, this.logPosSet);

				Set<BlockPos> set3 = Sets.newHashSet();
				BiConsumer<BlockPos, BlockState> biconsumer3 = (p_225290_, p_225291_) -> {
					set3.add(p_225290_.immutable());
					world.setBlock(p_225290_, p_225291_, 19);
				};

				if (!config.decorators.isEmpty()) {
					TreeDecorator.Context decoratorContext = new TreeDecorator.Context(world, biconsumer3, random, this.logPosSet, Sets.newHashSet(), Sets.newHashSet());
					config.decorators.forEach((decorator) -> decorator.place(decoratorContext));
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLeaves(LevelSimulatedReader worldIn, BlockPos pos, TreeConfiguration config, RandomSource random) {
		if (worldIn instanceof net.minecraft.world.level.LevelReader) // FORGE: Redirect to state method when possible
			return worldIn.isStateAtPosition(pos, state -> state == config.foliageProvider.getState(random, pos));
		return worldIn.isStateAtPosition(pos, (p_227223_0_) -> {
			return config.foliageProvider.getState(random, pos) == p_227223_0_;
		});
	}

	private void placeBranch(LevelSimulatedRW world, RandomSource random, BlockPos pos, int treeHeight, TreeConfiguration config) {
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
					logPosSet.add(placePos.immutable());
				}
			}
			WisteriaTreeUtil.placeLeafAt(world, placePos.above(), config.foliageProvider.getState(random, pos));
			if (vines)
				WisteriaTreeUtil.placeVines(world, random, startPos.below(), config.foliageProvider.getState(random, pos));
		}
	}

	public static BlockState getHangingWisteriaLeavesState(RandomSource random, BlockPos pos, TreeConfiguration config) {
		BlockState leafState = config.foliageProvider.getState(random, pos);
		if (leafState.is(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get())) {
			return EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		}

		if (leafState.is(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get())) {
			return EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		}

		if (leafState.is(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get())) {
			return EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		}

		return EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
	}
}