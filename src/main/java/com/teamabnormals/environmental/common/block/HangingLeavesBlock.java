package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.IShearable;

public class HangingLeavesBlock extends Block implements IShearable {
	protected static final VoxelShape SHAPE = Block.box(2.0, 7.0, 2.0, 14.0, 16.0, 14.0);

	public HangingLeavesBlock(Block.Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		return direction == Direction.UP && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return Block.isFaceFull(level.getBlockState(pos.above()).getCollisionShape(level, pos.above()), Direction.DOWN) && !level.isWaterAt(pos);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (worldIn.isRainingAt(pos.above())) {
			if (rand.nextInt(15) == 1) {
				BlockPos blockpos = pos.below();
				BlockState blockstate = worldIn.getBlockState(blockpos);
				if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP)) {
					double d0 = (float) pos.getX() + rand.nextFloat();
					double d1 = (double) pos.getY() - 0.05D;
					double d2 = (float) pos.getZ() + rand.nextFloat();
					worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}