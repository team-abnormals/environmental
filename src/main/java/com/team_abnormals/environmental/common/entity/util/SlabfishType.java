package com.team_abnormals.environmental.common.entity.util;

import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public enum SlabfishType implements IStringSerializable {
    SWAMP(0, "swamp", SlabfishRarity.COMMON),
    OCEAN(1, "ocean", SlabfishRarity.COMMON),
    MARSH(2, "marsh", SlabfishRarity.COMMON),
    MIRE(3, "mire", SlabfishRarity.UNCOMMON),
    CAVE(4, "cave", SlabfishRarity.RARE),
    JUNGLE(5, "jungle", SlabfishRarity.RARE),
    DESERT(6, "desert", SlabfishRarity.UNCOMMON),
    SAVANNA(7, "savanna", SlabfishRarity.UNCOMMON),
    BADLANDS(8, "badlands", SlabfishRarity.EPIC),
    SNOWY(9, "snowy", SlabfishRarity.UNCOMMON),
    TOTEM(10, "totem", SlabfishRarity.LEGENDARY),
    TAIGA(11, "taiga", SlabfishRarity.UNCOMMON),
    FOREST(12, "forest", SlabfishRarity.UNCOMMON),
    PLAINS(13, "plains", SlabfishRarity.UNCOMMON),
    SKELETON(14, "skeleton", SlabfishRarity.EPIC),
    WITHER(15, "wither", SlabfishRarity.LEGENDARY),
    RIVER(16, "river", SlabfishRarity.UNCOMMON),
    MAPLE(17, "maple", SlabfishRarity.UNCOMMON),
    ROSEWOOD(18, "rosewood", SlabfishRarity.EPIC),
    DUNES(19, "dunes", SlabfishRarity.EPIC),
    NIGHTMARE(20, "nightmare", SlabfishRarity.EPIC),
    ICE_SPIKES(21, "ice_spikes", SlabfishRarity.LEGENDARY),
    STRAY(22, "stray", SlabfishRarity.LEGENDARY),
    NETHER(23, "nether", SlabfishRarity.RARE),
    END(24, "end", SlabfishRarity.EPIC),
    POISE(25, "poise", SlabfishRarity.EPIC),
    GHOST(26, "ghost", SlabfishRarity.EPIC),
    BAGEL(27, "bagel", SlabfishRarity.UNCOMMON),
    CAMERON(28, "cameron", SlabfishRarity.UNCOMMON),
    GORE(29, "gore", SlabfishRarity.UNCOMMON),
    SNAKE_BLOCK(30, "snake_block", SlabfishRarity.UNCOMMON),
    DROWNED(31, "drowned", SlabfishRarity.EPIC),
    FROZEN_OCEAN(32, "frozen_ocean", SlabfishRarity.UNCOMMON),
    WARM_OCEAN(33, "warm_ocean", SlabfishRarity.UNCOMMON),
    MOUNTAIN(34, "mountain", SlabfishRarity.UNCOMMON),
    MUSHROOM(35, "mushroom", SlabfishRarity.EPIC),
    BAMBOO(36, "bamboo", SlabfishRarity.EPIC),
    CHORUS(37, "chorus", SlabfishRarity.EPIC),
    DARK_FOREST(38, "dark_forest", SlabfishRarity.RARE),
    FLOWER_FOREST(39, "flower_forest", SlabfishRarity.RARE),
    BEACH(40, "beach", SlabfishRarity.UNCOMMON),
    SKY(41, "sky", SlabfishRarity.LEGENDARY),
    BROWN_MUSHROOM(42, "brown_mushroom", SlabfishRarity.LEGENDARY),
    JACKSON(43, "jackson", SlabfishRarity.UNCOMMON),
    MISTA_JUB(44, "mista_jub", SlabfishRarity.UNCOMMON),
    SMELLY(45, "smelly", SlabfishRarity.UNCOMMON),
    SQUART(46, "squart", SlabfishRarity.UNCOMMON),
    CRIMSON(47, "crimson", SlabfishRarity.EPIC),
    WARPED(48, "warped", SlabfishRarity.RARE),
    SOUL_SAND_VALLEY(49, "soul_sand_valley", SlabfishRarity.EPIC),
    BASALT_DELTAS(50, "basalt_deltas", SlabfishRarity.LEGENDARY),
    BMO(51, "bmo", SlabfishRarity.UNCOMMON);

    private static final SlabfishType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishType::getId)).toArray((array) -> {
        return new SlabfishType[array];
    });
    private final int id;
    private final String name;
    private final SlabfishRarity rarity;

    private SlabfishType(int idIn, String name, SlabfishRarity rarity) {
        this.id = idIn;
        this.name = name;
        this.rarity = rarity;
    }

    public int getId() {
        return this.id;
    }

    public SlabfishRarity getRarity() {
        return this.rarity;
    }

    public static SlabfishType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static SlabfishType byName(String key, SlabfishType type) {
        for (SlabfishType slabfishtype : values()) {
            if (slabfishtype.name.equals(key)) {
                return slabfishtype;
            }
        }
        return type;
    }

    public static SlabfishType getRandomFromRarity(SlabfishRarity rarity, Random rand) {
        ArrayList<SlabfishType> types = new ArrayList<>();
        for (SlabfishType slabfishtype : values()) {
            if (slabfishtype.getRarity() == rarity) {
                types.add(slabfishtype);
            }
        }
        return types.get(rand.nextInt(types.size()));
    }

    @Override
    public String getString() {
        return this.name;
    }

    public String getTranslationKey() {
        return "entity.environmental.slabfish." + this.name;
    }
}