package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.*;
import net.minecraft.world.LightType;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is within the specified light values.</p>
 *
 * @author Ocelot
 */
public class SlabfishLightCondition implements SlabfishCondition
{
    private final Function<SlabfishConditionContext, Integer> lightGetter;
    private final int minLight;
    private final int maxLight;

    private SlabfishLightCondition(int minLight, int maxLight, @Nullable LightType lightType)
    {
        this.lightGetter = context -> lightType == null ? context.getLight() : context.getLightFor(lightType);
        this.minLight = minLight;
        this.maxLight = maxLight;
    }

    @Override
    public SlabfishConditionType getType()
    {
        return SlabfishConditionType.LIGHT_LEVEL;
    }

    @Override
    public boolean test(SlabfishConditionContext context)
    {
        return this.lightGetter.apply(context) >= this.minLight && this.lightGetter.apply(context) <= this.maxLight;
    }

    private static LightType deserializeLightType(JsonElement element) throws JsonParseException
    {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("Light type expected to be a string");
        String name = element.getAsString();
        for (LightType lightType : LightType.values())
            if (lightType.name().equalsIgnoreCase(name))
                return lightType;
        throw new JsonSyntaxException("Invalid light type: " + name);
    }

    /**
     * Creates a new {@link SlabfishLightCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    @SuppressWarnings("unused")
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context)
    {
        if ((json.has("min") || json.has("max")) && json.has("value"))
            throw new JsonSyntaxException("Either 'min' and 'max' or 'value' can be present.");
        if(!json.has("min") && !json.has("max") && !json.has("value"))
            throw new JsonSyntaxException("Either 'min' and 'max' or 'value' must be present.");
        LightType lightType = null;
        if (json.has("lightType"))
            lightType = deserializeLightType(json.get("lightType"));
        return json.has("value") ? new SlabfishLightCondition(json.get("value").getAsInt(), json.get("value").getAsInt(), lightType) : new SlabfishLightCondition(json.has("min") ? json.get("min").getAsInt() : Integer.MIN_VALUE, json.has("max") ? json.get("max").getAsInt() : Integer.MAX_VALUE, lightType);
    }
}
