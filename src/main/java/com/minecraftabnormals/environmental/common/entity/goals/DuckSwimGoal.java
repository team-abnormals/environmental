package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.entity.DuckEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.vector.Vector3d;

public class DuckSwimGoal extends Goal {
	private final DuckEntity duck;

	public DuckSwimGoal(DuckEntity entityIn) {
		this.duck = entityIn;
		entityIn.getNavigator().setCanSwim(true);
	}

	public boolean shouldExecute() {
		return this.duck.isInWater() || this.duck.isInLava();
	}
	
	public void tick() {
		Vector3d motion = this.duck.getMotion();
		double d0 = this.duck.func_233571_b_(FluidTags.WATER);
		double d1 = motion.y;
		
		if (!this.duck.isChild() && d0 > 0.3D || this.duck.isChild() && d0 > 0.15D) {
			if (d1 < 0.0D)
				d1 = 0.0D;
			else
				d1 = motion.y + (double)(motion.y < (double)0.12F ? 0.03F : 0.0F);
		}
		
		this.duck.setMotion(motion.x, d1, motion.z);
	}
}