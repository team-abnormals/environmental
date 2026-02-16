package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlockSetTypeRegistryHelper;
import com.teamabnormals.blueprint.core.api.WoodTypeRegistryHelper;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.PropertyUtil.WoodSetProperties;
import com.teamabnormals.environmental.common.block.ShrubBlock;
import com.teamabnormals.environmental.common.block.WallHibiscusBlock;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class EnvironmentalProperties {
	public static final BlockSetType WILLOW_BLOCK_SET = blockSetType("willow");
	public static final BlockSetType PINE_BLOCK_SET = blockSetType("pine");
	public static final BlockSetType CEDAR_BLOCK_SET = blockSetType("cedar");
	public static final BlockSetType PLUM_BLOCK_SET = blockSetType("plum");
	public static final BlockSetType WISTERIA_BLOCK_SET = blockSetType("wisteria");

	public static final WoodType WILLOW_WOOD_TYPE = woodSetType(WILLOW_BLOCK_SET);
	public static final WoodType PINE_WOOD_TYPE = woodSetType(PINE_BLOCK_SET);
	public static final WoodType CEDAR_WOOD_TYPE = woodSetType(CEDAR_BLOCK_SET);
	public static final WoodType PLUM_WOOD_TYPE = woodSetType(PLUM_BLOCK_SET);
	public static final WoodType WISTERIA_WOOD_TYPE = woodSetType(WISTERIA_BLOCK_SET);

	public static final WoodSetProperties WILLOW = WoodSetProperties.builder(MapColor.TERRACOTTA_GREEN, MapColor.WOOD).build();
	public static final WoodSetProperties PINE = WoodSetProperties.builder(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.WOOD).build();
	public static final WoodSetProperties CEDAR = WoodSetProperties.builder(MapColor.TERRACOTTA_GRAY, MapColor.WOOD).build();
	public static final WoodSetProperties PLUM = WoodSetProperties.builder(MapColor.TERRACOTTA_RED, MapColor.WOOD).leavesColor(MapColor.COLOR_PINK).build();
	public static final WoodSetProperties WISTERIA = WoodSetProperties.builder(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_CYAN).leavesColor(MapColor.SNOW).build();
	public static final WoodSetProperties PINK_WISTERIA = WoodSetProperties.builder(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_CYAN).leavesColor(MapColor.COLOR_PINK).build();
	public static final WoodSetProperties BLUE_WISTERIA = WoodSetProperties.builder(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_CYAN).leavesColor(MapColor.COLOR_LIGHT_BLUE).build();
	public static final WoodSetProperties PURPLE_WISTERIA = WoodSetProperties.builder(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_CYAN).leavesColor(MapColor.COLOR_PURPLE).build();

	public static final WoodSetProperties HIBISCUS = WoodSetProperties.builder(MapColor.COLOR_GREEN).build();

	public static final BlockBehaviour.Properties CATTAIL = BlockBehaviour.Properties.of().instabreak().noCollission().randomTicks().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties CATTAIL_STALK = BlockBehaviour.Properties.of().instabreak().noCollission().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);

	public static final BlockBehaviour.Properties DUCKWEED = BlockBehaviour.Properties.of().instabreak().noCollission().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties MYCELIUM_SPROUTS = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties CUP_LICHEN = BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties TREE_LICHEN = BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties CACTUS_BOBBLE = BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.WOOL).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties SHRUB = shrub();
	public static final BlockBehaviour.Properties DWARF_SPRUCE = BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);

	public static final BlockBehaviour.Properties TALL_FLOWERS = PropertyUtil.flower().ignitedByLava();
	public static final BlockBehaviour.Properties WALL_HIBISCUS = wallHibiscus();

	public static final BlockBehaviour.Properties PINECONE = Block.Properties.of().mapColor(MapColor.DIRT).strength(1.5F).sound(SoundType.WOOD).ignitedByLava();

	public static final BlockBehaviour.Properties DIRT_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.65F).sound(SoundType.GRAVEL).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
	public static final BlockBehaviour.Properties MYCELIUM_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
	public static final BlockBehaviour.Properties PODZOL_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
	public static final BlockBehaviour.Properties MUD_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.65F).sound(SoundType.MUD).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
	public static final BlockBehaviour.Properties MUDDY_PODZOL_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.65F).sound(SoundType.MUD).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);

	public static final BlockBehaviour.Properties DIRT_BRICKS = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL);
	public static final BlockBehaviour.Properties MUD_BRICKS = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS);
	public static final BlockBehaviour.Properties MUDDY_SANDSTONE = BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.SNARE).strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS);
	public static final BlockBehaviour.Properties MUDDY_SANDSTONE_WALL = BlockBehaviour.Properties.of().forceSolidOn().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.SNARE).strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS);

	public static final BlockBehaviour.Properties YAK_HAIR_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.8F).sound(SoundType.WOOL).noOcclusion().ignitedByLava();
	public static final BlockBehaviour.Properties YAK_HAIR_RUG = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL).noOcclusion().ignitedByLava();

	public static final BlockBehaviour.Properties GRASS_THATCH = PropertyUtil.thatch(MapColor.COLOR_YELLOW, SoundType.GRASS);
	public static final BlockBehaviour.Properties DUCKWEED_THATCH = PropertyUtil.thatch(MapColor.COLOR_GREEN, SoundType.GRASS);
	public static final BlockBehaviour.Properties CATTAIL_THATCH = PropertyUtil.thatch(MapColor.TERRACOTTA_GREEN, SoundType.GRASS);

	public static final BlockBehaviour.Properties BURIED_TRUFFLE = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.6F).sound(SoundType.GRAVEL);

	public static BlockSetType blockSetType(String name) {
		return BlockSetTypeRegistryHelper.register(new BlockSetType(Environmental.MOD_ID + ":" + name));
	}

	public static WoodType woodSetType(BlockSetType type) {
		return WoodTypeRegistryHelper.registerWoodType(new WoodType(type.name(), type));
	}

	public static BlockBehaviour.Properties wallHibiscus() {
		BlockBehaviour.Properties properties = Block.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY);
		properties.offsetFunction = (state, level, pos) -> {
			WallHibiscusBlock block = (WallHibiscusBlock) state.getBlock();
			long i = Mth.getSeed(pos.getX(), pos.getY(), pos.getZ());
			float f = block.getMaxHorizontalOffset();
			double d0 = Mth.clamp(((double) ((float) (i & 15L) / 15.0F) - 0.5D) * 0.4D, -f, f);
			double d1 = Mth.clamp(((double) ((float) (i >> 8 & 15L) / 15.0F) - 0.5D) * 0.4D, -f, f);

			Direction facing = state.getValue(WallHibiscusBlock.FACING);
			Axis axis = facing.getAxis();
			Vec3 vec3 = state.getValue(WallHibiscusBlock.FACE) != AttachFace.WALL ? new Vec3(d0, 0.0F, d1) : axis == Axis.X ? new Vec3(0.0F, d0, d1) : new Vec3(d0, d1, 0.0F);

			return vec3;
		};
		return properties;
	}

	private static BlockBehaviour.Properties shrub() {
		BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY);
		properties.offsetFunction = (state, blockGetter, pos) -> {
			double xOffset;
			double zOffset;
			int x = pos.getX();
			int z = pos.getZ();
			if (state.getValue(ShrubBlock.CENTERED)) {
				xOffset = zOffset = 0.0D;
			} else {
				long i = Mth.getSeed(x, 0, z);
				xOffset = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5) * 0.5, -0.25D, 0.25D);
				zOffset = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5) * 0.5, -0.25D, 0.25D);
			}
			// Fixes z fighting
			xOffset += (double) (((x & 1) << 1) | (z & 1)) * 0.0005D;
			return new Vec3(xOffset, 0.0, zOffset);
		};
		return properties;
	}

	public static BlockBehaviour.Properties applyBetterXZOffset(BlockBehaviour.Properties properties) {
		properties.offsetFunction = (state, blockGetter, pos) -> {
			long i = Mth.getSeed(pos.getX(), 0, pos.getZ());
			double xOffset = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5) * 0.5, -0.25D, 0.25D);
			double zOffset = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5) * 0.5, -0.25D, 0.25D);
			// Fix rare z-fighting
			if (pos.getX() % 2 == 0)
				zOffset += 0.001D;
			if (pos.getZ() % 2 == 0)
				xOffset += 0.001D;
			return new Vec3(xOffset, 0.0, zOffset);
		};
		return properties;
	}

	public static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}
}