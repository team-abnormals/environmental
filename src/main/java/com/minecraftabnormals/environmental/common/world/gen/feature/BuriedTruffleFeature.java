package com.minecraftabnormals.environmental.common.world.gen.feature;

import java.util.Random;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

public class BuriedTruffleFeature extends Feature<NoFeatureConfig> {
	public BuriedTruffleFeature(Codec<NoFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		for(int i = 0; i < 10; ++i) {
			int x = rand.nextInt(8) - rand.nextInt(8);
			int y = rand.nextInt(4);
			int z = rand.nextInt(8) - rand.nextInt(8);
			int j = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() + x, pos.getZ() + z);

			BlockPos blockpos = new BlockPos(pos.getX() + x, j - y, pos.getZ() + z);

			boolean flag = true;

			for(Direction direction : Direction.values()) {
				BlockState blockstate = worldIn.getBlockState(blockpos.offset(direction));

				if (!blockstate.isIn(Tags.Blocks.DIRT)) {
					flag = false;
					break;
				}
			}

			if (flag && worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT) {
				worldIn.setBlockState(blockpos, EnvironmentalBlocks.BURIED_TRUFFLE.get().getDefaultState(), 2);

				return true;
			}
		}

		return false;
	}
}