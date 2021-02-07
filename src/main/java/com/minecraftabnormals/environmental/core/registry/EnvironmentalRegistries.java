package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class EnvironmentalRegistries {
	public static final IForgeRegistry<SlabfishCondition.Factory> SLABFISH_CONDITIONS = RegistryManager.ACTIVE.getRegistry(SlabfishCondition.Factory.class);
}
