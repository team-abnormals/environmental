package com.minecraftabnormals.environmental.common.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import java.util.Map;
import java.util.function.Supplier;

public class EmptyNestBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	private final Map<Supplier<? extends Item>, Block> NESTS = Maps.newHashMap();

	public EmptyNestBlock(Properties properties) {
		super(properties);
	}

	public void addNest(Supplier<? extends Item> egg, Block nest) {
		NESTS.put(egg, nest);
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (player.mayBuild()) {

			ItemStack itemstack = player.getItemInHand(handIn);
			Item item = itemstack.getItem();

			Block nest = null;

			for (Supplier<? extends Item> supplier : NESTS.keySet()) {
				if (item == supplier.get()) {
					nest = NESTS.get(supplier);
					break;
				}
			}

			if (nest != null && ((BirdNestBlock) nest).getEgg() != Items.AIR) {
				if (!player.abilities.instabuild && !worldIn.isClientSide) {
					itemstack.shrink(1);
				}
				worldIn.setBlock(pos, nest.defaultBlockState(), 3);

				return ActionResultType.sidedSuccess(worldIn.isClientSide);
			}

			return ActionResultType.CONSUME;
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
	}

	public Block getNest(Item item) {
		Block nest = null;

		for (Supplier<? extends Item> supplier : NESTS.keySet()) {
			if (item == supplier.get()) {
				nest = NESTS.get(supplier);
				break;
			}
		}

		return nest;
	}
}