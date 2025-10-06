package com.teamabnormals.environmental.core.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Mule.class)
public abstract class MuleMixin extends AbstractChestedHorse {

	protected MuleMixin(EntityType<? extends Mule> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public int getInventoryColumns() {
		return this.hasChest() ? 3 : 0;
	}

	@Override
	public void containerChanged(Container container) {
		ItemStack itemstack = this.getBodyArmorItem();
		super.containerChanged(container);
		ItemStack itemstack1 = this.getBodyArmorItem();
		if (this.tickCount > 20 && this.isBodyArmorItem(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean canUseSlot(EquipmentSlot slot) {
		return true;
	}

	@Override
	public boolean isBodyArmorItem(ItemStack stack) {
		return stack.is(Items.LEATHER_HORSE_ARMOR);
	}
}