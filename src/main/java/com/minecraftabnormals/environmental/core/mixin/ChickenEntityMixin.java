package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements IEggLayingEntity {

	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Shadow
	public int timeUntilNextEgg;

	@Shadow
	public boolean chickenJockey;

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
		return this.chickenJockey;
	}

	@Override
	public Item getEggItem() {
		return Items.EGG;
	}

	@Override
	public int getNextEggTime(Random rand) {
		return rand.nextInt(6000) + 6000;
	}
}
