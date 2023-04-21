import request from "@/utils/request";

// 查询歌手列表
export function listSinger(query) {
  return request({
    url: "/music/singer/list",
    method: "get",
    params: query,
  });
}

// 查询歌手详细
export function getSinger(id) {
  return request({
    url: "/music/singer/" + id,
    method: "get",
  });
}

// 新增歌手
export function addSinger(data) {
  return request({
    url: "/music/singer",
    method: "post",
    data: data,
  });
}

// 修改歌手
export function updateSinger(data) {
  return request({
    url: "/music/singer",
    method: "put",
    data: data,
  });
}

// 删除歌手
export function delSinger(id) {
  return request({
    url: "/music/singer/" + id,
    method: "delete",
  });
}

//查询歌手id及姓名列表
export function listSingerIdAndSingerName() {
  return request({
    url: "/music/singer/listIdAndName",
    method: "get",
  });
}

//根据歌手姓名模糊查询
export function selectSimpleSingerByName(query) {
  return request({
    url: "/music/singer/getSimpleSingerByName?query=" + query,
    method: "get",
  });
}
