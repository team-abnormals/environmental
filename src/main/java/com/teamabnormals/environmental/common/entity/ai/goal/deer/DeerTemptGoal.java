package com.teamabnormals.environmental.common.entity.ai.goal.deer;

import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class DeerTemptGoal extends TemptGoal {
	private final AbstractDeer deer;
	private final double speedModifierTrusting;

	public DeerTemptGoal(AbstractDeer deerIn, double speedModifier, double speedModifierTrustingIn, Ingredient temptItems) {
		super(deerIn, speedModifier, temptItems, false);
		this.deer = deerIn;
		this.speedModifierTrusting = speedModifierTrustingIn;
	}

	@Override
	public void start() {
		super.start();
		if (!this.deer.isTrusting())
			this.deer.setTargetNeckAngle(45);
	}

	@Override
	public void stop() {
		super.stop();
		this.deer.resetTargetNeckAngle();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.deer.isTrusting()) {
			this.mob.getNavigation().setSpeedModifier(this.speedModifierTrusting);
			this.deer.resetTargetNeckAngle();
		}
	}
}