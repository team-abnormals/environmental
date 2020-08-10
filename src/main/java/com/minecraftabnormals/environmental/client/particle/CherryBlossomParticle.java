package com.minecraftabnormals.environmental.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CherryBlossomParticle extends SpriteTexturedParticle {
    private final float rotSpeed;

    private CherryBlossomParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double particleRedIn, double particleGreenIn, double particleBlueIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.particleScale *= 1.2F;
        int i = (int) (32.0D / (Math.random() * 0.8D + 0.2D));
        this.maxAge = (int) Math.max((float) i * 1.8F, 2.0F);
        this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
        this.particleAngle = (float) Math.random() * ((float) Math.PI * 2F);
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionY -= (double) 0.002F;
            this.motionY = Math.max(this.motionY, (double) -0.1F);

            this.prevParticleAngle = this.particleAngle;
            if (!this.onGround) {
                this.particleAngle += (float) Math.PI * this.rotSpeed * 1.6F;
            } else {
                this.motionY = 0.0D;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CherryBlossomParticle particle = new CherryBlossomParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}
