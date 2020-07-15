package com.team_abnormals.environmental.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.team_abnormals.environmental.common.block.CattailBlock;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class DenseCattailsFeature extends Feature<NoFeatureConfig> {

    public DenseCattailsFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager p_230362_2_, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        boolean place = false;
        for (int i = 0; i < 1536; ++i) {
            BlockPos placePos = pos.add(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
            if (i == 0 && !world.hasWater(placePos)) {
                return false;
            }
            if ((world.hasWater(placePos) || world.isAirBlock(placePos)) && world.isAirBlock(placePos.up()) && EnvironmentalBlocks.CATTAIL.get().getDefaultState().isValidPosition(world, placePos)) {
                ((CattailBlock) EnvironmentalBlocks.CATTAIL.get()).placeAt(world, placePos, 2);
                place = true;
            }
        }
        return place;
    }
}