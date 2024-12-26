import request from '@/utils/request'

// 查询所有购买商品列表
export function listEquipmentPurchases(query) {
  return request({
    url: '/featherBall/equipmentPurchase/listAll',
    method: 'get',
    params: query
  })
}

// 添加购买商品
export function addEquipmentPurchase(data) {
  return request({
    url: '/featherBall/equipmentPurchase/add',
    method: 'post',
    data: data
  })
}

// 更新购买商品信息
export function updateEquipmentPurchase(data) {
  return request({
    url: '/featherBall/equipmentPurchase/update',
    method: 'put',
    data: data
  })
}

// 删除购买商品
export function deleteEquipmentPurchase(equipmentPurchaseId) {
  return request({
    url: '/featherBall/equipmentPurchase/delete',
    method: 'delete',
    params: { equipmentPurchaseId }
  })
}
