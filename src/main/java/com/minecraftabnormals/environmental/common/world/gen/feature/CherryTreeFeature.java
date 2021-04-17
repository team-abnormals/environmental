package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.abnormals_core.core.util.TreeUtil;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CherryTreeFeature extends Feature<BaseTreeFeatureConfig> {
	public CherryTreeFeature(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos position, BaseTreeFeatureConfig config) {
		int height = 5 + rand.nextInt(3) + rand.nextInt(3) + rand.nextInt(3);
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + height + 1 <= worldIn.getHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == position.getY()) k = 0;
				if (j >= position.getY() + 1 + height - 2) k = 2;
				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getHeight()) {
							if (!TreeUtil.isAirOrLeaves(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
								flag = false;
						} else
							flag = false;
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isDirtAt(worldIn, position.down()) && position.getY() < worldIn.getHeight()) {
				// base log
				TreeUtil.setDirtAt(worldIn, position.down());

				int logX = position.getX();
				int logZ = position.getZ();

				for (int k1 = 0; k1 < height; ++k1) {
					int logY = position.getY() + k1;
					BlockPos blockpos = new BlockPos(logX, logY, logZ);
					if (TreeUtil.isAirOrLeaves(worldIn, blockpos)) {
						this.placeLogAt(worldIn, blockpos, Direction.UP, rand, config);
					}
				}

				Plane.HORIZONTAL.getDirectionValues().forEach(direction -> {
					BlockPos stumpPos = position.offset(direction);
					if (TreeUtil.isAirOrLeaves(worldIn, stumpPos) && isDirtAt(worldIn, stumpPos.down())) {
						this.placeLogAt(worldIn, stumpPos, Direction.UP, rand, config);
						BlockPos sideStumpPos = stumpPos.offset(direction.rotateY());
						if (rand.nextBoolean() && isDirtAt(worldIn, sideStumpPos.down()) && TreeUtil.isAirOrLeaves(worldIn, sideStumpPos))
							this.placeLogAt(worldIn, sideStumpPos, Direction.UP, rand, config);
						if (rand.nextBoolean() && TreeUtil.isAirOrLeaves(worldIn, stumpPos.up()))
							this.placeLogAt(worldIn, stumpPos.up(), Direction.UP, rand, config);
					}
				});


				Plane.HORIZONTAL.getDirectionValues().forEach(direction -> {
					int newHeight = rand.nextBoolean() ? height + rand.nextInt(2) : height - rand.nextInt(2);
					BlockPos newPos = this.createCherryBranch(newHeight, worldIn, position, direction, rand, config);
					for (int i = 0; i < 5; ++i) {
						this.createCherryLeaves(worldIn, newPos.up(2).down(i), rand, i, config);
					}
				});
				return true;
			} else {
				return false;
			}
		} else {
			return false;

		}
	}

	private void createCherryLeaves(IWorldGenerationReader worldIn, BlockPos newPos, Random rand, int level, BaseTreeFeatureConfig config) {
		int leafSize = 2;
		for (int k3 = -leafSize; k3 <= leafSize; ++k3) {
			for (int j4 = -leafSize; j4 <= leafSize; ++j4) {
				if (level == 2) {
					this.placeLeafAt(worldIn, newPos.add(k3, 0, j4), rand, config);
				} else {
					if (level > 0 && level < 4 && (Math.abs(k3) != leafSize || Math.abs(j4) != leafSize)) {
						this.placeLeafAt(worldIn, newPos.add(k3, 0, j4), rand, config);
					} else if (rand.nextInt(4) == 0) {
						this.placeLeafAt(worldIn, newPos.add(k3, 0, j4), rand, config);
					}
				}
			}
		}
	}

	private BlockPos createCherryBranch(int height, IWorldGenerationReader worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		int logX = pos.getX();
		int logZ = pos.getZ();
		int logY = pos.getY() + height - 1;
		int length = 4 + rand.nextInt(3) + rand.nextInt(3);
		BlockPos blockpos = new BlockPos(logX, logY, logZ);

		for (int i = 0; i < length; i++) {
			blockpos = new BlockPos(logX, logY, logZ);

			this.createHorizontalLog(worldIn, blockpos, direction, rand, config);
			if (direction == Direction.EAST || direction == Direction.WEST) {
				if (direction == Direction.EAST) {
					logX += rand.nextInt(2);
				} else {
					logX -= rand.nextInt(2);
				}
			} else {
				if (direction == Direction.SOUTH) {
					logZ += rand.nextInt(2);
				} else {
					logZ -= rand.nextInt(2);
				}
				
			}

			logY += 1;
		}
		return blockpos.offset(direction);
	}

	private void createHorizontalLog(IWorldGenerationReader worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		int logX = pos.getX();
		int logY = pos.getY();
		int logZ = pos.getZ();

		for (int k3 = 0; k3 < 1; ++k3) {
			logX += direction.getXOffset();
			logZ += direction.getZOffset();
			BlockPos blockpos1 = new BlockPos(logX, logY, logZ);
			if (TreeUtil.isAirOrLeaves(worldIn, blockpos1)) {
				this.placeLogAt(worldIn, blockpos1, Direction.UP, rand, config);
			}
		}
	}

	private void placeLogAt(IWorldWriter worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		BlockState logState = config.trunkProvider.getBlockState(rand, pos).with(RotatedPillarBlock.AXIS, direction.getAxis());
		TreeUtil.setForcedState(worldIn, pos, logState);
	}

	private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
		if (TreeUtil.isAirOrLeaves(world, pos)) {
			if (config.leavesProvider.getBlockState(rand, pos).hasProperty(LeavesBlock.DISTANCE)) {
				TreeUtil.setForcedState(world, pos, config.leavesProvider.getBlockState(rand, pos).with(LeavesBlock.DISTANCE, 1));
			} else {
				TreeUtil.setForcedState(world, pos, config.leavesProvider.getBlockState(rand, pos));
			}
		}
	}
}
