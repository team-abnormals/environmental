package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome.Precipitation;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class Reindeer extends AbstractDeer {
	private static final EntityDataAccessor<Boolean> HOLIDAY = SynchedEntityData.defineId(Reindeer.class, EntityDataSerializers.BOOLEAN);

	public Reindeer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HOLIDAY, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Holiday", this.isHoliday());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHoliday(compound.getBoolean("Holiday"));
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(EnvironmentalItemTags.REINDEER_FOOD);
	}

	@Override
	public Ingredient getTemptItems() {
		return Ingredient.of(EnvironmentalItemTags.REINDEER_TEMPT_ITEMS);
	}

	private void setHoliday(boolean isHoliday) {
		this.entityData.set(HOLIDAY, isHoliday);
	}

	public boolean isHoliday() {
		return this.entityData.get(HOLIDAY);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageable) {
		Reindeer entity = EnvironmentalEntityTypes.REINDEER.get().create(level);
		Reindeer partner = (Reindeer) ageable;
		if (entity != null) {
			entity.setHasAntlers(true);
			entity.setTrusting(this.isTrusting() || partner.isTrusting());
			entity.setHoliday(this.isHolidayCriteria());
		}

		return entity;
	}

	private boolean isHolidayCriteria() {
		return this.random.nextInt(9) == 0 && LocalDate.now().get(ChronoField.MONTH_OF_YEAR) == 12;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		this.setHasAntlers(true);
		this.setHoliday(this.isHolidayCriteria());
		return super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
	}
}
