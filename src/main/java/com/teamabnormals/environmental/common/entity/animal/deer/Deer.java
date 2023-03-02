package com.teamabnormals.environmental.common.entity.animal.deer;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class Deer extends AbstractDeer {
	private static final EntityDataAccessor<Integer> DEER_COAT_COLOR = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DEER_COAT_TYPE = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);

	public Deer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEER_COAT_COLOR, 0);
		this.entityData.define(DEER_COAT_TYPE, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("CoatColor", this.getCoatColor());
		compound.putInt("CoatType", this.getCoatType());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setCoatColor(compound.getInt("CoatColor"));
		this.setCoatType(compound.getInt("CoatType"));
	}

	private void setCoatColor(int id) {
		this.entityData.set(DEER_COAT_COLOR, id);
	}

	public int getCoatColor() {
		return this.entityData.get(DEER_COAT_COLOR);
	}

	private void setCoatType(int id) {
		int color = this.getCoatColor();
		if (color == 2 && id == 1) {
			this.entityData.set(DEER_COAT_TYPE, 0);
		} else {
			this.entityData.set(DEER_COAT_TYPE, id);
		}
	}

	public int getCoatType() {
		return this.entityData.get(DEER_COAT_TYPE);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageable) {
		Deer entity = EnvironmentalEntityTypes.DEER.get().create(level);
		Deer partner = (Deer) ageable;
		if (entity != null) {
			entity.setCoatColor(this.random.nextBoolean() ? partner.getCoatColor() : this.getCoatColor());
			entity.setCoatType(this.random.nextBoolean() ? partner.getCoatType() : this.getCoatType());
			entity.setHasAntlers(this.random.nextBoolean());
			entity.setTrusting(this.isTrusting() || partner.isTrusting());
		}

		return entity;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		int deerCoatColor;
		if (spawnDataIn instanceof DeerSpawnGroupData deerSpawnGroupData) {
			deerCoatColor = deerSpawnGroupData.coatColor;
		} else {
			deerCoatColor = this.random.nextInt(DeerCoatColors.values().length - 1);
			spawnDataIn = new DeerSpawnGroupData(deerCoatColor);
		}

		this.setCoatColor(deerCoatColor);
		this.setCoatType(this.random.nextInt(DeerCoatTypes.values().length));

		return super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
	}

	public static class DeerSpawnGroupData extends AgeableMobGroupData implements SpawnGroupData {
		public final int coatColor;

		public DeerSpawnGroupData(int coatColor) {
			super(0.3F);
			this.coatColor = coatColor;
		}
	}
}
