package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

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

		MutableBlockPos pos = new MutableBlockPos();
		pos.set(origin.above(trunkHeight - 2).relative(Plane.HORIZONTAL.getRandomDirection(random)));

		for (int i = 0; i < 3; i++) {
			this.addLog(pos.set(pos.above()));
		}

		pos.set(pos.below().relative(Plane.HORIZONTAL.getRandomDirection(random)));
		for (int i = 0; i < 4; i++) {
			this.addLog(pos.set(pos.above()));
		}

		Axis axis = Plane.HORIZONTAL.getRandomAxis(random);
		pos.set(pos.below());
		Plane.HORIZONTAL.stream().filter(direction -> direction.getAxis() == axis).forEach(direction -> {
			this.addLog(pos.relative(direction));
			pos.set(pos.above());

			BlockPos leafPos = pos.relative(direction, 2);
			this.addLog(leafPos);

			this.createLeafLayer(leafPos.above(), false, direction, random, EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState());
			this.createLeafLayer(leafPos, true, direction, random, config.foliageProvider.getState(random, pos).setValue(WisteriaLeavesBlock.HALF, Half.TOP));
			this.createLeafLayer(leafPos.below(), true, direction, random, config.foliageProvider.getState(random, pos));
			this.createLeafLayer(leafPos.below(2), true, direction, random, config.foliageProvider.getState(random, pos), 3);
		});
	}

	@Override
	public void doPostPlace(FeaturePlaceContext<TreeConfiguration> context) {
		BlockState state = context.config().foliageProvider.getState(context.random(), context.origin());
		if (state.is(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.WHITE_DELPHINIUM.get().defaultBlockState();
		else if (state.is(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.BLUE_DELPHINIUM.get().defaultBlockState();
		else if (state.is(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get())) state = EnvironmentalBlocks.PURPLE_DELPHINIUM.get().defaultBlockState();
		else state = EnvironmentalBlocks.PINK_DELPHINIUM.get().defaultBlockState();
		//Feature.RANDOM_PATCH.place(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state))), context.level(), context.chunkGenerator(), context.random(), context.origin());
	}

	private void createLeafLayer(BlockPos pos, boolean square, Direction direction, RandomSource random, BlockState state) {
		this.createLeafLayer(pos, square, direction, random, state, 0);
	}

	private void createLeafLayer(BlockPos pos, boolean square, Direction direction, RandomSource random, BlockState state, int chance) {
		int leafSize = 1;
		for (int i = -leafSize; i <= leafSize; ++i) {
			for (int k = -leafSize; k <= leafSize; ++k) {
				if (chance == 0 || random.nextInt(chance) == 0) {
					if (square) {
						this.addSpecialFoliage(pos.offset(i, 0, k), state);
					} else if ((Math.abs(i) != leafSize || Math.abs(k) != leafSize)) {
						this.addSpecialFoliage(pos.offset(i, 0, k), state);
					}
				}
			}
		}

		if (!square && direction != null) {
			if (random.nextBoolean()) {
				this.addSpecialFoliage(pos.relative(direction.getOpposite(), 1).relative(direction.getClockWise()), state);
			}

			if (random.nextBoolean()) {
				this.addSpecialFoliage(pos.relative(direction.getOpposite(), 1).relative(direction.getCounterClockWise()), state);
			}
		}
	}

	@Override
	public BlockState getSapling() {
		return EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get().defaultBlockState();
	}
}