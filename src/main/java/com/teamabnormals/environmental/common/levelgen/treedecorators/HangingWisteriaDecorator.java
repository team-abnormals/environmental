package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HangingWisteriaDecorator extends TreeDecorator {
	public static final Codec<HangingWisteriaDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(HangingWisteriaDecorator::new, (decorator) -> decorator.probability).codec();
	private final float probability;

	public HangingWisteriaDecorator(float probability) {
		this.probability = probability;
	}

	@Override
	public void place(Context context) {
		LevelSimulatedReader level = context.level();
		RandomSource random = context.random();
		BlockState state = this.getBlockForLeaf();

		for (BlockPos pos : context.leaves()) {
			int maxLength;
			for (maxLength = 0; maxLength < 2 && level.isStateAtPosition(pos.below(maxLength + 1), BlockState::isAir); maxLength++) {
			}

			if (maxLength > 0) {
				int actualLength = Math.max(1, random.nextInt(1 + maxLength));
				for (int i = 1; i <= actualLength; i++) {
					context.setBlock(pos.below(i), state.setValue(HangingWisteriaLeavesBlock.HALF, i == actualLength ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER));
				}
			}
		}
	}

	public BlockState getBlockForLeaf() {
		if (this.probability < 0.25F) return EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		if (this.probability < 0.50F) return EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		if (this.probability < 0.75F) return EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
		return EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().defaultBlockState();
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalFeatures.HANGING_WISTERIA_LEAVES.get();
	}
}