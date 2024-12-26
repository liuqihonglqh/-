package com.ruoyi.featherball.mapper;

/**
 *
 * 场馆管理 Mapper 接口
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:40
 */

import com.ruoyi.featherball.domain.Venue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VenueMapper {

    /**
     * 获取所有场馆
     *
     * @return 所有场馆列表
     */
    List<Venue> getAllVenues();

    /**
     * 根据场馆ID获取场馆信息
     *
     * @param venueId 场馆ID
     * @return 场馆信息
     */
    Venue getVenueById(Long venueId);

    /**
     * 添加场馆
     *
     * @param venue 待添加的场馆信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addVenue(Venue venue);

    /**
     * 更新场馆信息
     *
     * @param venue 待更新的场馆信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateVenue(Venue venue);

    /**
     * 删除场馆
     *
     * @param venueId 待删除的场馆ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteVenue(Long venueId);
}
