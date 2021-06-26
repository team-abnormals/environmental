package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class DuckweedBlock extends BushBlock implements IGrowable {
	protected static final VoxelShape DUCKWEED_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.LILY_PAD);

	public DuckweedBlock(Block.Properties builder) {
		super(builder);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return DUCKWEED_AABB;
	}

	@Override
	public boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
		FluidState ifluidstate = worldIn.getFluidState(pos);
		return ifluidstate.getType() == Fluids.WATER;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.WATER;
	}

	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return worldIn.getBlockState(pos.above()).isAir();
	}

	public boolean isBonemealSuccess(World world, Random random, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	public void performBonemeal(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
		label:
		for (int x = 0; x < 64; ++x) {
			BlockPos newBlockPos = blockPos;
			for (int y = 0; y < x / 16; ++y) {
				newBlockPos = newBlockPos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
				if (state.canSurvive(world, newBlockPos) && world.isEmptyBlock(newBlockPos)) {
					world.setBlockAndUpdate(newBlockPos, state);
					break label;
				}
			}
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}
