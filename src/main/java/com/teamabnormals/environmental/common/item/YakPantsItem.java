package com.teamabnormals.environmental.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class YakPantsItem extends ArmorItem {
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.TURTLE_HELMET);
	private static final UUID YAK_PANTS_UUID = UUID.fromString("35e7342c-9ff3-40ea-b72e-7a2c29c12caa");

	public YakPantsItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		ItemStack legsStack = entity.getItemBySlot(EquipmentSlot.LEGS);
		if (legsStack.getItem() instanceof YakPantsItem && entity.getVehicle() instanceof LivingEntity mount) {
			if (!event.getEntity().level.isClientSide()) {
				mount.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60));
				mount.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
			}
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getDefaultAttributeModifiers(this.getSlot()));
		builder.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(YAK_PANTS_UUID, "Step Height", 0.4F, AttributeModifier.Operation.ADDITION));
		return slot == this.slot ? builder.build() : super.getDefaultAttributeModifiers(slot);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}
}
