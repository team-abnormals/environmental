package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class CattailBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.SEA_PICKLE);

	public CattailBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(Blocks.FARMLAND);
	}

	public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
		Random rand = new Random();
		int type = rand.nextInt(3);

		BlockState seeds = EnvironmentalBlocks.CATTAIL_SPROUTS.get().defaultBlockState();
		BlockState cattail = EnvironmentalBlocks.CATTAIL.get().defaultBlockState();

		boolean waterlogged = worldIn.isWaterAt(pos);
		if (type == 0) {
			worldIn.setBlock(pos, seeds.setValue(WATERLOGGED, waterlogged), flags);
		} else if (type == 1) {
			worldIn.setBlock(pos, cattail.setValue(WATERLOGGED, waterlogged), flags);
		} else {
			DoubleCattailBlock.placeAt(worldIn, pos, flags);
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
		return this.defaultBlockState().setValue(WATERLOGGED, flag);
	}

	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
		DoubleCattailBlock doubleplantblock = (DoubleCattailBlock) (EnvironmentalBlocks.TALL_CATTAIL.get());
		FluidState ifluidstateUp = worldIn.getFluidState(pos.above());
		if (doubleplantblock.defaultBlockState().canSurvive(worldIn, pos) && (worldIn.isEmptyBlock(pos.above()) || (ifluidstateUp.is(FluidTags.WATER) && ifluidstateUp.getAmount() == 8))) {
			DoubleCattailBlock.placeAt(worldIn, pos, 2);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		int chance = worldIn.getBlockState(pos.below()).isFertile(worldIn, pos.below()) ? 15 : 17;
		if (worldIn.getRawBrightness(pos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(chance) == 0)) {
			DoubleCattailBlock doubleplantblock = (DoubleCattailBlock) (EnvironmentalBlocks.TALL_CATTAIL.get());
			if (doubleplantblock.defaultBlockState().canSurvive(worldIn, pos) && worldIn.isEmptyBlock(pos.above()) && worldIn.getBlockState(pos.below()).getBlock() == Blocks.FARMLAND) {
				DoubleCattailBlock.placeAt(worldIn, pos, 2);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
			}
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		return false;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return this.mayPlaceOn(world.getBlockState(pos.below()), world, pos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!state.canSurvive(worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (state.getValue(WATERLOGGED)) {
				worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			}
			return super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
}