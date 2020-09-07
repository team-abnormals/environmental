package com.minecraftabnormals.environmental.common.block;

import java.util.Random;

import com.google.common.base.Supplier;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;

import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LotusFlowerBlock extends AbnormalsFlowerBlock {
	private Supplier<IParticleData> particle;

    public LotusFlowerBlock(Supplier<IParticleData> particle, Effect stewEffect, int stewEffectDuration, Properties properties) {
        super(stewEffect, stewEffectDuration, properties);
        this.particle = particle;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        for (int i = 0; i < 3; i++) {
            double offsetX = rand.nextFloat() * 0.6F;
            double offsetZ = rand.nextFloat() * 0.45F;

            double x = pos.getX() + 0.25D + offsetX;
            double y = pos.getY() + 0.25D + (rand.nextFloat() * 0.05F);
            double z = pos.getZ() + 0.25D + offsetZ;

            if (worldIn.isRemote && worldIn.getGameTime() % 9 == 0)
                worldIn.addParticle(this.particle.get(), x, y, z, 0.03D, 0.0D, 0.03D);
        }
    }
}
