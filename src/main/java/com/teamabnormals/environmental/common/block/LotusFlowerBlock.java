package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

public class LotusFlowerBlock extends BlueprintFlowerBlock {
	private final Supplier<ParticleOptions> particle;

	public LotusFlowerBlock(Supplier<ParticleOptions> particle, Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.particle = particle;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {

		for (int i = 0; i < 3; i++) {
			double offsetX = rand.nextFloat() * 0.6F;
			double offsetZ = rand.nextFloat() * 0.45F;

			double x = pos.getX() + 0.25D + offsetX;
			double y = pos.getY() + 0.25D + (rand.nextFloat() * 0.05F);
			double z = pos.getZ() + 0.25D + offsetZ;

			if (worldIn.isClientSide && worldIn.getGameTime() % 9 == 0)
				worldIn.addParticle(this.particle.get(), x, y, z, 0.03D, 0.0D, 0.03D);
		}
	}
}
