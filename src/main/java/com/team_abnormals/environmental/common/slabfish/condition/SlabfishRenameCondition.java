package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the slabfish names are met.</p>
 *
 * @author Ocelot
 */
public class SlabfishRenameCondition implements SlabfishCondition
{
    private final String[] names;
    private final boolean caseSensitive;

    public SlabfishRenameCondition(String[] names, boolean caseSensitive)
    {
        this.names = names;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public SlabfishConditionType getType()
    {
        return SlabfishConditionType.RENAME;
    }

    @Override
    public boolean test(SlabfishConditionContext context)
    {
        for (String name : this.names)
            if (this.caseSensitive ? name.equals(context.getName()) : name.equalsIgnoreCase(context.getName()))
                return true;
        return false;
    }

    /**
     * Creates a new {@link SlabfishRenameCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context)
    {
        return new SlabfishRenameCondition(context.deserialize(json.get("names"), String[].class), json.has("caseSensitive") && json.get("caseSensitive").getAsBoolean());
    }
}
