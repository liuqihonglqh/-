package com.ruoyi.common.enums;

/**
 * 场馆设施枚举
 *
 * @Author fanjaixing
 * @Date 2024/3/2 1:09
 */
public enum VenueFacilityEnum {
    NO_SHOWER(0, "无淋浴设施"),
    HAS_SHOWER(1, "有淋浴设施");

    private final int code;
    private final String description;

    VenueFacilityEnum(int code, String description) {
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
    public static VenueFacilityEnum getByCode(int code) {
        for (VenueFacilityEnum type : VenueFacilityEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid VenueFacilityEnum code: " + code);
    }
}
