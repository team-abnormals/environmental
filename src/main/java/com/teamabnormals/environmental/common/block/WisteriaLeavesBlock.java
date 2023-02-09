package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

public class WisteriaLeavesBlock extends Block implements SimpleWaterloggedBlock, IForgeShearable {
	public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 8);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.DARK_OAK_LEAVES);

	public WisteriaLeavesBlock(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 8).setValue(PERSISTENT, false).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(DISTANCE) == 8 && !state.getValue(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 8) {
			dropResources(state, level, pos);
			level.removeBlock(pos, false);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlock(pos, updateDistance(state, level, pos), 3);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		int i = getDistanceAt(facingState) + 1;
		if (i != 1 || state.getValue(DISTANCE) != i) {
			level.scheduleTick(pos, this, 1);
		}

		return state;
	}

	public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int i = 8;
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			mutablePos.setWithOffset(pos, direction);
			i = Math.min(i, getDistanceAt(level.getBlockState(mutablePos)) + 1);
			if (i == 1) {
				break;
			}
		}

		return state.setValue(DISTANCE, i);
	}

	private static int getDistanceAt(BlockState state) {
		return state.is(BlockTags.LOGS) ? 0 : state.getBlock() instanceof WisteriaLeavesBlock ? state.getValue(DISTANCE) : state.getBlock() instanceof LeavesBlock ? state.getValue(LeavesBlock.DISTANCE) : 8;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.isRainingAt(pos.above())) {
			if (random.nextInt(15) == 1) {
				BlockPos belowPos = pos.below();
				BlockState belowState = level.getBlockState(belowPos);
				if (!belowState.canOcclude() || !belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
					double d0 = (double) pos.getX() + random.nextDouble();
					double d1 = (double) pos.getY() - 0.05D;
					double d2 = (double) pos.getZ() + random.nextDouble();
					level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockState state = this.defaultBlockState().setValue(PERSISTENT, true).setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
		return updateDistance(state, context.getLevel(), context.getClickedPos());
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}