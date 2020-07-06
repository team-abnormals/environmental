package com.team_abnormals.environmental.core.other;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.team_abnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.entity.util.SlabfishOverlay;
import com.team_abnormals.environmental.common.entity.util.SlabfishType;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
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
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
        if (event.getState().getBlock() instanceof HangingWisteriaLeavesBlock && event.getPlayer().getHeldItemMainhand().getItem() == Items.SHEARS) event.setNewSpeed(15.0F);
    }
	
	
	@SubscribeEvent
    @OnlyIn(Dist.CLIENT)
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
                if(slabs != null && slabs.size() > 0) {
                    for (SlabfishEntity slabfish : slabs) {
                    	slabfish.setSlabfishOverlay(SlabfishOverlay.NONE);
                    }
                }
            }
        }
        
        if (projectileEntity instanceof ProjectileItemEntity) {
        	ProjectileItemEntity snowball = (ProjectileItemEntity)projectileEntity;
        	if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
    			EntityRayTraceResult entity = (EntityRayTraceResult)event.getRayTraceResult();
    			if (entity.getEntity() instanceof SlabfishEntity) {
    				SlabfishEntity slabfish = (SlabfishEntity)entity.getEntity();
    				if (snowball.getItem().getItem() == Items.SNOWBALL) slabfish.setSlabfishOverlay(SlabfishOverlay.SNOWY);
        			if (snowball.getItem().getItem() == Items.EGG) slabfish.setSlabfishOverlay(SlabfishOverlay.EGG);
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
		            		hoe.damageItem(1, playerentity, (anim) -> { anim.sendBreakAnimation(event.getContext().getHand()); });
		            	}
		            }
				}
			}
		//}
	}
	
//	@SubscribeEvent
//	public static void bonemealLilypad(RightClickBlock event) {
//		BlockPos blockPos = event.getPos();
//		Random random = new Random();
//		World world = event.getWorld();
//		BlockState state = world.getBlockState(blockPos);
//		if (state.getBlock() == Blocks.LILY_PAD && event.getItemStack().getItem() == Items.BONE_MEAL) {
//            if (!event.getPlayer().abilities.isCreativeMode) event.getItemStack().shrink(1);
//			event.getPlayer().swingArm(event.getHand());
//            BoneMealItem.spawnBonemealParticles(world, blockPos, 2);
//            
//			label:
//				for(int x = 0; x < 64; ++x) {
//					BlockPos newBlockPos = blockPos;
//					for(int y = 0; y < x / 16; ++y) {
//						newBlockPos = newBlockPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
//	                    if (state.isValidPosition(world, newBlockPos) && world.isAirBlock(newBlockPos)) {
//	                        world.setBlockState(newBlockPos, state);
//	                        break label;
//	                    }
//	                }
//	            }
//		}
//	}
	
	@SubscribeEvent
	public static void onInteractWithEntity(PlayerInteractEvent.EntityInteract event){
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		if (target instanceof SlabfishEntity && stack.getItem() == Items.NAME_TAG) {
			SlabfishEntity slabby = (SlabfishEntity)event.getTarget();
			if (stack.hasDisplayName()) {
				if (slabby.hasCustomName() && slabby.getCustomName() != stack.getDisplayName()) {
					slabby.playTransformSound();
				} else if (!slabby.hasCustomName()) {
					slabby.playTransformSound();
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void SlabfishDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof SlabfishEntity) {
			SlabfishEntity entity = (SlabfishEntity)event.getEntity();
			if (entity.getEntityWorld().getBiome(new BlockPos(entity.getPositionVec())) == Biomes.field_235252_ay_) {
				if (!entity.getEntityWorld().isRemote && entity.getSlabfishType() != SlabfishType.GHOST) {
					SlabfishEntity ghost = EnvironmentalEntities.SLABFISH.get().create(entity.world);					
					ghost.addPotionEffect(new EffectInstance(Effects.LEVITATION, 140, 0, false, false));
					ghost.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 0, false, false));
					entity.getEntityWorld().playSound(null, new BlockPos(entity.getPositionVec()), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1, 1);
					ghost.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
					ghost.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
					ghost.setNoAI(((MobEntity) entity).isAIDisabled());
		    		ghost.setGrowingAge(entity.getGrowingAge());
		    		if(entity.hasCustomName()) {
		    			ghost.setCustomName(entity.getCustomName());
		    			ghost.setCustomNameVisible(entity.isCustomNameVisible());
		    		}
		    		ghost.setSlabfishType(SlabfishType.GHOST);
					ghost.setFire(0);
					entity.getEntityWorld().addEntity(ghost);
				}
			}
		}
	}
}

