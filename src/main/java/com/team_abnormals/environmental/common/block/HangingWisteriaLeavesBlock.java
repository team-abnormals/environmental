package com.team_abnormals.environmental.common.block;

import net.minecraft.block.*;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
@SuppressWarnings("deprecation")
public class HangingWisteriaLeavesBlock extends Block implements net.minecraftforge.common.IShearable {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    protected static final VoxelShape WISTERIA_VINE_TOP = Block.makeCuboidShape(1, 0, 1, 15, 16, 15);
    protected static final VoxelShape WISTERIA_VINE_BOTTOM = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);

    public HangingWisteriaLeavesBlock(Block.Properties properties) {
        super(properties);
        setDefaultState(stateContainer.getBaseState().with(HALF, DoubleBlockHalf.UPPER));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) return WISTERIA_VINE_TOP;
        if (state.get(HALF) == DoubleBlockHalf.LOWER) return WISTERIA_VINE_BOTTOM;
        else return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
        if (state == getDefaultState().with(HALF, DoubleBlockHalf.UPPER)) {
            if (world.getBlockState(pos.up()) == Blocks.AIR.getDefaultState()) {
                world.removeBlock(pos, false);
            }
        }
        else if (state == getDefaultState().with(HALF, DoubleBlockHalf.LOWER)) {
            if (world.getBlockState(pos.up()) == Blocks.AIR.getDefaultState()) {
                world.removeBlock(pos, false);
            }
        }
    }

    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRainingAt(pos.up())) {
            if (rand.nextInt(15) == 1) {
                BlockPos blockpos = pos.down();
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (!blockstate.isSolid() || !blockstate.isSolidSide(worldIn, blockpos, Direction.UP)) {
                    double d0 = (double)((float)pos.getX() + rand.nextFloat());
                    double d1 = (double)pos.getY() - 0.05D;
                    double d2 = (double)((float)pos.getZ() + rand.nextFloat());
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
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block == getDefaultState().with(HALF, DoubleBlockHalf.UPPER).getBlock() || block.isIn(BlockTags.LEAVES) || block.isIn(BlockTags.LOGS);
    }

    /*
    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (world.getBlockState(pos.down()) == getDefaultState().with(HALF, DoubleBlockHalf.LOWER)) {
            world.removeBlock(pos.down(), false);
        }
        world.removeBlock(pos, false);
        return false;
    }
    */

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        if (isStateValid(world, pos)) {
            if (world.getBlockState(pos.up()) == getDefaultState().with(HALF, DoubleBlockHalf.UPPER)) {
                return getDefaultState().with(HALF, DoubleBlockHalf.LOWER);
            }
            else if (world.getBlockState(pos.up()) == getDefaultState().with(HALF, DoubleBlockHalf.LOWER)) {
                return null;
            }
            else return getDefaultState().with(HALF, DoubleBlockHalf.UPPER);
        }
        return null;
    }
}