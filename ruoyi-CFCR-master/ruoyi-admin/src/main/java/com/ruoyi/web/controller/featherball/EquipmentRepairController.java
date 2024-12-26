package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.EquipmentRepair;
import com.ruoyi.featherball.service.EquipmentRepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品维修管理 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/equipmentRepair")
public class EquipmentRepairController extends BaseController {

    private final EquipmentRepairService equipmentRepairService;

    // 获取所有维修商品信息列表
    @GetMapping("/listAll")
    public TableDataInfo getAllEquipmentRepairs() {
        startPage();
        List<EquipmentRepair> allEquipmentRepairs = equipmentRepairService.getAllEquipmentRepairs();
        return getDataTable(allEquipmentRepairs);
    }

    // 添加维修商品信息
    @PostMapping("/add")
    public AjaxResult addEquipmentRepair(@RequestBody EquipmentRepair equipmentRepair) {
        return toAjax(equipmentRepairService.addEquipmentRepair(equipmentRepair));
    }

    // 根据维修商品ID获取商品信息
    @GetMapping("/getById")
    public AjaxResult getEquipmentRepairById(@RequestParam Long equipmentRepairId) {
        return AjaxResult.success(equipmentRepairService.getEquipmentRepairById(equipmentRepairId));
    }

    // 更新维修商品信息
    @PutMapping("/update")
    public AjaxResult updateEquipmentRepair(@RequestBody EquipmentRepair equipmentRepair) {
        return toAjax(equipmentRepairService.updateEquipmentRepair(equipmentRepair));
    }

    // 删除维修商品信息
    @DeleteMapping("/delete")
    public AjaxResult deleteEquipmentRepair(@RequestParam Long equipmentRepairId) {
        return toAjax(equipmentRepairService.deleteEquipmentRepair(equipmentRepairId));
    }
}
