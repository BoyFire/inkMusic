import api from "./instance";


// 注册
const register = ({
  userNickname = "",
  userLogin = "",
  pwd = "",
  checkPwd = "",
}) => {
  return api.post(`/music/user/register`, {
    userNickname: userNickname,
    userLogin: userLogin,
    pwd: pwd,
    checkPwd: checkPwd,
  });
};

// 登录
const myLogin = ({ userAuthType = "0", username = "", pwd = "" }) => {
  return api.post(`/music/user/login`, {
    userAuthType: userAuthType,
    username: username,
    password: pwd,
  });
};
//登出
export function myLogout(header) {
  return api.delete(`/music/user/logout`, {
    headers: {
      Authorization: header,
    },
  });
}
//获取用户信息
export function getMyUserInfo({ userId = 0 }) {
  return api.get(`/music/user/getUserInfo?userId=${userId}`, {})
}
//获取用户创建的歌单
export function getUserCreatSongList({ userId = 0 }) {
  return api.get(`/music/songList/getUserSongLists?userId=${userId}`, {})
}
//获取用户收藏的歌单
export function getUserCollectSongList({ userId = 0 }) {
  return api.get(`/music/songList/getUserCollectSongLists?userId=${userId}`, {})
}
//获取用户收藏的歌单
export function getUserCollectApiSongList({ userId = 0 }) {
  return api.get(`/music/songList/getUserCollectApiSongLists?userId=${userId}`, {})
}
//获取评论详情
export function getCommentDetail({ songListId = 0, type = 4 }) {
  //1-评论,2-专辑,3-歌曲,4-歌单,5-视频
  return api.get(`/music/songListSong/detail?songListId=${songListId}&type=${type}`, {})
}
//获取相似歌单
export function getSongListRelated({ songListId = 4 }) {
  return api.get(`/music/songList/getRelated?songListId=${songListId}`, {})
}
// 获取我的歌单详情
export function getMySongListDetail({ songListId = 4 }) {
  return api.get(`/music/songListSong/detail?songListId=${songListId}`, {})
}
//获取歌曲重定向地址
export function getRedirectUrl({ url = "" }) {
  return api.get(`/music/song/getRedirectUrl?url=${url}`, { withCredentials: true, })
}
//更新用户信息
export function updateMyUserInfo(params) {
  return api.post(`/music/user/updateUserInfo`, {
    id: params.id,
    userId: params.userId,
    userNickname: params.userNickname,
    userImgUrl: params.userImgUrl,
    userGender: params.userGender
  })
}
//收藏歌曲
export function collest(params) {
  return api.post(`/music/songListSong/collectSong`, {
    songListId: params.songListId,
    apiSongId: params.apiSongId
  })
}
//取消收藏歌曲
export function disCollectSong(params) {
  return api.post(`/music/songListSong/disCollectSong`, {
    songListId: params.songListId,
    songId: params.songId
  })
}
//是否已收藏歌单
export function isCollectApi(params) {
  return api.post(`/music/songList/isCollectApi`, {
    userId: params.userId,
    apiSongListId: params.apiSongListId
  })
}

//收藏歌单
export function collestSongList(params) {
  return api.post(`/music/songList/collectApiSongList`, {
    userId: params.userId,
    apiSongListId: params.apiSongListId,
    items: params.items
  })
}
//取消收藏歌单
export function disCollectSongList(params) {
  return api.post(`/music/songList/disCollectApiSongList`, {
    userId: params.userId,
    apiSongListId: params.apiSongListId,
  })
}
//新增歌单
export function createSongList(params) {
  return api.post(`/music/songList`, {
    songListCreatorId: params.songListCreatorId,
    songListImgUrl: params.songListImgUrl,
    songListTitle: params.songListTitle,
    songListStatus: params.songListStatus,
    createBy: params.createBy
  })
}

export function deleteSongList(ids) {
  return api.post(`/music/songList/deleteSongLists`,
    { ids })

}

