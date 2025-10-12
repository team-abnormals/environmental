package com.teamabnormals.environmental.common.inventory;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.DynamicInventory;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

/**
 * <p>An implementation of {@link DynamicInventory} for {@link Slabfish}.</p>
 *
 * @author Ocelot
 */
public class SlabfishInventory extends DynamicInventory {
	private final Slabfish slabfish;

	public SlabfishInventory(Slabfish slabfish) {
		super(slabfish.registryAccess());
		this.slabfish = slabfish;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		switch (index) {
			case 0:
				return SlabfishHelper.getSweaterType(SlabfishHelper.slabfishSweaters(this.slabfish.registryAccess()), stack).isPresent();
			case 1:
				return stack.is(Tags.Items.CHESTS_WOODEN);
			case 2:
				if (SlabfishHelper.getBackpackType(SlabfishHelper.slabfishBackpacks(this.slabfish.registryAccess()), stack).isEmpty())
					return false;
				return this.slabfish.hasBackpack();
			default:
				return super.canPlaceItem(index, stack);
		}
	}

	@Override
	public int getSlotStackLimit(int index) {
		return index < 3 ? 1 : super.getSlotStackLimit(index);
	}

	@Override
	public int getContainerSize() {
		return 3 + (this.slabfish.hasBackpack() || this.getItem(1).is(Tags.Items.CHESTS_WOODEN) ? 15 : 0);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.slabfish.isAlive() && player.distanceToSqr(this.slabfish) <= 64.0;
	}
}
