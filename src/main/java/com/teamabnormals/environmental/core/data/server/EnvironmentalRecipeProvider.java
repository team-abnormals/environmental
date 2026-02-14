package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.boatload.core.data.server.BoatloadRecipeProvider;
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
		conversionRecipe(consumer, Items.LIGHT_BLUE_DYE, BLUE_DELPHINIUM.get(), "light_blue_dye", 2);
		conversionRecipe(consumer, Items.PINK_DYE, PINK_DELPHINIUM.get(), "pink_dye", 2);
		conversionRecipe(consumer, Items.WHITE_DYE, WHITE_DELPHINIUM.get(), "white_dye", 2);
		conversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_DELPHINIUM.get(), "purple_dye", 2);
		conversionRecipe(consumer, Items.BLUE_DYE, BLUEBELL.get(), "blue_dye");
		conversionRecipe(consumer, Items.LIME_DYE, DIANTHUS.get(), "lime_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, BIRD_OF_PARADISE.get(), "orange_dye", 2);
		conversionRecipe(consumer, Items.PINK_DYE, CARTWHEEL.get(), "pink_dye");
		conversionRecipe(consumer, Items.PURPLE_DYE, VIOLET.get(), "purple_dye");
		conversionRecipe(consumer, Items.RED_DYE, RED_LOTUS_FLOWER.get(), "red_dye");
		conversionRecipe(consumer, Items.WHITE_DYE, WHITE_LOTUS_FLOWER.get(), "white_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, TASSELFLOWER.get(), "purple_dye");
		conversionRecipe(consumer, Items.MAGENTA_DYE, MAGENTA_HIBISCUS.get(), "magenta_dye");
		conversionRecipe(consumer, Items.ORANGE_DYE, ORANGE_HIBISCUS.get(), "orange_dye");
		conversionRecipe(consumer, Items.PINK_DYE, PINK_HIBISCUS.get(), "pink_dye");
		conversionRecipe(consumer, Items.YELLOW_DYE, YELLOW_HIBISCUS.get(), "yellow_dye");
		conversionRecipe(consumer, Items.RED_DYE, RED_HIBISCUS.get(), "red_dye");
		conversionRecipe(consumer, Items.PURPLE_DYE, PURPLE_HIBISCUS.get(), "purple_dye");
		conversionRecipe(consumer, Items.YELLOW_DYE, BLAZING_SUNFLOWER.get(), "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, BEAMING_SUNFLOWER.get(), "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, ECLIPSED_SUNFLOWER.get(), "yellow_dye", 2);
		conversionRecipe(consumer, Items.YELLOW_DYE, RADIANT_SUNFLOWER.get(), "yellow_dye", 2);

		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, HIBISCUS_LEAVES.get(), HIBISCUS_LEAF_PILE.get(), Environmental.MOD_ID);

		foodCookingRecipes(consumer, EnvironmentalItems.DUCK.get(), EnvironmentalItems.COOKED_DUCK.get());
		foodCookingRecipes(consumer, EnvironmentalItems.VENISON.get(), EnvironmentalItems.COOKED_VENISON.get());
		conditionalStorageRecipes(consumer, INCUBATION_LOADED, RecipeCategory.MISC, EnvironmentalItems.DUCK_EGG.get(), RecipeCategory.DECORATIONS, DUCK_EGG_CRATE.get());
		conditionalStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, EnvironmentalItems.CHERRIES.get(), RecipeCategory.DECORATIONS, CHERRY_CRATE.get());
		conditionalStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, EnvironmentalItems.PLUM.get(), RecipeCategory.DECORATIONS, PLUM_CRATE.get());
		storageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.CATTAIL_FLUFF.get(), RecipeCategory.BUILDING_BLOCKS, CATTAIL_FLUFF_BLOCK.get());

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING).requires(EnvironmentalItems.CATTAIL_FLUFF.get(), 7).unlockedBy("has_cattail_seeds", has(EnvironmentalItems.CATTAIL_FLUFF.get())).save(consumer, getModConversionRecipeName(Items.STRING, EnvironmentalItems.CATTAIL_FLUFF.get()));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 4).define('W', Items.WHEAT).define('G', Blocks.SHORT_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_grass", has(Blocks.SHORT_GRASS)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 6).define('W', Items.WHEAT).define('G', Blocks.TALL_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_tall_grass", has(Blocks.TALL_GRASS)).save(consumer, getModConversionRecipeName(GRASS_THATCH.get(), Blocks.SHORT_GRASS));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GRASS_THATCH.get(), 8).define('W', Items.WHEAT).define('G', GIANT_TALL_GRASS.get()).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_giant_tall_grass", has(GIANT_TALL_GRASS.get())).save(consumer, getModConversionRecipeName(GRASS_THATCH.get(), GIANT_TALL_GRASS.get()));
		generateRecipes(consumer, EnvironmentalBlockFamilies.GRASS_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CATTAIL_THATCH.get(), 4).define('#', CATTAIL.get()).pattern("##").pattern("##").unlockedBy("has_cattail", has(CATTAIL.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.CATTAIL_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DUCKWEED_THATCH.get(), 4).define('#', DUCKWEED.get()).pattern("##").pattern("##").unlockedBy("has_duckweed", has(DUCKWEED.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DUCKWEED_THATCH_FAMILY);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS.get(), 4).define('#', Blocks.DIRT).pattern("##").pattern("##").unlockedBy(getHasName(Blocks.DIRT), has(Blocks.DIRT)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB.get(), DIRT_BRICKS.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS.get(), DIRT_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL.get(), DIRT_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICKS.get(), Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_SLAB.get(), Blocks.DIRT, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_BRICK_STAIRS.get(), Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_BRICK_WALL.get(), Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), 4).define('#', DIRT_BRICKS.get()).pattern("##").pattern("##").unlockedBy(getHasName(DIRT_BRICKS.get()), has(DIRT_BRICKS.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), DIRT_TILES.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), DIRT_TILES.get());
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), DIRT_TILES.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), DIRT_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), DIRT_BRICKS.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), DIRT_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), DIRT_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILES.get(), Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_SLAB.get(), Blocks.DIRT, 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DIRT_TILE_STAIRS.get(), Blocks.DIRT);
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, DIRT_TILE_WALL.get(), Blocks.DIRT);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.MUD).define('#', EnvironmentalItems.MUD_BALL.get()).pattern("##").pattern("##").unlockedBy("has_mud_ball", has(EnvironmentalItems.MUD_BALL.get())).save(consumer, Environmental.location(RecipeBuilder.getDefaultRecipeId(Blocks.MUD).getPath()));
		conversionRecipeBuilder(EnvironmentalItems.MUD_BALL.get(), Blocks.MUD, 4).group("mud_ball").save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EnvironmentalItems.MUD_BALL.get(), 16).requires(Tags.Items.BUCKETS_WATER).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).requires(EnvironmentalItemTags.CONVERTABLE_TO_MUD).group("mud_ball").unlockedBy("has_convertable_to_mud", has(EnvironmentalItemTags.CONVERTABLE_TO_MUD)).save(consumer, getModConversionRecipeName(EnvironmentalItems.MUD_BALL.get(), Blocks.DIRT));
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SLABFISH_EFFIGY.get()).define('#', Blocks.MUD_BRICKS).define('S', Blocks.MUD_BRICK_SLAB).pattern(" S ").pattern("S#S").unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS)).save(consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PACKED_MUD), RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD.get().asItem(), 0.1F, 200).unlockedBy("has_packed_mud", has(Blocks.PACKED_MUD)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUD_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SMOOTH_MUD_SLAB.get(), SMOOTH_MUD.get(), 2);
		chiseled(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICK_SLAB);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICKS);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MUDDY_SAND.get()).requires(EnvironmentalItems.MUD_BALL.get()).requires(Blocks.SAND).unlockedBy("has_mud_ball", has(EnvironmentalItems.MUD_BALL.get())).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BROWN_WOOL).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("##").pattern("##").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer, Environmental.location(getItemName(Blocks.BROWN_WOOL)));
		storageRecipes(consumer, RecipeCategory.MISC, EnvironmentalItems.YAK_HAIR.get(), RecipeCategory.BUILDING_BLOCKS, YAK_HAIR_BLOCK.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, YAK_HAIR_RUG.get()).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("###").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, EnvironmentalItems.YAK_PANTS.get()).define('#', Items.LEATHER).define('B', YAK_HAIR_BLOCK.get()).pattern("BBB").pattern("# #").pattern("# #").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY);
		planksFromLogs(consumer, WILLOW_PLANKS.get(), EnvironmentalItemTags.WILLOW_LOGS, 4);
		woodFromLogs(consumer, WILLOW_WOOD.get(), WILLOW_LOG.get());
		woodFromLogs(consumer, STRIPPED_WILLOW_WOOD.get(), STRIPPED_WILLOW_LOG.get());
		hangingSign(consumer, WILLOW_HANGING_SIGNS.getFirst().get(), STRIPPED_WILLOW_LOG.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HANGING_WILLOW_LEAVES.get(), 3).define('#', WILLOW_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WILLOW_LEAVES.get()), has(WILLOW_LEAVES.get())).save(consumer);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WILLOW_LEAVES.get(), WILLOW_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WILLOW);
		WoodworksRecipeProvider.baseRecipes(consumer, WILLOW_PLANKS.get(), WILLOW_SLAB.get(), WILLOW_BOARDS.get(), WILLOW_BOOKSHELF.get(), CHISELED_WILLOW_BOOKSHELF.get(), WILLOW_LADDER.get(), WILLOW_BEEHIVE.get(), WILLOW_CHEST.get(), TRAPPED_WILLOW_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY, EnvironmentalItemTags.WILLOW_LOGS, WILLOW_BOARDS.get(), WILLOW_LADDER.get(), Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		planksFromLogs(consumer, PINE_PLANKS.get(), EnvironmentalItemTags.PINE_LOGS, 4);
		woodFromLogs(consumer, PINE_WOOD.get(), PINE_LOG.get());
		woodFromLogs(consumer, STRIPPED_PINE_WOOD.get(), STRIPPED_PINE_LOG.get());
		hangingSign(consumer, PINE_HANGING_SIGNS.getFirst().get(), STRIPPED_PINE_LOG.get());
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PINE_LEAVES.get(), PINE_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PINE);
		WoodworksRecipeProvider.baseRecipes(consumer, PINE_PLANKS.get(), PINE_SLAB.get(), PINE_BOARDS.get(), PINE_BOOKSHELF.get(), CHISELED_PINE_BOOKSHELF.get(), PINE_LADDER.get(), PINE_BEEHIVE.get(), PINE_CHEST.get(), TRAPPED_PINE_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY, EnvironmentalItemTags.PINE_LOGS, PINE_BOARDS.get(), PINE_LADDER.get(), Environmental.MOD_ID);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, WAXED_PINECONE.get()).requires(PINECONE.get()).requires(Items.HONEYCOMB).unlockedBy(getHasName(PINECONE.get()), has(PINECONE.get())).save(consumer, getConversionRecipeName(WAXED_PINECONE.get(), Items.HONEYCOMB));

		generateRecipes(consumer, EnvironmentalBlockFamilies.CEDAR_PLANKS_FAMILY);
		planksFromLogs(consumer, CEDAR_PLANKS.get(), EnvironmentalItemTags.CEDAR_LOGS, 4);
		woodFromLogs(consumer, CEDAR_WOOD.get(), CEDAR_LOG.get());
		woodFromLogs(consumer, STRIPPED_CEDAR_WOOD.get(), STRIPPED_CEDAR_LOG.get());
		hangingSign(consumer, CEDAR_HANGING_SIGNS.getFirst().get(), STRIPPED_CEDAR_LOG.get());
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, CEDAR_LEAVES.get(), CEDAR_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.CEDAR);
		WoodworksRecipeProvider.baseRecipes(consumer, CEDAR_PLANKS.get(), CEDAR_SLAB.get(), CEDAR_BOARDS.get(), CEDAR_BOOKSHELF.get(), CHISELED_CEDAR_BOOKSHELF.get(), CEDAR_LADDER.get(), CEDAR_BEEHIVE.get(), CEDAR_CHEST.get(), TRAPPED_CEDAR_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.CEDAR_PLANKS_FAMILY, EnvironmentalItemTags.CEDAR_LOGS, CEDAR_BOARDS.get(), CEDAR_LADDER.get(), Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY);
		planksFromLogs(consumer, PLUM_PLANKS.get(), EnvironmentalItemTags.PLUM_LOGS, 4);
		woodFromLogs(consumer, PLUM_WOOD.get(), PLUM_LOG.get());
		woodFromLogs(consumer, STRIPPED_PLUM_WOOD.get(), STRIPPED_PLUM_LOG.get());
		hangingSign(consumer, PLUM_HANGING_SIGNS.getFirst().get(), STRIPPED_PLUM_LOG.get());
		leafPileRecipes(consumer, PLUM_LEAVES.get(), PLUM_LEAF_PILE.get());
		leafPileRecipes(consumer, CHEERFUL_PLUM_LEAVES.get(), CHEERFUL_PLUM_LEAF_PILE.get());
		leafPileRecipes(consumer, MOODY_PLUM_LEAVES.get(), MOODY_PLUM_LEAF_PILE.get());
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.PLUM);
		WoodworksRecipeProvider.baseRecipes(consumer, PLUM_PLANKS.get(), PLUM_SLAB.get(), PLUM_BOARDS.get(), PLUM_BOOKSHELF.get(), CHISELED_PLUM_BOOKSHELF.get(), PLUM_LADDER.get(), PLUM_BEEHIVE.get(), PLUM_CHEST.get(), TRAPPED_PLUM_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY, EnvironmentalItemTags.PLUM_LOGS, PLUM_BOARDS.get(), PLUM_LADDER.get(), Environmental.MOD_ID);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY);
		planksFromLogs(consumer, WISTERIA_PLANKS.get(), EnvironmentalItemTags.WISTERIA_LOGS, 4);
		woodFromLogs(consumer, WISTERIA_WOOD.get(), WISTERIA_LOG.get());
		woodFromLogs(consumer, STRIPPED_WISTERIA_WOOD.get(), STRIPPED_WISTERIA_LOG.get());
		hangingSign(consumer, WISTERIA_HANGING_SIGNS.getFirst().get(), STRIPPED_WISTERIA_LOG.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PINK_HANGING_WISTERIA_LEAVES.get(), 3).define('#', PINK_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PINK_WISTERIA_LEAVES.get()), has(PINK_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BLUE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', BLUE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(BLUE_WISTERIA_LEAVES.get()), has(BLUE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURPLE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', PURPLE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(PURPLE_WISTERIA_LEAVES.get()), has(PURPLE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, WHITE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', WHITE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(WHITE_WISTERIA_LEAVES.get()), has(WHITE_WISTERIA_LEAVES.get())).save(consumer);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PINK_WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, BLUE_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, PURPLE_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WHITE_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.conditionalLeafPileRecipes(consumer, WISTERIA_LEAVES.get(), WISTERIA_LEAF_PILE.get(), Environmental.MOD_ID);
		BoatloadRecipeProvider.boatRecipes(consumer, EnvironmentalBoatTypes.WISTERIA);
		WoodworksRecipeProvider.baseRecipes(consumer, WISTERIA_PLANKS.get(), WISTERIA_SLAB.get(), WISTERIA_BOARDS.get(), WISTERIA_BOOKSHELF.get(), CHISELED_WISTERIA_BOOKSHELF.get(), WISTERIA_LADDER.get(), WISTERIA_BEEHIVE.get(), WISTERIA_CHEST.get(), TRAPPED_WISTERIA_CHEST.get(), Environmental.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY, EnvironmentalItemTags.WISTERIA_LOGS, WISTERIA_BOARDS.get(), WISTERIA_LADDER.get(), Environmental.MOD_ID);
	}
}