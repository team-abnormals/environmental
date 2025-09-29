package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.EffectsChangedModifier;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.data.server.EnvironmentalAdvancementProvider;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnvironmentalAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{EnvironmentalEntityTypes.SLABFISH.get(), EnvironmentalEntityTypes.DUCK.get(), EnvironmentalEntityTypes.DEER.get(), EnvironmentalEntityTypes.REINDEER.get(), EnvironmentalEntityTypes.YAK.get(), EnvironmentalEntityTypes.TAPIR.get(), EnvironmentalEntityTypes.ZEBRA.get()};

	public EnvironmentalAdvancementModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("nether/all_effects").selects("nether/all_effects").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.effects().and(MobEffects.HEALTH_BOOST).and(EnvironmentalMobEffects.SERENITY.get())));
		this.entry("nether/all_potions").selects("nether/all_potions").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.effects().and(MobEffects.HEALTH_BOOST)));

		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		EnvironmentalItems.HELPER.getDeferredRegister().getEntries().forEach(registryObject -> {
			Item item = registryObject.get();
			if (item.isEdible()) {
				balancedDiet.addCriterion(ForgeRegistries.ITEMS.getKey(item).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item));
			}
		});
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(RequirementsStrategy.AND).build());

		CriteriaModifier.Builder breedAllAnimals = CriteriaModifier.builder(this.modId);
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			breedAllAnimals.addCriterion(ForgeRegistries.ENTITY_TYPES.getKey(entityType).getPath(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityType)));
		}
		this.entry("husbandry/bred_all_animals").selects("husbandry/bred_all_animals").addModifier(breedAllAnimals.requirements(RequirementsStrategy.AND).build());

		CriteriaModifier.Builder adventuringTime = CriteriaModifier.builder(this.modId);
		EnvironmentalBiomes.NATURAL_BIOMES.forEach(biome -> {
			adventuringTime.addCriterion(biome.location().toString(), PlayerTrigger.TriggerInstance.located(LocationPredicate.inBiome(biome)));
		});
		this.entry("adventure/adventuring_time").selects("adventure/adventuring_time").addModifier(adventuringTime.requirements(RequirementsStrategy.AND).build());

		this.entry("husbandry/fishy_business").selects("husbandry/fishy_business").addModifier(CriteriaModifier.builder(this.modId).addCriterion("koi", FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.item().of(EnvironmentalItems.KOI.get()).build())).addIndexedRequirements(0, false, "koi").build());
		this.entry("husbandry/tactical_fishing").selects("husbandry/tactical_fishing").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("koi_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(EnvironmentalItems.KOI_BUCKET.get()).build()))
				.addCriterion("slabfish_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(EnvironmentalItems.SLABFISH_BUCKET.get()).build()))
				.addIndexedRequirements(0, false, "koi_bucket", "slabfish_bucket").build());

		this.entry("husbandry/plant_seed").selects("husbandry/plant_seed").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("cattail_sprouts", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(EnvironmentalBlocks.CATTAIL_SPROUT.get()))
				.addIndexedRequirements(0, false, "cattail_sprouts").build());

		this.compatSlabfishModifier(EnvironmentalConstants.ATMOSPHERIC, EnvironmentalSlabfishTypes.ATMOSPHERIC_SLABFISH);
		this.compatSlabfishModifier(EnvironmentalConstants.AUTUMNITY, EnvironmentalSlabfishTypes.AUTUMNITY_SLABFISH);
		this.compatSlabfishModifier(EnvironmentalConstants.ENDERGETIC, EnvironmentalSlabfishTypes.ENDERGETIC_SLABFISH);
	}

	public void compatSlabfishModifier(String modid, List<ResourceKey<SlabfishType>> slabfishTypes) {
		ConditionedResourceSelector selector = new ConditionedResourceSelector(new NamesResourceSelector(Environmental.location("husbandry/tame_all_slabfish")), new ModLoadedCondition(modid));
		CriteriaModifier.Builder tameAllSlabfish = CriteriaModifier.builder(this.modId);
		slabfishTypes.forEach(slabfish -> {
			tameAllSlabfish.addCriterion(slabfish.location().getPath(), EnvironmentalAdvancementProvider.slabfishCriterion(slabfish));
		});
		this.entry("husbandry/tame_all_slabfish_" + modid).selector(selector).addModifier(tameAllSlabfish.requirements(RequirementsStrategy.AND).build());
	}
}