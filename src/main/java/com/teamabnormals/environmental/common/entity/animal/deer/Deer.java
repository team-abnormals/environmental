package com.teamabnormals.environmental.common.entity.animal.deer;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.teamabnormals.environmental.common.entity.ai.goal.DeerAvoidEntityGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.DeerGrazeGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.DeerRunFromAttackerGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.DeerTemptGoal;
import com.teamabnormals.environmental.core.other.EnvironmentalTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Deer extends AbstractDeer {
	private static final EntityDataAccessor<Integer> DEER_COAT_COLOR = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DEER_COAT_TYPE = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> TRUSTING = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.BOOLEAN);

	private static final UUID SPEED_MODIFIER = UUID.fromString("a21208ef-5399-4341-800f-d5a9152afe98");
	private int floweringTime;
	private final List<BlockState> flowers = new ArrayList<>();
	@Nullable
	private TemptGoal temptGoal;

	public Deer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
		this.floweringTime = 0;
	}

	@Override
	protected void registerGoals() {
		this.temptGoal = new DeerTemptGoal(this, 0.6D, 1.1D, Ingredient.of(EnvironmentalTags.Items.DEER_TEMPT_ITEMS));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new DeerRunFromAttackerGoal(this));
		this.goalSelector.addGoal(2, new DeerAvoidEntityGoal(this));
		this.goalSelector.addGoal(3, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(4, this.temptGoal);
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.2D));
		this.goalSelector.addGoal(5, new DeerGrazeGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEER_COAT_COLOR, 0);
		this.entityData.define(DEER_COAT_TYPE, 0);
		this.entityData.define(TRUSTING, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("CoatColor", this.getCoatColor());
		compound.putInt("CoatType", this.getCoatType());
		compound.putBoolean("Trusting", this.isTrusting());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setCoatColor(compound.getInt("CoatColor"));
		this.setCoatType(compound.getInt("CoatType"));
		this.setTrusting(compound.getBoolean("Trusting"));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			AttributeModifier floweringModifier = new AttributeModifier(SPEED_MODIFIER, "Flowering speed boost", 0.07F,
					Operation.ADDITION);
			if (this.floweringTime > 0) {
				this.floweringTime -= 1;
				if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(floweringModifier))
					this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(floweringModifier);
				if (!this.flowers.isEmpty() && this.level.getGameTime() % 30 == 0) {
					BlockPos position = this.blockPosition();
					BlockState state = this.flowers.get(this.random.nextInt(this.flowers.size()));

					if (state.getBlock() instanceof DoublePlantBlock) {
						if (state.canSurvive(this.level, position) && this.level.isEmptyBlock(position)
								&& this.level.isEmptyBlock(position.above())) {
							DoublePlantBlock.placeAt(this.level, state, position, 2);
							this.level.levelEvent(2005, position, 0);
						}
					} else {
						if (state.canSurvive(this.level, position) && this.level.isEmptyBlock(position)) {
							this.level.setBlock(position, state, 3);
							this.level.levelEvent(2005, position, 0);
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

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		if (!this.isTrusting()) {
			if ((this.temptGoal == null || this.temptGoal.isRunning()) && this.isFood(stack)) {
				if (!this.level.isClientSide) {
					this.usePlayerItem(player, hand, stack);
					if (this.random.nextInt(3) == 0
							&& !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
						this.setTrusting(true);
						this.spawnTrustingParticles(true);
						this.level.broadcastEntityEvent(this, (byte) 4);
					} else {
						this.spawnTrustingParticles(false);
						this.level.broadcastEntityEvent(this, (byte) 6);
					}
				}

				return InteractionResult.sidedSuccess(this.level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		} else if (!this.isBaby()) {
			if (stack.is(Items.MELON_SLICE)) {
				this.floweringTime += 200;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (stack.is(Items.GLISTERING_MELON_SLICE)) {
				this.floweringTime += 600;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (this.floweringTime > 0 && stack.is(ItemTags.FLOWERS) && item instanceof BlockItem block) {
				if (!this.flowers.contains(block.getBlock().defaultBlockState())) {
					this.flowers.add(block.getBlock().defaultBlockState());
					this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				}
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			}
		}

		return super.mobInteract(player, hand);
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 4) {
			this.spawnTrustingParticles(true);
		} else if (id == 6) {
			this.spawnTrustingParticles(false);
		} else {
			super.handleEntityEvent(id);
		}
	}

	private void particleCloud(ParticleOptions particle) {
		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private void spawnTrustingParticles(boolean trusts) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!trusts) {
			particleoptions = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
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

	private void setTrusting(boolean trusting) {
		this.entityData.set(TRUSTING, trusting);
	}

	@Override
	public boolean isTrusting() {
		return this.entityData.get(TRUSTING);
	}

	private void setHoliday() {
		this.setCoatColor(DeerCoatColors.HOLIDAY.getId());
		this.setHasAntlers(true);
	}

	public boolean isHoliday() {
		return this.getCoatColor() == DeerCoatColors.HOLIDAY.getId();
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(EnvironmentalTags.Items.DEER_FOOD);
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
			if (this.isHolidayCriteria())
				entity.setHoliday();
		}

		return entity;
	}

	private boolean isHolidayCriteria() {
		if (this.random.nextInt(9) == 0) {
			LocalDate localdate = LocalDate.now();
			int month = localdate.get(ChronoField.MONTH_OF_YEAR);
			return month == 12
					&& this.level.getBiome(this.blockPosition()).value().getPrecipitation() == Precipitation.SNOW;
		}
		return false;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
		this.setCoatColor(this.random.nextInt(DeerCoatColors.values().length - 1));
		this.setCoatType(this.random.nextInt(DeerCoatTypes.values().length));
		if (this.isHolidayCriteria())
			this.setHoliday();
		return super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
	}
}
