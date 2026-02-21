package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.data.server.tags.BlueprintItemTagsProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemTagsProvider extends BlueprintItemTagsProvider {

	public EnvironmentalItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(Environmental.MOD_ID, output, provider, lookup, helper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTags(Provider provider) {
		this.copy(EnvironmentalBlockTags.WILLOW_LOGS, EnvironmentalItemTags.WILLOW_LOGS);
		this.copy(EnvironmentalBlockTags.PINE_LOGS, EnvironmentalItemTags.PINE_LOGS);
		this.copy(EnvironmentalBlockTags.CEDAR_LOGS, EnvironmentalItemTags.CEDAR_LOGS);
		this.copy(EnvironmentalBlockTags.WISTERIA_LOGS, EnvironmentalItemTags.WISTERIA_LOGS);
		this.copy(EnvironmentalBlockTags.PLUM_LOGS, EnvironmentalItemTags.PLUM_LOGS);
		this.tag(EnvironmentalItemTags.DUCK_FOOD).add(Items.SEAGRASS, EnvironmentalItems.DUCKWEED.get()).addOptionalTag(ResourceLocation.fromNamespaceAndPath("c", "crops/rice"));
		this.tag(EnvironmentalItemTags.DEER_FOOD).add(Items.SWEET_BERRIES).addTag(EnvironmentalItemTags.FOODS_CHERRY).addOptionalTag(ResourceLocation.fromNamespaceAndPath("c", "foods/strawberry"));
		this.tag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(ItemTags.SMALL_FLOWERS).addTag(ItemTags.TALL_FLOWERS);
		this.tag(EnvironmentalItemTags.DEER_CANNOT_PLANT);
		this.tag(EnvironmentalItemTags.DEER_TEMPT_ITEMS).addTag(EnvironmentalItemTags.DEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(EnvironmentalItemTags.DEER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.REINDEER_FOOD).add(EnvironmentalBlocks.CUP_LICHEN.get().asItem(), Items.CARROT);
		this.tag(EnvironmentalItemTags.REINDEER_TEMPT_ITEMS).addTag(EnvironmentalItemTags.REINDEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(EnvironmentalItemTags.DEER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.SLABFISH_FOOD).addTag(ItemTags.FISHES);
		this.tag(EnvironmentalItemTags.SLABFISH_TAME_ITEMS).add(Items.TROPICAL_FISH);
		this.tag(EnvironmentalItemTags.SLABFISH_SNACKS).add(Items.CHORUS_FRUIT).addOptional(ResourceLocation.fromNamespaceAndPath("atmospheric", "passion_fruit")).addOptional(ResourceLocation.fromNamespaceAndPath("atmospheric", "shimmering_passion_fruit")).addOptional(ResourceLocation.fromNamespaceAndPath("endergetic", "bolloom_fruit")).addOptional(ResourceLocation.fromNamespaceAndPath("caverns_and_chasms", "bejeweled_apple"));
		this.tag(EnvironmentalItemTags.YAK_FOOD).add(Items.WHEAT);
		this.tag(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS).addTag(ItemTags.SMALL_FLOWERS).addTag(ItemTags.SAPLINGS).addTag(Tags.Items.MUSHROOMS).add(Items.LILY_PAD, Items.DEAD_BUSH, Items.FERN, Items.BIG_DRIPLEAF, CATTAIL.get().asItem());
		this.tag(EnvironmentalItemTags.SPAWNS_ON_MUDDY_PIG).add(Items.BLUE_ORCHID, DIANTHUS.get().asItem(), Items.POPPY, Items.DANDELION, Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, CATTAIL.get().asItem(), Items.LILY_PAD);
		this.tag(EnvironmentalItemTags.MUDDY_PIG_DRYING_ITEMS).add(Items.WHEAT).addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "straw"));
		this.tag(EnvironmentalItemTags.PIG_TRUFFLE_ITEMS).add(Items.GOLDEN_CARROT);
		this.tag(ItemTags.PIG_FOOD).addTag(EnvironmentalItemTags.PIG_TRUFFLE_ITEMS);
		this.tag(EnvironmentalItemTags.DEER_FLOWER_ITEMS).add(Items.APPLE).addTag(EnvironmentalItemTags.DEER_STRONG_FLOWER_ITEMS).addTag(EnvironmentalItemTags.DEER_SUPER_FLOWER_ITEMS);
		this.tag(EnvironmentalItemTags.DEER_STRONG_FLOWER_ITEMS).add(Items.GOLDEN_APPLE);
		this.tag(EnvironmentalItemTags.DEER_SUPER_FLOWER_ITEMS).add(Items.ENCHANTED_GOLDEN_APPLE);
		this.tag(ItemTags.LEG_ARMOR).add(YAK_PANTS.get());

		this.copyWoodsetTags();
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
		this.copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.WALLS, ItemTags.WALLS);
		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.tag(ItemTags.SMALL_FLOWERS).add(EnvironmentalBlocks.CARTWHEEL.get().asItem(), EnvironmentalBlocks.BLUEBELL.get().asItem(), EnvironmentalBlocks.VIOLET.get().asItem(), EnvironmentalBlocks.DIANTHUS.get().asItem(), EnvironmentalBlocks.RED_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.TASSELFLOWER.get().asItem(), EnvironmentalBlocks.ESTUARY_MARIGOLD.get().asItem(), EnvironmentalBlocks.YELLOW_HIBISCUS.get().asItem(), EnvironmentalBlocks.ORANGE_HIBISCUS.get().asItem(), EnvironmentalBlocks.RED_HIBISCUS.get().asItem(), EnvironmentalBlocks.PINK_HIBISCUS.get().asItem(), EnvironmentalBlocks.MAGENTA_HIBISCUS.get().asItem(), EnvironmentalBlocks.PURPLE_HIBISCUS.get().asItem());
		this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
		this.copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
		this.tag(ItemTags.BOATS).add(WILLOW_BOAT.getFirst().get(), PINE_BOAT.getFirst().get(), CEDAR_BOAT.getFirst().get(), WISTERIA_BOAT.getFirst().get(), PLUM_BOAT.getFirst().get());
		this.tag(ItemTags.CHEST_BOATS).add(WILLOW_BOAT.getSecond().get(), PINE_BOAT.getSecond().get(), CEDAR_BOAT.getFirst().get(), WISTERIA_BOAT.getSecond().get(), PLUM_BOAT.getSecond().get());
		this.tag(BlueprintItemTags.FURNACE_BOATS).add(WILLOW_FURNACE_BOAT.get(), PINE_FURNACE_BOAT.get(), CEDAR_FURNACE_BOAT.get(), WISTERIA_FURNACE_BOAT.get(), PLUM_FURNACE_BOAT.get());
		this.tag(BlueprintItemTags.LARGE_BOATS).add(LARGE_WILLOW_BOAT.get(), LARGE_PINE_BOAT.get(), LARGE_CEDAR_BOAT.get(), LARGE_WISTERIA_BOAT.get(), LARGE_PLUM_BOAT.get());
		this.tag(Tags.Items.MUSIC_DISCS).add(MUSIC_DISC_LEAVING_HOME.get(), MUSIC_DISC_SLABRAVE.get());
		this.tag(ItemTags.TRIM_TEMPLATES).add(KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
		this.tag(ItemTags.TRIM_MATERIALS).add(BOG_IRON.get());
		this.tag(ItemTags.FISHES).add(KOI.get());

		this.copy(Tags.Blocks.GLASS_BLOCKS, Tags.Items.GLASS_BLOCKS);
		this.copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);

		this.copy(BlockTags.DIRT, ItemTags.DIRT);
		this.tag(EnvironmentalItemTags.CONVERTABLE_TO_MUD).add(Blocks.DIRT.asItem(), Blocks.COARSE_DIRT.asItem(), Blocks.ROOTED_DIRT.asItem());
		this.tag(Tags.Items.SANDSTONE_BLOCKS).add(MUDDY_SANDSTONE.get().asItem(), CUT_MUDDY_SANDSTONE.get().asItem(), CHISELED_MUDDY_SANDSTONE.get().asItem(), SMOOTH_MUDDY_SANDSTONE.get().asItem());
		this.tag(Tags.Items.SANDSTONE_STAIRS).add(MUDDY_SANDSTONE_STAIRS.get().asItem(), SMOOTH_MUDDY_SANDSTONE_STAIRS.get().asItem());
		this.tag(Tags.Items.SANDSTONE_SLABS).add(MUDDY_SANDSTONE_SLAB.get().asItem(), CUT_MUDDY_SANDSTONE_SLAB.get().asItem(), SMOOTH_MUDDY_SANDSTONE_SLAB.get().asItem());

		this.copy(EnvironmentalBlockTags.STORAGE_BLOCKS_CHERRY, EnvironmentalItemTags.STORAGE_BLOCKS_CHERRY);
		this.copy(EnvironmentalBlockTags.STORAGE_BLOCKS_PLUM, EnvironmentalItemTags.STORAGE_BLOCKS_PLUM);
		this.copy(EnvironmentalBlockTags.STORAGE_BLOCKS_DUCK_EGG, EnvironmentalItemTags.STORAGE_BLOCKS_DUCK_EGG);
		this.copy(EnvironmentalBlockTags.STORAGE_BLOCKS_CATTAIL_FLUFF, EnvironmentalItemTags.STORAGE_BLOCKS_CATTAIL_FLUFF);

		this.tag(Tags.Items.FOODS_FRUIT).addTags(EnvironmentalItemTags.FOODS_CHERRY, EnvironmentalItemTags.FOODS_PLUM);
		this.tag(EnvironmentalItemTags.FOODS_PLUM).add(PLUM.get());
		this.tag(EnvironmentalItemTags.FOODS_CHERRY).add(CHERRIES.get());

		this.tag(ItemTags.MEAT).add(DUCK.get(), COOKED_DUCK.get(), VENISON.get(), COOKED_VENISON.get());

		this.tag(Tags.Items.FOODS).add(TRUFFLE.get());
		this.tag(Tags.Items.FOODS_RAW_FISH).add(KOI.get());
		this.tag(Tags.Items.FOODS_RAW_MEAT).addTags(EnvironmentalItemTags.FOODS_RAW_DUCK, EnvironmentalItemTags.FOODS_RAW_VENISON);
		this.tag(Tags.Items.FOODS_COOKED_MEAT).addTags(EnvironmentalItemTags.FOODS_COOKED_DUCK, EnvironmentalItemTags.FOODS_COOKED_VENISON);
		this.tag(Tags.Items.FOODS_FOOD_POISONING).add(DUCK.get());

		this.tag(EnvironmentalItemTags.FOODS_RAW_DUCK).add(DUCK.get());
		this.tag(EnvironmentalItemTags.FOODS_COOKED_DUCK).add(COOKED_DUCK.get());
		this.tag(EnvironmentalItemTags.FOODS_RAW_VENISON).add(VENISON.get());
		this.tag(EnvironmentalItemTags.FOODS_COOKED_VENISON).add(COOKED_VENISON.get());

		this.tag(Tags.Items.EGGS).add(DUCK_EGG.get());

		this.tag(Tags.Items.BUCKETS_ENTITY_WATER).add(KOI_BUCKET.get(), SLABFISH_BUCKET.get());

		this.tag(Tags.Items.ANIMAL_FOODS).addTags(EnvironmentalItemTags.SLABFISH_FOOD, EnvironmentalItemTags.DUCK_FOOD, EnvironmentalItemTags.DEER_FOOD, EnvironmentalItemTags.REINDEER_FOOD, EnvironmentalItemTags.YAK_FOOD);
	}
}