import request from '@/utils/request'

// 查询某场馆的评价列表
export function listReviewsByVenueId(venueId) {
  return request({
    url: '/featherBall/review/listByVenueId',
    method: 'get',
    params: { venueId }
  })
}

// 查询评价详细信息
export function getReview(reviewId) {
  return request({
    url: '/featherBall/review/get',
    method: 'get',
    params: { reviewId }
  })
}

// 添加评价
export function addReview(data) {
  return request({
    url: '/featherBall/review/add',
    method: 'post',
    data: data
  })
}

// 删除评价
export function deleteReview(reviewId) {
  return request({
    url: '/featherBall/review/delete',
    method: 'get',
    params: { reviewId }
  })
}