// 首页轮播图
const getBanner = () => {
  return api.get("/api/banner", {});
};
// 搜索
const search = ({ keywords = "" }) => {
  return api.get(`/api/search?keywords=${keywords}`, {});
};
// 搜索类型；默认为 1 即单曲 , 取值意义 : 1: 单曲, 10: 专辑, 100: 歌手, 1000: 歌单, 1002: 用户, 1004: MV, 1006: 歌词, 1009: 电台, 1014: 视频, 1018:综合
const cloudsearch = ({ keywords = "", limit = 30, offset = 0, type = "1" }) => {
  return api.get(
    `/api/cloudsearch?keywords=${keywords}&limit=${limit}&offset=${offset}&type=${type}`,
    {}
  );
};
// 热门搜索
const serachHot = () => {
  return api.get("/api/search/hot", {});
};
// 热门搜索详细
const serachHotDetail = () => {
  return api.get("/api/search/hot/detail", {});
};
const serachSuggest = ({ keywords = "" }) => {
  return api.get(`/api/search/suggest?keywords=${keywords}`, {});
};
const serachMatch = ({ keywords = "" }) => {
  return api.get(`/api/search/multimatch?keywords=${keywords}`, {});
};
//匿名登录
const anonimous = () => {
  return api.get(`/api/register/anonimous`);
};

// 登录
const login = ({ phone = "", pwd = "", realIP = "43.241.243.255" }) => {
  return api.post(`/api/login/cellphone`, { phone, password: pwd, realIP });
};
// 退出登录
const logout = () => {
  return api.get("/api/logout", {});
};
// 获取用户详情
const getUserInfo = ({ uid = "" }) => {
  return api.get(`/api/user/detail?uid=${uid}`, {});
};
// 音乐是否可用
const checkSong = ({ id = "" }) => {
  return api.get(`/api/check/music?id=${id}`, {});
};

/* ********* 歌单 ********* */
// 热门歌单分类
const hotList = () => {
  return api.get("/api/playlist/hot", {});
};
// 歌单列表
const playList = ({ order = "hot", cat = "", limit = 50, offset = 0 }) => {
  return api.get(
    `/api/top/playlist?limit=${limit}&order=${order}&cat=${cat}&offset=${offset}`,
    {}
  );
};
// 推荐歌单
const personalized = (limit = 30) => {
  return api.get(`/api/personalized?limit=${limit}`, {});
};
// 精品歌单
const highquality = (limit = 20, before = 0) => {
  return api.get(
    `/api/top/playlist/highquality?limit=${limit}&before=${before}`,
    {}
  );
};
// 精品歌单标签
const highqualitytag = () => {
  return api.get("/api/playlist/highquality/tags", {});
};
// 歌单分类
const catlist = () => {
  return api.get("/api/playlist/catlist", {});
};
// 歌单详情
const playlistdetail = ({ id = "", s = 8 }) => {
  return api.get(`/api/playlist/detail?id=${id}&s=${s}`, {});
};
// 歌单收藏用户
const playlistSCollect = ({ id = "", limit = 20, offset = 0 }) => {
  return api.get(
    `/api/playlist/subscribers?id=${id}&limit=${limit}&offset=${offset}`,
    {}
  );
};
// 相关歌单推荐
const playlistRelated = ({ id = "" }) => {
  return api.get(`/api/related/playlist?id=${id}`, {});
};
// 歌单评论
const playlistComment = ({ id = "", limit = 20, offset = 0, before = 0 }) => {
  return api.get(
    `/api/comment/playlist?id=${id}&limit=${limit}&offset=${offset}&before=${before}`,
    {}
  );
};
// 收藏、取消歌单 1：收藏 2取消
const subPlayList = ({ t = 1, id = "" }) => {
  return api.get(`/api/playlist/subscribe?t=${t}&id=${id}`, {});
};
// 获取用户歌单
const playlistUser = ({ uid = "", limit = 30, offset = 0 }) => {
  return api.get(
    `/user/playlist?uid=${uid}&limit=${limit}&offset=${offset}`,
    {}
  );
};
// 添加歌曲到歌单
const addPlayList = ({ op = "add", pid = "", tracks = "" }) => {
  return api.get(
    `/api/playlist/tracks?op=${op}&pid=${pid}&tracks=${tracks}`,
    {}
  );
};

/* ********* 歌曲 ********* */
// 歌曲详情 多个id , 隔开
const songDetail = ({ ids = "", timestamp = 0 }) => {
  return api.post(`/api/song/detail?timestamp=${timestamp}`, { ids: ids });
};
// 获取音乐URL
const songUrl = ({ id = "" }) => {
  return api.get(`/api/song/url?id=${id}`, {});
};
// 喜欢歌曲
const likeSong = ({ id = "", like = false }) => {
  return api.get(`/api/like?id=${id}&like=${like}`, {});
};
// 歌词
const lyrics = ({ id = "" }) => {
  return api.get(`/api/lyric?id=${id}`, {});
};
// 获取相似音乐
const simiSong = ({ id = "" }) => {
  return api.get(`/api/simi/song?id=${id}`, {});
};
// 包含这首歌的歌单
const simiPlayList = ({ id = "" }) => {
  return api.get(`/api/simi/playlist?id=${id}`, {});
};

