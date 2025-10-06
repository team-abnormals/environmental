package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class Deer extends AbstractDeer {
	private static final EntityDataAccessor<Integer> DEER_COAT_COLOR = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DEER_COAT_TYPE = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);

	public Deer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DEER_COAT_COLOR, 0);
		builder.define(DEER_COAT_TYPE, 0);
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

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(EnvironmentalItemTags.DEER_FOOD);
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
			entity.setHasAntlers(this.random.nextFloat() < EnvironmentalConfig.COMMON.deerAntlerChance.get());
			entity.setTrusting(this.isTrusting() || partner.isTrusting());
		}

		return entity;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn) {
		if (spawnDataIn instanceof DeerSpawnGroupData deerSpawnGroupData) {
			this.setCoatColor(deerSpawnGroupData.coatColor);
		} else {
			Holder<Biome> holder = level.getBiome(this.blockPosition());
			if (holder.is(EnvironmentalBiomeTags.SPAWNS_CHESTNUT_DEER)) {
				this.setCoatColor(DeerCoatColors.CHESTNUT.getId());
			} else if (holder.is(EnvironmentalBiomeTags.SPAWNS_GRAY_DEER)) {
				this.setCoatColor(DeerCoatColors.GRAY.getId());
			} else {
				this.setCoatColor(DeerCoatColors.CREAMY.getId());
			}

			spawnDataIn = new DeerSpawnGroupData(this.getCoatColor());
		}

		this.setCoatType(this.random.nextInt(DeerCoatTypes.values().length));
		this.setHasAntlers(this.random.nextFloat() < EnvironmentalConfig.COMMON.deerAntlerChance.get());
		return super.finalizeSpawn(level, difficulty, reason, spawnDataIn);
	}

	public static class DeerSpawnGroupData extends AgeableMobGroupData implements SpawnGroupData {
		public final int coatColor;

		public DeerSpawnGroupData(int coatColor) {
			super(0.3F);
			this.coatColor = coatColor;
		}
	}
}
