package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.domain.Reservation;
import com.ruoyi.featherball.mapper.ReservationMapper;
import com.ruoyi.featherball.service.CourtService;
import com.ruoyi.featherball.service.ReservationService;
import com.ruoyi.featherball.service.VenueService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 场地预约service接口实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/3 20:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    private final CourtService courtService;

    private final ISysRoleService iSysRoleService;

    private  final ISysUserService iSysUserService;

    private final VenueService venueService;

    /**
     * 获取所有预约信息
     *
     * @return 所有预约信息列表
     */
    @Override
    public List<Reservation> getAllReservations() {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")){
            startPage();
            List<Reservation> allReservations = reservationMapper.getAllReservations();
            fillTrainerAndUserName(allReservations);
            fillCourtNumberAndVenueName(allReservations);
            return allReservations;
        } else {
            startPage();
            List<Reservation> reservationByUserId = reservationMapper.getReservationByUserId(userId);
            fillTrainerAndUserName(reservationByUserId);
            fillCourtNumberAndVenueName(reservationByUserId);
            return reservationByUserId;
        }
    }

    /**
     * 根据预约ID获取预约信息
     *
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationMapper.getReservationById(reservationId);
    }

    /**
     * 添加预约信息
     *
     * @param reservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addReservation(Reservation reservation) {
        log.info("startTime: "+reservation.getStartTime());
        log.info("endTime: "+reservation.getEndTime());
        checkReservationTimeConflict(reservation);
        Long userId = SecurityUtils.getUserId(); // 获取当前登录用户的ID
        reservation.setUserId(userId);
        reservation.setReservationStatus(0);
        int rows = reservationMapper.addReservation(reservation);
        return rows > 0;
    }

    /**
     * 更新预约信息
     *
     * @param reservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateReservation(Reservation reservation) {
        checkReservationTimeConflict(reservation);
        Long userId = SecurityUtils.getUserId(); // 获取当前登录用户的ID
        reservation.setUserId(userId);
        int rows = reservationMapper.updateReservation(reservation);
        return rows > 0;
    }

    /**
     * 删除预约信息
     *
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteReservation(Long reservationId) {
        int rows = reservationMapper.deleteReservation(reservationId);
        return rows > 0;
    }

    /**
     * 如果该场地不可预约，或者该场地为 VIP 场地，且用户不是 VIP 用户，给出提示
     *
     * @param courtId 场地ID
     * @return 如果需要提示，则返回提示信息；否则返回空字符串
     */
    public void getVIPUserNotification(Long courtId) {
        Court court = courtService.getCourtById(courtId);
        // 从场地服务中获取场地状态
        Integer courtStatus = court.getCourtStatus();
        // 如果场地不可预约，则抛出异常
        if (courtStatus == 0) {
            throw new RuntimeException("该场地不可预约");
        }

        // 从场地服务中获取场地是否为 VIP 场地
        Integer courtVip = court.getCourtVip();
        // 获取当前登录用户的ID
        Long userId = SecurityUtils.getUserId();
        // 从角色服务中获取当前用户的角色
        String role = iSysRoleService.selectStringRoleByUserId(userId);

        // 如果场地为 VIP 场地且当前用户不是 VIP 用户，则抛出异常
        if (courtVip == 1 && !role.equalsIgnoreCase("VIP")) {
            throw new RuntimeException("您选择的场地为 VIP 场地，只有 VIP 用户才能预约，请先升级为 VIP 用户。");
        }
    }

    /**
     * 取消预约
     *
     * @param reservationId 待取消预约的预约ID
     */
    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationMapper.getReservationById(reservationId);
        if (reservation != null && reservation.getReservationStatus() == 1){
            throw new RuntimeException("已经是取消状态，请勿重复取消");
        }
        else { // 如果预约状态为预约
            reservation.setReservationStatus(1); // 设置预约状态为取消
            reservationMapper.updateReservation(reservation); // 更新预约信息
        }
    }

    /**
     * 检查当前预约时间与场地其他预约时间是否存在冲突
     *
     * @param reservation 当前预约信息
     * @throws RuntimeException 如果当前预约时间与其他预约时间存在重叠部分，则抛出异常
     */
    private void checkReservationTimeConflict(Reservation reservation) {
        LocalDateTime startTime = reservation.getStartTime();
        LocalDateTime endTime = reservation.getEndTime();

        // 判断开始时间是否早于早八点
        LocalTime startTimeLimit = LocalTime.of(8, 0);
        if (startTime.toLocalTime().isBefore(startTimeLimit)) {
            throw new RuntimeException("开始时间不能早于早上8点！");
        }

        // 判断结束时间是否晚于晚10点
        LocalTime endTimeLimit = LocalTime.of(22, 0);
        if (endTime.toLocalTime().isAfter(endTimeLimit)) {
            throw new RuntimeException("结束时间不能晚于晚上10点！");
        }

        // 检查开始时间和结束时间之间的间隔是否超过三小时
        long durationHours = Duration.between(startTime, endTime).toHours();
        if (durationHours > 3) {
            throw new RuntimeException("预约时间段不能超过三小时！");
        }

        // 获取场地的所有预约信息
        List<Reservation> reservationList = reservationMapper.getReservationByCourtId(reservation.getCourtId())
                .stream()
                .filter(r -> r.getReservationStatus() == 0)// 过滤掉状态为取消的预约
                .collect(Collectors.toList());// 将过滤后的预约信息收集到列表中

        // 遍历场地的所有预约信息，检查是否存在时间冲突
        for (Reservation existingReservation : reservationList) {
            LocalDateTime existingStartTime = existingReservation.getStartTime();
            LocalDateTime existingEndTime = existingReservation.getEndTime();
            // 检查当前预约时间是否与其他预约时间发生重叠
            if ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingEndTime)) ||
                    (endTime.isAfter(existingStartTime) && endTime.isBefore(existingEndTime)) ||
                    (startTime.isBefore(existingStartTime) && endTime.isAfter(existingEndTime))) {
                // 发生时间冲突，可以抛出异常或进行其他处理
                throw new RuntimeException("预约时间与其他预约存在冲突，请选择其他时间段！");
            }
        }
    }

    /**
     * 填充交易记录中的教练名称和用户名称
     *
     * @param reservations 待填充的预约记录列表
     */
    private void fillTrainerAndUserName(List<Reservation> reservations){
        for (Reservation reservation : reservations){
            // 获取用户名称
            Long userId = reservation.getUserId();
            SysUser user = iSysUserService.selectUserById(userId);
            if (user != null) {
                reservation.setUserName(user.getNickName());
            }
            // 获取教练名称
            Long trainerId = reservation.getTrainerId();
            SysUser trainer = iSysUserService.selectUserById(trainerId);
            if (trainer != null) {
                reservation.setTrainerName(trainer.getNickName());
            }
        }
    }

    /**
     * 填充交易记录中的场地编号和场馆名称
     *
     * @param reservations 待填充的预约记录列表
     */
    private void fillCourtNumberAndVenueName(List<Reservation> reservations){
        for (Reservation reservation : reservations){
            //填充交易记录中的场地编号
            Long courtId = reservation.getCourtId();
            Court court = courtService.getCourtById(courtId);
            Integer courtNumber = court.getCourtNumber();
            reservation.setCourtNumber(courtNumber);
            //填充交易记录中的场馆名称
            Long venueId = court.getVenueId();
            String venueName = venueService.getVenueById(venueId).getVenueName();
            reservation.setVenueName(venueName);
        }
    }

}
