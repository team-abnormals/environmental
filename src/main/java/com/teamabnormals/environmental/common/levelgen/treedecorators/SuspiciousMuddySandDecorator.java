package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.other.EnvironmentalLootTables;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SuspiciousMuddySandDecorator extends TreeDecorator {
	public static final SuspiciousMuddySandDecorator INSTANCE = new SuspiciousMuddySandDecorator();
	public static final MapCodec<SuspiciousMuddySandDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public void place(Context context) {
		var logs = context.logs();
		if (logs.isEmpty()) return;
		BlockPos origin = logs.getFirst().below();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int originX = origin.getX();
		int originY = origin.getY();
		int originZ = origin.getZ();
		RandomSource random = context.random();
		LevelSimulatedReader level = context.level();
		for (int y = -2; y <= 2; y++) {
			mutable.setY(originY + y);
			for (int x = -3; x <= 3; x++) {
				mutable.setX(originX + x);
				for (int z = -3; z <= 3; z++) {
					int distanceSqr = x * x + y * y + z * z;
					if (distanceSqr > 9 || random.nextDouble() > 0.5D * (1 - distanceSqr / 10.0D))
						continue;
					mutable.setZ(originZ + z);
					if (!level.isStateAtPosition(mutable, state -> state.getBlock() == EnvironmentalBlocks.MUDDY_SAND.get()))
						continue;
					context.setBlock(mutable, EnvironmentalBlocks.SUSPICIOUS_MUDDY_SAND.get().defaultBlockState());
					level.getBlockEntity(mutable, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(brushableBlockEntity -> {
						brushableBlockEntity.setLootTable(EnvironmentalLootTables.CEDAR_TREE_SUSPICIOUS_SAND, random.nextLong());
					});
				}
			}
		}
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalTreeDecorators.SUSPICIOUS_MUDDY_SAND.get();
	}
}
