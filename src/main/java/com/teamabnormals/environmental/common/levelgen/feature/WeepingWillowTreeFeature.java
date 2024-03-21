package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.block.wood.LogBlock;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.List;

public class WeepingWillowTreeFeature extends BlueprintTreeFeature {

	public WeepingWillowTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public void doPlace(FeaturePlaceContext<TreeConfiguration> context) {
		TreeConfiguration config = context.config();
		BlockPos origin = context.origin();
		RandomSource random = context.random();

		int trunkHeight = config.trunkPlacer.getTreeHeight(random);
		int branches = 2 + random.nextInt(2);

		List<Integer> branchHeights = Lists.newArrayList(3, 4, 5);
		List<Direction> branchDirections = Lists.newArrayList();
		Plane.HORIZONTAL.forEach(branchDirections::add);

		for (int i = 0; i < branches; i++) {
			int branchHeight = branchHeights.remove(random.nextInt(branchHeights.size()));
			Direction direction = branchDirections.remove(random.nextInt(branchDirections.size()));

			this.createBranch(origin.above(branchHeight), direction, random, config);
		}

		Direction direction = branchDirections.get(random.nextInt(branchDirections.size()));

		for (int i = 0; i < trunkHeight; i++) {
			if (i < trunkHeight - 5)
				this.addLog(origin.above(i));
			else
				this.addLog(origin.above(i).relative(direction));
		}

		this.createTopLeafBlob(origin.above(trunkHeight).relative(direction), random, direction);
	}

	private void createBranch(BlockPos pos, Direction direction, RandomSource random, TreeConfiguration config) {
		int length = 3 + random.nextInt(2);
		int offsetpos = 1 + random.nextInt(length - 1);

		MutableBlockPos mutablepos = new MutableBlockPos();
		mutablepos.set(pos);

		for (int i = 0; i < length; i++) {
			if (i == offsetpos)
				mutablepos.set(mutablepos.above());
			this.addSpecialLog(mutablepos.set(mutablepos.relative(direction)), config.trunkProvider.getState(random, mutablepos).setValue(LogBlock.AXIS, direction.getAxis()));
		}

		this.createBranchLeafBlob(mutablepos, random, direction, length <= 3);
	}

	private void createTopLeafBlob(BlockPos pos, RandomSource random, Direction direction) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

		for (int y = 0; y >= -3; y--) {
			int r = 2 - y / 2;
			for (int x = -r; x <= r; x++) {
				for (int z = -r; z <= r; z++) {
					if (Math.abs(x) != r || Math.abs(z) != r || (random.nextInt(2) == 0 && y != 0)) {
						mutablepos.setWithOffset(pos, x, y, z);
						this.addFoliage(mutablepos);
					}
				}
			}
		}

		this.createLeafWall(pos.below(4), random, direction, 3, 2);
	}

	private void createBranchLeafBlob(BlockPos pos, RandomSource random, Direction direction, boolean small) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
		int i = small ? 1 : 2;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (Math.abs(x) != 1 || Math.abs(z) != 1 || random.nextInt(4) == 0) {
					mutablepos.setWithOffset(pos, x, i, z);
					this.addFoliage(mutablepos);
				}
			}
		}

		for (int y = i - 1; y >= 0; y--) {
			for (int x = -i; x <= i; x++) {
				for (int z = -i; z <= i; z++) {
					if (Math.abs(x) != 2 || Math.abs(z) != 2) {
						mutablepos.setWithOffset(pos, x, y, z);
						this.addFoliage(mutablepos);
					}
				}
			}
		}

		this.createLeafWall(pos.below(), random, direction, i, 3);
	}

	private void createLeafWall(BlockPos pos, RandomSource random, Direction direction, int width, int maxLength) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

		for (int x = -width; x <= width; x++) {
			for (int z = -width; z <= width; z++) {
				if (this.shouldPlaceLeafColumn(x, z, direction, width)) {
					boolean flag = Math.abs(x) >= 2 && Math.abs(z) >= 2;
					int i = flag ? random.nextInt(2) : 1 + random.nextInt(maxLength);

					for (int y = 0; y > -i; y--) {
						mutablepos.setWithOffset(pos, x, y, z);
						this.addFoliage(mutablepos);
					}
				}
			}
		}
	}

	private boolean shouldPlaceLeafColumn(int x, int z, Direction direction, int width) {
		Vec3i normal = direction.getNormal();
		int edgex = normal.getX() * width;
		int edgez = normal.getZ() * width;

		if (Math.abs(x) != width && Math.abs(z) != width)
			return false;
		else if (width > 1 && Math.abs(x) == width && Math.abs(z) == width)
			return false;
		else if ((edgex != 0 && x == -edgex) || (edgez != 0 && z == -edgez))
			return false;

		return true;
	}

	@Override
	public BlockState getSapling() {
		return EnvironmentalBlocks.WILLOW_SAPLING.get().defaultBlockState();
	}

}