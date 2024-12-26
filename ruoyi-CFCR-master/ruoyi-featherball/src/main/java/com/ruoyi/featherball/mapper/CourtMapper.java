package com.ruoyi.featherball.mapper;

/**
 *
 * 场地管理 Mapper 接口
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:54
 */

import com.ruoyi.featherball.domain.Court;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourtMapper {

    List<Court> getAllCourts();

    Court getCourtById(Long courtId);

    int addCourt(Court court);

    int updateCourt(Court court);

    int deleteCourt(Long courtId);

    /**
     * 根据场地编号查询数量
     *
     * @param courtNumber 场地编号
     * @return 场地编号数量
     */
    int countByCourtNumber(@Param("venueId")Long venueId, @Param("courtNumber") Integer courtNumber);

    int countByVenueId(Long venueId);

}
