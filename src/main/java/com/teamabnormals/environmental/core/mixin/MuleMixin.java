package com.teamabnormals.environmental.core.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(Mule.class)
public abstract class MuleMixin extends AbstractChestedHorse {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("4650ED0E-29A0-4A33-A975-76AE1B1D743C");

	protected MuleMixin(EntityType<? extends Mule> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected int getInventorySize() {
		return this.hasChest() ? 11 : 2;
	}

	@Override
	public int getInventoryColumns() {
		return 3;
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}

	private void setArmor(ItemStack stack) {
		this.setItemSlot(EquipmentSlot.CHEST, stack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0F);
	}

	@Override
	protected void updateContainerEquipment() {
		if (!this.level().isClientSide) {
			super.updateContainerEquipment();
			this.setArmorEquipment(this.inventory.getItem(1));
			this.setDropChance(EquipmentSlot.CHEST, 0.0F);
		}
	}

	private void setArmorEquipment(ItemStack stack) {
		this.setArmor(stack);
		if (!this.level().isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isArmor(stack)) {
				int i = ((HorseArmorItem) stack.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

	@Override
	public void containerChanged(Container container) {
		ItemStack itemstack = this.getArmor();
		super.containerChanged(container);
		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean canWearArmor() {
		return true;
	}

	@Override
	public boolean isArmor(ItemStack stack) {
		return stack.is(Items.LEATHER_HORSE_ARMOR);
	}
}