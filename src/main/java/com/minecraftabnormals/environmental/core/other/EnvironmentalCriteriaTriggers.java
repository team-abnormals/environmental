package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.abnormals_core.common.advancement.EmptyTrigger;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishNearbyCriteriaTrigger;
import com.minecraftabnormals.environmental.common.entity.util.UpgradeGearCriteriaTrigger;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalCriteriaTriggers {
	public static final EmptyTrigger BACKPACK_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("backpack_slabfish")));

	public static final SlabfishNearbyCriteriaTrigger SLABFISH = CriteriaTriggers.register(new SlabfishNearbyCriteriaTrigger());
	public static final UpgradeGearCriteriaTrigger UPGRADE_GEAR = CriteriaTriggers.register(new UpgradeGearCriteriaTrigger());

	private static ResourceLocation prefix(String name) {
		return new ResourceLocation(Environmental.MOD_ID, name);
	}
}	
