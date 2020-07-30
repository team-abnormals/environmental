package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.*;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;

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
     * @return The type of condition this is
     */
    SlabfishConditionType getType();

    /**
     * <p>Deserializes a {@link SlabfishCondition} from JSON.</p>
     *
     * @author Ocelot
     */
    class Deserializer implements JsonDeserializer<SlabfishCondition> {
        private static SlabfishConditionType deserializeType(JsonElement element) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
                throw new JsonSyntaxException("Slabfish condition type expected to be a string");
            String name = element.getAsString();
            for (SlabfishConditionType type : SlabfishConditionType.values())
                if (type.name().equalsIgnoreCase(name))
                    return type;
            throw new JsonSyntaxException("Invalid slabfish condition type: " + name);
        }

        @Override
        public SlabfishCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            if (!jsonObject.has("type"))
                throw new JsonSyntaxException("Slabfish condition type is required");
            return deserializeType(jsonObject.get("type")).deserialize(jsonObject, context);
        }
    }
}
