package com.minecraftabnormals.environmental.common.inventory;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.DynamicInventory;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

/**
 * <p>An implementation of {@link DynamicInventory} for {@link SlabfishEntity}.</p>
 *
 * @author Ocelot
 */
public class SlabfishInventory extends DynamicInventory {
    private final SlabfishEntity slabfish;

    public SlabfishInventory(SlabfishEntity slabfish) {
        this.slabfish = slabfish;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index) {
            case 0:
                return SlabfishManager.get(this.slabfish.getEntityWorld()).hasSweaterType(stack);
            case 1:
                return stack.getItem().isIn(Tags.Items.CHESTS_WOODEN);
            case 2:
                SlabfishManager slabfishManager = SlabfishManager.get(this.slabfish.getEntityWorld());
                if (!slabfishManager.hasBackpackType(stack))
                    return false;
                SlabfishType slabfishType = slabfishManager.getSlabfishType(this.slabfish.getSlabfishType());
                return this.slabfish.hasBackpack() && (slabfishType.getCustomBackpack() == null || !slabfishManager.hasBackpackType(slabfishType.getCustomBackpack()));
            default:
                return super.isItemValidForSlot(index, stack);
        }
    }

    @Override
    public int getSlotStackLimit(int index) {
        return index < 3 ? 1 : super.getSlotStackLimit(index);
    }

    @Override
    public int getSizeInventory() {
        return 3 + (this.slabfish.hasBackpack() ? 15 : 0);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.slabfish.isAlive() && player.getDistanceSq(this.slabfish) <= 64.0;
    }
}
