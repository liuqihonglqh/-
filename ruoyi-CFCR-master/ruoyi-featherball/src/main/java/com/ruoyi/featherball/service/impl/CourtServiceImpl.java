package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.domain.Venue;
import com.ruoyi.featherball.mapper.CourtMapper;
import com.ruoyi.featherball.mapper.VenueMapper;
import com.ruoyi.featherball.service.CourtService;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 场地管理 Service 实现类
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtMapper courtMapper;

    private final VenueMapper venueMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final ISysUserService iSysUserService;

    /**
     * 获取所有场地
     * @return 所有场地列表
     */
    @Override
    public List<Court> getAllCourts() {
        List<Court> allCourts = courtMapper.getAllCourts();
        for (Court court : allCourts){
            // 根据 court 中的 venueId 查询对应的场馆名称
            String venueName = venueMapper.getVenueById(court.getVenueId()).getVenueName();
            // 将场馆名称设置到 Court 对象中
            court.setVenueName(venueName);
        }
        fillTrainerName(allCourts);
        return allCourts;
    }

    /**
     * 根据场地ID获取场地信息
     * @param courtId 场地ID
     * @return 场地信息
     */
    @Override
    public Court getCourtById(Long courtId) {
        return courtMapper.getCourtById(courtId);
    }

    /**
     * 添加场地
     * @param court 待添加的场地信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addCourt(Court court) {
        countByCourtNumber(court);
        int rows = courtMapper.addCourt(court);
        return rows > 0;
    }

    /**
     * 更新场地信息
     * @param court 待更新的场地信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateCourt(Court court) {
        if (!isCourtNumberChanged(court)) {
            // 如果场地编号没有变化，则直接执行更新操作，不需要进行重复检查
            int rows = courtMapper.updateCourt(court);
            return rows > 0;
        }

        countByCourtNumber(court);
        int rows = courtMapper.updateCourt(court);
        return rows > 0;
    }

    /**
     * 删除场地
     * @param courtId 待删除的场地ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteCourt(Long courtId) {
        int rows = courtMapper.deleteCourt(courtId);
        return rows > 0;
    }

    /**
     * 判断该场馆中该场地是否已存在
     *
     * @param court
     */
    private void countByCourtNumber(Court court){
        if (courtMapper.countByCourtNumber(court.getVenueId(), court.getCourtNumber()) > 0) {
            // 如果场地编号已存在，则抛出异常
            throw new RuntimeException("该场地编号已存在");
        }
    }

    /**
     * 判断场地编号是否有变化
     * @param court 待更新的场地信息
     * @return true 如果场地编号有变化，否则返回 false
     */
    private boolean isCourtNumberChanged(Court court) {
        // 查询数据库中的场地信息
        Court existingCourt = courtMapper.getCourtById(court.getCourtId());
        // 比较数据库中的场地编号和更新后的场地编号是否相同
        return !existingCourt.getCourtNumber().equals(court.getCourtNumber());
    }

    /**
     * 查询所有教练员
     * @return
     */
    public List<SysUser> getTrainer(){
        List<Long> userIds = sysUserRoleMapper.searchAllByRoleId(2L);
        List<SysUser> trainers = new ArrayList<>();
        for (Long userId : userIds){
            SysUser trainer = iSysUserService.selectUserById(userId);
            trainers.add(trainer);
        }
        return trainers;
    }

    /**
     * 填充court中教练员名称
     * @param courts
     */
    private void fillTrainerName(List<Court> courts){
        for (Court court : courts){
            Long trainerId = court.getTrainerId();
            if (trainerId != null) {
                String nickName = iSysUserService.selectUserById(trainerId).getNickName();
                court.setTrainerName(nickName);
            }
        }
    }

    @Override
    public Map<String, Integer> getVenueCourtCountMap() {
        // 获取所有场馆
        List<Venue> allVenues = venueMapper.getAllVenues();
        // 获取每个场馆对应的场地数量
        Map<String, Integer> venueCourtCountMap = new HashMap<>();
        for (Venue venue : allVenues) {
            Long venueId = venue.getVenueId();
            int courtCount = courtMapper.countByVenueId(venueId);
            venueCourtCountMap.put(venue.getVenueName(), courtCount);
        }
        return venueCourtCountMap;
    }
}
