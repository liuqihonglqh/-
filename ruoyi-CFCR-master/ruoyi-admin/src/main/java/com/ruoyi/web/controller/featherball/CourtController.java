package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 场地Controller
 *
 * @Author fanjaixing
 * @Date 2024/3/1 22:24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/court")
public class CourtController extends BaseController {

    private final CourtService courtService;

    // 获取所有场地列表
    @GetMapping("/listAll")
    public TableDataInfo listAllCourts() {
        startPage();
        List<Court> allCourts = courtService.getAllCourts();
        return getDataTable(allCourts);
    }

    // 根据场地ID获取场地信息
    @GetMapping("/detail")
    public AjaxResult getCourtById(@RequestParam Long courtId) {
        return success(courtService.getCourtById(courtId));
    }

    // 添加场地
    @PostMapping("/add")
    public AjaxResult addCourt(@RequestBody Court court) {
        return toAjax(courtService.addCourt(court));
    }

    // 更新场地信息
    @PostMapping("/update")
    public AjaxResult updateCourt(@RequestBody Court court) {
        return toAjax(courtService.updateCourt(court));
    }

    // 删除场地
    @GetMapping("/delete")
    public AjaxResult deleteCourt(@RequestParam Long courtId) {
        return toAjax(courtService.deleteCourt(courtId));
    }

    // 查询所有教练员
    @GetMapping(value = "/getTrainer")
    public AjaxResult getTrainer(){
        return success(courtService.getTrainer());
    }

    // 查场馆与场地数量的映射
    @GetMapping(value = "/getMap")
    public AjaxResult getVenueCourtCountMap(){
        return success(courtService.getVenueCourtCountMap());
    }
}
