package com.minecraftabnormals.environmental.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.Tags;

public class DoubleRiceBlock extends Block implements IGrowable, IWaterLoggable, IPlantable {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty FAKE_WATERLOGGED = BooleanProperty.create("fake_waterlogged");
    public static final IntegerProperty AGE = IntegerProperty.create("age", 6, 7);

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);

    public DoubleRiceBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, 6).with(FAKE_WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP : SHAPE;
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(EnvironmentalItems.RICE.get());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF, WATERLOGGED, FAKE_WATERLOGGED);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        if (worldIn.getFluidState(pos).getLevel() == 8 && (block.isIn(Tags.Blocks.DIRT) || block.isIn(BlockTags.SAND)))
            return true;
        else if (block instanceof FarmlandBlock) return true;
        return false;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) {
            return this.isValidGround(worldIn.getBlockState(pos.down()), worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this)
                this.isValidGround(worldIn.getBlockState(pos.down()), worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.getBlock() == this && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos blockpos = context.getPos();
        return context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) ? super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8)).with(FAKE_WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8)) : null;
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags, int age) {
        FluidState ifluidstate = worldIn.getFluidState(pos);
        FluidState ifluidstateUp = worldIn.getFluidState(pos.up());
        boolean applyFakeWaterLogging = Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8) || Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8) ? true : false;
        worldIn.setBlockState(pos, this.getDefaultState().with(AGE, age).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8)).with(FAKE_WATERLOGGED, applyFakeWaterLogging), flags);
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(AGE, age).with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8)).with(FAKE_WATERLOGGED, applyFakeWaterLogging), flags);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        int i = state.get(AGE);
        boolean flag = i == 7;
        if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        } else if (i > 6) {
            Random rand = new Random();
            int j = 1 + rand.nextInt(3);
            spawnAsEntity(worldIn, pos, new ItemStack(EnvironmentalItems.RICE.get(), j));
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_CROP_BREAK,
                    SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            if (state.get(HALF) == DoubleBlockHalf.UPPER) {
                worldIn.setBlockState(pos.down(), EnvironmentalBlocks.RICE.get().getDefaultState().with(WATERLOGGED, worldIn.getBlockState(pos.down()).get(WATERLOGGED)).with(RiceBlock.AGE, Integer.valueOf(0)), 2);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else {
                worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
                worldIn.setBlockState(pos, EnvironmentalBlocks.RICE.get().getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)).with(RiceBlock.AGE, Integer.valueOf(0)), 2);
            }
            return ActionResultType.SUCCESS;
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = state.get(AGE);
        if (i < 7) {
            worldIn.setBlockState(pos, state.with(AGE, i + 1));
            if (state.get(HALF) == DoubleBlockHalf.UPPER) {
                worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, i + 1));
            } else {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(AGE, i + 1));
            }

        }
    }


    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        int i = state.get(AGE);
        int chance = worldIn.getBlockState(pos.down().down()).isFertile(worldIn, pos.down().down()) ? 6 : 8;
        if (state.get(HALF) == DoubleBlockHalf.UPPER && i < 7 && (worldIn.getBlockState(pos.down().down()).getBlock() == Blocks.FARMLAND || worldIn.getFluidState(pos.down()).getLevel() == 8) && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(chance) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
            if (worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.down()).get(AGE) == 6) {
                worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, Integer.valueOf(i + 1)), 2);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            if (doubleblockhalf == DoubleBlockHalf.UPPER && !stateIn.get(FAKE_WATERLOGGED)) {
                stateIn = stateIn.with(FAKE_WATERLOGGED, true);
                worldIn.setBlockState(currentPos, worldIn.getBlockState(currentPos).with(FAKE_WATERLOGGED, true), 2);
            } else if (doubleblockhalf == DoubleBlockHalf.LOWER && !stateIn.get(FAKE_WATERLOGGED)) {
                stateIn = stateIn.with(FAKE_WATERLOGGED, true);
                worldIn.setBlockState(currentPos, worldIn.getBlockState(currentPos).with(FAKE_WATERLOGGED, true), 2);
            }
        }
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        FluidState ifluidstateUp = worldIn.getFluidState(pos.up());
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8)), 3);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 7;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return state.get(AGE) < 7;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public long getPositionRandom(BlockState state, BlockPos pos) {
        return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return EnvironmentalBlocks.TALL_RICE.get().getDefaultState();
    }
}