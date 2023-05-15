<template>
  <div class="search-detail">
    <div class="search-container">
      <!-- 顶部搜索栏 -->
      <div class="search-hd">
        <el-select
          v-model="keyVal"
          class="search-box"
          clearable
          filterable
          remote
          placeholder="请输入歌名、歌词、歌手或专辑"
          :remote-method="remoteMethod"
          :fit-input-width="true"
          :loading="loading"
          loading-text="搜索中..."
          @focus="handleFocus"
          @clear="clearVal"
          @keyup.enter="enterHandler">
          <el-option-group
            v-for="list in suggestInfo"
            :key="listType[list.label]"
            :label="listType[list.label]">
            <el-option
              v-for="(item, index) in list.info"
              :key="list.label + index"
              :label="item.name"
              :value="list.label + item.name"
              class="item"
              @click="jumpPage(item, list.label)">
              {{ item.name }}
              <template v-if="list.label === 'songs'">
                -
                <span class="artists" v-for="(a, i) in item.artists" :key="i">{{
                  (i !== 0 ? " / " : "") + a.name
                }}</span>
              </template>
              <template v-else-if="list.label === 'albums'">
                - <span class="artists">{{ item.artist.name }}</span>
              </template>
            </el-option>
          </el-option-group>
        </el-select>
      </div>
      <!-- 搜索主体 -->
      <div class="search-main">
        <h5>
          搜索结果<em v-show="total">({{ total + typeList[index]["t"] }})</em>
        </h5>
        <div class="search-tab">
          <!-- 类别 -->
          <div
            class="tab-item"
            v-for="item in typeList"
            :key="item.k"
            :class="{ active: type === item.k.toString() }"
            @click="selectType(item)">
            {{ item.v }}
          </div>
        </div>
        <!-- 数据展示 -->
        <div class="search-list" v-if="total || loading">
          <song-list
            :songList="list"
            :stripe="true"
            :offset="offset"
            :pageSize="limit"
            v-if="type == '1'"></song-list>
          <album-list
            class="albums"
            :albumList="list"
            :loading="loading"
            :num="limit"
            v-else-if="type == '10'"></album-list>
          <search-artist
            :list="list"
            :loading="loading"
            v-else-if="type == '100'"></search-artist>
          <SongListShow
            class="play"
            :playList="list"
            :loading="loading"
            :num="limit"
            v-else-if="type === '1000'"></SongListShow>
          <mv-list
            :mvList="list"
            :loading="loading"
            :num="limit"
            v-else-if="type === '1004'"></mv-list>
        </div>
        <template v-else>
          <empty />
        </template>
        <div class="pagination" v-show="total > limit">
          <el-pagination
            @current-change="currentChange"
            :current-page="currentpage"
            :page-size="limit"
            layout="prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import MvList from "@/components/MvList.vue";
  import SongList from "@/components/SongList.vue";
  import SongListShow from "@/components/SongListShow.vue";
  import AlbumList from "@components/AlbumList.vue";
  import Empty from "@components/Empty.vue";
  import SearchArtist from "./SearchArtist.vue";

  import { getCurrentInstance, onMounted, Ref, ref, watch } from "vue";
  import { onBeforeRouteUpdate, useRoute, useRouter } from "vue-router";

  const route = useRoute();
  const router = useRouter();
  const { proxy } = getCurrentInstance();
  /*  搜索类型；默认为 1 即单曲 , 取值意义 :
      1: 单曲, 10: 专辑, 100: 歌手, 1000: 歌单, 1002:
      用户, 1004: MV, 1006:
      歌词, 1009: 电台, 1014: 视频, 1018:综合
  */
  const type = ref(route.query.type || "1");
  const typeList = ref([
    { k: 1, v: "单曲", t: "首单曲" },
    { k: 10, v: "专辑", t: "张专辑" },
    { k: 100, v: "歌手", t: "个歌手" },
    { k: 1000, v: "歌单", t: "个歌单" },
    // {k: 1002, v: '用户', t: '个用户'},
    { k: 1004, v: "MV", t: "个MV" },
    // {k: 1014, v: '视频', t: '个视频'},
    // {k: 1018, v: '综合', t: '个'}
  ]);
  const keyVal = ref(route.query.key);
  const index = ref(0);
  const total = ref(0);
  const offset = ref(0);
  const limit = ref(24);
  const list: Ref<any> = ref([]);
  const currentpage = ref(0);
  const loading = ref(false);
  const listType = {
    songs: "单曲",
    artists: "歌手",
    albums: "专辑",
    playlists: "歌单",
  };
  const suggestInfo: Ref<any> = ref([]);

  // 远程方法
  const remoteMethod = (query) => {
    keyVal.value = query;
    if (keyVal.value) {
      loading.value = true;
      suggestInfo.value = [];
      getSerachSuggest();
    }
  };
  //搜索框，获取焦点时，请求热门搜索列表接口
  const handleFocus = () => {
    if (keyVal.value) {
      loading.value = true;
      suggestInfo.value = [];
      getSerachSuggest();
    }
  };

  // 搜索结果列表建议
  const getSerachSuggest = async () => {
    const { data: res } = await proxy.$http.serachSuggest({
      keywords: keyVal.value,
    });
    loading.value = false;
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    if (res.result.order) {
      suggestInfo.value = res.result.order.map((item) => {
        return {
          label: item,
          info: res.result[item],
        };
      });
    }
  };

  // 选择类别
  const selectType = (item) => {
    type.value = item.k;
    total.value = 0;
    offset.value = 0;
    currentpage.value = 0;
    router.push({
      path: "/search",
      query: { key: keyVal.value, type: type.value },
    });
  };

  // 分页
  const currentChange = (page) => {
    offset.value = (page - 1) * limit.value;
    currentpage.value = page;
    getSerachMatch();
  };

  // 获取搜索结果
  const getSerachMatch = async () => {
    loading.value = true;
    const { data: res } = await proxy.$http.cloudsearch({
      keywords: keyVal.value,
      type: type.value,
      limit: limit.value,
      offset: offset.value,
    });

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    switch (type.value) {
      case "1":
        list.value =
          res.result.songs &&
          res.result.songs.map((item) => {
            return {
              id: String(item.id),
              name: item.name,
              mvId: item.mv,
              singer: item.ar,
              album: item.al,
              alia: item.alia,
              vip: item.fee === 1,
              license: item.license,
              duration: proxy.$utils.formatSongTime(item.dt),
              url: `https://music.163.com/song/media/outer/url?id=${item.id}.mp3`,
              publishTime: proxy.$utils.formatMsgTime(item.publishTime),
            };
          });
        total.value = res.result.songCount || 0;
        break;
      case "10":
        list.value = res.result.albums || [];
        total.value = res.result.albumCount || 0;
        break;
      case "100":
        list.value = res.result.artists || [];
        total.value = res.result.artistCount || 0;
        break;
      case "1000":
        list.value = res.result.playlists || [];
        total.value = res.result.playlistCount || 0;
        break;
      case "1004":
        list.value = res.result.mvs || [];
        total.value = res.result.mvCount || 0;
        break;
      default:
        list.value = res.result.videos || [];
        total.value = res.result.videoCount || 0;
        break;
    }
    loading.value = false;
  };

  // 搜索建议列表，点击后跳转到相对应的落地页
  const jumpPage = (item, type) => {
    switch (type) {
      case "songs":
        router.push({ path: "/song", query: { id: item.id } });
        break;
      case "artists":
        router.push({ path: "/artist/detail", query: { id: item.id } });
        break;
      case "albums":
        router.push({ path: "/album", query: { id: item.id } });
        break;
      case "playlists":
        router.push({ path: "/playlist/detail", query: { id: item.id } });
        break;
    }
  };

  //  清空搜索建议列表
  const clearVal = () => {
    suggestInfo.value = [];
  };
  // 回车响应
  const enterHandler = () => {
    router.push({
      path: "/search",
      query: { key: keyVal.value, type: type.value },
    });
  };
  const init = () => {
    total.value = 0;
    list.value = [];
    getSerachMatch();
  };
  onMounted(() => {
    index.value = typeList.value.findIndex(
      (item) => item.k.toString() == type.value
    );
    init();
  });
  onBeforeRouteUpdate((to) => {
    keyVal.value = to.query.key === "" ? keyVal.value : to.query.key;
    type.value = to.query.type || "1";
    index.value = typeList.value.findIndex(
      (item) => item.k.toString() == type.value
    );
    init();
  });

  watch(
    () => keyVal.value,
    (newVal, oldVal) => {
      if (newVal === "") {
        keyVal.value = oldVal;
      } else {
        keyVal.value = newVal;
      }
    }
  );
