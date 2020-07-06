package com.team_abnormals.environmental.common.world.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.team_abnormals.environmental.common.block.RiceBlock;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class RiceFeature extends Feature<NoFeatureConfig> {
    public RiceFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
        boolean place = false;
        for(int i = 0; i < 800; ++i) {
            BlockPos placePos = pos.add(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
            if (i == 0 && !world.hasWater(placePos)){
            	return false;
            }
            if ((world.hasWater(placePos)) && world.isAirBlock(placePos.up()) && placePos.getY() < world.getWorld().getDimension().getHeight() - 2 && EnvironmentalBlocks.RICE.get().getDefaultState().isValidPosition(world, placePos)) {
            	((RiceBlock) EnvironmentalBlocks.RICE.get()).placeAt(world, placePos, 2);
            	place = true;
            }
        }
        return place;
    }   
}