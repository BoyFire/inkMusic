<template>
  <div class="detail">
    <div class="detail-container">
      <div class="detail-main">
        <div class="cover">
          <div class="cover-img">
            <el-image :src="details.cover">
              <div slot="placeholder" class="image-slot">
                <i class="iconfont icon-placeholder"></i>
              </div>
            </el-image>
          </div>
          <div class="cover-info">
            <div class="cover-header">
              <div class="cover-title">{{ details.name }}</div>
            </div>
            <div class="cover-author" v-if="details.alias !== ''">
              <div class="cover-name" v-for="item in details.alias">
                {{ item }}
              </div>
            </div>

            <div class="cover-digital">
              <span class="cover-playCount"
                >单曲数: {{ details.musicSize }}</span
              >
              <span class="cover-collect">专辑数: {{ details.albumSize }}</span>
              <span class="cover-comment">MV数: {{ details.mvSize }}</span>
            </div>
            <div class="cover-desc">
              <h5>
                歌手简介<em
                  class="desc-close"
                  v-if="isShowDesc"
                  @click="isShowDesc = false"
                  ><i class="iconfont icon-closed"></i
                ></em>
              </h5>
              <p v-html="details.briefDesc" @click="showAllDesc"></p>
              <pre class="cover-desc-all" v-if="isShowDesc">
                                {{ details.briefDesc }}
                            </pre
              >
            </div>
          </div>
        </div>

        <el-tabs type="border-card">
          <el-tab-pane label="热门50首">
            <div class="song-header">
              <span class="play-all" @click="playAllSongs"
                ><i class="iconfont icon-audio-play"></i> 播放全部</span
              >
              <span
                :class="['collect', details.subscribed ? 'active' : '']"
                @click="subPlayList(details)"
                ><i
                  :class="[
                    'iconfont',
                    'icon-collect' + (details.subscribed ? '-active' : ''),
                  ]"></i>
                {{ details.subscribed ? "已收藏" : "收藏" }}</span
              >
            </div>
            <!-- 歌曲展示 -->
            <template v-if="songLoading">
              <Loading />
            </template>
            <template v-else>
              <SongList :songList="hotSongList" :stripe="true"></SongList>
            </template>
          </el-tab-pane>

          <el-tab-pane label="专辑">
            <div v-infinite-scroll="loadMore" v-if="isMounted">
              <AlbumList
                :albumList="album_list"
                :loading="album_loading"
                :num="album_count"></AlbumList>
              <template v-if="busy">
                <Loading />
              </template>
            </div>
          </el-tab-pane>

          <el-tab-pane label="MV">
            <MvList
              :mvList="mv_list"
              :loading="mv_loading"
              :num="mv_count"></MvList>
          </el-tab-pane>
        </el-tabs>
      </div>

      <div class="aside-box"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import Loading from "@/components/Loading.vue";
  import MvList from "@/components/MvList.vue";
  import SongList from "@/components/SongList.vue";
  import AlbumList from "@components/AlbumList.vue";
  import { getCurrentInstance, onMounted, Ref, ref, watch } from "vue";
  import { useRoute } from "vue-router";
  import { useStore } from "vuex";

  const store = useStore();
  const route = useRoute();
  const { proxy } = getCurrentInstance() as any;
  const id = ref("");
  const isLoading = ref(true);
  const songLoading = ref(true);
  const album_loading = ref(true);
  const busy = ref(true);
  const details: Ref<any> = ref({});
  const isShowDesc = ref(false);
  const hotSongList: Ref<any[]> = ref([]);
  const album_count = ref(24);
  const albumOffset = ref(0);
  const album_list: Ref<any[]> = ref([]);
  const isMounted = ref(false);
  const mv_list: Ref<any[]> = ref([]);
  const mv_count: Ref<number> = ref(12);
  const mv_loading: Ref<boolean> = ref(true);

  // 获取歌手详情
  const getDetail = async (id) => {
    isLoading.value = true;
    const { data: res } = await proxy.$http.artistDetall({ id: id });

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    details.value = res.data.artist;
    isLoading.value = false;
  };
  // 获取歌手热门50首
  const getSingerHot = async (id) => {
    songLoading.value = true;
    let idsArr = [];
    const { data: res } = await proxy.$http.artists({ id: id });
    if (res.code !== 200) {
      proxy.$msg.error(res.message);
    }

    const idList = [];
    for (let i = 0; i < res.hotSongs.length; i++) {
      idList.push(res.hotSongs[i].id);
    }
    const { data: res2 } = await proxy.$http.songDetail({
      ids: idList.join(","),
    });
    idsArr = idsArr.concat(
      proxy.$utils.formatSongs(res2.songs, res2.privileges)
    );
    hotSongList.value = idsArr;
    songLoading.value = false;
  };

  //展示详细简介
  const showAllDesc = () => {
    if (details.value.briefDesc.length > 120) {
      isShowDesc.value = !isShowDesc.value;
    }
  };

  // 播放列表为当前歌单的全部歌曲
  const playAllSongs = () => {
    //清空旧播放列表
    store.commit("SET_PLAYSTATUS", false);
    store.commit("SET_PLAYLIST", []);
    store.commit("SET_PLAYINDEX", 0);

    store.dispatch("playAll", {
      list: hotSongList.value,
    });
    store.commit("SET_PLAYLISTTIPS", true);
  };

  // 收藏、取消专辑
  const subPlayList = async (item) => {
    details.value.subscribed = !details.value.subscribed;
  };

  //获取专辑
  const getAlbumList = async (id, offset) => {
    busy.value = !busy.value;
    const { data: res } = await proxy.$http.artistAlbum({
      id: id,
      offset: offset,
      limit: 24,
    });

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    album_list.value =
      albumOffset.value !== 0
        ? [...album_list.value, ...res.hotAlbums]
        : res.hotAlbums;
    busy.value = !res.more;
    album_loading.value = false;
  };

  //专辑更多加载
  const loadMore = () => {
    busy.value = true;
    albumOffset.value = album_list.value.length;
  };
  watch(
    () => albumOffset.value,
    (newVal, oldVal) => {
      getAlbumList(id.value, newVal);
    }
  );

  //获取歌手MV
  const getMvList = async (id) => {
    mv_loading.value = true;
    //获取数据
    const { data: res } = await proxy.$http.artistMv({ id: id });

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    mv_list.value = res.mvs;
    mv_loading.value = false;
  };

  //获取相似歌手
  const getSimiArtists = async (id) => {};

  const _initialize = (id) => {
    getDetail(id);
    getSingerHot(id);
    getAlbumList(id, albumOffset.value);
    getMvList(id);
    getSimiArtists(id);
  };

  onMounted(() => {
    id.value = route.query.id as string;
    _initialize(id.value);
    isMounted.value = true;
  });
