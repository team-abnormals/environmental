package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import com.teamabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaTreeUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Set;

public class BigWisteriaTreeFeature extends Feature<TreeConfiguration> {

	public BigWisteriaTreeFeature(Codec<TreeConfiguration> configFactoryIn) {
		super(configFactoryIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		WorldGenLevel world = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();
		TreeConfiguration config = context.config();

		BlockState vine = WisteriaTreeFeature.getHangingWisteriaLeavesState(random, pos, config);
		Set<BlockPos> logPosSet = Sets.newHashSet();
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
				for (int k = 0; k < 3; ++k) {
					BlockPos position = new BlockPos(pos.offset(0, random.nextInt(3) + 4, 0));
					boolean xNeg = random.nextBoolean();
					boolean zNeg = random.nextBoolean();
					int size = random.nextInt(3) + 5;
					for (int j = 1; j <= size; j++) {
						position = position.offset(random.nextInt(2) - (xNeg ? 1 : 0), random.nextInt(2), random.nextInt(2) - (zNeg ? 1 : 0));
						TreeUtil.placeLogAt(world, position, random, config);
						logPosSet.add(position.immutable());
						if (j == size) {
							for (int y = 4; y > -4; --y) {
								for (int x = 4; x > -4; --x) {
									for (int z = 4; z > -4; --z) {
										if (Math.sqrt((x * x) + (y > 0 ? (y * y) : 0) + (z * z)) <= 4) {
											BlockPos leafPos = position.offset(x, y, z);
											boolean place = true;
											if (y < 0) {
												place = TreeUtil.isLeaves(world, leafPos.offset(0, 1, 0));
												if (place && random.nextInt(Math.abs(y) + 1) != 0) {
													place = false;
													if (random.nextInt(5) == 0 && !TreeUtil.isLog(world, leafPos)) {
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
						}
					}
				}
				for (int i2 = 0; i2 < height; ++i2) {
					if (WisteriaTreeUtil.isAirOrLeavesOrReplaceable(world, pos.above(i2))) {
						TreeUtil.placeLogAt(world, pos.above(i2), random, config);
						logPosSet.add(pos.above(i2).immutable());
					}
				}

				BlockPos startPos = pos.above(height);

				for (BlockPos blockpos : BlockPos.betweenClosed(startPos.getX() - 10, startPos.getY() - 10, startPos.getZ() - 10, startPos.getX() + 10, startPos.getY() + 10, startPos.getZ() + 10)) {
					if (world.getBlockState(blockpos).isAir() && isLeaves(world, blockpos.above(), config, random) && random.nextInt(4) == 0) {
						TreeUtil.setForcedState(world, blockpos, vine.setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER));
						if (world.getBlockState(blockpos.below()).isAir() && random.nextInt(2) == 0)
							TreeUtil.setForcedState(world, blockpos.below(), vine.setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER));
					}
				}

				WisteriaTreeUtil.updateLeaves(world, logPosSet);
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
}