package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Random;

public class DoubleCattailBlock extends Block implements IGrowable, IWaterLoggable {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty FAKE_WATERLOGGED = BooleanProperty.create("fake_waterlogged");
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape SHAPE_TOP = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);

	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.LARGE_FERN);

	public DoubleCattailBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FAKE_WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP.move(vec3d.x, vec3d.y, vec3d.z) : SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE, HALF, WATERLOGGED, FAKE_WATERLOGGED);
	}

	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block.is(Tags.Blocks.DIRT) || block.is(BlockTags.SAND) || block instanceof FarmlandBlock;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
			return this.isValidGround(worldIn.getBlockState(pos.below()), worldIn, pos);
		} else {
			BlockState blockstate = worldIn.getBlockState(pos.below());
			if (state.getBlock() != this)
				this.isValidGround(worldIn.getBlockState(pos.below()), worldIn, pos);
			return blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos = context.getClickedPos();
		return context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context).setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8).setValue(FAKE_WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8)) : null;
	}

	public static void placeAt(IWorld worldIn, BlockPos pos, int flags) {
		FluidState ifluidstate = worldIn.getFluidState(pos);
		FluidState ifluidstateUp = worldIn.getFluidState(pos.above());
		boolean applyFakeWaterLogging = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8 || ifluidstateUp.is(FluidTags.WATER) && ifluidstateUp.getAmount() == 8;
		worldIn.setBlock(pos, EnvironmentalBlocks.TALL_CATTAIL.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8).setValue(FAKE_WATERLOGGED, applyFakeWaterLogging), flags);
		worldIn.setBlock(pos.above(), EnvironmentalBlocks.TALL_CATTAIL.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, ifluidstateUp.is(FluidTags.WATER) && ifluidstateUp.getAmount() == 8).setValue(FAKE_WATERLOGGED, applyFakeWaterLogging), flags);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		int i = state.getValue(AGE);
		boolean flag = i == 1;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (i > 0) {
			Random rand = new Random();
			int j = 1 + rand.nextInt(3);
			popResource(worldIn, pos, new ItemStack(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), j));
			worldIn.playSound((PlayerEntity) null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlock(pos, state.setValue(AGE, 0), 2);
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
				worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, 0), 2);
			} else {
				worldIn.setBlock(pos.above(), worldIn.getBlockState(pos.above()).setValue(AGE, 0), 2);
			}
			return ActionResultType.SUCCESS;
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		int i = state.getValue(AGE);
		if (i < 1) {
			worldIn.setBlockAndUpdate(pos, state.setValue(AGE, i + 1));
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
				worldIn.setBlockAndUpdate(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, i + 1));
			} else {
				worldIn.setBlockAndUpdate(pos.above(), worldIn.getBlockState(pos.above()).setValue(AGE, i + 1));
			}

		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		int i = state.getValue(AGE);
		int chance = worldIn.getBlockState(pos.below().below()).isFertile(worldIn, pos.below().below()) ? 15 : 17;
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER && i < 1 && worldIn.getBlockState(pos.below().below()).getBlock() == Blocks.FARMLAND && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(chance) == 0)) {
			worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
			if (worldIn.getBlockState(pos.below()).getBlock() == this && worldIn.getBlockState(pos.below()).getValue(AGE) == 0) {
				worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, i + 1), 2);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
			}
		}
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			if (doubleblockhalf == DoubleBlockHalf.UPPER && !stateIn.getValue(FAKE_WATERLOGGED)) {
				stateIn = stateIn.setValue(FAKE_WATERLOGGED, true);
				worldIn.setBlock(currentPos, worldIn.getBlockState(currentPos).setValue(FAKE_WATERLOGGED, true), 2);
			} else if (doubleblockhalf == DoubleBlockHalf.LOWER && !stateIn.getValue(FAKE_WATERLOGGED)) {
				stateIn = stateIn.setValue(FAKE_WATERLOGGED, true);
				worldIn.setBlock(currentPos, worldIn.getBlockState(currentPos).setValue(FAKE_WATERLOGGED, true), 2);
			}
		}
		if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(HALF) != doubleblockhalf) {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isClientSide) {
			if (player.isCreative()) {
				removeBottomHalf(worldIn, pos, state, player);
			} else {
				dropResources(state, worldIn, pos, (TileEntity) null, player, player.getMainHandItem());
			}
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}

	@Override
	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
	}

	protected static void removeBottomHalf(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pos.below();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlock(blockpos, world.getFluidState(blockpos).getAmount() == 8 ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 51);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		} else if (doubleblockhalf == DoubleBlockHalf.LOWER) {
			BlockPos blockpos = pos.above();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER) {
				world.setBlock(blockpos, world.getFluidState(blockpos).getAmount() == 8 ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 51);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		FluidState ifluidstateUp = worldIn.getFluidState(pos.above());
		worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, ifluidstateUp.is(FluidTags.WATER) && ifluidstateUp.getAmount() == 8).setValue(FAKE_WATERLOGGED, worldIn.getFluidState(pos).is(FluidTags.WATER) && worldIn.getFluidState(pos).getAmount() == 8), 3);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		return false;
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return state.getValue(AGE) < 1;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return state.getValue(AGE) < 1;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public long getSeed(BlockState state, BlockPos pos) {
		return MathHelper.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}
}