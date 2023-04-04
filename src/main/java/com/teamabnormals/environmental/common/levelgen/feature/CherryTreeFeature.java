package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class CherryTreeFeature extends BlueprintTreeFeature {

	public CherryTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}


	@Override
	public void doPlace(FeaturePlaceContext<TreeConfiguration> context) {
		RandomSource random = context.random();
		BlockPos origin = context.origin();
		TreeConfiguration config = context.config();

		int trunkHeight = config.trunkPlacer.getTreeHeight(random);
		for (int y = 0; y < trunkHeight; y++) {
			this.addLog(origin.above(y));
		}

		Plane.HORIZONTAL.stream().forEach(direction -> {
			BlockPos stumpPos = origin.relative(direction);
			if (isGrassOrDirt(context.level(), stumpPos.below())) {
				this.addLog(stumpPos);
				BlockPos sideStumpPos = stumpPos.relative(direction.getClockWise());
				if (random.nextBoolean() && isGrassOrDirt(context.level(), sideStumpPos.below())) {
					this.addLog(sideStumpPos);
				}

				if (random.nextBoolean()) {
					this.addLog(stumpPos.above());
				}
			}
		});

		Plane.HORIZONTAL.stream().forEach(direction -> {
			BlockPos newPos = this.createCherryBranch(origin.above(trunkHeight - random.nextInt(3)), direction, random, config);
			for (int i = 0; i < 5; ++i) {
				this.createCherryLeaves(newPos.above().below(i), random, i);
			}
		});
	}

	private void createCherryLeaves(BlockPos pos, RandomSource random, int leafLevel) {
		int leafSize = 2;
		for (int k = -leafSize; k <= leafSize; ++k) {
			for (int j = -leafSize; j <= leafSize; ++j) {
				if (leafLevel == 2) {
					this.addFoliage(pos.offset(k, 0, j));
				} else {
					if (leafLevel > 1 && leafLevel < 4 && (Math.abs(k) != leafSize || Math.abs(j) != leafSize)) {
						this.addFoliage(pos.offset(k, 0, j));
					} else if ((leafLevel == 1 || leafLevel == 4) && (Math.abs(k) <= 1 && Math.abs(j) <= 1)) {
						if ((!(Math.abs(k) == 1 && Math.abs(j) == 1 && leafLevel == 4) && !(Math.abs(k) == 2 && Math.abs(j) == 2)) || random.nextBoolean()) {
							this.addFoliage(pos.offset(k, 0, j));
						}
					} else if (leafLevel == 1) {
						if (random.nextInt(3) == 0 && ((Math.abs(k) == 1 && Math.abs(j) == 2) || (Math.abs(k) == 2 && Math.abs(j) == 1))) {
							this.addFoliage(pos.offset(k, 0, j));
						} else if (random.nextInt(4) != 0 && ((Math.abs(k) == 0 && Math.abs(j) == 2) || (Math.abs(k) == 2 && Math.abs(j) == 0))) {
							this.addFoliage(pos.offset(k, 0, j));
						}
					}
				}
			}
		}
	}

	private BlockPos createCherryBranch(BlockPos pos, Direction direction, RandomSource random, TreeConfiguration config) {
		MutableBlockPos mutablePos = new MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());

		int length = 3 + random.nextInt(3) + random.nextInt(3);
		for (int i = 0; i < length; i++) {
			this.addLog(mutablePos.relative(direction));
			if (random.nextInt(3) != 0) {
				mutablePos.set(mutablePos.above());
			}

			mutablePos.set(mutablePos.relative(direction, random.nextInt(2)));
		}

		for (int i = 0; i < 3; i++) {
			this.addLog(mutablePos.relative(direction));
			mutablePos.set(mutablePos.above());
		}

		return mutablePos.relative(direction);
	}


	@Override
	public BlockState getSapling() {
		return EnvironmentalBlocks.CHERRY_SAPLING.get().defaultBlockState();
	}
}
