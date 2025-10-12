package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class PineconeDecorator extends TreeDecorator {
	public static final MapCodec<PineconeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(PineconeDecorator::new, (decorator) -> decorator.probability);
	private final float probability;

	public PineconeDecorator(float probability) {
		this.probability = probability;
	}

	@Override
	public void place(Context context) {
		LevelSimulatedReader level = context.level();
		RandomSource random = context.random();

		if (!(random.nextFloat() >= this.probability)) {
			List<BlockPos> list = context.leaves().stream().filter((blockpos) -> {
				return level.isStateAtPosition(blockpos.below(), BlockState::isAir) && level.isStateAtPosition(blockpos.below(2), BlockState::isAir);
			}).toList();

			if (!list.isEmpty()) {
				BlockPos pos = list.get(random.nextInt(list.size()));
				context.setBlock(pos.below(), EnvironmentalBlocks.PINECONE.get().defaultBlockState());
			}
		}
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalTreeDecorators.PINECONE.get();
	}
}