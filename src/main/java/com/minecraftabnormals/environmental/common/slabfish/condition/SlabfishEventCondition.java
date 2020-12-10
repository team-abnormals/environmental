package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.*;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if spawned under the specified actions.</p>
 *
 * @author Ocelot
 */
public class SlabfishEventCondition implements SlabfishCondition {
    private final byte events;

    public SlabfishEventCondition(SlabfishConditionContext.Event[] events) {
        byte flag = 0;
        for (SlabfishConditionContext.Event event : events)
            flag |= (1 << event.ordinal());
        this.events = flag;
    }

    private static SlabfishConditionContext.Event deserializeEvent(JsonElement element) {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("Slabfish condition event expected to be a string");
        String name = element.getAsString();
        for (SlabfishConditionContext.Event event : SlabfishConditionContext.Event.values())
            if (event.name().equalsIgnoreCase(name))
                return event;
        throw new JsonSyntaxException("Invalid slabfish condition event type: " + name);
    }

    /**
     * Creates a new {@link SlabfishEventCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        if (!json.has("events"))
            throw new JsonSyntaxException("'events' must be present.");

        JsonArray eventsJson = json.getAsJsonArray("events");
        SlabfishConditionContext.Event[] events = new SlabfishConditionContext.Event[eventsJson.size()];
        for (int i = 0; i < eventsJson.size(); i++)
            events[i] = deserializeEvent(eventsJson.get(i));

        return new SlabfishEventCondition(events);
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return ((this.events >> context.getEvent().ordinal()) & 1) > 0;
    }
}
