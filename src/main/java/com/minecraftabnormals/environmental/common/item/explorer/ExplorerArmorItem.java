package com.minecraftabnormals.environmental.common.item.explorer;

import com.minecraftabnormals.environmental.api.IExplorerArmorItem;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ExplorerArmorItem extends DyeableArmorItem implements IExplorerArmorItem {

	public ExplorerArmorItem(EquipmentSlotType slot, Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, slot, properties);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return getCorrectTexture(this.getRegistryName().getPath(), type);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (stack.getTag() != null && this.getIncreaseForUses(this.getUses(stack.getTag())) == 5) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
				if (!world.isRemote()) {
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
		if (!hasColor(stack))
			return 0x77533F;
		return super.getColor(stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT nbt = stack.getOrCreateTag();
		int uses = this.getUses(nbt);
		int nextLevel = this.getLevelsUntilUpgrade(uses);

		IFormattableTextComponent counter;
		if (nextLevel != 0) counter = new StringTextComponent(uses + "/" + nextLevel);
		else counter = new StringTextComponent(String.valueOf(uses));
		counter.mergeStyle(getFormattingForLevel(uses));

		IFormattableTextComponent description = new StringTextComponent(" ").append(this.getDescription()).mergeStyle(TextFormatting.GRAY);
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

	public int getUses(CompoundNBT nbt) {
		return nbt.getInt(this.getUsesTag());
	}

	public IFormattableTextComponent getDescription() {
		return new TranslationTextComponent(this.getTranslationKey() + ".desc");
	}

	public TextFormatting getFormattingForLevel(int uses) {
		TextFormatting[] formatting = new TextFormatting[]{TextFormatting.DARK_GRAY, TextFormatting.GREEN, TextFormatting.BLUE, TextFormatting.LIGHT_PURPLE, TextFormatting.GOLD};
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