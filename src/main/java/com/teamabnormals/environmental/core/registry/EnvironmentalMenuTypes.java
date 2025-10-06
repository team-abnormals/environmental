package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnvironmentalMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Environmental.MOD_ID);

	public static final DeferredHolder<MenuType<?>, MenuType<SlabfishInventoryMenu>> SLABFISH_INVENTORY = MENUS.register("slabfish_inventory", () -> new MenuType<>(SlabfishInventoryMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
