import request from '@/utils/request'

// 通过 歌曲id 查询 标签
export function getSimpleTagsBySongId(query) {
    return request({
        url: '/music/tag/getSimpleTags/' + query,
        method: 'get',
    })
}

// 通过 歌曲id 查询 标签
export function deleteSongTagByTagId(tagId) {
    return request({
        url: '/music/tag/deleteSongTagByTagId/' + tagId,
        method: 'delete',
    })
}

// 新增歌曲标签
export function addSongTags(data,songId) {
    return request({
      url: '/music/tag/addSongTags/'+ songId,
      method: 'post',
      data: data
    })
}