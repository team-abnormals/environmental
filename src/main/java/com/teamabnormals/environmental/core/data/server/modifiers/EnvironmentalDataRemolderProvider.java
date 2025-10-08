package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.remolder.data.RemolderProvider;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.data.server.EnvironmentalLootTableProvider.EnvironmentalBlockLoot;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalBiomes;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.teamabnormals.blueprint.common.remolder.util.LootRemolders.*;

public class EnvironmentalDataRemolderProvider extends RemolderProvider {

	public EnvironmentalDataRemolderProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, Target.DATA_PACK, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		HolderGetter<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);
		LootItemCondition.Builder inBlossomWoods = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomes.getOrThrow(EnvironmentalBiomes.BLOSSOM_WOODS), biomes.getOrThrow(EnvironmentalBiomes.BLOSSOM_VALLEYS))));
		this.entry("loot_table/gameplay/fishing/fish")
				.path("loot_table/gameplay/fishing/fish")
				.remolder(addEntry(0, LootItem.lootTableItem(EnvironmentalItems.KOI.get()).setWeight(70).when(inBlossomWoods).build()));

		this.entry("loot_table/blocks/pink_petals")
				.path("loot_table/blocks/pink_petals")
				.remolder(replacePools(createPetalsDrops(Blocks.PINK_PETALS).build()));

		this.entry("loot_table/blocks/cherry_leaves")
				.path("loot_table/blocks/cherry_leaves")
				.remolder(addPool(LootPool.lootPool().name("environmental:cherries")
						.setRolls(ConstantValue.exactly(1.0F))
						.when(this.doesNotHaveShearsOrSilkTouch(provider))
						.add(LootItem.lootTableItem(EnvironmentalItems.CHERRIES.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
								.apply(ApplyExplosionDecay.explosionDecay())
								.when(BonusLevelTableCondition.bonusLevelFlatChance(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.05F, 0.055555557F, 0.0625F, 0.08333334F, 0.25F)))
						.build()));
	}

	protected static LootPool.Builder createPetalsDrops(Block block) {
		return LootPool.lootPool().name("environmental:pink_petals").when(EnvironmentalBlockLoot.HAS_SHEARS).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(IntStream.rangeClosed(1, 4).boxed().toList(), (amount) -> {
			return SetItemCountFunction.setCount(ConstantValue.exactly((float) amount)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PinkPetalsBlock.AMOUNT, amount)));
		})).apply(ApplyExplosionDecay.explosionDecay());
	}

	private LootItemCondition.Builder hasShearsOrSilkTouch(Provider provider) {
		return EnvironmentalBlockLoot.HAS_SHEARS.or(this.hasSilkTouch(provider));
	}

	private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch(Provider provider) {
		return this.hasShearsOrSilkTouch(provider).invert();
	}

	protected LootItemCondition.Builder hasSilkTouch(Provider provider) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
		return MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(registrylookup.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))));
	}
}