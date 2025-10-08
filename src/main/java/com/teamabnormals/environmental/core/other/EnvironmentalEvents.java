package com.teamabnormals.environmental.core.other;

import com.google.common.collect.Sets;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.blueprint.core.util.MathUtil;
import com.teamabnormals.environmental.common.block.GiantLilyPadBlock;
import com.teamabnormals.environmental.common.block.LargeLilyPadBlock;
import com.teamabnormals.environmental.common.entity.ai.goal.CatLeapAtDwarfSpruceGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.HuntTruffleGoal;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalEvents {
	public static final List<MobSpawnType> VALID_SPAWNS = List.of(MobSpawnType.NATURAL, MobSpawnType.CHUNK_GENERATION, MobSpawnType.JOCKEY, MobSpawnType.REINFORCEMENT, MobSpawnType.PATROL);

	@SubscribeEvent
	public static void onLivingSpawn(FinalizeSpawnEvent event) {
		Mob entity = event.getEntity();
		ServerLevelAccessor level = event.getLevel();

		boolean natural = VALID_SPAWNS.contains(event.getSpawnType());
		boolean spawner = !EnvironmentalConfig.COMMON.blockOnlyNaturalSpawns.get() && event.getSpawnType() == MobSpawnType.SPAWNER;

		if ((natural || spawner) && entity.getType().getCategory() == MobCategory.MONSTER && !entity.getType().is(EnvironmentalEntityTypeTags.UNAFFECTED_BY_SERENITY)) {
			int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
			int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
			for (Koi koi : level.getEntitiesOfClass(Koi.class, entity.getBoundingBox().inflate(horizontalRange, verticalRange, horizontalRange))) {
				if (MathUtil.distanceBetweenPoints2d(entity.getX(), entity.getZ(), koi.getX(), koi.getZ()) <= horizontalRange) {
					event.setSpawnCancelled(true);
					event.setCanceled(true);
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEndermanPlaceBlock(EntityMobGriefingEvent event) {
		if (event.getEntity() instanceof EnderMan enderman && enderman.hasEffect(EnvironmentalMobEffects.SERENITY)) {
			event.setCanGrief(false);
		}
	}

	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof ThrownPotion thrownPotion) {
			ItemStack itemstack = thrownPotion.getItem();
			PotionContents contents = itemstack.get(DataComponents.POTION_CONTENTS);
			if (contents != null) {
				Optional<Holder<Potion>> potion = contents.potion();
				if (potion.isPresent() && potion.get().is(Potions.WATER) && contents.customEffects().isEmpty()) {
					AABB axisalignedbb = thrownPotion.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
					List<Slabfish> slabs = thrownPotion.level().getEntitiesOfClass(Slabfish.class, axisalignedbb);
					if (!slabs.isEmpty()) {
						for (Slabfish slabfish : slabs) {
							slabfish.setOverlay(null);
						}
					}
				}
			}
		}

		if (event.getEntity() instanceof ThrowableItemProjectile projectile && event.getRayTraceResult() instanceof EntityHitResult entity) {
			if (entity.getEntity() instanceof Slabfish slabfish) {
				ItemStack stack = projectile.getItem();
				Optional<Reference<SlabfishOverlay>> overlay = SlabfishOverlay.getOverlayForItem(slabfish.registryAccess(), stack);
				if (overlay.isPresent() && !(slabfish.getOverlay().isPresent() && slabfish.getOverlay().get().is(overlay.get()))) {
					slabfish.setOverlay(overlay.get());
				}
			}
		}
	}

	protected static final Set<Block> DIRT_SPREADABLES = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.MYCELIUM);

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		BlockState state = level.getBlockState(pos);
		Direction face = event.getFace();
		Player player = event.getEntity();
		ItemStack stack = event.getItemStack();
		if (stack.canPerformAction(ItemAbilities.SHOVEL_DIG) && !player.isSpectator() && state.is(EnvironmentalBlocks.BURIED_TRUFFLE.get())) {
			level.playSound(player, pos, EnvironmentalSoundEvents.SHOVEL_DIG.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!level.isClientSide()) {
				Vec3i vector3i = face.getNormal();
				double d0 = pos.getX() + 0.5D + 0.625D * vector3i.getX();
				double d1 = pos.getY() + 0.375D + 0.625D * vector3i.getY();
				double d2 = pos.getZ() + 0.5D + 0.625D * vector3i.getZ();
				ItemEntity itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(EnvironmentalItems.TRUFFLE.get(), 1));
				level.addFreshEntity(itementity);
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));
				level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 11);
			}
			event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
			event.setCanceled(true);
		} else if (stack.canPerformAction(ItemAbilities.HOE_TILL) && state.is(EnvironmentalBlocks.DIRT_PATH.get()) && !player.isSpectator() && level.isEmptyBlock(pos.above())) {
			level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!level.isClientSide()) {
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));
				level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
			}
			event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
			event.setCanceled(true);
		} else if (event.getFace() != Direction.DOWN && stack.canPerformAction(ItemAbilities.SHOVEL_FLATTEN) && !player.isSpectator() && level.isEmptyBlock(pos.above())) {
			if (state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.ROOTED_DIRT)) {
				level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!level.isClientSide) {
					stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));
					level.setBlock(pos, state.is(Blocks.PODZOL) ? EnvironmentalBlocks.PODZOL_PATH.get().defaultBlockState() : state.is(Blocks.MYCELIUM) ? EnvironmentalBlocks.MYCELIUM_PATH.get().defaultBlockState() : EnvironmentalBlocks.DIRT_PATH.get().defaultBlockState(), 11);
				}
				event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		Level level = event.getLevel();
		RandomSource random = level.getRandom();

		if (target instanceof Mob mob && target instanceof Bucketable && stack.is(Items.WATER_BUCKET) && target.isAlive()) {
			mob.dropLeash(true, true);
		}

		if (target instanceof Pig pig && target.isAlive()) {
			IDataManager data = ((IDataManager) target);
			if (!pig.isLeashed() && stack.is(EnvironmentalItemTags.PIG_TRUFFLE_ITEMS) && !pig.isBaby() && EnvironmentalConfig.COMMON.pigsHuntTruffles.get()) {
				if (data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) == 0) {
					if (level.dimensionType().natural()) {
						data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, 4800);
						if (!event.getEntity().isCreative()) stack.shrink(1);

						if (level.isClientSide()) {
							for (int i = 0; i < 7; ++i) {
								double d0 = random.nextGaussian() * 0.02D;
								double d1 = random.nextGaussian() * 0.02D;
								double d2 = random.nextGaussian() * 0.02D;
								level.addParticle(EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE.get(), target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
							}
						}
					} else if (level.isClientSide()) {
						for (int i = 0; i < 7; ++i) {
							double d0 = random.nextGaussian() * 0.02D;
							double d1 = random.nextGaussian() * 0.02D;
							double d2 = random.nextGaussian() * 0.02D;
							level.addParticle(ParticleTypes.SMOKE, target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
						}
					}

					event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}


	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		if (event.getEntity() instanceof Pig pig && pig.hasControllingPassenger()) {
			if (event.getDistance() > 3.75F && pig.getControllingPassenger() instanceof ServerPlayer player) {
				EnvironmentalCriteriaTriggers.WHEN_PIGS_FLY.get().trigger(player);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Level world = entity.getCommandSenderWorld();
		RandomSource rand = RandomSource.create();

		if (entity instanceof Slabfish slabfish) {
			if (world.getBiome(entity.blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
				if (!slabfish.getVariant().is(EnvironmentalSlabfishVariants.GHOST)) {
					if (world.isClientSide()) {
						for (int i = 0; i < 7; ++i) {
							double d0 = rand.nextGaussian() * 0.02D;
							double d1 = rand.nextGaussian() * 0.02D;
							double d2 = rand.nextGaussian() * 0.02D;
							world.addParticle(ParticleTypes.SOUL, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
						}
					}

					if (!world.isClientSide()) {
						Slabfish ghost = EnvironmentalEntityTypes.SLABFISH.get().create(world);
						if (ghost == null) return;

						ghost.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 140, 0, false, false));
						ghost.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
						world.playSound(null, entity.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1, 1);

						ghost.moveTo(slabfish.getX(), slabfish.getY(), slabfish.getZ(), slabfish.getYRot(), slabfish.getXRot());
						ghost.setNoAi(slabfish.isNoAi());
						ghost.setAge(slabfish.getAge());
						ghost.setVariant(SlabfishHelper.slabfishTypes(slabfish.registryAccess()).getHolderOrThrow(EnvironmentalSlabfishVariants.GHOST));
						ghost.extinguishFire();
						if (slabfish.hasCustomName()) {
							ghost.setCustomName(entity.getCustomName());
							ghost.setCustomNameVisible(entity.isCustomNameVisible());
						}

						world.addFreshEntity(ghost);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onBabySpawn(BabyEntitySpawnEvent event) {
		if (event.getParentA() instanceof Pig pig && event.getParentB() instanceof Pig && EnvironmentalConfig.COMMON.largerPigLitters.get()) {
			Level level = pig.level();
			int minBonus = EnvironmentalConfig.COMMON.minimumAdditionalPiglets.get();
			int piglets = minBonus + level.random.nextInt(EnvironmentalConfig.COMMON.maximumAdditionalPiglets.get() + 1 - minBonus);
			for (int i = 0; i < piglets; ++i) {
				Pig baby = EntityType.PIG.create(level);
				if (baby != null) {
					baby.setBaby(true);
					baby.moveTo(pig.getX(), pig.getY(), pig.getZ(), 0.0F, 0.0F);
					level.addFreshEntity(baby);
				}
			}
		}

		if (event.getParentA() instanceof Hoglin hoglin && event.getParentB() instanceof Hoglin && EnvironmentalConfig.COMMON.largerHoglinLitters.get()) {
			Level level = hoglin.level();
			int minBonus = EnvironmentalConfig.COMMON.minimumAdditionalHoglets.get();
			int piglets = minBonus + level.random.nextInt(EnvironmentalConfig.COMMON.maximumAdditionalHoglets.get() + 1 - minBonus);
			for (int i = 0; i < piglets; ++i) {
				Hoglin baby = EntityType.HOGLIN.create(level);
				if (baby != null) {
					baby.setBaby(true);
					baby.moveTo(hoglin.getX(), hoglin.getY(), hoglin.getZ(), 0.0F, 0.0F);
					level.addFreshEntity(baby);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Wolf wolf) {
			wolf.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(wolf, Animal.class, false, (target) -> {
				EntityType<?> type = target.getType();
				return type == EnvironmentalEntityTypes.DEER.get() || type == EnvironmentalEntityTypes.TAPIR.get() || type == EnvironmentalEntityTypes.REINDEER.get();
			}));
		}

		if (entity instanceof Ocelot ocelot) {
			ocelot.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(ocelot, Animal.class, 10, false, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntityTypes.DUCK.get()));
		}

		if (entity instanceof Cat cat) {
			cat.targetSelector.addGoal(8, new CatLeapAtDwarfSpruceGoal(cat));
		}

		if (entity instanceof Pig pig && EnvironmentalConfig.COMMON.pigsHuntTruffles.get()) {
			Set<WrappedGoal> goals = pig.goalSelector.getAvailableGoals();
			if (goals.stream().noneMatch((goal) -> goal.getGoal() instanceof HuntTruffleGoal)) pig.goalSelector.addGoal(2, new HuntTruffleGoal(pig));
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(EntityTickEvent.Post event) {
		Entity entity = event.getEntity();
		Level level = entity.getCommandSenderWorld();
		RandomSource random = level.getRandom();

		if (entity instanceof Pig && entity.isAlive()) {
			IDataManager data = ((IDataManager) entity);
			int huntingtime = data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME);
			BlockPos trufflepos = data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);

			if (huntingtime == 0 || (data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && level.getBlockState(trufflepos).getBlock() != EnvironmentalBlocks.BURIED_TRUFFLE.get())) {
				data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, false);
				if (huntingtime > 0) data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, Math.max(-400, -huntingtime));
			} else {
				if (huntingtime > 0) {
					data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, huntingtime - 1);
				} else {
					data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, huntingtime + 1);
					if (level.isClientSide() && data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && huntingtime % 10 == 0) {
						double d0 = random.nextGaussian() * 0.02D;
						double d1 = random.nextGaussian() * 0.02D;
						double d2 = random.nextGaussian() * 0.02D;
						level.addParticle(EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
					}
				}
			}

			int sniffsoundtime = data.getValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME);
			data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, sniffsoundtime + 1);
			if (!level.isClientSide() && data.getValue(EnvironmentalDataProcessors.LOOKING_FOR_TRUFFLE) && random.nextInt(60) < sniffsoundtime) {
				entity.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
				data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, -20);
			}
		}
	}

	@SubscribeEvent
	public static void onBonemeal(BonemealEvent event) {
		Level level = event.getLevel();
		BlockState state = event.getState();
		BlockPos pos = event.getPos();
		RandomSource random = level.getRandom();

		if (EnvironmentalConfig.COMMON.cactusBobble.get() && state.is(EnvironmentalBlockTags.CACTUS_BOBBLE_PLANTABLE_ON) && level.getBlockState(pos.above()).isAir()) {
			if (!level.isClientSide()) {
				level.setBlockAndUpdate(pos.above(), EnvironmentalBlocks.CACTUS_BOBBLE.get().defaultBlockState());
			}
			event.setSuccessful(true);
			event.setCanceled(true);
		}

		if (state.is(Blocks.DIRT) && level.getBlockState(pos.above()).propagatesSkylightDown(level, pos)) {
			ArrayList<BlockState> potentialStates = new ArrayList<>();
			for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
				Block block = level.getBlockState(blockpos).getBlock();
				if (DIRT_SPREADABLES.contains(block) && !potentialStates.contains(block.defaultBlockState())) {
					potentialStates.add(block.defaultBlockState());
				}
			}
			if (!potentialStates.isEmpty()) {
				if (!level.isClientSide()) {
					level.setBlock(pos, potentialStates.get(level.getRandom().nextInt(potentialStates.size())), 3);
				}
				event.setSuccessful(true);
				event.setCanceled(true);
			}
		}

		if (state.is(Blocks.MYCELIUM) && level.getBlockState(pos.above()).isAir()) {
			if (!level.isClientSide()) {
				BlockPos abovePos = pos.above();
				for (int i = 0; i < 128; ++i) {
					BlockPos newPos = abovePos;

					for (int j = 0; j < i / 16; ++j) {
						newPos = newPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
						if (!level.getBlockState(newPos.below()).is(Blocks.MYCELIUM) || level.getBlockState(newPos).isCollisionShapeFullBlock(level, newPos)) {
							break;
						}
					}

					if (level.getBlockState(newPos).isAir()) {
						if (EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState().canSurvive(level, newPos)) {
							level.setBlock(newPos, EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState(), 3);
						}
					}
				}
			}

			event.setSuccessful(true);
			event.setCanceled(true);
		}

		if (state.is(Blocks.TALL_GRASS)) {
			if (!level.isClientSide()) {
				DoublePlantBlock.placeAt(level, EnvironmentalBlocks.GIANT_TALL_GRASS.get().defaultBlockState(), state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? pos : pos.below(), 2);
			}
			event.setSuccessful(true);
			event.setCanceled(true);
		}

		if (state.is(Blocks.LILY_PAD)) {
			if (!level.isClientSide() && random.nextInt(3) == 0) {
				if (random.nextBoolean()) {
					if (LargeLilyPadBlock.checkPositions(level, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().defaultBlockState())) {
						LargeLilyPadBlock.placeAt(level, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().defaultBlockState(), 3);
					}
				} else if (GiantLilyPadBlock.checkPositions(level, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().defaultBlockState())) {
					GiantLilyPadBlock.placeAt(level, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().defaultBlockState(), 3);
				}
			}
			event.setSuccessful(true);
			event.setCanceled(true);
		}
	}
}
