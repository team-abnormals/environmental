package com.team_abnormals.environmental.common.world.gen.feature;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class WillowTreeFeature extends AbstractSmallTreeFeature<TreeFeatureConfig> {
   public WillowTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225820_1_) {
      super(p_i225820_1_);
   }

   public boolean place(IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundsIn, TreeFeatureConfig config) {
      int i = config.baseHeight + rand.nextInt(config.heightRandA + 1) + rand.nextInt(config.heightRandB + 1);
      int j = config.trunkHeight >= 0 ? config.trunkHeight + rand.nextInt(config.trunkHeightRandom + 1) : i - (config.foliageHeight + rand.nextInt(config.foliageHeightRandom + 1));
      int k = config.foliagePlacer.func_225573_a_(rand, j, i, config);
      Optional<BlockPos> optional = this.func_227212_a_(world, i, j, k, pos, config);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos blockpos = optional.get();
         this.setDirtAt(world, blockpos.down(), blockpos);
         config.foliagePlacer.func_225571_a_(world, rand, config, i, j, k, blockpos, p_225557_5_);
         this.func_227213_a_(world, rand, i, blockpos, config.trunkTopOffset + rand.nextInt(config.trunkTopOffsetRandom + 1), p_225557_4_, boundsIn, config);
         return true;
      }
   }
}