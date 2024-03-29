export default {
  isLogin(state) {
    return state.isLogin || JSON.parse(window.localStorage.getItem("isLogin"));
  },
  isPlayed(state) {
    return state.isPlayed;
  },
  loginDialogVisible(state) {
    return state.loginDialogVisible;
  },
  registerDialogVisible(state) {
    return state.registerDialogVisible;
  },
  userInfo(state) {
    return (
      state.userInfo ||
      JSON.parse(window.localStorage.getItem("userInfo") || "{}")
    );
  },
  playList(state) {
    return state.playList.length
      ? state.playList
      : JSON.parse(window.localStorage.getItem("playList")) || [];
  },
  playIndex(state) {
    return (
      state.playIndex ||
      JSON.parse(window.localStorage.getItem("playIndex")) ||
      0
    );
  },
  isShowPlayListTips(state) {
    return state.isShowPlayListTips;
  },
  historySearchList(state) {
    return state.historySearchList.length
      ? state.getItem
      : JSON.parse(window.localStorage.getItem("historySearchList")) || [];
  },
};
