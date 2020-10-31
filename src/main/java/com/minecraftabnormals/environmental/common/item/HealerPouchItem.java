package com.minecraftabnormals.environmental.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.client.model.HealerPouchModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class HealerPouchItem extends ArmorItem {
	private static final String NBT_TAG = "HealerPouchUses";
	
	public HealerPouchItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.CHEST, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return HealerPouchModel.get(1.0F);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/healer_pouch.png";
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();
		int uses = compoundnbt.getInt(NBT_TAG);
		tooltip.add((new StringTextComponent(Integer.toString(uses) + " hearts healed")).mergeStyle(TextFormatting.GRAY));
	}
	
	public static int getIncreaseForUses(int uses) {
		int increase = 1;
		if (uses >= 100)
			increase += 1;
		if (uses >= 500)
			increase += 1;
		if (uses >= 2500)
			increase += 1;
		if (uses >= 10000)
			increase += 1;
		return increase;
	}
}
