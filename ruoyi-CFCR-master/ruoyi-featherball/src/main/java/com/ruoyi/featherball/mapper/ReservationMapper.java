package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author fanjaixing
 * @Date 2024/3/3 20:51
 */
@Mapper
public interface ReservationMapper {
    /**
     * 查询所有预约信息
     *
     * @return 预约信息列表
     */
    List<Reservation> getAllReservations();

    /**
     * 根据预约ID查询预约信息
     *
     * @param reservationId 预约ID
     * @return 预约信息
     */
    Reservation getReservationById(Long reservationId);

    /**
     * 添加预约信息
     *
     * @param reservation 待添加的预约信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addReservation(Reservation reservation);

    /**
     * 更新预约信息
     *
     * @param reservation 待更新的预约信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateReservation(Reservation reservation);

    /**
     * 删除预约信息
     *
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteReservation(Long reservationId);

    /**
     * 根据预约ID查询预约信息
     *
     * @param courtId 场地ID
     * @return 预约信息
     */
    List<Reservation> getReservationByCourtId(Long courtId);

    /**
     * 根据预约ID查询预约信息
     *
     * @param userId 用户ID
     * @return 预约信息
     */
    List<Reservation> getReservationByUserId(Long userId);
}
