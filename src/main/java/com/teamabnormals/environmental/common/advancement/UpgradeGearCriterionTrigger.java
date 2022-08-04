package com.teamabnormals.environmental.common.advancement;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class UpgradeGearCriterionTrigger extends SimpleCriterionTrigger<UpgradeGearCriterionTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation(Environmental.MOD_ID, "upgrade_gear");

	public ResourceLocation getId() {
		return ID;
	}

	public UpgradeGearCriterionTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite predicate, DeserializationContext parser) {
		if (!json.has("item") || !json.get("item").isJsonPrimitive() || !json.get("item").getAsJsonPrimitive().isString())
			throw new JsonSyntaxException("'item' required as string");
		return new UpgradeGearCriterionTrigger.Instance(predicate, new ResourceLocation(json.get("item").getAsString()));
	}

	public void trigger(ServerPlayer player, Item item) {
		this.trigger(player, instance -> instance.item.equals(item.getRegistryName()));
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private final ResourceLocation item;

		public Instance(EntityPredicate.Composite p_i231560_1_, ResourceLocation item) {
			super(UpgradeGearCriterionTrigger.ID, p_i231560_1_);
			this.item = item;
		}

		@Override
		public JsonObject serializeToJson(SerializationContext serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.addProperty("item", this.item.toString());
			return jsonobject;
		}
	}
}
