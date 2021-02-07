package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.environmental.common.block.RiceBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class RiceFeature extends Feature<NoFeatureConfig> {

	public RiceFeature(Codec<NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
		boolean place = false;
		for (int i = 0; i < 800; ++i) {
			BlockPos placePos = pos.add(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
			if (i == 0 && !world.hasWater(placePos)) {
				return false;
			}
			if ((world.hasWater(placePos)) && world.isAirBlock(placePos.up()) && EnvironmentalBlocks.RICE.get().getDefaultState().isValidPosition(world, placePos)) {
				((RiceBlock) EnvironmentalBlocks.RICE.get()).placeAt(world, placePos, 2);
				place = true;
			}
		}
		return place;
	}
}