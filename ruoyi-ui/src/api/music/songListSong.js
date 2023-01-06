import request from '@/utils/request'

// 查询歌单歌曲列表
export function listSongListSong(query) {
  return request({
    url: '/music/songListSong/list',
    method: 'get',
    params: query
  })
}

// 查询歌单歌曲详细
export function getSongListSong(id) {
  return request({
    url: '/music/songListSong/' + id,
    method: 'get'
  })
}

// 新增歌单歌曲
export function addSongListSong(data) {
  return request({
    url: '/music/songListSong',
    method: 'post',
    data: data
  })
}

// 修改歌单歌曲
export function updateSongListSong(data) {
  return request({
    url: '/music/songListSong',
    method: 'put',
    data: data
  })
}

// 删除歌单歌曲
export function delSongListSong(id) {
  return request({
    url: '/music/songListSong/' + id,
    method: 'delete'
  })
}
