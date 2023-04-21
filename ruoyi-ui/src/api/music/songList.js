import request from "@/utils/request";

// 查询歌单列表
export function listSongList(query) {
  return request({
    url: "/music/songList/list",
    method: "get",
    params: query,
  });
}

// 获取简单歌单列表
export function listSimpleSongList() {
  return request({
    url: "/music/songList/listSimpleSongList",
    method: "get",
  });
}

// 查询歌单详细
export function getSongList(id) {
  return request({
    url: "/music/songList/" + id,
    method: "get",
  });
}

// 新增歌单
export function addSongList(data) {
  return request({
    url: "/music/songList",
    method: "post",
    data: data,
  });
}

// 修改歌单
export function updateSongList(data) {
  return request({
    url: "/music/songList",
    method: "put",
    data: data,
  });
}

// 删除歌单
export function delSongList(id) {
  return request({
    url: "/music/songList/" + id,
    method: "delete",
  });
}

export function selectSimpleSongListsByName(params) {
  return request({
    url: "/music/songList/getSimpleSongListsByName?query=" + query,
    method: "get",
  });
}
