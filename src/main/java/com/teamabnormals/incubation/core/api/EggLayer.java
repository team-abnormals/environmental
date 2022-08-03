package com.teamabnormals.incubation.core.api;

import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import java.util.Random;

public interface EggLayer {
	int getEggTimer();

	void setEggTimer(int time);

	boolean isBirdJockey();

	Item getEggItem();

	int getNextEggTime(Random rand);

	SoundEvent getEggLayingSound();
}