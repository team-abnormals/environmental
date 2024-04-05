package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class PinePodzolDecorator extends TreeDecorator {
	public static final Codec<PinePodzolDecorator> CODEC;
	public static final PinePodzolDecorator INSTANCE = new PinePodzolDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalFeatures.PINE_PODZOL.get();
	}

	@Override
	public void place(Context context) {
		LevelSimulatedReader level = context.level();
		BlockPos origin = context.logs().get(0).below();
		MutableBlockPos mutable = new MutableBlockPos();
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (Math.abs(x) != 1 || Math.abs(z) != 1) {
					if (x == 0 && z == 0 || context.random().nextInt(4) == 0) {
						mutable.set(origin.offset(x, 0, z));
						if (Feature.isGrassOrDirt(level, mutable)) {
							context.setBlock(mutable, Blocks.PODZOL.defaultBlockState());
							mutable.move(Direction.UP);
							if (level.isStateAtPosition(mutable, BlockStateBase::isAir))
								context.setBlock(mutable, Blocks.GRASS.defaultBlockState());
						}
					}
				}
			}
		}
	}

	static {
		CODEC = Codec.unit(() -> INSTANCE);
	}
}