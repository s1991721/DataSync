package com.jef.datasync.base;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 启用状态
 */

public enum Status {

    DISABLE("停用", 0),
    ENABLE("启用", 1);

    private final String name;

    @JsonValue
    private final int index;

    Status(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (Status status : Status.values()) {
            if (status.getIndex() == index) {
                return status.name;
            }
        }
        return null;
    }

    public static Status get(int index) {
        for (Status status : Status.values()) {
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
