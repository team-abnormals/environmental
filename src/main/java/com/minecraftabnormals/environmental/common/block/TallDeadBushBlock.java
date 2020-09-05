package com.minecraftabnormals.environmental.common.block;

import javax.annotation.Nullable;

import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

public class TallDeadBushBlock extends DoublePlantBlock implements net.minecraftforge.common.IForgeShearable {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public TallDeadBushBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == Blocks.SAND || block == Blocks.RED_SAND || block == Blocks.TERRACOTTA || block == Blocks.WHITE_TERRACOTTA || block == Blocks.ORANGE_TERRACOTTA || block == Blocks.MAGENTA_TERRACOTTA || block == Blocks.LIGHT_BLUE_TERRACOTTA || block == Blocks.YELLOW_TERRACOTTA || block == Blocks.LIME_TERRACOTTA || block == Blocks.PINK_TERRACOTTA || block == Blocks.GRAY_TERRACOTTA || block == Blocks.LIGHT_GRAY_TERRACOTTA || block == Blocks.CYAN_TERRACOTTA || block == Blocks.PURPLE_TERRACOTTA || block == Blocks.BLUE_TERRACOTTA || block == Blocks.BROWN_TERRACOTTA || block == Blocks.GREEN_TERRACOTTA || block == Blocks.RED_TERRACOTTA || block == Blocks.BLACK_TERRACOTTA || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.DESERT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return state.get(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP.withOffset(vec3d.x, vec3d.y, vec3d.z) : SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (ItemStackUtils.isInGroup(this.asItem(), group)) {
			int targetIndex = ItemStackUtils.findIndexOfItem(Items.LARGE_FERN, items);
			if (targetIndex != -1) {
				items.add(targetIndex + 1, new ItemStack(this));
			} else {
				super.fillItemGroup(group, items);
			}
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote) {
			if (player.isCreative()) {
				breakWithoutDrops(worldIn, pos, state, player);
			} else {
				spawnDrops(state, worldIn, pos, (TileEntity) null, player, player.getHeldItemMainhand());
			}
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	protected static void breakWithoutDrops(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf doubleblockhalf = state.get(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pos.down();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.get(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
				world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			}
		}

	}
}