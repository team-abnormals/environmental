package com.minecraftabnormals.environmental.integration.enhanced_mushrooms;

import net.minecraft.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class EnhancedMushrooms {
    @ObjectHolder("enhanced_mushrooms:red_mushroom_stem")
    public static final Block RED_MUSHROOM_STEM = null;

    @ObjectHolder("enhanced_mushrooms:brown_mushroom_stem")
    public static final Block BROWN_MUSHROOM_STEM = null;

    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().getModContainerById("enhanced_mushrooms").isPresent();
    }
}
