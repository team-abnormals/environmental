package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DuckNestFeature extends Feature<NoFeatureConfig> {
	public DuckNestFeature(Codec<NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		BlockState blockstate = EnvironmentalBlocks.TWIG_DUCK_NEST.get().defaultBlockState().setValue(BirdNestBlock.EGGS, 2 + rand.nextInt(3));

		int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockPos blockpos = new BlockPos(pos.getX(), i, pos.getZ());

		if (worldIn.isEmptyBlock(blockpos) && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
			worldIn.setBlock(blockpos, blockstate, 2);

			return true;
		}

		return false;
	}
}