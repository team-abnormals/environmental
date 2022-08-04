package com.teamabnormals.environmental.common.advancement;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * <p>A trigger used to detect nearby slabfish.</p>
 *
 * @author Ocelot
 */
public class SlabfishNearbyCriterionTrigger extends SimpleCriterionTrigger<SlabfishNearbyCriterionTrigger.Instance> {

	private static final ResourceLocation ID = new ResourceLocation(Environmental.MOD_ID, "slabfish");

	public ResourceLocation getId() {
		return ID;
	}

	public SlabfishNearbyCriterionTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite predicate, DeserializationContext parser) {
		if (!json.has("slabfish") || !json.get("slabfish").isJsonPrimitive() || !json.get("slabfish").getAsJsonPrimitive().isString())
			throw new JsonSyntaxException("'slabfish' required as string");
		return new SlabfishNearbyCriterionTrigger.Instance(predicate, new ResourceLocation(json.get("slabfish").getAsString()));
	}

	public void trigger(ServerPlayer player, Slabfish slabfish) {
		this.trigger(player, instance -> instance.slabfishType.equals(slabfish.getSlabfishType()));
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private final ResourceLocation slabfishType;

		public Instance(EntityPredicate.Composite predicate, ResourceLocation slabfishType) {
			super(SlabfishNearbyCriterionTrigger.ID, predicate);
			this.slabfishType = slabfishType;
		}

		@Override
		public JsonObject serializeToJson(SerializationContext serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.addProperty("block", this.slabfishType.toString());
			return jsonobject;
		}
	}
}
