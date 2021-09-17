package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FallenLeavesFeature extends Feature<NoFeatureConfig> {
	public FallenLeavesFeature(Codec<NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		int i = 0;
		BlockState blockstate = EnvironmentalBlocks.CHERRY_LEAF_CARPET.get().defaultBlockState();

		for (int x = -3; x <= 3; ++x) {
			for (int z = -3; z <= 3; ++z) {
				if (Math.abs(x) < 2 || Math.abs(z) < 2) {
					for (int y = -3; y <= 3; ++y) {
						BlockPos blockpos = pos.offset(x, y, z);
						if (rand.nextInt(3) > 0 && worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
							worldIn.setBlock(blockpos, blockstate, 2);
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}
}