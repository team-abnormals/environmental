package com.minecraftabnormals.environmental.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.client.model.HealerPouchModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEffects;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class HealerPouchItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "HealerPouchUses";
	
	public HealerPouchItem(Properties properties) {
		super(EquipmentSlotType.CHEST, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return HealerPouchModel.get(1.0F);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();
		int uses = compoundnbt.getInt(NBT_TAG);
		tooltip.add((new StringTextComponent(uses + " adrenaline boosts")).mergeStyle(TextFormatting.GRAY));
	}
	
	@SubscribeEvent
	public static void onEvent(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack stack = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);

		if (stack.getItem() instanceof HealerPouchItem && event.getSource().getTrueSource() instanceof LivingEntity) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
					CompoundNBT tag = stack.getOrCreateTag();
					int increase = getIncreaseForUses(tag.getInt(NBT_TAG));
					int panicSeconds = 4 * increase;
					player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 20 * panicSeconds, increase > 2 ? 1 : 0));
					player.addPotionEffect(new EffectInstance(EnvironmentalEffects.PANIC.get(), 20 * panicSeconds, 0));
					player.getCooldownTracker().setCooldown(stack.getItem(), 20 * (panicSeconds + (10 - increase * 2)));
					tag.putInt(NBT_TAG, tag.getInt(NBT_TAG) + 1);
				}
			} else {
				if (entity.world.getRandom().nextInt(entity.world.getDifficulty().getId() + 3) != 0) {
					entity.heal(4.0F);
					entity.addPotionEffect(new EffectInstance(EnvironmentalEffects.PANIC.get(), 120, 0));
				}
			}
		}
		
	}
	
	public static int getIncreaseForUses(int uses) {
		int increase = 1;
		if (uses >= 10)
			increase += 1;
		if (uses >= 50)
			increase += 1;
		if (uses >= 100)
			increase += 1;
		if (uses >= 250)
			increase += 1;
		return increase;
	}
}
