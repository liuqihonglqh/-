package com.ruoyi.featherball.service;

/**
 *
 * 场馆管理 Service 接口
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:33
 */

import com.ruoyi.featherball.domain.Venue;
import java.util.List;

public interface VenueService {

    /**
     * 获取所有场馆
     * @return 所有场馆列表
     */
    List<Venue> getAllVenues();

    /**
     * 根据场馆ID获取场馆信息
     * @param venueId 场馆ID
     * @return 场馆信息
     */
    Venue getVenueById(Long venueId);

    /**
     * 添加场馆
     * @param venue 待添加的场馆信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addVenue(Venue venue);

    /**
     * 更新场馆信息
     * @param venue 待更新的场馆信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateVenue(Venue venue);

    /**
     * 删除场馆
     * @param venueId 待删除的场馆ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteVenue(Long venueId);
}
