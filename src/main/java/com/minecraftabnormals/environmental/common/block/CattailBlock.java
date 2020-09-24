package com.minecraftabnormals.environmental.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

public class CattailBlock extends BushBlock implements IWaterLoggable, IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CattailBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, true));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block.isIn(Tags.Blocks.DIRT) || block.isIn(BlockTags.SAND) || block instanceof FarmlandBlock;
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        Random rand = new Random();
        int type = rand.nextInt(3);

        BlockState seeds = EnvironmentalBlocks.CATTAIL_SPROUTS.get().getDefaultState();
        BlockState cattail = EnvironmentalBlocks.CATTAIL.get().getDefaultState();

        boolean waterlogged = worldIn.hasWater(pos);
        if (type == 0) {
            worldIn.setBlockState(pos, seeds.with(WATERLOGGED, waterlogged), flags);
        } else if (type == 1) {
            worldIn.setBlockState(pos, cattail.with(WATERLOGGED, waterlogged), flags);
        } else {
            DoubleCattailBlock.placeAt(worldIn, pos, flags);
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (ItemStackUtils.isInGroup(this.asItem(), group)) {
            int targetIndex = ItemStackUtils.findIndexOfItem(Items.SEA_PICKLE, items);
            if (targetIndex != -1) {
                items.add(targetIndex + 1, new ItemStack(this));
            } else {
                super.fillItemGroup(group, items);
            }
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
        return this.getDefaultState().with(WATERLOGGED, flag);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        DoubleCattailBlock doubleplantblock = (DoubleCattailBlock) (EnvironmentalBlocks.TALL_CATTAIL.get());
        FluidState ifluidstateUp = worldIn.getFluidState(pos.up());
        if (doubleplantblock.getDefaultState().isValidPosition(worldIn, pos) && (worldIn.isAirBlock(pos.up()) || (Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8)))) {
            DoubleCattailBlock.placeAt(worldIn, pos, 2);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        int chance = worldIn.getBlockState(pos.down()).isFertile(worldIn, pos.down()) ? 15 : 17;
        if (worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(chance) == 0)) {
            DoubleCattailBlock doubleplantblock = (DoubleCattailBlock) (EnvironmentalBlocks.TALL_CATTAIL.get());
            if (doubleplantblock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND) {
                DoubleCattailBlock.placeAt(worldIn, pos, 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return this.isValidGround(world.getBlockState(pos.down()), world, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!state.isValidPosition(worldIn, currentPos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (state.get(WATERLOGGED)) {
                worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }
            return super.updatePostPlacement(state, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }
}