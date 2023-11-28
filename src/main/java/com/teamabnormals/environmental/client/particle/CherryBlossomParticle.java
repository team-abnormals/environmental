package com.teamabnormals.environmental.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CherryBlossomParticle extends TextureSheetParticle {
	private final float rotSpeed;
	private int waterTicks;
	private boolean isInWater;
	private boolean thunder;
	private boolean rain;

	private CherryBlossomParticle(ClientLevel level, double xCoordIn, double yCoordIn, double zCoordIn) {
		super(level, xCoordIn, yCoordIn, zCoordIn);
		this.quadSize *= 1.75F;
		this.lifetime = 500 + random.nextInt(200) + random.nextInt(200);
		this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
		this.roll = (float) Math.random() * ((float) Math.PI * 2F);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			if (this.thunder) {
				this.xd -= 0.0125F;
				this.yd -= 0.0075F;
			} else if (this.rain) {
				this.xd -= 0.001F;
			}

			this.move(this.xd, this.yd, this.zd);
			this.yd -= 0.001F;
			this.yd = Math.max(this.yd, -0.08F);

			this.oRoll = this.roll;

			if (!this.isInWater) {
				this.isInWater = this.level.getBlockState(new BlockPos(this.x, this.y, this.z)).getFluidState().is(FluidTags.WATER);
			} else {
				this.waterTicks++;
			}

			if (!this.onGround && !(this.isInWater && this.waterTicks >= 1)) {
				this.roll += (float) Math.PI * this.rotSpeed * 1.6F;
			} else {
				this.yd = 0.0D;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			CherryBlossomParticle particle = new CherryBlossomParticle(level, x, y, z);
			particle.pickSprite(this.spriteSet);
			if (level.getBrightness(LightLayer.SKY, new BlockPos(x, y, z)) > 8) {
				particle.rain = level.isRaining();
				particle.thunder = level.isThundering();
			}
			return particle;
		}
	}
}