</script>

<style lang="less" scoped>
  .detail-container {
    display: flex;
    padding: 40px 0 0 0;
  }
  .detail-main {
    flex: 1;
    padding-bottom: 45px;
    .el-tabs {
      border: 0px;
      margin-top: 25px;
      border-radius: 12px;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    }
  }
  .aside-box {
    .playlist-recom,
    .playlist-comment {
      padding: 20px;
      margin-bottom: 20px;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    }

    .aside-title {
      position: relative;
      font-size: 16px;
      line-height: 24px;

      &::before {
        content: "";
        display: inline-block;
        width: 4px;
        height: 18px;
        margin: 3px 5px 0 0;
        border-radius: 2px;
        background: var(--color-text-height);
        vertical-align: top;
      }
    }
  }

  .cover {
    display: flex;
  }
  .cover-img {
    display: flex;
    align-items: center;
    width: 250px;
    height: 250px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    flex-shrink: 0;

    &::after {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 90%;
      height: 100%;
      content: "";
      transform: scale(0.95) translateX(5%);
      background: #f0f0f0;
    }
  }

  .cover-info {
    flex: 1;
    padding: 20px;
    margin-left: 20px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);

    .cover-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .cover-title {
      flex: 1;
      font-size: 24px;
      font-weight: bold;
      height: 34px;
      line-height: 34px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      word-break: break-all;
    }

    .cover-collect-author {
      width: 200px;
      text-align: right;
    }

    .collect-author {
      position: relative;
      display: inline-block;
      width: 25px;
      height: 25px;
      border-radius: 100%;
      overflow: hidden;
      margin: 3px 0 0 -15px;
      border: 1px solid #fff;
      vertical-align: top;

      &:hover {
        z-index: 1;
        border: 1px solid var(--color-text-height);
      }
    }
  }
  .cover-author-tags {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .cover-author {
    padding: 15px 0 10px;
    .cover-name {
      padding: 10px 0 10px;
    }
    .cover-avatar {
      display: inline-block;
      width: 32px;
      height: 32px;
      border-radius: 100%;
      vertical-align: top;
    }

    .cover-name,
    .cover-date {
      display: inline-block;
      padding: 0 10px;
      line-height: 32px;
    }

    .cover-date {
      color: var(--color-text);
    }
  }
  .cover-tags {
    .tag {
      display: inline-block;
      margin-right: 5px;
      font-size: 12px;
      color: var(--color-text-height);
    }
  }
  .cover-playCount,
  .cover-collect,
  .cover-comment {
    display: inline-block;
    padding: 0 20px 5px 0;
    line-height: 16px;
    font-size: 14px;
    color: var(--color-text);

    i {
      vertical-align: top;
    }
  }
  .cover-desc {
    position: relative;

    h5 {
      padding: 30px 0 5px;
      line-height: 20px;
      font-size: 14px;
      color: #333;
    }

    .desc-close {
      position: absolute;
      top: 30px;
      right: 0;
      cursor: pointer;
    }

    p {
      line-height: 22px;
      font-size: 14px;
      color: var(--color-text);
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      word-break: break-all;
      cursor: pointer;
    }

    .cover-desc-all {
      position: absolute;
      top: 70px;
      left: 10px;
      z-index: 1;
      width: calc(~"100% - 20px");
      padding: 10px;
      margin: -10px;
      max-height: 250px;
      line-height: 22px;
      font-size: 14px;
      white-space: pre-line;
      font-family: inherit;
      color: var(--color-text);
      background: #fff;
      overflow-y: scroll;
      box-shadow: 0 2px 10px 0 rgba(52, 52, 52, 0.1);
    }
  }

  .song-header {
    display: flex;
    padding: 30px 0 10px;

    h4 {
      flex: 1;
      font-size: 20px;
      line-height: 40px;

      em {
        display: inline-block;
        padding-left: 10px;
        font-size: 12px;
        line-height: 14px;
        font-style: normal;
        font-weight: normal;
        color: #666;
        vertical-align: baseline;
      }
    }

    span {
      display: flex;
      line-height: 16px;
      align-items: center;
      justify-content: center;
      border-radius: 50px;
      padding: 7px 20px;
      cursor: pointer;
      margin: 5px 0 5px 15px;
      transition: all 0.4s;
      background: #f0f0f0;
      color: #333;

      i {
        margin-right: 3px;
      }
    }

    .play-all {
      color: #fff;
      background: var(--color-text-height);

      i {
        color: #fff;
      }
    }

    .collect.active,
    .collect.active i {
      color: var(--color-text-height);
    }
  }
  .showAllSongsTips {
    text-align: center;
    padding: 40px 0;

    span {
      display: inline-block;
      display: flex;
      line-height: 16px;
      align-items: center;
      justify-content: center;
      border-radius: 50px;
      padding: 7px 20px;
      color: #fff;
      background: var(--color-text-height);
      cursor: pointer;
    }
  }

  .song-main {
    position: relative;
    padding: 0 20px;
    margin-top: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgba(0, 0, 0, 0.05);
  }
  .recom-main {
    .el-image {
      width: 60px;
      height: 60px;
      border-radius: 4px;
      overflow: hidden;
    }

    .recom-item {
      display: flex;
      padding: 10px 0;
    }

    .recom-info {
      flex: 1;
      padding-left: 10px;
    }

    .recom-name {
      width: 100%;
      height: 20px;
      line-height: 20px;
      font-size: 14px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      word-break: break-all;
      cursor: pointer;
    }

    .recom-author {
      height: 20px;
      line-height: 20px;
      margin-top: 20px;
      font-size: 12px;
      color: var(--color-text);

      a {
        color: #666;
      }
    }
  }

  .comment-main {
    font-size: 0;

    .el-image {
      width: 40px;
      height: 40px;
      border-radius: 100%;
      overflow: hidden;
    }

    .comment-item {
      display: flex;
      padding: 10px 0;
    }

    .comment-info {
      flex: 1;
      padding-left: 10px;
    }

    .comment-userInfo {
      width: 100%;
      font-size: 0;
    }

    .comment-name {
      font-size: 14px;
      line-height: 14px;
      font-weight: bold;
    }

    .comment-desc {
      font-size: 12px;
      color: #666;
      line-height: 1.6;
      padding: 5px 10px;
      background: #f5f5f5;
      margin: 5px 0;
      border-radius: 3px;
    }

    .comment-date {
      display: inline-block;
      padding-left: 5px;
      font-size: 12px;
      color: #666;
    }
  }
  .album_list {
    padding: 0 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
</style>
