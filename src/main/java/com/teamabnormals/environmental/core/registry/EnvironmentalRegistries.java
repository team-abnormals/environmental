package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition.Factory;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class EnvironmentalRegistries {
	//TODO: Use proper creation
	public static final IForgeRegistry<Factory> SLABFISH_CONDITIONS = RegistryManager.ACTIVE.getRegistry(Factory.class);
}
