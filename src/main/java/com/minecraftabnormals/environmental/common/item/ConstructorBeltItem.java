package com.minecraftabnormals.environmental.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEnchantments;
import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

public class ConstructorBeltItem extends ArmorItem {

	public ConstructorBeltItem(Properties properties) {
		super(ArmorMaterial.LEATHER, EquipmentSlotType.LEGS, properties);
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
//		return (A) new WandererBootsModel(1.0F);
//	}

//	@Override
//	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
//		return Environmental.MODID + ":textures/models/armor/constructor_belt.png";
//	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(this.getEquipmentSlot()));
		UUID uuid = UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D");
		
		int level = EnchantmentHelper.getEnchantmentLevel(EnvironmentalEnchantments.REACHING.get(), stack);
		int increase = 2 + (level);
		
		builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(uuid, "Reach modifier", increase, AttributeModifier.Operation.ADDITION));

		return slot == this.slot ? builder.build() : super.getAttributeModifiers(slot);
	}
}
