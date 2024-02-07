package com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class PineconeGolemTemptGoal extends TemptGoal {

	public PineconeGolemTemptGoal(PineconeGolem golem, double speedModifier) {
		super(golem, speedModifier, Ingredient.of(ItemTags.SAPLINGS), false);
	}

	@Override
	public boolean canUse() {
		return this.mob.getMainHandItem().isEmpty() && super.canUse();
	}
}