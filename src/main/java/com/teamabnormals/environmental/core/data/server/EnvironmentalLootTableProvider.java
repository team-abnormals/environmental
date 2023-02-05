package com.teamabnormals.environmental.core.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock.VerticalSlabType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes.*;

public class EnvironmentalLootTableProvider extends LootTableProvider {
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(EnvironmentalBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(EnvironmentalEntityLoot::new, LootContextParamSets.ENTITY));

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
		private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).or(HAS_SILK_TOUCH).invert();

		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

		@Override
		public void addTables() {
			this.dropSelf(CARTWHEEL.get());
			this.dropSelf(BLUEBELL.get());
			this.dropSelf(VIOLET.get());
			this.dropSelf(DIANTHUS.get());
			this.dropSelf(RED_LOTUS_FLOWER.get());
			this.dropSelf(WHITE_LOTUS_FLOWER.get());
			this.dropSelf(YELLOW_HIBISCUS.get());
			this.dropSelf(ORANGE_HIBISCUS.get());
			this.dropSelf(RED_HIBISCUS.get());
			this.dropSelf(PINK_HIBISCUS.get());
			this.dropSelf(MAGENTA_HIBISCUS.get());
			this.dropSelf(PURPLE_HIBISCUS.get());
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

			this.dropSelf(SMOOTH_MUD.get());
			this.dropSelf(SMOOTH_MUD_STAIRS.get());
			this.dropSelf(SMOOTH_MUD_WALL.get());
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

			this.add(BURIED_TRUFFLE.get(), (block) -> createOreDrop(block, EnvironmentalItems.TRUFFLE.get()));
			this.dropOther(LARGE_LILY_PAD.get(), Blocks.LILY_PAD);
			this.dropOther(GIANT_LILY_PAD.get(), Blocks.LILY_PAD);
			this.dropOther(MYCELIUM_PATH.get(), Blocks.DIRT);
			this.dropOther(PODZOL_PATH.get(), Blocks.DIRT);
			this.dropOther(CATTAIL_SPROUTS.get(), EnvironmentalItems.CATTAIL_SEEDS.get());
			this.add(CATTAIL_SPROUTS.get(), (block) -> LootTable.lootTable().withPool(LootPool.lootPool().add(applyExplosionDecay(block, LootItem.lootTableItem(EnvironmentalItems.CATTAIL_SEEDS.get()).when(LootItemRandomChanceCondition.randomChance(0.1F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))))));
			this.add(CATTAIL.get(), (block) -> createShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(EnvironmentalItems.CATTAIL_SEEDS.get()).when(LootItemRandomChanceCondition.randomChance(0.25F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)))));
			this.add(TALL_CATTAIL.get(), (block) -> createDoublePlantWithOtherDrop(block, CATTAIL.get(), EnvironmentalItems.CATTAIL_SEEDS.get(), 2, 0.5F).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)));

			this.add(MYCELIUM_SPROUTS.get(), BlockLoot::createShearsOnlyDrop);
			this.add(DUCKWEED.get(), BlockLoot::createShearsOnlyDrop);
			this.add(GIANT_TALL_GRASS.get(), (block) -> createDoublePlantWithOtherDrop(block, Blocks.GRASS, Items.WHEAT_SEEDS, 3, 0.125F));
			this.add(TALL_DEAD_BUSH.get(), (block) -> createDoublePlantWithOtherDrop(block, Blocks.DEAD_BUSH, Items.STICK, 2, 0.125F));

			this.dropSelf(CATTAIL_SEED_SACK.get());
			this.dropSelf(CHERRY_CRATE.get());
			this.dropSelf(DUCK_EGG_CRATE.get());
			this.dropSelf(TRUFFLE_CRATE.get());
			this.dropSelf(YAK_HAIR_BLOCK.get());
			this.dropSelf(YAK_HAIR_RUG.get());

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
			this.dropSelf(CHERRY_HEDGE.get());
			this.dropSelf(CHERRY_LEAF_CARPET.get());
			this.add(CHERRY_LEAF_PILE.get(), EnvironmentalBlockLoot::createLeafPileDrops);
			this.dropSelf(CHERRY_SAPLING.get());
			this.dropPottedContents(POTTED_CHERRY_SAPLING.get());
			this.dropSelf(CHERRY_LADDER.get());
			this.add(CHERRY_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(CHERRY_VERTICAL_SLAB.get(), EnvironmentalBlockLoot::createVerticalSlabItemTable);
			this.add(CHERRY_DOOR.get(), BlockLoot::createDoorTable);
			this.add(CHERRY_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(CHERRY_CHESTS.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(CHERRY_CHESTS.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(CHERRY_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
			this.add(CHERRY_LEAVES.get(), (block) -> createCherryLeavesDrop(block, CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

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

			this.add(PINK_WISTERIA_LEAVES.get(), (block) -> createLeavesDrops(block, PINK_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(BLUE_WISTERIA_LEAVES.get(), (block) -> createLeavesDrops(block, BLUE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(PURPLE_WISTERIA_LEAVES.get(), (block) -> createLeavesDrops(block, PURPLE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(WHITE_WISTERIA_LEAVES.get(), (block) -> createLeavesDrops(block, WHITE_WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

			this.add(PINK_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(BLUE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(PURPLE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
			this.add(WHITE_HANGING_WISTERIA_LEAVES.get(), BlockLoot::createShearsOnlyDrop);
		}

		protected static LootTable.Builder createCherryLeavesDrop(Block block, Block sapling, float... saplingChances) {
			return createLeavesDrops(block, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(block, LootItem.lootTableItem(EnvironmentalItems.CHERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.055555557F, 0.0625F, 0.08333334F, 0.25F))));
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

		@Override
		public void addTables() {
			this.add(DEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.VENISON.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(DUCK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.DUCK.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(FENNEC_FOX.get(), LootTable.lootTable());
			this.add(KOI.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.KOI.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));
			this.add(SLABFISH.get(), LootTable.lootTable());
			this.add(TAPIR.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.PORKCHOP).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(YAK.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(EnvironmentalItems.YAK_HAIR.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 3.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BEEF).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(ZOMBIE_DEER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(EntityType.WANDERING_TRADER, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(EnvironmentalItems.MUSIC_DISC_LEAVING_HOME.get())).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))));
		}

		@Override
		public Iterable<EntityType<?>> getKnownEntities() {
			return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> ForgeRegistries.ENTITY_TYPES.getKey(entity).getNamespace().equals(Environmental.MOD_ID)).collect(Collectors.toSet());
		}
	}
}