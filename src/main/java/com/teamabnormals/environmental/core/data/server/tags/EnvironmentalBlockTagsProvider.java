package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockTagsProvider extends BlockTagsProvider {

	public EnvironmentalBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTags(Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(
				CATTAIL_SPROUT.get(), CATTAIL.get(), LARGE_LILY_PAD.get(), GIANT_LILY_PAD.get(), GIANT_TALL_GRASS.get(), MYCELIUM_SPROUTS.get(), CUP_LICHEN.get(), DWARF_SPRUCE.get(), DWARF_SPRUCE_PLANT.get(), DWARF_SPRUCE_TORCH.get(), DWARF_SPRUCE_PLANT_TORCH.get(), DWARF_SPRUCE_SOUL_TORCH.get(), DWARF_SPRUCE_PLANT_SOUL_TORCH.get(), DWARF_SPRUCE_REDSTONE_TORCH.get(), DWARF_SPRUCE_PLANT_REDSTONE_TORCH.get(), DWARF_SPRUCE_ENDER_TORCH.get(), DWARF_SPRUCE_PLANT_ENDER_TORCH.get(), DWARF_SPRUCE_CUPRIC_TORCH.get(), DWARF_SPRUCE_PLANT_CUPRIC_TORCH.get(),
				DUCK_EGG_CRATE.get(), YAK_HAIR_BLOCK.get(), YAK_HAIR_RUG.get(),
				CHERRY_CRATE.get(), PLUM_CRATE.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_HOE).add(
				WILLOW_LEAVES.get(), HANGING_WILLOW_LEAVES.get(), WILLOW_LEAF_PILE.get(),
				PINE_LEAVES.get(), PINE_LEAF_PILE.get(),
				WISTERIA_LEAVES.get(), WISTERIA_LEAF_PILE.get(),
				PINK_WISTERIA_LEAVES.get(), PINK_HANGING_WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAF_PILE.get(),
				BLUE_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAF_PILE.get(),
				PURPLE_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAF_PILE.get(),
				WHITE_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAF_PILE.get(),
				PLUM_LEAVES.get(), PLUM_LEAF_PILE.get(),
				CHEERFUL_PLUM_LEAVES.get(), CHEERFUL_PLUM_LEAF_PILE.get(),
				MOODY_PLUM_LEAVES.get(), MOODY_PLUM_LEAF_PILE.get(),
				DUCKWEED.get(), CATTAIL_FLUFF_BLOCK.get(),
				GRASS_THATCH.get(), GRASS_THATCH_SLAB.get(), GRASS_THATCH_STAIRS.get(),
				CATTAIL_THATCH.get(), CATTAIL_THATCH_SLAB.get(), CATTAIL_THATCH_STAIRS.get(),
				DUCKWEED_THATCH.get(), DUCKWEED_THATCH_SLAB.get(), DUCKWEED_THATCH_STAIRS.get(),
				HIBISCUS_LEAVES.get(), HIBISCUS_LEAF_PILE.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				SMOOTH_MUD.get(), SMOOTH_MUD_SLAB.get(), CHISELED_MUD_BRICKS.get(), SLABFISH_EFFIGY.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(DIRT_BRICKS.get(), DIRT_BRICK_STAIRS.get(), DIRT_BRICK_SLAB.get(), DIRT_BRICK_WALL.get(), DIRT_TILES.get(), DIRT_TILE_STAIRS.get(), DIRT_TILE_SLAB.get(), DIRT_TILE_WALL.get(), BURIED_TRUFFLE.get(), DIRT_PATH.get(), PODZOL_PATH.get(), MYCELIUM_PATH.get());

		this.tag(MINEABLE_WITH_KNIFE).add(YAK_HAIR_BLOCK.get(), YAK_HAIR_RUG.get());
		this.tag(COMPOST_ACTIVATORS).add(MYCELIUM_SPROUTS.get());

		this.tag(BlockTags.SLABS).add(GRASS_THATCH_SLAB.get(), CATTAIL_THATCH_SLAB.get(), DUCKWEED_THATCH_SLAB.get(), DIRT_BRICK_SLAB.get(), DIRT_TILE_SLAB.get(), SMOOTH_MUD_SLAB.get());
		this.tag(BlockTags.STAIRS).add(GRASS_THATCH_STAIRS.get(), CATTAIL_THATCH_STAIRS.get(), DUCKWEED_THATCH_STAIRS.get(), DIRT_BRICK_STAIRS.get(), DIRT_TILE_STAIRS.get());
		this.tag(BlockTags.WALLS).add(DIRT_BRICK_WALL.get(), DIRT_TILE_WALL.get());

		this.tag(BlockTags.DIRT).add(DIRT_BRICKS.get(), DIRT_BRICK_STAIRS.get(), DIRT_BRICK_SLAB.get(), DIRT_BRICK_WALL.get(), DIRT_TILES.get(), DIRT_TILE_STAIRS.get(), DIRT_TILE_SLAB.get(), DIRT_TILE_WALL.get());
		this.tag(BlockTags.FLOWER_POTS).add(POTTED_WILLOW_SAPLING.get(), POTTED_PINE_SAPLING.get(), POTTED_PINK_WISTERIA_SAPLING.get(), POTTED_BLUE_WISTERIA_SAPLING.get(), POTTED_PURPLE_WISTERIA_SAPLING.get(), POTTED_WHITE_WISTERIA_SAPLING.get(), POTTED_PLUM_SAPLING.get(), POTTED_CHEERFUL_PLUM_SAPLING.get(), POTTED_MOODY_PLUM_SAPLING.get(), POTTED_CARTWHEEL.get(), POTTED_BLUEBELL.get(), POTTED_VIOLET.get(), POTTED_DIANTHUS.get(), POTTED_RED_LOTUS_FLOWER.get(), POTTED_WHITE_LOTUS_FLOWER.get(), POTTED_TASSELFLOWER.get(), POTTED_YELLOW_HIBISCUS.get(), POTTED_ORANGE_HIBISCUS.get(), POTTED_RED_HIBISCUS.get(), POTTED_PINK_HIBISCUS.get(), POTTED_MAGENTA_HIBISCUS.get(), POTTED_PURPLE_HIBISCUS.get(), POTTED_BIRD_OF_PARADISE.get(), POTTED_PINK_DELPHINIUM.get(), POTTED_BLUE_DELPHINIUM.get(), POTTED_PURPLE_DELPHINIUM.get(), POTTED_WHITE_DELPHINIUM.get(), POTTED_CATTAIL.get(), POTTED_CUP_LICHEN.get(), POTTED_DWARF_SPRUCE.get());
		this.tag(BlockTags.SMALL_FLOWERS).addTag(HIBISCUSES).addTag(WALL_HIBISCUSES).add(CARTWHEEL.get(), BLUEBELL.get(), VIOLET.get(), DIANTHUS.get(), RED_LOTUS_FLOWER.get(), WHITE_LOTUS_FLOWER.get(), TASSELFLOWER.get());
		this.tag(BlockTags.TALL_FLOWERS).add(BIRD_OF_PARADISE.get(), PINK_DELPHINIUM.get(), BLUE_DELPHINIUM.get(), PURPLE_DELPHINIUM.get(), WHITE_DELPHINIUM.get());

		this.tag(BlockTags.SAPLINGS).add(WILLOW_SAPLING.get(), PINE_SAPLING.get(), PINK_WISTERIA_SAPLING.get(), BLUE_WISTERIA_SAPLING.get(), PURPLE_WISTERIA_SAPLING.get(), WHITE_WISTERIA_SAPLING.get(), PLUM_SAPLING.get(), CHEERFUL_PLUM_SAPLING.get(), MOODY_PLUM_SAPLING.get());
		this.tag(BlockTags.LEAVES).add(WILLOW_LEAVES.get(), PINE_LEAVES.get(), WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAVES.get(), PLUM_LEAVES.get(), CHEERFUL_PLUM_LEAVES.get(), MOODY_PLUM_LEAVES.get(), HIBISCUS_LEAVES.get());
		this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(WILLOW_LOG.get(), PINE_LOG.get(), WISTERIA_LOG.get(), PLUM_LOG.get());
		this.tag(BlockTags.LOGS_THAT_BURN).addTags(WILLOW_LOGS, PINE_LOGS, WISTERIA_LOGS, PLUM_LOGS);
		this.tag(BlockTags.PLANKS).add(WILLOW_PLANKS.get(), PINE_PLANKS.get(), WISTERIA_PLANKS.get(), PLUM_PLANKS.get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(WILLOW_BUTTON.get(), PINE_BUTTON.get(), WISTERIA_BUTTON.get(), PLUM_BUTTON.get());
		this.tag(BlockTags.WOODEN_DOORS).add(WILLOW_DOOR.get(), PINE_DOOR.get(), WISTERIA_DOOR.get(), PLUM_DOOR.get());
		this.tag(BlockTags.WOODEN_FENCES).add(WILLOW_FENCE.get(), PINE_FENCE.get(), WISTERIA_FENCE.get(), PLUM_FENCE.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(WILLOW_PRESSURE_PLATE.get(), PINE_PRESSURE_PLATE.get(), WISTERIA_PRESSURE_PLATE.get(), PLUM_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_SLABS).add(WILLOW_SLAB.get(), PINE_SLAB.get(), WISTERIA_SLAB.get(), PLUM_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(WILLOW_STAIRS.get(), PINE_STAIRS.get(), WISTERIA_STAIRS.get(), PLUM_STAIRS.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(WILLOW_TRAPDOOR.get(), PINE_TRAPDOOR.get(), WISTERIA_TRAPDOOR.get(), PLUM_TRAPDOOR.get());
		this.tag(BlockTags.FENCE_GATES).add(WILLOW_FENCE_GATE.get(), PINE_FENCE_GATE.get(), WISTERIA_FENCE_GATE.get(), PLUM_FENCE_GATE.get());

		this.tag(BlockTags.STANDING_SIGNS).add(WILLOW_SIGNS.getFirst().get(), PINE_SIGNS.getFirst().get(), WISTERIA_SIGNS.getFirst().get(), PLUM_SIGNS.getFirst().get());
		this.tag(BlockTags.WALL_SIGNS).add(WILLOW_SIGNS.getSecond().get(), PINE_SIGNS.getSecond().get(), WISTERIA_SIGNS.getSecond().get(), PLUM_SIGNS.getSecond().get());
		this.tag(BlockTags.CEILING_HANGING_SIGNS).add(WILLOW_HANGING_SIGNS.getFirst().get(), PINE_HANGING_SIGNS.getFirst().get(), PLUM_HANGING_SIGNS.getFirst().get(), WISTERIA_HANGING_SIGNS.getFirst().get());
		this.tag(BlockTags.WALL_HANGING_SIGNS).add(WILLOW_HANGING_SIGNS.getSecond().get(), PINE_HANGING_SIGNS.getSecond().get(), PLUM_HANGING_SIGNS.getSecond().get(), WISTERIA_HANGING_SIGNS.getSecond().get());

		this.tag(BlockTags.REPLACEABLE).add(Blocks.PINK_PETALS, HANGING_WILLOW_LEAVES.get(), PINK_HANGING_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get(), GIANT_TALL_GRASS.get(), MYCELIUM_SPROUTS.get(), CUP_LICHEN.get(), BIRD_OF_PARADISE.get(), PINK_DELPHINIUM.get(), BLUE_DELPHINIUM.get(), PURPLE_DELPHINIUM.get(), WHITE_DELPHINIUM.get(), CACTUS_BOBBLE.get());
		this.tag(BlockTags.CLIMBABLE).add(PINK_HANGING_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get());
		this.tag(BlockTags.GUARDED_BY_PIGLINS).add(WILLOW_CHEST.get(), TRAPPED_WILLOW_CHEST.get(), PINE_CHEST.get(), TRAPPED_PINE_CHEST.get(), WISTERIA_CHEST.get(), TRAPPED_WISTERIA_CHEST.get(), PLUM_CHEST.get(), TRAPPED_PLUM_CHEST.get());
		this.tag(BlockTags.PIGLIN_REPELLENTS).add(DWARF_SPRUCE_SOUL_TORCH.get(), DWARF_SPRUCE_PLANT_SOUL_TORCH.get());
		this.tag(BlockTags.FROG_PREFER_JUMP_TO).add(LARGE_LILY_PAD.get(), GIANT_LILY_PAD.get());
		this.tag(BlockTags.RABBITS_SPAWNABLE_ON).add(Blocks.STONE, Blocks.COARSE_DIRT);
		this.tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(YAK_HAIR_BLOCK.get());
		this.tag(BlockTags.DAMPENS_VIBRATIONS).add(YAK_HAIR_BLOCK.get(), YAK_HAIR_RUG.get());

		this.tag(DEER_SPAWNABLE_ON).add(Blocks.GRASS_BLOCK, Blocks.SNOW, Blocks.SNOW_BLOCK);
		this.tag(WATER_ANIMALS_SPAWNABLE_ON).addTag(BlockTags.ANIMALS_SPAWNABLE_ON).add(Blocks.WATER, Blocks.MUD);
		this.tag(GRASS_LIKE).add(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM).addOptional(ResourceLocation.fromNamespaceAndPath("atmospheric", "crustose"));
		this.tag(CUP_LICHEN_PLANTABLE_ON).addTag(BlockTags.DIRT).addTag(Tags.Blocks.STONES).addTag(Tags.Blocks.ORES).addTag(BlockTags.LOGS);
		this.tag(CACTUS_BOBBLE_PLANTABLE_ON).add(Blocks.CACTUS).addOptional(ResourceLocation.fromNamespaceAndPath("atmospheric", "snowy_cactus"));
		this.tag(CATTAIL_PLANTABLE_ON).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.FARMLAND);
		this.tag(MUD_REPLACEABLES).add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.SAND, Blocks.CLAY);
		this.tag(PINECONE_GOLEM_BASE_BLOCKS).add(PINECONE.get());
		this.tag(TRACKABLE_BY_TAPIRS).addTag(BlockTags.SWORD_EFFICIENT).add(Blocks.MOSS_BLOCK, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING, Blocks.CACTUS, Blocks.BEE_NEST);
		this.tag(BlockTags.SWORD_EFFICIENT).add(MYCELIUM_SPROUTS.get(), DWARF_SPRUCE.get(), HANGING_WILLOW_LEAVES.get(),
				PINK_HANGING_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get(),
				GIANT_TALL_GRASS.get(), PINK_DELPHINIUM.get(), PURPLE_DELPHINIUM.get(), BLUE_DELPHINIUM.get(), WHITE_DELPHINIUM.get(), BIRD_OF_PARADISE.get(),
				CUP_LICHEN.get(), LARGE_LILY_PAD.get(), GIANT_LILY_PAD.get(), DUCKWEED.get(), CACTUS_BOBBLE.get()
		);

		this.tag(HIBISCUSES).add(YELLOW_HIBISCUS.get(), ORANGE_HIBISCUS.get(), RED_HIBISCUS.get(), PINK_HIBISCUS.get(), MAGENTA_HIBISCUS.get(), PURPLE_HIBISCUS.get());
		this.tag(WALL_HIBISCUSES).add(YELLOW_WALL_HIBISCUS.get(), ORANGE_WALL_HIBISCUS.get(), RED_WALL_HIBISCUS.get(), PINK_WALL_HIBISCUS.get(), MAGENTA_WALL_HIBISCUS.get(), PURPLE_WALL_HIBISCUS.get());

		this.tag(WILLOW_LOGS).add(WILLOW_LOG.get(), WILLOW_WOOD.get(), STRIPPED_WILLOW_LOG.get(), STRIPPED_WILLOW_WOOD.get());
		this.tag(PINE_LOGS).add(PINE_LOG.get(), PINE_WOOD.get(), STRIPPED_PINE_LOG.get(), STRIPPED_PINE_WOOD.get());
		this.tag(WISTERIA_LOGS).add(WISTERIA_LOG.get(), WISTERIA_WOOD.get(), STRIPPED_WISTERIA_LOG.get(), STRIPPED_WISTERIA_WOOD.get());
		this.tag(PLUM_LOGS).add(PLUM_LOG.get(), PLUM_WOOD.get(), STRIPPED_PLUM_LOG.get(), STRIPPED_PLUM_WOOD.get());

		this.tag(BlueprintBlockTags.WOODEN_CHESTS).add(WILLOW_CHEST.get(), PINE_CHEST.get(), WISTERIA_CHEST.get(), PLUM_CHEST.get());
		this.tag(BlueprintBlockTags.WOODEN_TRAPPED_CHESTS).add(TRAPPED_WILLOW_CHEST.get(), TRAPPED_PINE_CHEST.get(), TRAPPED_WISTERIA_CHEST.get(), TRAPPED_PLUM_CHEST.get());
		this.tag(BlueprintBlockTags.WOODEN_LADDERS).add(WILLOW_LADDER.get(), PINE_LADDER.get(), WISTERIA_LADDER.get(), PLUM_LADDER.get());
		this.tag(BlueprintBlockTags.WOODEN_BEEHIVES).add(WILLOW_BEEHIVE.get(), PINE_BEEHIVE.get(), WISTERIA_BEEHIVE.get(), PLUM_BEEHIVE.get());
		this.tag(BlueprintBlockTags.WOODEN_BOOKSHELVES).add(WILLOW_BOOKSHELF.get(), PINE_BOOKSHELF.get(), WISTERIA_BOOKSHELF.get(), PLUM_BOOKSHELF.get());
		this.tag(BlueprintBlockTags.WOODEN_CHISELED_BOOKSHELVES).add(CHISELED_WILLOW_BOOKSHELF.get(), CHISELED_PINE_BOOKSHELF.get(), CHISELED_WISTERIA_BOOKSHELF.get(), CHISELED_PLUM_BOOKSHELF.get());

		this.tag(BlueprintBlockTags.WOODEN_BOARDS).add(WILLOW_BOARDS.get(), PINE_BOARDS.get(), WISTERIA_BOARDS.get(), PLUM_BOARDS.get());
		this.tag(BlueprintBlockTags.LEAF_PILES).add(WILLOW_LEAF_PILE.get(), PINE_LEAF_PILE.get(), WISTERIA_LEAF_PILE.get(), PINK_WISTERIA_LEAF_PILE.get(), BLUE_WISTERIA_LEAF_PILE.get(), PURPLE_WISTERIA_LEAF_PILE.get(), WHITE_WISTERIA_LEAF_PILE.get(), PLUM_LEAF_PILE.get(), CHEERFUL_PLUM_LEAF_PILE.get(), MOODY_PLUM_LEAF_PILE.get(), HIBISCUS_LEAF_PILE.get());

		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(WILLOW_FENCE_GATE.get(), PINE_FENCE_GATE.get(), WISTERIA_FENCE_GATE.get(), PLUM_FENCE_GATE.get());
		this.tag(Tags.Blocks.STRIPPED_LOGS).add(STRIPPED_WILLOW_LOG.get(), STRIPPED_PINE_LOG.get(), STRIPPED_WISTERIA_LOG.get(), STRIPPED_PLUM_LOG.get());
		this.tag(Tags.Blocks.STRIPPED_WOODS).add(STRIPPED_WILLOW_WOOD.get(), STRIPPED_PINE_WOOD.get(), STRIPPED_WISTERIA_WOOD.get(), STRIPPED_PLUM_WOOD.get());

		this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_CHERRY, STORAGE_BLOCKS_PLUM, STORAGE_BLOCKS_DUCK_EGG, STORAGE_BLOCKS_CATTAIL_FLUFF);
		this.tag(STORAGE_BLOCKS_CHERRY).add(CHERRY_CRATE.get());
		this.tag(STORAGE_BLOCKS_PLUM).add(PLUM_CRATE.get());
		this.tag(STORAGE_BLOCKS_DUCK_EGG).add(DUCK_EGG_CRATE.get());
		this.tag(STORAGE_BLOCKS_CATTAIL_FLUFF).add(CATTAIL_FLUFF_BLOCK.get());
	}
}