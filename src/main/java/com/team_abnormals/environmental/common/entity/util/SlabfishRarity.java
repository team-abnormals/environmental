package com.team_abnormals.environmental.common.entity.util;

import net.minecraft.util.text.TextFormatting;

public enum SlabfishRarity {
    COMMON(TextFormatting.GRAY),
    UNCOMMON(TextFormatting.GREEN),
    RARE(TextFormatting.AQUA),
    EPIC(TextFormatting.LIGHT_PURPLE),
    LEGENDARY(TextFormatting.GOLD);

    public final TextFormatting color;

    private SlabfishRarity(TextFormatting color) {
        this.color = color;
    }
}
