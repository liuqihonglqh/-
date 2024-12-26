package com.ruoyi.featherball.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


@TableName("cfcr_equipment_purchase")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EquipmentPurchase implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long equipmentPurchaseId;

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
     * 商品类别：0球拍，1羽毛球，2球鞋，3饮料，4运动服
     */
    private Integer equipmentPurchaseType;

    /**
     * 商品个数
     */
    private Integer equipmentPurchaseNumber;

    /**
     * 商品单价
     */
    private BigDecimal equipmentPurchaseFee;

    private static final long serialVersionUID = 1L;
}
