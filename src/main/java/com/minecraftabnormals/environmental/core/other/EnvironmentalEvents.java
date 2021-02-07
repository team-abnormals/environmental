package com.minecraftabnormals.environmental.core.other;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.minecraftabnormals.abnormals_core.core.util.MathUtil;
import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.entity.goals.LayEggInNestGoal;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
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
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
	public static void onEvent(PlayerEvent.BreakSpeed event) {
		if (event.getState().getBlock() instanceof HangingWisteriaLeavesBlock && event.getPlayer().getHeldItemMainhand().getItem() == Items.SHEARS)
			event.setNewSpeed(15.0F);
	}

	@SubscribeEvent
	public static void onEvent(LivingSpawnEvent.CheckSpawn event) {
		Entity entity = event.getEntity();
		IWorld world = event.getWorld();
		Random random = world.getRandom();

		boolean naturalSpawn = event.getSpawnReason() == SpawnReason.NATURAL;
		boolean chunkGenSpawn = event.getSpawnReason() == SpawnReason.CHUNK_GENERATION;
		boolean validSpawn = naturalSpawn || chunkGenSpawn;

		boolean replaceVariants = EnvironmentalConfig.COMMON.biomeVariantsAlwaysSpawn.get();

		if (event.getResult() != Event.Result.DENY && world instanceof IServerWorld) {
			IServerWorld serverWorld = (IServerWorld) world;
			if (replaceVariants && validSpawn && entity.getPosY() > 60) {
				if (entity.getType() == EntityType.ZOMBIE) {
					ZombieEntity zombie = (ZombieEntity) entity;
					if (world.getBiome(entity.getPosition()).getCategory() == Biome.Category.DESERT) {


						HuskEntity husk = EntityType.HUSK.create(serverWorld.getWorld());
						husk.setChild(zombie.isChild());
						for (EquipmentSlotType slot : EquipmentSlotType.values())
							zombie.setItemStackToSlot(slot, zombie.getItemStackFromSlot(slot));
						husk.setLocationAndAngles(zombie.getPosX(), zombie.getPosY(), zombie.getPosZ(), zombie.rotationYaw, zombie.rotationPitch);

						world.addEntity(husk);
						entity.remove();
					}
				}

				if (entity.getType() == EntityType.SKELETON) {
					SkeletonEntity skeleton = (SkeletonEntity) entity;
					if (world.getBiome(entity.getPosition()).getCategory() == Biome.Category.ICY) {
						StrayEntity stray = EntityType.STRAY.create(serverWorld.getWorld());
						for (EquipmentSlotType slot : EquipmentSlotType.values())
							skeleton.setItemStackToSlot(slot, skeleton.getItemStackFromSlot(slot));
						stray.setLocationAndAngles(skeleton.getPosX(), skeleton.getPosY(), skeleton.getPosZ(), skeleton.rotationYaw, skeleton.rotationPitch);

						world.addEntity(stray);
						entity.remove();
					}
				}
			}

			if (validSpawn && entity.getType() == EntityType.MOOSHROOM) {
				MooshroomEntity mooshroom = (MooshroomEntity) event.getEntity();
				if (random.nextInt(3) == 0) {
					MooshroomEntity brownMooshroom = EntityType.MOOSHROOM.create(serverWorld.getWorld());
					brownMooshroom.setLocationAndAngles(mooshroom.getPosX(), mooshroom.getPosY(), mooshroom.getPosZ(), mooshroom.rotationYaw, mooshroom.rotationPitch);
					brownMooshroom.setMooshroomType(MooshroomEntity.Type.BROWN);
					world.addEntity(brownMooshroom);
					entity.remove();
				}
			}
		}

		boolean blockOnlyNaturalSpawns = EnvironmentalConfig.COMMON.blockOnlyNaturalSpawns.get();

		if (blockOnlyNaturalSpawns && event.isSpawner()) return;
		if (!EnvironmentalTags.EntityTypes.SERENITY_WHITELIST.contains(entity.getType())) {
			if (entity.getType().getClassification() == EntityClassification.MONSTER) {
				int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
				int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
				for (Entity koi : world.getEntitiesWithinAABB(KoiEntity.class, entity.getBoundingBox().grow(horizontalRange, verticalRange, horizontalRange))) {
					if (MathUtil.distanceBetweenPoints2d(entity.getPosX(), entity.getPosZ(), koi.getPosX(), koi.getPosZ()) <= horizontalRange) {
						event.setResult(Event.Result.DENY);
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(final ProjectileImpactEvent.Throwable event) {
		ThrowableEntity projectileEntity = event.getThrowable();

		if (projectileEntity instanceof PotionEntity) {
			PotionEntity potionEntity = ((PotionEntity) projectileEntity);
			ItemStack itemstack = potionEntity.getItem();
			Potion potion = PotionUtils.getPotionFromItem(itemstack);
			List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemstack);

			if (potion == Potions.WATER && list.isEmpty()) {
				AxisAlignedBB axisalignedbb = potionEntity.getBoundingBox().grow(2.0D, 1.0D, 2.0D);
				List<SlabfishEntity> slabs = potionEntity.world.getEntitiesWithinAABB(SlabfishEntity.class, axisalignedbb);
				if (slabs != null && slabs.size() > 0) {
					for (SlabfishEntity slabfish : slabs) {
						slabfish.setSlabfishOverlay(SlabfishOverlay.NONE);
					}
				}
			}
		}

		if (projectileEntity instanceof ProjectileItemEntity) {
			ProjectileItemEntity projectileitem = (ProjectileItemEntity) projectileEntity;
			if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
				EntityRayTraceResult entity = (EntityRayTraceResult) event.getRayTraceResult();
				if (entity.getEntity() instanceof SlabfishEntity) {
					SlabfishEntity slabfish = (SlabfishEntity) entity.getEntity();
					Item item = projectileitem.getItem().getItem();
					if (item == Items.SNOWBALL)
						slabfish.setSlabfishOverlay(SlabfishOverlay.SNOWY);
					else if (item.isIn(Tags.Items.EGGS))
						slabfish.setSlabfishOverlay(SlabfishOverlay.EGG);
				}
			}
		}
	}

	protected static final Set<Block> DIRT_SPREADABLES = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.MYCELIUM);

	@SubscribeEvent
	public static void onEvent(RightClickBlock event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = event.getPlayer();
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		if (item == Items.BONE_MEAL && state.getBlock() == Blocks.DIRT && world.getBlockState(pos.up()).propagatesSkylightDown(world, pos)) {
			ArrayList<BlockState> potentialStates = new ArrayList<>();
			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
				Block block = world.getBlockState(blockpos).getBlock();
				if (DIRT_SPREADABLES.contains(block) && !potentialStates.contains(block.getDefaultState())) {
					potentialStates.add(block.getDefaultState());
				}
			}
			if (!potentialStates.isEmpty()) {
				if (!world.isRemote()) {
					world.playEvent(2005, pos, 0);
					world.setBlockState(pos, potentialStates.get(world.getRandom().nextInt(potentialStates.size())), 3);
				}
				if (!player.isCreative()) stack.shrink(1);
				event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
				event.setCanceled(true);
			}
		} else if (event.getFace() != Direction.DOWN) {
			if (item instanceof ShovelItem && !player.isSpectator() && world.isAirBlock(pos.up())){
				if (state.isIn(Blocks.PODZOL) || state.isIn(Blocks.MYCELIUM)) {
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					stack.damageItem(1, player, (damage) -> {
						damage.sendBreakAnimation(event.getHand());
					});
					world.setBlockState(pos, state.isIn(Blocks.PODZOL) ? EnvironmentalBlocks.PODZOL_PATH.get().getDefaultState() : EnvironmentalBlocks.MYCELIUM_PATH.get().getDefaultState(), 11);
					event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(PlayerInteractEvent.EntityInteract event) {
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		if (target instanceof SlabfishEntity && stack.getItem() == Items.NAME_TAG) {
			SlabfishEntity slabby = (SlabfishEntity) event.getTarget();
			if (stack.hasDisplayName()) {
				if (!slabby.hasCustomName() || slabby.getCustomName() == null || !slabby.getCustomName().getString().equals(stack.getDisplayName().getString())) {
					slabby.playTransformSound();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getEntityWorld();
		Random rand = new Random();

		if (entity instanceof SlabfishEntity) {
			SlabfishEntity slabfish = (SlabfishEntity) event.getEntity();
			if (world.func_242406_i(new BlockPos(entity.getPositionVec())).equals(Optional.of(Biomes.SOUL_SAND_VALLEY))) {
				if (!slabfish.getSlabfishType().equals(SlabfishManager.GHOST)) {
					if (world.isRemote()) {
						for (int i = 0; i < 7; ++i) {
							double d0 = rand.nextGaussian() * 0.02D;
							double d1 = rand.nextGaussian() * 0.02D;
							double d2 = rand.nextGaussian() * 0.02D;
							world.addParticle(ParticleTypes.SOUL, entity.getPosXRandom(1.0D), entity.getPosYRandom() + 0.5D, entity.getPosZRandom(1.0D), d0, d1, d2);
						}
					}

					if (!world.isRemote()) {
						SlabfishEntity ghost = EnvironmentalEntities.SLABFISH.get().create(world);
						if (ghost == null)
							return;

						ghost.addPotionEffect(new EffectInstance(Effects.LEVITATION, 140, 0, false, false));
						ghost.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 0, false, false));
						world.playSound(null, new BlockPos(entity.getPositionVec()), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1, 1);

						ghost.setPosition(slabfish.getPosX(), slabfish.getPosY(), slabfish.getPosZ());
						ghost.setLocationAndAngles(slabfish.getPosX(), slabfish.getPosY(), slabfish.getPosZ(), slabfish.rotationYaw, slabfish.rotationPitch);
						ghost.setNoAI(slabfish.isAIDisabled());
						ghost.setGrowingAge(slabfish.getGrowingAge());
						ghost.setSlabfishType(SlabfishManager.GHOST);
						ghost.setFire(0);
						if (slabfish.hasCustomName()) {
							ghost.setCustomName(entity.getCustomName());
							ghost.setCustomNameVisible(entity.isCustomNameVisible());
						}

						world.addEntity(ghost);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEvent(EnteringChunk event) {
		Entity entity = event.getEntity();
		if (entity instanceof IEggLayingEntity && entity instanceof AnimalEntity) {
			AnimalEntity eggLayer = (AnimalEntity) entity;
			if (eggLayer.goalSelector.goals.stream().noneMatch((goal) -> goal.getGoal() instanceof LayEggInNestGoal)) {
				eggLayer.goalSelector.addGoal(3, new LayEggInNestGoal(eggLayer, 1.0D));
			}
		} else if (entity instanceof WolfEntity) {
			WolfEntity wolf = (WolfEntity) entity;
			wolf.targetSelector.addGoal(4, new NonTamedTargetGoal<>(wolf, AnimalEntity.class, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntities.DEER.get()));
		} else if (entity instanceof OcelotEntity) {
			OcelotEntity ocelot = (OcelotEntity) entity;
			ocelot.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(ocelot, AnimalEntity.class, 10, false, false, (targetEntity) -> targetEntity.getType() == EnvironmentalEntities.DUCK.get()));
		}
	}
}
