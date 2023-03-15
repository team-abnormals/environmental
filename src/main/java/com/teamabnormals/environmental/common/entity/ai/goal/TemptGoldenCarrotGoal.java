package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptGoldenCarrotGoal extends TemptGoal {

	public TemptGoldenCarrotGoal(PathfinderMob creatureIn, double speedIn, Ingredient temptItemsIn, boolean scaredByPlayerMovementIn) {
		super(creatureIn, speedIn, temptItemsIn, scaredByPlayerMovementIn);
	}

	@Override
	public boolean canUse() {
		return ((IDataManager) this.mob).getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) == 0 && super.canUse();
	}
}
