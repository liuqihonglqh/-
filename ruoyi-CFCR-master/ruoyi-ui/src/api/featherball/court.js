// court.js

import request from '@/utils/request'

// 查询所有场地列表
export function listCourts(query) {
  return request({
    url: '/featherBall/court/listAll',
    method: 'get',
    params: query
  })
}

// 查询场地详细信息
export function getCourt(courtId) {
  return request({
    url: '/featherBall/court/detail',
    method: 'get',
    params: { courtId }
  })
}

// 添加场地
export function addCourt(data) {
  return request({
    url: '/featherBall/court/add',
    method: 'post',
    data: data
  })
}

// 更新场地信息
export function updateCourt(data) {
  return request({
    url: '/featherBall/court/update',
    method: 'post',
    data: data
  })
}

// 删除场地
export function deleteCourt(courtId) {
  return request({
    url: '/featherBall/court/delete',
    method: 'get',
    params: { courtId }
  })
}

// 查询所有教练员列表
export function listTrainers() {
  return request({
    url: '/featherBall/court/getTrainer',
    method: 'get'
  })
}

// 查询场馆与场地数量的映射
export function getVenueCourtCountMap() {
  return request({
    url: '/featherBall/court/getMap',
    method: 'get'
  })
}
