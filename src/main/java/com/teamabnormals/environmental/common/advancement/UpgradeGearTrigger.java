package com.teamabnormals.environmental.common.advancement;

import com.google.gson.JsonObject;
import com.teamabnormals.environmental.common.advancement.UpgradeGearTrigger.TriggerInstance;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class UpgradeGearTrigger extends SimpleCriterionTrigger<TriggerInstance> {
	private static final ResourceLocation ID = new ResourceLocation(Environmental.MOD_ID, "upgrade_gear");

	public ResourceLocation getId() {
		return ID;
	}

	public TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite predicate, DeserializationContext parser) {
		ItemPredicate itempredicate = ItemPredicate.fromJson(json.get("item"));
		return new TriggerInstance(predicate, itempredicate);
	}

	public void trigger(ServerPlayer player, ItemStack item) {
		this.trigger(player, instance -> instance.item.matches(item));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;

		public TriggerInstance(EntityPredicate.Composite p_i231560_1_, ItemPredicate item) {
			super(UpgradeGearTrigger.ID, p_i231560_1_);
			this.item = item;
		}

		public static TriggerInstance upgradeGear(ItemPredicate.Builder builder) {
			return new TriggerInstance(EntityPredicate.Composite.ANY, builder.build());
		}

		@Override
		public JsonObject serializeToJson(SerializationContext serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("item", this.item.serializeToJson());
			return jsonobject;
		}
	}
}
