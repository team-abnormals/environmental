package com.minecraftabnormals.environmental.common.inventory.container;

import com.google.common.collect.Lists;
import com.minecraftabnormals.environmental.common.item.crafting.SawingRecipe;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalContainers;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class SawmillContainer extends Container {
	private final IWorldPosCallable worldPosCallable;
	private final IntReferenceHolder selectedRecipe = IntReferenceHolder.standalone();
	private final World world;
	private List<SawingRecipe> recipes = Lists.newArrayList();
	private ItemStack itemStackInput = ItemStack.EMPTY;
	private long lastOnTake;
	final Slot inputInventorySlot;
	final Slot outputInventorySlot;
	private Runnable inventoryUpdateListener = () -> {
	};
	public final IInventory inputInventory = new Inventory(1) {
		public void setChanged() {
			super.setChanged();
			SawmillContainer.this.slotsChanged(this);
			SawmillContainer.this.inventoryUpdateListener.run();
		}
	};
	private final CraftResultInventory inventory = new CraftResultInventory();

	public SawmillContainer(int windowId, PlayerInventory playerInventory) {
		this(windowId, playerInventory, IWorldPosCallable.NULL);
	}

	public SawmillContainer(int windowId, PlayerInventory playerInventory, final IWorldPosCallable worldPos) {
		super(EnvironmentalContainers.SAWMILL.get(), windowId);
		this.worldPosCallable = worldPos;
		this.world = playerInventory.player.level;
		this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
		this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				ItemStack itemstack = SawmillContainer.this.inputInventorySlot.remove(1);
				if (!itemstack.isEmpty()) {
					SawmillContainer.this.updateRecipeResultSlot();
				}

				stack.getItem().onCraftedBy(stack, thePlayer.level, thePlayer);
				worldPos.execute((p_216954_1_, p_216954_2_) -> {
					long l = p_216954_1_.getGameTime();
					if (SawmillContainer.this.lastOnTake != l) {
						p_216954_1_.playSound(null, p_216954_2_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						SawmillContainer.this.lastOnTake = l;
					}

				});
				return super.onTake(thePlayer, stack);
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		this.addDataSlot(this.selectedRecipe);
	}

	public int getSelectedRecipe() {
		return this.selectedRecipe.get();
	}

	public List<SawingRecipe> getRecipeList() {
		return this.recipes;
	}

	public int getRecipeListSize() {
		return this.recipes.size();
	}

	public boolean hasItemsinInputSlot() {
		return this.inputInventorySlot.hasItem() && !this.recipes.isEmpty();
	}

	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(this.worldPosCallable, playerIn, EnvironmentalBlocks.SAWMILL.get());
	}

	public boolean clickMenuButton(PlayerEntity playerIn, int id) {
		if (this.isValidRecipeIndex(id)) {
			this.selectedRecipe.set(id);
			this.updateRecipeResultSlot();
		}

		return true;
	}

	private boolean isValidRecipeIndex(int p_241818_1_) {
		return p_241818_1_ >= 0 && p_241818_1_ < this.recipes.size();
	}

	public void slotsChanged(IInventory inventoryIn) {
		ItemStack itemstack = this.inputInventorySlot.getItem();
		if (itemstack.getItem() != this.itemStackInput.getItem()) {
			this.itemStackInput = itemstack.copy();
			this.updateAvailableRecipes(inventoryIn, itemstack);
		}

	}

	private void updateAvailableRecipes(IInventory inventoryIn, ItemStack stack) {
		this.recipes.clear();
		this.selectedRecipe.set(-1);
		this.outputInventorySlot.set(ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			this.recipes = this.world.getRecipeManager().getRecipesFor(EnvironmentalRecipes.RecipeTypes.SAWING, inventoryIn, this.world);
		}

	}

	private void updateRecipeResultSlot() {
		if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipe.get())) {
			SawingRecipe sawingrecipe = this.recipes.get(this.selectedRecipe.get());
			this.outputInventorySlot.set(sawingrecipe.assemble(this.inputInventory));
		} else {
			this.outputInventorySlot.set(ItemStack.EMPTY);
		}

		this.broadcastChanges();
	}

	public ContainerType<?> getType() {
		return EnvironmentalContainers.SAWMILL.get();
	}

	public void setInventoryUpdateListener(Runnable listenerIn) {
		this.inventoryUpdateListener = listenerIn;
	}

	public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
		return slotIn.container != this.inventory && super.canTakeItemForPickAll(stack, slotIn);
	}

	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			Item item = itemstack1.getItem();
			itemstack = itemstack1.copy();
			if (index == 1) {
				item.onCraftedBy(itemstack1, playerIn.level, playerIn);
				if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index == 0) {
				if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.world.getRecipeManager().getRecipeFor(EnvironmentalRecipes.RecipeTypes.SAWING, new Inventory(itemstack1), this.world).isPresent()) {
				if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 2 && index < 29) {
				if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}

			slot.setChanged();
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
			this.broadcastChanges();
		}

		return itemstack;
	}

	public void removed(PlayerEntity playerIn) {
		super.removed(playerIn);
		this.inventory.removeItemNoUpdate(1);
		this.worldPosCallable.execute((p_217079_2_, p_217079_3_) -> {
			this.clearContainer(playerIn, playerIn.level, this.inputInventory);
		});
	}
}