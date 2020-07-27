package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the player that bred two slabfish together has insomnia.</p>
 *
 * @author Ocelot
 */
public class SlabfishInsomniaCondition implements SlabfishCondition {
    private SlabfishInsomniaCondition() {
    }

    /**
     * Creates a new {@link SlabfishInsomniaCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        return new SlabfishInsomniaCondition();
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.INSOMNIA;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return context.isBreederInsomnia();
    }
}
