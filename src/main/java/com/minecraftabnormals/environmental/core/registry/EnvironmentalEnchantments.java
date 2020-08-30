package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.enchantment.ReachingEnchantment;
import com.minecraftabnormals.environmental.common.enchantment.TravellingEnchantment;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Environmental.MODID);

	public static final RegistryObject<Enchantment> REACHING 	= ENCHANTMENTS.register("reaching", () -> new ReachingEnchantment());
	public static final RegistryObject<Enchantment> TRAVELLING 	= ENCHANTMENTS.register("travelling", () -> new TravellingEnchantment());
}
