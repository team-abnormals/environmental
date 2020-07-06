package com.team_abnormals.environmental.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.team_abnormals.environmental.common.block.RiceBlock;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class RiceFeature extends Feature<NoFeatureConfig> {
	
    public RiceFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager p_230362_2_, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        boolean place = false;
        for(int i = 0; i < 800; ++i) {
            BlockPos placePos = pos.add(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
            if (i == 0 && !world.hasWater(placePos)){
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