package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.common.entity.DuckEggEntity;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.abnormals_core.core.utils.DataUtils;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnvironmentalCompat {
	
    public static void registerCompostables() {
    	DataUtils.registerCompostable(EnvironmentalItems.YAK_HAIR.get(), 0.30F);
    	DataUtils.registerCompostable(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), 0.85F);
    	DataUtils.registerCompostable(EnvironmentalBlocks.YAK_HAIR_RUG.get(), 0.50F);

        DataUtils.registerCompostable(EnvironmentalBlocks.LARGE_LILY_PAD.get(), 0.85F);
        DataUtils.registerCompostable(EnvironmentalBlocks.GIANT_LILY_PAD.get(), 1.0F);

        DataUtils.registerCompostable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WILLOW_SAPLING.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 0.30F);

        DataUtils.registerCompostable(EnvironmentalBlocks.CHERRY_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CHERRY_SAPLING.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), 0.30F);
        
        DataUtils.registerCompostable(EnvironmentalItems.CHERRIES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalItems.CHERRY_PIE.get(), 1.0F);
        DataUtils.registerCompostable(EnvironmentalItems.APPLE_PIE.get(), 1.0F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CHERRY_CRATE.get(), 1.0F);

        DataUtils.registerCompostable(EnvironmentalItems.RICE.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.RICE_SACK.get(), 1.0F);
        DataUtils.registerCompostable(EnvironmentalItems.CATTAIL_SEEDS.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL_SEED_SACK.get(), 1.0F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.DUCKWEED.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.TALL_CATTAIL.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 0.50F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.TWIG_NEST.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.HAY_NEST.get(), 0.65F);

        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), 0.65F);

        DataUtils.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), 0.65F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.GRASS_THATCH.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), 0.65F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), 0.30F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 0.30F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), 0.30F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), 0.30F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), 0.30F);
        
        DataUtils.registerCompostable(EnvironmentalBlocks.CARTWHEEL.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.VIOLET.get(), 0.65F);		
		DataUtils.registerCompostable(EnvironmentalBlocks.DIANTHUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.BLUEBELL.get(), 0.65F);		
		DataUtils.registerCompostable(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.RED_HIBISCUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.PINK_HIBISCUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), 0.65F);

        DataUtils.registerCompostable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 0.65F);
        DataUtils.registerCompostable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 0.65F);
		DataUtils.registerCompostable(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), 0.65F);
    }

    public static void registerFlammables() {
        DataUtils.registerFlammable(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.YAK_HAIR_RUG.get(), 30, 60);

        DataUtils.registerFlammable(EnvironmentalBlocks.RICE_SACK.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.CATTAIL_SEED_SACK.get(), 30, 60);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.GRASS_THATCH.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), 60, 20);

        DataUtils.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), 60, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), 60, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.TWIG_NEST.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.TWIG_CHICKEN_NEST.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.TWIG_DUCK_NEST.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.HAY_NEST.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.HAY_CHICKEN_NEST.get(), 60, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.HAY_DUCK_NEST.get(), 60, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.CHICKEN_EGG_CRATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.DUCK_EGG_CRATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.TURTLE_EGG_CRATE.get(), 5, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_STAIRS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE_GATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_VERTICAL_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_BOOKSHELF.get(), 30, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_CHERRY_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_STAIRS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_FENCE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_FENCE_GATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_VERTICAL_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_BOOKSHELF.get(), 30, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.CHERRY_CRATE.get(), 5, 20);
        
        DataUtils.registerFlammable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 60, 100);

        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_STAIRS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE_GATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_VERTICAL_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_BOOKSHELF.get(), 30, 20);
	}
	
	public static void registerDispenserBehaviors() {
		Environmental.REGISTRY_HELPER.processSpawnEggDispenseBehaviors();
		DispenserBlock.registerDispenseBehavior(EnvironmentalItems.DUCK_EGG.get(), new ProjectileDispenseBehavior() {
			protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return Util.make(new DuckEggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (egg) -> {
	               egg.setItem(stackIn);
	            });
	         }
	      });
		
        DispenserBlock.registerDispenseBehavior(EnvironmentalItems.SLABFISH_BUCKET.get(), new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BucketItem bucket = (BucketItem)stack.getItem();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                World world = source.getWorld();

                if (bucket.tryPlaceContainedLiquid(null, world, pos, null)) {
                    bucket.onLiquidPlaced(world, stack, pos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return super.dispense(source, stack);
                }
            }
        });
    }
    
    public static void registerUnusedDispenseBehaviors() {
        // TODO make these work properly with tags or smth
        for(Item item : SlabfishEntity.getSweaterMap().keySet()) {
            DispenserBlock.registerDispenseBehavior(item, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

                    for(SlabfishEntity slabfishEntity : source.getWorld().getEntitiesWithinAABB(SlabfishEntity.class, new AxisAlignedBB(blockpos), (entity) -> entity.isAlive() && !entity.hasSweater())) {
                        if (SlabfishEntity.getSweaterMap().containsKey(stack.getItem())) {
                            slabfishEntity.slabfishBackpack.setInventorySlotContents(0, stack.split(1));
                            this.setSuccessful(true);
                            return stack;
                        }
                    }

                    return super.dispenseStack(source, stack);
                }
            });
        }
        
        DispenserBlock.registerDispenseBehavior(Items.CHEST, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

                for(SlabfishEntity slabfishEntity : source.getWorld().getEntitiesWithinAABB(SlabfishEntity.class, new AxisAlignedBB(blockpos), (entity) -> entity.isAlive() && !entity.hasBackpack())) {
                    slabfishEntity.slabfishBackpack.setInventorySlotContents(1, stack.split(1));
                    this.setSuccessful(true);
                    return stack;
                }
                return super.dispenseStack(source, stack);
            }
        });
    }
}