</script>

<style lang="less" scoped>
  .search-detail {
    display: flex;
    padding: 40px 0;
    margin-left: -150px;

    .search-container {
      width: 70%;
      margin: 0 auto;
    }
  }

  .search-hd {
    padding: 20px;
    background: #fff;
    border-radius: 12px 12px 0 0;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
  .search-box {
    display: block;
    position: relative;
    width: 50%;
    padding: 30px 0;
    margin: 0 auto;
    :deep(input) {
      height: 50px;
      // border: 1px solid rgb(217, 217, 217);
      border-radius: 4px;
    }
  }

  .search-main {
    position: relative;
    padding: 20px;
    margin-top: 3px;
    background: #fff;
    border-radius: 0 0 12px 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);

    h5 {
      font-size: 24px;
      line-height: 30px;

      em {
        display: inline-block;
        width: 150px;
        font-style: normal;
        font-weight: normal;
        font-size: 14px;
        padding: 0 10px;
        vertical-align: top;
      }
    }
  }

  .search-tab {
    position: absolute;
    right: -80px;

    .tab-item {
      width: 80px;
      line-height: 14px;
      padding: 15px 0;
      margin-bottom: 15px;
      text-align: center;
      background: #fff;
      border-radius: 0 12px 12px 0;
      box-shadow: 10px 0px 10px rgb(0 0 0 / 5%);
      cursor: pointer;

      &:hover {
        background: #fafafa;
      }

      &.active {
        color: #fff;
        background: var(--color-text-height);
      }
    }
  }
  .search-list {
    min-height: 550px;
  }

  .el-skeleton.albums {
    :deep(.ske-img) {
      width: 97px;
      height: 97px;
      line-height: 120px;
    }
  }
  .el-skeleton.play {
    :deep(.ske-img) {
      height: calc(@mainWidth / 6 * 0.7 - 40px);
      line-height: calc(@mainWidth / 6 * 0.7 - 40px);
    }
  }

  .pagination {
    padding: 30px 0;
    text-align: center;
  }
</style>
