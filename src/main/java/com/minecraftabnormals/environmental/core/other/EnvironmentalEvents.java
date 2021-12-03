package com.minecraftabnormals.environmental.core.other;

import com.google.common.collect.Sets;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.abnormals_core.core.util.MathUtil;
import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.entity.goals.HuntTruffleGoal;
import com.minecraftabnormals.environmental.common.entity.goals.LayEggInNestGoal;
import com.minecraftabnormals.environmental.common.entity.goals.TemptGoldenCarrotGoal;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalParticles;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
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

import java.util.*;

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
		IWorld world = event.getWorld();
		Random random = world.getRandom();

		boolean naturalSpawn = event.getSpawnReason() == SpawnReason.NATURAL;
		boolean chunkGenSpawn = event.getSpawnReason() == SpawnReason.CHUNK_GENERATION;
		boolean validSpawn = naturalSpawn || chunkGenSpawn;

		boolean replaceVariants = EnvironmentalConfig.COMMON.biomeVariantsAlwaysSpawn.get();

		if (event.getResult() != Event.Result.DENY && world instanceof IServerWorld) {
			IServerWorld serverWorld = (IServerWorld) world;
			if (replaceVariants && validSpawn && entity.getY() > 60) {
				if (entity.getType() == EntityType.ZOMBIE) {
					ZombieEntity zombie = (ZombieEntity) entity;
					if (world.getBiome(entity.blockPosition()).getBiomeCategory() == Biome.Category.DESERT) {


						HuskEntity husk = EntityType.HUSK.create(serverWorld.getLevel());
						husk.setBaby(zombie.isBaby());
						for (EquipmentSlotType slot : EquipmentSlotType.values())
							zombie.setItemSlot(slot, zombie.getItemBySlot(slot));
						husk.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.yRot, zombie.xRot);

						world.addFreshEntity(husk);
						entity.remove();
					}
				}

				if (entity.getType() == EntityType.SKELETON) {
					SkeletonEntity skeleton = (SkeletonEntity) entity;
					if (world.getBiome(entity.blockPosition()).getBiomeCategory() == Biome.Category.ICY) {
						StrayEntity stray = EntityType.STRAY.create(serverWorld.getLevel());
						for (EquipmentSlotType slot : EquipmentSlotType.values())
							skeleton.setItemSlot(slot, skeleton.getItemBySlot(slot));
						stray.moveTo(skeleton.getX(), skeleton.getY(), skeleton.getZ(), skeleton.yRot, skeleton.xRot);

						world.addFreshEntity(stray);
						entity.remove();
					}
				}
			}

			if (validSpawn && entity.getType() == EntityType.MOOSHROOM) {
				MooshroomEntity mooshroom = (MooshroomEntity) event.getEntity();
				if (random.nextInt(3) == 0) {
					MooshroomEntity brownMooshroom = EntityType.MOOSHROOM.create(serverWorld.getLevel());
					brownMooshroom.moveTo(mooshroom.getX(), mooshroom.getY(), mooshroom.getZ(), mooshroom.yRot, mooshroom.xRot);
					brownMooshroom.setMushroomType(MooshroomEntity.Type.BROWN);
					world.addFreshEntity(brownMooshroom);
					entity.remove();
				}
			}
		}

		boolean blockOnlyNaturalSpawns = EnvironmentalConfig.COMMON.blockOnlyNaturalSpawns.get();

		if (blockOnlyNaturalSpawns && event.isSpawner()) return;
		if (!EnvironmentalTags.EntityTypes.UNAFFECTED_BY_SERENITY.contains(entity.getType())) {
			if (entity.getType().getCategory() == EntityClassification.MONSTER) {
				int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
				int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
				for (Entity koi : world.getEntitiesOfClass(KoiEntity.class, entity.getBoundingBox().inflate(horizontalRange, verticalRange, horizontalRange))) {
					if (MathUtil.distanceBetweenPoints2d(entity.getX(), entity.getZ(), koi.getX(), koi.getZ()) <= horizontalRange) {
						event.setResult(Event.Result.DENY);
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onProjectileImpact(final ProjectileImpactEvent.Throwable event) {
		ThrowableEntity projectileEntity = event.getThrowable();

		if (projectileEntity instanceof PotionEntity) {
			PotionEntity potionEntity = ((PotionEntity) projectileEntity);
			ItemStack itemstack = potionEntity.getItem();
			Potion potion = PotionUtils.getPotion(itemstack);
			List<EffectInstance> list = PotionUtils.getMobEffects(itemstack);

			if (potion == Potions.WATER && list.isEmpty()) {
				AxisAlignedBB axisalignedbb = potionEntity.getBoundingBox().inflate(2.0D, 1.0D, 2.0D);
				List<SlabfishEntity> slabs = potionEntity.level.getEntitiesOfClass(SlabfishEntity.class, axisalignedbb);
				if (!slabs.isEmpty()) {
					for (SlabfishEntity slabfish : slabs) {
						slabfish.setSlabfishOverlay(SlabfishOverlay.NONE);
					}
				}
			}
		}

		if (projectileEntity instanceof ProjectileItemEntity) {
			ProjectileItemEntity projectileitem = (ProjectileItemEntity) projectileEntity;
			if (event.getRayTraceResult() != null && event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
				EntityRayTraceResult entity = (EntityRayTraceResult) event.getRayTraceResult();
				if (entity.getEntity() instanceof SlabfishEntity) {
					SlabfishEntity slabfish = (SlabfishEntity) entity.getEntity();
					Item item = projectileitem.getItem().getItem();
					if (item == Items.SNOWBALL)
						slabfish.setSlabfishOverlay(SlabfishOverlay.SNOWY);
					else if (item.is(Tags.Items.EGGS))
						slabfish.setSlabfishOverlay(SlabfishOverlay.EGG);
				}
			}
		}
	}

	protected static final Set<Block> DIRT_SPREADABLES = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.MYCELIUM);

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		Direction face = event.getFace();
		PlayerEntity player = event.getPlayer();
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
				event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide()));
				event.setCanceled(true);
			}
		} else if (item instanceof ShovelItem && !player.isSpectator() && state.is(EnvironmentalBlocks.BURIED_TRUFFLE.get())) {
			Vector3i vector3i = face.getNormal();
			double d0 = pos.getX() + 0.5D + 0.625D * vector3i.getX();
			double d1 = pos.getY() + 0.375D + 0.625D * vector3i.getY();
			double d2 = pos.getZ() + 0.5D + 0.625D * vector3i.getZ();
			ItemEntity itementity = new ItemEntity(world, d0, d1, d2, new ItemStack(EnvironmentalItems.TRUFFLE.get()));
			world.addFreshEntity(itementity);

			world.playSound(player, pos, EnvironmentalSounds.SHOVEL_DIG.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			stack.hurtAndBreak(1, player, (damage) -> {
				damage.broadcastBreakEvent(event.getHand());
			});
			world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 11);
			event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide()));
			event.setCanceled(true);
		} else if (event.getFace() != Direction.DOWN) {
			if (item instanceof ShovelItem && !player.isSpectator() && world.isEmptyBlock(pos.above())) {
				if (state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM)) {
					world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					stack.hurtAndBreak(1, player, (damage) -> {
						damage.broadcastBreakEvent(event.getHand());
					});
					world.setBlock(pos, state.is(Blocks.PODZOL) ? EnvironmentalBlocks.PODZOL_PATH.get().defaultBlockState() : EnvironmentalBlocks.MYCELIUM_PATH.get().defaultBlockState(), 11);
					event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		World world = event.getWorld();
		Random random = world.getRandom();

		if (target instanceof SlabfishEntity && stack.getItem() == Items.NAME_TAG) {
			SlabfishEntity slabby = (SlabfishEntity) event.getTarget();
			if (stack.hasCustomHoverName()) {
				if (!slabby.hasCustomName() || slabby.getCustomName() == null || !slabby.getCustomName().getString().equals(stack.getHoverName().getString())) {
					slabby.playTransformSound();
				}
			}
		}

		if (target instanceof PigEntity && stack.getItem() == Items.GOLDEN_CARROT) {
			if (target.isAlive() && !((PigEntity) target).isBaby()) {
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
								world.addParticle(EnvironmentalParticles.PIG_FINDS_TRUFFLE.get(), target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
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

					event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getCommandSenderWorld();
		Random rand = new Random();

		if (entity instanceof SlabfishEntity) {
			SlabfishEntity slabfish = (SlabfishEntity) event.getEntity();
			if (world.getBiomeName(new BlockPos(entity.position())).equals(Optional.of(Biomes.SOUL_SAND_VALLEY))) {
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
						SlabfishEntity ghost = EnvironmentalEntities.SLABFISH.get().create(world);
						if (ghost == null)
							return;

						ghost.addEffect(new EffectInstance(Effects.LEVITATION, 140, 0, false, false));
						ghost.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 0, false, false));
						world.playSound(null, new BlockPos(entity.position()), SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1, 1);

						ghost.setPos(slabfish.getX(), slabfish.getY(), slabfish.getZ());
						ghost.moveTo(slabfish.getX(), slabfish.getY(), slabfish.getZ(), slabfish.yRot, slabfish.xRot);
						ghost.setNoAi(slabfish.isNoAi());
						ghost.setAge(slabfish.getAge());
						ghost.setSlabfishType(SlabfishManager.GHOST);
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
		if (event.getParentA() instanceof PigEntity && event.getParentB() instanceof PigEntity) {
			PigEntity pig = (PigEntity) event.getParentA();
			World world = pig.getCommandSenderWorld();
			int piglets = world.random.nextInt(4);
			for (int i = 0; i < piglets; ++i) {
				PigEntity baby = EntityType.PIG.create(world);
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
		if (entity instanceof IEggLayingEntity && entity instanceof AnimalEntity) {
			AnimalEntity eggLayer = (AnimalEntity) entity;
			if (eggLayer.goalSelector.availableGoals.stream().noneMatch((goal) -> goal.getGoal() instanceof LayEggInNestGoal)) {
				eggLayer.goalSelector.addGoal(3, new LayEggInNestGoal(eggLayer, 1.0D));
			}
		} else if (entity instanceof WolfEntity) {
			WolfEntity wolf = (WolfEntity) entity;
			wolf.targetSelector.addGoal(4, new NonTamedTargetGoal<>(wolf, AnimalEntity.class, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntities.DEER.get()));
		} else if (entity instanceof OcelotEntity) {
			OcelotEntity ocelot = (OcelotEntity) entity;
			ocelot.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(ocelot, AnimalEntity.class, 10, false, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntities.DUCK.get()));
		} else if (entity instanceof PigEntity) {
			PigEntity pig = (PigEntity) entity;
			Set<PrioritizedGoal> goals = pig.goalSelector.availableGoals;
			if (goals.stream().noneMatch((goal) -> goal.getGoal() instanceof HuntTruffleGoal))
				pig.goalSelector.addGoal(2, new HuntTruffleGoal(pig));
			if (goals.stream().noneMatch((goal) -> goal.getGoal() instanceof TemptGoldenCarrotGoal))
				pig.goalSelector.addGoal(4, new TemptGoldenCarrotGoal(pig, 1.2D, false, Ingredient.of(Items.GOLDEN_CARROT)));
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.getEntity();
		World world = entity.getCommandSenderWorld();
		Random random = world.getRandom();

		if (entity instanceof PigEntity && entity.isAlive()) {
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
						world.addParticle(EnvironmentalParticles.PIG_FINDS_TRUFFLE.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
					}
				}
			}

			int sniffsoundtime = data.getValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME);
			data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, sniffsoundtime + 1);
			if (!world.isClientSide() && data.getValue(EnvironmentalDataProcessors.LOOKING_FOR_TRUFFLE) && random.nextInt(60) < sniffsoundtime) {
				entity.playSound(EnvironmentalSounds.PIG_SNIFF.get(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
				data.setValue(EnvironmentalDataProcessors.SNIFF_SOUND_TIME, -20);
			}
		}
	}
}
