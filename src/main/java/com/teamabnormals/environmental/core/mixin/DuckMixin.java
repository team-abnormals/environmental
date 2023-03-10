package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.entity.animal.Duck;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import com.teamabnormals.incubation.core.api.EggLayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Duck.class)
public abstract class DuckMixin extends Animal implements EggLayer {

	@Shadow
	public int timeUntilNextEgg;

	@Shadow
	public boolean duckJockey;

	protected DuckMixin(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public int getEggTimer() {
		return this.timeUntilNextEgg;
	}

	@Override
	public void setEggTimer(int time) {
		this.timeUntilNextEgg = time;
	}

	@Override
	public boolean isBirdJockey() {
		return this.duckJockey;
	}

	@Override
	public Item getEggItem() {
		return EnvironmentalItems.DUCK_EGG.get();
	}

	@Override
	public int getNextEggTime(RandomSource rand) {
		return ((Duck) (Object) this).getRandomNextEggTime(rand);
	}

	@Override
	public SoundEvent getEggLayingSound() {
		return EnvironmentalSoundEvents.DUCK_EGG.get();
	}
}