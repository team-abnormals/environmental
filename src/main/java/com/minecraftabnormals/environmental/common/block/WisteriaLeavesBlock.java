package com.minecraftabnormals.environmental.common.block;

import java.util.Random;

import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLeavesBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

public class WisteriaLeavesBlock extends AbnormalsLeavesBlock implements IForgeShearable {
    public static final IntegerProperty DISTANCE_CLONE = IntegerProperty.create("distance_clone", 1, 8);

    public WisteriaLeavesBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(stateContainer.getBaseState().with(DISTANCE_CLONE, 8).with(PERSISTENT, false));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return state.get(DISTANCE_CLONE) == 8 && !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE_CLONE) == 8) {
            spawnDrops(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if (i != 1 || stateIn.get(DISTANCE_CLONE) != i) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }
        return stateIn;
    }

    private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = 8;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            blockpos$mutable.func_239622_a_(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE_CLONE, Integer.valueOf(i));
    }

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            if (neighbor.getBlock() instanceof WisteriaLeavesBlock) 
            	return neighbor.get(DISTANCE_CLONE);
            else if (neighbor.getBlock() instanceof LeavesBlock) 
            	return neighbor.get(LeavesBlock.DISTANCE);
            else return 8;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    	super.fillStateContainer(builder);
        builder.add(DISTANCE_CLONE);
    }
}