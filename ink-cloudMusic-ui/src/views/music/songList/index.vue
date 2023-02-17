<template>
  <el-scrollbar>
    <div class="playlist">
      <!-- 顶部 过滤条件 -->
      <div class="filter">
        <div
          class="filter-item"
          v-for="(item, index) in categories"
          :key="index">
          <div class="filter-title">{{ item.name }}</div>
          <div class="filter-box">
            <!-- 展示前 8个 -->
            <span
              :class="['item-box', curType === sub.name ? 'active' : '']"
              v-for="sub in item.children.slice(0, 8)"
              :key="sub.name"
              ><em @click="selectType(sub)">{{ sub.name }}</em></span
            >
            <!-- 更多选项 -->
            <el-popover :width="'auto'" trigger="click">
              <template #reference>
                <span
                  :class="[
                    'item-box',
                    'filter-more',
                    moreTxt[index] ? 'active' : '',
                  ]"
                  v-if="item.children.length > 9"
                  ><em
                    >{{ moreTxt[index] ? curType : "更多"
                    }}<i class="iconfont icon-arrow"></i></em
                ></span>
                <span v-else></span>
              </template>
              <div class="filter-drop">
                <span
                  :class="['item-box', curType === sub.name ? 'active' : '']"
                  v-for="sub in item.children.slice(9)"
                  :key="sub.name"
                  ><em @click="selectType(sub)">{{ sub.name }}</em></span
                >
              </div>
            </el-popover>
          </div>
        </div>
      </div>
      <!-- 歌单主体 -->
      <div class="list-container">
        <div class="list-head">
          <h2>
            {{ curType }}
            <em
              v-if="curType !== allList.name"
              class="filter-close"
              @click="closed"
              ><i class="iconfont icon-closed"></i
            ></em>
          </h2>
          <div class="type">
            <span
              :class="params.order === 'hot' ? 'active' : ''"
              @click="selectOrder('hot')"
              >热门</span
            >
            <span
              :class="params.order === 'new' ? 'active' : ''"
              @click="selectOrder('new')"
              >最新</span
            >
          </div>
        </div>
        <!-- 具体展示   v-infinite-scroll 对应代码在 188行 -->
        <div class="wrapper infinite-list" v-infinite-scroll="loadMore">
          <SongListShow
            :playList="playlist_list"
            :loading="playlist_loading"
            :num="playlist_count"></SongListShow>
          <template v-if="busy">
            <Loading />
          </template>
        </div>
      </div>
    </div>
  </el-scrollbar>
</template>

<script lang="ts" setup>
  import Loading from "@components/Loading.vue";
  import SongListShow from "@components/SongListShow.vue";
  import { getCurrentInstance, onMounted, ref, Ref, watch } from "vue";
  import { useRoute, useRouter } from "vue-router";

  const { proxy } = getCurrentInstance();
  const route = useRoute();
  const router = useRouter();
  const cat = route.query.cat;
  const sub: Ref<any[]> = ref([]);
  const categories: Ref<any[]> = ref([]);
  const curType: Ref<string> = ref("全部歌单");
  const moreTxt: Ref<any> = ref({});
  const allList: Ref<any> = ref({
    name: "全部歌单",
  });
  const playlist_list: Ref<any[]> = ref([]);
  const playlist_count: Ref<number> = ref(24);
  const playlist_loading: Ref<boolean> = ref(true);
  const busy: Ref<boolean> = ref(true);
  const params: Ref<any> = ref({
    order: "hot",
    cat: cat,
    limit: 48,
    offset: 0,
  });

  // 获取歌单分类
  const getCatlist = async () => {
    const { data: res } = await proxy.$http.catlist();
    if (res.code !== 200) {
      return console.log("数据请求失败");
    }

    sub.value = res.sub;
    for (const k in res.categories) {
      const params = { name: res.categories[k], children: [] };

      params.children = sub.value.filter((subItem) => {
        return subItem.category === Number(k);
      });
      categories.value.push(params);
    }
  };

  // 获取详情歌曲中
  const getMoreTxt = () => {
    const itemInfo = sub.value.find((subItem) => {
      return subItem.name === curType;
    });
    moreTxt.value = {};
    if (itemInfo) {
      // 若有歌单分类，且分类歌单的索引在更多里面，则渲染moreText数据
      const index =
        categories.value[itemInfo.category].children.indexOf(itemInfo);
      index >= 8 && (moreTxt.value[itemInfo.category] = itemInfo);
    }
  };

  // 选择类型
  const selectType = (item) => {
    router.push({
      path: "playlist",
      query: { cat: item.name, order: params.value.order },
    });
  };

  // 取消选择类型
  const closed = () => {
    router.push({ path: "playlist" });
  };

  // 选择最新 或 最热
  const selectOrder = (type) => {
    router.push({
      path: "playlist",
      query: { cat: params.value.cat, order: type },
    });
  };

  // 获取歌单列表
  const getPlayList = async (params) => {
    const { data: res } = await proxy.$http.playList(params);

    if (res.code !== 200) {
      return console.log("数据请求失败");
    }
    playlist_list.value =
      params.offset !== 0
        ? [...playlist_list.value, ...res.playlists]
        : res.playlists;
    busy.value = playlist_list.value.length >= res.total;
    playlist_loading.value = false;
  };

  // 无限滚动
  const loadMore = () => {
    busy.value = true;
    params.value.offset = playlist_list.value.length;
  };
  watch(
    () => params.value,
    (newVal, oldVal) => {
      if (newVal.cat !== oldVal.cat) {
        busy.value = true;
        playlist_list.value = [];
      }
      getPlayList(newVal);
    },
    {
      deep: true, // 深度监听
    }
  );

  watch(
    () => route.query,
    (newVal, oldVal) => {
      const { cat, order } = newVal;

      curType.value = cat || allList.value.name;
      params.value = Object.assign(
        {},
        { order: "hot", cat: "", limit: 50, offset: 0 },
        { cat: cat || "", order: order || "hot" }
      );
      getMoreTxt();
    }
  );

  onMounted(() => {
    getCatlist();
    getPlayList(params);
  });
