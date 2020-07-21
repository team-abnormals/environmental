package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.*;
import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Type;
import java.util.function.Predicate;

/**
 * <p>A single type of slabfish that exists.</p>
 *
 * @author Ocelot
 */
public class SlabfishType implements Predicate<SlabfishConditionContext>
{
    private ResourceLocation registryName;
    private final SlabfishRarity rarity;
    private final int priority;
    private final SlabfishCondition[] conditions;

    public SlabfishType(SlabfishRarity rarity, int priority, SlabfishCondition[] conditions)
    {
        this.rarity = rarity;
        this.priority = priority;
        this.conditions = conditions;
    }

    @Override
    public boolean test(SlabfishConditionContext slabfishEntity)
    {
        for (SlabfishCondition condition : this.conditions)
            if (!condition.test(slabfishEntity))
                return false;
        return true;
    }

    /**
     * @return The registry name of this slabfish
     */
    public ResourceLocation getRegistryName()
    {
        return registryName;
    }

    /**
     * @return The rarity of this slabfish
     */
    public SlabfishRarity getRarity()
    {
        return rarity;
    }

    /**
     * @return The priority of this slabfish. Used to determine what slabfish should be chosen over others when different types have passed conditions
     */
    public int getPriority()
    {
        return priority;
    }

    /**
     * Sets the registry name of this slabfish type.
     *
     * @param registryName The new registry name for this slabfish
     */
    SlabfishType setRegistryName(ResourceLocation registryName)
    {
        this.registryName = registryName;
        return this;
    }

    /**
     * Writes this {@link SlabfishType} into the specified {@link PacketBuffer} for syncing with clients.
     *
     * @param buf The buffer to write into
     */
    public void writeTo(PacketBuffer buf)
    {
        buf.writeResourceLocation(this.registryName);
        buf.writeVarInt(this.rarity.ordinal());
        buf.writeVarInt(this.priority);
    }

    @Override
    public String toString()
    {
        // TODO remove this println
        System.out.println(this.conditions.length);
        return "SlabfishType{" +
                "rarity=" + rarity +
                ", priority=" + priority +
                '}';
    }

    /**
     * Creates a new {@link SlabfishType} from the specified {@link PacketBuffer} on the client side.
     *
     * @param buf The buffer to read from
     * @return A new slabfish type created from the data in the buffer
     */
    @OnlyIn(Dist.CLIENT)
    public static SlabfishType readFrom(PacketBuffer buf)
    {
        ResourceLocation registryName = buf.readResourceLocation();
        SlabfishRarity rarity = SlabfishRarity.byId(buf.readVarInt());
        int priority = buf.readVarInt();
        return new SlabfishType(rarity, priority, new SlabfishCondition[0]).setRegistryName(registryName);
    }

    public static class Deserializer implements JsonDeserializer<SlabfishType>
    {
        private static SlabfishRarity deserializeRarity(JsonElement element) throws JsonParseException
        {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
                throw new JsonSyntaxException("Slabfish rarity expected to be a string");
            String name = element.getAsString();
            for (SlabfishRarity rarity : SlabfishRarity.values())
                if (rarity.name().equalsIgnoreCase(name))
                    return rarity;
            throw new JsonSyntaxException("Invalid slabfish rarity: " + name);
        }

        @Override
        public SlabfishType deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = json.getAsJsonObject();
            if (!jsonObject.has("rarity"))
                throw new JsonSyntaxException("Slabfish rarity is required");
            SlabfishRarity rarity = deserializeRarity(jsonObject.get("rarity"));
            return new SlabfishType(rarity, jsonObject.has("priority") ? jsonObject.get("priority").getAsInt() : 0, jsonObject.has("conditions") ? context.deserialize(jsonObject.get("conditions"), SlabfishCondition[].class) : new SlabfishCondition[0]);
        }
    }
}
