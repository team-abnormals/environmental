package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is struck by lightning.</p>
 *
 * @author Ocelot
 */
public class SlabfishLightningCondition implements SlabfishCondition
{
    private final ResourceLocation slabfishType;

    private SlabfishLightningCondition(ResourceLocation slabfishType)
    {
        this.slabfishType = slabfishType;
    }

    @Override
    public SlabfishConditionType getType()
    {
        return SlabfishConditionType.LIGHTNING;
    }

    @Override
    public boolean test(SlabfishConditionContext context)
    {
        return context.isStruckByLightning() && this.slabfishType.equals(context.getSlabfishType());
    }

    /**
     * Creates a new {@link SlabfishLightningCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    @SuppressWarnings("unused")
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context)
    {
        return new SlabfishLightningCondition(context.deserialize(json.get("slabfishType"), ResourceLocation.class));
    }
}
