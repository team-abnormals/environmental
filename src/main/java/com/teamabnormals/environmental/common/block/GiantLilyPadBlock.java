package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class GiantLilyPadBlock extends BushBlock implements IPlantable {
	protected static final VoxelShape GIANT_LILY_PAD_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
	public static final EnumProperty<LilyPadPosition> POSITION = EnumProperty.create("position", LilyPadPosition.class);
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> EnvironmentalBlocks.LARGE_LILY_PAD.get().asItem());

	public GiantLilyPadBlock(BlockBehaviour.Properties builder) {
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POSITION);
	}

	public static void placeAt(Level world, BlockPos pos, BlockState state, int flags) {
		world.setBlock(pos, state.setValue(POSITION, LilyPadPosition.CENTER), flags);

		world.setBlock(pos.relative(Direction.NORTH), state.setValue(POSITION, LilyPadPosition.NORTH), flags);
		world.setBlock(pos.relative(Direction.EAST), state.setValue(POSITION, LilyPadPosition.EAST), flags);
		world.setBlock(pos.relative(Direction.SOUTH), state.setValue(POSITION, LilyPadPosition.SOUTH), flags);
		world.setBlock(pos.relative(Direction.WEST), state.setValue(POSITION, LilyPadPosition.WEST), flags);

		world.setBlock(pos.relative(Direction.NORTH).relative(Direction.EAST), state.setValue(POSITION, LilyPadPosition.NORTHEAST), flags);
		world.setBlock(pos.relative(Direction.SOUTH).relative(Direction.EAST), state.setValue(POSITION, LilyPadPosition.SOUTHEAST), flags);
		world.setBlock(pos.relative(Direction.SOUTH).relative(Direction.WEST), state.setValue(POSITION, LilyPadPosition.SOUTHWEST), flags);
		world.setBlock(pos.relative(Direction.NORTH).relative(Direction.WEST), state.setValue(POSITION, LilyPadPosition.NORTHWEST), flags);
	}

	public static boolean checkPositions(Level world, BlockPos pos, BlockState state) {
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.NORTH), world, pos.relative(Direction.NORTH)))
			return false;
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.EAST), world, pos.relative(Direction.EAST)))
			return false;
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.SOUTH), world, pos.relative(Direction.SOUTH)))
			return false;
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.WEST), world, pos.relative(Direction.WEST)))
			return false;

		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.NORTHEAST), world, posToBlockPos(LilyPadPosition.NORTHEAST, pos, false)))
			return false;
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.SOUTHEAST), world, pos.relative(Direction.SOUTH).relative(Direction.EAST)))
			return false;
		if (!isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.SOUTHWEST), world, pos.relative(Direction.SOUTH).relative(Direction.WEST)))
			return false;
		return isValidPosAndAir(state.setValue(POSITION, LilyPadPosition.NORTHWEST), world, pos.relative(Direction.NORTH).relative(Direction.WEST));
	}

	public static boolean isValidPosAndAir(BlockState state, Level world, BlockPos pos) {
		return state.canSurvive(world, pos) && world.getBlockState(pos).isAir();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return GIANT_LILY_PAD_AABB;
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		super.fallOn(level, state, pos, entity, fallDistance * 0.25F);
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		if (entityIn.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(worldIn, entityIn);
		} else {
			this.bounce(entityIn);
		}
	}

	private void bounce(Entity entity) {
		Vec3 vector3d = entity.getDeltaMovement();
		if (vector3d.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.5D : 1.2D;
			entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
		}
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		FluidState fluidstate = worldIn.getFluidState(pos);
		FluidState fluidstate1 = worldIn.getFluidState(pos.above());
		return (fluidstate.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getType() == Fluids.EMPTY;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		if (state.getBlock() == this) {
			return worldIn.getBlockState(blockpos).canSustainPlant(worldIn, blockpos, Direction.UP, this);
		}
		return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return (!stateIn.canSurvive(worldIn, currentPos) || !this.isConnected(stateIn, worldIn, currentPos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean isConnected(BlockState state, BlockGetter worldIn, BlockPos pos) {
		LilyPadPosition position = state.getValue(POSITION);
		pos = posToBlockPos(position, pos, true);
		for (LilyPadPosition newPosition : LilyPadPosition.values()) {
			if (worldIn.getBlockState(posToBlockPos(newPosition, pos, false)).is(this) && worldIn.getBlockState(posToBlockPos(newPosition, pos, false)).getValue(POSITION) == newPosition) {
			} else
				return false;
		}
		return true;
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		if (!worldIn.isClientSide) {
			if (player.isCreative()) {
				removeEachBlock(worldIn, pos, state, player);
			} else {
				dropResources(state, worldIn, pos, null, player, player.getMainHandItem());
			}
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}

	private static void removeEachBlock(Level world, BlockPos pos, BlockState state, Player player) {
		LilyPadPosition position = state.getValue(POSITION);
		pos = posToBlockPos(position, pos, true);
		for (LilyPadPosition lilyPadPos : LilyPadPosition.values()) {
			removeBlock(lilyPadPos, world, pos, state, player);
		}
	}

	private static void removeBlock(LilyPadPosition position, Level world, BlockPos pos, BlockState state, Player player) {
		BlockPos blockpos = posToBlockPos(position, pos, false);
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(POSITION) == position) {
			world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 51);
			world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
		}
	}

	@Override
	public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
		super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
	}

	public static BlockPos posToBlockPos(LilyPadPosition position, BlockPos pos, boolean revert) {
		if (!revert) {
			switch (position) {

				case NORTH:
					return pos.relative(Direction.NORTH);
				case EAST:
					return pos.relative(Direction.EAST);
				case SOUTH:
					return pos.relative(Direction.SOUTH);
				case WEST:
					return pos.relative(Direction.WEST);

				case NORTHEAST:
					return pos.relative(Direction.NORTH).relative(Direction.EAST);
				case NORTHWEST:
					return pos.relative(Direction.NORTH).relative(Direction.WEST);
				case SOUTHEAST:
					return pos.relative(Direction.SOUTH).relative(Direction.EAST);
				case SOUTHWEST:
					return pos.relative(Direction.SOUTH).relative(Direction.WEST);
				case CENTER:
				default:
					return pos;
			}
		} else {
			switch (position) {

				case NORTH:
					return pos.relative(Direction.SOUTH);
				case EAST:
					return pos.relative(Direction.WEST);
				case SOUTH:
					return pos.relative(Direction.NORTH);
				case WEST:
					return pos.relative(Direction.EAST);

				case NORTHEAST:
					return pos.relative(Direction.SOUTH).relative(Direction.WEST);
				case NORTHWEST:
					return pos.relative(Direction.SOUTH).relative(Direction.EAST);
				case SOUTHEAST:
					return pos.relative(Direction.NORTH).relative(Direction.WEST);
				case SOUTHWEST:
					return pos.relative(Direction.NORTH).relative(Direction.EAST);
				case CENTER:
				default:
					return pos;
			}
		}
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.WATER;
	}

	public enum LilyPadPosition implements StringRepresentable {
		CENTER("center"),
		NORTH("north"),
		NORTHEAST("northeast"),
		EAST("east"),
		SOUTHEAST("southeast"),
		SOUTH("south"),
		SOUTHWEST("southwest"),
		WEST("west"),
		NORTHWEST("northwest");

		private final String heightName;

		LilyPadPosition(String nameIn) {
			this.heightName = nameIn;
		}

		public String toString() {
			return this.getSerializedName();
		}

		public String getSerializedName() {
			return this.heightName;
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}