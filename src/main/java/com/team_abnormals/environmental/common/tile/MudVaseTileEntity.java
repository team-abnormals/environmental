package com.team_abnormals.environmental.common.tile;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.core.registry.EnvironmentalTileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MudVaseTileEntity extends LockableLootTileEntity {
	private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public MudVaseTileEntity() {
		super(EnvironmentalTileEntities.MUD_VASE.get());
	}

    public CompoundNBT write(CompoundNBT compound) {
    	super.write(compound);
    	if (!this.checkLootAndWrite(compound)) {
           ItemStackHelper.saveAllItems(compound, this.inventory);
        }

        return compound;
     }

     public void func_230337_a_(BlockState state, CompoundNBT compound) {
        super.func_230337_a_(state, compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
           ItemStackHelper.loadAllItems(compound, this.inventory);
        }

     }

     public int getSizeInventory() {
        return 1;
     }

     protected NonNullList<ItemStack> getItems() {
        return this.inventory;
     }

     protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.inventory = itemsIn;
     }

     protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.mud_vase");
     }

     protected Container createMenu(int id, PlayerInventory player) {
        return null;
     }
     
     public static int calcRedstone(@Nullable TileEntity te) {
         return te instanceof IInventory ? calcRedstoneFromInventory((IInventory)te) : 0;
     }
     
     public static int calcRedstoneFromInventory(@Nullable IInventory inv) {
         if (inv == null) {
            return 0;
         } else {
             ItemStack itemstack = inv.getStackInSlot(0);
             if (itemstack == ItemStack.EMPTY) {
            	 return 0;
             } else {
            	 return 5;
             }

         }
      }
     
     public boolean addItem(ItemStack itemStackIn) {
    	 ItemStack itemstack = this.inventory.get(0);
    	 if (itemstack.isEmpty()) {
    		 this.inventory.set(0, itemStackIn.split(1));
    		 this.inventoryChanged();
    		 return true;
    	 }
         return false;
      }
     
     public boolean removeItem(PlayerEntity player, Hand hand) {
    	 ItemStack itemstack = this.inventory.get(0);
    	 if (!itemstack.isEmpty()) {
    		 this.inventory.set(0, ItemStack.EMPTY);
    		 this.inventoryChanged();
    		 player.setHeldItem(hand, itemstack);
    		 return true;
    	 }
         return false;
      }

      private void inventoryChanged() {
         this.markDirty();
         this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
      }

      public void clear() {
         this.inventory.clear();
      }
      
      public NonNullList<ItemStack> getInventory() {
          return this.inventory;
      }

      public void dropAllItems() {
    	  if (!this.getWorld().isRemote) {
    		  InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
    	  }

    	  this.inventoryChanged();
      }
}
