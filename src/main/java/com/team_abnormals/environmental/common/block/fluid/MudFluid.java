package com.team_abnormals.environmental.common.block.fluid;

import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalFluids;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidAttributes.Builder;

import java.util.Random;

public abstract class MudFluid extends FlowingFluid {
    public MudFluid() {
    }

    @Override
    public Fluid getFlowingFluid() {
        return EnvironmentalFluids.FLOWING_MUD.get();
    }

    @Override
    public Fluid getStillFluid() {
        return EnvironmentalFluids.MUD.get();
    }

    public Item getFilledBucket() {
        return EnvironmentalItems.MUD_BUCKET.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(World worldIn, BlockPos pos, FluidState state, Random random) {
        if (!state.isSource() && !(Boolean) state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.UNDERWATER, (double) ((float) pos.getX() + random.nextFloat()), (double) ((float) pos.getY() + random.nextFloat()), (double) ((float) pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }

    }

    public boolean isIn(Tag<Fluid> tagIn) {
        return tagIn == FluidTags.WATER;
    }

    protected boolean canSourcesMultiply() {
        return false;
    }

    protected void beforeReplacingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity(state) ? world.getTileEntity(pos) : null;
        Block.spawnDrops(state, world.getWorld(), pos, tileentity);
    }

    public int getSlopeFindDistance(IWorldReader reader) {
        return 1;
    }

    public BlockState getBlockState(FluidState state) {
        return (BlockState) EnvironmentalBlocks.MUD.get().getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == EnvironmentalFluids.MUD.get() || fluidIn == EnvironmentalFluids.FLOWING_MUD.get();
    }

    public int getLevelDecreasePerBlock(IWorldReader reader) {
        return 2;
    }

    public int getTickRate(IWorldReader reader) {
        return 6;
    }

    public boolean canDisplace(FluidState state, IBlockReader reader, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected FluidAttributes createAttributes() {
        Builder builder = FluidAttributes.builder(new ResourceLocation(Environmental.MODID, "block/mud_still"), new ResourceLocation(Environmental.MODID, "block/mud_flow"));
        builder.luminosity(15).density(500).viscosity(1000).translationKey("fluid.environmental.mud").overlay(new ResourceLocation(Environmental.MODID, "block/mud_overlay"));
        return builder.build(this);
    }

    public static class Source extends MudFluid {

        public int getLevel(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends MudFluid {

        protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(new IntegerProperty[]{LEVEL_1_8});
        }

        public int getLevel(FluidState state) {
            return (Integer) state.get(LEVEL_1_8);
        }

        public boolean isSource(FluidState state) {
            return false;
        }

    }
}