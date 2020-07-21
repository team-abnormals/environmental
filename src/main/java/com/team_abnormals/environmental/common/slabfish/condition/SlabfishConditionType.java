package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import java.util.function.BiFunction;

/**
 * <p>Different types of conditions that exist for slabfish.</p>
 *
 * @author Ocelot
 */
public enum SlabfishConditionType
{
    OR(SlabfishOrCondition::deserialize), RENAME(SlabfishRenameCondition::deserialize);//, BIOME, HEIGHT, LIGHT_LEVEL, LIGHTNING, DIMENSION, PARENTS, RAID;

    private final BiFunction<JsonObject, JsonDeserializationContext, SlabfishCondition> jsonDeserializer;

    SlabfishConditionType(BiFunction<JsonObject, JsonDeserializationContext, SlabfishCondition> jsonDeserializer)
    {
        this.jsonDeserializer = jsonDeserializer;
    }

    /**
     * Deserializes this condition type from JSON.
     *
     * @param json    The json to deserialize this
     * @param context The context of JSON deserialization
     * @return A new condition deserialized from JSON
     */
    public SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context)
    {
        return this.jsonDeserializer.apply(json, context);
    }
}
