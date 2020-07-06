package com.team_abnormals.environmental.common.block.fluid;

import java.util.function.Supplier;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.entity.SlabfishOverlay;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class MudFluidBlock extends FlowingFluidBlock {


    public MudFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public int tickRate(IWorldReader p_149738_1_) { return 35; }

    @Override
    public boolean reactWithNeighbors(World worldIn, BlockPos pos, BlockState state) {
        for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA)) {
                worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, Blocks.COARSE_DIRT.getDefaultState()));
            }
            if (direction != Direction.DOWN && worldIn.getFluidState(pos.offset(direction)).getFluid() instanceof WaterFluid) {
                worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, Blocks.DIRT.getDefaultState()));
            }
        }
        return super.reactWithNeighbors(worldIn, pos, state);
    }
    
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
    	if(!(entityIn instanceof SlabfishEntity || entityIn instanceof PigEntity)) entityIn.setMotionMultiplier(state, new Vector3d(0.9D, (double)0.8F, 0.9D));
    	
    	if (entityIn instanceof SlabfishEntity) {
    		SlabfishEntity slabby = (SlabfishEntity)entityIn;
			if(slabby.getSlabfishOverlay() != SlabfishOverlay.MUDDY) slabby.setSlabfishOverlay(SlabfishOverlay.MUDDY);
    	}
    }

} 