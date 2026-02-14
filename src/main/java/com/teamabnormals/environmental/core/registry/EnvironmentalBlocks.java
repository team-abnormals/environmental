package com.teamabnormals.environmental.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.BlueprintBeehiveBlock;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.common.block.LeafPileBlock;
import com.teamabnormals.blueprint.common.block.LogBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintCeilingHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchSlabBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchStairBlock;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.environmental.common.block.*;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.other.EnvironmentalProperties;
import com.teamabnormals.environmental.core.other.EnvironmentalTreeGrowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Predicate;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class EnvironmentalBlocks {
	public static final BlockSubRegistryHelper BLOCKS = Environmental.REGISTRY_HELPER.getBlockSubHelper();

	// Mud //

	public static final DeferredBlock<Block> DIRT_BRICKS = BLOCKS.createBlock("dirt_bricks", () -> new Block(EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_BRICK_STAIRS = BLOCKS.createBlock("dirt_brick_stairs", () -> new StairBlock(DIRT_BRICKS.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_BRICK_SLAB = BLOCKS.createBlock("dirt_brick_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_BRICK_WALL = BLOCKS.createBlock("dirt_brick_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS));

	public static final DeferredBlock<Block> DIRT_TILES = BLOCKS.createBlock("dirt_tiles", () -> new Block(EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_TILE_STAIRS = BLOCKS.createBlock("dirt_tile_stairs", () -> new StairBlock(DIRT_TILES.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_TILE_SLAB = BLOCKS.createBlock("dirt_tile_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS));
	public static final DeferredBlock<Block> DIRT_TILE_WALL = BLOCKS.createBlock("dirt_tile_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS));

	public static final DeferredBlock<Block> SMOOTH_MUD = BLOCKS.createBlock("smooth_mud", () -> new Block(EnvironmentalProperties.MUD_BRICKS));
	public static final DeferredBlock<Block> SMOOTH_MUD_SLAB = BLOCKS.createBlock("smooth_mud_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS));
	public static final DeferredBlock<Block> CHISELED_MUD_BRICKS = BLOCKS.createBlock("chiseled_mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS));
	public static final DeferredBlock<Block> SLABFISH_EFFIGY = BLOCKS.createBlock("slabfish_effigy", () -> new SlabfishEffigyBlock(PropertyUtil.flowerPot().sound(SoundType.MUD_BRICKS)));

	public static final DeferredBlock<Block> MUDDY_SAND = BLOCKS.createBlock("muddy_sand", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.MUD)));

	// Crops //

	public static final DeferredBlock<Block> CATTAIL_SPROUT = BLOCKS.createBlockNoItem("cattail_sprout", () -> new CattailSproutBlock(EnvironmentalProperties.CATTAIL));
	public static final DeferredBlock<Block> CATTAIL = BLOCKS.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL));
	public static final DeferredBlock<Block> CATTAIL_STALK = BLOCKS.createBlockNoItem("cattail_stalk", () -> new CattailStalkBlock(EnvironmentalProperties.CATTAIL_STALK));
	public static final DeferredBlock<Block> POTTED_CATTAIL = BLOCKS.createBlockNoItem("potted_cattail", () -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> CATTAIL_FLUFF_BLOCK = BLOCKS.createBlock("cattail_fluff_block", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.WOOL).ignitedByLava()));

	// Foliage //

	public static final DeferredBlock<Block> DUCKWEED = BLOCKS.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));

	public static final DeferredBlock<Block> MYCELIUM_SPROUTS = BLOCKS.createBlock("mycelium_sprouts", () -> new MyceliumSproutsBlock(EnvironmentalProperties.MYCELIUM_SPROUTS));
	public static final DeferredBlock<Block> GIANT_TALL_GRASS = BLOCKS.createBlock("giant_tall_grass", () -> new DoublePlantBlock(EnvironmentalProperties.applyBetterXZOffset(Block.Properties.ofFullCopy(Blocks.TALL_GRASS))));
	public static final DeferredBlock<Block> CUP_LICHEN = BLOCKS.createBlock("cup_lichen", () -> new CupLichenBlock(EnvironmentalProperties.CUP_LICHEN));
	public static final DeferredBlock<Block> TREE_LICHEN = BLOCKS.createBlock("tree_lichen",  () -> new TreeLichenBlock(EnvironmentalProperties.TREE_LICHEN));
	public static final DeferredBlock<Block> CACTUS_BOBBLE = BLOCKS.createBlockNoItem("cactus_bobble", () -> new CactusBobbleBlock(EnvironmentalProperties.CACTUS_BOBBLE));
	public static final DeferredBlock<Block> SHRUB = BLOCKS.createBlock("shrub", () -> new ShrubBlock(EnvironmentalProperties.SHRUB));
	public static final DeferredBlock<Block> FLOWERING_SHRUB = BLOCKS.createBlock("flowering_shrub", () -> new ShrubBlock(EnvironmentalProperties.SHRUB));

	public static final DeferredBlock<Block> BLAZING_SUNFLOWER = BLOCKS.createBlock("blazing_sunflower", () -> new DoublePlantBlock(Block.Properties.ofFullCopy(Blocks.SUNFLOWER)));
	public static final DeferredBlock<Block> BEAMING_SUNFLOWER = BLOCKS.createBlock("beaming_sunflower", () -> new DoublePlantBlock(Block.Properties.ofFullCopy(Blocks.SUNFLOWER)));
	public static final DeferredBlock<Block> ECLIPSED_SUNFLOWER = BLOCKS.createBlock("eclipsed_sunflower", () -> new DoublePlantBlock(Block.Properties.ofFullCopy(Blocks.SUNFLOWER)));
	public static final DeferredBlock<Block> RADIANT_SUNFLOWER = BLOCKS.createBlock("radiant_sunflower", () -> new DoublePlantBlock(Block.Properties.ofFullCopy(Blocks.SUNFLOWER)));

	public static final DeferredBlock<Block> DWARF_SPRUCE = BLOCKS.createBlock("dwarf_spruce", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT = BLOCKS.createBlockNoItem("dwarf_spruce_plant", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE, (DwarfSpruceHeadBlock) DWARF_SPRUCE.get()));
	public static final DeferredBlock<Block> DWARF_SPRUCE_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), () -> Items.TORCH));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_plant_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), () -> Items.TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_TORCH.get()));
	public static final DeferredBlock<Block> DWARF_SPRUCE_SOUL_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_soul_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), () -> Items.SOUL_TORCH));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT_SOUL_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_plant_soul_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), () -> Items.SOUL_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_SOUL_TORCH.get()));
	public static final DeferredBlock<Block> DWARF_SPRUCE_REDSTONE_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_redstone_torch", () -> new RedstoneDwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0), () -> Items.REDSTONE_TORCH));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT_REDSTONE_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_plant_redstone_torch", () -> new RedstoneDwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0), () -> Items.REDSTONE_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_REDSTONE_TORCH.get()));
	public static final DeferredBlock<Block> DWARF_SPRUCE_ENDER_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_ender_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), EnvironmentalConstants.ENDER_TORCH));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT_ENDER_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_plant_ender_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), EnvironmentalConstants.ENDER_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_ENDER_TORCH.get()));
	public static final DeferredBlock<Block> DWARF_SPRUCE_CUPRIC_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_cupric_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), EnvironmentalConstants.CUPRIC_TORCH));
	public static final DeferredBlock<Block> DWARF_SPRUCE_PLANT_CUPRIC_TORCH = BLOCKS.createBlockNoItem("dwarf_spruce_plant_cupric_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), EnvironmentalConstants.CUPRIC_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_CUPRIC_TORCH.get()));

	public static final DeferredBlock<Block> POTTED_CUP_LICHEN = BLOCKS.createBlockNoItem("potted_cup_lichen", () -> new FlowerPotBlock(EnvironmentalBlocks.CUP_LICHEN.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_SHRUB = BLOCKS.createBlockNoItem("potted_shrub", () -> new FlowerPotBlock(EnvironmentalBlocks.SHRUB.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_FLOWERING_SHRUB = BLOCKS.createBlockNoItem("potted_flowering_shrub", () -> new FlowerPotBlock(EnvironmentalBlocks.FLOWERING_SHRUB.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_DWARF_SPRUCE = BLOCKS.createBlockNoItem("potted_dwarf_spruce", () -> new FlowerPotBlock(EnvironmentalBlocks.DWARF_SPRUCE.get(), PropertyUtil.flowerPot()));

	// Misc //

	public static final DeferredBlock<Block> BURIED_TRUFFLE = BLOCKS.createBlock("buried_truffle", () -> new Block(EnvironmentalProperties.BURIED_TRUFFLE));
	public static final DeferredBlock<Block> DUCK_EGG_CRATE = BLOCKS.createBlock("duck_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	// Decorations //

	public static final DeferredBlock<Block> DIRT_PATH = BLOCKS.createBlock("dirt_path", () -> new DirtPathBlock(EnvironmentalProperties.DIRT_PATH));
	public static final DeferredBlock<Block> PODZOL_PATH = BLOCKS.createBlock("podzol_path", () -> new DirtPathBlock(EnvironmentalProperties.PODZOL_PATH));
	public static final DeferredBlock<Block> MYCELIUM_PATH = BLOCKS.createBlock("mycelium_path", () -> new DirtPathBlock(EnvironmentalProperties.MYCELIUM_PATH));
	public static final DeferredBlock<Block> YAK_HAIR_BLOCK = BLOCKS.createBlock("yak_hair_block", () -> new ThatchBlock(EnvironmentalProperties.YAK_HAIR_BLOCK));
	public static final DeferredBlock<Block> YAK_HAIR_RUG = BLOCKS.createBlock("yak_hair_rug", () -> new RugBlock(EnvironmentalProperties.YAK_HAIR_RUG));

	public static final DeferredBlock<Block> GRASS_THATCH = BLOCKS.createBlock("grass_thatch", () -> new ThatchBlock(EnvironmentalProperties.GRASS_THATCH));
	public static final DeferredBlock<Block> GRASS_THATCH_SLAB = BLOCKS.createBlock("grass_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.GRASS_THATCH));
	public static final DeferredBlock<Block> GRASS_THATCH_STAIRS = BLOCKS.createBlock("grass_thatch_stairs", () -> new ThatchStairBlock(GRASS_THATCH.get().defaultBlockState(), EnvironmentalProperties.GRASS_THATCH));

	public static final DeferredBlock<Block> CATTAIL_THATCH = BLOCKS.createBlock("cattail_thatch", () -> new ThatchBlock(EnvironmentalProperties.CATTAIL_THATCH));
	public static final DeferredBlock<Block> CATTAIL_THATCH_SLAB = BLOCKS.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.CATTAIL_THATCH));
	public static final DeferredBlock<Block> CATTAIL_THATCH_STAIRS = BLOCKS.createBlock("cattail_thatch_stairs", () -> new ThatchStairBlock(CATTAIL_THATCH.get().defaultBlockState(), EnvironmentalProperties.CATTAIL_THATCH));

	public static final DeferredBlock<Block> DUCKWEED_THATCH = BLOCKS.createBlock("duckweed_thatch", () -> new ThatchBlock(EnvironmentalProperties.DUCKWEED_THATCH));
	public static final DeferredBlock<Block> DUCKWEED_THATCH_SLAB = BLOCKS.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH));
	public static final DeferredBlock<Block> DUCKWEED_THATCH_STAIRS = BLOCKS.createBlock("duckweed_thatch_stairs", () -> new ThatchStairBlock(DUCKWEED_THATCH.get().defaultBlockState(), EnvironmentalProperties.DUCKWEED_THATCH));

	// Flowers //

	public static final DeferredBlock<Block> CARTWHEEL = BLOCKS.createBlock("cartwheel", () -> new CartwheelBlock(MobEffects.LEVITATION, 3, PropertyUtil.flower()));
	public static final DeferredBlock<Block> BLUEBELL = BLOCKS.createBlock("bluebell", () -> new FlowerBlock(MobEffects.HUNGER, 8, PropertyUtil.flower()));
	public static final DeferredBlock<Block> VIOLET = BLOCKS.createBlock("violet", () -> new FlowerBlock(MobEffects.INVISIBILITY, 9, PropertyUtil.flower()));
	public static final DeferredBlock<Block> DIANTHUS = BLOCKS.createBlock("dianthus", () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 7, PropertyUtil.flower()));
	public static final DeferredBlock<Block> RED_LOTUS_FLOWER = BLOCKS.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM::get, MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()));
	public static final DeferredBlock<Block> WHITE_LOTUS_FLOWER = BLOCKS.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM::get, MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()));
	public static final DeferredBlock<Block> TASSELFLOWER = BLOCKS.createBlock("tasselflower", () -> new FlowerBlock(MobEffects.DIG_SLOWDOWN, 6, PropertyUtil.flower()));

	public static final DeferredBlock<Block> HIBISCUS_LEAVES = BLOCKS.createBlock("hibiscus_leaves", () -> new HibiscusLeavesBlock(EnvironmentalProperties.HIBISCUS.leaves()));
	public static final DeferredBlock<Block> HIBISCUS_LEAF_PILE = BLOCKS.createBlock("hibiscus_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.HIBISCUS.leafPile()));

	public static final DeferredBlock<Block> YELLOW_WALL_HIBISCUS = BLOCKS.createBlockNoItem("yellow_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final DeferredBlock<Block> ORANGE_WALL_HIBISCUS = BLOCKS.createBlockNoItem("orange_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final DeferredBlock<Block> RED_WALL_HIBISCUS = BLOCKS.createBlockNoItem("red_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final DeferredBlock<Block> PINK_WALL_HIBISCUS = BLOCKS.createBlockNoItem("pink_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final DeferredBlock<Block> MAGENTA_WALL_HIBISCUS = BLOCKS.createBlockNoItem("magenta_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final DeferredBlock<Block> PURPLE_WALL_HIBISCUS = BLOCKS.createBlockNoItem("purple_wall_hibiscus", () -> new WallHibiscusBlock(MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));

	public static final DeferredBlock<Block> YELLOW_HIBISCUS = BLOCKS.createBlockNoItem("yellow_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, YELLOW_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final DeferredBlock<Block> ORANGE_HIBISCUS = BLOCKS.createBlockNoItem("orange_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, ORANGE_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final DeferredBlock<Block> RED_HIBISCUS = BLOCKS.createBlockNoItem("red_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, RED_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final DeferredBlock<Block> PINK_HIBISCUS = BLOCKS.createBlockNoItem("pink_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, PINK_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final DeferredBlock<Block> MAGENTA_HIBISCUS = BLOCKS.createBlockNoItem("magenta_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, MAGENTA_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final DeferredBlock<Block> PURPLE_HIBISCUS = BLOCKS.createBlockNoItem("purple_hibiscus", () -> new HibiscusBlock(MobEffects.GLOWING, 8, PURPLE_WALL_HIBISCUS.get(), PropertyUtil.flower()));

	public static final DeferredBlock<Block> POTTED_CARTWHEEL = BLOCKS.createBlockNoItem("potted_cartwheel", () -> new PottedCartwheelBlock(CARTWHEEL.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_BLUEBELL = BLOCKS.createBlockNoItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_VIOLET = BLOCKS.createBlockNoItem("potted_violet", () -> new FlowerPotBlock(VIOLET.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_DIANTHUS = BLOCKS.createBlockNoItem("potted_dianthus", () -> new FlowerPotBlock(DIANTHUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_RED_LOTUS_FLOWER = BLOCKS.createBlockNoItem("potted_red_lotus_flower", () -> new FlowerPotBlock(RED_LOTUS_FLOWER.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_WHITE_LOTUS_FLOWER = BLOCKS.createBlockNoItem("potted_white_lotus_flower", () -> new FlowerPotBlock(WHITE_LOTUS_FLOWER.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_TASSELFLOWER = BLOCKS.createBlockNoItem("potted_tasselflower", () -> new FlowerPotBlock(TASSELFLOWER.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_YELLOW_HIBISCUS = BLOCKS.createBlockNoItem("potted_yellow_hibiscus", () -> new FlowerPotBlock(YELLOW_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_ORANGE_HIBISCUS = BLOCKS.createBlockNoItem("potted_orange_hibiscus", () -> new FlowerPotBlock(ORANGE_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_RED_HIBISCUS = BLOCKS.createBlockNoItem("potted_red_hibiscus", () -> new FlowerPotBlock(RED_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_PINK_HIBISCUS = BLOCKS.createBlockNoItem("potted_pink_hibiscus", () -> new FlowerPotBlock(PINK_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_MAGENTA_HIBISCUS = BLOCKS.createBlockNoItem("potted_magenta_hibiscus", () -> new FlowerPotBlock(MAGENTA_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_PURPLE_HIBISCUS = BLOCKS.createBlockNoItem("potted_purple_hibiscus", () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), PropertyUtil.flowerPot()));

	// Tall Flowers //

	public static final DeferredBlock<Block> PINK_DELPHINIUM = BLOCKS.createBlock("pink_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final DeferredBlock<Block> BLUE_DELPHINIUM = BLOCKS.createBlock("blue_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final DeferredBlock<Block> PURPLE_DELPHINIUM = BLOCKS.createBlock("purple_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final DeferredBlock<Block> WHITE_DELPHINIUM = BLOCKS.createBlock("white_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final DeferredBlock<Block> BIRD_OF_PARADISE = BLOCKS.createBlock("bird_of_paradise", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));

	public static final DeferredBlock<Block> POTTED_PINK_DELPHINIUM = BLOCKS.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_BLUE_DELPHINIUM = BLOCKS.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_PURPLE_DELPHINIUM = BLOCKS.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_WHITE_DELPHINIUM = BLOCKS.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_BIRD_OF_PARADISE = BLOCKS.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), PropertyUtil.flowerPot()));

	// Willow //

	public static final DeferredBlock<Block> STRIPPED_WILLOW_LOG = BLOCKS.createBlock("stripped_willow_log", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.log()));
	public static final DeferredBlock<Block> STRIPPED_WILLOW_WOOD = BLOCKS.createBlock("stripped_willow_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.log()));
	public static final DeferredBlock<Block> WILLOW_LOG = BLOCKS.createBlock("willow_log", () -> new LogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.WILLOW.log()));
	public static final DeferredBlock<Block> WILLOW_WOOD = BLOCKS.createBlock("willow_wood", () -> new LogBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.WILLOW.log()));
	public static final DeferredBlock<Block> WILLOW_LEAVES = BLOCKS.createBlock("willow_leaves", () -> new LeavesBlock(EnvironmentalProperties.WILLOW.leaves()));
	public static final DeferredBlock<Block> HANGING_WILLOW_LEAVES = BLOCKS.createBlock("hanging_willow_leaves", () -> new HangingLeavesBlock(EnvironmentalProperties.WILLOW.leaves().noCollission()));
	public static final DeferredBlock<Block> WILLOW_SAPLING = BLOCKS.createBlock("willow_sapling", () -> new WillowSaplingBlock(EnvironmentalTreeGrowers.WILLOW, EnvironmentalTreeGrowers.WEEPING_WILLOW, EnvironmentalProperties.WILLOW.sapling()));
	public static final DeferredBlock<Block> POTTED_WILLOW_SAPLING = BLOCKS.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(WILLOW_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> WILLOW_PLANKS = BLOCKS.createBlock("willow_planks", () -> new Block(EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_STAIRS = BLOCKS.createBlock("willow_stairs", () -> new StairBlock(WILLOW_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_SLAB = BLOCKS.createBlock("willow_slab", () -> new SlabBlock(EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_PRESSURE_PLATE = BLOCKS.createBlock("willow_pressure_plate", () -> new PressurePlateBlock(EnvironmentalProperties.WILLOW_BLOCK_SET, EnvironmentalProperties.WILLOW.pressurePlate()));
	public static final DeferredBlock<Block> WILLOW_BUTTON = BLOCKS.createBlock("willow_button", () -> new ButtonBlock(EnvironmentalProperties.WILLOW_BLOCK_SET, 30, EnvironmentalProperties.WILLOW.button()));
	public static final DeferredBlock<Block> WILLOW_FENCE = BLOCKS.createBlock("willow_fence", () -> new FenceBlock(EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_FENCE_GATE = BLOCKS.createBlock("willow_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.WILLOW_WOOD_TYPE, EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_DOOR = BLOCKS.createBlock("willow_door", () -> new DoorBlock(EnvironmentalProperties.WILLOW_BLOCK_SET, EnvironmentalProperties.WILLOW.door()));
	public static final DeferredBlock<Block> WILLOW_TRAPDOOR = BLOCKS.createBlock("willow_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.WILLOW_BLOCK_SET, EnvironmentalProperties.WILLOW.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> WILLOW_SIGNS = BLOCKS.createSignBlock("willow", EnvironmentalProperties.WILLOW_WOOD_TYPE, EnvironmentalProperties.WILLOW.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> WILLOW_HANGING_SIGNS = BLOCKS.createHangingSignBlock("willow", EnvironmentalProperties.WILLOW_WOOD_TYPE, EnvironmentalProperties.WILLOW.hangingSign());

	public static final DeferredBlock<Block> WILLOW_BOARDS = BLOCKS.createBlock("willow_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.planks()));
	public static final DeferredBlock<Block> WILLOW_BOOKSHELF = BLOCKS.createBlock("willow_bookshelf", () -> new Block(EnvironmentalProperties.WILLOW.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_WILLOW_BOOKSHELF = BLOCKS.createBlock("chiseled_willow_bookshelf", () -> new ChiseledWillowBookShelfBlock(EnvironmentalProperties.WILLOW.chiseledBookshelf()));
	public static final DeferredBlock<Block> WILLOW_LADDER = BLOCKS.createBlock("willow_ladder", () -> new LadderBlock(EnvironmentalProperties.WILLOW.ladder()));
	public static final DeferredBlock<Block> WILLOW_BEEHIVE = BLOCKS.createBlock("willow_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WILLOW.beehive()));
	public static final DeferredBlock<Block> WILLOW_LEAF_PILE = BLOCKS.createBlock("willow_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WILLOW.leafPile()));
	public static final DeferredBlock<BlueprintChestBlock> WILLOW_CHEST = BLOCKS.createChestBlock("willow", EnvironmentalProperties.WILLOW.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_WILLOW_CHEST = BLOCKS.createTrappedChestBlock("willow", EnvironmentalProperties.WILLOW.chest());

	// Pine //

	public static final DeferredBlock<Block> STRIPPED_PINE_LOG = BLOCKS.createBlock("stripped_pine_log", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.log()));
	public static final DeferredBlock<Block> STRIPPED_PINE_WOOD = BLOCKS.createBlock("stripped_pine_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.log()));
	public static final DeferredBlock<Block> PINE_LOG = BLOCKS.createBlock("pine_log", () -> new LogBlock(STRIPPED_PINE_LOG, EnvironmentalProperties.PINE.log()));
	public static final DeferredBlock<Block> PINE_WOOD = BLOCKS.createBlock("pine_wood", () -> new LogBlock(STRIPPED_PINE_WOOD, EnvironmentalProperties.PINE.log()));
	public static final DeferredBlock<Block> PINE_LEAVES = BLOCKS.createBlock("pine_leaves", () -> new LeavesBlock(EnvironmentalProperties.PINE.leaves()));
	public static final DeferredBlock<Block> PINE_SAPLING = BLOCKS.createBlock("pine_sapling", () -> new PineSaplingBlock(EnvironmentalTreeGrowers.PINE, EnvironmentalTreeGrowers.TALL_PINE, EnvironmentalProperties.PINE.sapling()));
	public static final DeferredBlock<Block> POTTED_PINE_SAPLING = BLOCKS.createBlockNoItem("potted_pine_sapling", () -> new FlowerPotBlock(PINE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> PINE_PLANKS = BLOCKS.createBlock("pine_planks", () -> new Block(EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_STAIRS = BLOCKS.createBlock("pine_stairs", () -> new StairBlock(PINE_PLANKS.get().defaultBlockState(), EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_SLAB = BLOCKS.createBlock("pine_slab", () -> new SlabBlock(EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_PRESSURE_PLATE = BLOCKS.createBlock("pine_pressure_plate", () -> new PressurePlateBlock(EnvironmentalProperties.PINE_BLOCK_SET, EnvironmentalProperties.PINE.pressurePlate()));
	public static final DeferredBlock<Block> PINE_BUTTON = BLOCKS.createBlock("pine_button", () -> new ButtonBlock(EnvironmentalProperties.PINE_BLOCK_SET, 30, EnvironmentalProperties.PINE.button()));
	public static final DeferredBlock<Block> PINE_FENCE = BLOCKS.createBlock("pine_fence", () -> new FenceBlock(EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_FENCE_GATE = BLOCKS.createBlock("pine_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.PINE_WOOD_TYPE, EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_DOOR = BLOCKS.createBlock("pine_door", () -> new DoorBlock(EnvironmentalProperties.PINE_BLOCK_SET, EnvironmentalProperties.PINE.door()));
	public static final DeferredBlock<Block> PINE_TRAPDOOR = BLOCKS.createBlock("pine_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.PINE_BLOCK_SET, EnvironmentalProperties.PINE.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> PINE_SIGNS = BLOCKS.createSignBlock("pine", EnvironmentalProperties.PINE_WOOD_TYPE, EnvironmentalProperties.PINE.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> PINE_HANGING_SIGNS = BLOCKS.createHangingSignBlock("pine", EnvironmentalProperties.PINE_WOOD_TYPE, EnvironmentalProperties.PINE.hangingSign());

	public static final DeferredBlock<Block> PINE_BOARDS = BLOCKS.createBlock("pine_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.planks()));
	public static final DeferredBlock<Block> PINE_BOOKSHELF = BLOCKS.createBlock("pine_bookshelf", () -> new Block(EnvironmentalProperties.PINE.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_PINE_BOOKSHELF = BLOCKS.createBlock("chiseled_pine_bookshelf", () -> new ChiseledPineBookShelfBlock(EnvironmentalProperties.PINE.chiseledBookshelf()));
	public static final DeferredBlock<Block> PINE_LADDER = BLOCKS.createBlock("pine_ladder", () -> new LadderBlock(EnvironmentalProperties.PINE.ladder()));
	public static final DeferredBlock<Block> PINE_BEEHIVE = BLOCKS.createBlock("pine_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.PINE.beehive()));
	public static final DeferredBlock<Block> PINE_LEAF_PILE = BLOCKS.createBlock("pine_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PINE.leafPile()));
	public static final DeferredBlock<BlueprintChestBlock> PINE_CHEST = BLOCKS.createChestBlock("pine", EnvironmentalProperties.PINE.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_PINE_CHEST = BLOCKS.createTrappedChestBlock("pine", EnvironmentalProperties.PINE.chest());

	public static final DeferredBlock<Block> PINECONE = BLOCKS.createBlock("pinecone", () -> new PineconeBlock(EnvironmentalProperties.PINECONE.randomTicks()));
	public static final DeferredBlock<Block> WAXED_PINECONE = BLOCKS.createBlock("waxed_pinecone", () -> new WaxedPineconeBlock(EnvironmentalProperties.PINECONE));

	// Cedar //

	public static final DeferredBlock<Block> STRIPPED_CEDAR_LOG = BLOCKS.createBlock("stripped_cedar_log", () -> new RotatedPillarBlock(EnvironmentalProperties.CEDAR.log()));
	public static final DeferredBlock<Block> STRIPPED_CEDAR_WOOD = BLOCKS.createBlock("stripped_cedar_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.CEDAR.log()));
	public static final DeferredBlock<Block> CEDAR_LOG = BLOCKS.createBlock("cedar_log", () -> new LogBlock(STRIPPED_CEDAR_LOG, EnvironmentalProperties.CEDAR.log()));
	public static final DeferredBlock<Block> CEDAR_WOOD = BLOCKS.createBlock("cedar_wood", () -> new LogBlock(STRIPPED_CEDAR_WOOD, EnvironmentalProperties.CEDAR.log()));
	public static final DeferredBlock<Block> CEDAR_LEAVES = BLOCKS.createBlock("cedar_leaves", () -> new LeavesBlock(EnvironmentalProperties.CEDAR.leaves()));
	public static final DeferredBlock<Block> CEDAR_SAPLING = BLOCKS.createBlock("cedar_sapling", () -> new CedarSaplingBlock(EnvironmentalTreeGrowers.CEDAR, EnvironmentalProperties.CEDAR.sapling()));
	public static final DeferredBlock<Block> POTTED_CEDAR_SAPLING = BLOCKS.createBlockNoItem("potted_cedar_sapling", () -> new FlowerPotBlock(CEDAR_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> CEDAR_PLANKS = BLOCKS.createBlock("cedar_planks", () -> new Block(EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_STAIRS = BLOCKS.createBlock("cedar_stairs", () -> new StairBlock(CEDAR_PLANKS.get().defaultBlockState(), EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_SLAB = BLOCKS.createBlock("cedar_slab", () -> new SlabBlock(EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_PRESSURE_PLATE = BLOCKS.createBlock("cedar_pressure_plate", () -> new PressurePlateBlock(EnvironmentalProperties.CEDAR_BLOCK_SET, EnvironmentalProperties.CEDAR.pressurePlate()));
	public static final DeferredBlock<Block> CEDAR_BUTTON = BLOCKS.createBlock("cedar_button", () -> new ButtonBlock(EnvironmentalProperties.CEDAR_BLOCK_SET, 30, EnvironmentalProperties.CEDAR.button()));
	public static final DeferredBlock<Block> CEDAR_FENCE = BLOCKS.createBlock("cedar_fence", () -> new FenceBlock(EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_FENCE_GATE = BLOCKS.createBlock("cedar_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.CEDAR_WOOD_TYPE, EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_DOOR = BLOCKS.createBlock("cedar_door", () -> new DoorBlock(EnvironmentalProperties.CEDAR_BLOCK_SET, EnvironmentalProperties.CEDAR.door()));
	public static final DeferredBlock<Block> CEDAR_TRAPDOOR = BLOCKS.createBlock("cedar_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.CEDAR_BLOCK_SET, EnvironmentalProperties.CEDAR.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> CEDAR_SIGNS = BLOCKS.createSignBlock("cedar", EnvironmentalProperties.CEDAR_WOOD_TYPE, EnvironmentalProperties.CEDAR.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> CEDAR_HANGING_SIGNS = BLOCKS.createHangingSignBlock("cedar", EnvironmentalProperties.CEDAR_WOOD_TYPE, EnvironmentalProperties.CEDAR.hangingSign());

	public static final DeferredBlock<Block> CEDAR_BOARDS = BLOCKS.createBlock("cedar_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.CEDAR.planks()));
	public static final DeferredBlock<Block> CEDAR_BOOKSHELF = BLOCKS.createBlock("cedar_bookshelf", () -> new Block(EnvironmentalProperties.CEDAR.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_CEDAR_BOOKSHELF = BLOCKS.createBlock("chiseled_cedar_bookshelf", () -> new ChiseledPineBookShelfBlock(EnvironmentalProperties.CEDAR.chiseledBookshelf()));
	public static final DeferredBlock<Block> CEDAR_LADDER = BLOCKS.createBlock("cedar_ladder", () -> new LadderBlock(EnvironmentalProperties.CEDAR.ladder()));
	public static final DeferredBlock<Block> CEDAR_BEEHIVE = BLOCKS.createBlock("cedar_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.CEDAR.beehive()));
	public static final DeferredBlock<Block> CEDAR_LEAF_PILE = BLOCKS.createBlock("cedar_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.CEDAR.leafPile()));
	public static final DeferredBlock<BlueprintChestBlock> CEDAR_CHEST = BLOCKS.createChestBlock("cedar", EnvironmentalProperties.CEDAR.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_CEDAR_CHEST = BLOCKS.createTrappedChestBlock("cedar", EnvironmentalProperties.CEDAR.chest());

	// Plum //

	public static final DeferredBlock<Block> STRIPPED_PLUM_LOG = BLOCKS.createBlock("stripped_plum_log", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.log()));
	public static final DeferredBlock<Block> STRIPPED_PLUM_WOOD = BLOCKS.createBlock("stripped_plum_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.log()));
	public static final DeferredBlock<Block> PLUM_LOG = BLOCKS.createBlock("plum_log", () -> new LogBlock(STRIPPED_PLUM_LOG, EnvironmentalProperties.PLUM.log()));
	public static final DeferredBlock<Block> PLUM_WOOD = BLOCKS.createBlock("plum_wood", () -> new LogBlock(STRIPPED_PLUM_WOOD, EnvironmentalProperties.PLUM.log()));
	public static final DeferredBlock<Block> PLUM_PLANKS = BLOCKS.createBlock("plum_planks", () -> new Block(EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_STAIRS = BLOCKS.createBlock("plum_stairs", () -> new StairBlock(PLUM_PLANKS.get().defaultBlockState(), EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_SLAB = BLOCKS.createBlock("plum_slab", () -> new SlabBlock(EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_PRESSURE_PLATE = BLOCKS.createBlock("plum_pressure_plate", () -> new PressurePlateBlock(EnvironmentalProperties.PLUM_BLOCK_SET, EnvironmentalProperties.PLUM.pressurePlate()));
	public static final DeferredBlock<Block> PLUM_BUTTON = BLOCKS.createBlock("plum_button", () -> new ButtonBlock(EnvironmentalProperties.PLUM_BLOCK_SET, 30, EnvironmentalProperties.PLUM.button()));
	public static final DeferredBlock<Block> PLUM_FENCE = BLOCKS.createBlock("plum_fence", () -> new FenceBlock(EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_FENCE_GATE = BLOCKS.createBlock("plum_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.PLUM_WOOD_TYPE, EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_DOOR = BLOCKS.createBlock("plum_door", () -> new DoorBlock(EnvironmentalProperties.PLUM_BLOCK_SET, EnvironmentalProperties.PLUM.door()));
	public static final DeferredBlock<Block> PLUM_TRAPDOOR = BLOCKS.createBlock("plum_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.PLUM_BLOCK_SET, EnvironmentalProperties.PLUM.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> PLUM_SIGNS = BLOCKS.createSignBlock("plum", EnvironmentalProperties.PLUM_WOOD_TYPE, EnvironmentalProperties.PLUM.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> PLUM_HANGING_SIGNS = BLOCKS.createHangingSignBlock("plum", EnvironmentalProperties.PLUM_WOOD_TYPE, EnvironmentalProperties.PLUM.hangingSign());

	public static final DeferredBlock<Block> PLUM_BOARDS = BLOCKS.createBlock("plum_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.planks()));
	public static final DeferredBlock<Block> PLUM_BOOKSHELF = BLOCKS.createBlock("plum_bookshelf", () -> new Block(EnvironmentalProperties.PLUM.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_PLUM_BOOKSHELF = BLOCKS.createBlock("chiseled_plum_bookshelf", () -> new ChiseledPlumBookShelfBlock(EnvironmentalProperties.PLUM.chiseledBookshelf()));
	public static final DeferredBlock<Block> PLUM_LADDER = BLOCKS.createBlock("plum_ladder", () -> new LadderBlock(EnvironmentalProperties.PLUM.ladder()));
	public static final DeferredBlock<Block> PLUM_BEEHIVE = BLOCKS.createBlock("plum_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.PLUM.beehive()));
	public static final DeferredBlock<BlueprintChestBlock> PLUM_CHEST = BLOCKS.createChestBlock("plum", EnvironmentalProperties.PLUM.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_PLUM_CHEST = BLOCKS.createTrappedChestBlock("plum", EnvironmentalProperties.PLUM.chest());

	public static final DeferredBlock<Block> PLUM_LEAVES = BLOCKS.createBlock("plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves()));
	public static final DeferredBlock<Block> PLUM_SAPLING = BLOCKS.createBlock("plum_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.PLUM, EnvironmentalProperties.PLUM.sapling()));
	public static final DeferredBlock<Block> POTTED_PLUM_SAPLING = BLOCKS.createBlockNoItem("potted_plum_sapling", () -> new FlowerPotBlock(PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> PLUM_LEAF_PILE = BLOCKS.createBlock("plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile()));

	public static final DeferredBlock<Block> CHEERFUL_PLUM_LEAVES = BLOCKS.createBlock("cheerful_plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves()));
	public static final DeferredBlock<Block> CHEERFUL_PLUM_SAPLING = BLOCKS.createBlock("cheerful_plum_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.CHEERFUL_PLUM, EnvironmentalProperties.PLUM.sapling()));
	public static final DeferredBlock<Block> POTTED_CHEERFUL_PLUM_SAPLING = BLOCKS.createBlockNoItem("potted_cheerful_plum_sapling", () -> new FlowerPotBlock(CHEERFUL_PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> CHEERFUL_PLUM_LEAF_PILE = BLOCKS.createBlock("cheerful_plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile()));

	public static final DeferredBlock<Block> MOODY_PLUM_LEAVES = BLOCKS.createBlock("moody_plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves().mapColor(MapColor.TERRACOTTA_PINK)));
	public static final DeferredBlock<Block> MOODY_PLUM_SAPLING = BLOCKS.createBlock("moody_plum_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.MOODY_PLUM, EnvironmentalProperties.PLUM.sapling().mapColor(MapColor.TERRACOTTA_PINK)));
	public static final DeferredBlock<Block> POTTED_MOODY_PLUM_SAPLING = BLOCKS.createBlockNoItem("potted_moody_plum_sapling", () -> new FlowerPotBlock(MOODY_PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> MOODY_PLUM_LEAF_PILE = BLOCKS.createBlock("moody_plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile().mapColor(MapColor.TERRACOTTA_PINK)));

	public static final DeferredBlock<Block> PLUM_CRATE = BLOCKS.createBlock("plum_crate", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
	public static final DeferredBlock<Block> CHERRY_CRATE = BLOCKS.createBlock("cherry_crate", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	// Wisteria //

	public static final DeferredBlock<Block> STRIPPED_WISTERIA_LOG = BLOCKS.createBlock("stripped_wisteria_log", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.log()));
	public static final DeferredBlock<Block> STRIPPED_WISTERIA_WOOD = BLOCKS.createBlock("stripped_wisteria_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.log()));
	public static final DeferredBlock<Block> WISTERIA_LOG = BLOCKS.createBlock("wisteria_log", () -> new LogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA.log()));
	public static final DeferredBlock<Block> WISTERIA_WOOD = BLOCKS.createBlock("wisteria_wood", () -> new LogBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA.log()));
	public static final DeferredBlock<Block> WISTERIA_PLANKS = BLOCKS.createBlock("wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_STAIRS = BLOCKS.createBlock("wisteria_stairs", () -> new StairBlock(WISTERIA_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_SLAB = BLOCKS.createBlock("wisteria_slab", () -> new SlabBlock(EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_PRESSURE_PLATE = BLOCKS.createBlock("wisteria_pressure_plate", () -> new PressurePlateBlock(EnvironmentalProperties.WISTERIA_BLOCK_SET, EnvironmentalProperties.WISTERIA.pressurePlate()));
	public static final DeferredBlock<Block> WISTERIA_BUTTON = BLOCKS.createBlock("wisteria_button", () -> new ButtonBlock(EnvironmentalProperties.WISTERIA_BLOCK_SET, 30, EnvironmentalProperties.WISTERIA.button()));
	public static final DeferredBlock<Block> WISTERIA_FENCE = BLOCKS.createBlock("wisteria_fence", () -> new FenceBlock(EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_FENCE_GATE = BLOCKS.createBlock("wisteria_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.WISTERIA_WOOD_TYPE, EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_DOOR = BLOCKS.createBlock("wisteria_door", () -> new DoorBlock(EnvironmentalProperties.WISTERIA_BLOCK_SET, EnvironmentalProperties.WISTERIA.door()));
	public static final DeferredBlock<Block> WISTERIA_TRAPDOOR = BLOCKS.createBlock("wisteria_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.WISTERIA_BLOCK_SET, EnvironmentalProperties.WISTERIA.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> WISTERIA_SIGNS = BLOCKS.createSignBlock("wisteria", EnvironmentalProperties.WISTERIA_WOOD_TYPE, EnvironmentalProperties.WISTERIA.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> WISTERIA_HANGING_SIGNS = BLOCKS.createHangingSignBlock("wisteria", EnvironmentalProperties.WISTERIA_WOOD_TYPE, EnvironmentalProperties.WISTERIA.hangingSign());

	public static final DeferredBlock<Block> WISTERIA_LEAVES = BLOCKS.createBlock("wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves()));
	public static final DeferredBlock<Block> WISTERIA_LEAF_PILE = BLOCKS.createBlock("wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WISTERIA.leaves()));

	public static final DeferredBlock<Block> PINK_WISTERIA_LEAVES = BLOCKS.createBlock("pink_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA.leaves(), WisteriaColor.PINK));
	public static final DeferredBlock<Block> BLUE_WISTERIA_LEAVES = BLOCKS.createBlock("blue_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA.leaves(), WisteriaColor.BLUE));
	public static final DeferredBlock<Block> PURPLE_WISTERIA_LEAVES = BLOCKS.createBlock("purple_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA.leaves(), WisteriaColor.PURPLE));
	public static final DeferredBlock<Block> WHITE_WISTERIA_LEAVES = BLOCKS.createBlock("white_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves(), WisteriaColor.WHITE));
	public static final DeferredBlock<Block> PINK_HANGING_WISTERIA_LEAVES = BLOCKS.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA.leaves().noCollission(), WisteriaColor.PINK));
	public static final DeferredBlock<Block> BLUE_HANGING_WISTERIA_LEAVES = BLOCKS.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA.leaves().noCollission(), WisteriaColor.BLUE));
	public static final DeferredBlock<Block> PURPLE_HANGING_WISTERIA_LEAVES = BLOCKS.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA.leaves().noCollission(), WisteriaColor.PURPLE));
	public static final DeferredBlock<Block> WHITE_HANGING_WISTERIA_LEAVES = BLOCKS.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves().noCollission(), WisteriaColor.WHITE));
	public static final DeferredBlock<Block> PINK_WISTERIA_SAPLING = BLOCKS.createBlock("pink_wisteria_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.PINK_WISTERIA, EnvironmentalProperties.WISTERIA.sapling()));
	public static final DeferredBlock<Block> BLUE_WISTERIA_SAPLING = BLOCKS.createBlock("blue_wisteria_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.BLUE_WISTERIA, EnvironmentalProperties.WISTERIA.sapling()));
	public static final DeferredBlock<Block> PURPLE_WISTERIA_SAPLING = BLOCKS.createBlock("purple_wisteria_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.PURPLE_WISTERIA, EnvironmentalProperties.WISTERIA.sapling()));
	public static final DeferredBlock<Block> WHITE_WISTERIA_SAPLING = BLOCKS.createBlock("white_wisteria_sapling", () -> new SaplingBlock(EnvironmentalTreeGrowers.WHITE_WISTERIA, EnvironmentalProperties.WISTERIA.sapling()));
	public static final DeferredBlock<Block> POTTED_PINK_WISTERIA_SAPLING = BLOCKS.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_BLUE_WISTERIA_SAPLING = BLOCKS.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_PURPLE_WISTERIA_SAPLING = BLOCKS.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> POTTED_WHITE_WISTERIA_SAPLING = BLOCKS.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));

	public static final DeferredBlock<Block> WISTERIA_BOARDS = BLOCKS.createBlock("wisteria_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.planks()));
	public static final DeferredBlock<Block> WISTERIA_BOOKSHELF = BLOCKS.createBlock("wisteria_bookshelf", () -> new Block(EnvironmentalProperties.WISTERIA.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_WISTERIA_BOOKSHELF = BLOCKS.createBlock("chiseled_wisteria_bookshelf", () -> new ChiseledWisteriaBookShelfBlock(EnvironmentalProperties.WISTERIA.chiseledBookshelf()));
	public static final DeferredBlock<Block> WISTERIA_LADDER = BLOCKS.createBlock("wisteria_ladder", () -> new LadderBlock(EnvironmentalProperties.WISTERIA.ladder()));
	public static final DeferredBlock<Block> WISTERIA_BEEHIVE = BLOCKS.createBlock("wisteria_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WISTERIA.beehive()));
	public static final DeferredBlock<Block> PINK_WISTERIA_LEAF_PILE = BLOCKS.createBlock("pink_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PINK_WISTERIA.leafPile()));
	public static final DeferredBlock<Block> BLUE_WISTERIA_LEAF_PILE = BLOCKS.createBlock("blue_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.BLUE_WISTERIA.leafPile()));
	public static final DeferredBlock<Block> PURPLE_WISTERIA_LEAF_PILE = BLOCKS.createBlock("purple_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PURPLE_WISTERIA.leafPile()));
	public static final DeferredBlock<Block> WHITE_WISTERIA_LEAF_PILE = BLOCKS.createBlock("white_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WISTERIA.leafPile()));
	public static final DeferredBlock<BlueprintChestBlock> WISTERIA_CHEST = BLOCKS.createChestBlock("wisteria", EnvironmentalProperties.WISTERIA.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_WISTERIA_CHEST = BLOCKS.createTrappedChestBlock("wisteria", EnvironmentalProperties.WISTERIA.chest());

	public static void setupTabEditors() {
		CreativeModeTabContentsPopulator.mod(Environmental.MOD_ID)
				.tab(BUILDING_BLOCKS)
				.addItemsBefore(of(Blocks.PACKED_MUD), () -> Blocks.DIRT, DIRT_BRICKS, DIRT_BRICK_STAIRS, DIRT_BRICK_SLAB, DIRT_BRICK_WALL, DIRT_TILE_STAIRS, DIRT_TILES, DIRT_TILE_SLAB, DIRT_TILE_WALL)
				.addItemsBefore(of(Blocks.MUD_BRICKS), SMOOTH_MUD, SMOOTH_MUD_SLAB)
				.addItemsAfter(of(Blocks.MUD_BRICK_WALL), CHISELED_MUD_BRICKS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK), WILLOW_LOG, WILLOW_WOOD, STRIPPED_WILLOW_LOG, STRIPPED_WILLOW_WOOD, WILLOW_PLANKS)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), WILLOW_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						WILLOW_STAIRS, WILLOW_SLAB, WILLOW_FENCE, WILLOW_FENCE_GATE, WILLOW_DOOR, WILLOW_TRAPDOOR, WILLOW_PRESSURE_PLATE, WILLOW_BUTTON,
						CEDAR_LOG, CEDAR_WOOD, STRIPPED_CEDAR_LOG, STRIPPED_CEDAR_WOOD, CEDAR_PLANKS
				)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), CEDAR_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						CEDAR_STAIRS, CEDAR_SLAB, CEDAR_FENCE, CEDAR_FENCE_GATE, CEDAR_DOOR, CEDAR_TRAPDOOR, CEDAR_PRESSURE_PLATE, CEDAR_BUTTON,
						PINE_LOG, PINE_WOOD, STRIPPED_PINE_LOG, STRIPPED_PINE_WOOD, PINE_PLANKS
				)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), PINE_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						PINE_STAIRS, PINE_SLAB, PINE_FENCE, PINE_FENCE_GATE, PINE_DOOR, PINE_TRAPDOOR, PINE_PRESSURE_PLATE, PINE_BUTTON,
						PLUM_LOG, PLUM_WOOD, STRIPPED_PLUM_LOG, STRIPPED_PLUM_WOOD, PLUM_PLANKS
				)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), PLUM_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						PLUM_STAIRS, PLUM_SLAB, PLUM_FENCE, PLUM_FENCE_GATE, PLUM_DOOR, PLUM_TRAPDOOR, PLUM_PRESSURE_PLATE, PLUM_BUTTON,
						WISTERIA_LOG, WISTERIA_WOOD, STRIPPED_WISTERIA_LOG, STRIPPED_WISTERIA_WOOD, WISTERIA_PLANKS
				)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), WISTERIA_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						WISTERIA_STAIRS, WISTERIA_SLAB, WISTERIA_FENCE, WISTERIA_FENCE_GATE, WISTERIA_DOOR, WISTERIA_TRAPDOOR, WISTERIA_PRESSURE_PLATE, WISTERIA_BUTTON
				)
				.addItemsBefore(of(Blocks.SANDSTONE), GRASS_THATCH, GRASS_THATCH_STAIRS, GRASS_THATCH_SLAB, CATTAIL_THATCH, CATTAIL_THATCH_STAIRS, CATTAIL_THATCH_SLAB, DUCKWEED_THATCH, DUCKWEED_THATCH_STAIRS, DUCKWEED_THATCH_SLAB)
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(of(Blocks.BAMBOO_SIGN),
						WILLOW_SIGNS.getFirst(), WILLOW_HANGING_SIGNS.getFirst(),
						PINE_SIGNS.getFirst(), PINE_HANGING_SIGNS.getFirst(),
						CEDAR_SIGNS.getFirst(), CEDAR_HANGING_SIGNS.getFirst(),
						PLUM_SIGNS.getFirst(), PLUM_HANGING_SIGNS.getFirst(),
						WISTERIA_SIGNS.getFirst(), WISTERIA_HANGING_SIGNS.getFirst()
				)
				.addItemsBefore(of(Items.ARMOR_STAND), SLABFISH_EFFIGY)
				.tab(NATURAL_BLOCKS)
				.editor(event -> event.remove(new ItemStack(Blocks.DIRT_PATH), TabVisibility.PARENT_AND_SEARCH_TABS))
				.addItemsAfter(of(Blocks.MUD), MUDDY_SAND)
				.addItemsAfter(of(Blocks.GRASS_BLOCK), () -> Blocks.DIRT_PATH)
				.addItemsAfter(of(Blocks.PODZOL), PODZOL_PATH)
				.addItemsAfter(of(Blocks.MYCELIUM), MYCELIUM_PATH)
				.addItemsAfter(of(Blocks.DIRT), DIRT_PATH)
				.addItemsBefore(of(Blocks.FARMLAND), BURIED_TRUFFLE)
				.addItemsAfter(of(Blocks.LILY_PAD), DUCKWEED)
				.addItemsAfter(of(Blocks.SUGAR_CANE), CATTAIL)
				.addItemsAfter(of(Blocks.FERN), MYCELIUM_SPROUTS, DWARF_SPRUCE)
				.addItemsAfter(of(Blocks.TALL_GRASS), GIANT_TALL_GRASS)
				.addItemsAfter(of(Blocks.GLOW_LICHEN), CUP_LICHEN)
				.addItemsAfter(of(Blocks.GLOW_LICHEN), TREE_LICHEN)
				.addItemsAfter(of(Blocks.LARGE_FERN), SHRUB, FLOWERING_SHRUB)
				.addItemsAfter(of(Blocks.SUNFLOWER), RADIANT_SUNFLOWER, BEAMING_SUNFLOWER, ECLIPSED_SUNFLOWER, BLAZING_SUNFLOWER)
				.addItemsBefore(of(Blocks.TORCHFLOWER), BLUEBELL, DIANTHUS, VIOLET, TASSELFLOWER, RED_LOTUS_FLOWER, WHITE_LOTUS_FLOWER, CARTWHEEL,
						YELLOW_HIBISCUS, ORANGE_HIBISCUS, RED_HIBISCUS, PINK_HIBISCUS, MAGENTA_HIBISCUS, PURPLE_HIBISCUS)
				.addItemsBefore(of(Blocks.PITCHER_PLANT), PINK_DELPHINIUM, PURPLE_DELPHINIUM, BLUE_DELPHINIUM, WHITE_DELPHINIUM, BIRD_OF_PARADISE)
				.addItemsBefore(of(Blocks.MUSHROOM_STEM), WILLOW_LOG, PINE_LOG, CEDAR_LOG, PLUM_LOG, WISTERIA_LOG)
				.addItemsBefore(of(Blocks.MELON), PINECONE, WAXED_PINECONE)
				.addItemsAfter(modLoaded(Blocks.HAY_BLOCK, "berry_good"), PLUM_CRATE, CHERRY_CRATE)
				.addItemsAfter(of(Blocks.HAY_BLOCK), CATTAIL_FLUFF_BLOCK, YAK_HAIR_BLOCK, YAK_HAIR_RUG)
				.addItemsAfter(of(Blocks.VINE), HANGING_WILLOW_LEAVES, PINK_HANGING_WISTERIA_LEAVES, PURPLE_HANGING_WISTERIA_LEAVES, BLUE_HANGING_WISTERIA_LEAVES, WHITE_HANGING_WISTERIA_LEAVES)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), WILLOW_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), WILLOW_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), PINE_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), PINE_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), CEDAR_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), CEDAR_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), CHEERFUL_PLUM_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), CHEERFUL_PLUM_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), PLUM_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), PLUM_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), MOODY_PLUM_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), MOODY_PLUM_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), WISTERIA_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), WISTERIA_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), PINK_WISTERIA_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), PINK_WISTERIA_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), PURPLE_WISTERIA_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), PURPLE_WISTERIA_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), BLUE_WISTERIA_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), BLUE_WISTERIA_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), WHITE_WISTERIA_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), WHITE_WISTERIA_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), HIBISCUS_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), HIBISCUS_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA), WILLOW_SAPLING, PINE_SAPLING, CEDAR_SAPLING, CHEERFUL_PLUM_SAPLING, PLUM_SAPLING, MOODY_PLUM_SAPLING, PINK_WISTERIA_SAPLING, PURPLE_WISTERIA_SAPLING, BLUE_WISTERIA_SAPLING, WHITE_WISTERIA_SAPLING);

		CreativeModeTabContentsPopulator.mod("incubation_" + Environmental.MOD_ID)
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(ofID(EnvironmentalConstants.CHICKEN_EGG_CRATE), DUCK_EGG_CRATE);

		CreativeModeTabContentsPopulator.mod("woodworks_" + Environmental.MOD_ID)
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_LADDER), WILLOW_LADDER, PINE_LADDER, CEDAR_LADDER, PLUM_LADDER, WISTERIA_LADDER)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_BEEHIVE), WILLOW_BEEHIVE, PINE_BEEHIVE, CEDAR_BEEHIVE, PLUM_BEEHIVE, WISTERIA_BEEHIVE)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_BOOKSHELF), WILLOW_BOOKSHELF, CHISELED_WILLOW_BOOKSHELF, PINE_BOOKSHELF, CHISELED_PINE_BOOKSHELF, CEDAR_BOOKSHELF, CHISELED_CEDAR_BOOKSHELF, PLUM_BOOKSHELF, CHISELED_PLUM_BOOKSHELF, WISTERIA_BOOKSHELF, CHISELED_WISTERIA_BOOKSHELF)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_CLOSET), WILLOW_CHEST, PINE_CHEST, CEDAR_CHEST, PLUM_CHEST, WISTERIA_CHEST)
				.tab(REDSTONE_BLOCKS)
				.addItemsBefore(ofID(EnvironmentalConstants.TRAPPED_BAMBOO_CLOSET), TRAPPED_WILLOW_CHEST, TRAPPED_PINE_CHEST, TRAPPED_CEDAR_CHEST, TRAPPED_PLUM_CHEST, TRAPPED_WISTERIA_CHEST);
	}

	public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
		return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, ItemLike fallback, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) ? of(BuiltInRegistries.ITEM.get(location)) : of(fallback)).test(stack);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(BuiltInRegistries.ITEM.get(location)).test(stack));
	}
}
