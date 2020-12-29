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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class YakPantsItem extends ArmorItem {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.TURTLE_HELMET);

	public YakPantsItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack legsStack = entity.getItemStackFromSlot(EquipmentSlotType.LEGS);
		boolean wearingPants = legsStack.getItem() instanceof YakPantsItem;
		if (entity instanceof PlayerEntity) {
			float defaultHeight = 0.6F;
			float upgradedHeight = 1.1F;
			if (wearingPants && entity.stepHeight < upgradedHeight) {
				entity.stepHeight = upgradedHeight;
			} else if (!ModList.get().isLoaded("step") && entity.stepHeight > defaultHeight) {
				entity.stepHeight = defaultHeight;
			}
		}

		if (wearingPants && entity.getRidingEntity() instanceof LivingEntity) {
			LivingEntity mount = (LivingEntity)entity.getRidingEntity();
			mount.addPotionEffect(new EffectInstance(Effects.REGENERATION, 60));
			mount.addPotionEffect(new EffectInstance(Effects.SPEED, 60, 1));
		}
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}
}
