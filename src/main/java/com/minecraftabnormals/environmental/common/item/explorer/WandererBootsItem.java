package com.minecraftabnormals.environmental.common.item.explorer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.WandererBootsModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class WandererBootsItem extends ExplorerArmorItem {
	public static final String NBT_TAG = "WandererBootsUses";

	public WandererBootsItem(Properties properties) {
		super(EquipmentSlotType.FEET, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return WandererBootsModel.get(1.0F);
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
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 1000, 5000, 10000, 50000};
	}

	@Override
	public int levelUp(ItemStack stack, LivingEntity entity) {
		return super.levelUp(stack, entity, true);
	}
}
