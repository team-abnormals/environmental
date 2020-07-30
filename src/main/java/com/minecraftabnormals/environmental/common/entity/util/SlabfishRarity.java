package com.minecraftabnormals.environmental.common.entity.util;

import net.minecraft.util.text.TextFormatting;

/**
 * <p>Determines the different rarity types for slabfish.</p>
 */
public enum SlabfishRarity
{
    COMMON(TextFormatting.GRAY, 1.0f),
    UNCOMMON(TextFormatting.GREEN, 0.55f),
    RARE(TextFormatting.AQUA, 0.15f),
    EPIC(TextFormatting.LIGHT_PURPLE, 0.08f),
    LEGENDARY(TextFormatting.GOLD, 0.02f);

    private final TextFormatting color;
    private final float chance;

    SlabfishRarity(TextFormatting color, float chance)
    {
        this.color = color;
        this.chance = chance;
    }

    /**
     * @return The {@link TextFormatting} that should be used when displaying this rarity
     */
    public TextFormatting getFormatting()
    {
        return color;
    }

    /**
     * @return The percentage chance of getting this rarity
     */
    public float getChance()
    {
        return chance;
    }

    /**
     * Fetches a rarity by a percentage chance.
     *
     * @param chance The chance randomly select a rarity
     * @return The rarity associated with that chance
     */
    public static SlabfishRarity byChance(float chance)
    {
        for (int i = values().length - 1; i > 0; i--)
            if (chance <= values()[i].getChance())
                return values()[i];
        return COMMON;
    }

    /**
     * Checks the rarity types for a rarity with the specified id.
     *
     * @param id The id of the rarity
     * @return The rarity by that id or {@link #COMMON} if there is no rarity by that id
     */
    public static SlabfishRarity byId(int id)
    {
        if (id < 0 || id >= values().length)
            return COMMON;
        return values()[id];
    }
}
