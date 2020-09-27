package com.minecraftabnormals.environmental.common.entity.goals;

import java.util.Random;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.block.EmptyNestBlock;
import com.minecraftabnormals.environmental.common.entity.DuckEntity;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class DuckLayEggInNestGoal extends MoveToBlockGoal {
	private final DuckEntity duck;

	public DuckLayEggInNestGoal(DuckEntity duckIn, double speedIn) {
		super(duckIn, speedIn, 16);
		this.duck = duckIn;
	}

	public boolean shouldExecute() {
		return !this.duck.isChild() && !this.duck.isChickenJockey() && this.duck.timeUntilNextEgg < 800 && super.shouldExecute();
	}

	protected int getRunDelay(CreatureEntity creatureIn) {
		return 40;
	}

	public void tick() {
		super.tick();
		if (this.getIsAboveDestination() && !this.duck.isChild() && !this.duck.isChickenJockey() && this.duck.timeUntilNextEgg < 800) {
			BlockPos blockpos = this.destinationBlock.up();
			BlockState blockstate = this.duck.world.getBlockState(blockpos);
			Block block = blockstate.getBlock();

			if (block instanceof EmptyNestBlock) {
				this.duck.world.setBlockState(blockpos, ((EmptyNestBlock)block).getNest(EnvironmentalItems.DUCK_EGG.get()).getDefaultState(), 3);
				this.resetBird();
			}
			else if (block instanceof BirdNestBlock && ((BirdNestBlock)block).getEgg() == EnvironmentalItems.DUCK_EGG.get()) {
				int i = blockstate.get(BirdNestBlock.EGGS);
				if (i < 6) {
					this.duck.world.setBlockState(blockpos, blockstate.with(BirdNestBlock.EGGS, Integer.valueOf(i + 1)), 3);
					this.resetBird();
				}
			}
		}
	}

	private void resetBird() {
		Random random = duck.getRNG();
		this.duck.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		this.duck.timeUntilNextEgg = random.nextInt(6000) + 6000;
	}
	
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = this.duck.world.getBlockState(pos.up());
		Block block = blockstate.getBlock();
		
		if (block instanceof EmptyNestBlock || (block instanceof BirdNestBlock && ((BirdNestBlock)block).getEgg() == EnvironmentalItems.DUCK_EGG.get() && blockstate.get(BirdNestBlock.EGGS) < 6)) {
			if (this.duck.timeUntilNextEgg < 800) {
				return true;
			}
		}

		return false;
	}
}