package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.client.gui.screens.inventory.KilnScreen;
import com.teamabnormals.environmental.common.inventory.KilnMenu;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Environmental.MOD_ID);

	public static final RegistryObject<MenuType<KilnMenu>> KILN = MENU_TYPES.register("kiln", () -> new MenuType<>(KilnMenu::new));
	public static final RegistryObject<MenuType<SlabfishInventoryMenu>> SLABFISH_INVENTORY = MENU_TYPES.register("slabfish_inventory", () -> new MenuType<>(SlabfishInventoryMenu::new));

	public static void registerScreenFactories() {
		MenuScreens.register(EnvironmentalMenuTypes.KILN.get(), KilnScreen::new);
	}
}
