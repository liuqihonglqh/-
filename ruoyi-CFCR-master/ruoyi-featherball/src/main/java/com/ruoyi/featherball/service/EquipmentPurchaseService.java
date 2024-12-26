package com.ruoyi.featherball.service;

import com.ruoyi.featherball.domain.EquipmentPurchase;

import java.util.List;

/**
 * 商品购买记录管理 Service 接口

 */
public interface EquipmentPurchaseService {
    /**
     * 获取所有商品购买记录
     * @return 所有商品购买记录列表
     */
    List<EquipmentPurchase> getAllEquipmentPurchases();

    /**
     * 根据商品购买记录ID获取商品购买记录信息
     * @param equipmentPurchaseId 商品购买记录ID
     * @return 商品购买记录信息
     */
    EquipmentPurchase getEquipmentPurchaseById(Long equipmentPurchaseId);

    /**
     * 添加商品购买记录
     * @param equipmentPurchase 待添加的商品购买记录信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    /**
     * 更新商品购买记录信息
     * @param equipmentPurchase 待更新的商品购买记录信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    /**
     * 删除商品购买记录
     * @param equipmentPurchaseId 待删除的商品购买记录ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteEquipmentPurchase(Long equipmentPurchaseId);
}
