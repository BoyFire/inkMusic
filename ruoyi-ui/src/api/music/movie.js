import request from '@/utils/request'

// 查询视频列表
export function listMovie(query) {
  return request({
    url: '/music/movie/list',
    method: 'get',
    params: query
  })
}

// 获取简单视频列表
export function listSimpleMovie() {
  return request({
    url: '/music/movie/listSimpleMovie',
    method: 'get',
  })
}


// 查询视频详细
export function getMovie(id) {
  return request({
    url: '/music/movie/' + id,
    method: 'get'
  })
}

// 新增视频
export function addMovie(data) {
  return request({
    url: '/music/movie',
    method: 'post',
    data: data
  })
}

// 修改视频
export function updateMovie(data) {
  return request({
    url: '/music/movie',
    method: 'put',
    data: data
  })
}

// 删除视频
export function delMovie(id) {
  return request({
    url: '/music/movie/' + id,
    method: 'delete'
  })
}
