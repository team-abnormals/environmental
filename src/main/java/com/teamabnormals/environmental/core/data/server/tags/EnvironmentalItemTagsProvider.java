package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemTagsProvider extends ItemTagsProvider {

	public EnvironmentalItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
		super(generator, blockTags, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.copy(EnvironmentalBlockTags.WILLOW_LOGS, EnvironmentalItemTags.WILLOW_LOGS);
		this.copy(EnvironmentalBlockTags.WISTERIA_LOGS, EnvironmentalItemTags.WISTERIA_LOGS);
		this.copy(EnvironmentalBlockTags.CHERRY_LOGS, EnvironmentalItemTags.CHERRY_LOGS);
		this.tag(EnvironmentalItemTags.DUCK_FOOD).add(Items.SEAGRASS, EnvironmentalItems.DUCKWEED.get()).addOptionalTag(new ResourceLocation("forge", "crops/rice"));
		this.tag(EnvironmentalItemTags.DEER_FOOD).add(Items.APPLE, Items.SWEET_BERRIES).addTag(EnvironmentalItemTags.FRUITS_CHERRY).addOptionalTag(new ResourceLocation("forge", "fruits/tomato")).addOptionalTag(new ResourceLocation("forge", "vegetables/tomato")).addOptionalTag(new ResourceLocation("forge", "fruits/strawberry"));
		this.tag(EnvironmentalItemTags.DEER_PLANTABLES).addTag(ItemTags.SMALL_FLOWERS).addTag(ItemTags.TALL_FLOWERS);
		this.tag(EnvironmentalItemTags.DEER_TEMPT_ITEMS).add(Items.MELON_SLICE, Items.GLISTERING_MELON_SLICE).addTag(EnvironmentalItemTags.DEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES);
		this.tag(EnvironmentalItemTags.REINDEER_FOOD).add(EnvironmentalBlocks.CUP_LICHEN.get().asItem());
		this.tag(EnvironmentalItemTags.REINDEER_TEMPT_ITEMS).add(Items.MELON_SLICE, Items.GLISTERING_MELON_SLICE).addTag(EnvironmentalItemTags.REINDEER_FOOD).addTag(EnvironmentalItemTags.DEER_PLANTABLES);
		this.tag(EnvironmentalItemTags.SLABFISH_FOOD).addTag(ItemTags.FISHES);
		this.tag(EnvironmentalItemTags.SLABFISH_TAME_ITEMS).add(Items.TROPICAL_FISH);
		this.tag(EnvironmentalItemTags.SLABFISH_SNACKS).add(Items.CHORUS_FRUIT).addOptional(new ResourceLocation("atmospheric", "passionfruit")).addOptional(new ResourceLocation("atmospheric", "shimmering_passionfruit")).addOptional(new ResourceLocation("endergetic", "bolloom_fruit"));
		this.tag(EnvironmentalItemTags.YAK_FOOD).add(Items.WHEAT);

		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.copy(BlockTags.SAND, ItemTags.SAND);
		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.WALLS, ItemTags.WALLS);
		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		this.tag(ItemTags.SMALL_FLOWERS).add(EnvironmentalBlocks.CARTWHEEL.get().asItem(), EnvironmentalBlocks.BLUEBELL.get().asItem(), EnvironmentalBlocks.VIOLET.get().asItem(), EnvironmentalBlocks.DIANTHUS.get().asItem(), EnvironmentalBlocks.RED_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().asItem(), EnvironmentalBlocks.YELLOW_HIBISCUS.get().asItem(), EnvironmentalBlocks.ORANGE_HIBISCUS.get().asItem(), EnvironmentalBlocks.RED_HIBISCUS.get().asItem(), EnvironmentalBlocks.PINK_HIBISCUS.get().asItem(), EnvironmentalBlocks.MAGENTA_HIBISCUS.get().asItem(), EnvironmentalBlocks.PURPLE_HIBISCUS.get().asItem());
		this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
		this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
		this.copy(BlockTags.OVERWORLD_NATURAL_LOGS, ItemTags.OVERWORLD_NATURAL_LOGS);
		this.copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
		this.tag(ItemTags.BOATS).add(WILLOW_BOAT.getFirst().get(), WISTERIA_BOAT.getFirst().get(), CHERRY_BOAT.getFirst().get());
		this.tag(ItemTags.CHEST_BOATS).add(WILLOW_BOAT.getSecond().get(), WISTERIA_BOAT.getSecond().get(), CHERRY_BOAT.getSecond().get());
		this.tag(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_LEAVING_HOME.get(), MUSIC_DISC_SLABRAVE.get());
		this.tag(ItemTags.FISHES).add(KOI.get());

		this.copy(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
		this.copy(Tags.Blocks.CHESTS_TRAPPED, Tags.Items.CHESTS_TRAPPED);
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
		this.tag(Tags.Items.BOOKSHELVES).add(WILLOW_BOOKSHELF.get().asItem(), WISTERIA_BOOKSHELF.get().asItem(), CHERRY_BOOKSHELF.get().asItem());
		this.tag(EnvironmentalItemTags.FRUITS).addTag(EnvironmentalItemTags.FRUITS_CHERRY);
		this.tag(EnvironmentalItemTags.FRUITS_CHERRY).add(CHERRIES.get());
		this.tag(EnvironmentalItemTags.RAW_DUCK).add(DUCK.get());
		this.tag(EnvironmentalItemTags.COOKED_DUCK).add(COOKED_DUCK.get());
		this.tag(EnvironmentalItemTags.RAW_VENISON).add(VENISON.get());
		this.tag(EnvironmentalItemTags.COOKED_VENISON).add(COOKED_VENISON.get());
		this.tag(EnvironmentalItemTags.RAW_FISHES).addTag(EnvironmentalItemTags.RAW_FISHES_KOI);
		this.tag(EnvironmentalItemTags.RAW_FISHES_KOI).add(KOI.get());
		this.tag(BlueprintItemTags.EGGS).add(DUCK_EGG.get());

		this.copy(BlueprintBlockTags.LADDERS, BlueprintItemTags.LADDERS);
		this.copy(BlueprintBlockTags.HEDGES, BlueprintItemTags.HEDGES);
		this.copy(BlueprintBlockTags.VERTICAL_SLABS, BlueprintItemTags.VERTICAL_SLABS);
		this.copy(BlueprintBlockTags.WOODEN_VERTICAL_SLABS, BlueprintItemTags.WOODEN_VERTICAL_SLABS);
		this.tag(BlueprintItemTags.BOATABLE_CHESTS).add(WILLOW_CHESTS.getFirst().get().asItem(), WISTERIA_CHESTS.getFirst().get().asItem(), CHERRY_CHESTS.getFirst().get().asItem());
		this.tag(BlueprintItemTags.REVERTABLE_CHESTS).add(WILLOW_CHESTS.getFirst().get().asItem(), WISTERIA_CHESTS.getFirst().get().asItem(), CHERRY_CHESTS.getFirst().get().asItem());
	}
}