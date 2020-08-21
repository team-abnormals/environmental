package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.enchantment.ReachingEnchantment;
import com.minecraftabnormals.environmental.common.enchantment.TravellingEnchantment;
import com.minecraftabnormals.environmental.core.Environmental;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Environmental.MODID);
	
	public static final RegistryObject<Enchantment> REACHING 	= ENCHANTMENTS.register("reaching", () -> new ReachingEnchantment());
	public static final RegistryObject<Enchantment> TRAVELLING 	= ENCHANTMENTS.register("travelling", () -> new TravellingEnchantment());
	
	public static final EnchantmentType CONSTRUCTOR_BELT = EnchantmentType.create("CONSTRUCTOR_BELT", (item) -> item == EnvironmentalItems.CONSTRUCTOR_BELT.get());
	public static final EnchantmentType WANDERER_BOOTS = EnchantmentType.create("WANDERER_BOOTS", (item) -> item == EnvironmentalItems.WANDERER_BOOTS.get());
	
	public static void addEnchantmentsToItemGroups() {
        ItemGroup.COMBAT.setRelevantEnchantmentTypes(RegistryHelper.add(RegistryHelper.add(ItemGroup.COMBAT.getRelevantEnchantmentTypes(), EnvironmentalEnchantments.CONSTRUCTOR_BELT), EnvironmentalEnchantments.WANDERER_BOOTS));	
	}
}
