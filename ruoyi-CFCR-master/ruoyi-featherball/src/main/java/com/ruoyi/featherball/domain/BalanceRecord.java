package com.ruoyi.featherball.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 余额记录表实体类
 *
 * @Author fanjaixing
 * @Date 2024/3/8 23:50
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("cfcr_balance_record")
public class BalanceRecord implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long recordId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 场馆ID
     */
    private Long venueId;

    /**
     * 场馆名称
     */
    @TableField(exist = false)
    private String venueName;

    /**
     * 交易类型：0收入，1购买，2维修，3预约
     */
    private Integer transactionType;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime transactionTime;

    /**
     * 余额
     */
    private BigDecimal balance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
