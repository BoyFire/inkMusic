<template>
  <div class="mv_list">
    <div class="h_title">
      <h3>最新MV</h3>
      <span
        v-for="(item, index) in mv_area"
        :key="index"
        :class="index == mv_index ? 'active' : ''"
        @click="chooseMvType(index)"
        >{{ item }}</span
      >
    </div>

    <div class="wrapper">
      <MvList :mvList="mv_list" :loading="mv_loading" :num="mv_count"></MvList>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import MvList from "@components/MvList.vue";
  import { getCurrentInstance, onMounted, Ref, ref } from "vue";

  const { proxy } = getCurrentInstance();

  const mv_area = ["全部", "内地", "港台", "欧美", "日本", "韩国"];
  const mv_type = ["全部", "官方版", "原生", "现场版", "网易出品"];
  const mv_list: Ref<any[]> = ref([]);
  const mv_index: Ref<number> = ref(0);
  const mv_params: Ref<any> = ref({ limit: 12, area: "" });
  const mv_count: Ref<number> = ref(12);
  const mv_loading: Ref<boolean> = ref(true);

  // 获取最新mv
  const getMV = async (params) => {
    //获取数据
    const { data: res } = await proxy.$http.mv(params);

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    mv_list.value = res.data;
    mv_loading.value = false;
  };

  //切换mv类别
  const chooseMvType = (index) => {
    mv_index.value = index;
    mv_params.value.area = index !== 0 ? mv_area[index] : "";
    mv_loading.value = true;
    getMV(mv_params.value);
  };

  onMounted(() => {
    getMV(mv_params.value);
  });
</script>

<style lang="less" scoped>
  .mv_list {
    padding: 0 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }

  .h_title {
    padding: 20px 0 10px;

    h3 {
      display: inline-block;
      padding-right: 50px;
      font-size: 28px;
      font-weight: 700;
    }

    span {
      display: inline-block;
      font-size: 16px;
      margin: 0 40px 0 0;
      color: var(--color-text-main);
      cursor: pointer;

      &.active {
        position: relative;
        z-index: 1;
        font-weight: 600;
        color: var(--color-text-main);

        &:after {
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
</style>
