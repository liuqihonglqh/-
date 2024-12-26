package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.EquipmentPurchase;
import com.ruoyi.featherball.service.EquipmentPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**

 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/equipmentPurchase")
public class EquipmentPurchaseController extends BaseController {

    private final EquipmentPurchaseService equipmentPurchaseService;

    // 获取所有购买商品信息列表
    @GetMapping("/listAll")
    public TableDataInfo getAllEquipmentRepairs() {
        startPage();
        List<EquipmentPurchase> allEquipmentPurchases = equipmentPurchaseService.getAllEquipmentPurchases();
        return getDataTable(allEquipmentPurchases);
    }

    // 添加购买商品信息
    @PostMapping("/add")
    public AjaxResult addEquipmentRepair(@RequestBody EquipmentPurchase equipmentPurchase) {
        return toAjax(equipmentPurchaseService.addEquipmentPurchase(equipmentPurchase));
    }

    // 根据购买商品ID获取商品信息
    @GetMapping("/getById")
    public AjaxResult getEquipmentPurchaseById(@RequestParam Long equipmentPurchaseId) {
        return AjaxResult.success(equipmentPurchaseService.getEquipmentPurchaseById(equipmentPurchaseId));
    }

    // 更新购买商品信息
    @PutMapping("/update")
    public AjaxResult updateEquipmentPurchase(@RequestBody EquipmentPurchase equipmentPurchase) {
        return toAjax(equipmentPurchaseService.updateEquipmentPurchase(equipmentPurchase));
    }

    // 删除购买商品信息
    @DeleteMapping("/delete")
    public AjaxResult deleteEquipmentPurchase(@RequestParam Long equipmentPurchaseId) {
        return toAjax(equipmentPurchaseService.deleteEquipmentPurchase(equipmentPurchaseId));
    }
}
