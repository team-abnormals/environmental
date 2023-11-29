package com.teamabnormals.environmental.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CattailFluffParticle extends TextureSheetParticle {
	private float angle;

	public CattailFluffParticle(ClientLevel world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super(world, posX, posY, posZ, motionX, motionY, motionZ);
		this.xd = motionX;
		this.yd = motionY + (random.nextDouble() * 0.01D);
		this.zd = motionZ;
		this.angle = (float) Math.random() * ((float) Math.PI * 2F);
		this.lifetime = random.nextInt(50) + 50;
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
			this.move(this.xd, this.yd, this.zd);
			this.xd += Math.cos(this.angle) * 0.0001;
			this.zd += Math.sin(this.angle) * 0.0001;
		}
		this.alpha -= (this.lifetime / 10000F);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet animatedSprite;

		public Factory(SpriteSet animatedSprite) {
			this.animatedSprite = animatedSprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			CattailFluffParticle particle = new CattailFluffParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.pickSprite(this.animatedSprite);
			return particle;
		}
	}
}