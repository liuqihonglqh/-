import request from '@/utils/request'

// 查询所有维修商品列表
export function listEquipmentRepairs(query) {
  return request({
    url: '/featherBall/equipmentRepair/listAll',
    method: 'get',
    params: query
  })
}

// 添加维修商品
export function addEquipmentRepair(data) {
  return request({
    url: '/featherBall/equipmentRepair/add',
    method: 'post',
    data: data
  })
}

// 更新维修商品信息
export function updateEquipmentRepair(data) {
  return request({
    url: '/featherBall/equipmentRepair/update',
    method: 'put',
    data: data
  })
}

// 删除维修商品
export function deleteEquipmentRepair(equipmentRepairId) {
  return request({
    url: '/featherBall/equipmentRepair/delete',
    method: 'delete',
    params: { equipmentRepairId }
  })
}