/* ********* 歌曲评论 ********* */
// 歌曲评论
const commentSong = ({
  id = "",
  limit = 20,
  offset = 0,
  before = 0,
  timestamp = 0,
}) => {
  return api.get(
    `/api/comment/music?id=${id}&limit=${limit}&offset=${offset}&before=${before}&timestamp=${timestamp}`,
    {}
  );
};
/*
 * 发送/删除评论
 * t: 0删除 1发送 2回复
 * type: 0: 歌曲 1: mv 2: 歌单 3: 专辑  4: 电台 5: 视频 6: 动态
 * id: 对应资源id
 * content: 发送的内容/对应内容的id
 * commentId: 回复的评论id
 */
const comment = ({
  t = 1,
  type = 0,
  id = "",
  content = "",
  commentId = "",
}) => {
  return api.get(
    `/api/comment?t=${t}&type=${type}&id=${id}&content=${content}&commentId=${commentId}`,
    {}
  );
};
/*
 * 给评论点赞
 * id: 对应资源id
 * cid: 评论id
 * t: 是否点赞 1: 是  0: 取消
 * type: 0: 歌曲 1: mv 2: 歌单 3: 专辑  4: 电台 5: 视频 6: 动态
 */
const commentLike = ({ id = "", cid = "", t = 1, type = 0 }) => {
  return api.get(
    `/api/comment/like?id=${id}&cid=${cid}&t=${t}&type=${type}`,
    {}
  );
};

/* ********* 专辑 ********* */
// 获取专辑内容
const album = ({ id = "" }) => {
  return api.get(`/api/album?id=${id}`, {});
};
const albumDynamic = ({ id = "" }) => {
  return api.get(`/api/album/detail/dynamic?id=${id}`, {});
};
const albumSub = ({ id = "", t = 1 }) => {
  return api.get(`/api/album/sub?id=${id}&t=${t}`, {});
};
// 专辑评论
const albumComment = ({
  id = "",
  limit = 20,
  offset = 0,
  before = 0,
  timestamp = 0,
}) => {
  return api.get(
    `/api/comment/album?id=${id}&limit=${limit}&offset=${offset}&before=${before}&timestamp=${timestamp}`,
    {}
  );
};

/* ********* 歌手 ********* */
//歌手详情
const artistDetall = ({ id = "" }) => {
  return api.get(`/api/artist/detail?id=${id}`, {});
};
// 歌手介绍
const artistDesc = ({ id = "" }) => {
  return api.get(`/api/artist/desc?id=${id}`, {});
};
// 歌手热门歌曲
const artists = ({ id = "" }) => {
  return api.get(`/api/artists?id=${id}`, {});
};
// 收藏/取消收藏歌手
const artistSub = ({ id = "", t = "1" }) => {
  return api.get(`/api/artist/sub?id=${id}&t=${t}`, {});
};
// 相似歌手
const artistSimi = ({ id = "" }) => {
  return api.get(`/api/simi/artist?id=${id}`, {});
};
// 获取歌手专辑
const artistAlbum = ({ id = "", limit = 50, offset = 0 }) => {
  return api.get(
    `/api/artist/album?id=${id}&limit=${limit}&offset=${offset}`,
    {}
  );
};
// 获取歌手 mv
const artistMv = ({ id = "", limit = 50, offset = 0 }) => {
  return api.get(`/api/artist/mv?id=${id}&limit=${limit}&offset=${offset}`, {});
};
// 获取歌手列表
/*
 * 给评论点赞
 * type: -1:全部; 1:男歌手; 2:女歌手; 3:乐队
 * area: -1:全部; 7华语; 96欧美; 8:日本; 16韩国; 0:其他
 * initial: 按首字母索引查找参数, 热门传-1, #传0
 * limit: 30
 * offset: 0
 */
const artistList = ({
  type = -1,
  area = -1,
  initial = "",
  limit = 50,
  offset = 0,
}) => {
  return api.get(
    `/api/artist/list?type=${type}&area=${area}&initial=${initial}&limit=${limit}&offset=${offset}`,
    {}
  );
};
// 收藏的歌手列表
const subArtist = () => {
  return api.get("/api/artist/sublist", {});
};

