package com.teamabnormals.environmental.core.other;

import com.google.common.collect.Sets;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.blueprint.core.util.MathUtil;
import com.teamabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.teamabnormals.environmental.common.entity.ai.goal.HuntTruffleGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.TemptGoldenCarrotGoal;
import com.teamabnormals.environmental.common.entity.animal.Koi;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalEvents {

	@SubscribeEvent
	public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
		if (event.getState().getBlock() instanceof HangingWisteriaLeavesBlock && event.getPlayer().getMainHandItem().getItem() == Items.SHEARS)
			event.setNewSpeed(15.0F);
	}

	@SubscribeEvent
	public static void onLivingSpawn(LivingSpawnEvent.CheckSpawn event) {
		Entity entity = event.getEntity();
		LevelAccessor world = event.getWorld();
		Random random = world.getRandom();

		boolean naturalSpawn = event.getSpawnReason() == MobSpawnType.NATURAL;
		boolean chunkGenSpawn = event.getSpawnReason() == MobSpawnType.CHUNK_GENERATION;
		boolean validSpawn = naturalSpawn || chunkGenSpawn;

		boolean replaceVariants = EnvironmentalConfig.COMMON.biomeVariantsAlwaysSpawn.get();

		if (event.getResult() != Event.Result.DENY && world instanceof ServerLevelAccessor serverWorld) {
			if (replaceVariants && validSpawn && entity.getY() > 60) {
				if (entity.getType() == EntityType.ZOMBIE) {
					Zombie zombie = (Zombie) entity;
					if (world.getBiome(entity.blockPosition()).is(EnvironmentalBiomeTags.HAS_HUSK)) {
						Husk husk = EntityType.HUSK.create(serverWorld.getLevel());
						husk.setBaby(zombie.isBaby());
						for (EquipmentSlot slot : EquipmentSlot.values())
							zombie.setItemSlot(slot, zombie.getItemBySlot(slot));
						husk.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), zombie.getXRot());

						world.addFreshEntity(husk);
						entity.discard();
					}
				}

				if (entity.getType() == EntityType.SKELETON) {
					Skeleton skeleton = (Skeleton) entity;
					if (world.getBiome(entity.blockPosition()).is(EnvironmentalBiomeTags.HAS_STRAY)) {
						Stray stray = EntityType.STRAY.create(serverWorld.getLevel());
						for (EquipmentSlot slot : EquipmentSlot.values())
							skeleton.setItemSlot(slot, skeleton.getItemBySlot(slot));
						stray.moveTo(skeleton.getX(), skeleton.getY(), skeleton.getZ(), skeleton.getYRot(), skeleton.getXRot());

						world.addFreshEntity(stray);
						entity.discard();
					}
				}
			}

			if (validSpawn && entity.getType() == EntityType.MOOSHROOM) {
				MushroomCow mooshroom = (MushroomCow) event.getEntity();
				if (random.nextInt(3) == 0) {
					MushroomCow brownMooshroom = EntityType.MOOSHROOM.create(serverWorld.getLevel());
					brownMooshroom.moveTo(mooshroom.getX(), mooshroom.getY(), mooshroom.getZ(), mooshroom.getYRot(), mooshroom.getXRot());
					brownMooshroom.setMushroomType(MushroomCow.MushroomType.BROWN);
					world.addFreshEntity(brownMooshroom);
					entity.discard();
				}
			}
		}

		boolean blockOnlyNaturalSpawns = EnvironmentalConfig.COMMON.blockOnlyNaturalSpawns.get();

		if (blockOnlyNaturalSpawns && event.isSpawner()) return;
		if (!entity.getType().is(EnvironmentalEntityTypeTags.UNAFFECTED_BY_SERENITY)) {
			if (entity.getType().getCategory() == MobCategory.MONSTER) {
				int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
				int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
				for (Entity koi : world.getEntitiesOfClass(Koi.class, entity.getBoundingBox().inflate(horizontalRange, verticalRange, horizontalRange))) {
					if (MathUtil.distanceBetweenPoints2d(entity.getX(), entity.getZ(), koi.getX(), koi.getZ()) <= horizontalRange) {
						event.setResult(Event.Result.DENY);
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getEntity() instanceof ThrownPotion potionEntity) {
			ItemStack itemstack = potionEntity.getItem();
			Potion potion = PotionUtils.getPotion(itemstack);
			List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);

			if (potion == Potions.WATER && list.isEmpty()) {
				AABB axisalignedbb = potionEntity.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
				List<Slabfish> slabs = potionEntity.level.getEntitiesOfClass(Slabfish.class, axisalignedbb);
				if (!slabs.isEmpty()) {
					for (Slabfish slabfish : slabs) {
						slabfish.setSlabfishOverlay(SlabfishOverlay.NONE);
					}
				}
			}
		}

		if (event.getEntity() instanceof ThrowableItemProjectile projectileitem) {
			if (event.getRayTraceResult() != null && event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
				EntityHitResult entity = (EntityHitResult) event.getRayTraceResult();
				if (entity.getEntity() instanceof Slabfish slabfish) {
					ItemStack stack = projectileitem.getItem();
					if (stack.is(Items.SNOWBALL))
						slabfish.setSlabfishOverlay(SlabfishOverlay.SNOWY);
					else if (stack.is(Tags.Items.EGGS))
						slabfish.setSlabfishOverlay(SlabfishOverlay.EGG);
				}
			}
		}
	}

	protected static final Set<Block> DIRT_SPREADABLES = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.MYCELIUM);

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		Level world = event.getWorld();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		Direction face = event.getFace();
		Player player = event.getPlayer();
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		if (item == Items.BONE_MEAL && state.getBlock() == Blocks.DIRT && world.getBlockState(pos.above()).propagatesSkylightDown(world, pos)) {
			ArrayList<BlockState> potentialStates = new ArrayList<>();
			for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
				Block block = world.getBlockState(blockpos).getBlock();
				if (DIRT_SPREADABLES.contains(block) && !potentialStates.contains(block.defaultBlockState())) {
					potentialStates.add(block.defaultBlockState());
				}
			}
			if (!potentialStates.isEmpty()) {
				if (!world.isClientSide()) {
					world.levelEvent(2005, pos, 0);
					world.setBlock(pos, potentialStates.get(world.getRandom().nextInt(potentialStates.size())), 3);
				}
				if (!player.isCreative()) stack.shrink(1);
				event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide()));
				event.setCanceled(true);
			}
		} else if (item instanceof ShovelItem && !player.isSpectator() && state.is(EnvironmentalBlocks.BURIED_TRUFFLE.get())) {
			Vec3i vector3i = face.getNormal();
			double d0 = pos.getX() + 0.5D + 0.625D * vector3i.getX();
			double d1 = pos.getY() + 0.375D + 0.625D * vector3i.getY();
			double d2 = pos.getZ() + 0.5D + 0.625D * vector3i.getZ();
			ItemEntity itementity = new ItemEntity(world, d0, d1, d2, new ItemStack(EnvironmentalItems.TRUFFLE.get()));
			world.addFreshEntity(itementity);

			world.playSound(player, pos, EnvironmentalSoundEvents.SHOVEL_DIG.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
			stack.hurtAndBreak(1, player, (damage) -> {
				damage.broadcastBreakEvent(event.getHand());
			});
			world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 11);
			event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide()));
			event.setCanceled(true);
		} else if (event.getFace() != Direction.DOWN) {
			if (item instanceof ShovelItem && !player.isSpectator() && world.isEmptyBlock(pos.above())) {
				if (state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM)) {
					world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
					stack.hurtAndBreak(1, player, (damage) -> {
						damage.broadcastBreakEvent(event.getHand());
					});
					world.setBlock(pos, state.is(Blocks.PODZOL) ? EnvironmentalBlocks.PODZOL_PATH.get().defaultBlockState() : EnvironmentalBlocks.MYCELIUM_PATH.get().defaultBlockState(), 11);
					event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		Level world = event.getWorld();
		Random random = world.getRandom();

		if (target instanceof Slabfish && stack.getItem() == Items.NAME_TAG) {
			Slabfish slabby = (Slabfish) event.getTarget();
			if (stack.hasCustomHoverName()) {
				if (!slabby.hasCustomName() || slabby.getCustomName() == null || !slabby.getCustomName().getString().equals(stack.getHoverName().getString())) {
					slabby.playTransformSound();
				}
			}
		}

		if (target instanceof Pig && stack.getItem() == Items.GOLDEN_CARROT) {
			if (target.isAlive() && !((Pig) target).isBaby()) {
				IDataManager data = ((IDataManager) target);
				if (data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) == 0) {
					if (world.dimensionType().natural()) {
						data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, 4800);
						if (!event.getPlayer().isCreative()) stack.shrink(1);

						if (world.isClientSide()) {
							for (int i = 0; i < 7; ++i) {
								double d0 = random.nextGaussian() * 0.02D;
								double d1 = random.nextGaussian() * 0.02D;
								double d2 = random.nextGaussian() * 0.02D;
								world.addParticle(EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE.get(), target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
							}
						}
					} else if (world.isClientSide()) {
						for (int i = 0; i < 7; ++i) {
							double d0 = random.nextGaussian() * 0.02D;
							double d1 = random.nextGaussian() * 0.02D;
							double d2 = random.nextGaussian() * 0.02D;
							world.addParticle(ParticleTypes.SMOKE, target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
						}
					}

					event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Level world = entity.getCommandSenderWorld();
		Random rand = new Random();

		if (entity instanceof Slabfish) {
			Slabfish slabfish = (Slabfish) event.getEntity();
			if (world.getBiome(entity.blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
				if (!slabfish.getSlabfishType().equals(SlabfishManager.GHOST)) {
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
						if (ghost == null)
							return;

						ghost.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 140, 0, false, false));
						ghost.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
						world.playSound(null, new BlockPos(entity.position()), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1, 1);

						ghost.setPos(slabfish.getX(), slabfish.getY(), slabfish.getZ());
						ghost.moveTo(slabfish.getX(), slabfish.getY(), slabfish.getZ(), slabfish.getYRot(), slabfish.getXRot());
						ghost.setNoAi(slabfish.isNoAi());
						ghost.setAge(slabfish.getAge());
						ghost.setSlabfishType(EnvironmentalSlabfishTypes.GHOST.get().registryName());
						ghost.setSecondsOnFire(0);
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
		if (event.getParentA() instanceof Pig && event.getParentB() instanceof Pig) {
			Pig pig = (Pig) event.getParentA();
			Level world = pig.getCommandSenderWorld();
			int piglets = world.random.nextInt(4);
			for (int i = 0; i < piglets; ++i) {
				Pig baby = EntityType.PIG.create(world);
				if (baby != null) {
					baby.setBaby(true);
					baby.moveTo(pig.getX(), pig.getY(), pig.getZ(), 0.0F, 0.0F);
					world.addFreshEntity(baby);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Wolf wolf) {
			wolf.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(wolf, Animal.class, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntityTypes.DEER.get()));
		} else if (entity instanceof Ocelot ocelot) {
			ocelot.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(ocelot, Animal.class, 10, false, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntityTypes.DUCK.get()));
		} else if (entity instanceof Pig pig) {
			Set<WrappedGoal> goals = pig.goalSelector.availableGoals;
			if (goals.stream().noneMatch((goal) -> goal.getGoal() instanceof HuntTruffleGoal))
				pig.goalSelector.addGoal(2, new HuntTruffleGoal(pig));
			if ((pig.getNavigation() instanceof GroundPathNavigation || pig.getNavigation() instanceof FlyingPathNavigation) && goals.stream().noneMatch((goal) -> goal.getGoal() instanceof TemptGoldenCarrotGoal))
				pig.goalSelector.addGoal(4, new TemptGoldenCarrotGoal(pig, 1.2D, Ingredient.of(Items.GOLDEN_CARROT), false));
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.getEntity();
		Level world = entity.getCommandSenderWorld();
		Random random = world.getRandom();

		if (entity instanceof Pig && entity.isAlive()) {
			IDataManager data = ((IDataManager) entity);
			int huntingtime = data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME);
			BlockPos trufflepos = data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);

			if (huntingtime == 0 || (data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && world.getBlockState(trufflepos).getBlock() != EnvironmentalBlocks.BURIED_TRUFFLE.get())) {
				data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, false);
				if (huntingtime > 0)
					data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, Math.max(-400, -huntingtime));
			} else {
				if (huntingtime > 0) {
					data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, huntingtime - 1);
				} else if (huntingtime < 0) {
					data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, huntingtime + 1);
					if (world.isClientSide() && data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && huntingtime % 10 == 0) {
						double d0 = random.nextGaussian() * 0.02D;
						double d1 = random.nextGaussian() * 0.02D;
						double d2 = random.nextGaussian() * 0.02D;
						world.addParticle(EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
					}
				}
			}

			int sniffsoundtime = data.getValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME);
			data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, sniffsoundtime + 1);
			if (!world.isClientSide() && data.getValue(EnvironmentalDataProcessors.LOOKING_FOR_TRUFFLE) && random.nextInt(60) < sniffsoundtime) {
				entity.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
				data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, -20);
			}
		}
	}
}
