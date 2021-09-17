package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.entity.DuckEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.vector.Vector3d;

public class DuckSwimGoal extends Goal {
	private final DuckEntity duck;

	public DuckSwimGoal(DuckEntity entityIn) {
		this.duck = entityIn;
		entityIn.getNavigation().setCanFloat(true);
	}

	public boolean canUse() {
		return this.duck.isInWater() || this.duck.isInLava();
	}

	public void tick() {
		Vector3d motion = this.duck.getDeltaMovement();
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