<template>
  <div class="artist">
    <div class="artist-container">
      <!-- 歌手主体 -->
      <div class="artist-main">
        <div class="list-container" v-infinite-scroll="loadMore">
          <template v-for="item in list">
            <ArtistItem :item="item" />
          </template>
          <template v-if="isLoading">
            <Loading />
          </template>
        </div>
      </div>
      <!-- 侧边条件选择栏 -->
      <div class="aside-box">
        <el-affix :offset="140">
          <div class="aside-menu">
            <div class="filter">
              <!-- 热门+ 首字母条件 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in initial"
                  :key="index"
                  :class="index === initialIndex ? 'active' : ''"
                  @click="selectType('initial', index)"
                  >{{ item.label }}</span
                >
              </div>
              <!-- 语种 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in area"
                  :key="index"
                  :class="index === areaIndex ? 'active' : ''"
                  @click="selectType('area', index)"
                  >{{ item.label }}</span
                >
              </div>
              <!-- 歌手类型 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in singerType"
                  :key="index"
                  :class="index === typeIndex ? 'active' : ''"
                  @click="selectType('type', index)"
                  >{{ item.label }}</span
                >
              </div>
            </div>
          </div>
        </el-affix>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import ArtistItem from "@components/ArtistItem.vue";
  import Loading from "@components/Loading.vue";
  import { getCurrentInstance, onMounted, ref, Ref, watchEffect } from "vue";

  const { proxy } = getCurrentInstance();
  const singerType = [
    { label: "全部", val: -1 },
    { label: "男歌手", val: 1 },
    { label: "女歌手", val: 2 },
    { label: "乐队", val: 3 },
  ];

  const area = [
    { label: "全部", val: -1 },
    { label: "华语", val: 7 },
    { label: "欧美", val: 96 },
    { label: "日本", val: 8 },
    { label: "韩国", val: 16 },
    { label: "其他", val: 0 },
  ];

  const initial: Ref<any> = ref([
    { label: "热门", val: -1 },
    { label: "#", val: 0 },
  ]);

  const typeIndex: Ref<number> = ref(0);
  const areaIndex: Ref<number> = ref(0);
  const initialIndex: Ref<number> = ref(0);
  const params: Ref<any> = ref({
    area: "",
    type: "",
    initial: "",
    limit: 30,
    offset: 0,
  });
  const list: Ref<any[]> = ref([]);
  const isLoading: Ref<boolean> = ref(true);
  const busy: Ref<boolean> = ref(true);

  //  初始化并填充 第一个选择条件
  const renderInitial = () => {
    const alphabet = [];
    for (let i = 0; i < 26; i++) {
      alphabet.push({
        label: String.fromCharCode(65 + i),
        val: String.fromCharCode(97 + i),
      });
    }
    initial.value = [initial.value[0], ...alphabet, initial.value[1]];
  };

  const selectType = (type: string, index: number) => {
    switch (type) {
      case "type":
        typeIndex.value = index;
        break;
      case "area":
        areaIndex.value = index;
        break;
      default:
        initialIndex.value = index;
        break;
    }
    list.value = [];
    params.value.offset = 0;
    params.value.type = singerType[typeIndex.value].val;
  };

  const getArtist = async (params) => {
    //获取数据
    const { data: res } = await proxy.$http.artistList(params.value);
    if (res.code !== 200) {
      return console.log("数据请求失败");
    }

    list.value =
      params.value.offset !== 0 ? [...list.value, ...res.artists] : res.artists;
    busy.value = !res.more;
    isLoading.value = res.more;
  };

  const loadMore = () => {
    busy.value = true;
    params.value.offset = list.value.length;
  };

  onMounted(() => {
    params.value.area = area[areaIndex.value].val;
    params.value.type = singerType[typeIndex.value].val;
    params.value.initial = initial.value[initialIndex.value].val;
    renderInitial();
  });

  watchEffect(() => {
    getArtist(params);
  });
</script>

<style scoped lang="less">
  .artist-container {
    display: flex;
    padding-top: 40px;

    .artist-main {
      flex: 1;
    }
  }
  .aside-menu {
    padding: 20px 20px 1px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }

  .filter-item {
    font-size: 0;
    padding-bottom: 20px;

    span {
      display: inline-block;
      padding: 0 12px;
      font-size: 14px;
      line-height: 30px;
      margin-right: 6px;
      vertical-align: top;
      cursor: pointer;
      border-radius: 3px;

      &.active {
        color: #fff;
        background: #ff641e;
      }
    }
  }

  .list-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding-bottom: 40px;
    margin-right: -40px;

    .item {
      position: relative;
      display: flex;
      width: calc(100% / 3 - 80px);
      padding: 10px 0;
      margin: 0 40px 20px;
      border-radius: 4px;
      background: #fff;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
      transition: all 0.3s ease-in-out;

      &:hover {
        margin-left: 0;
        width: calc(100% / 3 - 40px);
        border-radius: 60px 12px 12px 60px;
        transition: all 0.3s ease-in-out;
        background: -moz-linear-gradient(
          -45deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* FF3.6-15 */
        background: -webkit-linear-gradient(
          -45deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* Chrome10-25,Safari5.1-6 */
        background: linear-gradient(
          135deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */

        .faceImg {
          margin-left: 10px;
          border-radius: 100%;
          transition: all 0.3s ease-in-out;
        }
      }

      .circle {
        position: absolute;
        right: 10px;
        display: inline-block;
        padding: 10px;
        border-radius: 100%;
        border: 1px solid transparent;
        background: #fafafa;
        box-shadow: 0 2px 7px rgb(0 0 0 / 10%) inset;

        &::before {
          position: absolute;
          top: 6px;
          left: 6px;
          display: inline-block;
          content: "";
          width: 8px;
          height: 8px;
          border-radius: 100%;
          background: #ddd;
          box-shadow: 0 2px 7px rgb(0 0 0 / 10%);
        }
      }
    }

    .faceImg {
      display: block;
      width: 100px;
      margin-left: -40px;
      font-size: 0;
      border-radius: 12px;
      overflow: hidden;
      flex-shrink: 0;
      transition: all 0.3s ease-in-out;
    }

    .info {
      flex: 1;
      padding: 0 20px;

      .name {
        display: inline-block;
        white-space: nowrap;
        max-width: 80%;
        margin-right: 10px;
        line-height: 40px;
        font-size: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        vertical-align: middle;
      }

      .info-num {
        display: flex;
        padding-top: 10px;
        color: var(--color-text);

        span {
          flex: 1;
          display: inline-block;
          font-weight: 600;
          color: var(--color-text-main);

          em {
            display: block;
            font-style: normal;
            font-size: 12px;
            font-weight: 300;
            color: var(--color-text);
          }
        }
      }

      .icon-collect {
        cursor: pointer;

        &.active {
          color: var(--color-text-height);
        }
      }
    }
  }
</style>
