package com.teamabnormals.environmental.core.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CupLichenBlock;
import com.teamabnormals.environmental.common.block.DwarfSpruceHeadBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalLootTables;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes.*;

public class EnvironmentalLootTableProvider extends LootTableProvider {

	public EnvironmentalLootTableProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, BuiltInLootTables.all(), ImmutableList.of(
				new LootTableProvider.SubProviderEntry(EnvironmentalBlockLoot::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(EnvironmentalEntityLoot::new, LootContextParamSets.ENTITY),
				new LootTableProvider.SubProviderEntry(EnvironmentalChestLoot::new, LootContextParamSets.CHEST)
		), provider);
	}

	@Override
	protected void validate(WritableRegistry<LootTable> registry, ValidationContext context, ProblemReporter.Collector collector) {
	}

	public static class EnvironmentalBlockLoot extends BlockLootSubProvider {
		public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(net.neoforged.neoforge.common.Tags.Items.TOOLS_SHEAR));

		protected LootItemCondition.Builder doesNotHaveSilkTouch() {
			return this.hasSilkTouch().invert();
		}

		private LootItemCondition.Builder hasShearsOrSilkTouch() {
			return HAS_SHEARS.or(this.hasSilkTouch());
		}

		private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
			return this.hasShearsOrSilkTouch().invert();
		}

		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
		private static final float[] DOUBLE_LEAVES_SAPLING_CHANCES = new float[]{0.1F, 1.25F, 0.166666672F, 0.2F};
		private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
		private static final float[] DOUBLE_LEAVES_STICK_CHANCES = new float[]{0.04F, 0.044444446F, 0.05F, 0.066666670F, 0.2F};

		protected EnvironmentalBlockLoot(Provider provider) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		public void generate() {
			RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

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
			this.add(DIRT_BRICK_SLAB.get(), this::createSlabItemTable);
			this.dropSelf(DIRT_TILES.get());
			this.dropSelf(DIRT_TILE_STAIRS.get());
			this.dropSelf(DIRT_TILE_WALL.get());
			this.add(DIRT_TILE_SLAB.get(), this::createSlabItemTable);

			this.dropSelf(SMOOTH_MUD.get());
			this.add(SMOOTH_MUD_SLAB.get(), this::createSlabItemTable);
			this.dropSelf(CHISELED_MUD_BRICKS.get());
			this.dropSelf(SLABFISH_EFFIGY.get());

			this.dropSelf(GRASS_THATCH.get());
			this.dropSelf(GRASS_THATCH_STAIRS.get());
			this.add(GRASS_THATCH_SLAB.get(), this::createSlabItemTable);
			this.dropSelf(CATTAIL_THATCH.get());
			this.dropSelf(CATTAIL_THATCH_STAIRS.get());
			this.add(CATTAIL_THATCH_SLAB.get(), this::createSlabItemTable);
			this.dropSelf(DUCKWEED_THATCH.get());
			this.dropSelf(DUCKWEED_THATCH_STAIRS.get());
			this.add(DUCKWEED_THATCH_SLAB.get(), this::createSlabItemTable);

			this.add(BURIED_TRUFFLE.get(), (block) -> createSingleItemTableWithSilkTouch(block, EnvironmentalItems.TRUFFLE.get()));
			this.dropOther(DIRT_PATH.get(), Blocks.DIRT);
			this.dropOther(MYCELIUM_PATH.get(), Blocks.DIRT);
			this.dropOther(PODZOL_PATH.get(), Blocks.DIRT);
			this.add(CATTAIL_SPROUT.get(), block -> createCattailDrops(block, EnvironmentalItems.CATTAIL_FLUFF.get()));
			this.add(CATTAIL.get(), block -> createCattailDrops(block, block));
			this.add(CATTAIL_STALK.get(), noDrop());

			this.add(HIBISCUS_LEAVES.get(), (block) -> createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), DOUBLE_LEAVES_STICK_CHANCES))));
			this.add(HIBISCUS_LEAF_PILE.get(), this::createLeafPileDrops);

			this.add(MYCELIUM_SPROUTS.get(), BlockLootSubProvider::createShearsOnlyDrop);
			this.add(DUCKWEED.get(), BlockLootSubProvider::createShearsOnlyDrop);
			this.add(GIANT_TALL_GRASS.get(), (block) -> createDoublePlantWithOtherDrop(block, Blocks.SHORT_GRASS, Items.WHEAT_SEEDS, 3, 0.125F));
			this.add(CUP_LICHEN.get(), this::createCupLichenDrops);
			this.add(CACTUS_BOBBLE.get(), noDrop());

			this.add(DWARF_SPRUCE.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT.get(), (block) -> createDwarfSpruceDrops(block, false));
			this.add(DWARF_SPRUCE_TORCH.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT_TORCH.get(), (block) -> createDwarfSpruceDrops(block, false));
			this.add(DWARF_SPRUCE_SOUL_TORCH.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT_SOUL_TORCH.get(), (block) -> createDwarfSpruceDrops(block, false));
			this.add(DWARF_SPRUCE_REDSTONE_TORCH.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT_REDSTONE_TORCH.get(), (block) -> createDwarfSpruceDrops(block, false));
			this.add(DWARF_SPRUCE_ENDER_TORCH.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT_ENDER_TORCH.get(), (block) -> createDwarfSpruceDrops(block, false));
			this.add(DWARF_SPRUCE_CUPRIC_TORCH.get(), (block) -> createDwarfSpruceDrops(block, true));
			this.add(DWARF_SPRUCE_PLANT_CUPRIC_TORCH.get(), (block) -> createDwarfSpruceDrops(block, false));

			this.dropSelf(CATTAIL_FLUFF_BLOCK.get());
			this.dropSelf(CHERRY_CRATE.get());
			this.dropSelf(PLUM_CRATE.get());
			this.dropSelf(DUCK_EGG_CRATE.get());
			this.dropSelf(YAK_HAIR_BLOCK.get());
			this.dropSelf(YAK_HAIR_RUG.get());
			this.dropSelf(PINECONE.get());
			this.dropSelf(WAXED_PINECONE.get());

			this.dropSelf(WILLOW_PLANKS.get());
			this.dropSelf(WILLOW_LOG.get());
			this.dropSelf(WILLOW_WOOD.get());
			this.dropSelf(STRIPPED_WILLOW_LOG.get());
			this.dropSelf(STRIPPED_WILLOW_WOOD.get());
			this.dropSelf(WILLOW_SIGNS.getFirst().get());
			this.dropSelf(WILLOW_HANGING_SIGNS.getFirst().get());
			this.dropSelf(WILLOW_PRESSURE_PLATE.get());
			this.dropSelf(WILLOW_TRAPDOOR.get());
			this.dropSelf(WILLOW_BUTTON.get());
			this.dropSelf(WILLOW_STAIRS.get());
			this.dropSelf(WILLOW_FENCE.get());
			this.dropSelf(WILLOW_FENCE_GATE.get());
			this.dropSelf(WILLOW_BOARDS.get());
			this.add(WILLOW_LEAF_PILE.get(), this::createLeafPileDrops);
			this.dropSelf(WILLOW_SAPLING.get());
			this.dropPottedContents(POTTED_WILLOW_SAPLING.get());
			this.dropSelf(WILLOW_LADDER.get());
			this.add(WILLOW_SLAB.get(), this::createSlabItemTable);
			this.add(WILLOW_DOOR.get(), this::createDoorTable);
			this.add(WILLOW_BEEHIVE.get(), this::createBeeHiveDrop);
			this.add(WILLOW_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(TRAPPED_WILLOW_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(WILLOW_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.dropWhenSilkTouch(CHISELED_WILLOW_BOOKSHELF.get());
			this.add(WILLOW_LEAVES.get(), (block) -> createLeavesDrops(block, WILLOW_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(HANGING_WILLOW_LEAVES.get(), BlockLootSubProvider::createShearsOnlyDrop);

			this.dropSelf(PINE_PLANKS.get());
			this.dropSelf(PINE_LOG.get());
			this.dropSelf(PINE_WOOD.get());
			this.dropSelf(STRIPPED_PINE_LOG.get());
			this.dropSelf(STRIPPED_PINE_WOOD.get());
			this.dropSelf(PINE_SIGNS.getFirst().get());
			this.dropSelf(PINE_HANGING_SIGNS.getFirst().get());
			this.dropSelf(PINE_PRESSURE_PLATE.get());
			this.dropSelf(PINE_TRAPDOOR.get());
			this.dropSelf(PINE_BUTTON.get());
			this.dropSelf(PINE_STAIRS.get());
			this.dropSelf(PINE_FENCE.get());
			this.dropSelf(PINE_FENCE_GATE.get());
			this.dropSelf(PINE_BOARDS.get());
			this.add(PINE_LEAF_PILE.get(), this::createLeafPileDrops);
			this.dropSelf(PINE_SAPLING.get());
			this.dropPottedContents(POTTED_PINE_SAPLING.get());
			this.dropSelf(PINE_LADDER.get());
			this.add(PINE_SLAB.get(), this::createSlabItemTable);
			this.add(PINE_DOOR.get(), this::createDoorTable);
			this.add(PINE_BEEHIVE.get(), this::createBeeHiveDrop);
			this.add(PINE_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(TRAPPED_PINE_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(PINE_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.dropWhenSilkTouch(CHISELED_PINE_BOOKSHELF.get());
			this.add(PINE_LEAVES.get(), (block) -> createLeavesDrops(block, PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

			this.dropSelf(PLUM_PLANKS.get());
			this.dropSelf(PLUM_LOG.get());
			this.dropSelf(PLUM_WOOD.get());
			this.dropSelf(STRIPPED_PLUM_LOG.get());
			this.dropSelf(STRIPPED_PLUM_WOOD.get());
			this.dropSelf(PLUM_SIGNS.getFirst().get());
			this.dropSelf(PLUM_HANGING_SIGNS.getFirst().get());
			this.dropSelf(PLUM_PRESSURE_PLATE.get());
			this.dropSelf(PLUM_TRAPDOOR.get());
			this.dropSelf(PLUM_BUTTON.get());
			this.dropSelf(PLUM_STAIRS.get());
			this.dropSelf(PLUM_FENCE.get());
			this.dropSelf(PLUM_FENCE_GATE.get());
			this.dropSelf(PLUM_BOARDS.get());
			this.dropSelf(PLUM_LADDER.get());
			this.add(PLUM_SLAB.get(), this::createSlabItemTable);
			this.add(PLUM_DOOR.get(), this::createDoorTable);
			this.add(PLUM_BEEHIVE.get(), this::createBeeHiveDrop);
			this.add(PLUM_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(TRAPPED_PLUM_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(PLUM_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.dropWhenSilkTouch(CHISELED_PLUM_BOOKSHELF.get());

			this.add(PLUM_LEAVES.get(), (block) -> createPlumLeavesDrops(block, PLUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(PLUM_LEAF_PILE.get(), this::createLeafPileDrops);
			this.dropSelf(PLUM_SAPLING.get());
			this.dropPottedContents(POTTED_PLUM_SAPLING.get());

			this.add(CHEERFUL_PLUM_LEAVES.get(), (block) -> createPlumLeavesDrops(block, CHEERFUL_PLUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(CHEERFUL_PLUM_LEAF_PILE.get(), this::createLeafPileDrops);
			this.dropSelf(CHEERFUL_PLUM_SAPLING.get());
			this.dropPottedContents(POTTED_CHEERFUL_PLUM_SAPLING.get());

			this.add(MOODY_PLUM_LEAVES.get(), (block) -> createPlumLeavesDrops(block, MOODY_PLUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(MOODY_PLUM_LEAF_PILE.get(), this::createLeafPileDrops);
			this.dropSelf(MOODY_PLUM_SAPLING.get());
			this.dropPottedContents(POTTED_MOODY_PLUM_SAPLING.get());

			this.dropSelf(WISTERIA_PLANKS.get());
			this.dropSelf(WISTERIA_LOG.get());
			this.dropSelf(WISTERIA_WOOD.get());
			this.dropSelf(STRIPPED_WISTERIA_LOG.get());
			this.dropSelf(STRIPPED_WISTERIA_WOOD.get());
			this.dropSelf(WISTERIA_SIGNS.getFirst().get());
			this.dropSelf(WISTERIA_HANGING_SIGNS.getFirst().get());
			this.dropSelf(WISTERIA_PRESSURE_PLATE.get());
			this.dropSelf(WISTERIA_TRAPDOOR.get());
			this.dropSelf(WISTERIA_BUTTON.get());
			this.dropSelf(WISTERIA_STAIRS.get());
			this.dropSelf(WISTERIA_FENCE.get());
			this.dropSelf(WISTERIA_FENCE_GATE.get());
			this.dropSelf(WISTERIA_BOARDS.get());
			this.add(PINK_WISTERIA_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(BLUE_WISTERIA_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(PURPLE_WISTERIA_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(WHITE_WISTERIA_LEAF_PILE.get(), this::createLeafPileDrops);

			this.add(WISTERIA_LEAVES.get(), (block) -> createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), DOUBLE_LEAVES_STICK_CHANCES))));
			this.add(WISTERIA_LEAF_PILE.get(), this::createLeafPileDrops);

			this.dropSelf(PINK_WISTERIA_SAPLING.get());
			this.dropSelf(BLUE_WISTERIA_SAPLING.get());
			this.dropSelf(PURPLE_WISTERIA_SAPLING.get());
			this.dropSelf(WHITE_WISTERIA_SAPLING.get());

			this.dropPottedContents(POTTED_PINK_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_BLUE_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_PURPLE_WISTERIA_SAPLING.get());
			this.dropPottedContents(POTTED_WHITE_WISTERIA_SAPLING.get());

			this.dropSelf(WISTERIA_LADDER.get());
			this.add(WISTERIA_SLAB.get(), this::createSlabItemTable);
			this.add(WISTERIA_DOOR.get(), this::createDoorTable);
			this.add(WISTERIA_BEEHIVE.get(), this::createBeeHiveDrop);
			this.add(WISTERIA_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(TRAPPED_WISTERIA_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(WISTERIA_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.dropWhenSilkTouch(CHISELED_WISTERIA_BOOKSHELF.get());

			this.add(PINK_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, PINK_WISTERIA_SAPLING.get(), DOUBLE_LEAVES_SAPLING_CHANCES));
			this.add(BLUE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, BLUE_WISTERIA_SAPLING.get(), DOUBLE_LEAVES_SAPLING_CHANCES));
			this.add(PURPLE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, PURPLE_WISTERIA_SAPLING.get(), DOUBLE_LEAVES_SAPLING_CHANCES));
			this.add(WHITE_WISTERIA_LEAVES.get(), (block) -> createWisteriaLeavesDrops(block, WHITE_WISTERIA_SAPLING.get(), DOUBLE_LEAVES_SAPLING_CHANCES));

			this.add(PINK_HANGING_WISTERIA_LEAVES.get(), BlockLootSubProvider::createShearsOnlyDrop);
			this.add(BLUE_HANGING_WISTERIA_LEAVES.get(), BlockLootSubProvider::createShearsOnlyDrop);
			this.add(PURPLE_HANGING_WISTERIA_LEAVES.get(), BlockLootSubProvider::createShearsOnlyDrop);
			this.add(WHITE_HANGING_WISTERIA_LEAVES.get(), BlockLootSubProvider::createShearsOnlyDrop);
		}

		protected LootTable.Builder createCupLichenDrops(Block block) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(List.of(2, 3, 4), (cups) -> {
				return SetItemCountFunction.setCount(ConstantValue.exactly((float) cups.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CupLichenBlock.CUPS, cups)));
			}))));
		}

		protected LootTable.Builder createDwarfSpruceDrops(Block block, boolean isHead) {
			LootTable.Builder builder = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(DWARF_SPRUCE.get()).when(HAS_SHEARS).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))))));
			if (isHead)
				builder = builder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.NETHER_STAR)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DwarfSpruceHeadBlock.STAR, true))));
			return builder;
		}

		protected LootTable.Builder createCattailDrops(Block block, ItemLike drop) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(drop).apply(List.of(2, 3), (cattails) -> {
				return SetItemCountFunction.setCount(ConstantValue.exactly((float) cattails.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CattailBlock.CATTAILS, cattails)));
			}))));
		}

		protected LootTable.Builder createPlumLeavesDrops(Block block, Block sapling, float... saplingChances) {
			RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
			return createLeavesDrops(block, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch()).add(applyExplosionDecay(block, LootItem.lootTableItem(EnvironmentalItems.PLUM.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.05F, 0.055555557F, 0.0625F, 0.08333334F, 0.25F))));
		}

		protected LootTable.Builder createWisteriaLeavesDrops(Block block, Block sapling, float... saplingChances) {
			RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
			LootItemCondition.Builder isBottom = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.HALF, Half.BOTTOM));
			LootItemCondition.Builder isTop = isBottom.invert();

			LootPool.Builder coloredLeafPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isBottom).add(LootItem.lootTableItem(block).when(this.hasShearsOrSilkTouch()).otherwise(applyExplosionCondition(sapling, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), saplingChances))));
			LootPool.Builder greenLeafPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isTop).add(LootItem.lootTableItem(WISTERIA_LEAVES.get()).when(this.hasShearsOrSilkTouch()));
			LootPool.Builder coloredStickPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isBottom).when(this.doesNotHaveShearsOrSilkTouch()).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES)));
			LootPool.Builder greenStickPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(isTop).when(this.doesNotHaveShearsOrSilkTouch()).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), DOUBLE_LEAVES_STICK_CHANCES)));

			return LootTable.lootTable().withPool(coloredLeafPool).withPool(greenLeafPool).withPool(coloredStickPool).withPool(greenStickPool);
		}

		protected LootTable.Builder createLeafPileDrops(Block block) {
			return createMultifaceBlockDrops(block, HAS_SHEARS);
		}

		protected LootTable.Builder createDoublePlantWithOtherDrop(Block block, Block grass, Item drop, int count, float chance) {
			LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(grass).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR))).otherwise(applyExplosionCondition(block, LootItem.lootTableItem(drop)).when(LootItemRandomChanceCondition.randomChance(chance)));
			return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0))));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(Environmental.MOD_ID)).collect(Collectors.toSet());
		}
	}

	private static class EnvironmentalEntityLoot extends EntityLootSubProvider {
		private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(PINECONE_GOLEM.get());

		protected EnvironmentalEntityLoot(Provider provider) {
			super(FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		public void generate() {
			this.add(DEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(REINDEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(DUCK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.DUCK.get()).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(KOI.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.KOI.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));
			this.add(SLABFISH.get(), LootTable.lootTable());
			this.add(TAPIR.get(), LootTable.lootTable());
			this.add(YAK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.YAK_HAIR.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(1.0F, 3.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BEEF).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(ZEBRA.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(ZORSE.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(ZONKEY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
			this.add(PINECONE_GOLEM.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(PINE_SAPLING.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
			this.add(EntityType.WANDERING_TRADER, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(EnvironmentalItems.MUSIC_DISC_LEAVING_HOME.get())).when(LootItemEntityPropertyCondition.hasProperties(EntityTarget.ATTACKER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))));
		}

		@Override
		public Stream<EntityType<?>> getKnownEntityTypes() {
			return BuiltInRegistries.ENTITY_TYPE.stream().filter(entity -> BuiltInRegistries.ENTITY_TYPE.getKey(entity).getNamespace().equals(Environmental.MOD_ID) || entity == EntityType.WANDERING_TRADER);
		}

		@Override
		protected boolean canHaveLootTable(EntityType<?> entityType) {
			return SPECIAL_LOOT_TABLE_TYPES.contains(entityType) || entityType.getCategory() != MobCategory.MISC;
		}
	}

	private record EnvironmentalChestLoot(Provider registries) implements LootTableSubProvider {

		@Override
		public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
			RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

			consumer.accept(EnvironmentalLootTables.LOG_CABIN_JUNK, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 6.0F))
							.add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
							.add(LootItem.lootTableItem(DWARF_SPRUCE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
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

			consumer.accept(EnvironmentalLootTables.LOG_CABIN, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
							.add(LootItem.lootTableItem(Items.IRON_AXE))
					)
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(Items.SHEARS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 1.0F))).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(enchantments.getOrThrow(Enchantments.UNBREAKING))))
							.add(LootItem.lootTableItem(Items.SHEARS).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(enchantments.getOrThrow(Enchantments.UNBREAKING))).apply((new EnchantRandomlyFunction.Builder()).withEnchantment(enchantments.getOrThrow(Enchantments.EFFICIENCY))))
					)
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
							.add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
					)
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(EnvironmentalItems.LUMBERER_BANNER_PATTERN.get()))
							.add(LootItem.lootTableItem(EnvironmentalItems.HELPER_BANNER_PATTERN.get()))
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
							.add(LootItem.lootTableItem(Items.LEATHER_HELMET).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
							.add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
							.add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
							.add(LootItem.lootTableItem(Items.LEATHER_BOOTS).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries))))
			);


			consumer.accept(EnvironmentalLootTables.LOG_CABIN_DISPENSER, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.CARVED_PUMPKIN))));
			consumer.accept(EnvironmentalLootTables.LOG_CABIN_DROPPER, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(PINE_SAPLING.get()))));
		}
	}
}