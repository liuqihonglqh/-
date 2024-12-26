package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.EquipmentPurchase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipmentPurchaseMapper {
    List<EquipmentPurchase> getAllEquipmentPurchases();

    EquipmentPurchase getEquipmentPurchaseById(Long equipmentPurchaseId);

    int addEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    int updateEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    int deleteEquipmentPurchase(Long equipmentPurchaseId);

}
