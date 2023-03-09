package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.block.wood.LogBlock;
import com.teamabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

public class WisteriaTreeFeature extends EnvironmentalTreeFeature {

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

		ArrayList<Direction> branchDirections = Lists.newArrayList();
		Plane.HORIZONTAL.forEach(branchDirections::add);

		int branches = 2 + random.nextInt(3 - random.nextInt(2));
		boolean offset = false;

		for (int i = 0; i < branches; i++) {
			int down = 2;
			if (!offset && random.nextBoolean()) {
				down = 1;
				offset = true;
			}
			Direction direction = branchDirections.get(random.nextInt(branchDirections.size()));
			this.createBranch(origin.above(trunkHeight).below(down), direction, random, config);

			branchDirections.remove(direction);
		}

		this.createLeafClump(origin.above(trunkHeight), null);

		Set<BlockPos> vinePos = Sets.newHashSet();
		for (BlockPos pos : this.foliagePositions) {
			if (!this.foliagePositions.contains(pos.below())) {
				vinePos.add(pos.below(random.nextInt(2)));
			}
		}

		vinePos.forEach(newPos -> {
			this.addSpecialFoliage(newPos, config.foliageProvider.getState(random, newPos).setValue(WisteriaLeavesBlock.HALF, Half.TOP));
			this.addFoliage(newPos.below());
		});
	}

	@Override
	public void doPostPlace(FeaturePlaceContext<TreeConfiguration> context) {
		BlockState state = context.config().foliageProvider.getState(context.random(), context.origin());
		if (state.is(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.WHITE_DELPHINIUM.get().defaultBlockState();
		else if (state.is(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.BLUE_DELPHINIUM.get().defaultBlockState();
		else if (state.is(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.PURPLE_DELPHINIUM.get().defaultBlockState();
		else state = EnvironmentalBlocks.PINK_DELPHINIUM.get().defaultBlockState();
		Feature.RANDOM_PATCH.place(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state))), context.level(), context.chunkGenerator(), context.random(), context.origin());
	}

	@Override
	public Block getSapling() {
		return EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get();
	}

	private void createBranch(BlockPos pos, Direction direction, RandomSource random, TreeConfiguration config) {
		MutableBlockPos mutablePos = new MutableBlockPos();
		mutablePos.set(pos);

		for (int i = 0; i < 3; i++) {
			this.addSpecialLog(mutablePos.set(mutablePos.relative(direction)), config.trunkProvider.getState(random, mutablePos).setValue(LogBlock.AXIS, direction.getAxis()));
		}

		this.createLeafClump(mutablePos.above(), direction);
	}

	private void createLeafLayer(BlockPos pos, boolean square, Direction direction, BlockState state) {
		int leafSize = 1;
		for (int i = -leafSize; i <= leafSize; ++i) {
			for (int k = -leafSize; k <= leafSize; ++k) {
				if (square) {
					this.addSpecialFoliage(pos.offset(i, 0, k), state);
				} else if ((Math.abs(i) != leafSize || Math.abs(k) != leafSize)) {
					this.addSpecialFoliage(pos.offset(i, 0, k), state);
				}
			}
		}

		if (!square && direction != null) {
			this.addSpecialFoliage(pos.relative(direction.getOpposite(), 1).relative(direction.getClockWise()), state);
			this.addSpecialFoliage(pos.relative(direction.getOpposite(), 1).relative(direction.getCounterClockWise()), state);
			this.addSpecialFoliage(pos.relative(direction.getOpposite(), 2).relative(direction.getClockWise()), state);
			this.addSpecialFoliage(pos.relative(direction.getOpposite(), 2).relative(direction.getCounterClockWise()), state);
		}
	}

	private void createLeafClump(BlockPos pos, @Nullable Direction direction) {
		createLeafLayer(pos, false, direction, EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState());
		createLeafLayer(pos.below(), true, direction, EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState());
	}
}