package com.teamabnormals.environmental.common.item.explorer;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.EnvironmentalTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ExplorerArmorItem extends DyeableArmorItem implements ExplorerArmor {

	public ExplorerArmorItem(EquipmentSlot slot, Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, slot, properties);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return getCorrectTexture(ForgeRegistries.ITEMS.getKey(this).getPath(), type);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		if (stack.getTag() != null && this.getIncreaseForUses(this.getUses(stack.getTag())) == 5) {
			if (player instanceof ServerPlayer serverplayerentity) {
				if (!world.isClientSide()) {
					EnvironmentalCriteriaTriggers.UPGRADE_GEAR.trigger(serverplayerentity, this);
				}
			}
		}
	}

	public static String getCorrectTexture(String armor, String type) {
		if ("overlay".equals(type)) return Environmental.MOD_ID + ":textures/models/armor/" + armor + "_overlay.png";
		else return Environmental.MOD_ID + ":textures/models/armor/" + armor + ".png";
	}

	@Override
	public int getColor(ItemStack stack) {
		if (!hasCustomColor(stack))
			return 0x77533F;
		return super.getColor(stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag nbt = stack.getOrCreateTag();
		int uses = this.getUses(nbt);
		int nextLevel = this.getLevelsUntilUpgrade(uses);

		MutableComponent counter;
		if (nextLevel != 0) counter = Component.literal(uses + "/" + nextLevel);
		else counter = Component.literal(String.valueOf(uses));
		counter.withStyle(getFormattingForLevel(uses));

		MutableComponent description = Component.literal(" ").append(this.getDescription()).withStyle(ChatFormatting.GRAY);
		tooltip.add(counter.append(description));
	}

	public int getIncreaseForUses(int uses) {
		int increase = 0;
		for (int level : this.getLevelCaps())
			if (uses >= level) increase += 1;
		return increase;
	}

	public int getLevelsUntilUpgrade(int uses) {
		int[] levels = this.getLevelCaps();
		int increase = this.getIncreaseForUses(uses);
		if (levels.length > increase)
			return levels[increase];
		else return 0;
	}

	public int getUses(CompoundTag nbt) {
		return nbt.getInt(this.getUsesTag());
	}

	public MutableComponent getDescription() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	public ChatFormatting getFormattingForLevel(int uses) {
		ChatFormatting[] formatting = new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.GOLD};
		return formatting[this.getIncreaseForUses(uses) - 1];
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		boolean maxed = this.getIncreaseForUses(this.getUses(stack.getOrCreateTag())) == 5;
		if (stack.isEnchanted()) {
			if (maxed) return Rarity.EPIC;
			else return Rarity.RARE;
		} else {
			if (maxed) return Rarity.RARE;
			else return Rarity.COMMON;
		}
	}
}