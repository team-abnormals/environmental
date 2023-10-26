package com.teamabnormals.environmental.core.data.server;

import com.teamabnormals.environmental.common.advancement.UpgradeGearTrigger;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class EnvironmentalAdvancementProvider extends AdvancementProvider {

	public EnvironmentalAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
		Advancement explorerGear = createAdvancement("obtain_explorer_gear", "adventure", new ResourceLocation("adventure/sleep_in_bed"), Items.RABBIT_HIDE, FrameType.TASK, true, true, false)
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