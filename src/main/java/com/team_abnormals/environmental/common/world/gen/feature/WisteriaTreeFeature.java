package com.team_abnormals.environmental.common.world.gen.feature;

import java.util.Random;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.team_abnormals.environmental.common.world.EnvironmentalFeatureConfigs;
import com.team_abnormals.environmental.common.world.gen.util.WisteriaTreeUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class WisteriaTreeFeature extends Feature<BaseTreeFeatureConfig> {
	private Supplier<BlockState> VINE_UPPER;
	private Supplier<BlockState> VINE_LOWER;

	public WisteriaTreeFeature(Codec<BaseTreeFeatureConfig> p_i231999_1_) {
		super(p_i231999_1_);
	}

	@Override
	public boolean func_230362_a_(ISeedReader world, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random random, BlockPos pos, BaseTreeFeatureConfig config) {
		if (config.leavesProvider.getBlockState(random, pos) == EnvironmentalFeatureConfigs.BLUE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatureConfigs.BLUE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatureConfigs.BLUE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.leavesProvider.getBlockState(random, pos) == EnvironmentalFeatureConfigs.PINK_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatureConfigs.PINK_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatureConfigs.PINK_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.leavesProvider.getBlockState(random, pos) == EnvironmentalFeatureConfigs.PURPLE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatureConfigs.PURPLE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatureConfigs.PURPLE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}
		if (config.leavesProvider.getBlockState(random, pos) == EnvironmentalFeatureConfigs.WHITE_WISTERIA_LEAVES) {
			VINE_UPPER = () -> EnvironmentalFeatureConfigs.WHITE_HANGING_WISTERIA_LEAVES_TOP;
			VINE_LOWER = () -> EnvironmentalFeatureConfigs.WHITE_HANGING_WISTERIA_LEAVES_BOTTOM;
		}

		int height = random.nextInt(7) + 5;
		boolean flag = true;
		if (pos.getY() >= 1 && pos.getY() + height + 1 <= world.getHeight()) {
			for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == pos.getY()) {
					k = 0;
				}
				if (j >= pos.getY() + 1 + height - 2) {
					k = 2;
				}
				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
				for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
					for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < world.getHeight()) {
							if (!WisteriaTreeUtils.isAirOrLeaves(world, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
			} else if (pos.getY() < world.getHeight() - height - 1) {
				WisteriaTreeUtils.setForcedState(world, pos.down(), Blocks.DIRT.getDefaultState());
				for (int y = 4; y > -4; --y) {
					for (int x = 4; x > -4; --x) {
						for (int z = 4; z > -4; --z) {
							if (Math.sqrt((x * x) + (y > 0 ? (y * y) : 0) + (z * z)) <= 4) {
								BlockPos leafPos = pos.add(x, y + height, z);
								boolean place = true;
								if (y < 0) {
									place = world.hasBlockState(leafPos.add(0, 1, 0), (state) -> {
										return state.isIn(BlockTags.LEAVES);
									});
									if (place && random.nextInt(Math.abs(y) + 1) != 0) {
										place = false;
										if (random.nextInt(4) == 0 && !WisteriaTreeUtils.isLog(world, leafPos)) {
											WisteriaTreeUtils.placeVines(world, random, leafPos, config.leavesProvider.getBlockState(random, pos), VINE_LOWER.get(), VINE_UPPER.get(), config);
										}
									}
								}
								if (place) {
									WisteriaTreeUtils.placeLeafAt(world, leafPos, config, random);
								}
							}
						}
					}
				}
				for (int i2 = 0; i2 < height; ++i2) {
					if (WisteriaTreeUtils.isAirOrLeaves(world, pos.up(i2))) {
						WisteriaTreeUtils.setForcedState(world, pos.up(i2), config.trunkProvider.getBlockState(random, pos));
					}
				}
				placeBranch(world, random, pos.down(), pos.up(height).getY(), config);
				if (random.nextInt(4) == 0)
					placeBranch(world, random, pos.down(), pos.up(height).getY(), config);

				BlockPos startPos = pos.up(height);

				for (BlockPos blockpos : BlockPos.getAllInBoxMutable(startPos.getX() - 10, startPos.getY() - 10, startPos.getZ() - 10, startPos.getX() + 10, startPos.getY() + 10, startPos.getZ() + 10)) {
					if (WisteriaTreeUtils.isAir(world, blockpos) && WisteriaTreeUtils.isLeaves(world, blockpos.up(), config, random) && random.nextInt(4) == 0) {
						if (WisteriaTreeUtils.isAir(world, blockpos))
							WisteriaTreeUtils.setForcedState(world, blockpos, VINE_UPPER.get());
						if (WisteriaTreeUtils.isAir(world, blockpos.down()) && random.nextInt(2) == 0)
							WisteriaTreeUtils.setForcedState(world, blockpos.down(), VINE_LOWER.get());
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void placeBranch(ISeedReader world, Random random, BlockPos pos, int treeHeight, BaseTreeFeatureConfig config) {
		int heightOffset = random.nextInt(3);
		BlockPos[] startPositions = new BlockPos[] { new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ()), new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ()),
				new BlockPos(pos.getX(), treeHeight - heightOffset, pos.getZ() - 1), new BlockPos(pos.getX(), treeHeight - heightOffset, pos.getZ() + 1), new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ() - 1),
				new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ() - 1), new BlockPos(pos.getX() - 1, treeHeight - heightOffset, pos.getZ() + 1), new BlockPos(pos.getX() + 1, treeHeight - heightOffset, pos.getZ() + 1) };
		BlockPos startPos = startPositions[random.nextInt(8)];
		if (WisteriaTreeUtils.isAirOrLeaves(world, startPos)) {
//			boolean vines = random.nextInt(6) != 5;
			BlockPos placePos = startPos;
			for (int y = (treeHeight - heightOffset); y <= treeHeight; ++y) {
				placePos = new BlockPos(startPos.getX(), y, startPos.getZ());
				if (WisteriaTreeUtils.isAirOrLeaves(world, placePos))
					WisteriaTreeUtils.setForcedState(world, placePos, config.trunkProvider.getBlockState(random, pos));
			}
			WisteriaTreeUtils.placeLeafAt(world, placePos.up(), config, random);
//			if (vines) WisteriaTreeUtils.placeVines(world, random, startPos.down(), config.leavesProvider.getBlockState(random, pos), VINE_LOWER.get(), VINE_UPPER.get(), config);
		}
	}
}