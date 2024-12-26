package com.ruoyi.common.enums;

/**
 * 场地类型枚举
 *
 * @Author fanjaixing
 * @Date 2024/2/29 20:44
 */
public enum CourtType {
    INDOOR(0, "室内"),
    OUTDOOR(1, "室外");

    private final int code;
    private final String description;

    CourtType(int code, String description) {
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
    public static CourtType getByCode(int code) {
        for (CourtType type : CourtType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CourtType code: " + code);
    }
}
