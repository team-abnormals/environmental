package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EnvironmentalRecipeProvider extends RecipeProvider {
	public static final ModLoadedCondition WOODWORKS_LOADED = new ModLoadedCondition("woodworks");
	public static final ModLoadedCondition BOATLOAD_LOADED = new ModLoadedCondition("boatload");
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");

	public static final OrCondition WOODEN_LADDERS = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_ladders"));
	public static final OrCondition WOODEN_BOOKSHELVES = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_bookshelves"));
	public static final OrCondition WOODEN_CHESTS = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_chests"));

	public static final QuarkFlagRecipeCondition LEAF_CARPETS = quarkFlag("leaf_carpet");
	public static final QuarkFlagRecipeCondition VERTICAL_PLANKS = quarkFlag("vertical_planks");
	public static final QuarkFlagRecipeCondition VERTICAL_SLABS = quarkFlag("vertical_slabs");
	public static final QuarkFlagRecipeCondition HEDGES = quarkFlag("hedges");
	public static final QuarkFlagRecipeCondition WOODEN_POSTS = quarkFlag("wooden_posts");
	public static final QuarkFlagRecipeCondition BERRY_SACK = quarkFlag("berry_sack");
	public static final QuarkFlagRecipeCondition APPLE_CRATE = quarkFlag("apple_crate");
	public static final AndCondition WOOD_TO_CHEST_RECIPES = new AndCondition(quarkFlag("variant_chests"), quarkFlag("wood_to_chest_recipes"));

	public EnvironmentalRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		oneToOneConversionRecipe(consumer, Items.LIGHT_BLUE_DYE, EnvironmentalBlocks.BLUE_DELPHINIUM.get(), "light_blue_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, EnvironmentalBlocks.PINK_DELPHINIUM.get(), "pink_dye", 2);
		oneToOneConversionRecipe(consumer, Items.WHITE_DYE, EnvironmentalBlocks.WHITE_DELPHINIUM.get(), "white_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), "purple_dye", 2);
		oneToOneConversionRecipe(consumer, Items.BLUE_DYE, EnvironmentalBlocks.BLUEBELL.get(), "blue_dye");
		oneToOneConversionRecipe(consumer, Items.LIME_DYE, EnvironmentalBlocks.DIANTHUS.get(), "lime_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, EnvironmentalBlocks.BIRD_OF_PARADISE.get(), "orange_dye", 2);
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, EnvironmentalBlocks.CARTWHEEL.get(), "pink_dye");
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, EnvironmentalBlocks.VIOLET.get(), "purple_dye");
		oneToOneConversionRecipe(consumer, Items.RED_DYE, EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), "red_dye");
		oneToOneConversionRecipe(consumer, Items.WHITE_DYE, EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), "white_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, EnvironmentalBlocks.TASSELFLOWER.get(), "purple_dye");
		oneToOneConversionRecipe(consumer, Items.MAGENTA_DYE, EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), "magenta_dye");
		oneToOneConversionRecipe(consumer, Items.ORANGE_DYE, EnvironmentalBlocks.ORANGE_HIBISCUS.get(), "orange_dye");
		oneToOneConversionRecipe(consumer, Items.PINK_DYE, EnvironmentalBlocks.PINK_HIBISCUS.get(), "pink_dye");
		oneToOneConversionRecipe(consumer, Items.YELLOW_DYE, EnvironmentalBlocks.YELLOW_HIBISCUS.get(), "yellow_dye");
		oneToOneConversionRecipe(consumer, Items.RED_DYE, EnvironmentalBlocks.RED_HIBISCUS.get(), "red_dye");
		oneToOneConversionRecipe(consumer, Items.PURPLE_DYE, EnvironmentalBlocks.PURPLE_HIBISCUS.get(), "purple_dye");

		// conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.HIBISCUS_LEAVES.get(), EnvironmentalBlocks.HIBISCUS_LEAF_PILE.get());
		leafCarpetRecipe(consumer, EnvironmentalBlocks.HIBISCUS_LEAVES.get(), EnvironmentalBlocks.HIBISCUS_LEAF_CARPET.get());

		foodCookingRecipes(consumer, EnvironmentalItems.DUCK.get(), EnvironmentalItems.COOKED_DUCK.get());
		foodCookingRecipes(consumer, EnvironmentalItems.VENISON.get(), EnvironmentalItems.COOKED_VENISON.get());
		conditionalNineBlockStorageRecipes(consumer, INCUBATION_LOADED, EnvironmentalItems.DUCK_EGG.get(), EnvironmentalBlocks.DUCK_EGG_CRATE.get());
		conditionalNineBlockStorageRecipes(consumer, APPLE_CRATE, EnvironmentalItems.CHERRIES.get(), EnvironmentalBlocks.CHERRY_CRATE.get());
		nineBlockStorageRecipes(consumer, EnvironmentalItems.CATTAIL_FLUFF.get(), EnvironmentalBlocks.CATTAIL_FLUFF_BLOCK.get());

		ShapelessRecipeBuilder.shapeless(Items.STRING).requires(EnvironmentalItems.CATTAIL_FLUFF.get(), 7).unlockedBy("has_cattail_seeds", has(EnvironmentalItems.CATTAIL_FLUFF.get())).save(consumer, getModConversionRecipeName(Items.STRING, EnvironmentalItems.CATTAIL_FLUFF.get()));
		ShapelessRecipeBuilder.shapeless(EnvironmentalItems.CHERRY_PIE.get()).requires(Ingredient.of(EnvironmentalItemTags.FRUITS_CHERRY), 3).requires(Items.SUGAR).requires(BlueprintItemTags.EGGS).unlockedBy("has_cherry", has(EnvironmentalItemTags.FRUITS_CHERRY)).save(consumer);

		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.GRASS_THATCH.get(), 4).define('W', Items.WHEAT).define('G', Blocks.GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_grass", has(Blocks.GRASS)).save(consumer);
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.GRASS_THATCH.get(), 6).define('W', Items.WHEAT).define('G', Blocks.TALL_GRASS).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_tall_grass", has(Blocks.TALL_GRASS)).save(consumer, getModConversionRecipeName(EnvironmentalBlocks.GRASS_THATCH.get(), Blocks.GRASS));
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.GRASS_THATCH.get(), 8).define('W', Items.WHEAT).define('G', EnvironmentalBlocks.GIANT_TALL_GRASS.get()).pattern("WG").pattern("GW").group("grass_thatch").unlockedBy("has_giant_tall_grass", has(EnvironmentalBlocks.GIANT_TALL_GRASS.get())).save(consumer, getModConversionRecipeName(EnvironmentalBlocks.GRASS_THATCH.get(), EnvironmentalBlocks.GIANT_TALL_GRASS.get()));
		generateRecipes(consumer, EnvironmentalBlockFamilies.GRASS_THATCH_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.GRASS_THATCH_FAMILY, EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get());

		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CATTAIL_THATCH.get(), 4).define('#', EnvironmentalBlocks.CATTAIL.get()).pattern("##").pattern("##").unlockedBy("has_cattail", has(EnvironmentalBlocks.CATTAIL.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.CATTAIL_THATCH_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.CATTAIL_THATCH_FAMILY, EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get());

		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 4).define('#', EnvironmentalBlocks.DUCKWEED.get()).pattern("##").pattern("##").unlockedBy("has_duckweed", has(EnvironmentalBlocks.DUCKWEED.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DUCKWEED_THATCH_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.DUCKWEED_THATCH_FAMILY, EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get());

		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.DIRT_BRICKS.get(), 4).define('#', Blocks.DIRT).pattern("##").pattern("##").unlockedBy(getHasName(Blocks.DIRT), has(Blocks.DIRT)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY, EnvironmentalBlocks.DIRT_BRICK_VERTICAL_SLAB.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_SLAB.get(), EnvironmentalBlocks.DIRT_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_STAIRS.get(), EnvironmentalBlocks.DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_WALL.get(), EnvironmentalBlocks.DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICKS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_SLAB.get(), Blocks.DIRT, 2);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_STAIRS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_BRICK_WALL.get(), Blocks.DIRT);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.DIRT_BRICK_VERTICAL_SLAB.get(), Blocks.DIRT, 2);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.DIRT_BRICK_VERTICAL_SLAB.get(), EnvironmentalBlocks.DIRT_BRICKS.get(), 2);
		
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.DIRT_TILES.get(), 4).define('#', EnvironmentalBlocks.DIRT_BRICKS.get()).pattern("##").pattern("##").unlockedBy(getHasName(EnvironmentalBlocks.DIRT_BRICKS.get()), has(EnvironmentalBlocks.DIRT_BRICKS.get())).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.DIRT_TILE_FAMILY, EnvironmentalBlocks.DIRT_TILE_VERTICAL_SLAB.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_SLAB.get(), EnvironmentalBlocks.DIRT_TILES.get(), 2);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_STAIRS.get(), EnvironmentalBlocks.DIRT_TILES.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_WALL.get(), EnvironmentalBlocks.DIRT_TILES.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILES.get(), EnvironmentalBlocks.DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_SLAB.get(), EnvironmentalBlocks.DIRT_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_STAIRS.get(), EnvironmentalBlocks.DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_WALL.get(), EnvironmentalBlocks.DIRT_BRICKS.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILES.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_SLAB.get(), Blocks.DIRT, 2);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_STAIRS.get(), Blocks.DIRT);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.DIRT_TILE_WALL.get(), Blocks.DIRT);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.DIRT_TILE_VERTICAL_SLAB.get(), Blocks.DIRT, 2);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.DIRT_TILE_VERTICAL_SLAB.get(), EnvironmentalBlocks.DIRT_BRICKS.get(), 2);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.DIRT_TILE_VERTICAL_SLAB.get(), EnvironmentalBlocks.DIRT_TILES.get(), 2);

		ShapedRecipeBuilder.shaped(Blocks.MUD).define('#', EnvironmentalItems.MUD_BALL.get()).pattern("##").pattern("##").unlockedBy("has_mud_ball", has(EnvironmentalItems.MUD_BALL.get())).save(consumer, new ResourceLocation(Environmental.MOD_ID, RecipeBuilder.getDefaultRecipeId(Blocks.MUD).getPath()));
		oneToOneConversionRecipeBuilder(EnvironmentalItems.MUD_BALL.get(), Blocks.MUD, 4).group("mud_ball").save(consumer);
		ShapelessRecipeBuilder.shapeless(EnvironmentalItems.MUD_BALL.get(), 16).requires(BlueprintItemTags.BUCKETS_WATER).requires(Blocks.DIRT, 4).group("mud_ball").unlockedBy("has_dirt", has(Blocks.DIRT)).save(consumer, getModConversionRecipeName(EnvironmentalItems.MUD_BALL.get(), Blocks.DIRT));
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.SLABFISH_EFFIGY.get()).define('#', Blocks.MUD_BRICKS).define('S', Blocks.MUD_BRICK_SLAB).pattern(" S ").pattern("S#S").unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS)).save(consumer);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PACKED_MUD), EnvironmentalBlocks.SMOOTH_MUD.get().asItem(), 0.1F, 200).unlockedBy("has_packed_mud", has(Blocks.PACKED_MUD)).save(consumer);
		generateRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUD_FAMILY);
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.SMOOTH_MUD_FAMILY, EnvironmentalBlocks.SMOOTH_MUD_VERTICAL_SLAB.get());
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.SMOOTH_MUD_SLAB.get(), EnvironmentalBlocks.SMOOTH_MUD.get(), 2);
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, EnvironmentalBlocks.SMOOTH_MUD_VERTICAL_SLAB.get(), EnvironmentalBlocks.SMOOTH_MUD.get(), 2);
		chiseled(consumer, EnvironmentalBlocks.CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICK_SLAB);
		stonecutterResultFromBase(consumer, EnvironmentalBlocks.CHISELED_MUD_BRICKS.get(), Blocks.MUD_BRICKS);

		ShapedRecipeBuilder.shaped(Blocks.BROWN_WOOL).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("##").pattern("##").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer, new ResourceLocation(Environmental.MOD_ID, getItemName(Blocks.BROWN_WOOL)));
		nineBlockStorageRecipes(consumer, EnvironmentalItems.YAK_HAIR.get(), EnvironmentalBlocks.YAK_HAIR_BLOCK.get());
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.YAK_HAIR_RUG.get()).define('#', EnvironmentalItems.YAK_HAIR.get()).pattern("###").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);
		ShapedRecipeBuilder.shaped(EnvironmentalItems.YAK_PANTS.get()).define('#', Items.LEATHER).define('B', EnvironmentalBlocks.YAK_HAIR_BLOCK.get()).pattern("BBB").pattern("# #").pattern("# #").unlockedBy("has_yak_hair", has(EnvironmentalItems.YAK_HAIR.get())).save(consumer);

		generateRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY);
		planksFromLogs(consumer, EnvironmentalBlocks.WILLOW_PLANKS.get(), EnvironmentalItemTags.WILLOW_LOGS);
		woodFromLogs(consumer, EnvironmentalBlocks.WILLOW_WOOD.get(), EnvironmentalBlocks.WILLOW_LOG.get());
		woodFromLogs(consumer, EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get(), EnvironmentalBlocks.STRIPPED_WILLOW_LOG.get());
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 3).define('#', EnvironmentalBlocks.WILLOW_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(EnvironmentalBlocks.WILLOW_LEAVES.get()), has(EnvironmentalBlocks.WILLOW_LEAVES.get())).save(consumer);
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.WILLOW_LEAVES.get(), EnvironmentalBlocks.WILLOW_LEAF_PILE.get());
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY, EnvironmentalBlocks.WILLOW_VERTICAL_SLAB.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.WILLOW_LEAVES.get(), EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), EnvironmentalBlocks.WILLOW_HEDGE.get(), EnvironmentalItemTags.WILLOW_LOGS);
		woodenBoat(consumer, EnvironmentalItems.WILLOW_BOAT.getFirst().get(), EnvironmentalBlocks.WILLOW_PLANKS.get());
		chestBoat(consumer, EnvironmentalItems.WILLOW_BOAT.getSecond().get(), EnvironmentalItems.WILLOW_BOAT.getFirst().get());
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapelessRecipeBuilder.shapeless(EnvironmentalItems.WILLOW_FURNACE_BOAT.get()).requires(Blocks.FURNACE).requires(EnvironmentalItems.WILLOW_BOAT.getFirst().get()).group("furnace_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalItems.LARGE_WILLOW_BOAT.get()).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).define('B', EnvironmentalItems.WILLOW_BOAT.getFirst().get()).pattern("#B#").pattern("###").group("large_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_BOARDS.get(), 3).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(EnvironmentalBlocks.WILLOW_PLANKS.get()), has(EnvironmentalBlocks.WILLOW_PLANKS.get())));
		//conditionalRecipe(consumer, WOODEN_BOOKSHELVES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_BOOKSHELF.get()).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK)));
		//conditionalRecipe(consumer, WOODEN_LADDERS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_LADDER.get(), 4).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_BEEHIVE.get()).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB)));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_CHESTS.getFirst().get()).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.WILLOW_CHESTS.getSecond().get()).requires(EnvironmentalBlocks.WILLOW_CHESTS.getFirst().get()).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK)));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get(), 3).define('#', EnvironmentalBlocks.WILLOW_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(EnvironmentalBlocks.WILLOW_PLANKS.get()), has(EnvironmentalBlocks.WILLOW_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get()).requires(EnvironmentalBlocks.WILLOW_PLANKS.get()).unlockedBy(getHasName(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get()), has(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get()) + "_revert"));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_POST.get(), 8).define('#', EnvironmentalBlocks.WILLOW_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.WILLOW_WOOD.get()), has(EnvironmentalBlocks.WILLOW_WOOD.get())));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.STRIPPED_WILLOW_POST.get(), 8).define('#', EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get()), has(EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get())));
		conditionalRecipe(consumer, WOOD_TO_CHEST_RECIPES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WILLOW_CHESTS.getFirst().get(), 4).define('#', EnvironmentalItemTags.WILLOW_LOGS).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.WILLOW_CHESTS.getFirst().get()) + "_wood"));

		generateRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		planksFromLogs(consumer, EnvironmentalBlocks.PINE_PLANKS.get(), EnvironmentalItemTags.PINE_LOGS);
		woodFromLogs(consumer, EnvironmentalBlocks.PINE_WOOD.get(), EnvironmentalBlocks.PINE_LOG.get());
		woodFromLogs(consumer, EnvironmentalBlocks.STRIPPED_PINE_WOOD.get(), EnvironmentalBlocks.STRIPPED_PINE_LOG.get());
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.PINE_LEAVES.get(), EnvironmentalBlocks.PINE_LEAF_PILE.get());
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY, EnvironmentalBlocks.PINE_VERTICAL_SLAB.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.PINE_LEAVES.get(), EnvironmentalBlocks.PINE_LEAF_CARPET.get(), EnvironmentalBlocks.PINE_HEDGE.get(), EnvironmentalItemTags.PINE_LOGS);
		woodenBoat(consumer, EnvironmentalItems.PINE_BOAT.getFirst().get(), EnvironmentalBlocks.PINE_PLANKS.get());
		chestBoat(consumer, EnvironmentalItems.PINE_BOAT.getSecond().get(), EnvironmentalItems.PINE_BOAT.getFirst().get());
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapelessRecipeBuilder.shapeless(EnvironmentalItems.PINE_FURNACE_BOAT.get()).requires(Blocks.FURNACE).requires(EnvironmentalItems.PINE_BOAT.getFirst().get()).group("furnace_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalItems.LARGE_PINE_BOAT.get()).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).define('B', EnvironmentalItems.PINE_BOAT.getFirst().get()).pattern("#B#").pattern("###").group("large_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_BOARDS.get(), 3).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(EnvironmentalBlocks.PINE_PLANKS.get()), has(EnvironmentalBlocks.PINE_PLANKS.get())));
		//conditionalRecipe(consumer, WOODEN_BOOKSHELVES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_BOOKSHELF.get()).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK)));
		//conditionalRecipe(consumer, WOODEN_LADDERS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_LADDER.get(), 4).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_BEEHIVE.get()).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB)));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_CHESTS.getFirst().get()).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.PINE_CHESTS.getSecond().get()).requires(EnvironmentalBlocks.PINE_CHESTS.getFirst().get()).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK)));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.VERTICAL_PINE_PLANKS.get(), 3).define('#', EnvironmentalBlocks.PINE_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(EnvironmentalBlocks.PINE_PLANKS.get()), has(EnvironmentalBlocks.PINE_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.VERTICAL_PINE_PLANKS.get()).requires(EnvironmentalBlocks.PINE_PLANKS.get()).unlockedBy(getHasName(EnvironmentalBlocks.VERTICAL_PINE_PLANKS.get()), has(EnvironmentalBlocks.VERTICAL_PINE_PLANKS.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.VERTICAL_PINE_PLANKS.get()) + "_revert"));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_POST.get(), 8).define('#', EnvironmentalBlocks.PINE_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.PINE_WOOD.get()), has(EnvironmentalBlocks.PINE_WOOD.get())));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.STRIPPED_PINE_POST.get(), 8).define('#', EnvironmentalBlocks.STRIPPED_PINE_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.STRIPPED_PINE_WOOD.get()), has(EnvironmentalBlocks.STRIPPED_PINE_WOOD.get())));
		conditionalRecipe(consumer, WOOD_TO_CHEST_RECIPES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINE_CHESTS.getFirst().get(), 4).define('#', EnvironmentalItemTags.PINE_LOGS).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.PINE_CHESTS.getFirst().get()) + "_wood"));

		ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.WAXED_PINECONE.get()).requires(EnvironmentalBlocks.PINECONE.get()).requires(Items.HONEYCOMB).unlockedBy(getHasName(EnvironmentalBlocks.PINECONE.get()), has(EnvironmentalBlocks.PINECONE.get())).save(consumer, getConversionRecipeName(EnvironmentalBlocks.WAXED_PINECONE.get(), Items.HONEYCOMB));

		generateRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY);
		planksFromLogs(consumer, EnvironmentalBlocks.WISTERIA_PLANKS.get(), EnvironmentalItemTags.WISTERIA_LOGS);
		woodFromLogs(consumer, EnvironmentalBlocks.WISTERIA_WOOD.get(), EnvironmentalBlocks.WISTERIA_LOG.get());
		woodFromLogs(consumer, EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get(), EnvironmentalBlocks.STRIPPED_WISTERIA_LOG.get());
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 3).define('#', EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get()), has(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get()), has(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get()), has(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get())).save(consumer);
		ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 3).define('#', EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get()).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get()), has(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get())).save(consumer);
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get());
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get());
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get());
		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get());
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY, EnvironmentalBlocks.WISTERIA_VERTICAL_SLAB.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), EnvironmentalBlocks.PINK_WISTERIA_HEDGE.get(), EnvironmentalItemTags.WISTERIA_LOGS);
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), EnvironmentalBlocks.BLUE_WISTERIA_HEDGE.get(), EnvironmentalItemTags.WISTERIA_LOGS);
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), EnvironmentalBlocks.PURPLE_WISTERIA_HEDGE.get(), EnvironmentalItemTags.WISTERIA_LOGS);
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), EnvironmentalBlocks.WHITE_WISTERIA_HEDGE.get(), EnvironmentalItemTags.WISTERIA_LOGS);
		woodenBoat(consumer, EnvironmentalItems.WISTERIA_BOAT.getFirst().get(), EnvironmentalBlocks.WISTERIA_PLANKS.get());
		chestBoat(consumer, EnvironmentalItems.WISTERIA_BOAT.getSecond().get(), EnvironmentalItems.WISTERIA_BOAT.getFirst().get());
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapelessRecipeBuilder.shapeless(EnvironmentalItems.WISTERIA_FURNACE_BOAT.get()).requires(Blocks.FURNACE).requires(EnvironmentalItems.WISTERIA_BOAT.getFirst().get()).group("furnace_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalItems.LARGE_WISTERIA_BOAT.get()).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).define('B', EnvironmentalItems.WISTERIA_BOAT.getFirst().get()).pattern("#B#").pattern("###").group("large_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_BOARDS.get(), 3).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(EnvironmentalBlocks.WISTERIA_PLANKS.get()), has(EnvironmentalBlocks.WISTERIA_PLANKS.get())));
		//conditionalRecipe(consumer, WOODEN_BOOKSHELVES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_BOOKSHELF.get()).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK)));
		//conditionalRecipe(consumer, WOODEN_LADDERS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_LADDER.get(), 4).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_BEEHIVE.get()).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB)));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_CHESTS.getFirst().get()).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.WISTERIA_CHEST.getSecond().get()).requires(EnvironmentalBlocks.WISTERIA_CHESTS.getFirst().get()).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK)));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get(), 3).define('#', EnvironmentalBlocks.WISTERIA_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(EnvironmentalBlocks.WISTERIA_PLANKS.get()), has(EnvironmentalBlocks.WISTERIA_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get()).requires(EnvironmentalBlocks.WISTERIA_PLANKS.get()).unlockedBy(getHasName(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get()), has(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get()) + "_revert"));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_POST.get(), 8).define('#', EnvironmentalBlocks.WISTERIA_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.WISTERIA_WOOD.get()), has(EnvironmentalBlocks.WISTERIA_WOOD.get())));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.STRIPPED_WISTERIA_POST.get(), 8).define('#', EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get()), has(EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get())));
		conditionalRecipe(consumer, WOOD_TO_CHEST_RECIPES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.WISTERIA_CHESTS.getFirst().get(), 4).define('#', EnvironmentalItemTags.WISTERIA_LOGS).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.WISTERIA_CHESTS.getFirst().get()) + "_wood"));

		//conditionalLeafPileRecipes(consumer, EnvironmentalBlocks.WISTERIA_LEAVES.get(), EnvironmentalBlocks.WISTERIA_LEAF_PILE.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.WISTERIA_LEAVES.get(), EnvironmentalBlocks.WISTERIA_LEAF_CARPET.get(), EnvironmentalBlocks.WISTERIA_HEDGE.get(), EnvironmentalItemTags.WISTERIA_LOGS);

		generateRecipes(consumer, EnvironmentalBlockFamilies.CHERRY_PLANKS_FAMILY);
		planksFromLogs(consumer, EnvironmentalBlocks.CHERRY_PLANKS.get(), EnvironmentalItemTags.CHERRY_LOGS);
		woodFromLogs(consumer, EnvironmentalBlocks.CHERRY_WOOD.get(), EnvironmentalBlocks.CHERRY_LOG.get());
		woodFromLogs(consumer, EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get(), EnvironmentalBlocks.STRIPPED_CHERRY_LOG.get());
		leafPileRecipes(consumer, EnvironmentalBlocks.CHERRY_LEAVES.get(), EnvironmentalBlocks.CHERRY_LEAF_PILE.get());
		verticalSlabRecipes(consumer, EnvironmentalBlockFamilies.CHERRY_PLANKS_FAMILY, EnvironmentalBlocks.CHERRY_VERTICAL_SLAB.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.CHERRY_LEAVES.get(), EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), EnvironmentalBlocks.CHERRY_HEDGE.get(), EnvironmentalItemTags.CHERRY_LOGS);
		woodenBoat(consumer, EnvironmentalItems.CHERRY_BOAT.getFirst().get(), EnvironmentalBlocks.CHERRY_PLANKS.get());
		chestBoat(consumer, EnvironmentalItems.CHERRY_BOAT.getSecond().get(), EnvironmentalItems.CHERRY_BOAT.getFirst().get());
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapelessRecipeBuilder.shapeless(EnvironmentalItems.CHERRY_FURNACE_BOAT.get()).requires(Blocks.FURNACE).requires(EnvironmentalItems.CHERRY_BOAT.getFirst().get()).group("furnace_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalItems.LARGE_CHERRY_BOAT.get()).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).define('B', EnvironmentalItems.CHERRY_BOAT.getFirst().get()).pattern("#B#").pattern("###").group("large_boat").unlockedBy("has_boat", has(ItemTags.BOATS)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_BOARDS.get(), 3).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(EnvironmentalBlocks.CHERRY_PLANKS.get()), has(EnvironmentalBlocks.CHERRY_PLANKS.get())));
		//conditionalRecipe(consumer, WOODEN_BOOKSHELVES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_BOOKSHELF.get()).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK)));
		//conditionalRecipe(consumer, WOODEN_LADDERS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_LADDER.get(), 4).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_BEEHIVE.get()).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB)));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_CHESTS.getFirst().get()).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.CHERRY_CHESTS.getSecond().get()).requires(EnvironmentalBlocks.CHERRY_CHESTS.getFirst().get()).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK)));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get(), 3).define('#', EnvironmentalBlocks.CHERRY_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(EnvironmentalBlocks.CHERRY_PLANKS.get()), has(EnvironmentalBlocks.CHERRY_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapelessRecipeBuilder.shapeless(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get()).requires(EnvironmentalBlocks.CHERRY_PLANKS.get()).unlockedBy(getHasName(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get()), has(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get()) + "_revert"));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_POST.get(), 8).define('#', EnvironmentalBlocks.CHERRY_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.CHERRY_WOOD.get()), has(EnvironmentalBlocks.CHERRY_WOOD.get())));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.STRIPPED_CHERRY_POST.get(), 8).define('#', EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get()), has(EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get())));
		conditionalRecipe(consumer, WOOD_TO_CHEST_RECIPES, ShapedRecipeBuilder.shaped(EnvironmentalBlocks.CHERRY_CHESTS.getFirst().get(), 4).define('#', EnvironmentalItemTags.CHERRY_LOGS).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(EnvironmentalBlocks.CHERRY_CHESTS.getFirst().get()) + "_wood"));

		leafPileRecipes(consumer, EnvironmentalBlocks.CHEERFUL_CHERRY_LEAVES.get(), EnvironmentalBlocks.CHEERFUL_CHERRY_LEAF_PILE.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.CHEERFUL_CHERRY_LEAVES.get(), EnvironmentalBlocks.CHEERFUL_CHERRY_LEAF_CARPET.get(), EnvironmentalBlocks.CHEERFUL_CHERRY_HEDGE.get(), EnvironmentalItemTags.CHERRY_LOGS);
		
		leafPileRecipes(consumer, EnvironmentalBlocks.MOODY_CHERRY_LEAVES.get(), EnvironmentalBlocks.MOODY_CHERRY_LEAF_PILE.get());
		leafCarpetAndHedgeRecipe(consumer, EnvironmentalBlocks.MOODY_CHERRY_LEAVES.get(), EnvironmentalBlocks.MOODY_CHERRY_LEAF_CARPET.get(), EnvironmentalBlocks.MOODY_CHERRY_HEDGE.get(), EnvironmentalItemTags.CHERRY_LOGS);

		ShapedRecipeBuilder.shaped(Blocks.CAKE).define('A', BlueprintItemTags.BUCKETS_MILK).define('B', Items.SUGAR).define('C', Items.WHEAT).define('E', BlueprintItemTags.EGGS).pattern("AAA").pattern("BEB").pattern("CCC").unlockedBy("has_egg", has(Items.EGG)).save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_PIE).requires(BlueprintItemTags.PUMPKINS).requires(Items.SUGAR).requires(BlueprintItemTags.EGGS).unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).unlockedBy("has_pumpkin", has(BlueprintItemTags.PUMPKINS)).save(consumer);
	}

	public static QuarkFlagRecipeCondition quarkFlag(String flag) {
		return new QuarkFlagRecipeCondition(new ResourceLocation(Blueprint.MOD_ID, "quark_flag"), flag);
	}

	public static void verticalSlabRecipes(Consumer<FinishedRecipe> consumer, BlockFamily family, ItemLike verticalSlab) {
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapedRecipeBuilder.shaped(verticalSlab, 6).define('#', family.get(Variant.SLAB)).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(family.getBaseBlock()), has(family.getBaseBlock())));
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapelessRecipeBuilder.shapeless(family.get(Variant.SLAB)).requires(verticalSlab).unlockedBy(getHasName(verticalSlab), has(verticalSlab)), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(verticalSlab) + "_revert"));
	}

	public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike storage) {
		nineBlockStorageRecipes(consumer, item, storage, Environmental.MOD_ID + ":" + getSimpleRecipeName(storage), null, Environmental.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group) {
		oneToOneConversionRecipe(consumer, output, input, group, 1);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group, int count) {
		ShapelessRecipeBuilder.shapeless(output, count).requires(input).group(group).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input));
	}

	public static ShapelessRecipeBuilder oneToOneConversionRecipeBuilder(ItemLike output, ItemLike input, int count) {
		return ShapelessRecipeBuilder.shapeless(output, count).requires(input).unlockedBy(getHasName(input), has(input));
	}

	public static void foodCookingRecipes(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, 0.35F, 200).unlockedBy(getHasName(input), has(input)).save(consumer);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), output, 0.35F, 100).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_smoking");
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), output, 0.35F, 600).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_campfire_cooking");
	}

	public static void leafPileRecipes(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafPile) {
		ShapelessRecipeBuilder.shapeless(leafPile, 4).requires(leaves).group("leaf_pile").unlockedBy(getHasName(leaves), has(leaves)).save(consumer);
		ShapedRecipeBuilder.shaped(leaves).define('#', leafPile).pattern("##").pattern("##").group("leaves").unlockedBy(getHasName(leafPile), has(leafPile)).save(consumer, getModConversionRecipeName(leaves, leafPile));
	}

	public static void conditionalLeafPileRecipes(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafPile) {
		conditionalRecipe(consumer, WOODWORKS_LOADED, ShapelessRecipeBuilder.shapeless(leafPile, 4).requires(leaves).group("leaf_pile").unlockedBy(getHasName(leaves), has(leaves)));
		conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(leaves).define('#', leafPile).pattern("##").pattern("##").group("leaves").unlockedBy(getHasName(leafPile), has(leafPile)), getModConversionRecipeName(leaves, leafPile));
	}

	private static void chestBoat(Consumer<FinishedRecipe> consumer, ItemLike chestBoat, ItemLike boat) {
		ShapelessRecipeBuilder.shapeless(chestBoat).requires(Tags.Items.CHESTS_WOODEN).requires(boat).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS)).save(consumer);
	}

	public static void leafCarpetRecipe(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafCarpet) {
		conditionalRecipe(consumer, LEAF_CARPETS, ShapedRecipeBuilder.shaped(leafCarpet, 3).define('#', leaves).pattern("##").group("leaf_carpet").unlockedBy(getHasName(leaves), has(leaves)));
	}

	public static void leafCarpetAndHedgeRecipe(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafCarpet, ItemLike hedge, TagKey<Item> logTag) {
		conditionalRecipe(consumer, LEAF_CARPETS, ShapedRecipeBuilder.shaped(leafCarpet, 3).define('#', leaves).pattern("##").group("leaf_carpet").unlockedBy(getHasName(leaves), has(leaves)));
		conditionalRecipe(consumer, HEDGES, ShapedRecipeBuilder.shaped(hedge, 2).define('#', leaves).define('L', logTag).pattern("#").pattern("L").group("hedge").unlockedBy(getHasName(leaves), has(leaves)));
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input) {
		stonecutterResultFromBase(consumer, output, input, 1);
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, count).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input) + "_stonecutting");
	}

	protected static ResourceLocation getModConversionRecipeName(ItemLike output, ItemLike input) {
		return new ResourceLocation(Environmental.MOD_ID, getConversionRecipeName(output, input));
	}

	public static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike item, ItemLike storage) {
		conditionalNineBlockStorageRecipes(consumer, condition, item, storage, Environmental.MOD_ID + ":" + getSimpleRecipeName(storage), null, Environmental.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	protected static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike item, ItemLike storage, String storageLocation, @Nullable String itemGroup, String itemLocation, @Nullable String storageGroup) {
		conditionalRecipe(consumer, condition, ShapelessRecipeBuilder.shapeless(item, 9).requires(storage).group(storageGroup).unlockedBy(getHasName(storage), has(storage)), new ResourceLocation(itemLocation));
		conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(storage).define('#', item).pattern("###").pattern("###").pattern("###").group(itemGroup).unlockedBy(getHasName(item), has(item)), new ResourceLocation(storageLocation));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe) {
		conditionalRecipe(consumer, condition, recipe, RecipeBuilder.getDefaultRecipeId(recipe.getResult()));
	}

	public static void conditionalStonecuttingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike output, ItemLike input, int count) {
		conditionalRecipe(consumer, condition, SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, count).unlockedBy(getHasName(input), has(input)), new ResourceLocation(Environmental.MOD_ID, getConversionRecipeName(output, input) + "_stonecutting"));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe, ResourceLocation id) {
		ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(consumer, id);
	}
}