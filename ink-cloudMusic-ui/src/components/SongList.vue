<!-- 详细的歌曲展示 -->
<template>
  <div class="songs-list-main">
    <!-- 标题栏 -->
    <div class="list-header flex">
      <div class="columnIndex">序号</div>
      <div class="columnSong">歌曲</div>
      <div class="columnSinger">歌手</div>
      <div class="columnAlbum" v-if="typeSize !== 'mini'">专辑</div>
      <div class="columnTime">时长</div>
    </div>

    <div
      class="list-scroll"
      :style="{ height: height + 'px' }"
      ref="curSongRef">
      <div class="list-main" :style="curSongSty">
        <div
          :class="isCurSong(item, index)"
          v-for="(item, index) in list"
          :key="item.id">
          <!-- 序号 -->
          <div class="columnIndex">
            <!-- 显示序号 -->
            <span class="songlist-index">{{ indexMethod(index) }}</span>
            <!-- 播放按钮样式 -->
            <div class="audio-icon">
              <div class="column" style="animation-delay: -1.2s"></div>
              <div class="column"></div>
              <div class="column" style="animation-delay: -1.5s"></div>
              <div class="column" style="animation-delay: -0.9s"></div>
              <div class="column" style="animation-delay: -0.6s"></div>
            </div>
            <!-- 按钮样式 -->
            <i :class="playIcon(item)" @click="currentSong(item)"></i>
          </div>
          <!-- 歌曲名 -->
          <div class="columnSong songlist-name">
            <router-link
              :class="typeSize"
              :to="{ path: '/song', query: { id: item.id } }">
              {{ item.name }}
            </router-link>
            <template v-if="typeSize !== 'mini'">
              <router-link
                class="mv-name"
                :to="{ path: '/mvlist/mv', query: { id: item.mvId } }"
                v-if="item.mvId">
                <i class="iconfont icon-mv"></i>
              </router-link>
              <i v-if="item.vip" class="iconfont icon-vip"></i>
            </template>
          </div>
          <!-- 歌手名 -->
          <div class="columnSinger songlist-singer">
            <!--- 返回的数据里用户的uid有可能为0 --->
            <router-link
              :to="{ path: '/artist/detail', query: { id: author.id } }"
              class="song_name"
              v-for="(author, k) in item.singer"
              :key="author.name"
              >{{ k !== 0 ? " / " + author.name : author.name }}</router-link
            >
          </div>
          <!-- 专辑名 -->
          <div class="columnAlbum" v-if="typeSize !== 'mini'">
            <router-link
              class="songlist-album"
              :to="{ path: '/album', query: { id: item.album.id } }"
              v-if="item.album"
              >{{ item.album.name }}</router-link
            >
          </div>
          <!-- 时长 -->
          <div class="columnTime">
            <div class="songlist-time">
              {{ item.duration }}
            </div>
            <div class="songlist-oper">
              <i
                class="iconfont icon-add"
                title="添加到列表"
                v-if="typeSize !== 'mini'"
                @click="addSongList(item)"></i>
              <!-- 收藏按钮 -->
              <el-popover placement="top" :visible="collectShow">
                <p>请选择要添加到所在位置的歌单</p>
                <div>
                  <el-select
                    v-model="selectSongListId"
                    placeholder="请选择要添加到的歌单">
                    <el-option
                      v-for="item2 in mySongList"
                      :label="item2.songListTitle"
                      :value="item2.songListId"></el-option>
                  </el-select>

                  <el-button size="small" text @click="collectShow = false"
                    >取消</el-button
                  >
                  <el-button
                    size="small"
                    type="primary"
                    @click="subCollect(item)"
                    >confirm</el-button
                  >
                </div>
                <template #reference>
                  <i class="iconfont icon-collect" @click="likeSong(item)"></i>
                </template>
              </el-popover>

              <!-- mini模式下 从列表删除 -->
              <i
                class="iconfont icon-del"
                title="删除"
                v-if="typeSize === 'mini'"
                @click.stop="delList(index)"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 分页插件 -->
    <div class="pagination" v-if="isShowPagination">
      <el-pagination
        @current-change="currentChange"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        layout="prev, pager, next"
        :total="songList.length">
      </el-pagination>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import {
    computed,
    getCurrentInstance,
    nextTick,
    onMounted,
    Ref,
    ref,
    watch,
  } from "vue";
  import { useStore } from "vuex";

  const props = defineProps({
    // 歌曲列表
    songList: {
      type: Array<any>,
      required: true,
    },
    // 播放列表展示类型， 默认normal是歌单下的展示列表，mini是播放条下的歌曲列表的
    typeSize: {
      type: String,
      default: "normal",
    },
    // 隔行变色
    stripe: {
      type: Boolean,
      default: false,
    },
    height: {
      type: [Number, String],
      default: "auto",
    },
    // 分页加载 || 无限滚动加载
    isScroll: {
      type: Boolean,
      default: false,
    },
    // 若是列表有分页，偏移数量
    offset: {
      type: Number,
      default: 0,
    },
    // 分页 每页展示数量
    pageSize: {
      type: Number,
      default: 20,
    },
  });
  const { proxy } = getCurrentInstance();
  const songList = ref(props.songList);
  const curSongRef = ref(null);
  const store = useStore();
  const typeSize = ref(props.typeSize);
  const height = ref(props.height);
  const curScroll = ref(-1 || 1);
  const pageSize = ref(props.pageSize);
  const currentPage = ref(1);
  const playing: Ref<boolean> = ref(false);
  const userInfo = computed(() => store.getters.userInfo);
  const isLogin = computed(() => store.getters.isLogin);

  // 获取播放列表
  const playList = computed(() => store.getters.playList);
  const isPlayed = computed(() => store.getters.isPlayed);
  // 渲染样式
  const curSongSty = computed(() => {
    return `transform: translateY(${curScroll.value}px)`;
  });
  // 获取当前播放音乐信息
  const curSongInfo = computed(() => {
    return playList.value[store.getters.playIndex];
  });
  // 获取歌曲列表
  const list = computed(() => {
    if (!props.isScroll) {
      // 分页加载数据
      return props.songList.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
      );
    } else {
      return props.songList;
    }
  });
  // 序号及播放状态
  const playIcon = computed(() => {
    return (item) => {
      return [
        "iconfont",
        "playicon",
        isPlayed.value && item.id === curSongInfo.value.id
          ? "icon-pause"
          : "icon-play",
      ];
    };
  });

  // 展示分页
  const isShowPagination = computed(() => {
    return props.songList.length > pageSize.value && !props.isScroll;
  });
  // 列表样式
  const isCurSong = computed(() => {
    return (item, index) => {
      return [
        "list-item",
        props.stripe ? (index % 2 === 0 ? "stripe" : "") : "",
        isPlayed.value && item.id === curSongInfo.value.id ? "active" : "",
        item.license || item.vip ? "disable" : "",
        item.vip ? "vip" : "",
      ];
    };
  });
  // 表格列表序号格式化
  const indexMethod = (page) => {
    if (!props.isScroll) {
      return (currentPage.value - 1) * pageSize.value + page + 1 + props.offset;
    } else {
      return page + 1;
    }
  };

  // 播放当前播放歌曲
  const currentSong = (item) => {
    // 若当前歌曲 或者 当前播放歌曲不是本歌单显示的歌曲  立即播放当前歌单
    if (!curSongInfo.value || item.id !== curSongInfo.value.id) {
      store.dispatch("selectPlay", { list: [item] });
    } else {
      store.commit("SET_PLAYSTATUS", !isPlayed.value);
    }
  };
  // 添加当前歌曲到播放列表
  const addSongList = (item) => {
    store.dispatch("addList", { list: [item] });
    store.commit("SET_PLAYLISTTIPS", true);
  };
  // 在播放列表删除当前歌曲
  const delList = (index) => {
    if (playList.value.length > 1) {
      playList.value.splice(index, 1);
      store.commit("SET_PLAYLIST", playList.value);
    } else {
      store.commit("SET_PLAYSTATUS", false);
      store.commit("SET_PLAYLIST", []);
    }
  };
  // 滚动到当前播放音乐的位置
  const scrollCurSong = (cur) => {
    if (props.isScroll) {
      const curIndex = props.songList.findIndex((item) => {
        return item.id === cur.id;
      });

      if (curIndex > 7 && curIndex < props.songList.length - 8) {
        curScroll.value = -(curIndex - 4) * 50;
      } else if (
        curIndex >= props.songList.length - 8 &&
        props.songList.length - 8 > 0
      ) {
        curScroll.value = -(props.songList.length - 8) * 50;
      } else {
        curScroll.value = 0;
      }
      const $curSongRef = curSongRef.value;
      $curSongRef.addEventListener(
        "wheel",
        (event) => {
          if (event.wheelDelta > 0 || event.detail < 0) {
            curScroll.value =
              Math.abs(curScroll.value) > 0 ? curScroll.value + 50 : 0;
          } else {
            curScroll.value =
              Math.abs(curScroll.value) <
              ((props.songList.length - 8) / 2) * 100
                ? curScroll.value - 50
                : curScroll.value;
          }
        },
        { passive: true }
      );
    }
  };

  // 歌曲列表分页功能
  const currentChange = (page) => {
    currentPage.value = page;
  };
  // 未登录状态，点击登录按钮，显示登录框
  const loginDialog = () => {
    store.dispatch("loginSuc", true);
  };

  // 收藏歌曲
  const collectShow = ref(false);
  const selectSongListId = ref(0);
  const mySongList: Ref<any[]> = ref([]);
  const likeSong = async (item) => {
    if (!isLogin.value) {
      return loginDialog();
    }

    const { data: res } = await proxy.$http.getUserCreatSongList({
      userId: userInfo.value.userId,
    });
    if (res.code != 200) {
      return proxy.$http.error("数据请求失败");
    }
    mySongList.value = res.data;
    collectShow.value = true;
  };
  const subCollect = async (item) => {
    collectShow.value = false;
    const { data: res } = await proxy.$http.collest({
      songListId: selectSongListId.value,
      apiSongId: item.id,
    });
    if (res.code !== 200) {
      return proxy.$msg.error(res.msg);
    }
    proxy.$msg.success(res.msg);
  };

  watch(
    curSongInfo,
    (val) => {
      // 添加歌曲的时候，props的歌曲列表没有更新
      nextTick(() => {
        val && scrollCurSong(val);
      });
    },
    { deep: true }
  );

  watch(
    () => {
      return props.songList;
    },
    () => {
      currentPage.value = 1;
    }
  );

  onMounted(() => {});
  // 获取歌曲列表
  const getList = computed(() => {
    if (!props.isScroll) {
      // 分页加载数据
      return props.songList.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
      );
    } else {
      return props.songList;
    }
  });
