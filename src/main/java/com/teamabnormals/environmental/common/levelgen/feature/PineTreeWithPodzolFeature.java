package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class PineTreeWithPodzolFeature extends PineTreeFeature {

	public PineTreeWithPodzolFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		boolean flag = super.place(context);
		if (flag) {
			WorldGenLevel level = context.level();
			RandomSource random = context.random();
			BlockPos origin = context.origin();

			MutableBlockPos mutable = new MutableBlockPos();
			for (int x = -1; x <= 1; ++x) {
				for (int z = -1; z <= 1; ++z) {
					for (int y = 0; y >= -2; --y) {
						mutable.setWithOffset(origin, x, y, z);
						BlockState abovestate = level.getBlockState(mutable.above());

						if (abovestate.isAir() && Feature.isGrassOrDirt(level, mutable)) {
							if (x == 0 || z == 0 || random.nextInt(3) == 0) {
								level.setBlock(mutable, Blocks.PODZOL.defaultBlockState(), 2);
							}
							break;
						}
					}
				}
			}
		}
		return flag;
	}
}