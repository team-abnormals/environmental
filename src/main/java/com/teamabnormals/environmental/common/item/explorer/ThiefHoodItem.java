package com.teamabnormals.environmental.common.item.explorer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.teamabnormals.environmental.client.model.ThiefHoodModel;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalAttributes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingVisibilityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class ThiefHoodItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "ThiefHoodUses";

	public ThiefHoodItem(Properties properties) {
		super(EquipmentSlot.HEAD, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> properties) {
				return ThiefHoodModel.INSTANCE;
			}
		});
	}

	@Override
	public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
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
		if (event.getTo().is(EnvironmentalItems.THIEF_HOOD.get()) || event.getFrom().is(EnvironmentalItems.THIEF_HOOD.get())) {
			if (event.getEntity() instanceof Player player) {
				player.refreshDisplayName();
			}
		}
	}

	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (event.getSource().getEntity() instanceof LivingEntity attacker && entity instanceof Enemy) {
			ItemStack stack = attacker.getItemBySlot(EquipmentSlot.HEAD);
			if (stack.getItem() instanceof ThiefHoodItem thiefHoodItem) {
				thiefHoodItem.levelUp(stack, attacker);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingVisibility(LivingVisibilityEvent event) {
		if (event.getLookingEntity() != null) {
			double attributeValue = 0.0D;
			for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
				ItemStack stack = event.getEntity().getItemBySlot(equipmentSlot);
				Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(equipmentSlot).get(EnvironmentalAttributes.STEALTH.get());
				if (!modifiers.isEmpty()) {
					attributeValue += modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
				}
			}

			if (attributeValue > 0.0D) {
				event.modifyVisibility(Math.max(1.0D - attributeValue, 0.0D));
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
