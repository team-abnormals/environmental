package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.environmental.common.inventory.container.SawmillContainer;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SawmillBlock extends Block {
	private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.sawmill");
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final VoxelShape[] SHAPES = new VoxelShape[]{
			VoxelShapes.or(box(0.0D, 0.0D, 9.0D, 16.0D, 16.0D, 16.0D), box(7.0D, 0.0D, 0.0D, 9.0D, 2.0D, 9.0D)),
			VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 7.0D), box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 16.0D)),
			VoxelShapes.or(box(9.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), box(0.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D)),
			VoxelShapes.or(box(0.0D, 0.0D, 0.0D, 7.0D, 16.0D, 16.0D), box(7.0D, 0.0D, 7.0D, 16.0D, 2.0D, 9.0D))
	};

	public SawmillBlock(AbstractBlock.Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(worldIn, pos));
			player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
			return ActionResultType.CONSUME;
		}
	}

	@Nullable
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> {
			return new SawmillContainer(p_220283_2_, p_220283_3_, IWorldPosCallable.create(worldIn, pos));
		}, CONTAINER_NAME);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(FACING).get3DDataValue() - 2];
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
}