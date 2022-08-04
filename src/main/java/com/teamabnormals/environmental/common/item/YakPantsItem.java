package com.teamabnormals.environmental.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class YakPantsItem extends ArmorItem {
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.TURTLE_HELMET);
	private static final Set<UUID> APPLIED_UUIDS = new HashSet<>();

	public YakPantsItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack legsStack = entity.getItemBySlot(EquipmentSlot.LEGS);
		boolean wearingPants = legsStack.getItem() instanceof YakPantsItem;

		if (entity instanceof Player) {
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
			mount.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60));
			mount.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}
}
