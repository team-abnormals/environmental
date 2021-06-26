package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;
import java.util.Random;

public class HangingWisteriaLeavesBlock extends Block implements IForgeShearable {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	protected static final VoxelShape WISTERIA_VINE_TOP = Block.box(1, 0, 1, 15, 16, 15);
	protected static final VoxelShape WISTERIA_VINE_BOTTOM = Block.box(4, 0, 4, 12, 16, 12);
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.VINE);

	public HangingWisteriaLeavesBlock(Block.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(HALF, DoubleBlockHalf.UPPER));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return WISTERIA_VINE_TOP;
		if (state.getValue(HALF) == DoubleBlockHalf.LOWER) return WISTERIA_VINE_BOTTOM;
		else return VoxelShapes.empty();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
		if (state == defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)) {
			if (world.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState()) {
				world.removeBlock(pos, false);
			}
		} else if (state == defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER)) {
			if (world.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState()) {
				world.removeBlock(pos, false);
			}
		}
	}

	public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.isRainingAt(pos.above())) {
			if (rand.nextInt(15) == 1) {
				BlockPos blockpos = pos.below();
				BlockState blockstate = worldIn.getBlockState(blockpos);
				if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP)) {
					double d0 = (double) ((float) pos.getX() + rand.nextFloat());
					double d1 = (double) pos.getY() - 0.05D;
					double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
					worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return true;
	}


	public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return false;
	}

	public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}

	protected boolean isStateValid(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.above()).getBlock();
		return block == defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).getBlock() || block.is(BlockTags.LEAVES) || block.is(BlockTags.LOGS);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if (isStateValid(world, pos)) {
			if (world.getBlockState(pos.above()) == defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)) {
				return defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
			} else if (world.getBlockState(pos.above()) == defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER)) {
				return null;
			} else return defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER);
		}
		return null;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}