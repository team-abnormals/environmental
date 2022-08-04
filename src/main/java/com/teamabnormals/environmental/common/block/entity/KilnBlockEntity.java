package com.teamabnormals.environmental.common.block.entity;

import com.teamabnormals.environmental.common.inventory.KilnMenu;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlockEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity {

	public KilnBlockEntity(BlockPos pos, BlockState state) {
		super(EnvironmentalBlockEntityTypes.KILN.get(), pos, state, EnvironmentalRecipeTypes.BAKING.get());
	}

	protected Component getDefaultName() {
		return new TranslatableComponent("container.kiln");
	}

	protected int getBurnDuration(ItemStack fuel) {
		return super.getBurnDuration(fuel) / 2;
	}

	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new KilnMenu(id, player, this, this.dataAccess);
	}
}