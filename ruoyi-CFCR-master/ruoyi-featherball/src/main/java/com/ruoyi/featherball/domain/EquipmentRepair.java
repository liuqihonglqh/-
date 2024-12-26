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
/**

 */
@TableName("cfcr_equipment_repair")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EquipmentRepair implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long equipmentRepairId;

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
     * 维修商品类别：0球拍，1球网，2球鞋
     */
    private Integer equipmentRepairType;

    /**
     * 维修单价
     */
    private BigDecimal equipmentRepairFee;

    private static final long serialVersionUID = 1L;
}
