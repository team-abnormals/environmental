package com.minecraftabnormals.environmental.common.entity.util;

import java.util.Arrays;
import java.util.Comparator;

public enum DeerCoatTypes {
    NONE(0), WHITE_SPOTS(1);

    private static final DeerCoatTypes[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(DeerCoatTypes::getId)).toArray((p_234255_0_) -> {
        return new DeerCoatTypes[p_234255_0_];
    });
    
    private final int id;

    private DeerCoatTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static DeerCoatTypes byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }
}