package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.*;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.network.PacketBuffer;
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
    private final SlabfishRarity rarity;
    private final SlabfishCondition[] conditions;

    public SlabfishType(SlabfishRarity rarity, SlabfishCondition[] conditions)
    {
        this.rarity = rarity;
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
     * @return The rarity of this slabfish
     */
    public SlabfishRarity getRarity()
    {
        return rarity;
    }

    /**
     * Writes this {@link SlabfishType} into the specified {@link PacketBuffer} for syncing with clients.
     *
     * @param buf The buffer to write into
     */
    public void writeTo(PacketBuffer buf)
    {
        buf.writeVarInt(this.rarity.ordinal());
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
        int rarity = buf.readVarInt();
        return new SlabfishType(rarity < 0 || rarity >= SlabfishRarity.values().length ? SlabfishRarity.COMMON : SlabfishRarity.values()[rarity], new SlabfishCondition[0]);
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
            return new SlabfishType(rarity, jsonObject.has("conditions") ? context.deserialize(jsonObject.get("conditions"), SlabfishCondition[].class) : new SlabfishCondition[0]);
        }
    }
}
