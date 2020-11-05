package com.minecraftabnormals.environmental.api;

import net.minecraft.item.Item;

public interface IEggLayingEntity {
	int getEggTimer();
	void setEggTimer(int time);
	boolean isBirdJockey();
	Item getEggItem();
}
