import request from '@/utils/request'

// 查询所有场馆列表
export function listVenues(query) {
  return request({
    url: '/featherBall/venue/listAll',
    method: 'get',
    params: query
  })
}

// 查询场馆详细信息
export function getVenue(venueId) {
  return request({
    url: '/featherBall/venue/detail',
    method: 'get',
    params: { venueId }
  })
}

// 添加场馆
export function addVenue(data) {
  return request({
    url: '/featherBall/venue/add',
    method: 'post',
    data: data
  })
}

// 更新场馆信息
export function updateVenue(data) {
  return request({
    url: '/featherBall/venue/update',
    method: 'post',
    data: data
  })
}

// 删除场馆
export function deleteVenue(venueId) {
  return request({
    url: '/featherBall/venue/delete',
    method: 'get',
    params: { venueId }
  })
}
