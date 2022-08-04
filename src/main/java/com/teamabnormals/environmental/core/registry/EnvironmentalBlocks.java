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
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.environmental.common.block.*;
import com.teamabnormals.environmental.common.block.grower.CherryTree;
import com.teamabnormals.environmental.common.block.grower.WillowTree;
import com.teamabnormals.environmental.common.block.grower.WisteriaTree;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalProperties;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlocks {

	public static final BlockSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBlockSubHelper();
	public static final DeferredRegister<Motive> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Environmental.MOD_ID);

	// Paintings //

	public static final RegistryObject<Motive> SLABFISH = PAINTINGS.register("slabfish", () -> new Motive(32, 32));
	public static final RegistryObject<Motive> SNAKE_BLOCK = PAINTINGS.register("snake_block", () -> new Motive(32, 32));
	public static final RegistryObject<Motive> SOMETHING_IN_THE_WATER = PAINTINGS.register("something_in_the_water", () -> new Motive(48, 32));

	// Crafting //

	public static final RegistryObject<Block> KILN = HELPER.createBlock("kiln", () -> new KilnBlock(Properties.copy(Blocks.SMOKER)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> SAWMILL = HELPER.createBlock("sawmill", () -> new SawmillBlock(Properties.copy(Blocks.STONECUTTER).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);

	// Building //

	public static final RegistryObject<Block> CHISELED_BRICKS = HELPER.createBlock("chiseled_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> MUD = HELPER.createBlock("mud", () -> new MudBlock(EnvironmentalProperties.MUD), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICKS = HELPER.createBlock("mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_STAIRS = HELPER.createBlock("mud_brick_stairs", () -> new StairBlock(() -> MUD_BRICKS.get().defaultBlockState(), EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_SLAB = HELPER.createBlock("mud_brick_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_WALL = HELPER.createBlock("mud_brick_wall", () -> new WallBlock(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "mud_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_MUD_BRICKS = HELPER.createBlock("chiseled_mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SLABFISH_EFFIGY = HELPER.createBlock("slabfish_effigy", () -> new SlabfishEffigyBlock(EnvironmentalProperties.FLOWER_POT), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> ICE_BRICKS = HELPER.createBlock("ice_bricks", () -> new Block(EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ICE_BRICK_STAIRS = HELPER.createBlock("ice_brick_stairs", () -> new StairBlock(() -> ICE_BRICKS.get().defaultBlockState(), EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ICE_BRICK_SLAB = HELPER.createBlock("ice_brick_slab", () -> new SlabBlock(EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ICE_BRICK_WALL = HELPER.createBlock("ice_brick_wall", () -> new WallBlock(EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ICE_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "ice_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_ICE_BRICKS = HELPER.createBlock("chiseled_ice_bricks", () -> new Block(EnvironmentalProperties.ICE_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> ICE_LANTERN = HELPER.createBlock("ice_lantern", () -> new LanternBlock(EnvironmentalProperties.ICE_LANTERN), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ICE_CHAIN = HELPER.createBlock("ice_chain", () -> new ChainBlock(EnvironmentalProperties.ICE_CHAIN), CreativeModeTab.TAB_DECORATIONS);

	// Crops //

	public static final RegistryObject<Block> CATTAIL_SPROUTS = HELPER.createBlockNoItem("cattail_sprouts", () -> new CattailSproutsBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL = HELPER.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TALL_CATTAIL = HELPER.createBlock("tall_cattail", () -> new DoubleCattailBlock(EnvironmentalProperties.CATTAIL), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_CATTAIL = HELPER.createBlockNoItem("potted_cattail", () -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> CATTAIL_SEED_SACK = HELPER.createCompatBlock("quark", "cattail_seed_sack", () -> new Block(Block.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_DECORATIONS);

	// Foliage //

	public static final RegistryObject<Block> DUCKWEED = HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));
	public static final RegistryObject<Block> LARGE_LILY_PAD = HELPER.createBlockNoItem("large_lily_pad", () -> new LargeLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));
	public static final RegistryObject<Block> GIANT_LILY_PAD = HELPER.createBlockNoItem("giant_lily_pad", () -> new GiantLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));

	public static final RegistryObject<Block> MYCELIUM_SPROUTS = HELPER.createBlock("mycelium_sprouts", () -> new MyceliumSproutsBlock(EnvironmentalProperties.MYCELIUM_SPROUTS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> GIANT_TALL_GRASS = HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.copy(Blocks.TALL_GRASS)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TALL_DEAD_BUSH = HELPER.createBlock("tall_dead_bush", () -> new TallDeadBushBlock(EnvironmentalProperties.TALL_DEAD_BUSH), CreativeModeTab.TAB_DECORATIONS);

	// Misc //

	public static final RegistryObject<Block> BURIED_TRUFFLE = HELPER.createBlock("buried_truffle", () -> new Block(EnvironmentalProperties.BURIED_TRUFFLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> TRUFFLE_CRATE = HELPER.createCompatBlock("quark", "truffle_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_BROWN).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> DUCK_EGG_CRATE = HELPER.createCompatBlock("incubation", "duck_egg_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);

	// Decorations //

	public static final RegistryObject<Block> PODZOL_PATH = HELPER.createBlock("podzol_path", () -> new DirtPathBlock(EnvironmentalProperties.PODZOL_PATH), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MYCELIUM_PATH = HELPER.createBlock("mycelium_path", () -> new DirtPathBlock(EnvironmentalProperties.MYCELIUM_PATH), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YAK_HAIR_BLOCK = HELPER.createFuelBlock("yak_hair_block", () -> new ThatchBlock(EnvironmentalProperties.YAK_HAIR_BLOCK), 225, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YAK_HAIR_RUG = HELPER.createFuelBlock("yak_hair_rug", () -> new RugBlock(EnvironmentalProperties.YAK_HAIR_RUG), 75, CreativeModeTab.TAB_DECORATIONS);

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

	public static final RegistryObject<Block> CARTWHEEL = HELPER.createBlock("cartwheel", () -> new CartwheelBlock(() -> MobEffects.LEVITATION, 3, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUEBELL = HELPER.createBlock("bluebell", () -> new BlueprintFlowerBlock(() -> MobEffects.HUNGER, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> VIOLET = HELPER.createBlock("violet", () -> new BlueprintFlowerBlock(() -> MobEffects.INVISIBILITY, 9, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> DIANTHUS = HELPER.createBlock("dianthus", () -> new BlueprintFlowerBlock(() -> MobEffects.DAMAGE_BOOST, 7, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> RED_LOTUS_FLOWER = HELPER.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_LOTUS_FLOWER = HELPER.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM::get, () -> MobEffects.SLOW_FALLING, 5, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_HIBISCUS = HELPER.createBlock("yellow_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_HIBISCUS = HELPER.createBlock("orange_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> RED_HIBISCUS = HELPER.createBlock("red_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_HIBISCUS = HELPER.createBlock("pink_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAGENTA_HIBISCUS = HELPER.createBlock("magenta_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_HIBISCUS = HELPER.createBlock("purple_hibiscus", () -> new BlueprintFlowerBlock(() -> MobEffects.GLOWING, 8, EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> POTTED_CARTWHEEL = HELPER.createBlockNoItem("potted_cartwheel", () -> new PottedCartwheelBlock(CARTWHEEL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BLUEBELL = HELPER.createBlockNoItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_VIOLET = HELPER.createBlockNoItem("potted_violet", () -> new FlowerPotBlock(VIOLET.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_DIANTHUS = HELPER.createBlockNoItem("potted_dianthus", () -> new FlowerPotBlock(DIANTHUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_RED_LOTUS_FLOWER = HELPER.createBlockNoItem("potted_red_lotus_flower", () -> new FlowerPotBlock(RED_LOTUS_FLOWER.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_WHITE_LOTUS_FLOWER = HELPER.createBlockNoItem("potted_white_lotus_flower", () -> new FlowerPotBlock(WHITE_LOTUS_FLOWER.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_YELLOW_HIBISCUS = HELPER.createBlockNoItem("potted_yellow_hibiscus", () -> new FlowerPotBlock(YELLOW_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_ORANGE_HIBISCUS = HELPER.createBlockNoItem("potted_orange_hibiscus", () -> new FlowerPotBlock(ORANGE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_RED_HIBISCUS = HELPER.createBlockNoItem("potted_red_hibiscus", () -> new FlowerPotBlock(RED_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PINK_HIBISCUS = HELPER.createBlockNoItem("potted_pink_hibiscus", () -> new FlowerPotBlock(PINK_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_MAGENTA_HIBISCUS = HELPER.createBlockNoItem("potted_magenta_hibiscus", () -> new FlowerPotBlock(MAGENTA_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PURPLE_HIBISCUS = HELPER.createBlockNoItem("potted_purple_hibiscus", () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));

	// Tall Flowers //

	public static final RegistryObject<Block> PINK_DELPHINIUM = HELPER.createBlock("pink_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_DELPHINIUM = HELPER.createBlock("blue_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_DELPHINIUM = HELPER.createBlock("purple_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_DELPHINIUM = HELPER.createBlock("white_delphinium", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BIRD_OF_PARADISE = HELPER.createBlock("bird_of_paradise", () -> new BlueprintTallFlowerBlock(EnvironmentalProperties.FLOWER), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> POTTED_PINK_DELPHINIUM = HELPER.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BLUE_DELPHINIUM = HELPER.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PURPLE_DELPHINIUM = HELPER.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_WHITE_DELPHINIUM = HELPER.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BIRD_OF_PARADISE = HELPER.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), EnvironmentalProperties.FLOWER_POT));

	// Willow //

	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG = HELPER.createBlock("stripped_willow_log", () -> new StrippedLogBlock(EnvironmentalProperties.WILLOW_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD = HELPER.createBlock("stripped_willow_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.WILLOW_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LOG = HELPER.createBlock("willow_log", () -> new LogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.WILLOW_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_WOOD = HELPER.createBlock("willow_wood", () -> new WoodBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.WILLOW_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_PLANKS = HELPER.createBlock("willow_planks", () -> new PlanksBlock(EnvironmentalProperties.WILLOW_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_SLAB = HELPER.createBlock("willow_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_STAIRS = HELPER.createBlock("willow_stairs", () -> new WoodStairBlock(WILLOW_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WILLOW_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE = HELPER.createFuelBlock("willow_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WILLOW_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE = HELPER.createFuelBlock("willow_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WILLOW_PLANKS), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WILLOW_PRESSURE_PLATE), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_DOOR = HELPER.createBlock("willow_door", () -> new WoodDoorBlock(EnvironmentalProperties.WILLOW_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_TRAPDOOR = HELPER.createBlock("willow_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WILLOW_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WILLOW_BUTTON = HELPER.createBlock("willow_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.WILLOW_BUTTON), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WILLOW_SIGNS = HELPER.createSignBlock("willow", MaterialColor.COLOR_GREEN);

	public static final RegistryObject<Block> WILLOW_LEAVES = HELPER.createBlock("willow_leaves", () -> new BlueprintLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_GREEN)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingWillowLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_GREEN)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_SAPLING = HELPER.createBlock("willow_sapling", () -> new BlueprintSaplingBlock(new WillowTree(), EnvironmentalProperties.WILLOW_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(EnvironmentalBlocks.WILLOW_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

	public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS = HELPER.createCompatBlock("quark", "vertical_willow_planks", () -> new Block(EnvironmentalProperties.WILLOW_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LADDER = HELPER.createCompatFuelBlock("quark", "willow_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.WILLOW_LADDER), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "willow_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WILLOW_BOOKSHELF), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LEAF_CARPET = HELPER.createCompatBlock("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.COLOR_GREEN)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_POST = HELPER.createCompatFuelBlock("quark", "stripped_willow_post", () -> new WoodPostBlock(EnvironmentalProperties.WILLOW_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_POST = HELPER.createCompatFuelBlock("quark", "willow_post", () -> new WoodPostBlock(STRIPPED_WILLOW_POST, EnvironmentalProperties.WILLOW_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_HEDGE = HELPER.createCompatFuelBlock("quark", "willow_hedge", () -> new HedgeBlock(EnvironmentalProperties.WILLOW_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "willow_beehive", () -> new BlueprintBeehiveBlock(Properties.copy(Blocks.BEEHIVE)), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> WILLOW_CHESTS = HELPER.createCompatChestBlocks("quark", "willow", MaterialColor.COLOR_GREEN);

	// Cherry //

	public static final RegistryObject<Block> STRIPPED_CHERRY_LOG = HELPER.createBlock("stripped_cherry_log", () -> new StrippedLogBlock(EnvironmentalProperties.CHERRY_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_CHERRY_WOOD = HELPER.createBlock("stripped_cherry_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.CHERRY_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_LOG = HELPER.createBlock("cherry_log", () -> new LogBlock(STRIPPED_CHERRY_LOG, EnvironmentalProperties.CHERRY_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_WOOD = HELPER.createBlock("cherry_wood", () -> new WoodBlock(STRIPPED_CHERRY_WOOD, EnvironmentalProperties.CHERRY_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_PLANKS = HELPER.createBlock("cherry_planks", () -> new PlanksBlock(EnvironmentalProperties.CHERRY_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_SLAB = HELPER.createBlock("cherry_slab", () -> new WoodSlabBlock(EnvironmentalProperties.CHERRY_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_STAIRS = HELPER.createBlock("cherry_stairs", () -> new WoodStairBlock(CHERRY_PLANKS.get().defaultBlockState(), EnvironmentalProperties.CHERRY_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_FENCE = HELPER.createFuelBlock("cherry_fence", () -> new WoodFenceBlock(EnvironmentalProperties.CHERRY_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_FENCE_GATE = HELPER.createFuelBlock("cherry_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.CHERRY_PLANKS), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_PRESSURE_PLATE = HELPER.createBlock("cherry_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.CHERRY_PRESSURE_PLATE), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_DOOR = HELPER.createBlock("cherry_door", () -> new WoodDoorBlock(EnvironmentalProperties.CHERRY_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_TRAPDOOR = HELPER.createBlock("cherry_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.CHERRY_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> CHERRY_BUTTON = HELPER.createBlock("cherry_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.CHERRY_BUTTON), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> CHERRY_SIGNS = HELPER.createSignBlock("cherry", MaterialColor.COLOR_RED);

	public static final RegistryObject<Block> CHERRY_LEAVES = HELPER.createBlock("cherry_leaves", () -> new CherryLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_SAPLING = HELPER.createBlock("cherry_sapling", () -> new BlueprintSaplingBlock(new CherryTree(), EnvironmentalProperties.CHERRY_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_CHERRY_SAPLING = HELPER.createBlockNoItem("potted_cherry_sapling", () -> new FlowerPotBlock(EnvironmentalBlocks.CHERRY_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

	public static final RegistryObject<Block> VERTICAL_CHERRY_PLANKS = HELPER.createCompatBlock("quark", "vertical_cherry_planks", () -> new Block(EnvironmentalProperties.CHERRY_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "cherry_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.CHERRY_PLANKS), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_LADDER = HELPER.createCompatFuelBlock("quark", "cherry_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.CHERRY_LADDER), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "cherry_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.CHERRY_BOOKSHELF), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_LEAF_CARPET = HELPER.createBlock("cherry_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> STRIPPED_CHERRY_POST = HELPER.createCompatFuelBlock("quark", "stripped_cherry_post", () -> new WoodPostBlock(EnvironmentalProperties.CHERRY_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_POST = HELPER.createCompatFuelBlock("quark", "cherry_post", () -> new WoodPostBlock(STRIPPED_CHERRY_POST, EnvironmentalProperties.CHERRY_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHERRY_HEDGE = HELPER.createCompatFuelBlock("quark", "cherry_hedge", () -> new HedgeBlock(EnvironmentalProperties.CHERRY_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CHERRY_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "cherry_beehive", () -> new BlueprintBeehiveBlock(Properties.copy(Blocks.BEEHIVE)), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> CHERRY_CHESTS = HELPER.createCompatChestBlocks("quark", "cherry", MaterialColor.COLOR_RED);

	public static final RegistryObject<Block> CHERRY_CRATE = HELPER.createCompatBlock("quark", "cherry_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_RED).strength(1.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);

	// Wisteria //

	public static final RegistryObject<Block> STRIPPED_WISTERIA_LOG = HELPER.createBlock("stripped_wisteria_log", () -> new StrippedLogBlock(EnvironmentalProperties.STRIPPED_WISTERIA_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_LOG = HELPER.createBlock("wisteria_log", () -> new LogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WISTERIA_WOOD = HELPER.createBlock("stripped_wisteria_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.STRIPPED_WISTERIA_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_WOOD = HELPER.createBlock("wisteria_wood", () -> new WoodBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_PLANKS = HELPER.createBlock("wisteria_planks", () -> new PlanksBlock(EnvironmentalProperties.WISTERIA_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_STAIRS = HELPER.createBlock("wisteria_stairs", () -> new WoodStairBlock(WISTERIA_PLANKS.get().defaultBlockState(), EnvironmentalProperties.WISTERIA_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_SLAB = HELPER.createBlock("wisteria_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_FENCE = HELPER.createFuelBlock("wisteria_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_FENCE_GATE = HELPER.createFuelBlock("wisteria_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_TRAPDOOR = HELPER.createBlock("wisteria_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WISTERIA_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_DOOR = HELPER.createBlock("wisteria_door", () -> new WoodDoorBlock(EnvironmentalProperties.WISTERIA_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_PRESSURE_PLATE = HELPER.createBlock("wisteria_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).noCollission().strength(0.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> WISTERIA_BUTTON = HELPER.createBlock("wisteria_button", () -> new BlueprintWoodButtonBlock(EnvironmentalProperties.WISTERIA_BUTTON), CreativeModeTab.TAB_REDSTONE);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> WISTERIA_SIGNS = HELPER.createSignBlock("wisteria", MaterialColor.TERRACOTTA_WHITE);

	public static final RegistryObject<Block> PINK_WISTERIA_LEAVES = HELPER.createBlock("pink_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAVES = HELPER.createBlock("blue_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_BLUE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAVES = HELPER.createBlock("purple_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_PURPLE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAVES = HELPER.createBlock("white_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.SNOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_HANGING_WISTERIA_LEAVES = HELPER.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_BLUE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.COLOR_PURPLE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.createLeaves(MaterialColor.SNOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_WISTERIA_SAPLING = HELPER.createBlock("pink_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.PINK), EnvironmentalProperties.WISTERIA_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_SAPLING = HELPER.createBlock("blue_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.BLUE), EnvironmentalProperties.WISTERIA_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_SAPLING = HELPER.createBlock("purple_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.PURPLE), EnvironmentalProperties.WISTERIA_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_SAPLING = HELPER.createBlock("white_wisteria_sapling", () -> new BlueprintSaplingBlock(new WisteriaTree(WisteriaColor.WHITE), EnvironmentalProperties.WISTERIA_SAPLING), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_PINK_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BLUE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PURPLE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_WHITE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

	public static final RegistryObject<Block> WISTERIA_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "wisteria_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WISTERIA_BOOKSHELF), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_LADDER = HELPER.createCompatFuelBlock("quark", "wisteria_ladder", () -> new BlueprintLadderBlock(EnvironmentalProperties.WISTERIA_LADDER), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> VERTICAL_WISTERIA_PLANKS = HELPER.createCompatBlock("quark", "vertical_wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA_PLANKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "wisteria_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "pink_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "blue_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.COLOR_BLUE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "purple_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.COLOR_PURPLE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "white_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.createLeafCarpet(MaterialColor.SNOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> STRIPPED_WISTERIA_POST = HELPER.createCompatFuelBlock("quark", "stripped_wisteria_post", () -> new WoodPostBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WISTERIA_POST = HELPER.createCompatFuelBlock("quark", "wisteria_post", () -> new WoodPostBlock(STRIPPED_WISTERIA_POST, EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "pink_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "blue_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "purple_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_WISTERIA_HEDGE = HELPER.createCompatFuelBlock("quark", "white_wisteria_hedge", () -> new HedgeBlock(EnvironmentalProperties.WISTERIA_PLANKS), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "wisteria_beehive", () -> new BlueprintBeehiveBlock(Properties.copy(Blocks.BEEHIVE)), CreativeModeTab.TAB_DECORATIONS);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> WISTERIA_CHESTS = HELPER.createCompatChestBlocks("quark", "wisteria", MaterialColor.TERRACOTTA_WHITE);

	// Terracotta Bricks //

	public static final RegistryObject<Block> TERRACOTTA_BRICKS = HELPER.createBlock("terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICKS = HELPER.createBlock("white_terracotta_bricks", () -> new Block(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICKS = HELPER.createBlock("orange_terracotta_bricks", () -> new Block(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICKS = HELPER.createBlock("magenta_terracotta_bricks", () -> new Block(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICKS = HELPER.createBlock("light_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICKS = HELPER.createBlock("yellow_terracotta_bricks", () -> new Block(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICKS = HELPER.createBlock("lime_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICKS = HELPER.createBlock("pink_terracotta_bricks", () -> new Block(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICKS = HELPER.createBlock("gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICKS = HELPER.createBlock("light_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICKS = HELPER.createBlock("cyan_terracotta_bricks", () -> new Block(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICKS = HELPER.createBlock("purple_terracotta_bricks", () -> new Block(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICKS = HELPER.createBlock("blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICKS = HELPER.createBlock("brown_terracotta_bricks", () -> new Block(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICKS = HELPER.createBlock("green_terracotta_bricks", () -> new Block(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICKS = HELPER.createBlock("red_terracotta_bricks", () -> new Block(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICKS = HELPER.createBlock("black_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("terracotta_brick_stairs", () -> new StairBlock(() -> TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("white_terracotta_brick_stairs", () -> new StairBlock(() -> WHITE_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("orange_terracotta_brick_stairs", () -> new StairBlock(() -> ORANGE_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("magenta_terracotta_brick_stairs", () -> new StairBlock(() -> MAGENTA_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("yellow_terracotta_brick_stairs", () -> new StairBlock(() -> YELLOW_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("light_blue_terracotta_brick_stairs", () -> new StairBlock(() -> LIGHT_BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("lime_terracotta_brick_stairs", () -> new StairBlock(() -> LIME_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("pink_terracotta_brick_stairs", () -> new StairBlock(() -> PINK_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("gray_terracotta_brick_stairs", () -> new StairBlock(() -> GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("light_gray_terracotta_brick_stairs", () -> new StairBlock(() -> LIGHT_GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("cyan_terracotta_brick_stairs", () -> new StairBlock(() -> CYAN_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("purple_terracotta_brick_stairs", () -> new StairBlock(() -> PURPLE_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("blue_terracotta_brick_stairs", () -> new StairBlock(() -> BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("brown_terracotta_brick_stairs", () -> new StairBlock(() -> BROWN_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("green_terracotta_brick_stairs", () -> new StairBlock(() -> GREEN_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("red_terracotta_brick_stairs", () -> new StairBlock(() -> RED_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_STAIRS = HELPER.createBlock("black_terracotta_brick_stairs", () -> new StairBlock(() -> BLACK_TERRACOTTA_BRICKS.get().defaultBlockState(), EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> TERRACOTTA_BRICK_SLAB = HELPER.createBlock("terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("white_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("orange_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("magenta_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("light_blue_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("yellow_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("lime_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("pink_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("gray_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("light_gray_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("cyan_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("purple_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("blue_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("brown_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("green_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("red_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_SLAB = HELPER.createBlock("black_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> TERRACOTTA_BRICK_WALL = HELPER.createBlock("terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("white_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("orange_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_WALL = HELPER.createBlock("magenta_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("light_blue_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_WALL = HELPER.createBlock("yellow_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_WALL = HELPER.createBlock("lime_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_WALL = HELPER.createBlock("pink_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_WALL = HELPER.createBlock("gray_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_WALL = HELPER.createBlock("light_gray_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_WALL = HELPER.createBlock("cyan_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("purple_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("blue_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_WALL = HELPER.createBlock("brown_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_WALL = HELPER.createBlock("green_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_WALL = HELPER.createBlock("red_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_WALL = HELPER.createBlock("black_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "white_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "orange_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "magenta_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "light_blue_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "yellow_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "lime_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "pink_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "gray_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "light_gray_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "cyan_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "purple_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "blue_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "brown_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "green_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "red_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "black_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> CHISELED_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_WHITE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_white_terracotta_bricks", () -> new Block(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_ORANGE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_orange_terracotta_bricks", () -> new Block(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_MAGENTA_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_magenta_terracotta_bricks", () -> new Block(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_LIGHT_BLUE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_light_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_YELLOW_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_yellow_terracotta_bricks", () -> new Block(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_LIME_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_lime_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_PINK_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_pink_terracotta_bricks", () -> new Block(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_GRAY_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_LIGHT_GRAY_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_light_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_CYAN_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_cyan_terracotta_bricks", () -> new Block(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_PURPLE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_purple_terracotta_bricks", () -> new Block(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_BLUE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_BROWN_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_brown_terracotta_bricks", () -> new Block(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_GREEN_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_green_terracotta_bricks", () -> new Block(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_RED_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_red_terracotta_bricks", () -> new Block(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_BLACK_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_black_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
}
