package com.minecraftabnormals.environmental.common.item.explorer;

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

import javax.annotation.Nullable;
import java.util.List;

@EventBusSubscriber(modid = Environmental.MOD_ID)
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

	@SubscribeEvent
	public static void onEvent(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack stack = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);

		if (stack.getItem() instanceof HealerPouchItem && event.getSource().getTrueSource() instanceof LivingEntity) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
					CompoundNBT tag = stack.getOrCreateTag();
					int increase = ((HealerPouchItem) stack.getItem()).getIncreaseForUses(tag.getInt(NBT_TAG));
					int panicSeconds = 4 * increase;
					player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 20 * panicSeconds, increase > 2 ? 1 : 0));
					player.addPotionEffect(new EffectInstance(EnvironmentalEffects.PANIC.get(), 20 * panicSeconds, 0));
					player.getCooldownTracker().setCooldown(stack.getItem(), 20 * (panicSeconds + (10 - increase * 2)));
					((HealerPouchItem) stack.getItem()).levelUp(stack, player);
				}
			} else {
				if (entity.world.getRandom().nextInt(entity.world.getDifficulty().getId() + 3) != 0) {
					entity.heal(4.0F);
					entity.addPotionEffect(new EffectInstance(EnvironmentalEffects.PANIC.get(), 120, 0));
				}
			}
		}

	}

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public String getDescriptionString() {
		return "adrenaline boosts";
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 10, 50, 100, 250};
	}
}
