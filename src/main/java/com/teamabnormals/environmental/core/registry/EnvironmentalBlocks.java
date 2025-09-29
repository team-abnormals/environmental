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
import com.teamabnormals.environmental.common.block.grower.*;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.other.EnvironmentalProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlocks {
	public static final BlockSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBlockSubHelper();

	// Mud //

	public static final RegistryObject<Block> DIRT_BRICKS = HELPER.createBlock("dirt_bricks", () -> new Block(EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_BRICK_STAIRS = HELPER.createBlock("dirt_brick_stairs", () -> new StairBlock(() -> DIRT_BRICKS.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_BRICK_SLAB = HELPER.createBlock("dirt_brick_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_BRICK_WALL = HELPER.createBlock("dirt_brick_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS));

	public static final RegistryObject<Block> DIRT_TILES = HELPER.createBlock("dirt_tiles", () -> new Block(EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_TILE_STAIRS = HELPER.createBlock("dirt_tile_stairs", () -> new StairBlock(() -> DIRT_TILES.get().defaultBlockState(), EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_TILE_SLAB = HELPER.createBlock("dirt_tile_slab", () -> new SlabBlock(EnvironmentalProperties.DIRT_BRICKS));
	public static final RegistryObject<Block> DIRT_TILE_WALL = HELPER.createBlock("dirt_tile_wall", () -> new WallBlock(EnvironmentalProperties.DIRT_BRICKS));

	public static final RegistryObject<Block> SMOOTH_MUD = HELPER.createBlock("smooth_mud", () -> new Block(EnvironmentalProperties.MUD_BRICKS));
	public static final RegistryObject<Block> SMOOTH_MUD_SLAB = HELPER.createBlock("smooth_mud_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS));
	public static final RegistryObject<Block> CHISELED_MUD_BRICKS = HELPER.createBlock("chiseled_mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS));
	public static final RegistryObject<Block> SLABFISH_EFFIGY = HELPER.createBlock("slabfish_effigy", () -> new SlabfishEffigyBlock(PropertyUtil.flowerPot().sound(SoundType.MUD_BRICKS)));

	// Crops //

	public static final RegistryObject<Block> CATTAIL_SPROUT = HELPER.createBlockNoItem("cattail_sprout", () -> new CattailSproutBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL = HELPER.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL_STALK = HELPER.createBlockNoItem("cattail_stalk", () -> new CattailStalkBlock(EnvironmentalProperties.CATTAIL_STALK));
	public static final RegistryObject<Block> POTTED_CATTAIL = HELPER.createBlockNoItem("potted_cattail", () -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> CATTAIL_FLUFF_BLOCK = HELPER.createBlock("cattail_fluff_block", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.WOOL).ignitedByLava()));

	// Foliage //

	public static final RegistryObject<Block> DUCKWEED = HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));
	public static final RegistryObject<Block> LARGE_LILY_PAD = HELPER.createBlockNoItem("large_lily_pad", () -> new LargeLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));
	public static final RegistryObject<Block> GIANT_LILY_PAD = HELPER.createBlockNoItem("giant_lily_pad", () -> new GiantLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));

	public static final RegistryObject<Block> MYCELIUM_SPROUTS = HELPER.createBlock("mycelium_sprouts", () -> new MyceliumSproutsBlock(EnvironmentalProperties.MYCELIUM_SPROUTS));
	public static final RegistryObject<Block> GIANT_TALL_GRASS = HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.copy(Blocks.TALL_GRASS)));
	public static final RegistryObject<Block> CUP_LICHEN = HELPER.createBlock("cup_lichen", () -> new CupLichenBlock(EnvironmentalProperties.CUP_LICHEN));
	public static final RegistryObject<Block> CACTUS_BOBBLE = HELPER.createBlockNoItem("cactus_bobble", () -> new CactusBobbleBlock(EnvironmentalProperties.CACTUS_BOBBLE));

	public static final RegistryObject<Block> DWARF_SPRUCE = HELPER.createFuelBlock("dwarf_spruce", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE), 100);
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT = HELPER.createBlockNoItem("dwarf_spruce_plant", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE, (DwarfSpruceHeadBlock) DWARF_SPRUCE.get()));
	public static final RegistryObject<Block> DWARF_SPRUCE_TORCH = HELPER.createBlockNoItem("dwarf_spruce_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), () -> Items.TORCH));
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT_TORCH = HELPER.createBlockNoItem("dwarf_spruce_plant_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), () -> Items.TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_TORCH.get()));
	public static final RegistryObject<Block> DWARF_SPRUCE_SOUL_TORCH = HELPER.createBlockNoItem("dwarf_spruce_soul_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), () -> Items.SOUL_TORCH));
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT_SOUL_TORCH = HELPER.createBlockNoItem("dwarf_spruce_plant_soul_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), () -> Items.SOUL_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_SOUL_TORCH.get()));
	public static final RegistryObject<Block> DWARF_SPRUCE_REDSTONE_TORCH = HELPER.createBlockNoItem("dwarf_spruce_redstone_torch", () -> new RedstoneDwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0), () -> Items.REDSTONE_TORCH));
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT_REDSTONE_TORCH = HELPER.createBlockNoItem("dwarf_spruce_plant_redstone_torch", () -> new RedstoneDwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 7 : 0), () -> Items.REDSTONE_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_REDSTONE_TORCH.get()));
	public static final RegistryObject<Block> DWARF_SPRUCE_ENDER_TORCH = HELPER.createBlockNoItem("dwarf_spruce_ender_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), EnvironmentalConstants.ENDER_TORCH));
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT_ENDER_TORCH = HELPER.createBlockNoItem("dwarf_spruce_plant_ender_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 14), EnvironmentalConstants.ENDER_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_ENDER_TORCH.get()));
	public static final RegistryObject<Block> DWARF_SPRUCE_CUPRIC_TORCH = HELPER.createBlockNoItem("dwarf_spruce_cupric_torch", () -> new DwarfSpruceHeadBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), EnvironmentalConstants.CUPRIC_TORCH));
	public static final RegistryObject<Block> DWARF_SPRUCE_PLANT_CUPRIC_TORCH = HELPER.createBlockNoItem("dwarf_spruce_plant_cupric_torch", () -> new DwarfSprucePlantBlock(EnvironmentalProperties.DWARF_SPRUCE.lightLevel(state -> 10), EnvironmentalConstants.CUPRIC_TORCH, (DwarfSpruceHeadBlock) DWARF_SPRUCE_CUPRIC_TORCH.get()));

	public static final RegistryObject<Block> POTTED_CUP_LICHEN = HELPER.createBlockNoItem("potted_cup_lichen", () -> new FlowerPotBlock(EnvironmentalBlocks.CUP_LICHEN.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_DWARF_SPRUCE = HELPER.createBlockNoItem("potted_dwarf_spruce", () -> new FlowerPotBlock(EnvironmentalBlocks.DWARF_SPRUCE.get(), PropertyUtil.flowerPot()));

	// Misc //

	public static final RegistryObject<Block> BURIED_TRUFFLE = HELPER.createBlock("buried_truffle", () -> new Block(EnvironmentalProperties.BURIED_TRUFFLE));
	public static final RegistryObject<Block> DUCK_EGG_CRATE = HELPER.createBlock("duck_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	// Decorations //

	public static final RegistryObject<Block> DIRT_PATH = HELPER.createBlock("dirt_path", () -> new DirtPathBlock(EnvironmentalProperties.DIRT_PATH));
	public static final RegistryObject<Block> PODZOL_PATH = HELPER.createBlock("podzol_path", () -> new DirtPathBlock(EnvironmentalProperties.PODZOL_PATH));
	public static final RegistryObject<Block> MYCELIUM_PATH = HELPER.createBlock("mycelium_path", () -> new DirtPathBlock(EnvironmentalProperties.MYCELIUM_PATH));
	public static final RegistryObject<Block> YAK_HAIR_BLOCK = HELPER.createFuelBlock("yak_hair_block", () -> new ThatchBlock(EnvironmentalProperties.YAK_HAIR_BLOCK), 200);
	public static final RegistryObject<Block> YAK_HAIR_RUG = HELPER.createFuelBlock("yak_hair_rug", () -> new RugBlock(EnvironmentalProperties.YAK_HAIR_RUG), 67);

	public static final RegistryObject<Block> GRASS_THATCH = HELPER.createBlock("grass_thatch", () -> new ThatchBlock(EnvironmentalProperties.GRASS_THATCH));
	public static final RegistryObject<Block> GRASS_THATCH_SLAB = HELPER.createBlock("grass_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.GRASS_THATCH));
	public static final RegistryObject<Block> GRASS_THATCH_STAIRS = HELPER.createBlock("grass_thatch_stairs", () -> new ThatchStairBlock(GRASS_THATCH.get().defaultBlockState(), EnvironmentalProperties.GRASS_THATCH));

	public static final RegistryObject<Block> CATTAIL_THATCH = HELPER.createBlock("cattail_thatch", () -> new ThatchBlock(EnvironmentalProperties.CATTAIL_THATCH));
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB = HELPER.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.CATTAIL_THATCH));
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS = HELPER.createBlock("cattail_thatch_stairs", () -> new ThatchStairBlock(CATTAIL_THATCH.get().defaultBlockState(), EnvironmentalProperties.CATTAIL_THATCH));

	public static final RegistryObject<Block> DUCKWEED_THATCH = HELPER.createBlock("duckweed_thatch", () -> new ThatchBlock(EnvironmentalProperties.DUCKWEED_THATCH));
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB = HELPER.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH));
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS = HELPER.createBlock("duckweed_thatch_stairs", () -> new ThatchStairBlock(DUCKWEED_THATCH.get().defaultBlockState(), EnvironmentalProperties.DUCKWEED_THATCH));

	// Flowers //

	public static final RegistryObject<Block> CARTWHEEL = HELPER.createBlock("cartwheel", () -> new CartwheelBlock(() -> MobEffects.LEVITATION, 3, PropertyUtil.flower()));
	public static final RegistryObject<Block> BLUEBELL = HELPER.createBlock("bluebell", () -> new FlowerBlock(() -> MobEffects.HUNGER, 8, PropertyUtil.flower()));
	public static final RegistryObject<Block> VIOLET = HELPER.createBlock("violet", () -> new FlowerBlock(() -> MobEffects.INVISIBILITY, 9, PropertyUtil.flower()));
	public static final RegistryObject<Block> DIANTHUS = HELPER.createBlock("dianthus", () -> new FlowerBlock(() -> MobEffects.DAMAGE_BOOST, 7, PropertyUtil.flower()));
	public static final RegistryObject<Block> RED_LOTUS_FLOWER = HELPER.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()));
	public static final RegistryObject<Block> WHITE_LOTUS_FLOWER = HELPER.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, PropertyUtil.flower()));
	public static final RegistryObject<Block> TASSELFLOWER = HELPER.createBlock("tasselflower", () -> new FlowerBlock(() -> MobEffects.DIG_SLOWDOWN, 6, PropertyUtil.flower()));

	public static final RegistryObject<Block> HIBISCUS_LEAVES = HELPER.createBlock("hibiscus_leaves", () -> new HibiscusLeavesBlock(EnvironmentalProperties.HIBISCUS.leaves()));
	public static final RegistryObject<Block> HIBISCUS_LEAF_PILE = HELPER.createBlock("hibiscus_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.HIBISCUS.leafPile()));

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

	public static final RegistryObject<Block> PINK_DELPHINIUM = HELPER.createBlock("pink_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final RegistryObject<Block> BLUE_DELPHINIUM = HELPER.createBlock("blue_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final RegistryObject<Block> PURPLE_DELPHINIUM = HELPER.createBlock("purple_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final RegistryObject<Block> WHITE_DELPHINIUM = HELPER.createBlock("white_delphinium", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));
	public static final RegistryObject<Block> BIRD_OF_PARADISE = HELPER.createBlock("bird_of_paradise", () -> new TallFlowerBlock(EnvironmentalProperties.TALL_FLOWERS));

	public static final RegistryObject<Block> POTTED_PINK_DELPHINIUM = HELPER.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BLUE_DELPHINIUM = HELPER.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PURPLE_DELPHINIUM = HELPER.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_WHITE_DELPHINIUM = HELPER.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BIRD_OF_PARADISE = HELPER.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), PropertyUtil.flowerPot()));

	// Willow //

	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG = HELPER.createBlock("stripped_willow_log", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.log()));
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD = HELPER.createBlock("stripped_willow_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.log()));
	public static final RegistryObject<Block> WILLOW_LOG = HELPER.createBlock("willow_log", () -> new LogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.WILLOW.log()));
	public static final RegistryObject<Block> WILLOW_WOOD = HELPER.createBlock("willow_wood", () -> new LogBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.WILLOW.log()));
	public static final RegistryObject<Block> WILLOW_LEAVES = HELPER.createBlock("willow_leaves", () -> new LeavesBlock(EnvironmentalProperties.WILLOW.leaves()));
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingLeavesBlock(EnvironmentalProperties.WILLOW.leaves().noCollission()));
	public static final RegistryObject<Block> WILLOW_SAPLING = HELPER.createBlock("willow_sapling", () -> new SaplingBlock(new WillowTreeGrower(), EnvironmentalProperties.WILLOW.sapling()));
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(WILLOW_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> WILLOW_PLANKS = HELPER.createBlock("willow_planks", () -> new Block(EnvironmentalProperties.WILLOW.planks()));
	public static final RegistryObject<Block> WILLOW_STAIRS = HELPER.createBlock("willow_stairs", () -> new StairBlock(() -> WILLOW_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WILLOW.planks()));
	public static final RegistryObject<Block> WILLOW_SLAB = HELPER.createBlock("willow_slab", () -> new SlabBlock(EnvironmentalProperties.WILLOW.planks()));
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WILLOW.pressurePlate(), EnvironmentalProperties.WILLOW_BLOCK_SET));
	public static final RegistryObject<Block> WILLOW_BUTTON = HELPER.createBlock("willow_button", () -> new ButtonBlock(EnvironmentalProperties.WILLOW.button(), EnvironmentalProperties.WILLOW_BLOCK_SET, 30, true));
	public static final RegistryObject<Block> WILLOW_FENCE = HELPER.createFuelBlock("willow_fence", () -> new FenceBlock(EnvironmentalProperties.WILLOW.planks()), 300);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE = HELPER.createFuelBlock("willow_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.WILLOW.planks(), EnvironmentalProperties.WILLOW_WOOD_TYPE), 300);
	public static final RegistryObject<Block> WILLOW_DOOR = HELPER.createBlock("willow_door", () -> new DoorBlock(EnvironmentalProperties.WILLOW.door(), EnvironmentalProperties.WILLOW_BLOCK_SET));
	public static final RegistryObject<Block> WILLOW_TRAPDOOR = HELPER.createBlock("willow_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.WILLOW.trapdoor(), EnvironmentalProperties.WILLOW_BLOCK_SET));
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WILLOW_SIGNS = HELPER.createSignBlock("willow", EnvironmentalProperties.WILLOW_WOOD_TYPE, EnvironmentalProperties.WILLOW.sign());
	public static final Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> WILLOW_HANGING_SIGNS = HELPER.createHangingSignBlock("willow", EnvironmentalProperties.WILLOW_WOOD_TYPE, EnvironmentalProperties.WILLOW.hangingSign());

	public static final RegistryObject<Block> WILLOW_BOARDS = HELPER.createFuelBlock("willow_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WILLOW.planks()), 300);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF = HELPER.createFuelBlock("willow_bookshelf", () -> new Block(EnvironmentalProperties.WILLOW.bookshelf()), 300);
	public static final RegistryObject<Block> CHISELED_WILLOW_BOOKSHELF = HELPER.createFuelBlock("chiseled_willow_bookshelf", () -> new ChiseledWillowBookShelfBlock(EnvironmentalProperties.WILLOW.chiseledBookshelf()), 300);
	public static final RegistryObject<Block> WILLOW_LADDER = HELPER.createFuelBlock("willow_ladder", () -> new LadderBlock(EnvironmentalProperties.WILLOW.ladder()), 300);
	public static final RegistryObject<Block> WILLOW_BEEHIVE = HELPER.createBlock("willow_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WILLOW.beehive()));
	public static final RegistryObject<Block> WILLOW_LEAF_PILE = HELPER.createBlock("willow_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WILLOW.leafPile()));
	public static final RegistryObject<BlueprintChestBlock> WILLOW_CHEST = HELPER.createChestBlock("willow", EnvironmentalProperties.WILLOW.chest());
	public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_WILLOW_CHEST = HELPER.createTrappedChestBlockNamed("willow", EnvironmentalProperties.WILLOW.chest());

	// Pine //

	public static final RegistryObject<Block> STRIPPED_PINE_LOG = HELPER.createBlock("stripped_pine_log", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.log()));
	public static final RegistryObject<Block> STRIPPED_PINE_WOOD = HELPER.createBlock("stripped_pine_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.log()));
	public static final RegistryObject<Block> PINE_LOG = HELPER.createBlock("pine_log", () -> new LogBlock(STRIPPED_PINE_LOG, EnvironmentalProperties.PINE.log()));
	public static final RegistryObject<Block> PINE_WOOD = HELPER.createBlock("pine_wood", () -> new LogBlock(STRIPPED_PINE_WOOD, EnvironmentalProperties.PINE.log()));
	public static final RegistryObject<Block> PINE_LEAVES = HELPER.createBlock("pine_leaves", () -> new LeavesBlock(EnvironmentalProperties.PINE.leaves()));
	public static final RegistryObject<Block> PINE_SAPLING = HELPER.createBlock("pine_sapling", () -> new SaplingBlock(new PineTreeGrower(), EnvironmentalProperties.PINE.sapling()));
	public static final RegistryObject<Block> POTTED_PINE_SAPLING = HELPER.createBlockNoItem("potted_pine_sapling", () -> new FlowerPotBlock(PINE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> PINE_PLANKS = HELPER.createBlock("pine_planks", () -> new Block(EnvironmentalProperties.PINE.planks()));
	public static final RegistryObject<Block> PINE_STAIRS = HELPER.createBlock("pine_stairs", () -> new StairBlock(() -> PINE_PLANKS.get().defaultBlockState(), EnvironmentalProperties.PINE.planks()));
	public static final RegistryObject<Block> PINE_SLAB = HELPER.createBlock("pine_slab", () -> new SlabBlock(EnvironmentalProperties.PINE.planks()));
	public static final RegistryObject<Block> PINE_PRESSURE_PLATE = HELPER.createBlock("pine_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.PINE.pressurePlate(), EnvironmentalProperties.PINE_BLOCK_SET));
	public static final RegistryObject<Block> PINE_BUTTON = HELPER.createBlock("pine_button", () -> new ButtonBlock(EnvironmentalProperties.PINE.button(), EnvironmentalProperties.PINE_BLOCK_SET, 30, true));
	public static final RegistryObject<Block> PINE_FENCE = HELPER.createFuelBlock("pine_fence", () -> new FenceBlock(EnvironmentalProperties.PINE.planks()), 300);
	public static final RegistryObject<Block> PINE_FENCE_GATE = HELPER.createFuelBlock("pine_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.PINE.planks(), EnvironmentalProperties.PINE_WOOD_TYPE), 300);
	public static final RegistryObject<Block> PINE_DOOR = HELPER.createBlock("pine_door", () -> new DoorBlock(EnvironmentalProperties.PINE.door(), EnvironmentalProperties.PINE_BLOCK_SET));
	public static final RegistryObject<Block> PINE_TRAPDOOR = HELPER.createBlock("pine_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.PINE.trapdoor(), EnvironmentalProperties.PINE_BLOCK_SET));
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> PINE_SIGNS = HELPER.createSignBlock("pine", EnvironmentalProperties.PINE_WOOD_TYPE, EnvironmentalProperties.PINE.sign());
	public static final Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> PINE_HANGING_SIGNS = HELPER.createHangingSignBlock("pine", EnvironmentalProperties.PINE_WOOD_TYPE, EnvironmentalProperties.PINE.hangingSign());

	public static final RegistryObject<Block> PINE_BOARDS = HELPER.createFuelBlock("pine_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.PINE.planks()), 300);
	public static final RegistryObject<Block> PINE_BOOKSHELF = HELPER.createFuelBlock("pine_bookshelf", () -> new Block(EnvironmentalProperties.PINE.bookshelf()), 300);
	public static final RegistryObject<Block> CHISELED_PINE_BOOKSHELF = HELPER.createFuelBlock("chiseled_pine_bookshelf", () -> new ChiseledPineBookShelfBlock(EnvironmentalProperties.PINE.chiseledBookshelf()), 300);
	public static final RegistryObject<Block> PINE_LADDER = HELPER.createFuelBlock("pine_ladder", () -> new LadderBlock(EnvironmentalProperties.PINE.ladder()), 300);
	public static final RegistryObject<Block> PINE_BEEHIVE = HELPER.createBlock("pine_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.PINE.beehive()));
	public static final RegistryObject<Block> PINE_LEAF_PILE = HELPER.createBlock("pine_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PINE.leafPile()));
	public static final RegistryObject<BlueprintChestBlock> PINE_CHEST = HELPER.createChestBlock("pine", EnvironmentalProperties.PINE.chest());
	public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_PINE_CHEST = HELPER.createTrappedChestBlockNamed("pine", EnvironmentalProperties.PINE.chest());

	public static final RegistryObject<Block> PINECONE = HELPER.createBlock("pinecone", () -> new PineconeBlock(EnvironmentalProperties.PINECONE.randomTicks()));
	public static final RegistryObject<Block> WAXED_PINECONE = HELPER.createBlock("waxed_pinecone", () -> new WaxedPineconeBlock(EnvironmentalProperties.PINECONE));

	// Plum //

	public static final RegistryObject<Block> STRIPPED_PLUM_LOG = HELPER.createBlock("stripped_plum_log", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.log()));
	public static final RegistryObject<Block> STRIPPED_PLUM_WOOD = HELPER.createBlock("stripped_plum_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.log()));
	public static final RegistryObject<Block> PLUM_LOG = HELPER.createBlock("plum_log", () -> new LogBlock(STRIPPED_PLUM_LOG, EnvironmentalProperties.PLUM.log()));
	public static final RegistryObject<Block> PLUM_WOOD = HELPER.createBlock("plum_wood", () -> new LogBlock(STRIPPED_PLUM_WOOD, EnvironmentalProperties.PLUM.log()));
	public static final RegistryObject<Block> PLUM_PLANKS = HELPER.createBlock("plum_planks", () -> new Block(EnvironmentalProperties.PLUM.planks()));
	public static final RegistryObject<Block> PLUM_STAIRS = HELPER.createBlock("plum_stairs", () -> new StairBlock(() -> PLUM_PLANKS.get().defaultBlockState(), EnvironmentalProperties.PLUM.planks()));
	public static final RegistryObject<Block> PLUM_SLAB = HELPER.createBlock("plum_slab", () -> new SlabBlock(EnvironmentalProperties.PLUM.planks()));
	public static final RegistryObject<Block> PLUM_PRESSURE_PLATE = HELPER.createBlock("plum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.PLUM.pressurePlate(), EnvironmentalProperties.PLUM_BLOCK_SET));
	public static final RegistryObject<Block> PLUM_BUTTON = HELPER.createBlock("plum_button", () -> new ButtonBlock(EnvironmentalProperties.PLUM.button(), EnvironmentalProperties.PLUM_BLOCK_SET, 30, true));
	public static final RegistryObject<Block> PLUM_FENCE = HELPER.createFuelBlock("plum_fence", () -> new FenceBlock(EnvironmentalProperties.PLUM.planks()), 300);
	public static final RegistryObject<Block> PLUM_FENCE_GATE = HELPER.createFuelBlock("plum_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.PLUM.planks(), EnvironmentalProperties.PLUM_WOOD_TYPE), 300);
	public static final RegistryObject<Block> PLUM_DOOR = HELPER.createBlock("plum_door", () -> new DoorBlock(EnvironmentalProperties.PLUM.door(), EnvironmentalProperties.PLUM_BLOCK_SET));
	public static final RegistryObject<Block> PLUM_TRAPDOOR = HELPER.createBlock("plum_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.PLUM.trapdoor(), EnvironmentalProperties.PLUM_BLOCK_SET));
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> PLUM_SIGNS = HELPER.createSignBlock("plum", EnvironmentalProperties.PLUM_WOOD_TYPE, EnvironmentalProperties.PLUM.sign());
	public static final Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> PLUM_HANGING_SIGNS = HELPER.createHangingSignBlock("plum", EnvironmentalProperties.PLUM_WOOD_TYPE, EnvironmentalProperties.PLUM.hangingSign());

	public static final RegistryObject<Block> PLUM_BOARDS = HELPER.createFuelBlock("plum_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.PLUM.planks()), 300);
	public static final RegistryObject<Block> PLUM_BOOKSHELF = HELPER.createFuelBlock("plum_bookshelf", () -> new Block(EnvironmentalProperties.PLUM.bookshelf()), 300);
	public static final RegistryObject<Block> CHISELED_PLUM_BOOKSHELF = HELPER.createFuelBlock("chiseled_plum_bookshelf", () -> new ChiseledPlumBookShelfBlock(EnvironmentalProperties.PLUM.chiseledBookshelf()), 300);
	public static final RegistryObject<Block> PLUM_LADDER = HELPER.createFuelBlock("plum_ladder", () -> new LadderBlock(EnvironmentalProperties.PLUM.ladder()), 300);
	public static final RegistryObject<Block> PLUM_BEEHIVE = HELPER.createBlock("plum_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.PLUM.beehive()));
	public static final RegistryObject<BlueprintChestBlock> PLUM_CHEST = HELPER.createChestBlock("plum", EnvironmentalProperties.PLUM.chest());
	public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_PLUM_CHEST = HELPER.createTrappedChestBlockNamed("plum", EnvironmentalProperties.PLUM.chest());

	public static final RegistryObject<Block> PLUM_LEAVES = HELPER.createBlock("plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves()));
	public static final RegistryObject<Block> PLUM_SAPLING = HELPER.createBlock("plum_sapling", () -> new SaplingBlock(new PlumTreeGrower(), EnvironmentalProperties.PLUM.sapling()));
	public static final RegistryObject<Block> POTTED_PLUM_SAPLING = HELPER.createBlockNoItem("potted_plum_sapling", () -> new FlowerPotBlock(PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> PLUM_LEAF_PILE = HELPER.createBlock("plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile()));

	public static final RegistryObject<Block> CHEERFUL_PLUM_LEAVES = HELPER.createBlock("cheerful_plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves()));
	public static final RegistryObject<Block> CHEERFUL_PLUM_SAPLING = HELPER.createBlock("cheerful_plum_sapling", () -> new SaplingBlock(new CheerfulPlumTreeGrower(), EnvironmentalProperties.PLUM.sapling()));
	public static final RegistryObject<Block> POTTED_CHEERFUL_PLUM_SAPLING = HELPER.createBlockNoItem("potted_cheerful_plum_sapling", () -> new FlowerPotBlock(CHEERFUL_PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> CHEERFUL_PLUM_LEAF_PILE = HELPER.createBlock("cheerful_plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile()));

	public static final RegistryObject<Block> MOODY_PLUM_LEAVES = HELPER.createBlock("moody_plum_leaves", () -> new PlumLeavesBlock(EnvironmentalProperties.PLUM.leaves().mapColor(MapColor.TERRACOTTA_PINK)));
	public static final RegistryObject<Block> MOODY_PLUM_SAPLING = HELPER.createBlock("moody_plum_sapling", () -> new SaplingBlock(new MoodyPlumTreeGrower(), EnvironmentalProperties.PLUM.sapling().mapColor(MapColor.TERRACOTTA_PINK)));
	public static final RegistryObject<Block> POTTED_MOODY_PLUM_SAPLING = HELPER.createBlockNoItem("potted_moody_plum_sapling", () -> new FlowerPotBlock(MOODY_PLUM_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> MOODY_PLUM_LEAF_PILE = HELPER.createBlock("moody_plum_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PLUM.leafPile().mapColor(MapColor.TERRACOTTA_PINK)));

	public static final RegistryObject<Block> PLUM_CRATE = HELPER.createBlock("plum_crate", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
	public static final RegistryObject<Block> CHERRY_CRATE = HELPER.createBlock("cherry_crate", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	// Wisteria //

	public static final RegistryObject<Block> STRIPPED_WISTERIA_LOG = HELPER.createBlock("stripped_wisteria_log", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.log()));
	public static final RegistryObject<Block> STRIPPED_WISTERIA_WOOD = HELPER.createBlock("stripped_wisteria_wood", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.log()));
	public static final RegistryObject<Block> WISTERIA_LOG = HELPER.createBlock("wisteria_log", () -> new LogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA.log()));
	public static final RegistryObject<Block> WISTERIA_WOOD = HELPER.createBlock("wisteria_wood", () -> new LogBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA.log()));
	public static final RegistryObject<Block> WISTERIA_PLANKS = HELPER.createBlock("wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA.planks()));
	public static final RegistryObject<Block> WISTERIA_STAIRS = HELPER.createBlock("wisteria_stairs", () -> new StairBlock(() -> WISTERIA_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WISTERIA.planks()));
	public static final RegistryObject<Block> WISTERIA_SLAB = HELPER.createBlock("wisteria_slab", () -> new SlabBlock(EnvironmentalProperties.WISTERIA.planks()));
	public static final RegistryObject<Block> WISTERIA_PRESSURE_PLATE = HELPER.createBlock("wisteria_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WISTERIA.pressurePlate(), EnvironmentalProperties.WISTERIA_BLOCK_SET));
	public static final RegistryObject<Block> WISTERIA_BUTTON = HELPER.createBlock("wisteria_button", () -> new ButtonBlock(EnvironmentalProperties.WISTERIA.button(), EnvironmentalProperties.WISTERIA_BLOCK_SET, 30, true));
	public static final RegistryObject<Block> WISTERIA_FENCE = HELPER.createFuelBlock("wisteria_fence", () -> new FenceBlock(EnvironmentalProperties.WISTERIA.planks()), 300);
	public static final RegistryObject<Block> WISTERIA_FENCE_GATE = HELPER.createFuelBlock("wisteria_fence_gate", () -> new FenceGateBlock(EnvironmentalProperties.WISTERIA.planks(), EnvironmentalProperties.WISTERIA_WOOD_TYPE), 300);
	public static final RegistryObject<Block> WISTERIA_DOOR = HELPER.createBlock("wisteria_door", () -> new DoorBlock(EnvironmentalProperties.WISTERIA.door(), EnvironmentalProperties.WISTERIA_BLOCK_SET));
	public static final RegistryObject<Block> WISTERIA_TRAPDOOR = HELPER.createBlock("wisteria_trapdoor", () -> new TrapDoorBlock(EnvironmentalProperties.WISTERIA.trapdoor(), EnvironmentalProperties.WISTERIA_BLOCK_SET));
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WISTERIA_SIGNS = HELPER.createSignBlock("wisteria", EnvironmentalProperties.WISTERIA_WOOD_TYPE, EnvironmentalProperties.WISTERIA.sign());
	public static final Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> WISTERIA_HANGING_SIGNS = HELPER.createHangingSignBlock("wisteria", EnvironmentalProperties.WISTERIA_WOOD_TYPE, EnvironmentalProperties.WISTERIA.hangingSign());

	public static final RegistryObject<Block> WISTERIA_LEAVES = HELPER.createBlock("wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves()));
	public static final RegistryObject<Block> WISTERIA_LEAF_PILE = HELPER.createBlock("wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WISTERIA.leaves()));

	public static final RegistryObject<Block> PINK_WISTERIA_LEAVES = HELPER.createBlock("pink_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA.leaves(), WisteriaColor.PINK));
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAVES = HELPER.createBlock("blue_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA.leaves(), WisteriaColor.BLUE));
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAVES = HELPER.createBlock("purple_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA.leaves(), WisteriaColor.PURPLE));
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAVES = HELPER.createBlock("white_wisteria_leaves", () -> new ColoredWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves(), WisteriaColor.WHITE));
	public static final RegistryObject<Block> PINK_HANGING_WISTERIA_LEAVES = HELPER.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA.leaves().noCollission(), WisteriaColor.PINK));
	public static final RegistryObject<Block> BLUE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA.leaves().noCollission(), WisteriaColor.BLUE));
	public static final RegistryObject<Block> PURPLE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA.leaves().noCollission(), WisteriaColor.PURPLE));
	public static final RegistryObject<Block> WHITE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA.leaves().noCollission(), WisteriaColor.WHITE));
	public static final RegistryObject<Block> PINK_WISTERIA_SAPLING = HELPER.createBlock("pink_wisteria_sapling", () -> new SaplingBlock(new WisteriaTree(WisteriaColor.PINK), EnvironmentalProperties.WISTERIA.sapling()));
	public static final RegistryObject<Block> BLUE_WISTERIA_SAPLING = HELPER.createBlock("blue_wisteria_sapling", () -> new SaplingBlock(new WisteriaTree(WisteriaColor.BLUE), EnvironmentalProperties.WISTERIA.sapling()));
	public static final RegistryObject<Block> PURPLE_WISTERIA_SAPLING = HELPER.createBlock("purple_wisteria_sapling", () -> new SaplingBlock(new WisteriaTree(WisteriaColor.PURPLE), EnvironmentalProperties.WISTERIA.sapling()));
	public static final RegistryObject<Block> WHITE_WISTERIA_SAPLING = HELPER.createBlock("white_wisteria_sapling", () -> new SaplingBlock(new WisteriaTree(WisteriaColor.WHITE), EnvironmentalProperties.WISTERIA.sapling()));
	public static final RegistryObject<Block> POTTED_PINK_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_BLUE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_PURPLE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> POTTED_WHITE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), PropertyUtil.flowerPot()));

	public static final RegistryObject<Block> WISTERIA_BOARDS = HELPER.createFuelBlock("wisteria_boards", () -> new RotatedPillarBlock(EnvironmentalProperties.WISTERIA.planks()), 300);
	public static final RegistryObject<Block> WISTERIA_BOOKSHELF = HELPER.createFuelBlock("wisteria_bookshelf", () -> new Block(EnvironmentalProperties.WISTERIA.bookshelf()), 300);
	public static final RegistryObject<Block> CHISELED_WISTERIA_BOOKSHELF = HELPER.createFuelBlock("chiseled_wisteria_bookshelf", () -> new ChiseledWisteriaBookShelfBlock(EnvironmentalProperties.WISTERIA.chiseledBookshelf()), 300);
	public static final RegistryObject<Block> WISTERIA_LADDER = HELPER.createFuelBlock("wisteria_ladder", () -> new LadderBlock(EnvironmentalProperties.WISTERIA.ladder()), 300);
	public static final RegistryObject<Block> WISTERIA_BEEHIVE = HELPER.createBlock("wisteria_beehive", () -> new BlueprintBeehiveBlock(EnvironmentalProperties.WISTERIA.beehive()));
	public static final RegistryObject<Block> PINK_WISTERIA_LEAF_PILE = HELPER.createBlock("pink_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PINK_WISTERIA.leafPile()));
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_PILE = HELPER.createBlock("blue_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.BLUE_WISTERIA.leafPile()));
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_PILE = HELPER.createBlock("purple_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.PURPLE_WISTERIA.leafPile()));
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_PILE = HELPER.createBlock("white_wisteria_leaf_pile", () -> new LeafPileBlock(EnvironmentalProperties.WISTERIA.leafPile()));
	public static final RegistryObject<BlueprintChestBlock> WISTERIA_CHEST = HELPER.createChestBlock("wisteria", EnvironmentalProperties.WISTERIA.chest());
	public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_WISTERIA_CHEST = HELPER.createTrappedChestBlockNamed("wisteria", EnvironmentalProperties.WISTERIA.chest());

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
						PINE_LOG, PINE_WOOD, STRIPPED_PINE_LOG, STRIPPED_PINE_WOOD, PINE_PLANKS)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), PINE_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						PINE_STAIRS, PINE_SLAB, PINE_FENCE, PINE_FENCE_GATE, PINE_DOOR, PINE_TRAPDOOR, PINE_PRESSURE_PLATE, PINE_BUTTON,
						PLUM_LOG, PLUM_WOOD, STRIPPED_PLUM_LOG, STRIPPED_PLUM_WOOD, PLUM_PLANKS)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), PLUM_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						PLUM_STAIRS, PLUM_SLAB, PLUM_FENCE, PLUM_FENCE_GATE, PLUM_DOOR, PLUM_TRAPDOOR, PLUM_PRESSURE_PLATE, PLUM_BUTTON,
						WISTERIA_LOG, WISTERIA_WOOD, STRIPPED_WISTERIA_LOG, STRIPPED_WISTERIA_WOOD, WISTERIA_PLANKS)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), WISTERIA_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK),
						WISTERIA_STAIRS, WISTERIA_SLAB, WISTERIA_FENCE, WISTERIA_FENCE_GATE, WISTERIA_DOOR, WISTERIA_TRAPDOOR, WISTERIA_PRESSURE_PLATE, WISTERIA_BUTTON
				)
				.addItemsBefore(of(Blocks.SANDSTONE), GRASS_THATCH, GRASS_THATCH_STAIRS, GRASS_THATCH_SLAB, CATTAIL_THATCH, CATTAIL_THATCH_STAIRS, CATTAIL_THATCH_SLAB, DUCKWEED_THATCH, DUCKWEED_THATCH_STAIRS, DUCKWEED_THATCH_SLAB)
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(of(Blocks.BAMBOO_SIGN),
						WILLOW_SIGNS.getFirst(), WILLOW_HANGING_SIGNS.getFirst(),
						PINE_SIGNS.getFirst(), PINE_HANGING_SIGNS.getFirst(),
						PLUM_SIGNS.getFirst(), PLUM_HANGING_SIGNS.getFirst(),
						WISTERIA_SIGNS.getFirst(), WISTERIA_HANGING_SIGNS.getFirst()
				)
				.addItemsBefore(of(Items.ARMOR_STAND), SLABFISH_EFFIGY)
				.tab(NATURAL_BLOCKS)
				.editor(event -> event.getEntries().remove(new ItemStack(Blocks.DIRT_PATH)))
				.addItemsAfter(of(Blocks.GRASS_BLOCK), () -> Blocks.DIRT_PATH)
				.addItemsAfter(of(Blocks.PODZOL), PODZOL_PATH)
				.addItemsAfter(of(Blocks.MYCELIUM), MYCELIUM_PATH)
				.addItemsAfter(of(Blocks.DIRT), DIRT_PATH)
				.addItemsBefore(of(Blocks.FARMLAND), BURIED_TRUFFLE)
				.addItemsAfter(of(Blocks.LILY_PAD), LARGE_LILY_PAD, GIANT_LILY_PAD, DUCKWEED)
				.addItemsAfter(of(Blocks.SUGAR_CANE), CATTAIL)
				.addItemsAfter(of(Blocks.FERN), MYCELIUM_SPROUTS, DWARF_SPRUCE)
				.addItemsAfter(of(Blocks.TALL_GRASS), GIANT_TALL_GRASS)
				.addItemsAfter(of(Blocks.GLOW_LICHEN), CUP_LICHEN)
				.addItemsBefore(of(Blocks.TORCHFLOWER), BLUEBELL, DIANTHUS, VIOLET, TASSELFLOWER, RED_LOTUS_FLOWER, WHITE_LOTUS_FLOWER, CARTWHEEL,
						YELLOW_HIBISCUS, ORANGE_HIBISCUS, RED_HIBISCUS, PINK_HIBISCUS, MAGENTA_HIBISCUS, PURPLE_HIBISCUS)
				.addItemsBefore(of(Blocks.PITCHER_PLANT), PINK_DELPHINIUM, PURPLE_DELPHINIUM, BLUE_DELPHINIUM, WHITE_DELPHINIUM, BIRD_OF_PARADISE)
				.addItemsBefore(of(Blocks.MUSHROOM_STEM), WILLOW_LOG, PINE_LOG, PLUM_LOG, WISTERIA_LOG)
				.addItemsBefore(of(Blocks.MELON), PINECONE, WAXED_PINECONE)
				.addItemsAfter(modLoaded(Blocks.HAY_BLOCK, "berry_good"), PLUM_CRATE, CHERRY_CRATE)
				.addItemsAfter(of(Blocks.HAY_BLOCK), CATTAIL_FLUFF_BLOCK, YAK_HAIR_BLOCK, YAK_HAIR_RUG)
				.addItemsAfter(of(Blocks.VINE), HANGING_WILLOW_LEAVES, PINK_HANGING_WISTERIA_LEAVES, PURPLE_HANGING_WISTERIA_LEAVES, BLUE_HANGING_WISTERIA_LEAVES, WHITE_HANGING_WISTERIA_LEAVES)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), WILLOW_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), WILLOW_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), PINE_LEAVES)
				.addItemsBefore(modLoaded(Blocks.AZALEA_LEAVES, "woodworks"), PINE_LEAF_PILE)
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
				.addItemsBefore(of(Blocks.AZALEA), WILLOW_SAPLING, PINE_SAPLING, CHEERFUL_PLUM_SAPLING, PLUM_SAPLING, MOODY_PLUM_SAPLING, PINK_WISTERIA_SAPLING, PURPLE_WISTERIA_SAPLING, BLUE_WISTERIA_SAPLING, WHITE_WISTERIA_SAPLING);

		CreativeModeTabContentsPopulator.mod("incubation_" + Environmental.MOD_ID)
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(ofID(EnvironmentalConstants.CHICKEN_EGG_CRATE), DUCK_EGG_CRATE);

		CreativeModeTabContentsPopulator.mod("woodworks_" + Environmental.MOD_ID)
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_LADDER), WILLOW_LADDER, PINE_LADDER, PLUM_LADDER, WISTERIA_LADDER)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_BEEHIVE), WILLOW_BEEHIVE, PINE_BEEHIVE, PLUM_BEEHIVE, WISTERIA_BEEHIVE)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_BOOKSHELF), WILLOW_BOOKSHELF, CHISELED_WILLOW_BOOKSHELF, PINE_BOOKSHELF, CHISELED_PINE_BOOKSHELF, PLUM_BOOKSHELF, CHISELED_PLUM_BOOKSHELF, WISTERIA_BOOKSHELF, CHISELED_WISTERIA_BOOKSHELF)
				.addItemsBefore(ofID(EnvironmentalConstants.BAMBOO_CLOSET), WILLOW_CHEST, PINE_CHEST, PLUM_CHEST, WISTERIA_CHEST)
				.tab(REDSTONE_BLOCKS)
				.addItemsBefore(ofID(EnvironmentalConstants.TRAPPED_BAMBOO_CLOSET), TRAPPED_WILLOW_CHEST, TRAPPED_PINE_CHEST, TRAPPED_PLUM_CHEST, TRAPPED_WISTERIA_CHEST);
	}

	public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
		return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, ItemLike fallback, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) ? of(ForgeRegistries.ITEMS.getValue(location)) : of(fallback)).test(stack);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(ForgeRegistries.ITEMS.getValue(location)).test(stack));
	}
}
