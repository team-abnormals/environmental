package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Environmental.MOD_ID);

	public static final RegistryObject<MenuType<SlabfishInventoryMenu>> SLABFISH_INVENTORY = MENU_TYPES.register("slabfish_inventory", () -> new MenuType<>(SlabfishInventoryMenu::new));
}
