package com.minecraftabnormals.environmental.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.ArchitectBeltModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class ArchitectBeltItem extends ExplorerArmorItem {
	private static final String NBT_TAG = "ArchitectBeltUses";
	
	public ArchitectBeltItem(Properties properties) {
		super(EquipmentSlotType.LEGS, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return ArchitectBeltModel.get(1.0F);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(this.getEquipmentSlot()));
		UUID uuid = UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D");
		
		int uses = stack.getTag().getInt(NBT_TAG);
		int increase = getIncreaseForUses(uses);
		
		builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(uuid, "Reach modifier", increase, AttributeModifier.Operation.ADDITION));

		return slot == this.slot ? builder.build() : super.getAttributeModifiers(slot);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();
		int uses = compoundnbt.getInt(NBT_TAG);
		tooltip.add((new StringTextComponent(Integer.toString(uses) + " blocks placed")).mergeStyle(TextFormatting.GRAY));
	}
	
	@SubscribeEvent
    public static void placeBlockEvent(BlockEvent.EntityPlaceEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
			if (stack.getItem() == EnvironmentalItems.ARCHITECT_BELT.get()) {
				int uses = stack.getOrCreateTag().getInt(NBT_TAG);
				stack.getOrCreateTag().putInt(NBT_TAG, uses + 1);
			}
		}
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
