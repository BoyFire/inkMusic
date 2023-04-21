import request from "@/utils/request";

// 查询专辑列表
export function listAlbum(query) {
  return request({
    url: "/music/album/list",
    method: "get",
    params: query,
  });
}

// 查询专辑详细
export function getAlbum(id) {
  return request({
    url: "/music/album/" + id,
    method: "get",
  });
}

//查询单个专辑 通过歌手id
export function listAlbumIdAndName(singerId) {
  return request({
    url: "/music/album/list/" + singerId,
    method: "get",
  });
}

//批量查询专辑 通过歌手
export function listAllSimpleAlbum(query) {
  return request({
    url: "/music/album/list/simpleAlbums",
    method: "post",
    params: query,
  });
}

//查询简单专辑列表
export function listSimpleAlbum() {
  return request({
    url: "/music/album/listSimpleAlbum",
    method: "get",
  });
}

export function selectSimpleSingerByName(query) {
  return request({
    url: "/music/album/getSimpleAlbumByName?query=" + query,
    method: "get",
  });
}

// 新增专辑
export function addAlbum(data) {
  return request({
    url: "/music/album",
    method: "post",
    data: data,
  });
}

// 修改专辑
export function updateAlbum(data) {
  return request({
    url: "/music/album",
    method: "put",
    data: data,
  });
}

// 删除专辑
export function delAlbum(id) {
  return request({
    url: "/music/album/" + id,
    method: "delete",
  });
}
