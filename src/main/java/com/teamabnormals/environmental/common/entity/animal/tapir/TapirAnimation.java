package com.teamabnormals.environmental.common.entity.animal.tapir;

public enum TapirAnimation {
    DEFAULT(0),
    RAISING_TRUNK(1),
    SNIFFING(2),
    GRAZING(3);

    private static final TapirAnimation[] VALUES = values();

    private final int id;

    TapirAnimation(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static TapirAnimation byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }
}