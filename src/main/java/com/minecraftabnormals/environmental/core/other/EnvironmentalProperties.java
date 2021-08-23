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
	public static final AbstractBlock.Properties WILLOW_PLANKS = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_DOOR = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_BUTTON = AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_PRESSURE_PLATE = AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_LADDER = AbstractBlock.Properties.of(Material.DECORATION).noOcclusion().harvestTool(ToolType.AXE).strength(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties WILLOW_BOOKSHELF = AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_LOG = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WILLOW_SAPLING = AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);

	public static final AbstractBlock.Properties CHERRY_PLANKS = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_DOOR = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_BUTTON = AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_PRESSURE_PLATE = AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LADDER = AbstractBlock.Properties.of(Material.DECORATION).noOcclusion().harvestTool(ToolType.AXE).strength(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties CHERRY_BOOKSHELF = AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_LOG = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties CHERRY_SAPLING = AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);
	public static final AbstractBlock.Properties WISTERIA_PLANKS = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sound(SoundType.WOOD);

	public static final AbstractBlock.Properties WISTERIA_BUTTON = AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_DOOR = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_LOG = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_GRAY).strength(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties STRIPPED_WISTERIA_LOG = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(2.0F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_LADDER = AbstractBlock.Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_WHITE).noOcclusion().harvestTool(ToolType.AXE).strength(0.4F).sound(SoundType.LADDER);
	public static final AbstractBlock.Properties WISTERIA_BOOKSHELF = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD);
	public static final AbstractBlock.Properties WISTERIA_SAPLING = AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);

	public static final AbstractBlock.Properties CATTAIL = AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).strength(0.0F).noCollission().randomTicks().sound(SoundType.WET_GRASS);

	public static final AbstractBlock.Properties DUCKWEED = AbstractBlock.Properties.of(Material.PLANT).strength(0.0F).noCollission().sound(SoundType.CROP);
	public static final AbstractBlock.Properties MYCELIUM_SPROUTS = AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS);
	public static final AbstractBlock.Properties TALL_DEAD_BUSH = AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.WOOD).noCollission().instabreak().sound(SoundType.GRASS);

	public static final AbstractBlock.Properties TWIG_NEST = AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.GRASS);
	public static final AbstractBlock.Properties HAY_NEST = AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS);

	public static final AbstractBlock.Properties FLOWER = AbstractBlock.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS);
	public static final AbstractBlock.Properties DELPHINIUMS = AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS);
	public static final AbstractBlock.Properties FLOWER_POT = AbstractBlock.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion();

	public static final AbstractBlock.Properties MYCELIUM_PATH = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.COLOR_PURPLE).strength(0.65F).sound(SoundType.GRASS).harvestTool(ToolType.SHOVEL).isViewBlocking(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);
	public static final AbstractBlock.Properties PODZOL_PATH = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.PODZOL).strength(0.65F).sound(SoundType.GRASS).harvestTool(ToolType.SHOVEL).isViewBlocking(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);

	public static final AbstractBlock.Properties MUD = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.SLIME_BLOCK).harvestTool(ToolType.SHOVEL).speedFactor(0.2F).isValidSpawn(EnvironmentalProperties::alwaysAllowSpawn).isRedstoneConductor(EnvironmentalProperties::needsPostProcessing).isViewBlocking(EnvironmentalProperties::needsPostProcessing);
	public static final AbstractBlock.Properties MUD_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).strength(1.5F, 2.5F).sound(SoundType.STONE);

	public static final AbstractBlock.Properties YAK_HAIR_BLOCK = AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(0.8F).sound(SoundType.WOOL).noOcclusion().harvestTool(ToolType.HOE);
	public static final AbstractBlock.Properties YAK_HAIR_RUG = AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL).noOcclusion().harvestTool(ToolType.HOE);

	public static final AbstractBlock.Properties ICE_BRICKS = AbstractBlock.Properties.of(Material.ICE).friction(0.99F).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(1.0F, 2.0F).sound(SoundType.GLASS);
	public static final AbstractBlock.Properties ICE_LANTERN = AbstractBlock.Properties.of(Material.ICE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 12).noOcclusion();
	public static final AbstractBlock.Properties ICE_CHAIN = AbstractBlock.Properties.of(Material.ICE, MaterialColor.NONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion();

	public static final AbstractBlock.Properties GRASS_THATCH = AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS).noOcclusion().harvestTool(ToolType.HOE);
	public static final AbstractBlock.Properties DUCKWEED_THATCH = AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS).noOcclusion().harvestTool(ToolType.HOE);
	public static final AbstractBlock.Properties CATTAIL_THATCH = AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS).noOcclusion().harvestTool(ToolType.HOE);

	public static final AbstractBlock.Properties TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties WHITE_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties ORANGE_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties MAGENTA_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_MAGENTA).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIGHT_BLUE_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties YELLOW_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIME_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GREEN).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties PINK_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_PINK).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties GRAY_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GRAY).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties LIGHT_GRAY_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GRAY).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties CYAN_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_CYAN).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties PURPLE_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_PURPLE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties BLUE_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties BROWN_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties GREEN_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GREEN).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties RED_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
	public static final AbstractBlock.Properties BLACK_TERRACOTTA_BRICKS = AbstractBlock.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F);

	public static final AbstractBlock.Properties BURIED_TRUFFLE = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).harvestTool(ToolType.SHOVEL).strength(0.6F).sound(SoundType.GRAVEL);

	public static AbstractBlock.Properties createLeaves(MaterialColor color) {
		return AbstractBlock.Properties.of(Material.LEAVES, color).harvestTool(ToolType.HOE).noOcclusion().strength(0.2F).randomTicks().sound(SoundType.GRASS).isValidSpawn(EnvironmentalProperties::allowsSpawnOnLeaves).isSuffocating(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);
	}

	public static AbstractBlock.Properties createLeafCarpet(MaterialColor color) {
		return AbstractBlock.Properties.of(Material.CLOTH_DECORATION, color).strength(0.0F).sound(SoundType.GRASS).harvestTool(ToolType.HOE).noOcclusion();
	}

	public static boolean allowsSpawnOnLeaves(BlockState state, IBlockReader access, BlockPos pos, EntityType<?> entity) {
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	public static boolean alwaysAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		return true;
	}

	public static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	public static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

}