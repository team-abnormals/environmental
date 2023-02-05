package com.teamabnormals.environmental.common.item.explorer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.teamabnormals.environmental.client.model.WandererBootsModel;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.UUID;
import java.util.function.Consumer;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class WandererBootsItem extends ExplorerArmorItem {
	public static final String NBT_TAG = "WandererBootsUses";

	public WandererBootsItem(Properties properties) {
		super(EquipmentSlot.FEET, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> properties) {
				return WandererBootsModel.INSTANCE;
			}
		});
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getDefaultAttributeModifiers(this.getSlot()));
		UUID uuid = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");

		int uses = Math.round(stack.getOrCreateTag().getFloat(NBT_TAG));
		double increase = 0.15F + (0.05F * getIncreaseForUses(uses));

		builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Speed modifier", increase, AttributeModifier.Operation.MULTIPLY_BASE));
		return slot == this.slot ? builder.build() : super.getDefaultAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void onFallEvent(LivingFallEvent event) {
		if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() == EnvironmentalItems.WANDERER_BOOTS.get() && event.getEntity().fallDistance < 6)
			event.setDamageMultiplier(0);
	}

	public float getIncreaseForUses(float uses) {
		int increase = 0;
		for (int level : this.getLevelCaps())
			if (uses >= level) increase += 1;
		return increase;
	}

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 1000, 5000, 10000, 50000};
	}

	public float levelUp(ItemStack stack, LivingEntity entity, float increase) {
		CompoundTag tag = stack.getOrCreateTag();
		float uses = tag.getFloat(this.getUsesTag());
		float level = this.getIncreaseForUses(uses);

		tag.putFloat(this.getUsesTag(), uses + increase);

		float newLevel = this.getIncreaseForUses(uses + increase);
		if (newLevel > level) this.playEffects(newLevel, entity);

		return newLevel;
	}
}
