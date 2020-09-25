package com.minecraftabnormals.environmental.common.block;

import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;
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
    protected static final VoxelShape DUCKWEED_AABB = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public DuckweedBlock(Block.Properties builder) {
        super(builder);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return DUCKWEED_AABB;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        FluidState ifluidstate = worldIn.getFluidState(pos);
        return ifluidstate.getFluid() == Fluids.WATER;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.WATER;
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.up()).isAir();
    }

    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
        label:
        for (int x = 0; x < 64; ++x) {
            BlockPos newBlockPos = blockPos;
            for (int y = 0; y < x / 16; ++y) {
                newBlockPos = newBlockPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (state.isValidPosition(world, newBlockPos) && world.isAirBlock(newBlockPos)) {
                    world.setBlockState(newBlockPos, state);
                    break label;
                }
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (ItemStackUtils.isInGroup(this.asItem(), group)) {
            int targetIndex = ItemStackUtils.findIndexOfItem(Items.LILY_PAD, items);
            if (targetIndex != -1) {
                items.add(targetIndex + 1, new ItemStack(this));
            } else {
                super.fillItemGroup(group, items);
            }
        }
    }
}
