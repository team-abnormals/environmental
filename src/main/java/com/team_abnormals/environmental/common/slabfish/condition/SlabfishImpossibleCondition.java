package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <p>A {@link SlabfishCondition} that returns <code>false</code>.</p>
 *
 * @author Ocelot
 */
public class SlabfishImpossibleCondition implements SlabfishCondition {
    public SlabfishImpossibleCondition() {
    }

    /**
     * Creates a new {@link SlabfishImpossibleCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        return new SlabfishImpossibleCondition();
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.IMPOSSIBLE;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return false;
    }
}
