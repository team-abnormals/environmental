package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.environmental.common.advancement.UpgradeGearTrigger;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class EnvironmentalAdvancementProvider extends AdvancementProvider {

	public EnvironmentalAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
		Advancement explorerGear = createAdvancement("obtain_explorer_gear", "adventure", new ResourceLocation("adventure/root"), Items.RABBIT_HIDE, FrameType.TASK, true, true, false)
				.requirements(RequirementsStrategy.OR)
				.addCriterion("thief_hood", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.THIEF_HOOD.get()))
				.addCriterion("healer_pouch", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.HEALER_POUCH.get()))
				.addCriterion("architect_belt", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.ARCHITECT_BELT.get()))
				.addCriterion("wanderer_boots", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.WANDERER_BOOTS.get()))
				.save(consumer, Environmental.MOD_ID + ":adventure/obtain_explorer_gear");
		createAdvancement("upgrade_thief_hood", "adventure", explorerGear, EnvironmentalItems.THIEF_HOOD.get(), FrameType.GOAL, true, true, false)
				.addCriterion("upgrade_thief_hood", UpgradeGearTrigger.TriggerInstance.upgradeGear(ItemPredicate.Builder.item().of(EnvironmentalItems.THIEF_HOOD.get())))
				.save(consumer, Environmental.MOD_ID + ":adventure/upgrade_thief_hood");
		createAdvancement("upgrade_healer_pouch", "adventure", explorerGear, EnvironmentalItems.HEALER_POUCH.get(), FrameType.GOAL, true, true, false)
				.addCriterion("upgrade_healer_pouch", UpgradeGearTrigger.TriggerInstance.upgradeGear(ItemPredicate.Builder.item().of(EnvironmentalItems.HEALER_POUCH.get())))
				.save(consumer, Environmental.MOD_ID + ":adventure/upgrade_healer_pouch");
		createAdvancement("upgrade_architect_belt", "adventure", explorerGear, EnvironmentalItems.ARCHITECT_BELT.get(), FrameType.GOAL, true, true, false)
				.addCriterion("upgrade_architect_belt", UpgradeGearTrigger.TriggerInstance.upgradeGear(ItemPredicate.Builder.item().of(EnvironmentalItems.ARCHITECT_BELT.get())))
				.save(consumer, Environmental.MOD_ID + ":adventure/upgrade_architect_belt");
		createAdvancement("upgrade_wanderer_boots", "adventure", explorerGear, EnvironmentalItems.WANDERER_BOOTS.get(), FrameType.GOAL, true, true, false)
				.addCriterion("upgrade_wanderer_boots", UpgradeGearTrigger.TriggerInstance.upgradeGear(ItemPredicate.Builder.item().of(EnvironmentalItems.WANDERER_BOOTS.get())))
				.save(consumer, Environmental.MOD_ID + ":adventure/upgrade_wanderer_boots");

		createAdvancement("backpack_slabfish", "husbandry", new ResourceLocation("husbandry/tame_an_animal"), Items.CHEST, FrameType.TASK, true, true, false)
				.addCriterion("backpack_slabfish", EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/backpack_slabfish");

		createAdvancement("place_koi_in_village", "husbandry", new ResourceLocation("husbandry/tactical_fishing"), EnvironmentalItems.KOI_BUCKET.get(), FrameType.TASK, true, true, false)
				.addCriterion("place_koi_in_village", EnvironmentalCriteriaTriggers.PLACE_KOI_IN_VILLAGE.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/place_koi_in_village");

		Advancement saddlePig = createAdvancement("saddle_pig", "husbandry", new ResourceLocation("husbandry/root"), Items.SADDLE, FrameType.TASK, true, true, false)
				.addCriterion("saddle_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.SADDLE),
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/saddle_pig");


		createAdvancement("when_pigs_fly", "husbandry", saddlePig, Items.CARROT_ON_A_STICK, FrameType.CHALLENGE, true, true, false)
				.addCriterion("when_pigs_fly", EnvironmentalCriteriaTriggers.WHEN_PIGS_FLY.createInstance())
				.save(consumer, Environmental.MOD_ID + ":husbandry/when_pigs_fly");

		Advancement throwMud = createAdvancement("throw_mud_at_pig", "husbandry", saddlePig, EnvironmentalItems.MUD_BALL.get(), FrameType.TASK, true, true, false)
				.addCriterion("throw_mud_at_pig", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
						DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().isProjectile(true).direct(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.MUD_BALL.get()))),
						EntityPredicate.Builder.entity().of(EntityType.PIG).build()))
				.save(consumer, Environmental.MOD_ID + ":husbandry/throw_mud_at_pig");
		createAdvancement("plant_on_muddy_pig", "husbandry", throwMud, Items.RED_TULIP, FrameType.TASK, true, true, false)
				.addCriterion("plant_on_muddy_pig", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS),
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/plant_on_muddy_pig");

		Advancement feedPig = createAdvancement("truffle_shuffle", "husbandry", saddlePig, Items.GOLDEN_CARROT, FrameType.TASK, true, true, false)
				.addCriterion("truffle_shuffle", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(Items.GOLDEN_CARROT),
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/truffle_shuffle");
		createAdvancement("find_truffle", "husbandry", feedPig, EnvironmentalItems.TRUFFLE.get(), FrameType.TASK, true, true, false)
				.addCriterion("find_truffle", InventoryChangeTrigger.TriggerInstance.hasItems(EnvironmentalItems.TRUFFLE.get()))
				.save(consumer, Environmental.MOD_ID + ":husbandry/find_truffle");

		createAdvancement("shear_yak_with_pants", "husbandry", new ResourceLocation("husbandry/root"), EnvironmentalItems.YAK_PANTS.get(), FrameType.TASK, true, true, false)
				.addCriterion("shear_yak_with_pants", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().legs(ItemPredicate.Builder.item().of(EnvironmentalItems.YAK_PANTS.get()).build()).build()).build()),
						ItemPredicate.Builder.item().of(Tags.Items.SHEARS),
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypes.YAK.get()).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_yak_with_pants");

		createAdvancement("shear_cattail", "husbandry", new ResourceLocation("husbandry/root"), EnvironmentalItems.CATTAIL_FLUFF.get(), FrameType.TASK, true, true, false)
				.addCriterion("shear_cattail", ItemInteractWithBlockTrigger.TriggerInstance.itemUsedOnBlock(
						LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(EnvironmentalBlocks.CATTAIL.get()).build()),
						ItemPredicate.Builder.item().of(Tags.Items.SHEARS)))
				.save(consumer, Environmental.MOD_ID + ":husbandry/shear_cattail");

		createAdvancement("feed_deer_flower", "husbandry", new ResourceLocation("husbandry/root"), Items.APPLE, FrameType.TASK, true, true, false)
				.addCriterion("feed_deer_flower", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
						ItemPredicate.Builder.item().of(EnvironmentalItemTags.DEER_PLANTABLES),
						EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EnvironmentalEntityTypeTags.DEER).build())))
				.save(consumer, Environmental.MOD_ID + ":husbandry/feed_deer_flower");
	}

	private static Advancement.Builder createAdvancement(String name, String category, Advancement parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(parent).display(icon,
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + Environmental.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return createAdvancement(name, category, Advancement.Builder.advancement().build(parent), icon, frame, showToast, announceToChat, hidden);
	}
}