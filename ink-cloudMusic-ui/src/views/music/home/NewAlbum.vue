<template>
  <div class="album_list">
    <div class="h_title">
      <h3>新碟上架</h3>
      <span
        v-for="(item, index) in album_area"
        :key="index"
        :class="index == album_index ? 'active' : ''"
        @click="chooseAlbumType(index)"
        >{{ item.name }}</span
      >
    </div>
    <div class="wrapper">
      <AlbumList
        :albumList="album_list"
        :loading="album_loading"
        :num="album_count"></AlbumList>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import AlbumList from "@components/AlbumList.vue";
  import { getCurrentInstance, onMounted, Ref, ref } from "vue";

  const album_area = [
    { name: "全部", code: "all" },
    { name: "华语", code: "zh" },
    { name: "欧美", code: "ea" },
    { name: "韩国", code: "kr" },
    { name: "日本", code: "jp" },
  ];
  const { proxy } = getCurrentInstance();

  const album_list: Ref<any[]> = ref([]);
  const album_index: Ref<number> = ref(0);
  const album_params: Ref<any> = ref({ limit: 12, area: "all" });
  const album_count: number = 12;
  const album_loading: Ref<boolean> = ref(true);

  // 获取新碟
  const getAlbumList = async (params) => {
    //发送请求 获取数据
    const { data: res } = await proxy.$http.topAlbum(params);

    if (res.code !== 200) {
      return console.log("数据请求失败");
    }
    if (album_params.value.area === "all") {
      album_list.value = res.monthData.splice(0, album_count);
    } else {
      album_list.value = res.monthData.splice(0, album_count);
    }
    album_loading.value = false;
  };

  //切换 类型
  const chooseAlbumType = (index) => {
    album_index.value = index;
    album_params.value.area = index != 0 ? album_area[index].code : "";
    getAlbumList(album_params.value);
  };

  onMounted(() => {
    getAlbumList(album_params.value);
  });
</script>

<style lang="less" scoped>
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
  .album_list {
    padding: 0 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
</style>
