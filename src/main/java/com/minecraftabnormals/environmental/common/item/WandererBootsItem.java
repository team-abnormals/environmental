package com.minecraftabnormals.environmental.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.WandererBootsModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEnchantments;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class WandererBootsItem extends ArmorItem {

	public WandererBootsItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.FEET, properties);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return (A) new WandererBootsModel(1.0F);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/wanderer_boots.png";
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(this.getEquipmentSlot()));
		UUID uuid = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");

		int level = EnchantmentHelper.getEnchantmentLevel(EnvironmentalEnchantments.TRAVELLING.get(), stack);
		double increase = 0.20F + (0.05F * level);

		builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Speed modifier", increase, AttributeModifier.Operation.MULTIPLY_BASE));
		return slot == this.slot ? builder.build() : super.getAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void onFallEvent(LivingFallEvent event) {
		if (event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == EnvironmentalItems.WANDERER_BOOTS.get() && event.getEntityLiving().fallDistance < 6)
			event.setDamageMultiplier(0);
	}
}
