package com.minecraftabnormals.environmental.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LotusBlossomParticle extends SpriteTexturedParticle {
	private float angle;

	public LotusBlossomParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super(world, posX, posY, posZ, motionX, motionY, motionZ);
		this.xd = motionX;
		this.yd = motionY + (random.nextDouble() * 0.05D);
		this.zd = motionZ;
		this.angle = (float) Math.random() * ((float) Math.PI * 2F);
		this.lifetime = random.nextInt(75) + 75;
		this.quadSize = 0.2F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
	}

	@Override
	public void tick() {
		if (this.age % 5 == 0) {
			this.angle = (float) Math.random() * ((float) Math.PI * 2F);
		}
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.yd -= 0.04D * this.gravity;
			this.move(this.xd, this.yd, this.zd);
			this.xd += Math.cos(this.angle) * 0.0005;
			this.zd += Math.sin(this.angle) * 0.0005;
			this.yd *= 0.98D;
		}
		this.alpha -= (this.lifetime / 10000F);
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite animatedSprite;

		public Factory(IAnimatedSprite animatedSprite) {
			this.animatedSprite = animatedSprite;
		}

		@Override
		public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			LotusBlossomParticle particle = new LotusBlossomParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.pickSprite(this.animatedSprite);
			return particle;
		}
	}
}