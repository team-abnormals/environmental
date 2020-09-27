package com.minecraftabnormals.environmental.common.entity.goals;

import java.util.Random;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.block.EmptyNestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class ChickenLayEggInNestGoal extends MoveToBlockGoal {
	private final ChickenEntity chicken;

	public ChickenLayEggInNestGoal(ChickenEntity chickenIn, double speedIn) {
		super(chickenIn, speedIn, 16);
		this.chicken = chickenIn;
	}

	public boolean shouldExecute() {
		return !this.chicken.isChild() && !this.chicken.isChickenJockey() && this.chicken.timeUntilNextEgg < 800 && super.shouldExecute();
	}

	protected int getRunDelay(CreatureEntity creatureIn) {
		return 40;
	}

	public void tick() {
		super.tick();
		if (this.getIsAboveDestination() && !this.chicken.isChild() && !this.chicken.isChickenJockey() && this.chicken.timeUntilNextEgg < 800) {
			BlockPos blockpos = this.destinationBlock.up();
			BlockState blockstate = this.chicken.world.getBlockState(blockpos);
			Block block = blockstate.getBlock();

			if (block instanceof EmptyNestBlock) {
				this.chicken.world.setBlockState(blockpos, ((EmptyNestBlock)block).getNest(Items.EGG).getDefaultState(), 3);
				this.resetBird();
			}
			else if (block instanceof BirdNestBlock && ((BirdNestBlock)block).getEgg() == Items.EGG) {
				int i = blockstate.get(BirdNestBlock.EGGS);
				if (i < 6) {
					this.chicken.world.setBlockState(blockpos, blockstate.with(BirdNestBlock.EGGS, Integer.valueOf(i + 1)), 3);
					this.resetBird();
				}
			}
		}
	}

	private void resetBird() {
		Random random = chicken.getRNG();
		this.chicken.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		this.chicken.timeUntilNextEgg = random.nextInt(6000) + 6000;
	}
	
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = this.chicken.world.getBlockState(pos.up());
		Block block = blockstate.getBlock();
		
		if (block instanceof EmptyNestBlock || (block instanceof BirdNestBlock && ((BirdNestBlock)block).getEgg() == Items.EGG && blockstate.get(BirdNestBlock.EGGS) < 6)) {
			if (this.chicken.timeUntilNextEgg < 800) {
				return true;
			}
		}

		return false;
	}
}