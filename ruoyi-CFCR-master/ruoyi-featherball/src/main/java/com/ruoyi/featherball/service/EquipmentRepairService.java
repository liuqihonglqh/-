package com.ruoyi.featherball.service;

import com.ruoyi.featherball.domain.EquipmentRepair;

import java.util.List;

/**
 *商品维修记录管理 Service 接口

 */
public interface EquipmentRepairService {
    List<EquipmentRepair> getAllEquipmentRepairs();

    EquipmentRepair getEquipmentRepairById(Long equipmentRepairId);

    boolean addEquipmentRepair(EquipmentRepair equipmentRepair);

    boolean updateEquipmentRepair(EquipmentRepair equipmentRepair);

    boolean deleteEquipmentRepair(Long equipmentRepairId);
}
