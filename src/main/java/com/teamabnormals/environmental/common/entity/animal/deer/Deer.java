package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.common.entity.ai.goal.*;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class Deer extends AbstractDeer {
	private static final EntityDataAccessor<Integer> DEER_COAT_COLOR = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DEER_COAT_TYPE = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FLOWER_AMOUNT = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> TRUSTING = SynchedEntityData.defineId(Deer.class, EntityDataSerializers.BOOLEAN);

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
		this.temptGoal = new DeerTemptGoal(this, 0.6D, 1.1D, Ingredient.of(EnvironmentalItemTags.DEER_TEMPT_ITEMS));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new DeerFollowParentGoal(this));
		this.goalSelector.addGoal(2, new DeerRunFromAttackerGoal(this));
		this.goalSelector.addGoal(3, new DeerAvoidEntityGoal(this));
		this.goalSelector.addGoal(4, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(5, new DeerFrolicGoal(this));
		this.goalSelector.addGoal(6, this.temptGoal);
		this.goalSelector.addGoal(7, new DeerGrazeGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEER_COAT_COLOR, 0);
		this.entityData.define(DEER_COAT_TYPE, 0);
		this.entityData.define(FLOWER_AMOUNT, 0);
		this.entityData.define(TRUSTING, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("CoatColor", this.getCoatColor());
		compound.putInt("CoatType", this.getCoatType());
		compound.putBoolean("Trusting", this.isTrusting());

		compound.putInt("FloweringTime", this.floweringTime);
		compound.putInt("FlowerAmount", this.getFlowerAmount());
		ListTag listtag = new ListTag();
		for (BlockState blockstate : this.flowers) {
			if (blockstate != null) {
				listtag.add(NbtUtils.writeBlockState(blockstate));
			}
		}
		compound.put("Flowers", listtag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setCoatColor(compound.getInt("CoatColor"));
		this.setCoatType(compound.getInt("CoatType"));
		this.setTrusting(compound.getBoolean("Trusting"));

		this.floweringTime = compound.getInt("FloweringTime");
		this.setFlowerAmount(compound.getInt("FlowerAmount"));
		ListTag listtag = compound.getList("Flowers", 10);
		for (int i = 0; i < listtag.size(); ++i) {
			BlockState blockstate = NbtUtils.readBlockState(listtag.getCompound(i));
			if (blockstate != null && !blockstate.isAir()) {
				this.flowers.add(blockstate);
			}
		}
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.level.isClientSide) {
			if (this.getFlowerAmount() > 0 && this.tickCount % 16 == 0)
				this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
		} else {
			if (this.floweringTime > 0)
				--this.floweringTime;

			if ((this.floweringTime <= 0 && this.getFlowerAmount() > 0) || (this.getFlowerAmount() <= 0 && !this.flowers.isEmpty())) {
				this.setFlowerAmount(0);
				this.flowers.clear();
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		boolean flag = player.isCreative() || this.temptGoal == null || this.temptGoal.isRunning();

		if (!this.isBaby() && (this.isTrusting() || flag)) {
			if (stack.is(Items.MELON_SLICE)) {
				this.setFlowerAmount(this.getFlowerAmount() + 6);
				this.floweringTime += 2400;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (stack.is(Items.GLISTERING_MELON_SLICE)) {
				this.setFlowerAmount(this.getFlowerAmount() + 20);
				this.floweringTime += 2400;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (this.getFlowerAmount() > 0 && stack.is(EnvironmentalItemTags.DEER_PLANTABLES) && item instanceof BlockItem block) {
				if (!this.flowers.contains(block.getBlock().defaultBlockState())) {
					this.flowers.add(block.getBlock().defaultBlockState());
					this.floweringTime = Math.max(600, this.floweringTime);
					this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
					this.usePlayerItem(player, hand, stack);
					return InteractionResult.SUCCESS;
				} else {
					return InteractionResult.PASS;
				}
			}
		}

		if (!this.isTrusting()) {
			if (flag && this.isFood(stack)) {
				if (!this.level.isClientSide) {
					this.usePlayerItem(player, hand, stack);
					if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
						this.setTrusting(true);
						this.spawnTrustingParticles(true);
						this.level.broadcastEntityEvent(this, (byte) 5);
					} else {
						this.spawnTrustingParticles(false);
						this.level.broadcastEntityEvent(this, (byte) 6);
					}
				}

				return InteractionResult.sidedSuccess(this.level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		} else {
			return super.mobInteract(player, hand);
		}
	}

	public void spawnFlower() {
		if (this.isSpreadingFlowers()) {
			BlockPos pos = this.blockPosition();
			BlockState state = this.flowers.get(this.random.nextInt(this.flowers.size()));
			boolean tall = state.getBlock() instanceof DoublePlantBlock;
			if (state.canSurvive(this.level, pos) && this.level.isEmptyBlock(pos) && (!tall || this.level.isEmptyBlock(pos.above()))) {
				if (!tall) {
					this.level.setBlock(pos, state, 3);
				} else {
					DoublePlantBlock.placeAt(this.level, state, pos, 2);
				}
				SoundType sound = state.getSoundType();
				this.playSound(sound.getPlaceSound(), (sound.getVolume() + 1.0F) / 4.0F, sound.getPitch() * 0.8F);
				this.setFlowerAmount(this.getFlowerAmount() - 1);
				this.spawnBoneMealParticles(state, pos);
			}
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 5) {
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

	private void spawnBoneMealParticles(BlockState blockstate, BlockPos blockpos) {
		int amount = 15;
		double d0 = 0.5D;
		double d1;
		if (blockstate.isSolidRender(this.level, blockpos)) {
			blockpos = blockpos.above();
			amount *= 3;
			d0 = 3.0D;
			d1 = 1.0D;
		} else {
			d1 = blockstate.getShape(this.level, blockpos).max(Direction.Axis.Y);
		}

		((ServerLevel) this.level).sendParticles(ParticleTypes.HAPPY_VILLAGER, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D, 0, 0.0D, 0.0D, 0.0D, 0.0D);
		RandomSource random = this.level.getRandom();

		for (int i = 0; i < amount; ++i) {
			double d2 = random.nextGaussian() * 0.02D;
			double d3 = random.nextGaussian() * 0.02D;
			double d4 = random.nextGaussian() * 0.02D;
			double d5 = 0.5D - d0;
			double d6 = (double) blockpos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
			double d7 = (double) blockpos.getY() + random.nextDouble() * d1;
			double d8 = (double) blockpos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
			((ServerLevel) this.level).sendParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 0, d2, d3, d4, 0.0D);
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

	public boolean isSpreadingFlowers() {
		return this.getFlowerAmount() > 0 && !this.flowers.isEmpty();
	}

	private int getFlowerAmount() {
		return this.entityData.get(FLOWER_AMOUNT);
	}

	private void setFlowerAmount(int amount) {
		this.entityData.set(FLOWER_AMOUNT, amount);
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
		return stack.is(EnvironmentalItemTags.DEER_FOOD);
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
			return month == 12 && this.level.getBiome(this.blockPosition()).value().getPrecipitation() == Precipitation.SNOW;
		}
		return false;
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
		if (this.isHolidayCriteria())
			this.setHoliday();
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
