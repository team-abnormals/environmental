package com.minecraftabnormals.environmental.common.item.explorer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.client.model.ArchitectBeltModel;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
		builder.putAll(super.getDefaultAttributeModifiers(this.getSlot()));
		UUID uuid = UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D");

		int uses = stack.getTag().getInt(NBT_TAG);
		int increase = getIncreaseForUses(uses);

		builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(uuid, "Reach modifier", increase, AttributeModifier.Operation.ADDITION));

		return slot == this.slot ? builder.build() : super.getDefaultAttributeModifiers(slot);
	}

	@SubscribeEvent
	public static void placeBlockEvent(BlockEvent.EntityPlaceEvent event) {
		Entity entity = event.getEntity();
		BlockState state = event.getState();
		if (entity instanceof PlayerEntity && !state.is(Blocks.FROSTED_ICE)) {
			PlayerEntity player = (PlayerEntity) entity;
			ItemStack stack = player.getItemBySlot(EquipmentSlotType.LEGS);
			Item item = stack.getItem();
			if (item instanceof ArchitectBeltItem) {
				((ArchitectBeltItem) item).levelUp(stack, player);
			}
		}
	}

	@Override
	public String getUsesTag() {
		return NBT_TAG;
	}

	@Override
	public int[] getLevelCaps() {
		return new int[]{0, 100, 500, 1000, 2500};
	}
}
