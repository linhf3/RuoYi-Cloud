import request from '@/utils/request'

// 查询证劵交易列表
export function listSecurities(query) {
  return request({
    url: '/securities/securities/list',
    method: 'get',
    params: query
  })
}

// 查询证劵交易详细
export function getSecurities(id) {
  return request({
    url: '/securities/securities/' + id,
    method: 'get'
  })
}

// 新增证劵交易
export function addSecurities(data) {
  return request({
    url: '/securities/securities',
    method: 'post',
    data: data
  })
}

// 修改证劵交易
export function updateSecurities(data) {
  return request({
    url: '/securities/securities',
    method: 'put',
    data: data
  })
}

// 删除证劵交易
export function delSecurities(id) {
  return request({
    url: '/securities/securities/' + id,
    method: 'delete'
  })
}
