package com.minecraftabnormals.environmental.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LargeLilyPadBlock extends BushBlock implements IGrowable {
	protected static final VoxelShape LARGE_LILY_PAD_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
	public static final EnumProperty<LilyPadPosition> POSITION = EnumProperty.create("position", LilyPadPosition.class);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public LargeLilyPadBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
//		super.onEntityCollision(state, worldIn, pos, entityIn);
//		if (entityIn.isSuppressingBounce()) {
//			super.onLanded(worldIn, entityIn);
//		} else {
//			this.bounce(entityIn);
//		}
//	}
//
//	public void bounce(Entity entity) {
//		Vector3d vector3d = entity.getMotion();
//		if (vector3d.y < 0.0D) {
//			double d0 = entity instanceof LivingEntity ? 1.5D : 1.2D;
//			entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
//		}
//	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(POSITION);
	}

	public static void placeAt(World world, BlockPos pos, BlockState state) {
		if (checkPositions(world, pos, state)) {
			world.setBlockState(pos, state.with(POSITION, LilyPadPosition.CENTER));

			world.setBlockState(pos.offset(Direction.NORTH), state.with(POSITION, LilyPadPosition.NORTH));
			world.setBlockState(pos.offset(Direction.EAST), state.with(POSITION, LilyPadPosition.EAST));
			world.setBlockState(pos.offset(Direction.SOUTH), state.with(POSITION, LilyPadPosition.SOUTH));
			world.setBlockState(pos.offset(Direction.WEST), state.with(POSITION, LilyPadPosition.WEST));

			world.setBlockState(pos.offset(Direction.NORTH).offset(Direction.EAST), state.with(POSITION, LilyPadPosition.NORTHEAST));
			world.setBlockState(pos.offset(Direction.SOUTH).offset(Direction.EAST), state.with(POSITION, LilyPadPosition.SOUTHEAST));
			world.setBlockState(pos.offset(Direction.SOUTH).offset(Direction.WEST), state.with(POSITION, LilyPadPosition.SOUTHWEST));
			world.setBlockState(pos.offset(Direction.NORTH).offset(Direction.WEST), state.with(POSITION, LilyPadPosition.NORTHWEST));
		}
	}

	public static boolean checkPositions(World world, BlockPos pos, BlockState state) {
		if (!state.with(POSITION, LilyPadPosition.CENTER).isValidPosition(world, pos))
			return false;

		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.NORTH), world, pos.offset(Direction.NORTH)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.EAST), world, pos.offset(Direction.EAST)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.SOUTH), world, pos.offset(Direction.SOUTH)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.WEST), world, pos.offset(Direction.WEST)))
			return false;

		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.NORTHEAST), world, posToBlockPos(LilyPadPosition.NORTHEAST, pos, false)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.SOUTHEAST), world, pos.offset(Direction.SOUTH).offset(Direction.EAST)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.SOUTHWEST), world, pos.offset(Direction.SOUTH).offset(Direction.WEST)))
			return false;
		if (!isValidPosAndAir(state.with(POSITION, LilyPadPosition.NORTHWEST), world, pos.offset(Direction.NORTH).offset(Direction.WEST)))
			return false;

		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isValidPosAndAir(BlockState state, World world, BlockPos pos) {
		if (state.isIn(EnvironmentalBlocks.GIANT_LILY_PAD.get())) {
			if (state.isValidPosition(world, pos) && (world.getBlockState(pos).isIn(EnvironmentalBlocks.LARGE_LILY_PAD.get()))) return true;
		} else {
			if (state.isValidPosition(world, pos) && world.getBlockState(pos).isAir()) return true;
		}
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return LARGE_LILY_PAD_AABB;
	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.25F);
	}

	@Override
	public void onLanded(IBlockReader worldIn, Entity entityIn) {
		if (entityIn.isSuppressingBounce()) {
			super.onLanded(worldIn, entityIn);
		} else {
			this.bounce(entityIn);
		}

	}

	private void bounce(Entity entity) {
		Vector3d vector3d = entity.getMotion();
		if (vector3d.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.5D : 1.2D;
			entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
		}
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		FluidState fluidstate = worldIn.getFluidState(pos);
		FluidState fluidstate1 = worldIn.getFluidState(pos.up());
		return (fluidstate.getFluid() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getFluid() == Fluids.EMPTY;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		if (state.getBlock() == this) {
			return worldIn.getBlockState(blockpos).canSustainPlant(worldIn, blockpos, Direction.UP, this);
		}
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos) && this.isConnected(state, worldIn, pos);
	}
	
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	public boolean isConnected(BlockState state, IBlockReader worldIn, BlockPos pos) {
		LilyPadPosition position = state.get(POSITION);
		pos = posToBlockPos(position, pos, true);
		for(LilyPadPosition newPosition : LilyPadPosition.values()) {
			if(worldIn.getBlockState(posToBlockPos(newPosition, pos, false)).isIn(this.getBlock()) && worldIn.getBlockState(posToBlockPos(newPosition, pos, false)).get(POSITION) == newPosition) {
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(POSITION) == LilyPadPosition.CENTER;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return state.get(POSITION) == LilyPadPosition.CENTER;
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote) {
			if (player.isCreative()) {
				func_241471_b_(worldIn, pos, state, player);
			} else {
	            spawnDrops(state, worldIn, pos, (TileEntity)null, player, player.getHeldItemMainhand());
			}
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	protected static void func_241471_b_(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		LilyPadPosition position = state.get(POSITION);
		pos = posToBlockPos(position, pos, true);

		removeBlock(LilyPadPosition.CENTER, world, pos, state, player);
		removeBlock(LilyPadPosition.NORTH, world, pos, state, player);
		removeBlock(LilyPadPosition.EAST, world, pos, state, player);
		removeBlock(LilyPadPosition.SOUTH, world, pos, state, player);
		removeBlock(LilyPadPosition.WEST, world, pos, state, player);
		removeBlock(LilyPadPosition.NORTHEAST, world, pos, state, player);
		removeBlock(LilyPadPosition.NORTHWEST, world, pos, state, player);
		removeBlock(LilyPadPosition.SOUTHEAST, world, pos, state, player);
		removeBlock(LilyPadPosition.SOUTHWEST, world, pos, state, player);
	}
	
	private static void removeBlock(LilyPadPosition position, World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockPos blockpos = posToBlockPos(position, pos, false);
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.getBlock() == state.getBlock() && blockstate.get(POSITION) == position) {
			world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
			world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
		}
	}
	
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}
	
	public static BlockPos posToBlockPos(LilyPadPosition position, BlockPos pos, boolean revert) {
		if (!revert) {
			switch(position) {
			
			case NORTH: return pos.offset(Direction.NORTH);
			case EAST: return pos.offset(Direction.EAST);
			case SOUTH: return pos.offset(Direction.SOUTH);
			case WEST: return pos.offset(Direction.WEST);

			case NORTHEAST: return pos.offset(Direction.NORTH).offset(Direction.EAST);
			case NORTHWEST: return pos.offset(Direction.NORTH).offset(Direction.WEST);
			case SOUTHEAST: return pos.offset(Direction.SOUTH).offset(Direction.EAST);
			case SOUTHWEST: return pos.offset(Direction.SOUTH).offset(Direction.WEST);
			case CENTER: default: return pos;
			}
		} else {
			switch(position) {
			
			case NORTH: return pos.offset(Direction.SOUTH);
			case EAST: return pos.offset(Direction.WEST);
			case SOUTH: return pos.offset(Direction.NORTH);
			case WEST: return pos.offset(Direction.EAST);

			case NORTHEAST: return pos.offset(Direction.SOUTH).offset(Direction.WEST);
			case NORTHWEST: return pos.offset(Direction.SOUTH).offset(Direction.EAST);
			case SOUTHEAST: return pos.offset(Direction.NORTH).offset(Direction.WEST);
			case SOUTHWEST: return pos.offset(Direction.NORTH).offset(Direction.EAST);
			case CENTER: default: return pos;
			}
		}
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if (rand.nextInt(4) == 0 && checkPositions(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().getDefaultState()))
			placeAt(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().getDefaultState());
	}
}