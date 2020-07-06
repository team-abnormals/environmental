package com.team_abnormals.environmental.common.world.gen.treedecorator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class HangingWillowLeavesTreeDecorator extends TreeDecorator {
   public HangingWillowLeavesTreeDecorator() {
      super(TreeDecoratorType.LEAVE_VINE);
   }

   public <T> HangingWillowLeavesTreeDecorator(Dynamic<T> p_i225870_1_) {
      this();
   }

   @SuppressWarnings("deprecation")
public void func_225576_a_(IWorld world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> changedBlocks, MutableBoundingBox boundsIn) {
	   for (BlockPos pos : leavesPositions) {
		   if (world.getBlockState(pos.down()).isAir()) {
			   if (rand.nextInt(2) == 0) {
				   world.setBlockState(pos.down(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState(), 3);
			   }
		   }
	   }
   }
   
   public <T> T serialize(DynamicOps<T> p_218175_1_) {
	      return (new Dynamic<>(p_218175_1_, p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("type"), p_218175_1_.createString(Registry.TREE_DECORATOR_TYPE.getKey(this.field_227422_a_).toString()))))).getValue();
	   }
}