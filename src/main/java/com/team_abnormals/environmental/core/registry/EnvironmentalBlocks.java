package com.team_abnormals.environmental.core.registry;

import com.mojang.datafixers.util.Pair;
import com.team_abnormals.environmental.common.block.CartwheelBlock;
import com.team_abnormals.environmental.common.block.CattailBlock;
import com.team_abnormals.environmental.common.block.CattailSproutsBlock;
import com.team_abnormals.environmental.common.block.DoubleCattailBlock;
import com.team_abnormals.environmental.common.block.DoubleRiceBlock;
import com.team_abnormals.environmental.common.block.DuckweedBlock;
import com.team_abnormals.environmental.common.block.HangingWillowLeavesBlock;
import com.team_abnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.team_abnormals.environmental.common.block.KilnBlock;
import com.team_abnormals.environmental.common.block.MudVaseBlock;
import com.team_abnormals.environmental.common.block.PottedCartwheelBlock;
import com.team_abnormals.environmental.common.block.RiceBlock;
import com.team_abnormals.environmental.common.block.WisteriaLeavesBlock;
import com.team_abnormals.environmental.common.block.fluid.MudFluidBlock;
import com.team_abnormals.environmental.common.world.gen.feature.trees.WillowTree;
import com.team_abnormals.environmental.common.world.gen.feature.trees.WisteriaTree;
import com.team_abnormals.environmental.common.world.gen.util.WisteriaColor;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.other.EnvironmentalProperties;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsBeehiveBlock;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsLadderBlock;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsTallFlowerBlock;
import com.teamabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.teamabnormals.abnormals_core.common.blocks.LeafCarpetBlock;
import com.teamabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.chest.AbnormalsChestBlock;
import com.teamabnormals.abnormals_core.common.blocks.chest.AbnormalsTrappedChestBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchVerticalSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLeavesBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLogBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsSaplingBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsWoodButtonBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.PlanksBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedLogBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedWoodBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodDoorBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceGateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodPressurePlateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodTrapDoorBlock;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlocks {
    
	public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;
	public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Environmental.MODID);

	// Mud //
	
	public static final RegistryObject<Block> MUD_BRICKS 				= HELPER.createBlock("mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_STAIRS 			= HELPER.createBlock("mud_brick_stairs", () -> new AbnormalsStairsBlock(MUD_BRICKS.get().getDefaultState(), EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_SLAB 			= HELPER.createBlock("mud_brick_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_WALL 			= HELPER.createBlock("mud_brick_wall", () -> new WallBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB	= HELPER.createCompatBlock("quark", "mud_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_VASE	 				= HELPER.createBlock("mud_vase", () -> new MudVaseBlock(EnvironmentalProperties.FLOWER_POT), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MUD 						= HELPER.createBlockNoItem("mud", () -> new MudFluidBlock(() -> {return EnvironmentalFluids.FLOWING_MUD.get();}, Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static final RegistryObject<Block> KILN		 				= HELPER.createBlock("kiln", () -> new KilnBlock(Properties.from(Blocks.SMOKER)), ItemGroup.DECORATIONS);

	// Willow //
	
	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG 	= HELPER.createBlock("stripped_willow_log", () -> new StrippedLogBlock(EnvironmentalProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD 	= HELPER.createBlock("stripped_willow_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LOG			= HELPER.createBlock("willow_log", () -> new AbnormalsLogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_WOOD 			= HELPER.createBlock("willow_wood", () -> new WoodBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_PLANKS 		= HELPER.createBlock("willow_planks", () -> new PlanksBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_SLAB 			= HELPER.createBlock("willow_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_STAIRS 		= HELPER.createBlock("willow_stairs", () -> new WoodStairsBlock(WILLOW_PLANKS.get().getDefaultState(), EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE 			= HELPER.createBlock("willow_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE 	= HELPER.createBlock("willow_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WILLOW_PRESSURE_PLATE), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_DOOR 			= HELPER.createBlock("willow_door", () -> new WoodDoorBlock(EnvironmentalProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_TRAPDOOR 		= HELPER.createBlock("willow_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_BUTTON 		= HELPER.createBlock("willow_button", () -> new AbnormalsWoodButtonBlock(EnvironmentalProperties.WILLOW_BUTTON), ItemGroup.REDSTONE);
	public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> SIGNS = HELPER.createSignBlock("willow", MaterialColor.GREEN);
	
	public static final RegistryObject<Block> WILLOW_LEAVES 		= HELPER.createBlock("willow_leaves", () -> new AbnormalsLeavesBlock(EnvironmentalProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingWillowLeavesBlock(EnvironmentalProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_SAPLING 		= HELPER.createBlock("willow_sapling", () -> new AbnormalsSaplingBlock(new WillowTree(), EnvironmentalProperties.SAPLING), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(EnvironmentalBlocks.WILLOW_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	
	public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS= HELPER.createCompatBlock("quark", "vertical_willow_planks", () -> new Block(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB 	= HELPER.createCompatBlock("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LADDER 		= HELPER.createCompatBlock("quark", "willow_ladder", () -> new AbnormalsLadderBlock(EnvironmentalProperties.LADDER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF 		= HELPER.createCompatBlock("quark", "willow_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.BOOKSHELF), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.LEAVES.notSolid()), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BEEHIVE 		= HELPER.createCompatBlock("buzzier_bees", "willow_beehive", () -> new AbnormalsBeehiveBlock(Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
	public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> WILLOW_CHESTS = HELPER.createCompatChestBlocks("willow", MaterialColor.GREEN);

	// Wisteria //
	
	public static final RegistryObject<Block> STRIPPED_WISTERIA_LOG 		= HELPER.createBlock("stripped_wisteria_log", () -> new StrippedLogBlock(EnvironmentalProperties.WISTERIA_LOG(true)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_LOG 					= HELPER.createBlock("wisteria_log", () -> new AbnormalsLogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA_LOG(false)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_WISTERIA_WOOD 		= HELPER.createBlock("stripped_wisteria_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.WISTERIA_LOG(true)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_WOOD 				= HELPER.createBlock("wisteria_wood", () -> new WoodBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA_LOG(false)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_PLANKS 				= HELPER.createBlock("wisteria_planks", () -> new PlanksBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_STAIRS				= HELPER.createBlock("wisteria_stairs", () -> new WoodStairsBlock(WISTERIA_PLANKS.get().getDefaultState(), EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_SLAB 				= HELPER.createBlock("wisteria_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_FENCE 				= HELPER.createBlock("wisteria_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WISTERIA_FENCE_GATE 			= HELPER.createBlock("wisteria_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_TRAPDOOR 			= HELPER.createBlock("wisteria_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WISTERIA_DOORS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_DOOR 				= HELPER.createBlock("wisteria_door", () -> new WoodDoorBlock(EnvironmentalProperties.WISTERIA_DOORS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_PRESSURE_PLATE 		= HELPER.createBlock("wisteria_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_BUTTON 				= HELPER.createBlock("wisteria_button", () -> new AbnormalsWoodButtonBlock(EnvironmentalProperties.WOOD_BUTTON), ItemGroup.REDSTONE);
    public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> WISTERIA_SIGN = HELPER.createSignBlock("wisteria", MaterialColor.WHITE_TERRACOTTA);

    public static final RegistryObject<Block> PINK_WISTERIA_LEAVES 			= HELPER.createBlock("pink_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PINK)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_LEAVES 			= HELPER.createBlock("blue_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.BLUE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_LEAVES 		= HELPER.createBlock("purple_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PURPLE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_LEAVES 		= HELPER.createBlock("white_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.SNOW)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_HANGING_WISTERIA_LEAVES	= HELPER.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PINK)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_HANGING_WISTERIA_LEAVES 	= HELPER.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.BLUE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_HANGING_WISTERIA_LEAVES= HELPER.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PURPLE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.SNOW)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_WISTERIA_SAPLING 		= HELPER.createBlock("pink_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.PINK), EnvironmentalProperties.SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_SAPLING 		= HELPER.createBlock("blue_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.BLUE), EnvironmentalProperties.SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_SAPLING 		= HELPER.createBlock("purple_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.PURPLE), EnvironmentalProperties.SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_SAPLING 		= HELPER.createBlock("white_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.WHITE), EnvironmentalProperties.SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> POTTED_PINK_WISTERIA_SAPLING	= HELPER.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BLUE_WISTERIA_SAPLING 	= HELPER.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PURPLE_WISTERIA_SAPLING= HELPER.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_WHITE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
	
    public static final RegistryObject<Block> WISTERIA_BOOKSHELF 			= HELPER.createCompatBlock("quark", "wisteria_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.BOOKSHELF), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WISTERIA_LADDER 				= HELPER.createCompatBlock("quark", "wisteria_ladder", () -> new AbnormalsLadderBlock(EnvironmentalProperties.LADDER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> VERTICAL_WISTERIA_PLANKS 		= HELPER.createCompatBlock("quark", "vertical_wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_VERTICAL_SLAB 		= HELPER.createCompatBlock("quark", "wisteria_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_WISTERIA_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "pink_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PINK)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "blue_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.BLUE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "purple_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.PURPLE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "white_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WISTERIA_LEAVES(MaterialColor.SNOW)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WISTERIA_BEEHIVE		 		= HELPER.createCompatBlock("buzzier_bees", "wisteria_beehive", () -> new AbnormalsBeehiveBlock(Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> WISTERIA_CHESTS = HELPER.createCompatChestBlocks("wisteria", MaterialColor.WHITE_TERRACOTTA);

    // Delphiniums //
    
    public static final RegistryObject<Block> PINK_DELPHINIUM 	= HELPER.createBlock("pink_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_DELPHINIUM 	= HELPER.createBlock("blue_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_DELPHINIUM = HELPER.createBlock("purple_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_DELPHINIUM 	= HELPER.createBlock("white_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    
    public static final RegistryObject<Block> POTTED_PINK_DELPHINIUM 	= HELPER.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BLUE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PURPLE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_WHITE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    
    // Flowers //
    
    public static final RegistryObject<Block> CARTWHEEL 		= HELPER.createBlock("cartwheel", () -> new CartwheelBlock(Effects.SPEED, 11, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> BLUEBELL 			= HELPER.createBlock("bluebell", () -> new AbnormalsFlowerBlock(Effects.WATER_BREATHING, 6, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> VIOLET 			= HELPER.createBlock("violet", () -> new AbnormalsFlowerBlock(Effects.INVISIBILITY, 6, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> DIANTHUS 			= HELPER.createBlock("dianthus", () -> new AbnormalsFlowerBlock(Effects.STRENGTH, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> COLUMBINE 		= HELPER.createBlock("columbine", () -> new AbnormalsFlowerBlock(Effects.MINING_FATIGUE, 6, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_HIBISCUS	= HELPER.createBlock("yellow_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_HIBISCUS 	= HELPER.createBlock("orange_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> RED_HIBISCUS 		= HELPER.createBlock("red_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> PINK_HIBISCUS 	= HELPER.createBlock("pink_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAGENTA_HIBISCUS 	= HELPER.createBlock("magenta_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> PURPLE_HIBISCUS 	= HELPER.createBlock("purple_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> BIRD_OF_PARADISE 	= HELPER.createBlock("bird_of_paradise", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    
	public static final RegistryObject<Block> POTTED_CARTWHEEL 	  		= HELPER.createBlockNoItem("potted_cartwheel", () -> new PottedCartwheelBlock(CARTWHEEL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BLUEBELL     		= HELPER.createBlockNoItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_VIOLET 	  		= HELPER.createBlockNoItem("potted_violet", () -> new FlowerPotBlock(VIOLET.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_DIANTHUS 	  		= HELPER.createBlockNoItem("potted_dianthus", () -> new FlowerPotBlock(DIANTHUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_COLUMBINE 	  		= HELPER.createBlockNoItem("potted_columbine", () -> new FlowerPotBlock(COLUMBINE.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_YELLOW_HIBISCUS	= HELPER.createBlockNoItem("potted_yellow_hibiscus", () -> new FlowerPotBlock(YELLOW_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_ORANGE_HIBISCUS	= HELPER.createBlockNoItem("potted_orange_hibiscus", () -> new FlowerPotBlock(ORANGE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_RED_HIBISCUS 		= HELPER.createBlockNoItem("potted_red_hibiscus", () -> new FlowerPotBlock(RED_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PINK_HIBISCUS  	= HELPER.createBlockNoItem("potted_pink_hibiscus", () -> new FlowerPotBlock(PINK_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_MAGENTA_HIBISCUS	= HELPER.createBlockNoItem("potted_magenta_hibiscus", () -> new FlowerPotBlock(MAGENTA_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_PURPLE_HIBISCUS	= HELPER.createBlockNoItem("potted_purple_hibiscus", () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> POTTED_BIRD_OF_PARADISE	= HELPER.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), EnvironmentalProperties.FLOWER_POT));
	
    // Cattail //
    
	public static final RegistryObject<Block> CATTAIL_SPROUTS 		= HELPER.createBlockNoItem("cattail_sprouts", () -> new CattailSproutsBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL 				= HELPER.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TALL_CATTAIL 			= HELPER.createBlock("tall_cattail", () -> new DoubleCattailBlock(EnvironmentalProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_CATTAIL		= HELPER.createBlockNoItem("potted_cattail",	() -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> CATTAIL_SEED_SACK		= HELPER.createCompatBlock("quark", "cattail_seed_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> CATTAIL_THATCH 				= HELPER.createBlock("cattail_thatch", () -> new ThatchBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB     		= HELPER.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS   		= HELPER.createBlock("cattail_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","cattail_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	
	// Rice //
	
	public static final RegistryObject<Block> RICE 		= HELPER.createBlockNoItem("rice", () -> new RiceBlock(EnvironmentalProperties.RICE));
	public static final RegistryObject<Block> TALL_RICE = HELPER.createBlockNoItem("tall_rice", () -> new DoubleRiceBlock(EnvironmentalProperties.RICE));
	public static final RegistryObject<Block> RICE_SACK	= HELPER.createCompatBlock("quark", "rice_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	// Duckweed //
	
	public static final RegistryObject<Block> DUCKWEED 						= HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));
	public static final RegistryObject<Block> DUCKWEED_THATCH          		= HELPER.createBlock("duckweed_thatch", () -> new ThatchBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB     		= HELPER.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS   		= HELPER.createBlock("duckweed_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","duckweed_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.THATCH), ItemGroup.BUILDING_BLOCKS);

	// Plants //
	
	public static final RegistryObject<Block> GIANT_TALL_GRASS = HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.from(Blocks.TALL_GRASS)), ItemGroup.DECORATIONS);
	
	// Paintings //
	
	public static final RegistryObject<PaintingType> SNAKE_BLOCK 			= PAINTINGS.register("snake_block", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> SOMETHING_IN_THE_WATER = PAINTINGS.register("something_in_the_water", () -> new PaintingType(48, 32));
}
