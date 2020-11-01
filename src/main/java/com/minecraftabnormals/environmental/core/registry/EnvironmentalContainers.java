package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.minecraftabnormals.environmental.client.gui.screen.inventory.SawmillScreen;
import com.minecraftabnormals.environmental.common.inventory.container.KilnContainer;
import com.minecraftabnormals.environmental.common.inventory.container.SawmillContainer;
import com.minecraftabnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Environmental.MODID);

    public static final RegistryObject<ContainerType<KilnContainer>> KILN = CONTAINER_TYPES.register("kiln", () -> new ContainerType<>(KilnContainer::new));
    public static final RegistryObject<ContainerType<SawmillContainer>> SAWMILL = CONTAINER_TYPES.register("sawmill", () -> new ContainerType<>(SawmillContainer::new));
    public static final RegistryObject<ContainerType<SlabfishInventoryContainer>> SLABFISH_INVENTORY = CONTAINER_TYPES.register("slabfish_inventory", () -> new ContainerType<>(SlabfishInventoryContainer::new));
    
    public static void registerScreenFactories() {
        ScreenManager.registerFactory(EnvironmentalContainers.KILN.get(), KilnScreen::new);
        ScreenManager.registerFactory(EnvironmentalContainers.SAWMILL.get(), SawmillScreen::new);
    }
}
