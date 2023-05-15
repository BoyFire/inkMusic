import * as types from "./mutation-types";

export default {
  [types.SET_LOGIN](state, val) {
    state.isLogin = val;
  },
  setUserInfo(state, val) {
    state.userInfo = val;
  },
  setLoginDialog(state, val) {
    state.loginDialogVisible = val;
  },
  setRegisterDialog(state, val) {
    state.registerDialogVisible = val;
  },
  [types.SET_PLAYSTATUS](state, val = false) {
    state.isPlayed = val;
  },
  [types.SET_PLAYLIST](state, val = null) {
    state.playList = val;
    window.localStorage.setItem("playList", JSON.stringify(val));
  },
  [types.SET_PLAYINDEX](state, val = 0) {
    state.playIndex = val;
    window.localStorage.setItem("playIndex", val);
  },
  [types.SET_PLAYLISTTIPS](state, val = false) {
    state.isShowPlayListTips = val;
  },
  [types.SET_HISTORYSEARCHLIST](state, val = null) {
    state.SET_HISTORYSEARCHLIST = val;
    window.localStorage.setItem("historySearchList", JSON.stringify(val));
  },
};
