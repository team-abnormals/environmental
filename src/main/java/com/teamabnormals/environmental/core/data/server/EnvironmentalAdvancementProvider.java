package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EnvironmentalAdvancementProvider implements AdvancementGenerator {

	public static AdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, net.neoforged.neoforge.common.data.ExistingFileHelper helper) {
		return new AdvancementProvider(output, provider, helper, List.of(new EnvironmentalAdvancementProvider()));
	}

	@Override
	public void generate(Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
		createAdvancement("backpack_slabfish", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/tame_an_animal"), Items.CHEST, AdvancementType.TASK, true, true, false)
				.addCriterion("backpack_slabfish", EnvironmentalCriteriaTriggers.backpackSlabfish())
				.save(consumer, Environmental.MOD_ID + ":husbandry/backpack_slabfish");


		createAdvancement("place_koi_in_village", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/tactical_fishing"), EnvironmentalItems.KOI_BUCKET.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("place_koi_in_village", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setStructures(provider.lookupOrThrow(Registries.STRUCTURE).get(StructureTags.VILLAGE).get()), ItemPredicate.Builder.item().of(EnvironmentalItems.KOI_BUCKET)))
				.save(consumer, Environmental.MOD_ID + ":husbandry/place_koi_in_village");
		AdvancementHolder saddlePig = createAdvancement("saddle_pig", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/root"), Items.SADDLE, AdvancementType.TASK, true, true, false)
				.addCriterion("saddle_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.SADDLE),
						Optional.of(EntityPredicate.wrap(Builder.entity().of(EntityType.PIG).build()))))
				.save(consumer, Environmental.MOD_ID + ":husbandry/saddle_pig");

		createAdvancement("when_pigs_fly", "husbandry", saddlePig, Items.CARROT_ON_A_STICK, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("when_pigs_fly", EnvironmentalCriteriaTriggers.whenPigsFly())
				.save(consumer, Environmental.MOD_ID + ":husbandry/when_pigs_fly");

		AdvancementHolder throwMud = createAdvancement("throw_mud_at_pig", "husbandry", saddlePig, EnvironmentalItems.MUD_BALL.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("throw_mud_at_pig", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
						DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.MUD_BALL.get()))),
						Optional.of(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/throw_mud_at_pig");
		createAdvancement("plant_on_muddy_pig", "husbandry", throwMud, Items.RED_TULIP, AdvancementType.TASK, true, true, false)
				.addCriterion("plant_on_muddy_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS),
						Optional.of(EntityPredicate.wrap(Builder.entity().of(EntityType.PIG).build()))))
				.save(consumer, Environmental.MOD_ID + ":husbandry/plant_on_muddy_pig");

		AdvancementHolder feedPig = createAdvancement("truffle_shuffle", "husbandry", saddlePig, Items.GOLDEN_CARROT, AdvancementType.TASK, true, true, false)
				.addCriterion("truffle_shuffle", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.GOLDEN_CARROT),
						Optional.of(EntityPredicate.wrap(Builder.entity().of(EntityType.PIG).build()))))
				.save(consumer, Environmental.MOD_ID + ":husbandry/truffle_shuffle");
		createAdvancement("find_truffle", "husbandry", feedPig, EnvironmentalItems.TRUFFLE.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("find_truffle", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.TRUFFLE.get()))
				.save(consumer, Environmental.MOD_ID + ":husbandry/find_truffle");

		createAdvancement("shear_yak_with_pants", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/root"), EnvironmentalItems.YAK_PANTS.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("shear_yak_with_pants", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						Optional.of(EntityPredicate.wrap(Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().legs(ItemPredicate.Builder.item().of(EnvironmentalItems.YAK_PANTS.get())).build()).build())),
						ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR),
						Optional.of(EntityPredicate.wrap(Builder.entity().of(EnvironmentalEntityTypes.YAK.get()).build()))))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_yak_with_pants");

		createAdvancement("shear_cattail", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/root"), EnvironmentalItems.CATTAIL_FLUFF.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("shear_cattail", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
						LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(EnvironmentalBlocks.CATTAIL.get())),
						ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR)))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_cattail");

		createAdvancement("feed_deer_flower", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/root"), Items.APPLE, AdvancementType.TASK, true, true, false)
				.addCriterion("feed_deer_flower", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.DEER_PLANTABLES),
						Optional.of(EntityPredicate.wrap(Builder.entity().of(EnvironmentalEntityTypeTags.DEER).build()))))
				.save(consumer, Environmental.MOD_ID + ":husbandry/feed_deer_flower");

		Advancement.Builder tameSlabfish = createAdvancement("tame_all_slabfish", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/tame_an_animal"), Items.TROPICAL_FISH, AdvancementType.CHALLENGE, true, true, false);
		for (ResourceKey<SlabfishVariant> slabfish : provider.lookup(EnvironmentalRegistries.SLABFISH_VARIANT).get().listElementIds().filter(key -> !EnvironmentalSlabfishVariants.COMPAT_SLABFISH.contains(key)).sorted().toList()) {
			tameSlabfish.addCriterion(slabfish.location().toString(), slabfishCriterion(slabfish));
		}
		tameSlabfish.save(consumer, Environmental.MOD_ID + ":husbandry/tame_all_slabfish");
	}

	public static Criterion<TameAnimalTrigger.TriggerInstance> slabfishCriterion(ResourceKey<SlabfishVariant> slabfish) {
		CompoundTag tag = new CompoundTag();
		tag.putString("SlabfishType", slabfish.location().toString());
		return TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.SLABFISH.get()).nbt(new NbtPredicate(tag)));
	}

	private static Advancement.Builder createAdvancement(String name, String category, AdvancementHolder parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(parent).display(icon,
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return createAdvancement(name, category, Advancement.Builder.advancement().build(parent), icon, frame, showToast, announceToChat, hidden);
	}
}