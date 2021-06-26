package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.environmental.common.tile.BirdNestTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class BirdNestBlock extends ContainerBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 6);
	private final Supplier<? extends Item> egg;
	private final EmptyNestBlock emptyNest;

	public BirdNestBlock(Supplier<? extends Item> eggIn, EmptyNestBlock emptyNestIn, Properties properties) {
		super(properties);
		this.egg = eggIn;
		this.emptyNest = emptyNestIn;
		this.emptyNest.addNest(this.egg, this);

		this.registerDefaultState(this.stateDefinition.any().setValue(EGGS, 1));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (player.mayBuild()) {
			int i = state.getValue(EGGS);
			ItemStack itemstack = player.getItemInHand(handIn);
			if (this.egg.get() != Items.AIR && itemstack.getItem() == this.egg.get()) {
				if (i < 6) {
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
					}
					worldIn.setBlock(pos, state.setValue(EGGS, i + 1), 3);
				}
			} else {
				popResource(worldIn, pos, new ItemStack(this.egg.get()));

				if (i > 1)
					worldIn.setBlock(pos, state.setValue(EGGS, i - 1), 3);
				else
					worldIn.setBlock(pos, this.getEmptyNest().defaultBlockState(), 3);
			}
			return ActionResultType.sidedSuccess(worldIn.isClientSide);
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}

	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(this.getEgg());
	}

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.playerWillDestroy(worldIn, pos, state, player);
		if (!worldIn.isClientSide && !player.isCreative() && this.getEgg() != null && state.getValue(EGGS) > 0)
			popResource(worldIn, pos, new ItemStack(this.getEgg(), state.getValue(EGGS)));
	}

	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new BirdNestTileEntity();
	}

	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(EGGS);
	}

	public Item getEgg() {
		return this.egg.get();
	}

	public EmptyNestBlock getEmptyNest() {
		return this.emptyNest;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(EGGS);
	}
}