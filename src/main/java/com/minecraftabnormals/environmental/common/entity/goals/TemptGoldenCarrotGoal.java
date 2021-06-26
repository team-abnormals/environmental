package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.crafting.Ingredient;

public class TemptGoldenCarrotGoal extends TemptGoal {
	public TemptGoldenCarrotGoal(CreatureEntity creatureIn, double speedIn, boolean scaredByPlayerMovementIn, Ingredient temptItemsIn) {
		super(creatureIn, speedIn, scaredByPlayerMovementIn, temptItemsIn);
	}

	public boolean canUse() {
		return ((IDataManager) this.mob).getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) == 0 && super.canUse();
	}
}
