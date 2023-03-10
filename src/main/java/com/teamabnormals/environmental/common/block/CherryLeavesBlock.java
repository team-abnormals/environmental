package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.wood.BlueprintLeavesBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CherryLeavesBlock extends BlueprintLeavesBlock {

	public CherryLeavesBlock(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		super.animateTick(stateIn, worldIn, pos, rand);

		int color = worldIn.getBiome(pos).value().getFoliageColor();

		double d0 = (color >> 16 & 255) / 255.0F;
		double d1 = (color >> 8 & 255) / 255.0F;
		double d2 = (color & 255) / 255.0F;

		if (rand.nextInt(50) == 0) {
			BlockPos blockpos = pos.below();
			if (worldIn.isEmptyBlock(blockpos)) {
				double d3 = (float) pos.getX() + rand.nextFloat();
				double d4 = (double) pos.getY() - 0.05D;
				double d6 = (float) pos.getZ() + rand.nextFloat();
				worldIn.addParticle(EnvironmentalParticleTypes.CHERRY_BLOSSOM.get(), d3, d4, d6, d0, d1, d2);
			}
		}
	}
}
