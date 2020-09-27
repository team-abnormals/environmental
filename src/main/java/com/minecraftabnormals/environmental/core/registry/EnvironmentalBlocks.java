package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.block.CartwheelBlock;
import com.minecraftabnormals.environmental.common.block.CattailBlock;
import com.minecraftabnormals.environmental.common.block.CattailSproutsBlock;
import com.minecraftabnormals.environmental.common.block.CherryLeavesBlock;
import com.minecraftabnormals.environmental.common.block.DoubleCattailBlock;
import com.minecraftabnormals.environmental.common.block.DoubleRiceBlock;
import com.minecraftabnormals.environmental.common.block.DuckweedBlock;
import com.minecraftabnormals.environmental.common.block.EmptyNestBlock;
import com.minecraftabnormals.environmental.common.block.GiantLilyPadBlock;
import com.minecraftabnormals.environmental.common.block.HangingWillowLeavesBlock;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.block.KilnBlock;
import com.minecraftabnormals.environmental.common.block.LargeLilyPadBlock;
import com.minecraftabnormals.environmental.common.block.LotusFlowerBlock;
import com.minecraftabnormals.environmental.common.block.MudBlock;
import com.minecraftabnormals.environmental.common.block.MyceliumSproutsBlock;
import com.minecraftabnormals.environmental.common.block.PottedCartwheelBlock;
import com.minecraftabnormals.environmental.common.block.RiceBlock;
import com.minecraftabnormals.environmental.common.block.RugBlock;
import com.minecraftabnormals.environmental.common.block.SawmillBlock;
import com.minecraftabnormals.environmental.common.block.SlabfishEffigyBlock;
import com.minecraftabnormals.environmental.common.block.TallDeadBushBlock;
import com.minecraftabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.world.gen.feature.trees.CherryTree;
import com.minecraftabnormals.environmental.common.world.gen.feature.trees.WillowTree;
import com.minecraftabnormals.environmental.common.world.gen.feature.trees.WisteriaTree;
import com.minecraftabnormals.environmental.common.world.gen.util.WisteriaColor;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalProperties;
import com.mojang.datafixers.util.Pair;
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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlocks {

    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;
    public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Environmental.MODID);

	// Paintings //
	
	public static final RegistryObject<PaintingType> SNAKE_BLOCK 			= PAINTINGS.register("snake_block", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> SOMETHING_IN_THE_WATER = PAINTINGS.register("something_in_the_water", () -> new PaintingType(48, 32));
	
    // Crafting //
	
    public static final RegistryObject<Block> KILN 		= HELPER.createBlock("kiln", () -> new KilnBlock(Properties.from(Blocks.SMOKER)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> SAWMILL 	= HELPER.createBlock("sawmill", () -> new SawmillBlock(Properties.from(Blocks.STONECUTTER).notSolid()), ItemGroup.DECORATIONS);

    // Building //
    
    public static final RegistryObject<Block> CHISELED_BRICKS 			= HELPER.createBlock("chiseled_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.BRICKS)), ItemGroup.BUILDING_BLOCKS);
    
    public static final RegistryObject<Block> MUD 						= HELPER.createBlock("mud", () -> new MudBlock(EnvironmentalProperties.MUD), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_BRICKS 				= HELPER.createBlock("mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_BRICK_STAIRS 			= HELPER.createBlock("mud_brick_stairs", () -> new AbnormalsStairsBlock(MUD_BRICKS.get().getDefaultState(), EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_BRICK_SLAB 			= HELPER.createBlock("mud_brick_slab", () -> new SlabBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_BRICK_WALL 			= HELPER.createBlock("mud_brick_wall", () -> new WallBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB 	= HELPER.createCompatBlock("quark", "mud_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_MUD_BRICKS 		= HELPER.createBlock("chiseled_mud_bricks", () -> new Block(EnvironmentalProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SLABFISH_EFFIGY 			= HELPER.createBlock("slabfish_effigy", () -> new SlabfishEffigyBlock(EnvironmentalProperties.FLOWER_POT), ItemGroup.DECORATIONS);
    
    public static final RegistryObject<Block> ICE_BRICKS 				= HELPER.createBlock("ice_bricks", () -> new Block(EnvironmentalProperties.ICE_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ICE_BRICK_STAIRS 			= HELPER.createBlock("ice_brick_stairs", () -> new AbnormalsStairsBlock(ICE_BRICKS.get().getDefaultState(), EnvironmentalProperties.ICE_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ICE_BRICK_SLAB 			= HELPER.createBlock("ice_brick_slab", () -> new SlabBlock(EnvironmentalProperties.ICE_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ICE_BRICK_WALL 			= HELPER.createBlock("ice_brick_wall", () -> new WallBlock(EnvironmentalProperties.ICE_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ICE_BRICK_VERTICAL_SLAB 	= HELPER.createCompatBlock("quark", "ice_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.ICE_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_ICE_BRICKS 		= HELPER.createBlock("chiseled_ice_bricks", () -> new Block(EnvironmentalProperties.ICE_BRICKS), ItemGroup.BUILDING_BLOCKS);
    
    public static final RegistryObject<Block> ICE_LANTERN 				= HELPER.createBlock("ice_lantern", () -> new LanternBlock(EnvironmentalProperties.ICE_LANTERN), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ICE_CHAIN 				= HELPER.createBlock("ice_chain", () -> new ChainBlock(EnvironmentalProperties.ICE_CHAIN), ItemGroup.DECORATIONS);

    // Crops //
    
	public static final RegistryObject<Block> CATTAIL_SPROUTS 		= HELPER.createBlockNoItem("cattail_sprouts", () -> new CattailSproutsBlock(EnvironmentalProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL 				= HELPER.createBlock("cattail", () -> new CattailBlock(EnvironmentalProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TALL_CATTAIL 			= HELPER.createBlock("tall_cattail", () -> new DoubleCattailBlock(EnvironmentalProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_CATTAIL		= HELPER.createBlockNoItem("potted_cattail",	() -> new FlowerPotBlock(EnvironmentalBlocks.CATTAIL.get(), EnvironmentalProperties.FLOWER_POT));
	public static final RegistryObject<Block> CATTAIL_SEED_SACK		= HELPER.createCompatBlock("quark", "cattail_seed_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);
	
	public static final RegistryObject<Block> RICE 		= HELPER.createBlockNoItem("rice", () -> new RiceBlock(EnvironmentalProperties.RICE));
	public static final RegistryObject<Block> TALL_RICE = HELPER.createBlockNoItem("tall_rice", () -> new DoubleRiceBlock(EnvironmentalProperties.RICE));
	public static final RegistryObject<Block> RICE_SACK	= HELPER.createCompatBlock("quark", "rice_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	// Foliage //
	
	public static final RegistryObject<Block> DUCKWEED 			= HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(EnvironmentalProperties.DUCKWEED));
	public static final RegistryObject<Block> LARGE_LILY_PAD 	= HELPER.createBlockNoItem("large_lily_pad", () -> new LargeLilyPadBlock(AbstractBlock.Properties.from(Blocks.LILY_PAD)));
	public static final RegistryObject<Block> GIANT_LILY_PAD 	= HELPER.createBlockNoItem("giant_lily_pad", () -> new GiantLilyPadBlock(AbstractBlock.Properties.from(Blocks.LILY_PAD)));

	public static final RegistryObject<Block> MYCELIUM_SPROUTS	= HELPER.createBlock("mycelium_sprouts", () -> new MyceliumSproutsBlock(EnvironmentalProperties.MYCELIUM_SPROUTS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> GIANT_TALL_GRASS 	= HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.from(Blocks.TALL_GRASS)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> TALL_DEAD_BUSH 	= HELPER.createBlock("tall_dead_bush", () -> new TallDeadBushBlock(EnvironmentalProperties.TALL_DEAD_BUSH), ItemGroup.DECORATIONS);
    
    // Eggs //
    
	public static final RegistryObject<Block> TWIG_NEST 		= HELPER.createBlock("twig_nest", () -> new EmptyNestBlock(EnvironmentalProperties.TWIG_NEST), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TWIG_CHICKEN_NEST = HELPER.createBlockNoItem("twig_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock)TWIG_NEST.get(), EnvironmentalProperties.TWIG_NEST));
	public static final RegistryObject<Block> TWIG_DUCK_NEST 	= HELPER.createBlockNoItem("twig_duck_nest", () -> new BirdNestBlock(EnvironmentalItems.DUCK_EGG, (EmptyNestBlock)TWIG_NEST.get(), EnvironmentalProperties.TWIG_NEST));
	public static final RegistryObject<Block> HAY_NEST 			= HELPER.createBlock("hay_nest", () -> new EmptyNestBlock(EnvironmentalProperties.HAY_NEST), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> HAY_CHICKEN_NEST 	= HELPER.createBlockNoItem("hay_chicken_nest", () -> new BirdNestBlock(() -> Items.EGG, (EmptyNestBlock)HAY_NEST.get(), EnvironmentalProperties.HAY_NEST));
	public static final RegistryObject<Block> HAY_DUCK_NEST 	= HELPER.createBlockNoItem("hay_duck_nest", () -> new BirdNestBlock(EnvironmentalItems.DUCK_EGG, (EmptyNestBlock)HAY_NEST.get(), EnvironmentalProperties.HAY_NEST));
    
	public static final RegistryObject<Block> CHICKEN_EGG_CRATE = HELPER.createCompatBlock("quark", "chicken_egg_crate", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> DUCK_EGG_CRATE = HELPER.createCompatBlock("quark", "duck_egg_crate", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.LIGHT_BLUE).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TURTLE_EGG_CRATE = HELPER.createCompatBlock("quark", "turtle_egg_crate", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.DECORATIONS);
	
	// Decorations //
	
	public static final RegistryObject<Block> PODZOL_PATH 		= HELPER.createBlock("podzol_path", () -> new GrassPathBlock(EnvironmentalProperties.PODZOL_PATH), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MYCELIUM_PATH 	= HELPER.createBlock("mycelium_path", () -> new GrassPathBlock(EnvironmentalProperties.MYCELIUM_PATH), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YAK_HAIR_BLOCK	= HELPER.createBlock("yak_hair_block", () -> new ThatchBlock(EnvironmentalProperties.YAK_HAIR_BLOCK), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> YAK_HAIR_RUG		= HELPER.createBlock("yak_hair_rug", () -> new RugBlock(EnvironmentalProperties.YAK_HAIR_RUG), ItemGroup.BUILDING_BLOCKS);

    public static final RegistryObject<Block> GRASS_THATCH               	= HELPER.createBlock("grass_thatch", () -> new ThatchBlock(EnvironmentalProperties.GRASS_THATCH), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRASS_THATCH_SLAB          	= HELPER.createBlock("grass_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.GRASS_THATCH), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRASS_THATCH_STAIRS        	= HELPER.createBlock("grass_thatch_stairs", () -> new ThatchStairsBlock(GRASS_THATCH.get().getDefaultState(), EnvironmentalProperties.GRASS_THATCH), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRASS_THATCH_VERTICAL_SLAB 	= HELPER.createCompatBlock("quark","grass_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.GRASS_THATCH), ItemGroup.BUILDING_BLOCKS);

    public static final RegistryObject<Block> CATTAIL_THATCH 				= HELPER.createBlock("cattail_thatch", () -> new ThatchBlock(EnvironmentalProperties.CATTAIL_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB     		= HELPER.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.CATTAIL_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS   		= HELPER.createBlock("cattail_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), EnvironmentalProperties.CATTAIL_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","cattail_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.CATTAIL_THATCH), ItemGroup.BUILDING_BLOCKS);
    
    public static final RegistryObject<Block> DUCKWEED_THATCH          		= HELPER.createBlock("duckweed_thatch", () -> new ThatchBlock(EnvironmentalProperties.DUCKWEED_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB     		= HELPER.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS   		= HELPER.createBlock("duckweed_thatch_stairs", () -> new ThatchStairsBlock(DUCKWEED_THATCH.get().getDefaultState(), EnvironmentalProperties.DUCKWEED_THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","duckweed_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(EnvironmentalProperties.DUCKWEED_THATCH), ItemGroup.BUILDING_BLOCKS);

    // Flowers //

    public static final RegistryObject<Block> CARTWHEEL 			= HELPER.createBlock("cartwheel", () -> new CartwheelBlock(Effects.LEVITATION, 3, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUEBELL 				= HELPER.createBlock("bluebell", () -> new AbnormalsFlowerBlock(Effects.HUNGER, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> VIOLET 				= HELPER.createBlock("violet", () -> new AbnormalsFlowerBlock(Effects.INVISIBILITY, 9, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DIANTHUS 				= HELPER.createBlock("dianthus", () -> new AbnormalsFlowerBlock(Effects.STRENGTH, 7, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> RED_LOTUS_FLOWER 		= HELPER.createBlock("red_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticles.RED_LOTUS_BLOSSOM::get, Effects.SLOW_FALLING, 5, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_LOTUS_FLOWER 	= HELPER.createBlock("white_lotus_flower", () -> new LotusFlowerBlock(EnvironmentalParticles.WHITE_LOTUS_BLOSSOM::get, Effects.SLOW_FALLING, 5, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> YELLOW_HIBISCUS 		= HELPER.createBlock("yellow_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ORANGE_HIBISCUS 		= HELPER.createBlock("orange_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> RED_HIBISCUS 			= HELPER.createBlock("red_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_HIBISCUS 		= HELPER.createBlock("pink_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> MAGENTA_HIBISCUS 		= HELPER.createBlock("magenta_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_HIBISCUS 		= HELPER.createBlock("purple_hibiscus", () -> new AbnormalsFlowerBlock(Effects.GLOWING, 8, EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);

    public static final RegistryObject<Block> POTTED_CARTWHEEL 			= HELPER.createBlockNoItem("potted_cartwheel", () -> new PottedCartwheelBlock(CARTWHEEL.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BLUEBELL 			= HELPER.createBlockNoItem("potted_bluebell", () -> new FlowerPotBlock(BLUEBELL.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_VIOLET 			= HELPER.createBlockNoItem("potted_violet", () -> new FlowerPotBlock(VIOLET.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_DIANTHUS 			= HELPER.createBlockNoItem("potted_dianthus", () -> new FlowerPotBlock(DIANTHUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_RED_LOTUS_FLOWER 	= HELPER.createBlockNoItem("potted_red_lotus_flower", () -> new FlowerPotBlock(RED_LOTUS_FLOWER.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_WHITE_LOTUS_FLOWER = HELPER.createBlockNoItem("potted_white_lotus_flower", () -> new FlowerPotBlock(WHITE_LOTUS_FLOWER.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_YELLOW_HIBISCUS 	= HELPER.createBlockNoItem("potted_yellow_hibiscus", () -> new FlowerPotBlock(YELLOW_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_ORANGE_HIBISCUS 	= HELPER.createBlockNoItem("potted_orange_hibiscus", () -> new FlowerPotBlock(ORANGE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_RED_HIBISCUS 		= HELPER.createBlockNoItem("potted_red_hibiscus", () -> new FlowerPotBlock(RED_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PINK_HIBISCUS 		= HELPER.createBlockNoItem("potted_pink_hibiscus", () -> new FlowerPotBlock(PINK_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_MAGENTA_HIBISCUS 	= HELPER.createBlockNoItem("potted_magenta_hibiscus", () -> new FlowerPotBlock(MAGENTA_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PURPLE_HIBISCUS 	= HELPER.createBlockNoItem("potted_purple_hibiscus", () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), EnvironmentalProperties.FLOWER_POT));

    // Tall Flowers //

    public static final RegistryObject<Block> PINK_DELPHINIUM 		= HELPER.createBlock("pink_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_DELPHINIUM 		= HELPER.createBlock("blue_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_DELPHINIUM 	= HELPER.createBlock("purple_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_DELPHINIUM 		= HELPER.createBlock("white_delphinium", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.DELPHINIUMS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BIRD_OF_PARADISE 		= HELPER.createBlock("bird_of_paradise", () -> new AbnormalsTallFlowerBlock(EnvironmentalProperties.FLOWER), ItemGroup.DECORATIONS);

    public static final RegistryObject<Block> POTTED_PINK_DELPHINIUM 	= HELPER.createBlockNoItem("potted_pink_delphinium", () -> new FlowerPotBlock(PINK_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BLUE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_blue_delphinium", () -> new FlowerPotBlock(BLUE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PURPLE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_purple_delphinium", () -> new FlowerPotBlock(PURPLE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_WHITE_DELPHINIUM 	= HELPER.createBlockNoItem("potted_white_delphinium", () -> new FlowerPotBlock(WHITE_DELPHINIUM.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BIRD_OF_PARADISE 	= HELPER.createBlockNoItem("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.get(), EnvironmentalProperties.FLOWER_POT));

    // Willow //

    public static final RegistryObject<Block> STRIPPED_WILLOW_LOG = HELPER.createBlock("stripped_willow_log", () -> new StrippedLogBlock(EnvironmentalProperties.WILLOW_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD = HELPER.createBlock("stripped_willow_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.WILLOW_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_LOG = HELPER.createBlock("willow_log", () -> new AbnormalsLogBlock(STRIPPED_WILLOW_LOG, EnvironmentalProperties.WILLOW_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_WOOD = HELPER.createBlock("willow_wood", () -> new WoodBlock(STRIPPED_WILLOW_WOOD, EnvironmentalProperties.WILLOW_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_PLANKS = HELPER.createBlock("willow_planks", () -> new PlanksBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_SLAB = HELPER.createBlock("willow_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_STAIRS = HELPER.createBlock("willow_stairs", () -> new WoodStairsBlock(WILLOW_PLANKS.get().getDefaultState(), EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_FENCE = HELPER.createBlock("willow_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WILLOW_FENCE_GATE = HELPER.createBlock("willow_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.WILLOW_PRESSURE_PLATE), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WILLOW_DOOR = HELPER.createBlock("willow_door", () -> new WoodDoorBlock(EnvironmentalProperties.WILLOW_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WILLOW_TRAPDOOR = HELPER.createBlock("willow_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WILLOW_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WILLOW_BUTTON = HELPER.createBlock("willow_button", () -> new AbnormalsWoodButtonBlock(EnvironmentalProperties.WILLOW_BUTTON), ItemGroup.REDSTONE);
    public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> WILLOW_SIGNS = HELPER.createSignBlock("willow", MaterialColor.GREEN);

    public static final RegistryObject<Block> WILLOW_LEAVES = HELPER.createBlock("willow_leaves", () -> new AbnormalsLeavesBlock(EnvironmentalProperties.WILLOW_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingWillowLeavesBlock(EnvironmentalProperties.WILLOW_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WILLOW_SAPLING = HELPER.createBlock("willow_sapling", () -> new AbnormalsSaplingBlock(new WillowTree(), EnvironmentalProperties.WILLOW_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(EnvironmentalBlocks.WILLOW_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

    public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS = HELPER.createCompatBlock("quark", "vertical_willow_planks", () -> new Block(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_LADDER = HELPER.createCompatBlock("quark", "willow_ladder", () -> new AbnormalsLadderBlock(EnvironmentalProperties.WILLOW_LADDER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WILLOW_BOOKSHELF = HELPER.createCompatBlock("quark", "willow_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WILLOW_BOOKSHELF), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WILLOW_LEAF_CARPET = HELPER.createCompatBlock("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WILLOW_LEAVES.notSolid()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WILLOW_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "willow_beehive", () -> new AbnormalsBeehiveBlock(Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> WILLOW_CHESTS = HELPER.createCompatChestBlocks("willow", MaterialColor.GREEN);

    // Cherry //

    public static final RegistryObject<Block> STRIPPED_CHERRY_LOG = HELPER.createBlock("stripped_cherry_log", () -> new StrippedLogBlock(EnvironmentalProperties.CHERRY_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_CHERRY_WOOD = HELPER.createBlock("stripped_cherry_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.CHERRY_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_LOG = HELPER.createBlock("cherry_log", () -> new AbnormalsLogBlock(STRIPPED_CHERRY_LOG, EnvironmentalProperties.CHERRY_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_WOOD = HELPER.createBlock("cherry_wood", () -> new WoodBlock(STRIPPED_CHERRY_WOOD, EnvironmentalProperties.CHERRY_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_PLANKS = HELPER.createBlock("cherry_planks", () -> new PlanksBlock(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_SLAB = HELPER.createBlock("cherry_slab", () -> new WoodSlabBlock(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_STAIRS = HELPER.createBlock("cherry_stairs", () -> new WoodStairsBlock(CHERRY_PLANKS.get().getDefaultState(), EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_FENCE = HELPER.createBlock("cherry_fence", () -> new WoodFenceBlock(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CHERRY_FENCE_GATE = HELPER.createBlock("cherry_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> CHERRY_PRESSURE_PLATE = HELPER.createBlock("cherry_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, EnvironmentalProperties.CHERRY_PRESSURE_PLATE), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> CHERRY_DOOR = HELPER.createBlock("cherry_door", () -> new WoodDoorBlock(EnvironmentalProperties.CHERRY_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> CHERRY_TRAPDOOR = HELPER.createBlock("cherry_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.CHERRY_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> CHERRY_BUTTON = HELPER.createBlock("cherry_button", () -> new AbnormalsWoodButtonBlock(EnvironmentalProperties.CHERRY_BUTTON), ItemGroup.REDSTONE);
    public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> CHERRY_SIGNS = HELPER.createSignBlock("cherry", MaterialColor.RED);

    public static final RegistryObject<Block> CHERRY_LEAVES = HELPER.createBlock("cherry_leaves", () -> new CherryLeavesBlock(EnvironmentalProperties.CHERRY_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CHERRY_SAPLING = HELPER.createBlock("cherry_sapling", () -> new AbnormalsSaplingBlock(new CherryTree(), EnvironmentalProperties.CHERRY_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> POTTED_CHERRY_SAPLING = HELPER.createBlockNoItem("potted_cherry_sapling", () -> new FlowerPotBlock(EnvironmentalBlocks.CHERRY_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANKS = HELPER.createCompatBlock("quark", "vertical_cherry_planks", () -> new Block(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "cherry_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.CHERRY_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_LADDER = HELPER.createCompatBlock("quark", "cherry_ladder", () -> new AbnormalsLadderBlock(EnvironmentalProperties.CHERRY_LADDER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CHERRY_BOOKSHELF = HELPER.createCompatBlock("quark", "cherry_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.CHERRY_BOOKSHELF), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHERRY_LEAF_CARPET = HELPER.createCompatBlock("quark", "cherry_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.CHERRY_LEAVES.notSolid()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CHERRY_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "cherry_beehive", () -> new AbnormalsBeehiveBlock(Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> CHERRY_CHESTS = HELPER.createCompatChestBlocks("cherry", MaterialColor.RED);
    
	public static final RegistryObject<Block> CHERRY_CRATE = HELPER.createCompatBlock("quark", "cherry_crate", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.DECORATIONS);

    // Wisteria //

    public static final RegistryObject<Block> STRIPPED_WISTERIA_LOG = HELPER.createBlock("stripped_wisteria_log", () -> new StrippedLogBlock(EnvironmentalProperties.STRIPPED_WISTERIA_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_LOG = HELPER.createBlock("wisteria_log", () -> new AbnormalsLogBlock(STRIPPED_WISTERIA_LOG, EnvironmentalProperties.WISTERIA_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_WISTERIA_WOOD = HELPER.createBlock("stripped_wisteria_wood", () -> new StrippedWoodBlock(EnvironmentalProperties.STRIPPED_WISTERIA_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_WOOD = HELPER.createBlock("wisteria_wood", () -> new WoodBlock(STRIPPED_WISTERIA_WOOD, EnvironmentalProperties.WISTERIA_LOG), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_PLANKS = HELPER.createBlock("wisteria_planks", () -> new PlanksBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_STAIRS = HELPER.createBlock("wisteria_stairs", () -> new WoodStairsBlock(WISTERIA_PLANKS.get().getDefaultState(), EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_SLAB = HELPER.createBlock("wisteria_slab", () -> new WoodSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_FENCE = HELPER.createBlock("wisteria_fence", () -> new WoodFenceBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WISTERIA_FENCE_GATE = HELPER.createBlock("wisteria_fence_gate", () -> new WoodFenceGateBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_TRAPDOOR = HELPER.createBlock("wisteria_trapdoor", () -> new WoodTrapDoorBlock(EnvironmentalProperties.WISTERIA_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_DOOR = HELPER.createBlock("wisteria_door", () -> new WoodDoorBlock(EnvironmentalProperties.WISTERIA_DOOR), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_PRESSURE_PLATE = HELPER.createBlock("wisteria_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> WISTERIA_BUTTON = HELPER.createBlock("wisteria_button", () -> new AbnormalsWoodButtonBlock(EnvironmentalProperties.WISTERIA_BUTTON), ItemGroup.REDSTONE);
    public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> WISTERIA_SIGNS = HELPER.createSignBlock("wisteria", MaterialColor.WHITE_TERRACOTTA);

    public static final RegistryObject<Block> PINK_WISTERIA_LEAVES = HELPER.createBlock("pink_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_LEAVES = HELPER.createBlock("blue_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_LEAVES = HELPER.createBlock("purple_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_LEAVES = HELPER.createBlock("white_wisteria_leaves", () -> new WisteriaLeavesBlock(EnvironmentalProperties.WHITE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_HANGING_WISTERIA_LEAVES = HELPER.createBlock("pink_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PINK_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("blue_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.BLUE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("purple_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.PURPLE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_HANGING_WISTERIA_LEAVES = HELPER.createBlock("white_hanging_wisteria_leaves", () -> new HangingWisteriaLeavesBlock(EnvironmentalProperties.WHITE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_WISTERIA_SAPLING = HELPER.createBlock("pink_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.PINK), EnvironmentalProperties.WISTERIA_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_SAPLING = HELPER.createBlock("blue_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.BLUE), EnvironmentalProperties.WISTERIA_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_SAPLING = HELPER.createBlock("purple_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.PURPLE), EnvironmentalProperties.WISTERIA_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_SAPLING = HELPER.createBlock("white_wisteria_sapling", () -> new AbnormalsSaplingBlock(new WisteriaTree(WisteriaColor.WHITE), EnvironmentalProperties.WISTERIA_SAPLING), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> POTTED_PINK_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_pink_wisteria_sapling", () -> new FlowerPotBlock(PINK_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_BLUE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_blue_wisteria_sapling", () -> new FlowerPotBlock(BLUE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_PURPLE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_purple_wisteria_sapling", () -> new FlowerPotBlock(PURPLE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));
    public static final RegistryObject<Block> POTTED_WHITE_WISTERIA_SAPLING = HELPER.createBlockNoItem("potted_white_wisteria_sapling", () -> new FlowerPotBlock(WHITE_WISTERIA_SAPLING.get(), EnvironmentalProperties.FLOWER_POT));

    public static final RegistryObject<Block> WISTERIA_BOOKSHELF = HELPER.createCompatBlock("quark", "wisteria_bookshelf", () -> new BookshelfBlock(EnvironmentalProperties.WISTERIA_BOOKSHELF), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_LADDER = HELPER.createCompatBlock("quark", "wisteria_ladder", () -> new AbnormalsLadderBlock(EnvironmentalProperties.WISTERIA_LADDER), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> VERTICAL_WISTERIA_PLANKS = HELPER.createCompatBlock("quark", "vertical_wisteria_planks", () -> new Block(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WISTERIA_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "wisteria_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WISTERIA_PLANKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "pink_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.PINK_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "blue_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.BLUE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "purple_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.PURPLE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_WISTERIA_LEAF_CARPET = HELPER.createCompatBlock("quark", "white_wisteria_leaf_carpet", () -> new LeafCarpetBlock(EnvironmentalProperties.WHITE_WISTERIA_LEAVES), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WISTERIA_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "wisteria_beehive", () -> new AbnormalsBeehiveBlock(Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
    public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> WISTERIA_CHESTS = HELPER.createCompatChestBlocks("wisteria", MaterialColor.WHITE_TERRACOTTA);

    // Terracotta Bricks //
    
    public static final RegistryObject<Block> TERRACOTTA_BRICKS 			= HELPER.createBlock("terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICKS       = HELPER.createBlock("white_terracotta_bricks", () -> new Block(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICKS      = HELPER.createBlock("orange_terracotta_bricks", () -> new Block(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICKS     = HELPER.createBlock("magenta_terracotta_bricks", () -> new Block(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICKS  = HELPER.createBlock("light_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICKS      = HELPER.createBlock("yellow_terracotta_bricks", () -> new Block(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_TERRACOTTA_BRICKS        = HELPER.createBlock("lime_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_TERRACOTTA_BRICKS        = HELPER.createBlock("pink_terracotta_bricks", () -> new Block(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICKS        = HELPER.createBlock("gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICKS  = HELPER.createBlock("light_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICKS        = HELPER.createBlock("cyan_terracotta_bricks", () -> new Block(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICKS      = HELPER.createBlock("purple_terracotta_bricks", () -> new Block(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICKS        = HELPER.createBlock("blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICKS       = HELPER.createBlock("brown_terracotta_bricks", () -> new Block(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICKS       = HELPER.createBlock("green_terracotta_bricks", () -> new Block(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_TERRACOTTA_BRICKS         = HELPER.createBlock("red_terracotta_bricks", () -> new Block(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICKS       = HELPER.createBlock("black_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);

    public static final RegistryObject<Block> TERRACOTTA_BRICK_STAIRS               = HELPER.createBlock("terracotta_brick_stairs", () -> new AbnormalsStairsBlock(TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_STAIRS         = HELPER.createBlock("white_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(WHITE_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_STAIRS        = HELPER.createBlock("orange_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(ORANGE_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_STAIRS       = HELPER.createBlock("magenta_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(MAGENTA_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_STAIRS        = HELPER.createBlock("yellow_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(YELLOW_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS    = HELPER.createBlock("light_blue_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(LIGHT_BLUE_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_STAIRS          = HELPER.createBlock("lime_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(LIME_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_STAIRS          = HELPER.createBlock("pink_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(PINK_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_STAIRS          = HELPER.createBlock("gray_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(GRAY_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS    = HELPER.createBlock("light_gray_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(LIGHT_GRAY_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_STAIRS          = HELPER.createBlock("cyan_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(CYAN_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_STAIRS        = HELPER.createBlock("purple_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(PURPLE_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_STAIRS          = HELPER.createBlock("blue_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(BLUE_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_STAIRS         = HELPER.createBlock("brown_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(BROWN_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_STAIRS         = HELPER.createBlock("green_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(GREEN_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_STAIRS           = HELPER.createBlock("red_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(RED_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_STAIRS         = HELPER.createBlock("black_terracotta_brick_stairs", () -> new AbnormalsStairsBlock(BLACK_TERRACOTTA_BRICKS.get().getDefaultState(), EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    
    public static final RegistryObject<Block> TERRACOTTA_BRICK_SLAB 			= HELPER.createBlock("terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_SLAB       = HELPER.createBlock("white_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_SLAB      = HELPER.createBlock("orange_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_SLAB     = HELPER.createBlock("magenta_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_SLAB  = HELPER.createBlock("light_blue_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_SLAB      = HELPER.createBlock("yellow_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_SLAB        = HELPER.createBlock("lime_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_SLAB        = HELPER.createBlock("pink_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_SLAB        = HELPER.createBlock("gray_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_SLAB  = HELPER.createBlock("light_gray_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_SLAB        = HELPER.createBlock("cyan_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_SLAB      = HELPER.createBlock("purple_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_SLAB        = HELPER.createBlock("blue_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_SLAB       = HELPER.createBlock("brown_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_SLAB       = HELPER.createBlock("green_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_SLAB         = HELPER.createBlock("red_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);	
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_SLAB       = HELPER.createBlock("black_terracotta_brick_slab", () -> new SlabBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    
	public static final RegistryObject<Block> TERRACOTTA_BRICK_WALL            = HELPER.createBlock("terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_WALL      = HELPER.createBlock("white_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_WALL     = HELPER.createBlock("orange_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_WALL    = HELPER.createBlock("magenta_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_WALL = HELPER.createBlock("light_blue_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_WALL     = HELPER.createBlock("yellow_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_WALL       = HELPER.createBlock("lime_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_WALL       = HELPER.createBlock("pink_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_WALL       = HELPER.createBlock("gray_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_WALL = HELPER.createBlock("light_gray_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_WALL       = HELPER.createBlock("cyan_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_WALL     = HELPER.createBlock("purple_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_WALL       = HELPER.createBlock("blue_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_WALL      = HELPER.createBlock("brown_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_WALL      = HELPER.createBlock("green_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_WALL        = HELPER.createBlock("red_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_WALL      = HELPER.createBlock("black_terracotta_brick_wall", () -> new WallBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.DECORATIONS);

    public static final RegistryObject<Block> TERRACOTTA_BRICK_VERTICAL_SLAB            = HELPER.createCompatBlock("quark", "terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICK_VERTICAL_SLAB      = HELPER.createCompatBlock("quark", "white_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICK_VERTICAL_SLAB     = HELPER.createCompatBlock("quark", "orange_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICK_VERTICAL_SLAB    = HELPER.createCompatBlock("quark", "magenta_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "light_blue_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICK_VERTICAL_SLAB     = HELPER.createCompatBlock("quark", "yellow_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_TERRACOTTA_BRICK_VERTICAL_SLAB       = HELPER.createCompatBlock("quark", "lime_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_TERRACOTTA_BRICK_VERTICAL_SLAB       = HELPER.createCompatBlock("quark", "pink_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICK_VERTICAL_SLAB       = HELPER.createCompatBlock("quark", "gray_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "light_gray_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.LIGHT_GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICK_VERTICAL_SLAB       = HELPER.createCompatBlock("quark", "cyan_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICK_VERTICAL_SLAB     = HELPER.createCompatBlock("quark", "purple_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICK_VERTICAL_SLAB       = HELPER.createCompatBlock("quark", "blue_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICK_VERTICAL_SLAB      = HELPER.createCompatBlock("quark", "brown_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICK_VERTICAL_SLAB      = HELPER.createCompatBlock("quark", "green_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_TERRACOTTA_BRICK_VERTICAL_SLAB        = HELPER.createCompatBlock("quark", "red_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICK_VERTICAL_SLAB      = HELPER.createCompatBlock("quark", "black_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);

    public static final RegistryObject<Block> CHISELED_TERRACOTTA_BRICKS            = HELPER.createBlock("chiseled_terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_WHITE_TERRACOTTA_BRICKS      = HELPER.createBlock("chiseled_white_terracotta_bricks", () -> new Block(EnvironmentalProperties.WHITE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_ORANGE_TERRACOTTA_BRICKS     = HELPER.createBlock("chiseled_orange_terracotta_bricks", () -> new Block(EnvironmentalProperties.ORANGE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_MAGENTA_TERRACOTTA_BRICKS    = HELPER.createBlock("chiseled_magenta_terracotta_bricks", () -> new Block(EnvironmentalProperties.MAGENTA_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_LIGHT_BLUE_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_light_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIGHT_BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_YELLOW_TERRACOTTA_BRICKS     = HELPER.createBlock("chiseled_yellow_terracotta_bricks", () -> new Block(EnvironmentalProperties.YELLOW_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_LIME_TERRACOTTA_BRICKS       = HELPER.createBlock("chiseled_lime_terracotta_bricks", () -> new Block(EnvironmentalProperties.LIME_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_PINK_TERRACOTTA_BRICKS       = HELPER.createBlock("chiseled_pink_terracotta_bricks", () -> new Block(EnvironmentalProperties.PINK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_GRAY_TERRACOTTA_BRICKS       = HELPER.createBlock("chiseled_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.GRAY_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_LIGHT_GRAY_TERRACOTTA_BRICKS = HELPER.createBlock("chiseled_light_gray_terracotta_bricks", () -> new Block(EnvironmentalProperties.TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_CYAN_TERRACOTTA_BRICKS       = HELPER.createBlock("chiseled_cyan_terracotta_bricks", () -> new Block(EnvironmentalProperties.CYAN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_PURPLE_TERRACOTTA_BRICKS     = HELPER.createBlock("chiseled_purple_terracotta_bricks", () -> new Block(EnvironmentalProperties.PURPLE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_BLUE_TERRACOTTA_BRICKS       = HELPER.createBlock("chiseled_blue_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLUE_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_BROWN_TERRACOTTA_BRICKS      = HELPER.createBlock("chiseled_brown_terracotta_bricks", () -> new Block(EnvironmentalProperties.BROWN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_GREEN_TERRACOTTA_BRICKS      = HELPER.createBlock("chiseled_green_terracotta_bricks", () -> new Block(EnvironmentalProperties.GREEN_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_RED_TERRACOTTA_BRICKS        = HELPER.createBlock("chiseled_red_terracotta_bricks", () -> new Block(EnvironmentalProperties.RED_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_BLACK_TERRACOTTA_BRICKS      = HELPER.createBlock("chiseled_black_terracotta_bricks", () -> new Block(EnvironmentalProperties.BLACK_TERRACOTTA_BRICKS), ItemGroup.BUILDING_BLOCKS);
}
