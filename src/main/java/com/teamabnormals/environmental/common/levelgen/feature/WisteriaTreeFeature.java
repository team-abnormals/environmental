package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import com.teamabnormals.environmental.common.block.ColoredWisteriaLeavesBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.List;
import java.util.Set;

public class WisteriaTreeFeature extends BlueprintTreeFeature {

	public WisteriaTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public void doPlace(FeaturePlaceContext<TreeConfiguration> context) {
		TreeConfiguration config = context.config();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		int trunkHeight = config.trunkPlacer.getTreeHeight(random);
		for (int y = 0; y < trunkHeight; y++) {
			this.addLog(origin.above(y));
		}

		Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
		MutableBlockPos pos = new MutableBlockPos();
		pos.set(origin.above(trunkHeight - 2).relative(direction));

		for (int y = 0; y < 3; y++) {
			this.addLog(pos.set(pos.above()));
		}

		List<Direction> rootDirections = Lists.newArrayList();
		for (Direction direction1 : Plane.HORIZONTAL) {
			if (isGrassOrDirt(context.level(), origin.relative(direction1).below()) && (trunkHeight > 2 || direction1 != direction))
				rootDirections.add(direction1);
		}

		if (!rootDirections.isEmpty()) {
			this.addLog(origin.relative(rootDirections.get(random.nextInt(rootDirections.size()))));
		}

		pos.set(pos.below().relative(Plane.HORIZONTAL.getRandomDirection(random)));
		for (int y = 0; y < 3; y++) {
			this.addLog(pos.set(pos.above()));
		}

		Direction direction1 = Plane.HORIZONTAL.getRandomDirection(random);
		Direction offset = direction1.getClockWise().getOpposite();
		Set<Direction> directions = Set.of(direction1, direction1.getOpposite(), direction1.getClockWise());
		directions.forEach(branchDirection -> {
			BlockPos branchPos = branchDirection == direction1 ? pos.relative(offset) : pos;
			int height = random.nextInt(2);
			if (height > 0)
				this.addLog(pos.above());
			this.createBranch(branchPos.relative(branchDirection).above(height), branchDirection, random, config);
		});
	}

	private void createBranch(BlockPos pos, Direction direction, RandomSource random, TreeConfiguration config) {
		MutableBlockPos mutablePos = new MutableBlockPos();
		mutablePos.set(pos);

		this.addLog(mutablePos);
		this.addLog(mutablePos.set(mutablePos.relative(direction)));
		this.addLog(mutablePos.set(mutablePos.relative(direction).above()));

		this.createLeaves(mutablePos, direction, random, config);
	}

	private void createLeaves(BlockPos pos, Direction direction, RandomSource random, TreeConfiguration config) {
		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				int i = -1 - (random.nextInt(3) == 0 ? 1 : 0);
				int j = i < -1 && random.nextInt(2) == 0 ? -1 : 0;
				for (int y = 1; y >= i; --y) {
					if (y <= 0 || x == 0 || z == 0 || random.nextInt(3) == 0) {
						BlockPos blockpos = pos.offset(x, y, z);
						if (y > j || (y == 0 && x == -direction.getStepX() && z == -direction.getStepZ()))
							this.addSpecialFoliage(blockpos, EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState());
						else if (y == j)
							this.addSpecialFoliage(blockpos, config.foliageProvider.getState(random, blockpos).setValue(ColoredWisteriaLeavesBlock.HALF, Half.TOP));
						else
							this.addSpecialFoliage(blockpos, config.foliageProvider.getState(random, blockpos));
					}
				}
			}
		}
	}

	@Override
	public BlockState getSapling() {
		return EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get().defaultBlockState();
	}
}