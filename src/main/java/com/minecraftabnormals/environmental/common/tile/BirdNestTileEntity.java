package com.minecraftabnormals.environmental.common.tile;

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

import javax.annotation.Nonnull;

public class BirdNestTileEntity extends TileEntity implements ITickableTileEntity {

	public BirdNestTileEntity() {
		super(EnvironmentalTileEntities.BIRD_NEST.get());
	}

	public void tick() {
		if (this.level != null && !this.level.isClientSide) {
			BlockState blockstate = this.level.getBlockState(this.worldPosition);

			int i = blockstate.getValue(BirdNestBlock.EGGS);
			BlockPos blockpos = worldPosition.below();
			BirdNestBlock block = (BirdNestBlock) blockstate.getBlock();

			if (this.level.getBlockState(blockpos).hasTileEntity()) {
				TileEntity tileentity = this.level.getBlockEntity(blockpos);
				if (tileentity instanceof HopperTileEntity) {
					if (!((HopperTileEntity) tileentity).isOnCooldown() && insertEggToHopper(tileentity, new ItemStack(block.getEgg()))) {
						if (i > 1)
							this.level.setBlock(this.worldPosition, blockstate.setValue(BirdNestBlock.EGGS, Integer.valueOf(i - 1)), 2);
						else
							this.level.setBlock(this.worldPosition, block.getEmptyNest().defaultBlockState(), 2);
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

		if (!hopper.isOnCustomCooldown()) {
			hopper.setCooldown(8);
		}

		return true;
	}
}