package com.teamabnormals.environmental.core.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock.VerticalSlabType;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CupLichenBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes.*;

public class EnvironmentalLootTableProvider extends LootTableProvider {
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(EnvironmentalBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(EnvironmentalEntityLoot::new, LootContextParamSets.ENTITY), Pair.of(EnvironmentalChestLoot::new, LootContextParamSets.CHEST));


	public EnvironmentalLootTableProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
		return tables;
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
	}

	private static class EnvironmentalBlockLoot extends BlockLoot {
		private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
		private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
		private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
		private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).or(HAS_SILK_TOUCH).invert();

		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
		private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
		private static final float[] DOUBLE_LEAVES_STICK_CHANCES = new float[]{0.04F, 0.044444446F, 0.05F, 0.066666670F, 0.2F};

		@Override
		public void addTables() {
			this.dropSelf(CARTWHEEL.get());
			this.dropSelf(BLUEBELL.get());
			this.dropSelf(VIOLET.get());
			this.dropSelf(DIANTHUS.get());
			this.dropSelf(RED_LOTUS_FLOWER.get());
			this.dropSelf(WHITE_LOTUS_FLOWER.get());
			this.dropSelf(TASSELFLOWER.get());
			this.dropSelf(YELLOW_HIBISCUS.get());
			this.dropSelf(ORANGE_HIBISCUS.get());
			this.dropSelf(RED_HIBISCUS.get());
			this.dropSelf(PINK_HIBISCUS.get());
			this.dropSelf(MAGENTA_HIBISCUS.get());
			this.dropSelf(PURPLE_HIBISCUS.get());
			this.dropOther(YELLOW_WALL_HIBISCUS.get(), YELLOW_HIBISCUS.get());
			this.dropOther(ORANGE_WALL_HIBISCUS.get(), ORANGE_HIBISCUS.get());
			this.dropOther(RED_WALL_HIBISCUS.get(), RED_HIBISCUS.get());
			this.dropOther(PINK_WALL_HIBISCUS.get(), PINK_HIBISCUS.get());
			this.dropOther(MAGENTA_WALL_HIBISCUS.get(), MAGENTA_HIBISCUS.get());
			this.dropOther(PURPLE_WALL_HIBISCUS.get(), PURPLE_HIBISCUS.get());
			this.add(BIRD_OF_PARADISE.get(), (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
			this.add(PINK_DELPHINIUM.get(), (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
			this.add(BLUE_DELPHINIUM.get(), (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
			this.add(PURPLE_DELPHINIUM.get(), (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
			this.add(WHITE_DELPHINIUM.get(), (block) -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

			this.dropPottedContents(POTTED_CARTWHEEL.get());
			this.dropPottedContents(POTTED_BLUEBELL.get());
			this.dropPottedContents(POTTED_VIOLET.get());
			this.dropPottedContents(POTTED_DIANTHUS.get());
			this.dropPottedContents(POTTED_RED_LOTUS_FLOWER.get());
			this.dropPottedContents(POTTED_WHITE_LOTUS_FLOWER.get());
			this.dropPottedContents(POTTED_TASSELFLOWER.get());
			this.dropPottedContents(POTTED_YELLOW_HIBISCUS.get());
			this.dropPottedContents(POTTED_ORANGE_HIBISCUS.get());
			this.dropPottedContents(POTTED_RED_HIBISCUS.get());
			this.dropPottedContents(POTTED_PINK_HIBISCUS.get());
			this.dropPottedContents(POTTED_MAGENTA_HIBISCUS.get());
			this.dropPottedContents(POTTED_PURPLE_HIBISCUS.get());
			this.dropPottedContents(POTTED_BIRD_OF_PARADISE.get());
			this.dropPottedContents(POTTED_PINK_DELPHINIUM.get());
			this.dropPottedContents(POTTED_BLUE_DELPHINIUM.get());
			this.dropPottedContents(POTTED_PURPLE_DELPHINIUM.get());
			this.dropPottedContents(POTTED_WHITE_DELPHINIUM.get());
			this.dropPottedContents(POTTED_CATTAIL.get());
			this.dropPottedContents(POTTED_CUP_LICHEN.get());
			this.dropPottedContents(POTTED_DWARF_SPRUCE.get());

			this.dropSelf(DIRT_BRICKS.get());
			this.dropSelf(DIRT_BRICK_STAIRS.get());
			this.dropSelf(DIRT_BRICK_WALL.get());
			this.add(DIRT_BRICK_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(DIRT_BRICK_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.dropSelf(DIRT_TILES.get());
			this.dropSelf(DIRT_TILE_STAIRS.get());
			this.dropSelf(DIRT_TILE_WALL.get());
			this.add(DIRT_TILE_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(DIRT_TILE_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);

			this.dropSelf(SMOOTH_MUD.get());
			this.add(SMOOTH_MUD_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(SMOOTH_MUD_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.dropSelf(CHISELED_MUD_BRICKS.get());
			this.dropSelf(SLABFISH_EFFIGY.get());

			this.dropSelf(GRASS_THATCH.get());
			this.dropSelf(GRASS_THATCH_STAIRS.get());
			this.add(GRASS_THATCH_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(GRASS_THATCH_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.dropSelf(CATTAIL_THATCH.get());
			this.dropSelf(CATTAIL_THATCH_STAIRS.get());
			this.add(CATTAIL_THATCH_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(CATTAIL_THATCH_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.dropSelf(DUCKWEED_THATCH.get());
			this.dropSelf(DUCKWEED_THATCH_STAIRS.get());
			this.add(DUCKWEED_THATCH_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(DUCKWEED_THATCH_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);

			this.add(BURIED_TRUFFLE.get(), (block) -> createSingleItemTableWithSilkTouch(block, EnvironmentalItems.TRUFFLE.get()));
			this.dropOther(LARGE_LILY_PAD.get(), Blocks.LILY_PAD);
			this.dropOther(GIANT_LILY_PAD.get(), Blocks.LILY_PAD);
			this.dropOther(DIRT_PATH.get(), Blocks.DIRT);
			this.dropOther(MYCELIUM_PATH.get(), Blocks.DIRT);
			this.dropOther(PODZOL_PATH.get(), Blocks.DIRT);
			this.add(CATTAIL_SPROUT.get(), block -> createCattailDrops(block, EnvironmentalItems.CATTAIL_FLUFF.get()));
			this.add(CATTAIL.get(), block -> createCattailDrops(block, block));
			this.add(CATTAIL_STALK.get(), noDrop());

			this.add(HIBISCUS_LEAVES.get(), (block) -> createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, DOUBLE_LEAVES_STICK_CHANCES))));
			this.dropSelf(HIBISCUS_LEAF_CARPET.get());
			this.add(HIBISCUS_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);

			this.add(MYCELIUM_SPROUTS.get(), BlockLoot::createShearsOnlyDrop);
			this.add(DUCKWEED.get(), BlockLoot::createShearsOnlyDrop);
			this.add(GIANT_TALL_GRASS.get(), (block) -> createDoublePlantWithOtherDrop(block, Blocks.GRASS, Items.WHEAT_SEEDS, 3, 0.125F));
			this.add(CUP_LICHEN.get(), EnvironmentalBlockLoot::createCupLichenDrops);
			this.add(CACTUS_BOBBLE.get(), noDrop());
			this.add(DWARF_SPRUCE.get(), EnvironmentalBlockLoot::createDwarfSpruceDrops);
			this.add(TALL_DWARF_SPRUCE.get(), EnvironmentalBlockLoot::createDwarfSpruceDrops);

			this.dropSelf(CATTAIL_FLUFF_BLOCK.get());
			this.dropSelf(CHERRY_CRATE.get());
			this.dropSelf(DUCK_EGG_CRATE.get());
			this.dropSelf(YAK_HAIR_BLOCK.get());
			this.dropSelf(YAK_HAIR_RUG.get());
			this.dropSelf(PINECONE.get());
			this.dropSelf(WAXED_PINECONE.get());

			this.dropSelf(WILLOW_PLANKS.get());
			this.dropSelf(VERTICAL_WILLOW_PLANKS.get());
			this.dropSelf(WILLOW_LOG.get());
			this.dropSelf(WILLOW_WOOD.get());
			this.dropSelf(STRIPPED_WILLOW_LOG.get());
			this.dropSelf(STRIPPED_WILLOW_WOOD.get());
			this.dropSelf(WILLOW_SIGNS.getFirst().get());
			this.dropSelf(WILLOW_PRESSURE_PLATE.get());
			this.dropSelf(WILLOW_TRAPDOOR.get());
			this.dropSelf(WILLOW_BUTTON.get());
			this.dropSelf(WILLOW_STAIRS.get());
			this.dropSelf(WILLOW_FENCE.get());
			this.dropSelf(WILLOW_FENCE_GATE.get());
			this.dropSelf(WILLOW_BOARDS.get());
			this.dropSelf(WILLOW_POST.get());
			this.dropSelf(STRIPPED_WILLOW_POST.get());
			this.dropSelf(WILLOW_HEDGE.get());
			this.dropSelf(WILLOW_LEAF_CARPET.get());
			this.add(WILLOW_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(WILLOW_SAPLING.get());
			this.dropPottedContents(POTTED_WILLOW_SAPLING.get());
			this.dropSelf(WILLOW_LADDER.get());
			this.add(WILLOW_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(WILLOW_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.add(WILLOW_DOOR.get(), BlockLoot::createDoorTable);
			this.add(WILLOW_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(WILLOW_CHESTS.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(WILLOW_CHESTS.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(WILLOW_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.add(WILLOW_LEAVES.get(), (block) -> createLeavesDrops(block, WILLOW_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(HANGING_WILLOW_LEAVES.get(), BlockLoot::createShearsOnlyDrop);

			this.dropSelf(PINE_PLANKS.get());
			this.dropSelf(VERTICAL_PINE_PLANKS.get());
			this.dropSelf(PINE_LOG.get());
			this.dropSelf(PINE_WOOD.get());
			this.dropSelf(STRIPPED_PINE_LOG.get());
			this.dropSelf(STRIPPED_PINE_WOOD.get());
			this.dropSelf(PINE_SIGNS.getFirst().get());
			this.dropSelf(PINE_PRESSURE_PLATE.get());
			this.dropSelf(PINE_TRAPDOOR.get());
			this.dropSelf(PINE_BUTTON.get());
			this.dropSelf(PINE_STAIRS.get());
			this.dropSelf(PINE_FENCE.get());
			this.dropSelf(PINE_FENCE_GATE.get());
			this.dropSelf(PINE_BOARDS.get());
			this.dropSelf(PINE_POST.get());
			this.dropSelf(STRIPPED_PINE_POST.get());
			this.dropSelf(PINE_HEDGE.get());
			this.dropSelf(PINE_LEAF_CARPET.get());
			this.add(PINE_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(PINE_SAPLING.get());
			this.dropPottedContents(POTTED_PINE_SAPLING.get());
			this.dropSelf(PINE_LADDER.get());
			this.add(PINE_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(PINE_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.add(PINE_DOOR.get(), BlockLoot::createDoorTable);
			this.add(PINE_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(PINE_CHESTS.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(PINE_CHESTS.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(PINE_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.add(PINE_LEAVES.get(), (block) -> createLeavesDrops(block, PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

			this.dropSelf(CHERRY_PLANKS.get());
			this.dropSelf(VERTICAL_CHERRY_PLANKS.get());
			this.dropSelf(CHERRY_LOG.get());
			this.dropSelf(CHERRY_WOOD.get());
			this.dropSelf(STRIPPED_CHERRY_LOG.get());
			this.dropSelf(STRIPPED_CHERRY_WOOD.get());
			this.dropSelf(CHERRY_SIGNS.getFirst().get());
			this.dropSelf(CHERRY_PRESSURE_PLATE.get());
			this.dropSelf(CHERRY_TRAPDOOR.get());
			this.dropSelf(CHERRY_BUTTON.get());
			this.dropSelf(CHERRY_STAIRS.get());
			this.dropSelf(CHERRY_FENCE.get());
			this.dropSelf(CHERRY_FENCE_GATE.get());
			this.dropSelf(CHERRY_BOARDS.get());
			this.dropSelf(CHERRY_POST.get());
			this.dropSelf(STRIPPED_CHERRY_POST.get());
			this.dropSelf(CHERRY_LADDER.get());
			this.add(CHERRY_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(CHERRY_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.add(CHERRY_DOOR.get(), BlockLoot::createDoorTable);
			this.add(CHERRY_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(CHERRY_CHESTS.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(CHERRY_CHESTS.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(CHERRY_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));

			this.add(CHERRY_LEAVES.get(), (block) -> createCherryLeavesDrops(block, CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.dropSelf(CHERRY_HEDGE.get());
			this.dropSelf(CHERRY_LEAF_CARPET.get());
			this.add(CHERRY_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(CHERRY_SAPLING.get());
			this.dropPottedContents(POTTED_CHERRY_SAPLING.get());

			this.add(CHEERFUL_CHERRY_LEAVES.get(), (block) -> createCherryLeavesDrops(block, CHEERFUL_CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.dropSelf(CHEERFUL_CHERRY_HEDGE.get());
			this.dropSelf(CHEERFUL_CHERRY_LEAF_CARPET.get());
			this.add(CHEERFUL_CHERRY_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(CHEERFUL_CHERRY_SAPLING.get());
			this.dropPottedContents(POTTED_CHEERFUL_CHERRY_SAPLING.get());

			this.add(MOODY_CHERRY_LEAVES.get(), (block) -> createCherryLeavesDrops(block, MOODY_CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.dropSelf(MOODY_CHERRY_HEDGE.get());
			this.dropSelf(MOODY_CHERRY_LEAF_CARPET.get());
			this.add(MOODY_CHERRY_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(MOODY_CHERRY_SAPLING.get());
			this.dropPottedContents(POTTED_MOODY_CHERRY_SAPLING.get());

			this.dropSelf(WISTERIA_PLANKS.get());
			this.dropSelf(VERTICAL_WISTERIA_PLANKS.get());
			this.dropSelf(WISTERIA_LOG.get());
			this.dropSelf(WISTERIA_WOOD.get());
			this.dropSelf(STRIPPED_WISTERIA_LOG.get());
			this.dropSelf(STRIPPED_WISTERIA_WOOD.get());
			this.dropSelf(WISTERIA_SIGNS.getFirst().get());
			this.dropSelf(WISTERIA_PRESSURE_PLATE.get());
			this.dropSelf(WISTERIA_TRAPDOOR.get());
			this.dropSelf(WISTERIA_BUTTON.get());
			this.dropSelf(WISTERIA_STAIRS.get());
			this.dropSelf(WISTERIA_FENCE.get());
			this.dropSelf(WISTERIA_FENCE_GATE.get());
			this.dropSelf(WISTERIA_BOARDS.get());
			this.dropSelf(WISTERIA_POST.get());
			this.dropSelf(STRIPPED_WISTERIA_POST.get());
			this.dropSelf(PINK_WISTERIA_HEDGE.get());
			this.dropSelf(BLUE_WISTERIA_HEDGE.get());
			this.dropSelf(PURPLE_WISTERIA_HEDGE.get());
			this.dropSelf(WHITE_WISTERIA_HEDGE.get());
			this.dropSelf(PINK_WISTERIA_LEAF_CARPET.get());
			this.dropSelf(BLUE_WISTERIA_LEAF_CARPET.get());
			this.dropSelf(PURPLE_WISTERIA_LEAF_CARPET.get());
			this.dropSelf(WHITE_WISTERIA_LEAF_CARPET.get());
			this.add(PINK_WISTERIA_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.add(BLUE_WISTERIA_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.add(PURPLE_WISTERIA_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.add(WHITE_WISTERIA_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);

			this.add(WISTERIA_LEAVES.get(), (block) -> createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, DOUBLE_LEAVES_STICK_CHANCES))));
			this.dropSelf(WISTERIA_LEAF_CARPET.get());
			this.add(WISTERIA_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(WISTERIA_HEDGE.get());

			this.dropSelf(PINK_WISTERIA_SAPLING.get());
			this.dropSelf(BLUE_WISTERIA_SAPLING.get());
			this.dropSelf(PURPLE_WISTERIA_SAPLING.get());
			this.dropSelf(WHITE_WISTERIA_SAPLING.get());

			this.dropPottedContents(POTTED_PINK_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_BLUE_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_PURPLE_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_WHITE_WISTERIA_SAPLING.get());

			this.dropSelf(WISTERIA_LADDER.get());
			this.add(WISTERIA_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(WISTERIA_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.add(WISTERIA_DOOR.get(), BlockLoot::createDoorTable);
			this.add(WISTERIA_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(WISTERIA_CHESTS.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(WISTERIA_CHESTS.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(WISTERIA_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));

			this.add(PINK_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, PINK_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(BLUE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, BLUE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(PURPLE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, PURPLE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(WHITE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, WHITE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

			this.add(PINK_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(BLUE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(PURPLE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(WHITE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
		}

		protected static LootTable.Builder createCupLichenDrops(Block block) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(List.of(2, 3, 4), (cups) -> {
				return SetItemCountFunction.setCount(ConstantValue.exactly((float) cups.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CupLichenBlock.CUPS, cups)));
			}))));
		}
		protected static LootTable.Builder createDwarfSpruceDrops(Block block) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(DWARF_SPRUCE.get()).when(HAS_SHEARS).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))))));
		}

		protected static LootTable.Builder createCattailDrops(Block block, ItemLike drop) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(drop).apply(List.of(2, 3), (cattails) -> {
				return SetItemCountFunction.setCount(ConstantValue.exactly((float) cattails.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CattailBlock.CATTAILS, cattails)));
			}))));
		}

		protected static LootTable.Builder createCherryLeavesDrops(Block block, Block sapling, float... saplingChances) {
			return createLeavesDrops(block, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(block, LootItem.lootTableItem(EnvironmentalItems.CHERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.055555557F, 0.0625F, 0.08333334F, 0.25F))));
		}

		protected static LootTable.Builder createWisteriaLeavesDrops(Block block, Block sapling, float... saplingChances) {
			LootItemCondition.Builder isBottom = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.HALF, Half.BOTTOM));
			LootItemCondition.Builder isTop = isBottom.invert();

			LootPool.Builder coloredLeafPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isBottom).add(LootItem.lootTableItem(block).when(HAS_SHEARS_OR_SILK_TOUCH).otherwise(applyExplosionCondition(sapling, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, saplingChances))));
			LootPool.Builder greenLeafPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isTop).add(LootItem.lootTableItem(WISTERIA_LEAVES.get()).when(HAS_SHEARS_OR_SILK_TOUCH));
			LootPool.Builder coloredStickPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isBottom).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES)));
			LootPool.Builder greenStickPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isTop).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, DOUBLE_LEAVES_STICK_CHANCES)));

			return LootTable.lootTable().withPool(coloredLeafPool).withPool(greenLeafPool).withPool(coloredStickPool).withPool(greenStickPool);
		}

		protected static LootTable.Builder createLeafPileDrops(Block block) {
			return createMultifaceBlockDrops(block, MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)));
		}

		protected static LootTable.Builder createVerticalSlabItemTable(Block block) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)))))));
		}

		protected static LootTable.Builder createDoublePlantWithOtherDrop(Block block, Block grass, Item drop, int count, float chance) {
			LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(grass).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))).otherwise(applyExplosionCondition(block, LootItem.lootTableItem(drop)).when(LootItemRandomChanceCondition.randomChance(chance)));
			return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(Environmental.MOD_ID)).collect(Collectors.toSet());
		}
	}

	private static class EnvironmentalEntityLoot extends EntityLoot {
		private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(PINECONE_GOLEM.get());

		@Override
		public void addTables() {
			this.add(DEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(REINDEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(DUCK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.DUCK.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			// this.add(FENNEC_FOX.get(), LootTable.lootTable());
			this.add(KOI.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.KOI.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));
			this.add(SLABFISH.get(), LootTable.lootTable());
			this.add(TAPIR.get(), LootTable.lootTable());
			this.add(YAK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.YAK_HAIR.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 3.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BEEF).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(ZEBRA.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(PINECONE_GOLEM.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(PINE_SAPLING.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
			this.add(EntityType.WANDERING_TRADER, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(EnvironmentalItems.MUSIC_DISC_LEAVING_HOME.get())).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))));
		}

		@Override
		public Iterable<EntityType<?>> getKnownEntities() {
			return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> ForgeRegistries.ENTITY_TYPES.getKey(entity).getNamespace().equals(Environmental.MOD_ID)).collect(Collectors.toSet());
		}

		@Override
		protected boolean isNonLiving(EntityType<?> entityType) {
			return !SPECIAL_LOOT_TABLE_TYPES.contains(entityType) && entityType.getCategory() == MobCategory.MISC;
		}
	}

	private static class EnvironmentalChestLoot extends ChestLoot {

		@Override
		public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
			consumer.accept(new ResourceLocation(Environmental.MOD_ID, "chests/log_cabin_junk"), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 6.0F))
							.add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
							.add(LootItem.lootTableItem(PINE_PLANKS.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 12.0F))))
							.add(LootItem.lootTableItem(PINE_LOG.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
							.add(LootItem.lootTableItem(STRIPPED_PINE_LOG.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
							.add(LootItem.lootTableItem(PINE_LEAVES.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
					)
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
							.add(LootItem.lootTableItem(Items.LANTERN).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
							.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
							.add(LootItem.lootTableItem(PINE_TRAPDOOR.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
							.add(LootItem.lootTableItem(PINE_SAPLING.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
							.add(LootItem.lootTableItem(PINECONE.get()).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
					)
			);

			consumer.accept(new ResourceLocation(Environmental.MOD_ID, "chests/log_cabin"), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
					)
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
							.add(LootItem.lootTableItem(Items.SHEARS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 1.0F))))
							.add(LootItem.lootTableItem(Items.SHEARS).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.UNBREAKING)))
					)
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
							.add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
					)
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 6.0F))
							.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
							.add(LootItem.lootTableItem(Items.STICK).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
							.add(LootItem.lootTableItem(Items.COAL).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
							.add(LootItem.lootTableItem(Items.LEATHER).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
							.add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
							.add(LootItem.lootTableItem(EnvironmentalItems.COOKED_VENISON.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
							.add(LootItem.lootTableItem(Items.RABBIT_HIDE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.RABBIT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
							.add(LootItem.lootTableItem(Items.COOKED_RABBIT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
							.add(LootItem.lootTableItem(Items.CAMPFIRE).setWeight(4))
					)

					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
							.add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(3))
							.add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(3))
							.add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(3))
							.add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(3))
							.add(LootItem.lootTableItem(Items.LEATHER_HELMET).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
							.add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
							.add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
							.add(LootItem.lootTableItem(Items.LEATHER_BOOTS).apply(EnchantRandomlyFunction.randomApplicableEnchantment())))
			);


			consumer.accept(new ResourceLocation(Environmental.MOD_ID, "chests/log_cabin_dispenser"), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.CARVED_PUMPKIN))));
			consumer.accept(new ResourceLocation(Environmental.MOD_ID, "chests/log_cabin_dropper"), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(PINE_SAPLING.get()))));
		}
	}
}