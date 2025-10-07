package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalDeerVariants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public class Deer extends AbstractDeer implements VariantHolder<Holder<DeerVariant>> {
	private static final EntityDataAccessor<Holder<DeerVariant>> VARIANT = SynchedEntityData.defineId(Deer.class, EnvironmentalDataSerializers.DEER_VARIANT.get());
	private static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.BOOLEAN);

	public Deer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		Registry<DeerVariant> registry = this.registryAccess().registryOrThrow(EnvironmentalRegistries.DEER_VARIANT);
		builder.define(VARIANT, registry.getHolder(EnvironmentalDeerVariants.DEFAULT).or(registry::getAny).orElseThrow());
		builder.define(SPOTTED, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		this.getVariant().unwrapKey().ifPresent(variant -> tag.putString("variant", variant.location().toString()));
		tag.putBoolean("spotted", this.hasSpots());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("variant")))
				.map(loc -> ResourceKey.create(EnvironmentalRegistries.DEER_VARIANT, loc))
				.flatMap(key -> this.registryAccess().registryOrThrow(EnvironmentalRegistries.DEER_VARIANT).getHolder(key))
				.ifPresent(this::setVariant);
		this.setSpotted(tag.getBoolean("spotted"));
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(EnvironmentalItemTags.DEER_FOOD);
	}

	@Override
	public void setVariant(Holder<DeerVariant> variant) {
		this.entityData.set(VARIANT, variant);
	}

	@Override
	public Holder<DeerVariant> getVariant() {
		return this.entityData.get(VARIANT);
	}

	public void setSpotted(boolean spotted) {
		this.entityData.set(SPOTTED, !this.getVariant().is(EnvironmentalDeerVariants.GRAY) && spotted);
	}

	public boolean hasSpots() {
		return this.entityData.get(SPOTTED);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageable) {
		Deer entity = EnvironmentalEntityTypes.DEER.get().create(level);
		if (ageable instanceof Deer partner && entity != null) {
			entity.setVariant(this.random.nextBoolean() ? partner.getVariant() : this.getVariant());
			entity.setSpotted(this.random.nextBoolean() ? partner.hasSpots() : this.hasSpots());
			entity.setHasAntlers(this.random.nextFloat() < EnvironmentalConfig.COMMON.deerAntlerChance.get());
			entity.setTrusting(this.isTrusting() || partner.isTrusting());
		}

		return entity;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
		if (spawnData instanceof DeerSpawnGroupData deerSpawnGroupData) {
			this.setVariant(deerSpawnGroupData.type);
		} else {
			Holder<Biome> biome = level.getBiome(this.blockPosition());
			Holder<DeerVariant> variant = DeerVariant.getSpawnVariant(this.registryAccess(), biome);
			spawnData = new DeerSpawnGroupData(variant);
			this.setVariant(variant);
		}

		this.setSpotted(this.random.nextBoolean());
		this.setHasAntlers(this.random.nextFloat() < EnvironmentalConfig.COMMON.deerAntlerChance.get());
		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	public static class DeerSpawnGroupData extends AgeableMobGroupData implements SpawnGroupData {
		public final Holder<DeerVariant> type;

		public DeerSpawnGroupData(Holder<DeerVariant> type) {
			super(0.3F);
			this.type = type;
		}
	}
}
