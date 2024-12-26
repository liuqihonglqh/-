package com.ruoyi.featherball.domain;

/**
 *
 * 羽毛球场预约表
 *
 * @Author fanjaixing
 * @Date 2024/2/29 21:09
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.enums.ReservationStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("cfcr_reservation")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Reservation implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long reservationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 场馆名称
     */
    @TableField(exist = false)
    private String venueName;

    /**
     * 场地ID
     */
    private Long courtId;

    /**
     * 场地编号
     */
    @TableField(exist = false)
    private Integer courtNumber;

    /**
     * 场教练ID
     */
    private Long trainerId;

    /**
     * 教练名称
     */
    @TableField(exist = false)
    private String trainerName;

    /**
     * 预约起始时间
     */
    private LocalDateTime startTime;

    /**
     * 预约终止时间
     */
    private LocalDateTime endTime;

    /**
     * 预约状态，0预约，1取消
     */
    private Integer reservationStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
