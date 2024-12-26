package com.ruoyi.featherball.service;

/**
 *
 * 场地管理 Service 接口
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:53
 */

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.featherball.domain.Court;

import java.util.List;
import java.util.Map;

public interface CourtService {

    /**
     * 获取所有场地
     * @return 所有场地列表
     */
    List<Court> getAllCourts();

    /**
     * 根据场地ID获取场地信息
     * @param courtId 场地ID
     * @return 场地信息
     */
    Court getCourtById(Long courtId);

    /**
     * 添加场地
     * @param court 待添加的场地信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addCourt(Court court);

    /**
     * 更新场地信息
     * @param court 待更新的场地信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateCourt(Court court);

    /**
     * 删除场地
     * @param courtId 待删除的场地ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteCourt(Long courtId);

    /**
     * 查询所有教练员
     * @return
     */
    public List<SysUser> getTrainer();

    /**
     * 获取场馆及其场地数量的映射
     * @return 场馆名称和对应场地数量的映射
     */
    Map<String, Integer> getVenueCourtCountMap();
}
