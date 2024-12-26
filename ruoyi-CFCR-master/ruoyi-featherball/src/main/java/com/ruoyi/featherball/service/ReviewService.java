package com.ruoyi.featherball.service;

import com.ruoyi.featherball.domain.Review;

import java.util.List;

/**
 * 场地评价表 Service
 *
 * @Author fanjaixing
 * @Date 2024/3/2 19:44
 */
public interface ReviewService {

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
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addReview(Review review);

    /**
     * 删除评价
     *
     * @param reviewId 待删除的评价ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteReview(Long reviewId);

    /**
     * 根据场馆ID获取评论
     *
     * @param venueId
     * @return
     */
    public List<Review> listReviewsByVenueId(Long venueId);
}
