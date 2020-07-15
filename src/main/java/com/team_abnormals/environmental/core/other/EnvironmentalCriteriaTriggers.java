package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.core.Environmental;
import com.teamabnormals.abnormals_core.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalCriteriaTriggers {
    public static final EmptyTrigger BACKPACK_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("backpack_slabfish")));

    public static final EmptyTrigger SWAMP_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("swamp_slabfish")));
    public static final EmptyTrigger MARSH_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("marsh_slabfish")));

    private static ResourceLocation prefix(String name) {
        return new ResourceLocation(Environmental.MODID, name);
    }
}	
