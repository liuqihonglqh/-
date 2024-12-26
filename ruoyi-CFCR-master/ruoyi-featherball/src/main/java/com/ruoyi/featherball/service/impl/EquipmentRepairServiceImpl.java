package com.ruoyi.featherball.service.impl;

import com.ruoyi.featherball.domain.EquipmentRepair;
import com.ruoyi.featherball.mapper.EquipmentRepairMapper;
import com.ruoyi.featherball.mapper.VenueMapper;
import com.ruoyi.featherball.service.EquipmentRepairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品维修记录管理 Service 实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentRepairServiceImpl implements EquipmentRepairService {

    private final EquipmentRepairMapper equipmentRepairMapper;

    private final VenueMapper venueMapper;

    @Override
    public List<EquipmentRepair> getAllEquipmentRepairs() {
        List<EquipmentRepair> allEquipmentRepairs = equipmentRepairMapper.getAllEquipmentRepairs();
        for (EquipmentRepair equipmentRepair : allEquipmentRepairs){
            Long venueId = equipmentRepair.getVenueId();
            equipmentRepair.setVenueName(venueMapper.getVenueById(venueId).getVenueName());
        }
        return allEquipmentRepairs;
    }

    @Override
    public EquipmentRepair getEquipmentRepairById(Long equipmentRepairId) {
        return equipmentRepairMapper.getEquipmentRepairById(equipmentRepairId);
    }

    @Override
    public boolean addEquipmentRepair(EquipmentRepair equipmentRepair) {
        int rows = equipmentRepairMapper.addEquipmentRepair(equipmentRepair);
        return rows > 0;
    }

    @Override
    public boolean updateEquipmentRepair(EquipmentRepair equipmentRepair) {
        int rows = equipmentRepairMapper.updateEquipmentRepair(equipmentRepair);
        return rows > 0;
    }

    @Override
    public boolean deleteEquipmentRepair(Long equipmentRepairId) {
        int rows = equipmentRepairMapper.deleteEquipmentRepair(equipmentRepairId);
        return rows > 0;
    }
}
