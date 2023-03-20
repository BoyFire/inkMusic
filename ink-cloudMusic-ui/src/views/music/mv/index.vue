<template>
  <div class="mv">
    <div class="mv-container">
      <div class="mv-main">
        <div
          class="wrapper infinite-list"
          v-infinite-scroll="loadMv"
          :infinite-scroll-disabled="isLoadMv"
          :infinite-scroll-distance="50">
          <mvList :mvList="list" :loading="mv_loading" :num="mv_count"></mvList>
          <template v-if="isLoading">
            <Loading />
          </template>
        </div>
      </div>
      <div class="aside-box">
        <el-affix :offset="140">
          <div class="aside-menu">
            <h3 class="aside-title">排序</h3>
            <div class="filter-main">
              <em
                v-for="(item, index) in order"
                :class="index === orderIndex ? 'active' : ''"
                :key="index"
                @click="selectType('order', index)"
                >{{ item }}</em
              >
            </div>
            <h3 class="aside-title">区域</h3>
            <div class="filter-main">
              <em
                v-for="(item, index) in area"
                :class="index === areaIndex ? 'active' : ''"
                :key="index"
                @click="selectType('area', index)"
                >{{ item }}</em
              >
            </div>
            <h3 class="aside-title">类型</h3>
            <div class="filter-main">
              <em
                v-for="(item, index) in type"
                :class="index === typeIndex ? 'active' : ''"
                :key="index"
                @click="selectType('type', index)"
                >{{ item }}</em
              >
            </div>
          </div>
        </el-affix>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import Loading from "@/components/Loading.vue";
  import mvList from "@components/MvList.vue";
  import { getCurrentInstance, onMounted, Ref, ref, watchEffect } from "vue";

  const { proxy } = getCurrentInstance();
  const area = ["全部", "内地", "港台", "欧美", "日本", "韩国"];
  const type = ["全部", "官方版", "原生", "现场版", "网易出品"];
  const order = ["上升最快", "最新"];
  const areaIndex = ref(0);
  const typeIndex = ref(0);
  const orderIndex = ref(0);
  const params = ref({
    area: "",
    type: "",
    order: "",
    limit: 30,
    offset: 0,
  });
  const list: Ref<any> = ref([]);
  const mv_count = 20;
  const mv_loading = ref(true);
  const isLoading = ref(true);
  const isLoadMv = ref(true);

  // 获取 Mv
  const getMv = async (params) => {
    console.log(params);

    const { data: res } = await proxy.$http.mv(params);

    if (res.code !== 200) {
      return console.log("数据请求失败");
    }

    list.value = params.offset !== 0 ? [...list.value, ...res.data] : res.data;
    isLoadMv.value = !res.hasMore;
    isLoading.value = res.hasMore;
    mv_loading.value = false;
  };

  // 选择类型
  const selectType = (type, index) => {
    switch (type) {
      case "area":
        areaIndex.value = index;
        break;
      case "type":
        typeIndex.value = index;
        break;
      default:
        orderIndex.value = index;
        break;
    }
    list.value = [];
    params.value.offset = 0;
    params.value.type = type[index];
    mv_loading.value = true;
  };

  // 加载MV
  const loadMv = () => {
    isLoadMv.value = true;
    params.value.offset = list.value.length;
  };

  watchEffect(() => {
    getMv(params.value);
  });

  onMounted(() => {
    params.value.area = area[areaIndex.value];
    params.value.type = area[typeIndex.value];
    params.value.order = area[orderIndex.value];
  });
</script>

<style lang="less" scoped>
  .mv-container {
    display: flex;
    padding-top: 40px;

    .mv-main {
      flex: 1;
      margin-top: -20px;
    }
  }

  .aside-menu {
    padding: 20px 20px 1px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
  .aside-title {
  }
  .filter-main {
    margin: 5px 0 20px;

    em {
      position: relative;
      z-index: 1;
      display: inline-block;
      padding: 5px 0;
      margin-right: 15px;
      font-style: normal;
      cursor: pointer;

      &.active {
        &::after {
          position: absolute;
          content: "";
          left: 0;
          bottom: 5px;
          width: 100%;
          height: 6px;
          background: #ff641e;
          z-index: -1;
        }
      }
    }
  }
</style>
