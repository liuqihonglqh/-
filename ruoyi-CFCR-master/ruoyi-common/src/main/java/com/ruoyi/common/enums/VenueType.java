package com.ruoyi.common.enums;

/**
 * 场馆类型枚举
 *
 * @Author fanjaixing
 * @Date 2024/3/2 1:11
 */
public enum VenueType {
    INDOOR(0, "室内"),
    OUTDOOR(1, "室外");

    private final int code;
    private final String description;

    VenueType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据code获取对应的枚举值
    public static VenueType getByCode(int code) {
        for (VenueType type : VenueType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid VenueType code: " + code);
    }
}
