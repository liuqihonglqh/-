package com.ruoyi.featherball.domain;

/**
 *
 * 场地表
 *
 * @Author fanjaixing
 * @Date 2024/2/29 21:07
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.enums.CourtType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("cfcr_court")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Court implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long courtId;

    /**
     * 场馆ID，外键
     */
    private Long venueId;

    /**
     * 场馆名
     */
    @TableField(exist = false)
    private String venueName;

    /**
     * 场地编号
     */
    private Integer courtNumber;

    /**
     * 场地状态（0可预约，1不可预约）
     */
    private Integer courtStatus;

    /**
     * 场地费用
     */
    private BigDecimal courtFee;

    /**
     * 是否为VIP场地（0否，1是）
     */
    private Integer courtVip;

    /**
     * 教练ID
     */
    private Long trainerId;

    /**
     * 教练名称
     */
    private String trainerName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

