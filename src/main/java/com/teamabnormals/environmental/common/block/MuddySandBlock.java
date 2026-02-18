package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MuddySandBlock extends FallingBlock {
	public static final MapCodec<MuddySandBlock> CODEC = simpleCodec(MuddySandBlock::new);
	private static final Direction[] EXTRA_DIRECTIONS_TO_CHECK = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

	public MuddySandBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (shouldFall(level, pos) && pos.getY() >= level.getMinBuildHeight())
			this.falling(FallingBlockEntity.fall(level, pos, state));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(16) == 0 && shouldFall(level, pos))
			ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
	}

	@Override
	protected int getDelayAfterPlace() {
		return 20;
	}

	public static boolean shouldFall(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos.below());
		if (state.liquid()) return true;
		if (state.isAir() || state.is(BlockTags.FIRE) || state.canBeReplaced()) {
			for (Direction horizontal : EXTRA_DIRECTIONS_TO_CHECK) {
				if (level.getBlockState(pos.relative(horizontal)).getFluidState().is(FluidTags.WATER))
					return true;
			}
		}
		return false;
	}

	@Override
	protected MapCodec<? extends FallingBlock> codec() {
		return CODEC;
	}
}
