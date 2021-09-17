package com.minecraftabnormals.environmental.common.item.explorer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.ThiefHoodModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalAttributes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class ThiefHoodItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "ThiefHoodUses";

	public ThiefHoodItem(Properties properties) {
		super(EquipmentSlotType.HEAD, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return ThiefHoodModel.get(1.0F);
	}

	@Override
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity) {
		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getDefaultAttributeModifiers(this.getSlot()));
		UUID uuid = UUID.fromString("1D45B301-E65D-47A2-B63F-6EC5FCAC9316");

		int uses = Math.round(stack.getOrCreateTag().getFloat(NBT_TAG));
		double increase = 0.15D * getIncreaseForUses(uses);

		builder.put(EnvironmentalAttributes.STEALTH.get(), new AttributeModifier(uuid, "Stealth", increase, AttributeModifier.Operation.ADDITION));
		return slot == this.slot ? builder.build() : super.getDefaultAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void hoodEquippedEvent(LivingEquipmentChangeEvent event) {
		if (event.getTo().getItem() == EnvironmentalItems.THIEF_HOOD.get() || event.getFrom().getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				((PlayerEntity) event.getEntityLiving()).refreshDisplayName();
			}
		}
	}

	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getSource().getEntity() instanceof LivingEntity && entity instanceof IMob) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			ItemStack stack = attacker.getItemBySlot(EquipmentSlotType.HEAD);
			if (stack.getItem() instanceof ThiefHoodItem) {
				((ThiefHoodItem) stack.getItem()).levelUp(stack, attacker);
			}
		}
	}

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 10, 50, 100, 500};
	}
}
