import request from '@/utils/request'

// 查询用户id 去重
export function getUserIds(){
  return request({
    url:'/music/auth/userIdList',
    method: 'get'
  })
}

// 查询用户认证列表
export function listAuth(query) {
  return request({
    url: '/music/auth/list',
    method: 'get',
    params: query
  })
}

// 查询用户认证详细
export function getAuth(id) {
  return request({
    url: '/music/auth/' + id,
    method: 'get'
  })
}

// 新增用户认证
export function addAuth(data) {
  return request({
    url: '/music/auth',
    method: 'post',
    data: data
  })
}

// 修改用户认证
export function updateAuth(data) {
  return request({
    url: '/music/auth',
    method: 'put',
    data: data
  })
}

// 删除用户认证
export function delAuth(id) {
  return request({
    url: '/music/auth/' + id,
    method: 'delete'
  })
}
