import request from '@/utils/request'

// 查询实时期货分析数据
export function findList() {
  return request({
    url: '/securities/securities/findList',
    method: 'post'
  })
}
