package com.minecraftabnormals.environmental.api;

import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import java.util.Random;

public interface IExplorerArmorItem {
	String getUsesTag();
	String getDescriptionString();
	int getIncreaseForUses(int uses);
	int[] getLevelCaps();
}
