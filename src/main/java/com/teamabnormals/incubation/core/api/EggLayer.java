package com.teamabnormals.incubation.core.api;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import java.util.Random;

//TODO: Remove so the mod works lol
public interface EggLayer {
	int getEggTimer();

	void setEggTimer(int time);

	boolean isBirdJockey();

	Item getEggItem();

	int getNextEggTime(Random rand);

	SoundEvent getEggLayingSound();
}