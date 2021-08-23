package com.minecraftabnormals.environmental.api;

import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import java.util.Random;

public interface IEggLayingEntity {
	int getEggTimer();

	void setEggTimer(int time);

	boolean isBirdJockey();

	Item getEggItem();

	int getNextEggTime(Random rand);

	SoundEvent getEggLayingSound();
}
