package com.teamabnormals.environmental.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.*;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchSlabBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchStairBlock;
import com.teamabnormals.blueprint.common.block.thatch.ThatchVerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.wood.*;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.environmental.common.block.*;
import com.teamabnormals.environmental.common.block.grower.*;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalProperties;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlocks {
	public static final BlockSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBlockSubHelper();

	// Mud //

	public static final RegistryObject<Block> DIRT_BRICKS = HELPER.createBlock("dirt_bricks", () -> new Block(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_BRICK_STAIRS = HELPER.createBlock("dirt_brick_stairs", () -> new StairBlock(() -> DIRT_BRICKS.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_BRICK_SLAB = HELPER.createBlock("dirt_brick_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_BRICK_WALL = HELPER.createBlock("dirt_brick_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> DIRT_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "dirt_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> DIRT_TILES = HELPER.createBlock("dirt_tiles", () -> new Block(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_TILE_STAIRS = HELPER.createBlock("dirt_tile_stairs", () -> new StairBlock(() -> DIRT_TILES.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_TILE_SLAB = HELPER.createBlock("dirt_tile_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DIRT_TILE_WALL = HELPER.createBlock("dirt_tile_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> DIRT_TILE_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "dirt_tile_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.DIRT_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> SMOOTH_MUD = HELPER.createBlock("smooth_mud", () -> new Block(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SMOOTH_MUD_SLAB = HELPER.createBlock("smooth_mud_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SMOOTH_MUD_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "smooth_mud_vertical_slab", () -> new DirectionalVerticalSlabBlock(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_MUD_BRICKS = HELPER.createBlock("chiseled_mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SLABFISH_EFFIGY = HELPER.createBlock("slabfish_effigy", () -> new SlabfishEffigyBlock(PropertyUtil.flowerPot().sound(SoundType.MUD_BRICKS)), CreativeModeTab.TAB_DECORATIONS);

	// Crops //

	public static final RegistryObject<Block> CATTAIL_SPROUT = HELPER.createBlockNoItem("cattail_sprout", () -> new CattailSproutBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL = HELPER.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CATTAIL_STALK = HELPER.createBlockNoItem("cattail_stalk", () -> new CattailStalkBlock(EnvironmentalProperties.CATTAIL_STALK));
	public static final RegistryObject<Block> POTTED_CATTAIL = HELPER.createBlockNoItem("potted_cattail", () -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> CATTAIL_FLUFF_BLOCK = HELPER.createBlock("cattail_fluff_block", () -> new Block(Block.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_DECORATIONS);

	// Foliage //

	public static final RegistryObject<Block> DUCKWEED = HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));
	public static final RegistryObject<Block> LARGE_LILY_PAD = HELPER.createBlockNoItem("large_lily_pad", () -> new LargeLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));
	public static final RegistryObject<Block> GIANT_LILY_PAD = HELPER.createBlockNoItem("giant_lily_pad", () -> new GiantLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));

	public static final RegistryObject<Block> MYCELIUM_SPROUTS = HELPER.createBlock("mycelium_sprouts", () -> new MyceliumSproutsBlock(EnvironmentalProperties.MYCELIUM_SPROUTS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> GIANT_TALL_GRASS = HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.copy(Blocks.TALL_GRASS)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CUP_LICHEN = HELPER.createBlock("cup_lichen", () -> new CupLichenBlock(EnvironmentalProperties.CUP_LICHEN), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CACTUS_BOBBLE = HELPER.createBlockNoItem("cactus_bobble", () -> new CactusBobbleBlock(EnvironmentalProperties.CACTUS_BOBBLE));

	public static final RegistryObject<Block> DWARF_SPRUCE = HELPER.createFuelBlock("dwarf_spruce", () -> new DwarfSpruceBlock(EnvironmentalProperties.DWARF_SPRUCE), 100, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TALL_DWARF_SPRUCE = HELPER.createBlockNoItem("tall_dwarf_spruce", () -> new DwarfSpruceTallBlock(EnvironmentalProperties.DWARF_SPRUCE));

	public static final RegistryObject<Block> POTTED_CUP_LICHEN = HELPER.createBlockNoItem("potted_cup_lichen", () -> new FlowerPotBlock(EnvironmentalBlocks.CUP_LICHEN.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_DWARF_SPRUCE = HELPER.createBlockNoItem("potted_dwarf_spruce", () -> new FlowerPotBlock(EnvironmentalBlocks.DWARF_SPRUCE.get(), PropertyUtil.flowerPot()));

	// Misc //

	public static final RegistryObject<Block> BURIED_TRUFFLE = HELPER.createBlock("buried_truffle", () -> new Block(EnvironmentalProperties.BURIED_TRUFFLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCK_EGG_CRATE = HELPER.createCompatBlock("incubation", "duck_egg_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);

	// Decorations //

	public static final RegistryObject<Block> DIRT_PATH = HELPER.createBlock("dirt_path", () -> new DirtPathBlock(EnvironmentalProperties.DIRT_PATH), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PODZOL_PATH = HELPER.createBlock("podzol_path", () -> new DirtPathBlock(EnvironmentalProperties.PODZOL_PATH), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MYCELIUM_PATH = HELPER.createBlock("mycelium_path", () -> new DirtPathBlock(EnvironmentalProperties.MYCELIUM_PATH), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YAK_HAIR_BLOCK = HELPER.createFuelBlock("yak_hair_block", () -> new ThatchBlock(EnvironmentalProperties.YAK_HAIR_BLOCK), 200, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YAK_HAIR_RUG = HELPER.createFuelBlock("yak_hair_rug", () -> new RugBlock(EnvironmentalProperties.YAK_HAIR_RUG), 67, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> GRASS_THATCH = HELPER.createBlock("grass_thatch", () -> new ThatchBlock(EnvironmentalProperties.GRASS_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRASS_THATCH_SLAB = HELPER.createBlock("grass_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.GRASS_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRASS_THATCH_STAIRS = HELPER.createBlock("grass_thatch_stairs", () -> new ThatchStairBlock(GRASS_THATCH.get().defaultBlockState(), EnvironmentalProperties.GRASS_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRASS_THATCH_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "grass_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.GRASS_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> CATTAIL_THATCH = HELPER.createBlock("cattail_thatch", () -> new ThatchBlock(EnvironmentalProperties.CATTAIL_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB = HELPER.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.CATTAIL_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS = HELPER.createBlock("cattail_thatch_stairs", () -> new ThatchStairBlock(CATTAIL_THATCH.get().defaultBlockState(), EnvironmentalProperties.CATTAIL_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "cattail_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.CATTAIL_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> DUCKWEED_THATCH = HELPER.createBlock("duckweed_thatch", () -> new ThatchBlock(EnvironmentalProperties.DUCKWEED_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB = HELPER.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS = HELPER.createBlock("duckweed_thatch_stairs", () -> new ThatchStairBlock(DUCKWEED_THATCH.get().defaultBlockState(), EnvironmentalProperties.DUCKWEED_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "duckweed_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH), CreativeModeTab.TAB_BUILDING_BLOCKS);

	// Flowers //

	public static final RegistryObject<Block> CARTWHEEL = HELPER.createBlock("cartwheel", () -> new CartwheelBlock(() -> MobEffects.LEVITATION, 3, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUEBELL = HELPER.createBlock("bluebell", () -> new BlueprintFlowerBlock(() -> MobEffects.HUNGER, 8, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> VIOLET = HELPER.createBlock("violet", () -> new BlueprintFlowerBlock(() -> MobEffects.INVISIBILITY, 9, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> DIANTHUS = HELPER.createBlock("dianthus", () -> new BlueprintFlowerBlock(() -> MobEffects.DAMAGE_BOOST, 7, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> RED_LOTUS_FLOWER = HELPER.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_LOTUS_FLOWER = HELPER.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TASSELFLOWER = HELPER.createBlock("tasselflower", () -> new BlueprintFlowerBlock(() -> MobEffects.DIG_SLOWDOWN, 6, PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> HIBISCUS_LEAVES = HELPER.createBlock("hibiscus_leaves", () -> new HibiscusLeavesBlock(EnvironmentalProperties.HIBISCUS_LEAVES), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> HIBISCUS_LEAF_PILE = HELPER.createCompatBlock("woodworks", "hibiscus_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.HIBISCUS_LEAF_PILE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> HIBISCUS_LEAF_CARPET = HELPER.createCompatBlock("quark", "hibiscus_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.HIBISCUS_LEAF_CARPET), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> YELLOW_WALL_HIBISCUS = HELPER.createBlockNoItem("yellow_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final RegistryObject<Block> ORANGE_WALL_HIBISCUS = HELPER.createBlockNoItem("orange_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final RegistryObject<Block> RED_WALL_HIBISCUS = HELPER.createBlockNoItem("red_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final RegistryObject<Block> PINK_WALL_HIBISCUS = HELPER.createBlockNoItem("pink_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final RegistryObject<Block> MAGENTA_WALL_HIBISCUS = HELPER.createBlockNoItem("magenta_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));
	public static final RegistryObject<Block> PURPLE_WALL_HIBISCUS = HELPER.createBlockNoItem("purple_wall_hibiscus", () -> new WallHibiscusBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.WALL_HIBISCUS));

	public static final RegistryObject<Block> YELLOW_HIBISCUS = HELPER.createBlockNoItem("yellow_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, YELLOW_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final RegistryObject<Block> ORANGE_HIBISCUS = HELPER.createBlockNoItem("orange_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, ORANGE_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final RegistryObject<Block> RED_HIBISCUS = HELPER.createBlockNoItem("red_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, RED_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final RegistryObject<Block> PINK_HIBISCUS = HELPER.createBlockNoItem("pink_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, PINK_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final RegistryObject<Block> MAGENTA_HIBISCUS = HELPER.createBlockNoItem("magenta_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, MAGENTA_WALL_HIBISCUS.get(), PropertyUtil.flower()));
	public static final RegistryObject<Block> PURPLE_HIBISCUS = HELPER.createBlockNoItem("purple_hibiscus", () -> new HibiscusBlock(() -> MobEffects.GLOWING, 8, PURPLE_WALL_HIBISCUS.get(), PropertyUtil.flower()));

	public static final RegistryObject<Block> POTTED_CARTWHEEL = HELPER.createBlockNoItem("potted_cartwheel", () -> new PottedCartwheelBlock(CARTWHEEL.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BLUEBELL = HELPER.createBlockNoItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_VIOLET = HELPER.createBlockNoItem("potted_violet", () -> new FlowerPotBlock(VIOLET.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_DIANTHUS = HELPER.createBlockNoItem("potted_dianthus", () -> new FlowerPotBlock(DIANTHUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_RED_LOTUS_FLOWER = HELPER.createBlockNoItem("potted_red_lotus_flower", () -> new FlowerPotBlock(RED_LOTUS_FLOWER.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_WHITE_LOTUS_FLOWER = HELPER.createBlockNoItem("potted_white_lotus_flower", () -> new FlowerPotBlock(WHITE_LOTUS_FLOWER.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_TASSELFLOWER = HELPER.createBlockNoItem("potted_tasselflower", () -> new FlowerPotBlock(TASSELFLOWER.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_YELLOW_HIBISCUS = HELPER.createBlockNoItem("potted_yellow_hibiscus", () -> new FlowerPotBlock(YELLOW_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_ORANGE_HIBISCUS = HELPER.createBlockNoItem("potted_orange_hibiscus", () -> new FlowerPotBlock(ORANGE_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_RED_HIBISCUS = HELPER.createBlockNoItem("potted_red_hibiscus", () -> new FlowerPotBlock(RED_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PINK_HIBISCUS = HELPER.createBlockNoItem("potted_pink_hibiscus", () -> new FlowerPotBlock(PINK_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_MAGENTA_HIBISCUS = HELPER.createBlockNoItem("potted_magenta_hibiscus", () -> new FlowerPotBlock(MAGENTA_HIBISCUS.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PURPLE_HIBISCUS = HELPER.createBlockNoItem("potted_purple_hibiscus", () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), PropertyUtil.flowerPot()));

	// Tall Flowers //

	public static final RegistryObject<Block> PINK_DELPHINIUM = HELPER.createBlock("pink_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_DELPHINIUM = HELPER.createBlock("blue_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_DELPHINIUM = HELPER.createBlock("purple_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_DELPHINIUM = HELPER.createBlock("white_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BIRD_OF_PARADISE = HELPER.createBlock("bird_of_paradise", () -> new BlueprintTallFlowerBlock(PropertyUtil.flower()), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> POTTED_PINK_DELPHINIUM = HELPER.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BLUE_DELPHINIUM = HELPER.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PURPLE_DELPHINIUM = HELPER.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_WHITE_DELPHINIUM = HELPER.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BIRD_OF_PARADISE = HELPER.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), PropertyUtil.flowerPot()));

	// Willow //

	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG = HELPER.createBlock("stripped_willow_log", () -> new StrippedLogBlock(EnvironmentalProperties.WILLOW.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD = HELPER.createBlock("stripped_willow_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.WILLOW.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LOG = HELPER.createBlock("willow_log", () -> new LogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.WILLOW.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_WOOD = HELPER.createBlock("willow_wood", () -> new WoodBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.WILLOW.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LEAVES = HELPER.createBlock("willow_leaves", () -> new BlueprintLeavesBlock(EnvironmentalProperties.WILLOW.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingLeavesBlock(EnvironmentalProperties.WILLOW.leaves().noCollission()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_SAPLING = HELPER.createBlock("willow_sapling", () -> new BlueprintSaplingBlock(new WillowTreeGrower(), EnvironmentalProperties.WILLOW.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(WILLOW_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> WILLOW_PLANKS = HELPER.createBlock("willow_planks", () -> new PlanksBlock(EnvironmentalProperties.WILLOW.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_STAIRS = HELPER.createBlock("willow_stairs", () -> new WoodStairBlock(WILLOW_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WILLOW.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_SLAB = HELPER.createBlock("willow_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WILLOW.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WILLOW.pressurePlate()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_BUTTON = HELPER.createBlock("willow_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.WILLOW.button()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_FENCE = HELPER.createFuelBlock("willow_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WILLOW.planks()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE = HELPER.createFuelBlock("willow_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WILLOW.planks()), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_DOOR = HELPER.createBlock("willow_door", () -> new WoodDoorBlock(EnvironmentalProperties.WILLOW.door()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_TRAPDOOR = HELPER.createBlock("willow_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WILLOW.trapdoor()), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WILLOW_SIGNS = HELPER.createSignBlock("willow", MaterialColor.COLOR_GREEN);

	public static final RegistryObject<Block> WILLOW_BOARDS = HELPER.createCompatFuelBlock("woodworks", "willow_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.planks()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF = HELPER.createCompatFuelBlock("woodworks", "willow_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WILLOW.bookshelf()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LADDER = HELPER.createCompatFuelBlock("woodworks", "willow_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.WILLOW.ladder()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BEEHIVE = HELPER.createCompatBlock("woodworks", "willow_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WILLOW.beehive()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LEAF_PILE = HELPER.createCompatBlock("woodworks", "willow_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WILLOW.leafPile()), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> WILLOW_CHESTS = HELPER.createCompatChestBlocks("woodworks", "willow", MaterialColor.COLOR_GREEN);

	public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS = HELPER.createCompatBlock("quark", "vertical_willow_planks", () -> new Block(EnvironmentalProperties.WILLOW.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WILLOW.planks()), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_POST = HELPER.createCompatFuelBlock("quark", "stripped_willow_post", () -> new WoodPostBlock(EnvironmentalProperties.WILLOW.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_POST = HELPER.createCompatFuelBlock("quark", "willow_post", () -> new WoodPostBlock(STRIPPED_WILLOW_POST, EnvironmentalProperties.WILLOW.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LEAF_CARPET = HELPER.createCompatBlock("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WILLOW.leafCarpet()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_HEDGE = HELPER.createCompatFuelBlock("quark", "willow_hedge", () -> new HedgeBlock(EnvironmentalProperties.WILLOW.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	// Pine //

	public static final RegistryObject<Block> STRIPPED_PINE_LOG = HELPER.createBlock("stripped_pine_log", () -> new StrippedLogBlock(EnvironmentalProperties.PINE.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_PINE_WOOD = HELPER.createBlock("stripped_pine_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.PINE.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_LOG = HELPER.createBlock("pine_log", () -> new LogBlock(STRIPPED_PINE_LOG, EnvironmentalProperties.PINE.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_WOOD = HELPER.createBlock("pine_wood", () -> new WoodBlock(STRIPPED_PINE_WOOD, EnvironmentalProperties.PINE.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_LEAVES = HELPER.createBlock("pine_leaves", () -> new BlueprintLeavesBlock(EnvironmentalProperties.PINE.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_SAPLING = HELPER.createBlock("pine_sapling", () -> new BlueprintSaplingBlock(new PineTreeGrower(), EnvironmentalProperties.PINE.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_PINE_SAPLING = HELPER.createBlockNoItem("potted_pine_sapling", () -> new FlowerPotBlock(PINE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> PINE_PLANKS = HELPER.createBlock("pine_planks", () -> new PlanksBlock(EnvironmentalProperties.PINE.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_STAIRS = HELPER.createBlock("pine_stairs", () -> new WoodStairBlock(PINE_PLANKS.get().defaultBlockState(), EnvironmentalProperties.PINE.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_SLAB = HELPER.createBlock("pine_slab", () -> new WoodSlabBlock(EnvironmentalProperties.PINE.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_PRESSURE_PLATE = HELPER.createBlock("pine_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.PINE.pressurePlate()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> PINE_BUTTON = HELPER.createBlock("pine_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.PINE.button()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> PINE_FENCE = HELPER.createFuelBlock("pine_fence", () -> new WoodFenceBlock(EnvironmentalProperties.PINE.planks()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_FENCE_GATE = HELPER.createFuelBlock("pine_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.PINE.planks()), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> PINE_DOOR = HELPER.createBlock("pine_door", () -> new WoodDoorBlock(EnvironmentalProperties.PINE.door()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> PINE_TRAPDOOR = HELPER.createBlock("pine_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.PINE.trapdoor()), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> PINE_SIGNS = HELPER.createSignBlock("pine", MaterialColor.COLOR_GREEN);

	public static final RegistryObject<Block> PINE_BOARDS = HELPER.createCompatFuelBlock("woodworks", "pine_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.planks()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_BOOKSHELF = HELPER.createCompatFuelBlock("woodworks", "pine_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.PINE.bookshelf()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_LADDER = HELPER.createCompatFuelBlock("woodworks", "pine_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.PINE.ladder()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_BEEHIVE = HELPER.createCompatBlock("woodworks", "pine_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.PINE.beehive()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_LEAF_PILE = HELPER.createCompatBlock("woodworks", "pine_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PINE.leafPile()), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> PINE_CHESTS = HELPER.createCompatChestBlocks("woodworks", "pine", MaterialColor.COLOR_GREEN);

	public static final RegistryObject<Block> VERTICAL_PINE_PLANKS = HELPER.createCompatBlock("quark", "vertical_pine_planks", () -> new Block(EnvironmentalProperties.PINE.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINE_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "pine_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.PINE.planks()), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_PINE_POST = HELPER.createCompatFuelBlock("quark", "stripped_pine_post", () -> new WoodPostBlock(EnvironmentalProperties.PINE.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_POST = HELPER.createCompatFuelBlock("quark", "pine_post", () -> new WoodPostBlock(STRIPPED_PINE_POST, EnvironmentalProperties.PINE.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_LEAF_CARPET = HELPER.createCompatBlock("quark", "pine_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.PINE.leafCarpet()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINE_HEDGE = HELPER.createCompatFuelBlock("quark", "pine_hedge", () -> new HedgeBlock(EnvironmentalProperties.PINE.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> PINECONE = HELPER.createBlock("pinecone", () -> new PineconeBlock(EnvironmentalProperties.PINECONE.randomTicks()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WAXED_PINECONE = HELPER.createBlock("waxed_pinecone", () -> new WaxedPineconeBlock(EnvironmentalProperties.PINECONE), CreativeModeTab.TAB_DECORATIONS);

	// Cherry //

	public static final RegistryObject<Block> STRIPPED_CHERRY_LOG = HELPER.createBlock("stripped_cherry_log", () -> new StrippedLogBlock(EnvironmentalProperties.CHERRY.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_CHERRY_WOOD = HELPER.createBlock("stripped_cherry_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.CHERRY.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_LOG = HELPER.createBlock("cherry_log", () -> new LogBlock(STRIPPED_CHERRY_LOG, EnvironmentalProperties.CHERRY.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_WOOD = HELPER.createBlock("cherry_wood", () -> new WoodBlock(STRIPPED_CHERRY_WOOD, EnvironmentalProperties.CHERRY.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_PLANKS = HELPER.createBlock("cherry_planks", () -> new PlanksBlock(EnvironmentalProperties.CHERRY.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_STAIRS = HELPER.createBlock("cherry_stairs", () -> new WoodStairBlock(CHERRY_PLANKS.get().defaultBlockState(), EnvironmentalProperties.CHERRY.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_SLAB = HELPER.createBlock("cherry_slab", () -> new WoodSlabBlock(EnvironmentalProperties.CHERRY.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_PRESSURE_PLATE = HELPER.createBlock("cherry_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.CHERRY.pressurePlate()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_BUTTON = HELPER.createBlock("cherry_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.CHERRY.button()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_FENCE = HELPER.createFuelBlock("cherry_fence", () -> new WoodFenceBlock(EnvironmentalProperties.CHERRY.planks()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_FENCE_GATE = HELPER.createFuelBlock("cherry_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.CHERRY.planks()), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_DOOR = HELPER.createBlock("cherry_door", () -> new WoodDoorBlock(EnvironmentalProperties.CHERRY.door()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_TRAPDOOR = HELPER.createBlock("cherry_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.CHERRY.trapdoor()), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> CHERRY_SIGNS = HELPER.createSignBlock("cherry", MaterialColor.TERRACOTTA_RED);

	public static final RegistryObject<Block> CHERRY_BOARDS = HELPER.createCompatFuelBlock("woodworks", "cherry_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.CHERRY.planks()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_BOOKSHELF = HELPER.createCompatFuelBlock("woodworks", "cherry_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.CHERRY.bookshelf()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_LADDER = HELPER.createCompatFuelBlock("woodworks", "cherry_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.CHERRY.ladder()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_BEEHIVE = HELPER.createCompatBlock("woodworks", "cherry_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.CHERRY.beehive()), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> CHERRY_CHESTS = HELPER.createCompatChestBlocks("woodworks", "cherry", MaterialColor.TERRACOTTA_RED);

	public static final RegistryObject<Block> VERTICAL_CHERRY_PLANKS = HELPER.createCompatBlock("quark", "vertical_cherry_planks", () -> new Block(EnvironmentalProperties.CHERRY.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "cherry_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.CHERRY.planks()), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_CHERRY_POST = HELPER.createCompatFuelBlock("quark", "stripped_cherry_post", () -> new WoodPostBlock(EnvironmentalProperties.CHERRY.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_POST = HELPER.createCompatFuelBlock("quark", "cherry_post", () -> new WoodPostBlock(STRIPPED_CHERRY_POST, EnvironmentalProperties.CHERRY.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> CHERRY_LEAVES = HELPER.createBlock("cherry_leaves", () -> new CherryLeavesBlock(EnvironmentalProperties.CHERRY.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_SAPLING = HELPER.createBlock("cherry_sapling", () -> new BlueprintSaplingBlock(new CherryTreeGrower(), EnvironmentalProperties.CHERRY.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_CHERRY_SAPLING = HELPER.createBlockNoItem("potted_cherry_sapling", () -> new FlowerPotBlock(CHERRY_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> CHERRY_LEAF_PILE = HELPER.createBlock("cherry_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.CHERRY.leafPile()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_LEAF_CARPET = HELPER.createCompatBlock("quark", "cherry_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.CHERRY.leafCarpet()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_HEDGE = HELPER.createCompatFuelBlock("quark", "cherry_hedge", () -> new HedgeBlock(EnvironmentalProperties.CHERRY.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> CHEERFUL_CHERRY_LEAVES = HELPER.createBlock("cheerful_cherry_leaves", () -> new CherryLeavesBlock(EnvironmentalProperties.CHERRY.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHEERFUL_CHERRY_SAPLING = HELPER.createBlock("cheerful_cherry_sapling", () -> new BlueprintSaplingBlock(new CheerfulCherryTreeGrower(), EnvironmentalProperties.CHERRY.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_CHEERFUL_CHERRY_SAPLING = HELPER.createBlockNoItem("potted_cheerful_cherry_sapling", () -> new FlowerPotBlock(CHEERFUL_CHERRY_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> CHEERFUL_CHERRY_LEAF_PILE = HELPER.createBlock("cheerful_cherry_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.CHERRY.leafPile()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHEERFUL_CHERRY_LEAF_CARPET = HELPER.createCompatBlock("quark", "cheerful_cherry_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.CHERRY.leafCarpet()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHEERFUL_CHERRY_HEDGE = HELPER.createCompatFuelBlock("quark", "cheerful_cherry_hedge", () -> new HedgeBlock(EnvironmentalProperties.CHERRY.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> MOODY_CHERRY_LEAVES = HELPER.createBlock("moody_cherry_leaves", () -> new CherryLeavesBlock(EnvironmentalProperties.CHERRY.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MOODY_CHERRY_SAPLING = HELPER.createBlock("moody_cherry_sapling", () -> new BlueprintSaplingBlock(new MoodyCherryTreeGrower(), EnvironmentalProperties.CHERRY.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_MOODY_CHERRY_SAPLING = HELPER.createBlockNoItem("potted_moody_cherry_sapling", () -> new FlowerPotBlock(MOODY_CHERRY_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> MOODY_CHERRY_LEAF_PILE = HELPER.createBlock("moody_cherry_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.CHERRY.leafPile()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MOODY_CHERRY_LEAF_CARPET = HELPER.createCompatBlock("quark", "moody_cherry_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.CHERRY.leafCarpet()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MOODY_CHERRY_HEDGE = HELPER.createCompatFuelBlock("quark", "moody_cherry_hedge", () -> new HedgeBlock(EnvironmentalProperties.CHERRY.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> CHERRY_CRATE = HELPER.createCompatBlock("quark", "cherry_crate", () -> new BlueprintDirectionalBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);

	// Wisteria //

	public static final RegistryObject<Block> STRIPPED_WISTERIA_LOG = HELPER.createBlock("stripped_wisteria_log", () -> new StrippedLogBlock(EnvironmentalProperties.WISTERIA.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WISTERIA_WOOD = HELPER.createBlock("stripped_wisteria_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.WISTERIA.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_LOG = HELPER.createBlock("wisteria_log", () -> new LogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_WOOD = HELPER.createBlock("wisteria_wood", () -> new WoodBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA.log()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_PLANKS = HELPER.createBlock("wisteria_planks", () -> new PlanksBlock(EnvironmentalProperties.WISTERIA.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_STAIRS = HELPER.createBlock("wisteria_stairs", () -> new WoodStairBlock(WISTERIA_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WISTERIA.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_SLAB = HELPER.createBlock("wisteria_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WISTERIA.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_PRESSURE_PLATE = HELPER.createBlock("wisteria_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WISTERIA.pressurePlate()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_BUTTON = HELPER.createBlock("wisteria_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.WISTERIA.button()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_FENCE = HELPER.createFuelBlock("wisteria_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WISTERIA.planks()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_FENCE_GATE = HELPER.createFuelBlock("wisteria_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WISTERIA.planks()), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_DOOR = HELPER.createBlock("wisteria_door", () -> new WoodDoorBlock(EnvironmentalProperties.WISTERIA.door()), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_TRAPDOOR = HELPER.createBlock("wisteria_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WISTERIA.trapdoor()), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WISTERIA_SIGNS = HELPER.createSignBlock("wisteria", MaterialColor.TERRACOTTA_WHITE);

	public static final RegistryObject<Block> WISTERIA_LEAVES = HELPER.createBlock("wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_LEAF_PILE = HELPER.createCompatBlock("woodworks", "wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WISTERIA.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WISTERIA.leaves()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> PINK_WISTERIA_LEAVES = HELPER.createBlock("pink_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_PINK), WisteriaColor.PINK), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAVES = HELPER.createBlock("blue_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_BLUE), WisteriaColor.BLUE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAVES = HELPER.createBlock("purple_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_PURPLE), WisteriaColor.PURPLE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAVES = HELPER.createBlock("white_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.SNOW), WisteriaColor.WHITE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_HANGING_WISTERIA_LEAVES = HELPER.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_PINK).noCollission(), WisteriaColor.PINK), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_BLUE).noCollission(), WisteriaColor.BLUE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.COLOR_PURPLE).noCollission(), WisteriaColor.PURPLE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.leaves(MaterialColor.SNOW).noCollission(), WisteriaColor.WHITE), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_WISTERIA_SAPLING = HELPER.createBlock("pink_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.PINK), EnvironmentalProperties.WISTERIA.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_SAPLING = HELPER.createBlock("blue_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.BLUE), EnvironmentalProperties.WISTERIA.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_SAPLING = HELPER.createBlock("purple_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.PURPLE), EnvironmentalProperties.WISTERIA.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_SAPLING = HELPER.createBlock("white_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.WHITE), EnvironmentalProperties.WISTERIA.sapling()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_PINK_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BLUE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PURPLE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_WHITE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));

	public static final RegistryObject<Block> WISTERIA_BOARDS = HELPER.createCompatFuelBlock("woodworks", "wisteria_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.planks()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_BOOKSHELF = HELPER.createCompatFuelBlock("woodworks", "wisteria_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WISTERIA.bookshelf()), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_LADDER = HELPER.createCompatFuelBlock("woodworks", "wisteria_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.WISTERIA.ladder()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_BEEHIVE = HELPER.createCompatBlock("woodworks", "wisteria_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WISTERIA.beehive()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_WISTERIA_LEAF_PILE = HELPER.createCompatBlock("woodworks", "pink_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.leafPile(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_PILE = HELPER.createCompatBlock("woodworks", "blue_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.leafPile(MaterialColor.COLOR_BLUE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_PILE = HELPER.createCompatBlock("woodworks", "purple_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.leafPile(MaterialColor.COLOR_PURPLE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_PILE = HELPER.createCompatBlock("woodworks", "white_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.leafPile(MaterialColor.SNOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> WISTERIA_CHESTS = HELPER.createCompatChestBlocks("woodworks", "wisteria", MaterialColor.TERRACOTTA_WHITE);

	public static final RegistryObject<Block> VERTICAL_WISTERIA_PLANKS = HELPER.createCompatBlock("quark", "vertical_wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA.planks()), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "wisteria_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WISTERIA.planks()), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WISTERIA_POST = HELPER.createCompatFuelBlock("quark", "stripped_wisteria_post", () -> new WoodPostBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_POST = HELPER.createCompatFuelBlock("quark", "wisteria_post", () -> new WoodPostBlock(STRIPPED_WISTERIA_POST, EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "pink_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.leafCarpet(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "blue_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.leafCarpet(MaterialColor.COLOR_BLUE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "purple_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.leafCarpet(MaterialColor.COLOR_PURPLE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "white_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.leafCarpet(MaterialColor.SNOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "pink_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "blue_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "purple_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "white_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA.log()), 300, CreativeModeTab.TAB_DECORATIONS);
}
