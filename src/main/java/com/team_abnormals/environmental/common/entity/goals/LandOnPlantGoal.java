package com.team_abnormals.environmental.common.entity.goals;

import com.team_abnormals.environmental.common.entity.ButterflyEntity;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class LandOnPlantGoal extends MoveToBlockGoal {
    private final ButterflyEntity butterfly;
    private Random random = new Random();

    public LandOnPlantGoal(ButterflyEntity butterflyIn, double speed) {
        super(butterflyIn, speed, 8);
        butterfly = butterflyIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return super.shouldExecute();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        if (getIsAboveDestination()) {
            World world = butterfly.world;
            BlockPos blockpos = destinationBlock.up();
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (block instanceof PotatoBlock || block instanceof CarrotBlock || block == Blocks.WHEAT || block instanceof StemBlock) {
                if (block instanceof BeetrootBlock) {
                    Integer integer = blockstate.get(BeetrootBlock.BEETROOT_AGE);
                    if (integer < 3) {
                        world.setBlockState(blockpos, blockstate.with(BeetrootBlock.BEETROOT_AGE, integer + 1), 2);
                    }
                } else {
                    Integer integer = blockstate.get(BlockStateProperties.AGE_0_7);
                    if (integer < 7) {
                        world.setBlockState(blockpos, blockstate.with(CropsBlock.AGE, integer + 1), 2);
                    }
                }
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) ((float) blockpos.getX() + random.nextFloat()), (double) blockpos.getY() + (double) random.nextFloat() * blockstate.getShape(world, blockpos).getEnd(Direction.Axis.Y), (double) ((float) blockpos.getZ() + random.nextFloat()), d0, d1, d2);
            }
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        Block block = blockstate.getBlock();
        if (block instanceof BushBlock) return true;
        else return false;
    }
}