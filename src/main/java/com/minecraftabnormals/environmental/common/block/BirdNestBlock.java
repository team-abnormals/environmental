package com.minecraftabnormals.environmental.common.block;

import java.util.function.Supplier;

import com.minecraftabnormals.environmental.common.tile.BirdNestTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class BirdNestBlock extends ContainerBlock {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 6);
	private final Supplier<? extends Item> egg;
	private final EmptyNestBlock emptyNest;

	public BirdNestBlock(Supplier<? extends Item> eggIn, EmptyNestBlock emptyNestIn, Properties properties) {
		super(properties);
		this.egg = eggIn;
		this.emptyNest = emptyNestIn;
		this.emptyNest.addNest(this.egg, this);

		this.setDefaultState(this.stateContainer.getBaseState().with(EGGS, Integer.valueOf(1)));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (player.isAllowEdit()) {
			int i = state.get(EGGS);
			ItemStack itemstack = player.getHeldItem(handIn);
			if (itemstack.getItem() == this.egg.get()) {
				if (i < 6) {
					if (!player.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}
					worldIn.setBlockState(pos, state.with(EGGS, Integer.valueOf(i + 1)), 3);
				}
			}
			else {
				spawnAsEntity(worldIn, pos, new ItemStack(this.egg.get()));

				if (i > 1)
					worldIn.setBlockState(pos, state.with(EGGS, Integer.valueOf(i - 1)), 3);
				else
					worldIn.setBlockState(pos, this.getEmptyNest().getDefaultState(), 3);
			}
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
		else {
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
	}

	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(this.getEgg());
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos){
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new BirdNestTileEntity();
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(EGGS);
	}

	public Item getEgg() {
		return this.egg.get();
	}

	public EmptyNestBlock getEmptyNest() {
		return this.emptyNest;
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return blockState.get(EGGS);
	}
}