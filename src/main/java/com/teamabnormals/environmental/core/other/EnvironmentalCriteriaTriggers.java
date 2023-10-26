package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import com.teamabnormals.environmental.common.advancement.SlabfishNearbyCriterionTrigger;
import com.teamabnormals.environmental.common.advancement.UpgradeGearTrigger;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalCriteriaTriggers {
	public static final EmptyTrigger BACKPACK_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("backpack_slabfish")));

	public static final SlabfishNearbyCriterionTrigger SLABFISH = CriteriaTriggers.register(new SlabfishNearbyCriterionTrigger());
	public static final UpgradeGearTrigger UPGRADE_GEAR = CriteriaTriggers.register(new UpgradeGearTrigger());

	private static ResourceLocation prefix(String name) {
		return new ResourceLocation(Environmental.MOD_ID, name);
	}
}	
