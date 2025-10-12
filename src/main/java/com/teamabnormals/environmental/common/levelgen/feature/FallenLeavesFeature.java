package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.FallenLeavesConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Set;

public class FallenLeavesFeature extends Feature<FallenLeavesConfiguration> {

	public FallenLeavesFeature(Codec<FallenLeavesConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<FallenLeavesConfiguration> context) {
		FallenLeavesConfiguration config = context.config();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		Set<BlockPos> positions = placeLeaves(config.radius(), config.ySpread(), context.origin(), context.level(), context.random());
		if (!positions.isEmpty()) {
			for (BlockPos blockpos : positions)
				level.setBlock(blockpos, config.provider().getState(random, blockpos), 2);
			return true;
		} else {
			return false;
		}
	}

	public static Set<BlockPos> placeLeaves(int radius, int ySpread, BlockPos origin, LevelSimulatedReader level, RandomSource random) {
		Set<BlockPos> positions = Sets.newHashSet();
		MutableBlockPos mutable = new MutableBlockPos();
		int ditheroffset = random.nextBoolean() ? 1 : 0;

		for (int x = -radius; x <= radius; ++x) {
			for (int z = -radius; z <= radius; ++z) {
				if ((Math.abs(x) < radius || Math.abs(z) < radius - 1) && (Math.abs(x) < radius - 1 || Math.abs(z) < radius)) {
					for (int y = ySpread; y >= -ySpread; --y) {
						mutable.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);

						float leafdensity = 1.0F - (float) Math.max(Math.abs(x), Math.abs(z)) / (radius * 2.0F);
						leafdensity += random.nextFloat() * 0.35F;
						if (Math.abs(x + z + 2 * radius) % 2 == ditheroffset)
							leafdensity -= 0.45F;

						if (leafdensity >= 0.5F && level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::isAir) && Feature.isGrassOrDirt(level, mutable.below())) {
							positions.add(mutable.immutable());
							break;
						}
					}
				}
			}
		}

		return positions;
	}
}