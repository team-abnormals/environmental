package com.minecraftabnormals.environmental.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.WandererBootsModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class WandererBootsItem extends ArmorItem {
	public static final String NBT_TAG = "WandererBootsUses";
	
	public WandererBootsItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.FEET, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return WandererBootsModel.get(1.0F);
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

		int uses = Math.round(stack.getOrCreateTag().getFloat(NBT_TAG));
		double increase = 0.15F + (0.05F * getIncreaseForUses(uses));

		builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Speed modifier", increase, AttributeModifier.Operation.MULTIPLY_BASE));
		return slot == this.slot ? builder.build() : super.getAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void onFallEvent(LivingFallEvent event) {
		if (event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == EnvironmentalItems.WANDERER_BOOTS.get() && event.getEntityLiving().fallDistance < 6)
			event.setDamageMultiplier(0);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compound = stack.getOrCreateTag();
		int uses = Math.round(compound.getFloat(NBT_TAG));
		tooltip.add((new StringTextComponent(Integer.toString(uses) + " meters travelled")).mergeStyle(TextFormatting.GRAY));
	}
	
	public static int getIncreaseForUses(int uses) {
		int increase = 1;
		if (uses >= 1000)
			increase += 1;
		if (uses >= 5000)
			increase += 1;
		if (uses >= 10000)
			increase += 1;
		if (uses >= 50000)
			increase += 1;
		return increase;
	}
}
