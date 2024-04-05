package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.PropertyUtil.WoodSetProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class EnvironmentalProperties {
	public static final WoodSetProperties WILLOW = WoodSetProperties.builder(MaterialColor.TERRACOTTA_GREEN, MaterialColor.WOOD).build();
	public static final WoodSetProperties PINE = WoodSetProperties.builder(MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.WOOD).build();
	public static final WoodSetProperties CHERRY = WoodSetProperties.builder(MaterialColor.TERRACOTTA_RED, MaterialColor.WOOD).leavesColor(MaterialColor.COLOR_PINK).build();
	public static final WoodSetProperties WISTERIA = WoodSetProperties.builder(MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_CYAN).build();

	public static final BlockBehaviour.Properties CATTAIL = BlockBehaviour.Properties.of(Material.PLANT).instabreak().noCollission().randomTicks().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ);
	public static final BlockBehaviour.Properties CATTAIL_STALK = BlockBehaviour.Properties.of(Material.PLANT).instabreak().noCollission().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ);

	public static final BlockBehaviour.Properties DUCKWEED = BlockBehaviour.Properties.of(Material.PLANT).strength(0.0F).noCollission().sound(SoundType.CROP);
	public static final BlockBehaviour.Properties MYCELIUM_SPROUTS = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ);
	public static final BlockBehaviour.Properties CUP_LICHEN = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, MaterialColor.GLOW_LICHEN).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS);
	public static final BlockBehaviour.Properties CACTUS_BOBBLE = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).strength(0.0F).noCollission().sound(SoundType.WOOL);
	public static final BlockBehaviour.Properties DWARF_SPRUCE = BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ);

	public static final BlockBehaviour.Properties DELPHINIUMS = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS);
	public static final BlockBehaviour.Properties HIBISCUS_LEAVES = BlockBehaviour.Properties.of(Material.LEAVES).strength(0.4F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(PropertyUtil::ocelotOrParrot).isSuffocating(PropertyUtil::never).isViewBlocking(PropertyUtil::never);
	public static final BlockBehaviour.Properties HIBISCUS_LEAF_PILE = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.2F).sound(SoundType.GRASS);
	public static final BlockBehaviour.Properties HIBISCUS_LEAF_CARPET = BlockBehaviour.Properties.of(Material.CLOTH_DECORATION).strength(0.0F).sound(SoundType.GRASS).noOcclusion();
	public static final BlockBehaviour.Properties WALL_HIBISCUS = Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS);

	public static final BlockBehaviour.Properties PINECONE = Block.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(1.5F).sound(SoundType.WOOD);

	public static final BlockBehaviour.Properties DIRT_PATH = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.65F).sound(SoundType.GRAVEL).isViewBlocking(PropertyUtil::never).isViewBlocking(PropertyUtil::never);
	public static final BlockBehaviour.Properties MYCELIUM_PATH = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_PURPLE).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(PropertyUtil::never).isViewBlocking(PropertyUtil::never);
	public static final BlockBehaviour.Properties PODZOL_PATH = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.PODZOL).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(PropertyUtil::never).isViewBlocking(PropertyUtil::never);

	public static final BlockBehaviour.Properties DIRT_BRICKS = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL);
	public static final BlockBehaviour.Properties MUD_BRICKS = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GRAY).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS);

	public static final BlockBehaviour.Properties YAK_HAIR_BLOCK = BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(0.8F).sound(SoundType.WOOL).noOcclusion();
	public static final BlockBehaviour.Properties YAK_HAIR_RUG = BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL).noOcclusion();

	public static final BlockBehaviour.Properties GRASS_THATCH = PropertyUtil.thatch(MaterialColor.COLOR_YELLOW, SoundType.GRASS);
	public static final BlockBehaviour.Properties DUCKWEED_THATCH = PropertyUtil.thatch(MaterialColor.COLOR_GREEN, SoundType.GRASS);
	public static final BlockBehaviour.Properties CATTAIL_THATCH = PropertyUtil.thatch(MaterialColor.TERRACOTTA_GREEN, SoundType.GRASS);

	public static final BlockBehaviour.Properties BURIED_TRUFFLE = BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.6F).sound(SoundType.GRAVEL);

	public static BlockBehaviour.Properties leaves(MaterialColor color) {
		return Block.Properties.of(Material.LEAVES, color).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(PropertyUtil::ocelotOrParrot).isSuffocating(PropertyUtil::never).isViewBlocking(PropertyUtil::never);
	}

	public static BlockBehaviour.Properties leafPile(MaterialColor color) {
		return Block.Properties.of(Material.REPLACEABLE_PLANT, color).noCollission().strength(0.2F).sound(SoundType.GRASS);
	}

	public static BlockBehaviour.Properties leafCarpet(MaterialColor color) {
		return BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, color).strength(0.0F).sound(SoundType.GRASS).noOcclusion();
	}
}