</script>

<style scoped lang="less">
  .list-header {
    display: flex;
    line-height: 50px;
    border-bottom: 1px solid #ebeef5;
    font-weight: bold;
    color: #999;
  }
  .columnIndex {
    width: 80px;
    line-height: 50px;
    padding-left: 10px;
  }
  .columnSong {
    display: flex;
    flex: 2;
    width: 0;
    padding-right: 10px;
    overflow: hidden;
    align-items: center;
  }
  .columnSinger {
    flex: 1.5;
    width: 0;
    padding-right: 10px;
    overflow: hidden;
  }
  .columnAlbum {
    flex: 1;
    width: 0;
  }
  .columnTime {
    width: 100px;
    padding-right: 10px;
    text-align: right;
  }
  .audio-icon {
    display: none;
    width: 20px;
    height: 18px;
    margin: 16px 2px;
    overflow: hidden;

    .column {
      width: 2px;
      height: 100%;
      margin-left: 2px;
      background-color: var(--color-text-height);
      -webkit-animation: play 0.9s linear infinite alternate;
      animation: play 0.9s linear infinite alternate;
    }
  }
  .list-scroll {
    -moz-user-select: none; /*火狐*/
    -webkit-user-select: none; /*webkit浏览器*/
    -ms-user-select: none; /*IE10*/
    -khtml-user-select: none; /*早期浏览器*/
    user-select: none;
    overflow-y: hidden;
  }
  .list-main {
    transition: transform 0.3s;
  }
  .list-item {
    display: flex;
    line-height: 0;

    .iconfont {
      margin-left: 10px;
      font-size: 22px;
      cursor: pointer;
      vertical-align: middle;

      &:hover {
        color: var(--color-text-height);
      }
    }

    .icon-mv {
      font-size: 14px;
    }

    .songlist-index {
      display: inline-block;
      width: 24px;
      text-align: center;
    }

    .playicon {
      display: none;
      margin-left: 0;
    }

    .songlist-name {
      a {
        display: inline-block;
        max-width: 80%;
        line-height: 50px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        vertical-align: top;
      }

      .mini {
        max-width: 100%;
      }

      i:hover {
        color: var(--color-text-height);
        cursor: pointer;
      }
    }

    .songlist-singer,
    .songlist-album {
      display: inline-block;
      max-width: 100%;
      line-height: 50px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .songlist-time {
      line-height: 50px;
    }

    .songlist-oper {
      display: none;
      line-height: 50px;
    }

    &.stripe {
      background: #fafafa;
    }

    &.active {
      background: #f0f0f0;

      .songlist-index,
      .playicon {
        display: none;
      }

      .audio-icon {
        display: flex;
      }

      &:hover {
        .playicon {
          display: block;
          color: var(--color-text-height);
        }
        .audio-icon {
          display: none;
        }
      }
    }

    &:hover {
      background: #f0f0f0;

      .songlist-time,
      .songlist-index {
        display: none;
      }

      .songlist-oper {
        display: flex;
        justify-content: flex-end;
      }

      .playicon {
        display: block;
      }
    }

    .icon-vip {
      font-size: 30px;
      color: var(--color-text-height);
    }

    &.disable {
      color: #ccc;

      a {
        color: #ccc;
      }

      .playicon,
      .songlist-oper {
        display: none;
      }

      .songlist-index,
      .songlist-time {
        display: block;
      }
    }

    &.vip {
      color: var(--color-text-height);

      a {
        color: var(--color-text-height);
      }
    }
  }

  .song-list {
    .songlist-singer {
      display: inline-block;
      width: 100px;
      height: 23px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .songlist-name {
      width: 210px;

      a {
        display: inline-block;
        max-width: 185px;
        height: 23px;
        margin-right: 5px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }

      i:hover {
        color: var(--color-text-height);
        cursor: pointer;
      }
    }

    .songlist-album {
      display: inline-block;
      width: 95%;
      height: 23px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .songlist-oper {
      display: none;

      .iconfont {
        margin-left: 15px;
        font-size: 22px;
        cursor: pointer;

        &:hover {
          color: var(--color-text-height);
        }
      }
    }
  }
  .pagination {
    padding: 30px 0;
    text-align: center;
  }

  @-webkit-keyframes play {
    0% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }

    to {
      -webkit-transform: translateY(75%);
      transform: translateY(75%);
    }
  }

  @keyframes play {
    0% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }

    to {
      -webkit-transform: translateY(75%);
      transform: translateY(75%);
    }
  }
</style>
