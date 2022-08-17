package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.Duck;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class DuckSwimGoal extends Goal {
	private final Duck duck;

	public DuckSwimGoal(Duck entityIn) {
		this.duck = entityIn;
		entityIn.getNavigation().setCanFloat(true);
	}

	@Override
	public boolean canUse() {
		return this.duck.isInWater() || this.duck.isInLava();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		Vec3 motion = this.duck.getDeltaMovement();
		double d0 = this.duck.getFluidHeight(FluidTags.WATER);
		double d1 = motion.y;

		if (!this.duck.isBaby() && d0 > 0.3D || this.duck.isBaby() && d0 > 0.15D) {
			if (d1 < -0.02D) {
				d1 *= 0.3F;
			} else if (!this.duck.isBaby() && d0 > 0.31D || this.duck.isBaby() && d0 > 0.16) {
				d1 += motion.y < (double) 0.12F ? 0.03F : 0.0F;
			} else if (d1 < 0.0D) {
				d1 = 0.0D;
			}
		}

		this.duck.setDeltaMovement(motion.x, d1, motion.z);
	}
}