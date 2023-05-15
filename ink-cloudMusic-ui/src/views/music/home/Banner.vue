<template>
  <div class="carousel-container">
    <!-- 推荐栏 -->
    <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in lists" :key="item">
        <el-image
          :src="item.imageUrl"
          :alt="item.typeTitle"
          @click="pathHandler(item)">
        </el-image>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script lang="ts" setup>
  import { getCurrentInstance, onMounted, ref, Ref } from "vue";
  import { useRouter } from "vue-router";

  const { proxy } = getCurrentInstance();
  const router = useRouter();
  const lists: Ref<any[]> = ref([]);

  // 获取轮播图
  const getBanner = async () => {
    // 接口获取推荐栏信息
    const { data: res } = await proxy.$http.getBanner();

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    lists.value = res.banners;
  };
  // 页面跳转
  const pathHandler = (params) => {
    switch (params.targetType) {
      case 1: // 单曲
        router.push({ path: "/song", query: { id: params.targetId } });
        break;
      case 10: // 专辑
        router.push({ path: "/album", query: { id: params.targetId } });
        break;
      case 1000: // 歌单
        router.push({ path: "/playlist", query: { id: params.targetId } });
        break;
      case 1004: // MV
        router.push({ path: "/mvlist/mv", query: { id: params.targetId } });
        break;
      case 3000: // 外链
        window.open(params.url, "_blank");
        break;
    }
  };

  onMounted(() => {
    getBanner();
  });
</script>

<style scoped>
  .carousel-container {
    display: flex;
    justify-content: center;
  }
  .el-carousel {
    width: 70%;
  }
</style>
