package com.teamabnormals.environmental.core.data.server.modifiers;

public class EnvironmentalLootModifierProvider {// extends LootModifierProvider {
//
//	public EnvironmentalLootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
//		super(Environmental.MOD_ID, output, provider);
//	}
//
//	@Override
//	protected void registerEntries(Provider provider) {
//		LootItemCondition.Builder inBlossomWoods = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(EnvironmentalBiomes.BLOSSOM_WOODS));
//		LootItemCondition.Builder inBlossomValleys = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(EnvironmentalBiomes.BLOSSOM_VALLEYS));
//		this.entry(BuiltInLootTables.FISHING_FISH.getPath()).selects(BuiltInLootTables.FISHING_FISH).addModifier(new LootPoolEntriesModifier(false, 0, LootItem.lootTableItem(EnvironmentalItems.KOI.get()).setWeight(70).when(inBlossomWoods.or(inBlossomValleys)).build()));
//
//		this.entry("blocks/pink_petals").selects("blocks/pink_petals").addModifier(new LootPoolsModifier(List.of(createPetalsDrops(Blocks.PINK_PETALS).build()), true));
//		this.entry("blocks/cherry_leaves").selects("blocks/cherry_leaves").addModifier(new LootPoolsModifier(List.of(
//				LootPool.lootPool().name("environmental:cherries").setRolls(ConstantValue.exactly(1.0F)).when(EnvironmentalBlockLoot.HAS_NO_SHEARS_OR_SILK_TOUCH).add(LootItem.lootTableItem(EnvironmentalItems.CHERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyExplosionDecay.explosionDecay()).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.055555557F, 0.0625F, 0.08333334F, 0.25F))).build()
//		), false));
//	}
//
//	protected static Builder createPetalsDrops(Block block) {
//		return LootPool.lootPool().name("environmental:pink_petals").when(EnvironmentalBlockLoot.HAS_SHEARS).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(IntStream.rangeClosed(1, 4).boxed().toList(), (amount) -> {
//			return SetItemCountFunction.setCount(ConstantValue.exactly((float) amount)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PinkPetalsBlock.AMOUNT, amount)));
//		})).apply(ApplyExplosionDecay.explosionDecay());
//	}
}