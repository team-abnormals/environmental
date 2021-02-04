package com.minecraftabnormals.environmental.common.item.explorer;

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

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MOD_ID)
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

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public String getDescriptionString() {
		return "blocks placed";
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 100, 500, 2500, 1000};
	}
}
