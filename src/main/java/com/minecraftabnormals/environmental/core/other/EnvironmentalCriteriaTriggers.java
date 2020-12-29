package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.common.entity.util.SlabfishNearbyCriteriaTrigger;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.abnormals_core.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalCriteriaTriggers {
    public static final EmptyTrigger BACKPACK_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("backpack_slabfish")));

    public static final SlabfishNearbyCriteriaTrigger SLABFISH = CriteriaTriggers.register(new SlabfishNearbyCriteriaTrigger());

    private static ResourceLocation prefix(String name) {
        return new ResourceLocation(Environmental.MODID, name);
    }
}	
