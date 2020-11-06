package com.minecraftabnormals.environmental.common.entity.goals;

import java.util.Random;

import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.block.EmptyNestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class LayEggInNestGoal extends MoveToBlockGoal {
	private final AnimalEntity bird;
	private final IEggLayingEntity eggLayer;
	private int eggCounter;

	public LayEggInNestGoal(AnimalEntity birdIn, double speedIn) {
		super(birdIn, speedIn, 16);
		this.bird = birdIn;
		this.eggLayer = (IEggLayingEntity) birdIn;
	}

	public boolean shouldExecute() {
		return !this.bird.isChild() && !this.eggLayer.isBirdJockey() && this.eggLayer.getEggTimer() < 800 && super.shouldExecute();
	}

	protected int getRunDelay(CreatureEntity creatureIn) {
		return 40;
	}

	public void startExecuting() {
		this.eggCounter = 30;
	}

	public void tick() {
		super.tick();
		if (this.getIsAboveDestination() && !this.bird.isChild() && !this.eggLayer.isBirdJockey() && this.eggLayer.getEggTimer() < 800) {
			if (--this.eggCounter <= 0) {
				BlockPos blockpos = this.destinationBlock.up();
				BlockState blockstate = this.bird.world.getBlockState(blockpos);
				Block block = blockstate.getBlock();

				if (block instanceof EmptyNestBlock) {
					this.bird.world.setBlockState(blockpos, ((EmptyNestBlock) block).getNest(this.eggLayer.getEggItem()).getDefaultState(), 3);
					this.resetBird();
				} else if (block instanceof BirdNestBlock && ((BirdNestBlock) block).getEgg() == this.eggLayer.getEggItem()) {
					int i = blockstate.get(BirdNestBlock.EGGS);
					if (i < 6) {
						this.bird.world.setBlockState(blockpos, blockstate.with(BirdNestBlock.EGGS, Integer.valueOf(i + 1)), 3);
						this.resetBird();
					}
				}
			}
		}
	}

	private void resetBird() {
		Random random = bird.getRNG();
		this.bird.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		this.eggLayer.setEggTimer(this.eggLayer.getNextEggTime(random));
	}

	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = this.bird.world.getBlockState(pos.up());
		Block block = blockstate.getBlock();

		if (block instanceof EmptyNestBlock || (block instanceof BirdNestBlock && ((BirdNestBlock) block).getEgg() == this.eggLayer.getEggItem() && blockstate.get(BirdNestBlock.EGGS) < 6)) {
			if (this.eggLayer.getEggTimer() < 800) {
				return true;
			}
		}

		return false;
	}
}