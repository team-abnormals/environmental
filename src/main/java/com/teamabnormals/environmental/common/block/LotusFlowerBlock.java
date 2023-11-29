package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class LotusFlowerBlock extends BlueprintFlowerBlock {
	private final Supplier<ParticleOptions> particle;

	public LotusFlowerBlock(Supplier<ParticleOptions> particle, Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.particle = particle;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
		if (level.isClientSide()) {
			for (int i = 0; i < 3; i++) {
				double x = pos.getX() + 0.25D + (rand.nextFloat() * 0.6F);
				double y = pos.getY() + 0.25D + (rand.nextFloat() * 0.05F);
				double z = pos.getZ() + 0.25D + (rand.nextFloat() * 0.45F);

				if (level.getGameTime() % 9 == 0) {
					level.addParticle(this.particle.get(), x, y, z, 0.03D, 0.0D, 0.03D);
				}
			}
		}
	}
}
