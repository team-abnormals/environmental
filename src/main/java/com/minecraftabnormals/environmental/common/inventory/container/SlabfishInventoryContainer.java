package com.minecraftabnormals.environmental.common.inventory.container;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.inventory.SlabfishInventory;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SlabfishInventoryContainer extends Container {

	private static final ResourceLocation[] SLOT_INDEX_NAMES = {
			new ResourceLocation(Environmental.MOD_ID, "item/slabfish_sweater_slot"),
			new ResourceLocation(Environmental.MOD_ID, "item/slabfish_backpack_slot"),
			new ResourceLocation(Environmental.MOD_ID, "item/slabfish_backpack_type_slot")
	};

	private final IInventory slabfishInventory;
	private final SlabfishEntity slabfish;

	public SlabfishInventoryContainer(int windowId, PlayerInventory playerInventory, @Nullable SlabfishInventory slabfishInventory, @Nullable SlabfishEntity slabfish) {
		super(EnvironmentalContainers.SLABFISH_INVENTORY.get(), windowId);
		this.slabfishInventory = slabfishInventory;
		this.slabfish = slabfish;
		if (slabfishInventory != null && slabfish != null) {
			slabfishInventory.openInventory(playerInventory.player);
			for (int i = 0; i < 3; i++) {
				this.addSlot(new Slot(slabfishInventory, i, 8, 18 + i * 18) {
					@Override
					public boolean isItemValid(ItemStack stack) {
						return slabfishInventory.isItemValidForSlot(this.getSlotIndex(), stack);
					}

					@Override
					public int getSlotStackLimit() {
						return slabfishInventory.getSlotStackLimit(this.getSlotIndex());
					}

					@Override
					public boolean isEnabled() {
						if (this.getSlotIndex() != 2)
							return true;
						SlabfishManager slabfishManager = SlabfishManager.get(slabfish.getEntityWorld());
						SlabfishType slabfishType = slabfishManager.getSlabfishType(slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
						return slabfish.hasBackpack() && (slabfishType.getCustomBackpack() == null || !slabfishManager.getBackpackType(slabfishType.getCustomBackpack()).isPresent());
					}
				}).setBackground(PlayerContainer.LOCATION_BLOCKS_TEXTURE, SLOT_INDEX_NAMES[i]);
			}
			for (int k = 0; k < 3; ++k) {
				for (int l = 0; l < 5; ++l) {
					this.addSlot(new Slot(slabfishInventory, 3 + l + k * 5, 80 + l * 18, 18 + k * 18) {
						@Override
						public boolean isEnabled() {
							return slabfish.hasBackpack();
						}
					});
				}
			}
		}
		for (int i1 = 0; i1 < 3; ++i1) {
			for (int k1 = 0; k1 < 9; ++k1) {
				this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
			}
		}
		for (int j1 = 0; j1 < 9; ++j1) {
			this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
		}
	}

	public SlabfishInventoryContainer(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, null, null);
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return this.slabfishInventory.isUsableByPlayer(player) && this.slabfish.isAlive() && this.slabfish.getDistance(player) < 8.0F;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			itemstack = slotStack.copy();
			int i = this.slabfishInventory.getSizeInventory();
			if (index < i) {
				if (!this.mergeItemStack(slotStack, i, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(0).isItemValid(slotStack) && !this.getSlot(0).getHasStack() && !this.mergeItemStack(slotStack, 0, 1, false)) {
				return ItemStack.EMPTY;
			} else if (this.getSlot(1).isItemValid(slotStack) && !this.getSlot(1).getHasStack() && !this.mergeItemStack(slotStack, 1, 2, false)) {
				return ItemStack.EMPTY;
			} else if (this.getSlot(2).isItemValid(slotStack) && !this.getSlot(2).getHasStack() && !this.mergeItemStack(slotStack, 2, 3, false)) {
				return ItemStack.EMPTY;
			} else if (i <= 3 || !this.mergeItemStack(slotStack, 3, i, false)) {
				int j = i + 27;
				int k = j + 9;
				if (index >= j && index < k) {
					if (!this.mergeItemStack(slotStack, i, j, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= i && index < j) {
					if (!this.mergeItemStack(slotStack, j, k, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.mergeItemStack(slotStack, j, j, false)) {
					return ItemStack.EMPTY;
				}

				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(PlayerEntity player) {
		super.onContainerClosed(player);
		this.slabfish.playersUsing--;
		this.slabfishInventory.closeInventory(player);
	}
}
