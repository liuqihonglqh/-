package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 场地评价表 Mapper
 *
 * @Author fanjaixing
 * @Date 2024/3/2 19:46
 */
@Mapper
public interface ReviewMapper {
    /**
     * 根据场馆ID获取场地评价列表
     *
     * @param venueId 场地ID
     * @return 场地评价列表
     */
    List<Review> listReviewsByVenueId(Long venueId);

    /**
     * 根据评价ID获取评价信息
     *
     * @param reviewId 评价ID
     * @return 评价信息
     */
    Review getReviewById(Long reviewId);

    /**
     * 添加评价
     *
     * @param review 待添加的评价信息
     * @return 添加成功返回影响的行数，否则返回 0
     */
    int addReview(Review review);

    /**
     * 删除评价
     *
     * @param reviewId 待删除的评价ID
     * @return 删除成功返回影响的行数，否则返回 0
     */
    int deleteReview(Long reviewId);
}
