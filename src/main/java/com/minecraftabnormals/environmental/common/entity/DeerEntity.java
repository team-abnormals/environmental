package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatTypes;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTags;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class DeerEntity extends AnimalEntity {
	private static final DataParameter<Integer> DEER_COAT_COLOR = EntityDataManager.defineId(DeerEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DEER_COAT_TYPE = EntityDataManager.defineId(DeerEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> HAS_ANTLERS = EntityDataManager.defineId(DeerEntity.class, DataSerializers.BOOLEAN);

	private static final Predicate<Entity> SHOULD_AVOID = (entity) -> !entity.isDiscrete() && EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(entity);

	private static final UUID SPEED_MODIFIER = UUID.fromString("a21208ef-5399-4341-800f-d5a9152afe98");
	private int floweringTime;
	private final List<BlockState> flowers = new ArrayList<>();

	public DeerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.floweringTime = 0;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.5D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 22.0F, 2.0D, 2.7D, SHOULD_AVOID::test));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, false, Ingredient.of(EnvironmentalTags.Items.DEER_TEMPT_ITEMS)));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEER_COAT_COLOR, 0);
		this.entityData.define(DEER_COAT_TYPE, 0);
		this.entityData.define(HAS_ANTLERS, true);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("CoatColor", this.getCoatColor());
		compound.putInt("CoatType", this.getCoatType());
		compound.putBoolean("Antlers", this.hasAntlers());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setCoatColor(compound.getInt("CoatColor"));
		this.setCoatType(compound.getInt("CoatType"));
		this.setHasAntlers(compound.getBoolean("Antlers"));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			AttributeModifier floweringModifier = new AttributeModifier(SPEED_MODIFIER, "Flowering speed boost", 0.07F, Operation.ADDITION);
			if (this.floweringTime > 0) {
				this.floweringTime -= 1;
				if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(floweringModifier))
					this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(floweringModifier);
				if (!flowers.isEmpty() && this.level.getGameTime() % 30 == 0) {
					BlockPos position = this.blockPosition();
					BlockState state = flowers.get(random.nextInt(flowers.size()));

					if (state.getBlock() instanceof DoublePlantBlock) {
						DoublePlantBlock block = (DoublePlantBlock) state.getBlock();
						if (state.canSurvive(level, position) && level.isEmptyBlock(position) && level.isEmptyBlock(position.above())) {
							block.placeAt(level, position, 2);
							level.levelEvent(2005, position, 0);
						}
					} else {
						if (state.canSurvive(level, position) && level.isEmptyBlock(position)) {
							level.setBlock(position, state, 3);
							level.levelEvent(2005, position, 0);
						}
					}
				}
			} else {
				this.flowers.clear();
				if (this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(floweringModifier))
					this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(floweringModifier);
			}
		}
	}

	private void particleCloud(IParticleData particle) {
		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		if (!this.isBaby()) {
			if (item == Items.MELON_SLICE) {
				this.floweringTime += 200;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, stack);
				return ActionResultType.SUCCESS;
			} else if (item == Items.GLISTERING_MELON_SLICE) {
				this.floweringTime += 600;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, stack);
				return ActionResultType.SUCCESS;
			} else if (this.floweringTime > 0 && item.is(ItemTags.FLOWERS) && item instanceof BlockItem) {
				BlockItem block = (BlockItem) item;
				if (!this.flowers.contains(block.getBlock().defaultBlockState())) {
					flowers.add(block.getBlock().defaultBlockState());
					this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				}
				this.usePlayerItem(player, stack);
				return ActionResultType.SUCCESS;
			}
		}
		return super.mobInteract(player, hand);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSounds.DEER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSounds.DEER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSounds.DEER_DEATH.get();
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSounds.DEER_STEP.get(), 0.15F, 1.0F);
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

	private void setHasAntlers(boolean antlers) {
		this.entityData.set(HAS_ANTLERS, antlers);
	}

	public boolean hasAntlers() {
		return this.entityData.get(HAS_ANTLERS);
	}

	public void setDeerData(DeerCoatColors coatColor, DeerCoatTypes coatType, boolean antlers) {
		this.setCoatColor(coatColor.getId());
		this.setCoatType(coatType.getId());
		this.setHasAntlers(antlers);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem().is(EnvironmentalTags.Items.DEER_FOOD);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		DeerEntity entity = EnvironmentalEntities.DEER.get().create(world);

		if (this.isHolidayDeer()) {
			entity.setCoatColor(DeerCoatColors.HOLIDAY.getId());
		} else {
			entity.setCoatColor(((DeerEntity) ageable).getCoatColor());
		}

		entity.setCoatType(this.getCoatType());
		entity.setHasAntlers(random.nextBoolean());

		return entity;
	}

	public boolean isHolidayDeer() {
		LocalDate localdate = LocalDate.now();
		int day = localdate.get(ChronoField.DAY_OF_MONTH);
		int month = localdate.get(ChronoField.MONTH_OF_YEAR);
		return (month == 12 && day >= 21 && day <= 31) && this.random.nextFloat() < 0.5F && this.level.getBiome(this.blockPosition()).getBiomeCategory() == Biome.Category.ICY;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return AnimalEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.15D);
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
		if (this.isHolidayDeer()) {
			this.setCoatColor(DeerCoatColors.HOLIDAY.getId());
		} else {
			this.setCoatColor(random.nextInt(DeerCoatColors.values().length - 1));
		}

		this.setCoatType(random.nextInt(DeerCoatTypes.values().length));
		this.setHasAntlers(random.nextBoolean());

		return super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
	}
}
