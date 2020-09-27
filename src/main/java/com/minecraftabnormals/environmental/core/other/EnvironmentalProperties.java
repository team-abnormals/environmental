package com.minecraftabnormals.environmental.core.other;

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
	public static final AbstractBlock.Properties WILLOW_LEAVES 			= PropertyExtensions.createLeaves(MaterialColor.GREEN);
	public static final AbstractBlock.Properties WILLOW_LOG 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_SAPLING 		= AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	
	public static final AbstractBlock.Properties CHERRY_PLANKS 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_DOOR 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_BUTTON 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_PRESSURE_PLATE 	= AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LADDER 			= AbstractBlock.Properties.create(Material.MISCELLANEOUS).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties CHERRY_BOOKSHELF 		= AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LEAVES 			= PropertyExtensions.createLeaves(MaterialColor.PINK);
	public static final AbstractBlock.Properties CHERRY_LOG 			= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_SAPLING 		= AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	
	public static final AbstractBlock.Properties WISTERIA_PLANKS 		= AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties PINK_WISTERIA_LEAVES 	= PropertyExtensions.createLeaves(MaterialColor.PINK);
	public static final AbstractBlock.Properties BLUE_WISTERIA_LEAVES 	= PropertyExtensions.createLeaves(MaterialColor.BLUE);
	public static final AbstractBlock.Properties PURPLE_WISTERIA_LEAVES = PropertyExtensions.createLeaves(MaterialColor.PURPLE);
	public static final AbstractBlock.Properties WHITE_WISTERIA_LEAVES 	= PropertyExtensions.createLeaves(MaterialColor.SNOW);
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
	public static final AbstractBlock.Properties MYCELIUM_SPROUTS 	= AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_SPROUT);
	public static final AbstractBlock.Properties TALL_DEAD_BUSH 	= AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT);
	
	public static final AbstractBlock.Properties TWIG_NEST 	= AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.PLANT);
	public static final AbstractBlock.Properties HAY_NEST 	= AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT);

	public static final AbstractBlock.Properties FLOWER 		= AbstractBlock.Properties.create(Material.PLANTS).notSolid().doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT);
	public static final AbstractBlock.Properties DELPHINIUMS 	= AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	public static final AbstractBlock.Properties FLOWER_POT 	= AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid();

	public static final AbstractBlock.Properties MYCELIUM_PATH 	= AbstractBlock.Properties.create(Material.EARTH, MaterialColor.PURPLE).hardnessAndResistance(0.65F).sound(SoundType.PLANT).setBlocksVision(PropertyExtensions::isntSolid).setBlocksVision(PropertyExtensions::isntSolid);
	public static final AbstractBlock.Properties PODZOL_PATH 	= AbstractBlock.Properties.create(Material.EARTH, MaterialColor.OBSIDIAN).hardnessAndResistance(0.65F).sound(SoundType.PLANT).setBlocksVision(PropertyExtensions::isntSolid).setBlocksVision(PropertyExtensions::isntSolid);
	
	public static final AbstractBlock.Properties MUD           = AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.SLIME).harvestTool(ToolType.SHOVEL).speedFactor(0.2F).setAllowsSpawn(PropertyExtensions::alwaysAllowSpawn).setOpaque(PropertyExtensions::needsPostProcessing).setBlocksVision(PropertyExtensions::needsPostProcessing);
	public static final AbstractBlock.Properties MUD_BRICKS    = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN).hardnessAndResistance(1.5F, 2.5F).sound(SoundType.STONE);
	
    public static final AbstractBlock.Properties YAK_HAIR_BLOCK	= AbstractBlock.Properties.create(Material.WOOL, MaterialColor.BROWN).hardnessAndResistance(0.8F).sound(SoundType.CLOTH).notSolid().harvestTool(ToolType.HOE);
    public static final AbstractBlock.Properties YAK_HAIR_RUG	= AbstractBlock.Properties.create(Material.CARPET, MaterialColor.BROWN).hardnessAndResistance(0.1F).sound(SoundType.CLOTH).notSolid().harvestTool(ToolType.HOE);

	public static final AbstractBlock.Properties ICE_BRICKS 	= AbstractBlock.Properties.create(Material.ICE).slipperiness(0.99F).hardnessAndResistance(1.0F, 2.0F).sound(SoundType.GLASS);
	public static final AbstractBlock.Properties ICE_LANTERN	= AbstractBlock.Properties.create(Material.ICE).setRequiresTool().hardnessAndResistance(3.5F).sound(SoundType.LANTERN).setLightLevel((state) -> 12).notSolid();
	public static final AbstractBlock.Properties ICE_CHAIN 		= AbstractBlock.Properties.create(Material.ICE, MaterialColor.AIR).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.CHAIN).notSolid();
	
    public static final AbstractBlock.Properties GRASS_THATCH       = AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT).notSolid().harvestTool(ToolType.HOE);
	public static final AbstractBlock.Properties DUCKWEED_THATCH 	= AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.GREEN).hardnessAndResistance(0.5F).sound(SoundType.PLANT).notSolid().harvestTool(ToolType.HOE);
    public static final AbstractBlock.Properties CATTAIL_THATCH     = AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.GREEN).hardnessAndResistance(0.5F).sound(SoundType.PLANT).notSolid().harvestTool(ToolType.HOE);

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
        private static AbstractBlock.Properties createLeaves(MaterialColor color) {
            return AbstractBlock.Properties.create(Material.LEAVES, color).harvestTool(ToolType.HOE).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).setAllowsSpawn(PropertyExtensions::allowsSpawnOnLeaves).setSuffocates(PropertyExtensions::isntSolid).setBlocksVision(PropertyExtensions::isntSolid);
        }
        
        private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader access, BlockPos pos, EntityType<?> entity) {
            return entity == EntityType.OCELOT || entity == EntityType.PARROT;
        }

        private static Boolean alwaysAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
            return (boolean) true;
        }

        private static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
            return true;
        }

        private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
            return false;
        }
    }
}