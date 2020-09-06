package com.minecraftabnormals.environmental.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KilnSmokeParticle extends SpriteTexturedParticle {
    private KilnSmokeParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z);
        this.particleScale *= 2.5F;
        this.maxAge = this.rand.nextInt(50) + 40;

        this.particleGravity = 3.0E-6F;
        this.motionX = motionX;
        this.motionY = motionY + (double)(this.rand.nextFloat() / 500.0F);
        this.motionZ = motionZ;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ < this.maxAge && !(this.particleAlpha <= 0.0F)) {
            this.motionX += (this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1));
            this.motionZ += (this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1));
            this.motionY -= this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.age >= this.maxAge - 60 && this.particleAlpha > 0.01F) {
                this.particleAlpha -= 0.015F;
            }

        } else {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            KilnSmokeParticle particle = new KilnSmokeParticle(world, x, y, z, motionX, motionY, motionZ);
            particle.setAlphaF(0.9F);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}
