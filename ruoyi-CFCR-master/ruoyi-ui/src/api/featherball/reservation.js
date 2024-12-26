import request from '@/utils/request'

// 查询所有预约信息列表
export function listReservations(query) {
  return request({
    url: '/featherBall/reservation/listAll',
    method: 'get',
    params: query
  })
}

// 查询预约详细信息
export function getReservation(reservationId) {
  return request({
    url: '/featherBall/reservation/detail',
    method: 'get',
    params: { reservationId }
  })
}

// 添加预约信息
export function addReservation(data) {
  return request({
    url: '/featherBall/reservation/add',
    method: 'post',
    data: data
  })
}

// 更新预约信息
export function updateReservation(data) {
  return request({
    url: '/featherBall/reservation/update',
    method: 'post',
    data: data
  })
}

// 删除预约
export function deleteReservation(reservationId) {
  return request({
    url: '/featherBall/reservation/delete',
    method: 'get',
    params: { reservationId }
  })
}

// 获取场地预约提示信息
export function getVIPUserNotification(courtId) {
  return request({
    url: '/featherBall/reservation/getVIP',
    method: 'get',
    params: { courtId }
  })
}

// 取消预约
export function cancelReservation(reservationId) {
  return request({
    url: '/featherBall/reservation/cancel',
    method: 'get',
    params: { reservationId }
  })
}
