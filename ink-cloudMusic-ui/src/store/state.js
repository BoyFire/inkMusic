export default {
  isLogin: false, //是否登录
  loginDialogVisible: false, // 登录弹窗显示与隐藏
  registerDialogVisible: false, // 注册弹窗显示与隐藏
  isPlayed: false, //播放状态
  playList: [], //播放列表
  userInfo: null, //用户信息
  playIndex: 0, // 当前播放歌曲在播放列表的所有位置
  isShowPlayListTips: false, // 添加及播放成功后，播放列表按钮提示的文字
  historySearchList: [], //历史搜索存储
  historySearchSize: 10, // 存储历史记录大小
};
