package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.common.inventory.container.KilnContainer;
import com.team_abnormals.environmental.common.inventory.container.SawmillContainer;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Environmental.MODID);

    public static final RegistryObject<ContainerType<KilnContainer>> KILN = CONTAINER_TYPES.register("kiln", () -> new ContainerType<>(KilnContainer::new));
    public static final RegistryObject<ContainerType<SawmillContainer>> SAWMILL = CONTAINER_TYPES.register("sawmill", () -> new ContainerType<>(SawmillContainer::new));
}
