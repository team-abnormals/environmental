package com.minecraftabnormals.environmental.common.tile;

import javax.annotation.Nonnull;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalTileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.VanillaHopperItemHandler;

public class BirdNestTileEntity extends TileEntity implements ITickableTileEntity {
	
	public BirdNestTileEntity() {
		super(EnvironmentalTileEntities.BIRD_NEST.get());
	}
	
	public void tick() {
		if (this.world != null && !this.world.isRemote) {
			BlockState blockstate = this.world.getBlockState(this.pos);
			
			int i = blockstate.get(BirdNestBlock.EGGS);
			BlockPos blockpos = pos.down();
			BirdNestBlock block = (BirdNestBlock) blockstate.getBlock();
			
			if (this.world.getBlockState(blockpos).hasTileEntity()) {
				TileEntity tileentity = this.world.getTileEntity(blockpos);
				if (tileentity instanceof HopperTileEntity) {
					if (!((HopperTileEntity)tileentity).isOnTransferCooldown() && insertEggToHopper(tileentity, new ItemStack(block.getEgg()))) {
						if (i > 1)
							this.world.setBlockState(this.pos, blockstate.with(BirdNestBlock.EGGS, Integer.valueOf(i - 1)), 2);
						else
							this.world.setBlockState(this.pos, block.getEmptyNest().getDefaultState(), 2);
					}
				}
			}
		}
	}
	
	private static boolean insertEggToHopper(TileEntity tileentity, @Nonnull ItemStack stack) {
		HopperTileEntity hopper = (HopperTileEntity) tileentity;
		VanillaHopperItemHandler inventory = new VanillaHopperItemHandler(hopper);
		
		if (!stack.isEmpty()) {
			stack = ItemHandlerHelper.insertItemStacked(inventory, stack, false);
		}

		if (!stack.isEmpty()) {
			return false;
		}

		if (!hopper.mayTransfer()) {
			hopper.setTransferCooldown(8);
		}

		return true;
	}
}