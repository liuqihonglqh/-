package com.ruoyi.featherball.service.impl;

import com.ruoyi.featherball.domain.EquipmentPurchase;
import com.ruoyi.featherball.mapper.EquipmentPurchaseMapper;
import com.ruoyi.featherball.mapper.VenueMapper;
import com.ruoyi.featherball.service.EquipmentPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品购买记录管理 Service 实现类

 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentPurchaseServiceImpl implements EquipmentPurchaseService {

    private final EquipmentPurchaseMapper equipmentPurchaseMapper;

    private final VenueMapper venueMapper;

    /**
     * 获取所有商品购买记录
     * @return 所有商品购买记录列表
     */
    @Override
    public List<EquipmentPurchase> getAllEquipmentPurchases() {
        List<EquipmentPurchase> allEquipmentPurchases = equipmentPurchaseMapper.getAllEquipmentPurchases();
        for (EquipmentPurchase equipmentPurchase : allEquipmentPurchases){
            Long venueId = equipmentPurchase.getVenueId();
            equipmentPurchase.setVenueName(venueMapper.getVenueById(venueId).getVenueName());
        }
        return allEquipmentPurchases;
    }

    /**
     * 根据商品购买记录ID获取商品购买记录信息
     * @param equipmentPurchaseId 商品购买记录ID
     * @return 商品购买记录信息
     */
    @Override
    public EquipmentPurchase getEquipmentPurchaseById(Long equipmentPurchaseId) {
        return equipmentPurchaseMapper.getEquipmentPurchaseById(equipmentPurchaseId);
    }

    /**
     * 添加商品购买记录
     * @param equipmentPurchase 待添加的商品购买记录信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addEquipmentPurchase(EquipmentPurchase equipmentPurchase) {
        int rows = equipmentPurchaseMapper.addEquipmentPurchase(equipmentPurchase);
        return rows > 0;
    }

    /**
     * 更新商品购买记录信息
     * @param equipmentPurchase 待更新的商品购买记录信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateEquipmentPurchase(EquipmentPurchase equipmentPurchase) {
        int rows = equipmentPurchaseMapper.updateEquipmentPurchase(equipmentPurchase);
        return rows > 0;
    }

    /**
     * 删除商品购买记录
     * @param equipmentPurchaseId 待删除的商品购买记录ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteEquipmentPurchase(Long equipmentPurchaseId) {
        int rows = equipmentPurchaseMapper.deleteEquipmentPurchase(equipmentPurchaseId);
        return rows > 0;
    }
}
