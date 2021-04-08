package com.minecraftabnormals.environmental.common.entity.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class UpgradeGearCriteriaTrigger extends AbstractCriterionTrigger<UpgradeGearCriteriaTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation(Environmental.MOD_ID, "upgrade_gear");

	public ResourceLocation getId() {
		return ID;
	}

	public UpgradeGearCriteriaTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate predicate, ConditionArrayParser parser) {
		if (!json.has("item") || !json.get("item").isJsonPrimitive() || !json.get("item").getAsJsonPrimitive().isString())
			throw new JsonSyntaxException("'item' required as string");
		return new UpgradeGearCriteriaTrigger.Instance(predicate, new ResourceLocation(json.get("item").getAsString()));
	}

	public void trigger(ServerPlayerEntity player, Item item) {
		this.triggerListeners(player, instance -> instance.item.equals(item.getRegistryName()));
	}

	public static class Instance extends CriterionInstance {
		private final ResourceLocation item;

		public Instance(EntityPredicate.AndPredicate p_i231560_1_, ResourceLocation item) {
			super(UpgradeGearCriteriaTrigger.ID, p_i231560_1_);
			this.item = item;
		}

		@Override
		public JsonObject serialize(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serialize(serializer);
			jsonobject.addProperty("item", this.item.toString());
			return jsonobject;
		}
	}
}
