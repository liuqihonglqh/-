package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.BalanceRecord;
import com.ruoyi.featherball.service.BalanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单记录Controller
 * 
 * @Author fanjaixing
 * @Date 2024/3/9 12:15
 */
@RestController
@RequestMapping("/featherBall/balanceRecord")
@RequiredArgsConstructor
public class BalanceRecordController extends BaseController {
    
    private final BalanceRecordService balanceRecordService;

    // 获取所有订单记录信息列表
    @GetMapping("/listAll")
    public TableDataInfo listAllBalanceRecords() {
        startPage();
        List<BalanceRecord> allBalanceRecords = balanceRecordService.getAllBalanceRecords();
        return getDataTable(allBalanceRecords);
    }

    // 根据记录ID获取订单记录信息
    @GetMapping("/detail")
    public AjaxResult getBalanceRecordById(@RequestParam Long recordId) {
        return AjaxResult.success(balanceRecordService.getBalanceRecordById(recordId));
    }

    // 添加订单记录信息
    @PostMapping("/add")
    public AjaxResult addBalanceRecord(@RequestBody BalanceRecord balanceRecord) {
        return toAjax(balanceRecordService.addBalanceRecord(balanceRecord));
    }

    // 查交易类型和消费额的映射
    @GetMapping(value = "/getMap")
    public AjaxResult getBalanceTotalByType(){
        return success(balanceRecordService.getBalanceTotalByType());
    }
}