/* ********* MV ********* */
// 获取 mv
const mv = ({ area = "", type = "", order = "", limit = 50, offset = 0 }) => {
  return api.get(
    `/api/mv/all?area=${area}&type=${type}&order=${order}&limit=${limit}&offset=${offset}`,
    {}
  );
};
// 获取 mv详情
const mvDetail = ({ id = "" }) => {
  return api.get(`/api/mv/detail?mvid=${id}`, {});
};
// 获取 地址
const mvUrl = ({ id = "", r = 1080 }) => {
  return api.get(`/api/mv/url?id=${id}&r=${r}`, {});
};
// 获取mv评论
const commentMv = ({
  id = "",
  limit = 20,
  offset = 0,
  before = 0,
  timestamp = 0,
}) => {
  return api.get(
    `/api/comment/mv?id=${id}&limit=${limit}&offset=${offset}&before=${before}&timestamp=${timestamp}`,
    {}
  );
};
// 相似mv
const simiMv = ({ id = "" }) => {
  return api.get(`/api/simi/mv?mvid=${id}`, {});
};

/* ********* 排行榜 ********* */
// 排行榜
const toplist = () => {
  return api.get("/api/toplist", {});
};
// 排行榜歌单列表
const topRankList = ({ id = "", s = 8 }) => {
  return api.get(`/api/playlist/detail?id=${id}&s=${s}`, {});
};
// 所有榜单内容摘要
const topListDetail = () => {
  return api.get("/api/toplist/detail", {});
};
// 歌单详情
const listDetail = ({ id = "", s = 8 }) => {
  return api.get(`/api/playlist/detail?id=${id}&s=${s}`, {});
};

/* ********* video ********* */
// 视频播放地址
const videoUrl = ({ id = "", r = 1080 }) => {
  return api.get(`/api/video/url?id=${id}&r=${r}`, {});
};
// 获取 video 详情
const videoDetail = ({ id = "" }) => {
  return api.get(`/api/video/detail?id=${id}`, {});
};
// 相似video
const simiVideo = ({ id = "" }) => {
  return api.get(`/api/related/allvideo?id=${id}`, {});
};
// 获取video评论
const commentVideo = ({
  id = "",
  limit = 20,
  offset = 0,
  before = 0,
  timestamp = 0,
}) => {
  return api.get(
    `/api/comment/video?id=${id}&limit=${limit}&offset=${offset}&before=${before}&timestamp=${timestamp}`,
    {}
  );
};

// 热门话题
const hotTopic = (limit = 20, offset = 0) => {
  return api.get(`/api/hot/topic?limit=${limit}&offset=${offset}`, {});
};
// 新碟上架
const topAlbum = ({
  limit = 20,
  offset = 0,
  area = "all",
  type = "new",
  year = "2023",
  month = "4",
}) => {
  return api.get(
    `/api/top/album?limit=${limit}&offset=${offset}&area=${area}&type=${type}&year=${year}&month=${month}`,
    {}
  );
};
// 热门歌手
const topArtists = ({ limit = 30, offset = 0 }) => {
  return api.get(`/api/top/artists?limit=${limit}&offset=${offset}`, {});
};
// 最新MV
const getNewMv = ({ limit = 30, area = "" }) => {
  return api.get(`/api/mv/first?limit=${limit}&area=${area}`, {});
};
// 热门电台
const getHotDj = ({ limit = 30, offset = 0 }) => {
  return api.get(`/api/dj/hot?limit=${limit}&offset=${offset}`, {});
};

export {
  getBanner,
  search,
  serachHot,
  serachHotDetail,
  serachSuggest,
  serachMatch,
  cloudsearch,
  login,
  anonimous,
  logout,
  getUserInfo,
  checkSong,
  hotList,
  playList,
  catlist,
  topRankList,
  playlistdetail,
  playlistSCollect,
  playlistRelated,
  playlistComment,
  subPlayList,
  playlistUser,
  addPlayList,
  songDetail,
  songUrl,
  likeSong,
  lyrics,
  simiSong,
  simiPlayList,
  commentSong,
  comment,
  commentLike,
  album,
  albumSub,
  albumDynamic,
  albumComment,
  artistDetall,
  artistDesc,
  artists,
  artistSub,
  artistAlbum,
  artistMv,
  artistList,
  artistSimi,
  mv,
  mvDetail,
  mvUrl,
  commentMv,
  simiMv,
  personalized,
  highquality,
  highqualitytag,
  videoUrl,
  videoDetail,
  simiVideo,
  commentVideo,
  hotTopic,
  topAlbum,
  toplist,
  topListDetail,
  listDetail,
  getNewMv,
  topArtists,
  subArtist,
  getHotDj,

  // 我的程序
  register,
  myLogin,
};
