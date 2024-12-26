package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.Venue;
import com.ruoyi.featherball.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author fanjaixing
 * @Date 2024/3/1 22:24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/venue")
public class VenueController extends BaseController {

    private final VenueService venueService;

    // 获取所有场馆列表
    @GetMapping("/listAll")
    public TableDataInfo listAllVenues() {
        startPage();
        List<Venue> allVenues = venueService.getAllVenues();
        return getDataTable(allVenues);
    }

    // 根据场馆ID获取场馆信息
    @GetMapping("/detail")
    public AjaxResult getVenueById(@RequestParam Long venueId) {
        return success(venueService.getVenueById(venueId));
    }

    // 添加场馆
    @PostMapping("/add")
    public AjaxResult addVenue(@RequestBody Venue venue) {
        return toAjax(venueService.addVenue(venue));
    }

    // 更新场馆信息
    @PostMapping("/update")
    public AjaxResult updateVenue(@RequestBody Venue venue) {
        return toAjax(venueService.updateVenue(venue));
    }

    // 删除场馆
    @GetMapping("/delete")
    public AjaxResult deleteVenue(@RequestParam Long venueId) {
        return toAjax(venueService.deleteVenue(venueId));
    }
}
