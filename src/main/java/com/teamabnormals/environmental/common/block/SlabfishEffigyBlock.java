package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlabfishEffigyBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
	protected static final VoxelShape SLABFISH_SHAPE = Block.box(5.0D, 0.0D, 3.0D, 11.0D, 17.0D, 13.0D);
	protected static final VoxelShape SLABFISH_SHAPE_ROTATED = Block.box(3.0D, 0.0D, 5.0D, 13.0D, 17.0D, 11.0D);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public SlabfishEffigyBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false));
	}

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return null;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
		return state.getValue(POWERED) ? SHAPE : Shapes.or(SHAPE, state.getValue(FACING).getAxis() == Axis.X ? SLABFISH_SHAPE : SLABFISH_SHAPE_ROTATED);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	public void onLightningStrike(BlockState state, Level level, BlockPos pos) {
		if (!state.getValue(POWERED)) {
			level.setBlock(pos, state.setValue(POWERED, true), 3);
			Direction facing = state.getValue(FACING);
			if (!level.isClientSide()) {
				Slabfish slabfish = EnvironmentalEntityTypes.SLABFISH.get().create(level);
				if (slabfish == null) return;
				slabfish.moveTo(pos.getX() + 0.5F, pos.getY() + 0.125F, pos.getZ() + 0.5F, 0.0F, 0.0F);
				slabfish.setVariant(SlabfishHelper.slabfishTypes(slabfish.registryAccess()).getHolderOrThrow(EnvironmentalSlabfishVariants.GOLEM));
				slabfish.lookAt(Anchor.EYES, Vec3.atCenterOf(pos.relative(facing)));
				level.addFreshEntity(slabfish);
			}
		}
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			levelIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelIn));
		}
		return super.updateShape(stateIn, facing, facingState, levelIn, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
	}

}
