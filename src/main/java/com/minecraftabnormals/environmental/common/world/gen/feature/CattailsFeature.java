package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.environmental.common.block.CattailBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CattailsFeature extends Feature<NoFeatureConfig> {

	public CattailsFeature(Codec<NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator p_230362_3_, Random random, BlockPos pos, NoFeatureConfig p_230362_6_) {
		boolean place = false;
		for (int i = 0; i < 512; ++i) {
			BlockPos placePos = pos.offset(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
			if (i == 0 && !world.isWaterAt(placePos)) {
				return false;
			}
			if ((world.isWaterAt(placePos) || world.isEmptyBlock(placePos)) && world.isEmptyBlock(placePos.above()) && EnvironmentalBlocks.CATTAIL.get().defaultBlockState().canSurvive(world, placePos)) {
				((CattailBlock) EnvironmentalBlocks.CATTAIL.get()).placeAt(world, placePos, 2);
				place = true;
			}
		}
		return place;
	}
}