package com.teamabnormals.environmental.common.inventory;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.DynamicInventory;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

/**
 * <p>An implementation of {@link DynamicInventory} for {@link Slabfish}.</p>
 *
 * @author Ocelot
 */
public class SlabfishInventory extends DynamicInventory {
	private final Slabfish slabfish;

	public SlabfishInventory(Slabfish slabfish) {
		this.slabfish = slabfish;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		switch (index) {
			case 0:
				return SlabfishManager.get(this.slabfish.getCommandSenderWorld()).getSweaterType(stack).isPresent();
			case 1:
				return stack.is(Tags.Items.CHESTS_WOODEN);
			case 2:
				SlabfishManager slabfishManager = SlabfishManager.get(this.slabfish.getCommandSenderWorld());
				if (!slabfishManager.getBackpackType(stack).isPresent())
					return false;
				SlabfishType slabfishType = slabfishManager.getSlabfishType(this.slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
				return this.slabfish.hasBackpack() && (slabfishType.getCustomBackpack() == null || !slabfishManager.getBackpackType(slabfishType.getCustomBackpack()).isPresent());
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
