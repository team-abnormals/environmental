package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in a currently ongoing raid.</p>
 *
 * @author Ocelot
 */
public class SlabfishRaidCondition implements SlabfishCondition {
    private SlabfishRaidCondition() {
    }

    /**
     * Creates a new {@link SlabfishRaidCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        return new SlabfishRaidCondition();
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.RAID;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return context.isInRaid();
    }
}
