package com.team_abnormals.environmental.common.inventory.container;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.core.registry.EnvironmentalContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlabfishInventoryContainer extends Container {
    private final IInventory slabfishInventory;
    private final SlabfishEntity slabfish;

    public SlabfishInventoryContainer(int windowId, PlayerInventory playerInventory, @Nullable IInventory slabfishInventory, @Nullable SlabfishEntity slabfish) {
        super(EnvironmentalContainerTypes.SLABFISH_INVENTORY.get(), windowId);
        this.slabfishInventory = slabfishInventory;
        this.slabfish = slabfish;
        if (slabfishInventory != null && slabfish != null) {
            slabfishInventory.openInventory(playerInventory.player);
            this.addSlot(new Slot(slabfishInventory, 0, 8, 18) {
                /**
                 * Check if the stack is allowed to be placed in this slot
                 */
                public boolean isItemValid(ItemStack stack) {
                    return SlabfishEntity.getSweaterMap().containsKey(stack.getItem()) && !this.getHasStack();
                }

                @Override
                public int getSlotStackLimit() {
                    return 1;
                }
            });
            if (slabfish.hasBackpack()) {
                for (int k = 0; k < 3; ++k) {
                    for (int l = 0; l < slabfish.getInventoryColumns(); ++l) {
                        this.addSlot(new Slot(slabfishInventory, 1 + l + k * slabfish.getInventoryColumns(), 80 + l * 18, 18 + k * 18));
                    }
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
            } else if (this.getSlot(0).isItemValid(slotStack)) {
                if (!this.mergeItemStack(slotStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 1 || !this.mergeItemStack(slotStack, 1, i, false)) {
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
