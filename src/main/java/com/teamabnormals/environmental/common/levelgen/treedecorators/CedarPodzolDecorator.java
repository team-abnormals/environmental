package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class CedarPodzolDecorator extends TreeDecorator {
	public static final CedarPodzolDecorator INSTANCE = new CedarPodzolDecorator();
	public static final MapCodec<CedarPodzolDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

	@SuppressWarnings("deprecation")
	@Override
	public void place(Context context) {
		var logs = context.logs();
		if (logs.isEmpty()) return;
		BlockPos origin = logs.getFirst().below();
		int originX = origin.getX();
		int originZ = origin.getZ();
		int originY = origin.getY();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		RandomSource random = context.random();
		LevelSimulatedReader level = context.level();
		for (int x = -2; x <= 2; x++) {
			mutable.setX(originX + x);
			for (int z = -2; z <= 2; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared >= 4) continue;
				float chance = distanceSquared > 1 ? 0.5F : 0.8F;
				mutable.setZ(originZ + z);
				vertical:
				for (int y = -1; y <= 1; y++) {
					if (random.nextFloat() > chance) continue;
					mutable.setY(originY + y);
					int prevY = mutable.getY();
					if (!level.isStateAtPosition(mutable, state -> state.is(EnvironmentalBlocks.MUDDY_PODZOL))) continue;
					for (Direction horizontalFacing : Direction.Plane.HORIZONTAL) {
						mutable.move(horizontalFacing);
						if (!level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::isSolid)) {
							mutable.move(horizontalFacing.getOpposite());
							continue vertical;
						}
						mutable.move(horizontalFacing.getOpposite());
					}
					if (!level.isStateAtPosition(mutable.setY(prevY + 1), BlockBehaviour.BlockStateBase::isAir)) continue;
					context.setBlock(mutable.setY(prevY), Blocks.PODZOL.defaultBlockState());
				}
			}
		}
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalTreeDecorators.CEDAR_PODZOL.get();
	}
}