</script>

<style lang="less" scoped>
  .el-scrollbar {
    height: calc(100% - 80px);
  }
  .wrapper {
    min-height: 500px;
  }
  .playlist {
    padding-top: 40px;
  }
  .filter {
    display: flex;
    height: 116px;
    padding: 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
  .filter-item {
    flex: 2;
    border-left: 1px solid #f5f5f5;
    padding-left: 35px;

    .filter-title {
      font-weight: bold;
      margin-bottom: 5px;
    }

    .filter-box {
      font-size: 0;
    }

    .item-box {
      display: inline-block;
      width: calc(100% / 3);
      vertical-align: top;

      em {
        display: inline-block;
        font-style: normal;
        font-size: 14px;
        line-height: 26px;
        margin-top: 4px;
        cursor: pointer;
      }

      &.active {
        em {
          padding: 0 6px 0 5px;
          margin: 4px -5px 0;
          color: #fff;
          font-style: normal;
          background: var(--color-text-height);
        }
      }
    }

    .filter-more {
      width: 50px;

      &.active {
        em {
          width: 40px;
          overflow: hidden;
          height: 26px;
          text-overflow: ellipsis;
          white-space: nowrap;
          vertical-align: top;
        }
      }
    }

    &:first-child {
      flex: 1;
      border: 0;
      padding-left: 0;

      .item-box {
        width: 50%;
      }
    }
  }
  .filter-drop {
    .item-box {
      display: inline-block;
      line-height: 26px;
      padding: 0 15px;
      font-size: 14px;

      em {
        display: inline-block;
        font-style: normal;
        cursor: pointer;
      }

      &.active {
        em {
          padding: 0 5px;
          margin: 0 -5px;
          color: #fff;
          font-style: normal;
          background: var(--color-text);
        }
      }
    }
  }

  .list-head {
    display: flex;
    padding: 15px 0;

    h2 {
      font-size: 24px;
      line-height: 30px;
    }

    .filter-close {
      display: inline-block;
      line-height: 16px;
      vertical-align: top;
      cursor: pointer;
    }

    .type {
      flex: 1;
      padding: 5px 40px;

      span {
        position: relative;
        z-index: 9;
        display: inline-block;
        height: 20px;
        line-height: 20px;
        margin-right: 34px;
        font-weight: 300;
        color: #333;
        cursor: pointer;

        &.active {
          font-weight: 600;
          color: #000;

          &::after {
            position: absolute;
            content: "";
            left: 0;
            bottom: 1px;
            width: 100%;
            height: 6px;
            background: var(--color-text-height);
            z-index: -1;
          }
        }
      }
    }
  }
</style>
