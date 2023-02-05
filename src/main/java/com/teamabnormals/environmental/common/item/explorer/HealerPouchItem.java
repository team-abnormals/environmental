package com.teamabnormals.environmental.common.item.explorer;

import com.teamabnormals.environmental.client.model.HealerPouchModel;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalMobEffects;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.function.Consumer;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class HealerPouchItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "HealerPouchUses";

	public HealerPouchItem(Properties properties) {
		super(EquipmentSlot.CHEST, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> properties) {
				return HealerPouchModel.INSTANCE;
			}
		});
	}

	@SubscribeEvent
	public static void onEvent(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);

		if (stack.getItem() instanceof HealerPouchItem && event.getSource().getEntity() instanceof LivingEntity) {
			if (entity instanceof Player player) {
				if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
					CompoundTag tag = stack.getOrCreateTag();
					int increase = ((HealerPouchItem) stack.getItem()).getIncreaseForUses(tag.getInt(NBT_TAG));
					int panicSeconds = 4 * increase;
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * panicSeconds, increase > 2 ? 1 : 0));
					player.addEffect(new MobEffectInstance(EnvironmentalMobEffects.PANIC.get(), 20 * panicSeconds, 0));
					player.getCooldowns().addCooldown(stack.getItem(), 20 * (panicSeconds + (10 - increase * 2)));
					((HealerPouchItem) stack.getItem()).levelUp(stack, player);
				}
			} else {
				if (entity.level.getRandom().nextInt(entity.level.getDifficulty().getId() + 3) != 0) {
					entity.heal(4.0F);
					entity.addEffect(new MobEffectInstance(EnvironmentalMobEffects.PANIC.get(), 120, 0));
				}
			}
		}
	}

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 10, 50, 100, 250};
	}
}
