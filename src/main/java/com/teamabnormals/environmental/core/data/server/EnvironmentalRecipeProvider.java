package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.boatload.core.data.server.BoatloadRecipeProvider;
import com.teamabnormals.clayworks.core.data.server.ClayworksRecipeProvider;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.integration.boatload.EnvironmentalBoatTypes;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalRecipeProvider extends BlueprintRecipeProvider {
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");
	public static final ModLoadedCondition BERRY_GOOD_LOADED = new ModLoadedCondition("berry_good");

	public EnvironmentalRecipeProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, output, provider);
	}

	@Override
	public void buildRecipes(RecipeOutput consumer) {
		conversionRecipe(consumer, Items.LIGHT_BLUE_DYE, BLUE_DELPHINIUM, "light_blue_dye", 2);
		conversionRecipe(consumer, Items.PINK_DYE, PINK_DELPHINIUM, "pink_dye", 2);
		conversionRecipe(consumer, Items.WHITE_DYE, WHITE_DELPHINIUM, "white_dye", 2);
		conversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_DELPHINIUM, "purple_dye", 2);
		conversionRecipe(consumer, Items.BLUE_DYE, BLUEBELL, "blue_dye");
		conversionRecipe(consumer, Items.LIME_DYE, DIANTHUS, "lime_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, BIRD_OF_PARADISE, "orange_dye", 2);
		conversionRecipe(consumer, Items.PINK_DYE, CARTWHEEL, "pink_dye");
		conversionRecipe(consumer, Items.PURPLE_DYE, VIOLET, "purple_dye");
		conversionRecipe(consumer, Items.RED_DYE, RED_LOTUS_FLOWER, "red_dye");
		conversionRecipe(consumer, Items.WHITE_DYE, WHITE_LOTUS_FLOWER, "white_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, TASSELFLOWER, "orange_dye");
		conversionRecipe(consumer, Items.YELLOW_DYE, ESTUARY_MARIGOLD, "yellow_dye");
		conversionRecipe(consumer, Items.MAGENTA_DYE, MAGENTA_HIBISCUS, "magenta_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, ORANGE_HIBISCUS, "orange_dye");
		conversionRecipe(consumer, Items.PINK_DYE, PINK_HIBISCUS, "pink_dye");
		conversionRecipe(consumer, Items.YELLOW_DYE, YELLOW_HIBISCUS, "yellow_dye");
		conversionRecipe(consumer, Items.RED_DYE, RED_HIBISCUS, "red_dye");
		conversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_HIBISCUS, "purple_dye");
		conversionRecipe(consumer, Items.YELLOW_DYE, BLAZING_SUNFLOWER, "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, BEAMING_SUNFLOWER, "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, ECLIPSED_SUNFLOWER, "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, RADIANT_SUNFLOWER, "yellow_dye", 2);

		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, HIBISCUS_LEAVES, HIBISCUS_LEAF_PILE, Environmental.MOD_ID);

		foodCookingRecipes(consumer, EnvironmentalItems.DUCK, EnvironmentalItems.COOKED_DUCK);
		foodCookingRecipes(consumer, EnvironmentalItems.VENISON, EnvironmentalItems.COOKED_VENISON);
		conditionalStorageRecipes(consumer, INCUBATION_LOADED, RecipeCategory.MISC, EnvironmentalItems.DUCK_EGG, RecipeCategory.DECORATIONS, DUCK_EGG_CRATE);
		conditionalStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, EnvironmentalItems.CHERRIES, RecipeCategory.DECORATIONS, CHERRY_CRATE);
		conditionalStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, EnvironmentalItems.PLUM, RecipeCategory.DECORATIONS, PLUM_CRATE);
		storageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.CATTAIL_FLUFF, RecipeCategory.BUILDING_BLOCKS, CATTAIL_FLUFF_BLOCK);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING).requires(EnvironmentalItems.CATTAIL_FLUFF, 7).unlockedBy("has_cattail_seeds", has(EnvironmentalItems.CATTAIL_FLUFF)).save(consumer, getModConversionRecipeName(Items.STRING, EnvironmentalItems.CATTAIL_FLUFF));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH, 4).define('W', Items.WHEAT).define('G', Blocks.SHORT_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_grass", has(Blocks.SHORT_GRASS)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH, 6).define('W', Items.WHEAT).define('G', Blocks.TALL_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_tall_grass", has(Blocks.TALL_GRASS)).save(consumer, getModConversionRecipeName(GRASS_THATCH, Blocks.SHORT_GRASS));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH, 8).define('W', Items.WHEAT).define('G', GIANT_TALL_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_giant_tall_grass", has(GIANT_TALL_GRASS)).save(consumer, getModConversionRecipeName(GRASS_THATCH, GIANT_TALL_GRASS));
		generateRecipes(consumer, EnvironmentalBlockFamilies.GRASS_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CATTAIL_THATCH, 4).define('#', CATTAIL).pattern("##").pattern("##").unlockedBy("has_cattail", has(CATTAIL)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.CATTAIL_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DUCKWEED_THATCH, 4).define('#', DUCKWEED).pattern("##").pattern("##").unlockedBy("has_duckweed", has(DUCKWEED)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DUCKWEED_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS, 4).define('#', Blocks.DIRT).pattern("##").pattern("##").unlockedBy(getHasName(Blocks.DIRT), has(Blocks.DIRT)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB, DIRT_BRICKS, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS, DIRT_BRICKS);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL, DIRT_BRICKS);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS, Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB, Blocks.DIRT, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS, Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL, Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_TILES, 4).define('#', DIRT_BRICKS).pattern("##").pattern("##").unlockedBy(getHasName(DIRT_BRICKS), has(DIRT_BRICKS)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB, DIRT_TILES, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS, DIRT_TILES);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL, DIRT_TILES);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES, DIRT_BRICKS);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB, DIRT_BRICKS, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS, DIRT_BRICKS);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL, DIRT_BRICKS);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES, Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB, Blocks.DIRT, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS, Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL, Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.MUD).define('#', EnvironmentalItems.MUD_BALL).pattern("##").pattern("##").unlockedBy("has_mud_ball", has(EnvironmentalItems.MUD_BALL)).save(consumer, Environmental.location(RecipeBuilder.getDefaultRecipeId(Blocks.MUD).getPath()));
		conversionRecipeBuilder(EnvironmentalItems.MUD_BALL, Blocks.MUD, 4).group("mud_ball").save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EnvironmentalItems.MUD_BALL, 16).requires(Tags.Items.BUCKETS_WATER).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).group("mud_ball").unlockedBy("has_convertable_to_mud", has(EnvironmentalItemTags.CONVERTABLE_TO_MUD)).save(consumer, getModConversionRecipeName(EnvironmentalItems.MUD_BALL, Blocks.DIRT));
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SLABFISH_EFFIGY).define('#', Blocks.MUD_BRICKS).define('S', Blocks.MUD_BRICK_SLAB).pattern(" S ").pattern("S#S").unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS)).save(consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PACKED_MUD), RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD, 0.1F, 200).unlockedBy("has_packed_mud", has(Blocks.PACKED_MUD)).save(consumer);
		ClayworksRecipeProvider.bakingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Blocks.PACKED_MUD, SMOOTH_MUD, 0.1F, 100, Environmental.MOD_ID);
		generateRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUD_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD_SLAB, SMOOTH_MUD, 2);
		chiseled(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS, Blocks.MUD_BRICK_SLAB);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS, Blocks.MUD_BRICKS);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BOG_IRON_BLOCK).define('#', EnvironmentalItems.BOG_IRON).pattern("##").pattern("##").unlockedBy("has_bog_iron", has(EnvironmentalItems.BOG_IRON)).save(consumer, Environmental.location(RecipeBuilder.getDefaultRecipeId(BOG_IRON_BLOCK).getPath()));
		conversionRecipeBuilder(EnvironmentalItems.BOG_IRON, BOG_IRON_BLOCK, 4).group("bog_iron").save(consumer);
		trimRecipes(consumer, EnvironmentalItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE, MUDDY_SANDSTONE);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MUDDY_SAND, 2).requires(Blocks.MUD).requires(Blocks.SAND).unlockedBy("has_mud", has(Blocks.MUD)).save(consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(MUDDY_SAND), RecipeCategory.BUILDING_BLOCKS, MUDGLASS.asItem(), 0.1F, 200)
				.unlockedBy("has_smelts_to_mudglass", has(MUDDY_SAND))
				.save(consumer);
		ClayworksRecipeProvider.bakingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, MUDDY_SAND, MUDGLASS, 0.1F, 100, Environmental.MOD_ID);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MUDGLASS_PANE, 16)
				.define('#', MUDGLASS)
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_mudglass", has(MUDGLASS))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, MUDDY_SANDSTONE).define('#', MUDDY_SAND).pattern("##").pattern("##").unlockedBy("has_muddy_sandstone", has(MUDDY_SANDSTONE)).save(consumer, Environmental.location(getItemName(MUDDY_SANDSTONE)));
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, MUDDY_SANDSTONE_SLAB, Ingredient.of(MUDDY_SANDSTONE, CHISELED_MUDDY_SANDSTONE))
				.unlockedBy("has_muddy_sandstone", has(MUDDY_SANDSTONE))
				.unlockedBy("has_chiseled_muddy_sandstone", has(CHISELED_MUDDY_SANDSTONE))
				.save(consumer);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, MUDDY_SANDSTONE_SLAB, MUDDY_SANDSTONE, 2);
		stairBuilder(MUDDY_SANDSTONE_STAIRS, Ingredient.of(MUDDY_SANDSTONE, CHISELED_MUDDY_SANDSTONE, CUT_MUDDY_SANDSTONE))
				.unlockedBy("has_muddy_sandstone", has(MUDDY_SANDSTONE))
				.unlockedBy("has_chiseled_muddy_sandstone", has(CHISELED_MUDDY_SANDSTONE))
				.unlockedBy("has_cut_muddy_sandstone", has(CUT_MUDDY_SANDSTONE))
				.save(consumer);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, MUDDY_SANDSTONE_STAIRS, MUDDY_SANDSTONE);
		wall(consumer, RecipeCategory.DECORATIONS, MUDDY_SANDSTONE_WALL, MUDDY_SANDSTONE);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, MUDDY_SANDSTONE_WALL, MUDDY_SANDSTONE);
		chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, CHISELED_MUDDY_SANDSTONE, Ingredient.of(MUDDY_SANDSTONE_SLAB))
				.unlockedBy("has_muddy_sandstone", has(MUDDY_SANDSTONE))
				.unlockedBy("has_chiseled_muddy_sandstone", has(CHISELED_MUDDY_SANDSTONE))
				.unlockedBy("has_cut_muddy_sandstone", has(CUT_MUDDY_SANDSTONE))
				.save(consumer);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUDDY_SANDSTONE, MUDDY_SANDSTONE);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(MUDDY_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUDDY_SANDSTONE.asItem(), 0.1F, 200).unlockedBy("has_muddy_sandstone", has(MUDDY_SANDSTONE)).save(consumer);
		ClayworksRecipeProvider.bakingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, MUDDY_SANDSTONE, SMOOTH_MUDDY_SANDSTONE, 0.1F, 100, Environmental.MOD_ID);
		generateRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUDDY_SANDSTONE_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUDDY_SANDSTONE_SLAB, SMOOTH_MUDDY_SANDSTONE, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUDDY_SANDSTONE_STAIRS, SMOOTH_MUDDY_SANDSTONE, 1);
		generateRecipes(consumer, EnvironmentalBlockFamilies.CUT_MUDDY_SANDSTONE_FAMILY);
		cut(consumer, RecipeCategory.BUILDING_BLOCKS, CUT_MUDDY_SANDSTONE, MUDDY_SANDSTONE);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, CUT_MUDDY_SANDSTONE, MUDDY_SANDSTONE);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CUT_MUDDY_SANDSTONE_SLAB, MUDDY_SANDSTONE, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CUT_MUDDY_SANDSTONE_SLAB, CUT_MUDDY_SANDSTONE, 2);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BROWN_WOOL).define('#', EnvironmentalItems.YAK_HAIR).pattern("##").pattern("##").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR)).save(consumer, Environmental.location(getItemName(Blocks.BROWN_WOOL)));
		storageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.YAK_HAIR, RecipeCategory.BUILDING_BLOCKS, YAK_HAIR_BLOCK);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, YAK_HAIR_RUG).define('#', EnvironmentalItems.YAK_HAIR).pattern("###").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, EnvironmentalItems.YAK_PANTS).define('#', Items.LEATHER).define('B', YAK_HAIR_BLOCK).pattern("BBB").pattern("# #").pattern("# #").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR)).save(consumer);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY);
		planksFromLogs(consumer, WILLOW_PLANKS, EnvironmentalItemTags.WILLOW_LOGS, 4);
		woodFromLogs(consumer, WILLOW_WOOD, WILLOW_LOG);
		woodFromLogs(consumer, STRIPPED_WILLOW_WOOD, STRIPPED_WILLOW_LOG);
		hangingSign(consumer, WILLOW_HANGING_SIGNS.getFirst(), STRIPPED_WILLOW_LOG);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HANGING_WILLOW_LEAVES, 3).define('#', WILLOW_LEAVES).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WILLOW_LEAVES), has(WILLOW_LEAVES)).save(consumer);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WILLOW_LEAVES, WILLOW_LEAF_PILE, Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WILLOW);
		WoodworksRecipeProvider.baseRecipes(consumer, WILLOW_PLANKS, WILLOW_SLAB, WILLOW_BOARDS, WILLOW_BOOKSHELF, CHISELED_WILLOW_BOOKSHELF, WILLOW_LADDER, WILLOW_BEEHIVE, WILLOW_CHEST, TRAPPED_WILLOW_CHEST, Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY, EnvironmentalItemTags.WILLOW_LOGS, WILLOW_BOARDS, WILLOW_LADDER, Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		planksFromLogs(consumer, PINE_PLANKS, EnvironmentalItemTags.PINE_LOGS, 4);
		woodFromLogs(consumer, PINE_WOOD, PINE_LOG);
		woodFromLogs(consumer, STRIPPED_PINE_WOOD, STRIPPED_PINE_LOG);
		hangingSign(consumer, PINE_HANGING_SIGNS.getFirst(), STRIPPED_PINE_LOG);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PINE_LEAVES, PINE_LEAF_PILE, Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PINE);
		WoodworksRecipeProvider.baseRecipes(consumer, PINE_PLANKS, PINE_SLAB, PINE_BOARDS, PINE_BOOKSHELF, CHISELED_PINE_BOOKSHELF, PINE_LADDER, PINE_BEEHIVE, PINE_CHEST, TRAPPED_PINE_CHEST, Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY, EnvironmentalItemTags.PINE_LOGS, PINE_BOARDS, PINE_LADDER, Environmental.MOD_ID);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, WAXED_PINECONE).requires(PINECONE).requires(Items.HONEYCOMB).unlockedBy(getHasName(PINECONE), has(PINECONE)).save(consumer, getConversionRecipeName(WAXED_PINECONE, Items.HONEYCOMB));

		generateRecipes(consumer, EnvironmentalBlockFamilies.CEDAR_PLANKS_FAMILY);
		planksFromLogs(consumer, CEDAR_PLANKS, EnvironmentalItemTags.CEDAR_LOGS, 4);
		woodFromLogs(consumer, CEDAR_WOOD, CEDAR_LOG);
		woodFromLogs(consumer, STRIPPED_CEDAR_WOOD, STRIPPED_CEDAR_LOG);
		hangingSign(consumer, CEDAR_HANGING_SIGNS.getFirst(), STRIPPED_CEDAR_LOG);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, CEDAR_LEAVES, CEDAR_LEAF_PILE, Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.CEDAR);
		WoodworksRecipeProvider.baseRecipes(consumer, CEDAR_PLANKS, CEDAR_SLAB, CEDAR_BOARDS, CEDAR_BOOKSHELF, CHISELED_CEDAR_BOOKSHELF, CEDAR_LADDER, CEDAR_BEEHIVE, CEDAR_CHEST, TRAPPED_CEDAR_CHEST, Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.CEDAR_PLANKS_FAMILY, EnvironmentalItemTags.CEDAR_LOGS, CEDAR_BOARDS, CEDAR_LADDER, Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY);
		planksFromLogs(consumer, PLUM_PLANKS, EnvironmentalItemTags.PLUM_LOGS, 4);
		woodFromLogs(consumer, PLUM_WOOD, PLUM_LOG);
		woodFromLogs(consumer, STRIPPED_PLUM_WOOD, STRIPPED_PLUM_LOG);
		hangingSign(consumer, PLUM_HANGING_SIGNS.getFirst(), STRIPPED_PLUM_LOG);
		leafPileRecipes(consumer, PLUM_LEAVES, PLUM_LEAF_PILE);
		leafPileRecipes(consumer, CHEERFUL_PLUM_LEAVES, CHEERFUL_PLUM_LEAF_PILE);
		leafPileRecipes(consumer, MOODY_PLUM_LEAVES, MOODY_PLUM_LEAF_PILE);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PLUM);
		WoodworksRecipeProvider.baseRecipes(consumer, PLUM_PLANKS, PLUM_SLAB, PLUM_BOARDS, PLUM_BOOKSHELF, CHISELED_PLUM_BOOKSHELF, PLUM_LADDER, PLUM_BEEHIVE, PLUM_CHEST, TRAPPED_PLUM_CHEST, Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY, EnvironmentalItemTags.PLUM_LOGS, PLUM_BOARDS, PLUM_LADDER, Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY);
		planksFromLogs(consumer, WISTERIA_PLANKS, EnvironmentalItemTags.WISTERIA_LOGS, 4);
		woodFromLogs(consumer, WISTERIA_WOOD, WISTERIA_LOG);
		woodFromLogs(consumer, STRIPPED_WISTERIA_WOOD, STRIPPED_WISTERIA_LOG);
		hangingSign(consumer, WISTERIA_HANGING_SIGNS.getFirst(), STRIPPED_WISTERIA_LOG);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PINK_HANGING_WISTERIA_LEAVES, 3).define('#', PINK_WISTERIA_LEAVES).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PINK_WISTERIA_LEAVES), has(PINK_WISTERIA_LEAVES)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BLUE_HANGING_WISTERIA_LEAVES, 3).define('#', BLUE_WISTERIA_LEAVES).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(BLUE_WISTERIA_LEAVES), has(BLUE_WISTERIA_LEAVES)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURPLE_HANGING_WISTERIA_LEAVES, 3).define('#', PURPLE_WISTERIA_LEAVES).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PURPLE_WISTERIA_LEAVES), has(PURPLE_WISTERIA_LEAVES)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, WHITE_HANGING_WISTERIA_LEAVES, 3).define('#', WHITE_WISTERIA_LEAVES).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WHITE_WISTERIA_LEAVES), has(WHITE_WISTERIA_LEAVES)).save(consumer);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PINK_WISTERIA_LEAVES, PINK_WISTERIA_LEAF_PILE, Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, BLUE_WISTERIA_LEAVES, BLUE_WISTERIA_LEAF_PILE, Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PURPLE_WISTERIA_LEAVES, PURPLE_WISTERIA_LEAF_PILE, Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WHITE_WISTERIA_LEAVES, WHITE_WISTERIA_LEAF_PILE, Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WISTERIA_LEAVES, WISTERIA_LEAF_PILE, Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WISTERIA);
		WoodworksRecipeProvider.baseRecipes(consumer, WISTERIA_PLANKS, WISTERIA_SLAB, WISTERIA_BOARDS, WISTERIA_BOOKSHELF, CHISELED_WISTERIA_BOOKSHELF, WISTERIA_LADDER, WISTERIA_BEEHIVE, WISTERIA_CHEST, TRAPPED_WISTERIA_CHEST, Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY, EnvironmentalItemTags.WISTERIA_LOGS, WISTERIA_BOARDS, WISTERIA_LADDER, Environmental.MOD_ID);

		carpet(consumer, SMOOTHCAP_MOSS, SMOOTHCAP_MOSS_BLOCK);
	}
}