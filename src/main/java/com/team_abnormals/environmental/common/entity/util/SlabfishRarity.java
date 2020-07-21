package com.team_abnormals.environmental.common.entity.util;

import com.google.gson.*;
import net.minecraft.util.text.TextFormatting;

import java.lang.reflect.Type;

public enum SlabfishRarity
{
    COMMON(TextFormatting.GRAY),
    UNCOMMON(TextFormatting.GREEN),
    RARE(TextFormatting.AQUA),
    EPIC(TextFormatting.LIGHT_PURPLE),
    LEGENDARY(TextFormatting.GOLD);

    private final TextFormatting color;

    SlabfishRarity(TextFormatting color)
    {
        this.color = color;
    }

    public TextFormatting getColor()
    {
        return color;
    }

    /**
     * <p>Deserializes a {@link SlabfishRarity} from JSON.</p>
     *
     * @author Ocelot
     */
    public static class Deserializer implements JsonDeserializer<SlabfishRarity>
    {
        @Override
        public SlabfishRarity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            String name = json.getAsString().trim();
            for (SlabfishRarity rarity : values())
                if (rarity.name().equalsIgnoreCase(name))
                    return rarity;
            throw new JsonSyntaxException("Invalid slabfish rarity: " + name);
        }
    }
}
