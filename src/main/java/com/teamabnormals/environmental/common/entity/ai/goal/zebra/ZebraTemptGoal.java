package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class ZebraTemptGoal extends TemptGoal {

	public ZebraTemptGoal(PathfinderMob zebra, double speedModifier, Ingredient items) {
		super(zebra, speedModifier, items, false);
	}

	@Override
	public boolean canUse() {
		return ((Zebra) this.mob).isTamed() && super.canUse();
	}
}