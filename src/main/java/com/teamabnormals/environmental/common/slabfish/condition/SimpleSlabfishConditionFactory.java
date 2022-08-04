package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition.SlabfishConditionFactory;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.BiFunction;

/**
 * <p>A simple implementation of {@link SlabfishConditionFactory}.</p>
 *
 * @author Ocelot
 */
public class SimpleSlabfishConditionFactory extends ForgeRegistryEntry<SlabfishConditionFactory> implements SlabfishConditionFactory {
	private final BiFunction<JsonObject, JsonDeserializationContext, SlabfishCondition> jsonDeserializer;

	public SimpleSlabfishConditionFactory(BiFunction<JsonObject, JsonDeserializationContext, SlabfishCondition> jsonDeserializer) {
		this.jsonDeserializer = jsonDeserializer;
	}

	@Override
	public SlabfishCondition create(JsonObject json, JsonDeserializationContext context) throws JsonParseException {
		return this.jsonDeserializer.apply(json, context);
	}
}
