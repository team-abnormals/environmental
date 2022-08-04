package com.teamabnormals.environmental.core.other;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class EnvironmentalProperties {
	public static final BlockBehaviour.Properties WILLOW_PLANKS = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_DOOR = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_BUTTON = BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_PRESSURE_PLATE = BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_LADDER = BlockBehaviour.Properties.of(Material.DECORATION).noOcclusion().strength(0.4F).sound(SoundType.LADDER);
	public static final BlockBehaviour.Properties WILLOW_BOOKSHELF = BlockBehaviour.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_LOG = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WILLOW_SAPLING = BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);

	public static final BlockBehaviour.Properties CHERRY_PLANKS = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_DOOR = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_BUTTON = BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_PRESSURE_PLATE = BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_LADDER = BlockBehaviour.Properties.of(Material.DECORATION).noOcclusion().strength(0.4F).sound(SoundType.LADDER);
	public static final BlockBehaviour.Properties CHERRY_BOOKSHELF = BlockBehaviour.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_LOG = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties CHERRY_SAPLING = BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);
	public static final BlockBehaviour.Properties WISTERIA_PLANKS = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sound(SoundType.WOOD);

	public static final BlockBehaviour.Properties WISTERIA_BUTTON = BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WISTERIA_DOOR = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WISTERIA_LOG = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_GRAY).strength(2.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties STRIPPED_WISTERIA_LOG = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(2.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WISTERIA_LADDER = BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_WHITE).noOcclusion().strength(0.4F).sound(SoundType.LADDER);
	public static final BlockBehaviour.Properties WISTERIA_BOOKSHELF = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties WISTERIA_SAPLING = BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);

	public static final BlockBehaviour.Properties CATTAIL = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).strength(0.0F).noCollission().randomTicks().sound(SoundType.WET_GRASS);

	public static final BlockBehaviour.Properties DUCKWEED = BlockBehaviour.Properties.of(Material.PLANT).strength(0.0F).noCollission().sound(SoundType.CROP);
	public static final BlockBehaviour.Properties MYCELIUM_SPROUTS = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS);
	public static final BlockBehaviour.Properties TALL_DEAD_BUSH = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.WOOD).noCollission().instabreak().sound(SoundType.GRASS);

	public static final BlockBehaviour.Properties FLOWER = BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().noCollission().instabreak().sound(SoundType.GRASS);
	public static final BlockBehaviour.Properties DELPHINIUMS = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS);
	public static final BlockBehaviour.Properties FLOWER_POT = BlockBehaviour.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion();

	public static final BlockBehaviour.Properties MYCELIUM_PATH = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_PURPLE).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);
	public static final BlockBehaviour.Properties PODZOL_PATH = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.PODZOL).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);

	public static final BlockBehaviour.Properties MUD = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.SLIME_BLOCK).speedFactor(0.2F).isValidSpawn(EnvironmentalProperties::alwaysAllowSpawn).isRedstoneConductor(EnvironmentalProperties::needsPostProcessing).isViewBlocking(EnvironmentalProperties::needsPostProcessing);
	public static final BlockBehaviour.Properties MUD_BRICKS = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).strength(1.5F, 2.5F).sound(SoundType.STONE);

	public static final BlockBehaviour.Properties YAK_HAIR_BLOCK = BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(0.8F).sound(SoundType.WOOL).noOcclusion();
	public static final BlockBehaviour.Properties YAK_HAIR_RUG = BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL).noOcclusion();

	public static final BlockBehaviour.Properties GRASS_THATCH = BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS).noOcclusion();
	public static final BlockBehaviour.Properties DUCKWEED_THATCH = BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS).noOcclusion();
	public static final BlockBehaviour.Properties CATTAIL_THATCH = BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS).noOcclusion();

	public static final BlockBehaviour.Properties BURIED_TRUFFLE = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.6F).sound(SoundType.GRAVEL);

	public static BlockBehaviour.Properties createLeaves(MaterialColor color) {
		return BlockBehaviour.Properties.of(Material.LEAVES, color).noOcclusion().strength(0.2F).randomTicks().sound(SoundType.GRASS).isValidSpawn(EnvironmentalProperties::allowsSpawnOnLeaves).isSuffocating(EnvironmentalProperties::isntSolid).isViewBlocking(EnvironmentalProperties::isntSolid);
	}

	public static BlockBehaviour.Properties createLeafCarpet(MaterialColor color) {
		return BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, color).strength(0.0F).sound(SoundType.GRASS).noOcclusion();
	}

	public static boolean allowsSpawnOnLeaves(BlockState state, BlockGetter access, BlockPos pos, EntityType<?> entity) {
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	public static boolean alwaysAllowSpawn(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
		return true;
	}

	public static boolean needsPostProcessing(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	public static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}
}