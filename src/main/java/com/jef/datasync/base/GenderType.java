package com.jef.datasync.base;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 性别类型
 */
public enum GenderType {

    MALE("男", 0),
    FEMALE("女", 1),
    OTHER("未指定", 2);


    private final String name;

    @JsonValue
    private final int index;

    GenderType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (GenderType status : GenderType.values()) {
            if (status.getIndex() == index) {
                return status.name;
            }
        }
        return null;
    }

    public static GenderType get(int index) {
        for (GenderType status : GenderType.values()) {
            if (status.getIndex() == index) {
                return status;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

}
