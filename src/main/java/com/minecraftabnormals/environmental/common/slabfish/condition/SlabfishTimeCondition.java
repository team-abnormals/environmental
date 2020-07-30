package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the time is day or night.</p>
 *
 * @author Ocelot
 */
public class SlabfishTimeCondition implements SlabfishCondition {
    private final boolean day;

    private SlabfishTimeCondition(boolean day) {
        this.day = day;
    }

    /**
     * Creates a new {@link SlabfishTimeCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        return new SlabfishTimeCondition(!json.has("time") || !json.get("time").getAsString().equals("night"));
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.TIME;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return this.day ? context.isDay() : context.isNight();
    }
}
