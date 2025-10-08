package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.EffectsChangedModifier;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.data.server.EnvironmentalAdvancementProvider;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalMobEffects;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EnvironmentalAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{EnvironmentalEntityTypes.SLABFISH.get(), EnvironmentalEntityTypes.DUCK.get(), EnvironmentalEntityTypes.DEER.get(), EnvironmentalEntityTypes.REINDEER.get(), EnvironmentalEntityTypes.YAK.get(), EnvironmentalEntityTypes.TAPIR.get(), EnvironmentalEntityTypes.ZEBRA.get()};

	public EnvironmentalAdvancementModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("nether/all_effects").selects("nether/all_effects").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.Builder.effects().and(MobEffects.HEALTH_BOOST).and(EnvironmentalMobEffects.SERENITY).build().get()));
		this.entry("nether/all_potions").selects("nether/all_potions").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.Builder.effects().and(MobEffects.HEALTH_BOOST).build().get()));

		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		Collection<DeferredHolder<Item, ? extends Item>> items = EnvironmentalItems.ITEMS.getDeferredRegister().getEntries().stream().filter(i -> i.get().getDefaultInstance().getFoodProperties(null) != null).toList();
		items.forEach(item -> {
			balancedDiet.addCriterion(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item.get()));
		});
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(Strategy.AND).build());

		CriteriaModifier.Builder breedAllAnimals = CriteriaModifier.builder(this.modId);
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			breedAllAnimals.addCriterion(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityType)));
		}
		this.entry("husbandry/bred_all_animals").selects("husbandry/bred_all_animals").addModifier(breedAllAnimals.requirements(Strategy.AND).build());

		this.entry("husbandry/whole_pack").selects("husbandry/whole_pack").addModifier(addTamedWolfVariants(provider).requirements(Strategy.AND).build());

		CriteriaModifier.Builder adventuringTime = CriteriaModifier.builder(this.modId);
		RegistryLookup<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);
		EnvironmentalBiomes.NATURAL_BIOMES.forEach(biome -> {
			adventuringTime.addCriterion(biome.location().toString(), PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(biome))));
		});
		this.entry("adventure/adventuring_time").selects("adventure/adventuring_time").addModifier(adventuringTime.requirements(Strategy.AND).build());

		this.entry("husbandry/fishy_business").selects("husbandry/fishy_business").addModifier(CriteriaModifier.builder(this.modId).addCriterion("koi", FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.empty(), Optional.of(ItemPredicate.Builder.item().of(EnvironmentalItems.KOI.get()).build()))).addIndexedRequirements(0, false, "koi").build());
		this.entry("husbandry/tactical_fishing").selects("husbandry/tactical_fishing").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("koi_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(EnvironmentalItems.KOI_BUCKET.get())))
				.addCriterion("slabfish_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(EnvironmentalItems.SLABFISH_BUCKET.get())))
				.addIndexedRequirements(0, false, "koi_bucket", "slabfish_bucket").build());

		this.entry("husbandry/plant_seed").selects("husbandry/plant_seed").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("cattail_sprouts", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(EnvironmentalBlocks.CATTAIL_SPROUT.get()))
				.addIndexedRequirements(0, false, "cattail_sprouts").build());

		this.compatSlabfishModifier(EnvironmentalConstants.ATMOSPHERIC, EnvironmentalSlabfishVariants.ATMOSPHERIC_SLABFISH);
		this.compatSlabfishModifier(EnvironmentalConstants.AUTUMNITY, EnvironmentalSlabfishVariants.AUTUMNITY_SLABFISH);
		this.compatSlabfishModifier(EnvironmentalConstants.ENDERGETIC, EnvironmentalSlabfishVariants.ENDERGETIC_SLABFISH);
	}

	public void compatSlabfishModifier(String modid, List<ResourceKey<SlabfishVariant>> slabfishTypes) {
		ConditionedResourceSelector selector = new ConditionedResourceSelector(new NamesResourceSelector(Environmental.location("husbandry/tame_all_slabfish")), new ModLoadedCondition(modid));
		CriteriaModifier.Builder tameAllSlabfish = CriteriaModifier.builder(this.modId);
		slabfishTypes.forEach(slabfish -> {
			tameAllSlabfish.addCriterion(slabfish.location().getPath(), EnvironmentalAdvancementProvider.slabfishCriterion(slabfish));
		});
		this.entry("husbandry/tame_all_slabfish_" + modid).selector(selector).addModifier(tameAllSlabfish.requirements(Strategy.AND).build());
	}

	private CriteriaModifier.Builder addTamedWolfVariants(HolderLookup.Provider registries) {
		CriteriaModifier.Builder builder = CriteriaModifier.builder(this.modId);
		HolderLookup.RegistryLookup<WolfVariant> registrylookup = registries.lookupOrThrow(Registries.WOLF_VARIANT);
		registrylookup.listElementIds()
				.filter(key -> key.location().getNamespace().equals(Environmental.MOD_ID))
				.sorted(Comparator.comparing(ResourceKey::location))
				.forEach(variant -> {
							Holder<WolfVariant> holder = registrylookup.getOrThrow(variant);
							builder.addCriterion(variant.location().toString(), TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.wolfVariant(HolderSet.direct(holder)))));
						}
				);
		return builder;
	}
}