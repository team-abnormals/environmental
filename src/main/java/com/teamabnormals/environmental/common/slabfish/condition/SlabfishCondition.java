package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.*;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Type;
import java.util.function.Predicate;

/**
 * <p>A condition that can be used to determine whether or not a {@link SlabfishType} can be added.</p>
 *
 * @author Ocelot
 */
public interface SlabfishCondition extends Predicate<SlabfishConditionContext> {
	/**
	 * Determines if this condition is met based on the specified context.
	 *
	 * @param context The context of the test
	 * @return Whether or not this condition passed
	 */
	@Override
	boolean test(SlabfishConditionContext context);

	/**
	 * <p>Deserializes a {@link SlabfishCondition} from JSON.</p>
	 *
	 * @author Ocelot
	 */
	class Deserializer implements JsonDeserializer<SlabfishCondition> {
		@Override
		public SlabfishCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			if (!jsonObject.has("type"))
				throw new JsonSyntaxException("Slabfish condition type is required");

			ResourceLocation type = context.deserialize(jsonObject.get("type"), ResourceLocation.class);
			if (!EnvironmentalRegistries.SLABFISH_CONDITIONS.containsKey(type))
				throw new JsonSyntaxException("Invalid slabfish condition type: " + type);

			Factory factory = EnvironmentalRegistries.SLABFISH_CONDITIONS.getValue(type);
			if (factory == null)
				throw new JsonSyntaxException("Invalid slabfish condition type: " + type);

			return factory.create(jsonObject, context);
		}
	}

	/**
	 * <p>Creates new slabfish conditions from JSON.</p>
	 *
	 * @author Ocelot
	 */
	interface Factory extends IForgeRegistryEntry<Factory> {
		/**
		 * Creates a new condition from the specified json with the provided context
		 *
		 * @param json    The json to parse
		 * @param context The JSON context
		 * @return A new slabfish condition
		 * @throws JsonParseException If there is any issue with the json
		 */
		SlabfishCondition create(JsonObject json, JsonDeserializationContext context) throws JsonParseException;
	}
}
