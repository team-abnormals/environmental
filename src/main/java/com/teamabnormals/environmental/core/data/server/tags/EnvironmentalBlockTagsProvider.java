package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockTagsProvider extends BlockTagsProvider {

	public EnvironmentalBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(
				WILLOW_BOARDS.get(), WILLOW_BOOKSHELF.get(), WILLOW_LADDER.get(), WILLOW_BEEHIVE.get(), WILLOW_CHESTS.getFirst().get(), WILLOW_CHESTS.getSecond().get(), WILLOW_HEDGE.get(), WILLOW_POST.get(), STRIPPED_WILLOW_POST.get(),
				PINE_BOARDS.get(), PINE_BOOKSHELF.get(), PINE_LADDER.get(), PINE_BEEHIVE.get(), PINE_CHESTS.getFirst().get(), PINE_CHESTS.getSecond().get(), PINE_HEDGE.get(), PINE_POST.get(), STRIPPED_PINE_POST.get(), PINECONE.get(), WAXED_PINECONE.get(),
				WISTERIA_BOARDS.get(), WISTERIA_BOOKSHELF.get(), WISTERIA_LADDER.get(), WISTERIA_BEEHIVE.get(), WISTERIA_CHESTS.getFirst().get(), WISTERIA_CHESTS.getSecond().get(), PINK_WISTERIA_HEDGE.get(), BLUE_WISTERIA_HEDGE.get(), PURPLE_WISTERIA_HEDGE.get(), WHITE_WISTERIA_HEDGE.get(), WISTERIA_POST.get(), STRIPPED_WISTERIA_POST.get(),
				CHERRY_BOARDS.get(), CHERRY_BOOKSHELF.get(), CHERRY_LADDER.get(), CHERRY_BEEHIVE.get(), CHERRY_CHESTS.getFirst().get(), CHERRY_CHESTS.getSecond().get(), CHERRY_HEDGE.get(), CHEERFUL_CHERRY_HEDGE.get(), MOODY_CHERRY_HEDGE.get(), CHERRY_POST.get(), STRIPPED_CHERRY_POST.get(),
				CATTAIL_SPROUT.get(), CATTAIL.get(), LARGE_LILY_PAD.get(), GIANT_LILY_PAD.get(), GIANT_TALL_GRASS.get(), MYCELIUM_SPROUTS.get(), CUP_LICHEN.get(), DWARF_SPRUCE.get(), TALL_DWARF_SPRUCE.get(),
				DUCK_EGG_CRATE.get(), YAK_HAIR_BLOCK.get(), YAK_HAIR_RUG.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_HOE).add(
				WILLOW_LEAVES.get(), HANGING_WILLOW_LEAVES.get(), WILLOW_LEAF_PILE.get(), WILLOW_LEAF_CARPET.get(),
				PINE_LEAVES.get(), PINE_LEAF_PILE.get(), PINE_LEAF_CARPET.get(),
				WISTERIA_LEAVES.get(), WISTERIA_LEAF_PILE.get(), WISTERIA_LEAF_CARPET.get(),
				PINK_WISTERIA_LEAVES.get(), PINK_HANGING_WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAF_PILE.get(), PINK_WISTERIA_LEAF_CARPET.get(),
				BLUE_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAF_PILE.get(), BLUE_WISTERIA_LEAF_CARPET.get(),
				PURPLE_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAF_PILE.get(), PURPLE_WISTERIA_LEAF_CARPET.get(),
				WHITE_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAF_PILE.get(), WHITE_WISTERIA_LEAF_CARPET.get(),
				CHERRY_LEAVES.get(), CHERRY_LEAF_PILE.get(), CHERRY_LEAF_CARPET.get(),
				CHEERFUL_CHERRY_LEAVES.get(), CHEERFUL_CHERRY_LEAF_PILE.get(), CHEERFUL_CHERRY_LEAF_CARPET.get(),
				MOODY_CHERRY_LEAVES.get(), MOODY_CHERRY_LEAF_PILE.get(), MOODY_CHERRY_LEAF_CARPET.get(),
				DUCKWEED.get(), CATTAIL_FLUFF_BLOCK.get(),
				GRASS_THATCH.get(), GRASS_THATCH_SLAB.get(), GRASS_THATCH_STAIRS.get(), GRASS_THATCH_VERTICAL_SLAB.get(),
				CATTAIL_THATCH.get(), CATTAIL_THATCH_SLAB.get(), CATTAIL_THATCH_STAIRS.get(), CATTAIL_THATCH_VERTICAL_SLAB.get(),
				DUCKWEED_THATCH.get(), DUCKWEED_THATCH_SLAB.get(), DUCKWEED_THATCH_STAIRS.get(), DUCKWEED_THATCH_VERTICAL_SLAB.get(),
				HIBISCUS_LEAVES.get(), HIBISCUS_LEAF_PILE.get(), HIBISCUS_LEAF_CARPET.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				SMOOTH_MUD.get(), SMOOTH_MUD_SLAB.get(), SMOOTH_MUD_VERTICAL_SLAB.get(), CHISELED_MUD_BRICKS.get(), SLABFISH_EFFIGY.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(DIRT_BRICKS.get(), DIRT_BRICK_STAIRS.get(), DIRT_BRICK_SLAB.get(), DIRT_BRICK_WALL.get(), DIRT_BRICK_VERTICAL_SLAB.get(), DIRT_TILES.get(), DIRT_TILE_STAIRS.get(), DIRT_TILE_SLAB.get(), DIRT_TILE_WALL.get(), DIRT_TILE_VERTICAL_SLAB.get(), BURIED_TRUFFLE.get(), DIRT_PATH.get(), PODZOL_PATH.get(), MYCELIUM_PATH.get());

		this.tag(BlockTags.BEEHIVES).add(WILLOW_BEEHIVE.get(), PINE_BEEHIVE.get(), WISTERIA_BEEHIVE.get(), CHERRY_BEEHIVE.get());
		this.tag(BlockTags.CLIMBABLE).add(WILLOW_LADDER.get(), PINE_LADDER.get(), WISTERIA_LADDER.get(), CHERRY_LADDER.get(), PINK_HANGING_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get());
		this.tag(BlockTags.DIRT).add(DIRT_BRICKS.get(), DIRT_BRICK_STAIRS.get(), DIRT_BRICK_SLAB.get(), DIRT_BRICK_WALL.get(), DIRT_BRICK_VERTICAL_SLAB.get(), DIRT_TILES.get(), DIRT_TILE_STAIRS.get(), DIRT_TILE_SLAB.get(), DIRT_TILE_WALL.get(), DIRT_TILE_VERTICAL_SLAB.get());
		this.tag(BlockTags.FLOWER_POTS).add(POTTED_WILLOW_SAPLING.get(), POTTED_PINE_SAPLING.get(), POTTED_PINK_WISTERIA_SAPLING.get(), POTTED_BLUE_WISTERIA_SAPLING.get(), POTTED_PURPLE_WISTERIA_SAPLING.get(), POTTED_WHITE_WISTERIA_SAPLING.get(), POTTED_CHERRY_SAPLING.get(), POTTED_CHEERFUL_CHERRY_SAPLING.get(), POTTED_MOODY_CHERRY_SAPLING.get(), POTTED_CARTWHEEL.get(), POTTED_BLUEBELL.get(), POTTED_VIOLET.get(), POTTED_DIANTHUS.get(), POTTED_RED_LOTUS_FLOWER.get(), POTTED_WHITE_LOTUS_FLOWER.get(), POTTED_TASSELFLOWER.get(), POTTED_YELLOW_HIBISCUS.get(), POTTED_ORANGE_HIBISCUS.get(), POTTED_RED_HIBISCUS.get(), POTTED_PINK_HIBISCUS.get(), POTTED_MAGENTA_HIBISCUS.get(), POTTED_PURPLE_HIBISCUS.get(), POTTED_BIRD_OF_PARADISE.get(), POTTED_PINK_DELPHINIUM.get(), POTTED_BLUE_DELPHINIUM.get(), POTTED_PURPLE_DELPHINIUM.get(), POTTED_WHITE_DELPHINIUM.get(), POTTED_CATTAIL.get(), POTTED_CUP_LICHEN.get(), POTTED_DWARF_SPRUCE.get());
		this.tag(BlockTags.GUARDED_BY_PIGLINS).add(WILLOW_CHESTS.getFirst().get(), WILLOW_CHESTS.getSecond().get(), PINE_CHESTS.getFirst().get(), PINE_CHESTS.getSecond().get(), WISTERIA_CHESTS.getFirst().get(), WISTERIA_CHESTS.getSecond().get(), CHERRY_CHESTS.getFirst().get(), CHERRY_CHESTS.getSecond().get());
		this.tag(BlockTags.LEAVES).add(WILLOW_LEAVES.get(), PINE_LEAVES.get(), WISTERIA_LEAVES.get(), PINK_WISTERIA_LEAVES.get(), BLUE_WISTERIA_LEAVES.get(), PURPLE_WISTERIA_LEAVES.get(), WHITE_WISTERIA_LEAVES.get(), CHERRY_LEAVES.get(), CHEERFUL_CHERRY_LEAVES.get(), MOODY_CHERRY_LEAVES.get(), HIBISCUS_LEAVES.get());
		this.tag(BlockTags.LOGS_THAT_BURN).addTags(WILLOW_LOGS, PINE_LOGS, WISTERIA_LOGS, CHERRY_LOGS);
		this.tag(BlockTags.PLANKS).add(WILLOW_PLANKS.get(), PINE_PLANKS.get(), WISTERIA_PLANKS.get(), CHERRY_PLANKS.get(), VERTICAL_WILLOW_PLANKS.get(), VERTICAL_PINE_PLANKS.get(), VERTICAL_WISTERIA_PLANKS.get(), VERTICAL_CHERRY_PLANKS.get());
		this.tag(BlockTags.SAPLINGS).add(WILLOW_SAPLING.get(), PINE_SAPLING.get(), PINK_WISTERIA_SAPLING.get(), BLUE_WISTERIA_SAPLING.get(), PURPLE_WISTERIA_SAPLING.get(), WHITE_WISTERIA_SAPLING.get(), CHERRY_SAPLING.get(), CHEERFUL_CHERRY_SAPLING.get(), MOODY_CHERRY_SAPLING.get());
		this.tag(BlockTags.SMALL_FLOWERS).addTag(HIBISCUSES).addTag(WALL_HIBISCUSES).add(CARTWHEEL.get(), BLUEBELL.get(), VIOLET.get(), DIANTHUS.get(), RED_LOTUS_FLOWER.get(), WHITE_LOTUS_FLOWER.get(), TASSELFLOWER.get());
		this.tag(BlockTags.STANDING_SIGNS).add(WILLOW_SIGNS.getFirst().get(), PINE_SIGNS.getFirst().get(), WISTERIA_SIGNS.getFirst().get(), CHERRY_SIGNS.getFirst().get());
		this.tag(BlockTags.TALL_FLOWERS).add(BIRD_OF_PARADISE.get(), PINK_DELPHINIUM.get(), BLUE_DELPHINIUM.get(), PURPLE_DELPHINIUM.get(), WHITE_DELPHINIUM.get());
		this.tag(BlockTags.WALL_SIGNS).add(WILLOW_SIGNS.getSecond().get(), PINE_SIGNS.getSecond().get(), WISTERIA_SIGNS.getSecond().get(), CHERRY_SIGNS.getSecond().get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(WILLOW_BUTTON.get(), PINE_BUTTON.get(), WISTERIA_BUTTON.get(), CHERRY_BUTTON.get());
		this.tag(BlockTags.WOODEN_DOORS).add(WILLOW_DOOR.get(), PINE_DOOR.get(), WISTERIA_DOOR.get(), CHERRY_DOOR.get());
		this.tag(BlockTags.WOODEN_FENCES).add(WILLOW_FENCE.get(), PINE_FENCE.get(), WISTERIA_FENCE.get(), CHERRY_FENCE.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(WILLOW_PRESSURE_PLATE.get(), PINE_PRESSURE_PLATE.get(), WISTERIA_PRESSURE_PLATE.get(), CHERRY_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_SLABS).add(WILLOW_SLAB.get(), PINE_SLAB.get(), WISTERIA_SLAB.get(), CHERRY_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(WILLOW_STAIRS.get(), PINE_STAIRS.get(), WISTERIA_STAIRS.get(), CHERRY_STAIRS.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(WILLOW_TRAPDOOR.get(), PINE_TRAPDOOR.get(), WISTERIA_TRAPDOOR.get(), CHERRY_TRAPDOOR.get());
		this.tag(BlockTags.FENCE_GATES).add(WILLOW_FENCE_GATE.get(), PINE_FENCE_GATE.get(), WISTERIA_FENCE_GATE.get(), CHERRY_FENCE_GATE.get());
		this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(WILLOW_LOG.get(), PINE_LOG.get(), WISTERIA_LOG.get(), CHERRY_LOG.get());

		this.tag(BlockTags.SLABS).add(GRASS_THATCH_SLAB.get(), CATTAIL_THATCH_SLAB.get(), DUCKWEED_THATCH_SLAB.get(), DIRT_BRICK_SLAB.get(), DIRT_TILE_SLAB.get(), SMOOTH_MUD_SLAB.get());
		this.tag(BlockTags.STAIRS).add(GRASS_THATCH_STAIRS.get(), CATTAIL_THATCH_STAIRS.get(), DUCKWEED_THATCH_STAIRS.get(), DIRT_BRICK_STAIRS.get(), DIRT_TILE_STAIRS.get());
		this.tag(BlockTags.WALLS).add(DIRT_BRICK_WALL.get(), DIRT_TILE_WALL.get());
		this.tag(BlueprintBlockTags.VERTICAL_SLABS).add(GRASS_THATCH_VERTICAL_SLAB.get(), CATTAIL_THATCH_VERTICAL_SLAB.get(), DUCKWEED_THATCH_VERTICAL_SLAB.get(), SMOOTH_MUD_VERTICAL_SLAB.get());

		this.tag(BlockTags.FROG_PREFER_JUMP_TO).add(LARGE_LILY_PAD.get(), GIANT_LILY_PAD.get());
		this.tag(BlockTags.RABBITS_SPAWNABLE_ON).add(Blocks.STONE, Blocks.COARSE_DIRT);
		this.tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(YAK_HAIR_BLOCK.get());
		this.tag(BlockTags.DAMPENS_VIBRATIONS).add(YAK_HAIR_BLOCK.get(), YAK_HAIR_RUG.get());
		this.tag(BlockTags.REPLACEABLE_PLANTS).add(HANGING_WILLOW_LEAVES.get(), PINK_HANGING_WISTERIA_LEAVES.get(), BLUE_HANGING_WISTERIA_LEAVES.get(), PURPLE_HANGING_WISTERIA_LEAVES.get(), WHITE_HANGING_WISTERIA_LEAVES.get(), GIANT_TALL_GRASS.get(), MYCELIUM_SPROUTS.get(), CUP_LICHEN.get(), BIRD_OF_PARADISE.get(), PINK_DELPHINIUM.get(), BLUE_DELPHINIUM.get(), PURPLE_DELPHINIUM.get(), WHITE_DELPHINIUM.get(), CACTUS_BOBBLE.get());

		this.tag(DEER_SPAWNABLE_ON).add(Blocks.GRASS_BLOCK, Blocks.SNOW, Blocks.SNOW_BLOCK);
		this.tag(WATER_ANIMALS_SPAWNABLE_ON).addTag(BlockTags.ANIMALS_SPAWNABLE_ON).add(Blocks.WATER);
		this.tag(GRASS_LIKE).add(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM).addOptional(new ResourceLocation("atmospheric", "crustose"));
		this.tag(CUP_LICHEN_PLANTABLE_ON).addTag(BlockTags.DIRT).addTag(Tags.Blocks.STONE).addTag(Tags.Blocks.ORES).addTag(BlockTags.LOGS);
		this.tag(CACTUS_BOBBLE_PLANTABLE_ON).add(Blocks.CACTUS).addOptional(new ResourceLocation("atmospheric", "snowy_cactus"));
		this.tag(CATTAIL_PLANTABLE_ON).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.FARMLAND);
		this.tag(MUD_REPLACEABLES).add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.SAND, Blocks.CLAY);

		this.tag(HIBISCUSES).add(YELLOW_HIBISCUS.get(), ORANGE_HIBISCUS.get(), RED_HIBISCUS.get(), PINK_HIBISCUS.get(), MAGENTA_HIBISCUS.get(), PURPLE_HIBISCUS.get());
		this.tag(WALL_HIBISCUSES).add(YELLOW_WALL_HIBISCUS.get(), ORANGE_WALL_HIBISCUS.get(), RED_WALL_HIBISCUS.get(), PINK_WALL_HIBISCUS.get(), MAGENTA_WALL_HIBISCUS.get(), PURPLE_WALL_HIBISCUS.get());

		this.tag(WILLOW_LOGS).add(WILLOW_LOG.get(), WILLOW_WOOD.get(), STRIPPED_WILLOW_LOG.get(), STRIPPED_WILLOW_WOOD.get());
		this.tag(PINE_LOGS).add(PINE_LOG.get(), PINE_WOOD.get(), STRIPPED_PINE_LOG.get(), STRIPPED_PINE_WOOD.get());
		this.tag(WISTERIA_LOGS).add(WISTERIA_LOG.get(), WISTERIA_WOOD.get(), STRIPPED_WISTERIA_LOG.get(), STRIPPED_WISTERIA_WOOD.get());
		this.tag(CHERRY_LOGS).add(CHERRY_LOG.get(), CHERRY_WOOD.get(), STRIPPED_CHERRY_LOG.get(), STRIPPED_CHERRY_WOOD.get());

		this.tag(Tags.Blocks.CHESTS_WOODEN).add(WILLOW_CHESTS.getFirst().get(), WILLOW_CHESTS.getSecond().get(), PINE_CHESTS.getFirst().get(), PINE_CHESTS.getSecond().get(), WISTERIA_CHESTS.getFirst().get(), WISTERIA_CHESTS.getSecond().get(), CHERRY_CHESTS.getFirst().get(), CHERRY_CHESTS.getSecond().get());
		this.tag(Tags.Blocks.CHESTS_TRAPPED).add(WILLOW_CHESTS.getSecond().get(), PINE_CHESTS.getSecond().get(), WISTERIA_CHESTS.getSecond().get(), CHERRY_CHESTS.getSecond().get());
		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(WILLOW_FENCE_GATE.get(), PINE_FENCE_GATE.get(), WISTERIA_FENCE_GATE.get(), CHERRY_FENCE_GATE.get());
		this.tag(Tags.Blocks.FENCES_WOODEN).add(WILLOW_FENCE.get(), PINE_FENCE.get(), WISTERIA_FENCE.get(), CHERRY_FENCE.get());

		this.tag(BlueprintBlockTags.LEAF_PILES).add(WILLOW_LEAF_PILE.get(), PINE_LEAF_PILE.get(), WISTERIA_LEAF_PILE.get(), PINK_WISTERIA_LEAF_PILE.get(), BLUE_WISTERIA_LEAF_PILE.get(), PURPLE_WISTERIA_LEAF_PILE.get(), WHITE_WISTERIA_LEAF_PILE.get(), CHERRY_LEAF_PILE.get(), CHEERFUL_CHERRY_LEAF_PILE.get(), MOODY_CHERRY_LEAF_PILE.get(), HIBISCUS_LEAF_PILE.get());
		this.tag(BlueprintBlockTags.LADDERS).add(WILLOW_LADDER.get(), PINE_LADDER.get(), WISTERIA_LADDER.get(), CHERRY_LADDER.get());
		this.tag(BlueprintBlockTags.WOODEN_VERTICAL_SLABS).add(WILLOW_VERTICAL_SLAB.get(), PINE_VERTICAL_SLAB.get(), WISTERIA_VERTICAL_SLAB.get(), CHERRY_VERTICAL_SLAB.get());
		this.tag(BlueprintBlockTags.HEDGES).add(WILLOW_HEDGE.get(), PINE_HEDGE.get(), WISTERIA_HEDGE.get(), PINK_WISTERIA_HEDGE.get(), BLUE_WISTERIA_HEDGE.get(), PURPLE_WISTERIA_HEDGE.get(), WHITE_WISTERIA_HEDGE.get(), CHERRY_HEDGE.get(), CHEERFUL_CHERRY_HEDGE.get(), MOODY_CHERRY_HEDGE.get());
	}
}