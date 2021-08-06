package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class YakPantsItem extends ArmorItem {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.TURTLE_HELMET);
	private static final Set<UUID> APPLIED_UUIDS = new HashSet<>();

	public YakPantsItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack legsStack = entity.getItemBySlot(EquipmentSlotType.LEGS);
		boolean wearingPants = legsStack.getItem() instanceof YakPantsItem;

		if (entity instanceof PlayerEntity) {
			UUID entityUUID = entity.getUUID();
			float defaultHeight = 0.6F;
			float upgradedHeight = 1.0F;
			if (wearingPants && entity.maxUpStep < upgradedHeight) {
				entity.maxUpStep = upgradedHeight;
				APPLIED_UUIDS.add(entityUUID);
			} else if (APPLIED_UUIDS.contains(entityUUID) && entity.maxUpStep > defaultHeight) {
				entity.maxUpStep = defaultHeight;
				APPLIED_UUIDS.remove(entityUUID);
			}
		}

		if (wearingPants && entity.getVehicle() instanceof LivingEntity) {
			LivingEntity mount = (LivingEntity) entity.getVehicle();
			mount.addEffect(new EffectInstance(Effects.REGENERATION, 60));
			mount.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 60, 1));
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}
}
