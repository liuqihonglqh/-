package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.enums.VenueFacilityEnum;
import com.ruoyi.common.enums.VenueType;
import com.ruoyi.featherball.domain.Venue;
import com.ruoyi.featherball.mapper.VenueMapper;
import com.ruoyi.featherball.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Author fanjaixing
 * @Date 2024/2/29 22:46
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueMapper venueMapper;

    /**
     * 获取所有场馆信息
     * @return 所有场馆列表
     */
    @Override
    public List<Venue> getAllVenues() {
        return venueMapper.getAllVenues();
    }

    /**
     * 根据场馆ID获取场馆信息
     * @param venueId 场馆ID
     * @return 场馆信息
     */
    @Override
    public Venue getVenueById(Long venueId) {
        return venueMapper.getVenueById(venueId);
    }

    /**
     * 添加场馆信息
     * @param venue 待添加的场馆信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addVenue(Venue venue) {
        int rows = venueMapper.addVenue(venue);
        return rows > 0;
    }

    /**
     * 更新场馆信息
     * @param venue 待更新的场馆信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateVenue(Venue venue) {
        int rows = venueMapper.updateVenue(venue);
        return rows > 0;
    }

    /**
     * 删除场馆信息
     * @param venueId 待删除的场馆ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteVenue(Long venueId) {
        int rows = venueMapper.deleteVenue(venueId);
        return rows > 0;
    }
}
