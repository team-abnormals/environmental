package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.common.slabfish.SlabfishManager;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.util.ResourceLocation;

/**
 * <p>Contains the base slabfish registry type names. Used for special functionality for slabfish types.</p>
 *
 * @author Ocelot
 */
public class EnvironmentalSlabfishTypes
{
    public static final ResourceLocation SWAMP = SlabfishManager.DEFAULT_SLABFISH.getRegistryName();
    public static final ResourceLocation OCEAN = getType("ocean");
    public static final ResourceLocation MARSH = getType("marsh");
    public static final ResourceLocation MIRE = getType("mire");
    public static final ResourceLocation CAVE = getType("cave");
    public static final ResourceLocation JUNGLE = getType("jungle");
    public static final ResourceLocation DESERT = getType("desert");
    public static final ResourceLocation SAVANNA = getType("savanna");
    public static final ResourceLocation BADLANDS = getType("badlands");
    public static final ResourceLocation SNOWY = getType("snowy");
    public static final ResourceLocation TOTEM = getType("totem");
    public static final ResourceLocation TAIGA = getType("taiga");
    public static final ResourceLocation FOREST = getType("forest");
    public static final ResourceLocation PLAINS = getType("plains");
    public static final ResourceLocation SKELETON = getType("skeleton");
    public static final ResourceLocation WITHER = getType("wither");
    public static final ResourceLocation RIVER = getType("river");
    public static final ResourceLocation MAPLE = getType("maple");
    public static final ResourceLocation ROSEWOOD = getType("rosewood");
    public static final ResourceLocation DUNES = getType("dunes");
    public static final ResourceLocation NIGHTMARE = getType("nightmare");
    public static final ResourceLocation ICE_SPIKES = getType("ice_spikes");
    public static final ResourceLocation STRAY = getType("stray");
    public static final ResourceLocation NETHER = getType("nether");
    public static final ResourceLocation END = getType("end");
    public static final ResourceLocation POISE = getType("poise");
    public static final ResourceLocation GHOST = getType("ghost");
    public static final ResourceLocation BAGEL = getType("bagel");
    public static final ResourceLocation CAMERON = getType("cameron");
    public static final ResourceLocation GORE = getType("gore");
    public static final ResourceLocation SNAKE_BLOCK = getType("snake_block");
    public static final ResourceLocation DROWNED = getType("drowned");
    public static final ResourceLocation FROZEN_OCEAN = getType("frozen_ocean");
    public static final ResourceLocation WARM_OCEAN = getType("warm_ocean");
    public static final ResourceLocation MOUNTAIN = getType("mountain");
    public static final ResourceLocation MUSHROOM = getType("mushroom");
    public static final ResourceLocation BAMBOO = getType("bamboo");
    public static final ResourceLocation CHORUS = getType("chorus");
    public static final ResourceLocation DARK_FOREST = getType("dark_forest");
    public static final ResourceLocation FLOWER_FOREST = getType("flower_forest");
    public static final ResourceLocation BEACH = getType("beach");
    public static final ResourceLocation SKY = getType("sky");
    public static final ResourceLocation BROWN_MUSHROOM = getType("brown_mushroom");
    public static final ResourceLocation JACKSONPLAYSYT = getType("jackson");
    public static final ResourceLocation MISTA_JUB = getType("mista_jub");
    public static final ResourceLocation SMELLY = getType("smelly");
    public static final ResourceLocation SQUART = getType("squart");
    public static final ResourceLocation CRIMSON = getType("crimson");
    public static final ResourceLocation WARPED = getType("warped");
    public static final ResourceLocation SOUL_SAND_VALLEY = getType("soul_sand_valley");
    public static final ResourceLocation BASALT_DELTAS = getType("basalt_deltas");
    public static final ResourceLocation BMO = getType("bmo");

    private static ResourceLocation getType(String name)
    {
        return new ResourceLocation(Environmental.MODID, name);
    }
}
