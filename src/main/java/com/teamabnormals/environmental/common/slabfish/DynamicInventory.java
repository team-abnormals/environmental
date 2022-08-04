package com.teamabnormals.environmental.common.slabfish;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>A dynamic {@link IInventory} implementation.</p>
 *
 * @author Ocelot
 * @since 3.1.0
 */
public abstract class DynamicInventory implements Container {
	private final Int2ObjectOpenHashMap<ItemStack> inventory;
	private Set<ContainerListener> listeners;

	public DynamicInventory() {
		this.inventory = new Int2ObjectOpenHashMap<>();
		this.listeners = null;
	}

	private int getNextEmptySlot(ItemStack stack, int loopStart, int loopEnd) {
		for (int i = loopStart; i < loopEnd; i++)
			if (this.canPlaceItem(i, stack) && this.inventory.getOrDefault(i, ItemStack.EMPTY).isEmpty())
				return i;
		return -1;
	}

	private void mergeStacks(ItemStack stack, ItemStack stackInSlot, int index) {
		int maxStackSize = Math.min(this.getSlotStackLimit(index), stackInSlot.getMaxStackSize());
		int addAmount = Math.min(stack.getCount(), maxStackSize - stackInSlot.getCount());
		if (addAmount > 0) {
			stackInSlot.grow(addAmount);
			stack.shrink(addAmount);
		}
	}

	/**
	 * Adds the specified inventory changed listener.
	 *
	 * @param listener The new listener to add
	 */
	public void addListener(ContainerListener listener) {
		if (this.listeners == null)
			this.listeners = new HashSet<>();
		this.listeners.add(listener);
	}

	/**
	 * Removes the specified listener if already added.
	 *
	 * @param listener The listener to remove
	 */
	public void removeListener(ContainerListener listener) {
		if (this.listeners == null)
			return;
		this.listeners.remove(listener);
		if (this.listeners.isEmpty())
			this.listeners = null;
	}

	/**
	 * Adds the specified item to the inventory.
	 *
	 * @param stack The stack to add
	 * @return The remaining items that could not be inserted into the inventory or {@link ItemStack#EMPTY} if the merge was successful
	 */
	public ItemStack addItem(ItemStack stack) {
		return this.addItem(stack, 0, this.getContainerSize());
	}

	/**
	 * Adds the specified item to the inventory from the starting index to the ending index.
	 *
	 * @param stack      The stack to add
	 * @param startIndex The index to start adding items from
	 * @param endIndex   The index to end adding items to
	 * @return The remaining items that could not be inserted into the inventory or {@link ItemStack#EMPTY} if the merge was successful
	 */
	public ItemStack addItem(ItemStack stack, int startIndex, int endIndex) {
		int loopStart = Math.max(0, startIndex);
		int loopEnd = Math.min(this.getContainerSize(), endIndex);
		ItemStack copy = stack.copy();
		for (int i = loopStart; i < loopEnd; i++) {
			ItemStack stackInSlot = this.getItem(i);
			if (this.canPlaceItem(i, stack) && ItemStack.isSame(stackInSlot, copy) && ItemStack.tagMatches(stackInSlot, copy)) {
				this.mergeStacks(copy, stackInSlot, i);
				if (copy.isEmpty()) {
					this.setChanged();
					return ItemStack.EMPTY;
				}
			}
		}

		int index;
		while (!copy.isEmpty() && (index = this.getNextEmptySlot(copy, loopStart, loopEnd)) >= 0) {
			if (copy.getCount() > this.getSlotStackLimit(index)) {
				this.inventory.put(index, copy.split(this.getSlotStackLimit(index)));
			} else {
				this.inventory.put(index, copy.copy());
				copy = ItemStack.EMPTY;
			}
		}

		if (!ItemStack.matches(stack, copy))
			this.setChanged();

		return copy;
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}

	@Override
	public ItemStack getItem(int index) {
		return this.inventory.getOrDefault(index, ItemStack.EMPTY);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		if (this.inventory.getOrDefault(index, ItemStack.EMPTY).isEmpty() || count <= 0)
			return ItemStack.EMPTY;
		ItemStack stack = this.inventory.getOrDefault(index, ItemStack.EMPTY).split(count);
		this.setChanged();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		if (this.inventory.getOrDefault(index, ItemStack.EMPTY).isEmpty())
			return ItemStack.EMPTY;
		ItemStack stack = this.inventory.remove(index);
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.setChanged();
			return stack;
		}
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		if (index < 0 || index >= this.getContainerSize())
			return;
		this.inventory.put(index, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getSlotStackLimit(index))
			stack.setCount(this.getSlotStackLimit(index));
		this.setChanged();
	}

	@Override
	public void setChanged() {
		if (this.listeners == null)
			return;
		for (ContainerListener listener : this.listeners)
			listener.containerChanged(this);
	}

	/**
	 * Same as {@link IInventory#getInventoryStackLimit()} but used to determine if a stack can be added on a per slot basis.
	 *
	 * @param index The index to get the stack limit for
	 * @return The limit for the specified stack
	 */
	public int getSlotStackLimit(int index) {
		return this.getMaxStackSize();
	}

	@Override
	public void clearContent() {
		this.inventory.clear();
	}

	/**
	 * Writes the contents of this inventory to NBT.
	 *
	 * @param nbt The tag to write into
	 */
	public void write(CompoundTag nbt) {
		ListTag list = new ListTag();
		for (int i = 0; i < this.getContainerSize(); ++i) {
			ItemStack stack = this.inventory.getOrDefault(i, ItemStack.EMPTY);
			if (!stack.isEmpty()) {
				CompoundTag slotNbt = new CompoundTag();
				slotNbt.putByte("Slot", (byte) i);
				stack.save(slotNbt);
				list.add(slotNbt);
			}
		}
		nbt.put("Items", list);
	}

	/**
	 * Reads the contents of this inventory from NBT.
	 *
	 * @param nbt The tag to read from
	 */
	public void read(CompoundTag nbt) {
		this.clearContent();

		ListTag list = nbt.getList("Items", 10);
		for (int i = 0; i < list.size(); ++i) {
			CompoundTag slotNbt = list.getCompound(i);
			int index = slotNbt.getByte("Slot") & 255;
			if (index < this.getContainerSize()) {
				this.inventory.put(index, ItemStack.of(slotNbt));
			}
		}
	}
}
