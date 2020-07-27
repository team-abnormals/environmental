package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in the specified dimension.</p>
 *
 * @author Ocelot
 */
public class SlabfishDimensionCondition implements SlabfishCondition {
    private final ResourceLocation dimensionRegistryName;

    private SlabfishDimensionCondition(ResourceLocation dimensionRegistryName) {
        this.dimensionRegistryName = dimensionRegistryName;
    }

    /**
     * Creates a new {@link SlabfishDimensionCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        if (!json.has("dimension"))
            throw new JsonSyntaxException("'dimension' is required");
        return new SlabfishDimensionCondition(context.deserialize(json.get("dimension"), ResourceLocation.class));
    }

    @Override
    public SlabfishConditionType getType() {
        return SlabfishConditionType.DIMENSION;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return context.getDimension().equals(this.dimensionRegistryName);
    }
}
