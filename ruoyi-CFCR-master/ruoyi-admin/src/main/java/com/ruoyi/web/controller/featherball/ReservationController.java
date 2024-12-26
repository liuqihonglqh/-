package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.Reservation;
import com.ruoyi.featherball.service.ReservationService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 场地预约Controller
 *
 * @Author fanjaixing
 * @Date 2024/3/1 22:24
 */
@RestController
@RequestMapping("/featherBall/reservation")
@RequiredArgsConstructor
public class ReservationController extends BaseController {

    private final ReservationService reservationService;

    // 获取所有预约信息列表
    @GetMapping("/listAll")
    public TableDataInfo listAllReservations() {
//        startPage();
        List<Reservation> allReservations = reservationService.getAllReservations();
        return getDataTable(allReservations);
    }

    // 根据预约ID获取预约信息
    @GetMapping("/detail")
    public AjaxResult getReservationById(@RequestParam Long reservationId) {
        return success(reservationService.getReservationById(reservationId));
    }

    // 添加预约信息
    @PostMapping("/add")
    public AjaxResult addReservation(@RequestBody Reservation reservation) {
        return toAjax(reservationService.addReservation(reservation));
    }

    // 更新预约信息
    @PostMapping("/update")
    public AjaxResult updateReservation(@RequestBody Reservation reservation) {
        return toAjax(reservationService.updateReservation(reservation));
    }

    // 删除预约信息
    @GetMapping("/delete")
    public AjaxResult deleteReservation(@RequestParam Long reservationId) {
        return toAjax(reservationService.deleteReservation(reservationId));
    }

    //如果该场地不可预约，或者该场地为 VIP 场地，且用户不是 VIP 用户，给出提示
    @GetMapping("/getVIP")
    public AjaxResult getVIPUserNotification(@RequestParam Long courtId){
        reservationService.getVIPUserNotification(courtId);
        return success();
    }

    //取消预约
    @GetMapping("/cancel")
    public AjaxResult cancelReservation(@RequestParam Long reservationId){
        reservationService.cancelReservation(reservationId);
        return success();
    }

}
