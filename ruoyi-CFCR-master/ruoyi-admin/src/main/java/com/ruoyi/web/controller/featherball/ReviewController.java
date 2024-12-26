package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.featherball.domain.Review;
import com.ruoyi.featherball.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制器实现场地评价的增删查操作
 *
 * @Author fanjaixing
 * @Date 2024/3/2 19:44
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/review")
public class ReviewController extends BaseController {

    private final ReviewService reviewService;

    /**
     * 获取某场地的评价列表
     *
     * @param venueId 场馆ID
     * @return 评价列表
     */
    @GetMapping("/listByVenueId")
    public TableDataInfo listReviewsByVenueId(@RequestParam Long venueId) {
        List<Review> reviewList = reviewService.listReviewsByVenueId(venueId);
        return getDataTable(reviewList);
    }

    /**
     * 根据评价ID获取评价信息
     *
     * @param reviewId 评价ID
     * @return 评价信息
     */
    @GetMapping("/get")
    public AjaxResult getReviewById(@RequestParam Long reviewId) {
        return success(reviewService.getReviewById(reviewId));

    }

    /**
     * 添加评价
     *
     * @param review 待添加的评价信息
     * @return 添加成功返回 true，否则返回 false
     */
    @PostMapping("/add")
    public AjaxResult addReview(@RequestBody Review review) {
        return toAjax(reviewService.addReview(review));

    }

    /**
     * 删除评价
     *
     * @param reviewId 待删除的评价ID
     * @return 删除成功返回 true，否则返回 false
     */
    @GetMapping("/delete")
    public AjaxResult deleteReview(@RequestParam Long reviewId) {
        return toAjax(reviewService.deleteReview(reviewId));

    }
}
