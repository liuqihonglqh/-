package com.ruoyi.common.enums;

/**
 *
 * 预约状态枚举
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:59
 */
public enum ReservationStatusEnum {
    RESERVED(0, "预约"),
    CANCELLED(1, "取消");

    private final int code;
    private final String description;

    ReservationStatusEnum(int code, String description) {
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
    public static ReservationStatusEnum getByCode(int code) {
        for (ReservationStatusEnum status : ReservationStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ReservationStatusEnum code: " + code);
    }
}
