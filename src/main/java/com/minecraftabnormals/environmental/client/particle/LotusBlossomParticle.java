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
public class LotusBlossomParticle extends SpriteTexturedParticle {
	private float angle;

	public LotusBlossomParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super(world, posX, posY, posZ, motionX, motionY, motionZ);
		this.motionX = motionX;
		this.motionY = motionY + (rand.nextDouble() * 0.05D);
		this.motionZ = motionZ;
		this.angle = (float) Math.random() * ((float) Math.PI * 2F);
		this.maxAge = rand.nextInt(75) + 75;
		this.particleScale = 0.2F * (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
	}

	@Override
	public void tick() {
		if (this.age % 5 == 0) {
			this.angle = (float) Math.random() * ((float) Math.PI * 2F);
		}
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			this.motionY -= 0.04D * this.particleGravity;
			this.move(this.motionX, this.motionY, this.motionZ);
			this.motionX += Math.cos(this.angle) * 0.0005;
			this.motionZ += Math.sin(this.angle) * 0.0005;
			this.motionY *= 0.98D;
		}
		this.particleAlpha -= (this.maxAge / 10000F);
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Factory implements IParticleFactory<BasicParticleType> {
		private IAnimatedSprite animatedSprite;

		public Factory(IAnimatedSprite animatedSprite) {
			this.animatedSprite = animatedSprite;
		}

		@Override
		public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			LotusBlossomParticle particle = new LotusBlossomParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteRandomly(this.animatedSprite);
			return particle;
		}
	}
}