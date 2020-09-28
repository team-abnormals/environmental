package com.minecraftabnormals.environmental.core.other;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.entity.goals.ChickenLayEggInNestGoal;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalEvents {
    private static final Set<ResourceLocation> RICE_SHIPWRECK_LOOT_INJECTIONS = Sets.newHashSet(LootTables.CHESTS_SHIPWRECK_SUPPLY);

    @SubscribeEvent
    public static void onInjectLoot(LootTableLoadEvent event) {
        if (RICE_SHIPWRECK_LOOT_INJECTIONS.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(Environmental.MODID, "injections/rice_shipwreck")).weight(1).quality(0)).name("rice_shipwreck").build();
            event.getTable().addPool(pool);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() instanceof HangingWisteriaLeavesBlock && event.getPlayer().getHeldItemMainhand().getItem() == Items.SHEARS)
            event.setNewSpeed(15.0F);
    }

    @SubscribeEvent
    public static void livingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        Entity entity = event.getEntity();
        boolean naturalSpawn = event.getSpawnReason() == SpawnReason.NATURAL;
        boolean chunkGenSpawn = event.getSpawnReason() == SpawnReason.CHUNK_GENERATION;
        boolean validSpawn = naturalSpawn || chunkGenSpawn;
        boolean replaceVariants = EnvironmentalConfig.COMMON.biomeVariantsAlwaysSpawn.get();
        
        if (replaceVariants && validSpawn && entity.getPosY() > 60 && entity.getType() == EntityType.ZOMBIE) {
            ZombieEntity zombie = (ZombieEntity) event.getEntity();
            if (event.getWorld().getBiome(entity.getPosition()).getCategory() == Biome.Category.DESERT) {

                HuskEntity husk = EntityType.HUSK.create(event.getWorld().getWorld());
                husk.setChild(zombie.isChild());
                for (EquipmentSlotType slot : EquipmentSlotType.values())
                    zombie.setItemStackToSlot(slot, zombie.getItemStackFromSlot(slot));
                husk.setLocationAndAngles(zombie.getPosX(), zombie.getPosY(), zombie.getPosZ(), zombie.rotationYaw, zombie.rotationPitch);

                event.getWorld().addEntity(husk);
                event.getEntity().remove();
            }
        }

        if (replaceVariants && validSpawn && entity.getPosY() > 60 && entity.getType() == EntityType.SKELETON) {
            SkeletonEntity zombie = (SkeletonEntity) event.getEntity();
            if (event.getWorld().getBiome(entity.getPosition()).getCategory() == Biome.Category.ICY) {

                StrayEntity husk = EntityType.STRAY.create(event.getWorld().getWorld());
                for (EquipmentSlotType slot : EquipmentSlotType.values())
                    zombie.setItemStackToSlot(slot, zombie.getItemStackFromSlot(slot));
                husk.setLocationAndAngles(zombie.getPosX(), zombie.getPosY(), zombie.getPosZ(), zombie.rotationYaw, zombie.rotationPitch);

                event.getWorld().addEntity(husk);
                event.getEntity().remove();
            }
        }
        
        if (validSpawn && entity.getType() == EntityType.MOOSHROOM) {
            MooshroomEntity mooshroom = (MooshroomEntity) event.getEntity();
            Random random = new Random();
            if (random.nextInt(3) == 0) {
                MooshroomEntity brownMooshroom = EntityType.MOOSHROOM.create(event.getWorld().getWorld());
                brownMooshroom.setLocationAndAngles(mooshroom.getPosX(), mooshroom.getPosY(), mooshroom.getPosZ(), mooshroom.rotationYaw, mooshroom.rotationPitch);
                brownMooshroom.setMooshroomType(MooshroomEntity.Type.BROWN);
                event.getWorld().addEntity(brownMooshroom);
                event.getEntity().remove();
            }
        }
    }

    @SubscribeEvent
    public static void onThrowableImpact(final ProjectileImpactEvent.Throwable event) {
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
                    else if (item == Items.EGG || item == EnvironmentalItems.DUCK_EGG.get()) slabfish.setSlabfishOverlay(SlabfishOverlay.EGG);
                }
            }
        }
    }

    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

	@SubscribeEvent
    public static void underwaterHoe(UseHoeEvent event) {
        ItemStack hoe = event.getContext().getItem();

        //if (event.getResult() == Result.ALLOW) {
        World world = event.getContext().getWorld();
        BlockPos blockpos = event.getContext().getPos();
        if (event.getContext().getFace() != Direction.DOWN) {
            BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
            if (blockstate != null) {
                PlayerEntity playerentity = event.getPlayer();
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                playerentity.swingArm(event.getContext().getHand());
                if (!world.isRemote) {
                    world.setBlockState(blockpos, blockstate, 11);
                    if (playerentity != null) {
                        hoe.damageItem(1, playerentity, (anim) -> {
                            anim.sendBreakAnimation(event.getContext().getHand());
                        });
                    }
                }
            }
        }
        //}
    }
    
    @SubscribeEvent
    public static void rightClickBlock(RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        if (event.getFace() != Direction.DOWN && item instanceof ShovelItem && !player.isSpectator() && world.isAirBlock(pos.up())) {
            if (state.isIn(Blocks.PODZOL) || state.isIn(Blocks.MYCELIUM)) {
                player.swingArm(event.getHand());
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                event.getItemStack().damageItem(1, player, (damage) -> {
                    damage.sendBreakAnimation(event.getHand());
                });
                world.setBlockState(pos, state.isIn(Blocks.PODZOL) ? EnvironmentalBlocks.PODZOL_PATH.get().getDefaultState() : EnvironmentalBlocks.MYCELIUM_PATH.get().getDefaultState(), 11);
            }
        }
    }

    @SubscribeEvent
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteract event) {
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

    @Deprecated
    @SubscribeEvent
    public static void onSlabfishDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof SlabfishEntity) {
            SlabfishEntity entity = (SlabfishEntity) event.getEntity();
            if (entity.getEntityWorld().getBiome(new BlockPos(entity.getPositionVec())) == Biomes.SOUL_SAND_VALLEY) {
                if (!entity.getSlabfishType().equals(SlabfishManager.GHOST)) {
                    if (entity.getEntityWorld().isRemote()) {
                        Random rand = new Random();

                        for (int i = 0; i < 7; ++i) {
                            double d0 = rand.nextGaussian() * 0.02D;
                            double d1 = rand.nextGaussian() * 0.02D;
                            double d2 = rand.nextGaussian() * 0.02D;
                            entity.world.addParticle(ParticleTypes.SOUL, entity.getPosXRandom(1.0D), entity.getPosYRandom() + 0.5D, entity.getPosZRandom(1.0D), d0, d1, d2);
                        }
                    }

                    if (!entity.getEntityWorld().isRemote()) {
                        SlabfishEntity ghost = EnvironmentalEntities.SLABFISH.get().create(entity.world);
                        if (ghost == null)
                            return;

                        ghost.addPotionEffect(new EffectInstance(Effects.LEVITATION, 140, 0, false, false));
                        ghost.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 0, false, false));
                        entity.getEntityWorld().playSound(null, new BlockPos(entity.getPositionVec()), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1, 1);

                        ghost.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
                        ghost.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
                        ghost.setNoAI(entity.isAIDisabled());
                        ghost.setGrowingAge(entity.getGrowingAge());
                        ghost.setSlabfishType(SlabfishManager.GHOST);
                        ghost.setFire(0);
                        if (entity.hasCustomName()) {
                            ghost.setCustomName(entity.getCustomName());
                            ghost.setCustomNameVisible(entity.isCustomNameVisible());
                        }

                        entity.getEntityWorld().addEntity(ghost);
                    }
                }
            }
        }
    }
    
	@SubscribeEvent
	public static void onEnterChunk(EnteringChunk event) {
		if(event.getEntity() instanceof ChickenEntity) {
			ChickenEntity chicken = (ChickenEntity)event.getEntity();

			if(!chicken.goalSelector.goals.stream().anyMatch((goal) -> goal.getGoal() instanceof ChickenLayEggInNestGoal)) {
				chicken.goalSelector.addGoal(2, new ChickenLayEggInNestGoal(chicken, 1.0D));
			}
		}
	}
}

