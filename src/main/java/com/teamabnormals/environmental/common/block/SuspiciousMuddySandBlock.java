package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SuspiciousMuddySandBlock extends BrushableBlock {
	public static final MapCodec<BrushableBlock> CODEC = simpleCodec(SuspiciousMuddySandBlock::new);

	public SuspiciousMuddySandBlock(Properties properties) {
		super(Blocks.SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, properties);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushableBlockEntity)
			brushableBlockEntity.checkReset();

		if (MuddySandBlock.shouldFall(level, pos) && pos.getY() >= level.getMinBuildHeight()) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
			fallingblockentity.disableDrop();
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(16) == 0 && MuddySandBlock.shouldFall(level, pos)) {
			double d0 = (double) pos.getX() + random.nextDouble();
			double d1 = (double) pos.getY() - 0.05;
			double d2 = (double) pos.getZ() + random.nextDouble();
			level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d0, d1, d2, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public Block getTurnsInto() {
		return EnvironmentalBlocks.MUDDY_SAND.get();
	}

	@Override
	public MapCodec<BrushableBlock> codec() {
		return CODEC;
	}
}
