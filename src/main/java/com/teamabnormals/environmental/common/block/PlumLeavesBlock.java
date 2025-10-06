package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PlumLeavesBlock extends LeavesBlock {

	public PlumLeavesBlock(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		double d0 = random.nextGaussian() * 0.02D;
		double d1 = random.nextGaussian() * 0.02D;
		double d2 = random.nextGaussian() * 0.02D;

		float rate = level.isThundering() ? 0.2F : level.isRaining() ? 0.05F : 0.025F;

		if (random.nextFloat() < rate) {
			BlockPos blockpos = pos.below();
			if (level.isEmptyBlock(blockpos)) {
				double d3 = (float) pos.getX() + random.nextFloat();
				double d4 = (double) pos.getY() - 0.05D;
				double d6 = (float) pos.getZ() + random.nextFloat();
				level.addParticle(state.is(EnvironmentalBlocks.CHEERFUL_PLUM_LEAVES.get()) ? EnvironmentalParticleTypes.CHEERFUL_PLUM_BLOSSOM.get() : state.is(EnvironmentalBlocks.MOODY_PLUM_LEAVES.get()) ? EnvironmentalParticleTypes.MOODY_PLUM_BLOSSOM.get() : EnvironmentalParticleTypes.PLUM_BLOSSOM.get(), d3, d4, d6, d0, d1, d2);
			}
		}
	}
}
