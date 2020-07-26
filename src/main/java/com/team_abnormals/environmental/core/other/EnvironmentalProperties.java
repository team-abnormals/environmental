package com.team_abnormals.environmental.core.other;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class EnvironmentalProperties {
	public static final AbstractBlock.Properties WILLOW_PLANKS 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_DOOR 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_BUTTON 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_PRESSURE_PLATE 	= AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_LADDER 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties WILLOW_BOOKSHELF 		= AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_LEAVES 			= AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).notSolid().tickRandomly().sound(SoundType.PLANT);
	public static final AbstractBlock.Properties WILLOW_LOG 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_SAPLING 		= AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	
	public static final AbstractBlock.Properties CHERRY_PLANKS 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_DOOR 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_BUTTON 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_PRESSURE_PLATE 	= AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LADDER 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties CHERRY_BOOKSHELF 		= AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LEAVES 			= AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).notSolid().tickRandomly().sound(SoundType.PLANT);
	public static final AbstractBlock.Properties CHERRY_LOG 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_SAPLING 		= AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	
	public static final AbstractBlock.Properties WISTERIA_PLANKS 		= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties PINK_WISTERIA_LEAVES 	= AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.PINK).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).setPropagatesDownwards(PropertyExtensions::canSpawn).setSuffocates(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	public static final AbstractBlock.Properties BLUE_WISTERIA_LEAVES 	= AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.BLUE).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).setPropagatesDownwards(PropertyExtensions::canSpawn).setSuffocates(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	public static final AbstractBlock.Properties PURPLE_WISTERIA_LEAVES = AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.PURPLE).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).setPropagatesDownwards(PropertyExtensions::canSpawn).setSuffocates(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	public static final AbstractBlock.Properties WHITE_WISTERIA_LEAVES 	= AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.SNOW).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).setPropagatesDownwards(PropertyExtensions::canSpawn).setSuffocates(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	public static final AbstractBlock.Properties WISTERIA_BUTTON 		= AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_DOOR 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_LOG 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties STRIPPED_WISTERIA_LOG 	= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_LADDER 		= AbstractBlock.Properties.create(Material.MISCELLANEOUS, MaterialColor.WHITE_TERRACOTTA).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties WISTERIA_BOOKSHELF 	= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_SAPLING 		= AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);

	public static final AbstractBlock.Properties CATTAIL 	= AbstractBlock.Properties.create(Material.TALL_PLANTS).hardnessAndResistance(0.0F).doesNotBlockMovement().tickRandomly().sound(SoundType.WET_GRASS);
	public static final AbstractBlock.Properties RICE 		= AbstractBlock.Properties.create(Material.TALL_PLANTS).hardnessAndResistance(0.0F).doesNotBlockMovement().tickRandomly().sound(SoundType.CROP);
	
	public static final AbstractBlock.Properties DUCKWEED 			= AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).doesNotBlockMovement().sound(SoundType.CROP);
	public static final AbstractBlock.Properties MYCELIUM_SPROUTS 	= AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.field_235591_M_);
	
	public static final AbstractBlock.Properties FLOWER 		= AbstractBlock.Properties.create(Material.PLANTS).notSolid().doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT);
	public static final AbstractBlock.Properties DELPHINIUMS 	= AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	public static final AbstractBlock.Properties FLOWER_POT 	= AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid();

	public static final AbstractBlock.Properties MYCELIUM_PATH 	= AbstractBlock.Properties.create(Material.EARTH, MaterialColor.PURPLE).hardnessAndResistance(0.65F).sound(SoundType.PLANT).setBlocksVision(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	public static final AbstractBlock.Properties PODZOL_PATH 	= AbstractBlock.Properties.create(Material.EARTH, MaterialColor.OBSIDIAN).hardnessAndResistance(0.65F).sound(SoundType.PLANT).setBlocksVision(PropertyExtensions::canSuffocate).setBlocksVision(PropertyExtensions::canSuffocate);
	
	public static final AbstractBlock.Properties MUD_BRICKS = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).hardnessAndResistance(1.5F, 2.5F).sound(SoundType.STONE);
	
	public static final AbstractBlock.Properties ICE_BRICKS 	= AbstractBlock.Properties.create(Material.ICE).slipperiness(0.99F).hardnessAndResistance(1.0F, 2.0F).sound(SoundType.GLASS);
	public static final AbstractBlock.Properties ICE_LANTERN	= AbstractBlock.Properties.create(Material.ICE).setRequiresTool().hardnessAndResistance(3.5F).sound(SoundType.LANTERN).setLightLevel((state) -> {return 12;}).notSolid();
	
	public static final AbstractBlock.Properties THATCH 	= AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.GREEN).hardnessAndResistance(0.5F).sound(SoundType.PLANT).harvestTool(ToolType.AXE);

	public static final AbstractBlock.Properties TERRACOTTA_BRICKS 				= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties WHITE_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties ORANGE_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties MAGENTA_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIGHT_BLUE_TERRACOTTA_BRICKS 	= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties YELLOW_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIME_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIME_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties PINK_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties GRAY_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIGHT_GRAY_TERRACOTTA_BRICKS 	= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties CYAN_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties PURPLE_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties BLUE_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties BROWN_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties GREEN_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GREEN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties RED_TERRACOTTA_BRICKS 			= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	public static final AbstractBlock.Properties BLACK_TERRACOTTA_BRICKS 		= AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25F, 4.2F);
	
	static class PropertyExtensions {
		private static Boolean canSpawn(BlockState state, IBlockReader access, BlockPos pos, EntityType<?> entity) {
			return entity == EntityType.OCELOT || entity == EntityType.PARROT;
		}

		private static boolean canSuffocate(BlockState state, IBlockReader access, BlockPos pos) {
			return false;
		}
	}	
}