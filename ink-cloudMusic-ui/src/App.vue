<template>
  <el-container>
    <el-aside>
      <Aside />
    </el-aside>
    <el-main>
      <Header />
      <RouterView />
    </el-main>
  </el-container>
  <PlayBar />
</template>

<script lang="ts" setup>
  import PlayBar from "@components/PlayBar/index.vue";
  import Aside from "@views/components/aside/index.vue";
  import Header from "@views/components/topbar/index.vue";
  import "element-plus/theme-chalk/el-container.css";
  import Cookie from "js-cookie";
  import { getCurrentInstance, onMounted } from "vue";
  import { RouterView } from "vue-router";

  const { proxy } = getCurrentInstance();

  // 游客登录获取cookie
  const getCookie = async () => {
    const { data: res } = await proxy.$http.anonimous();
    if (res.code !== 200) {
      return console.log("数据请求失败");
    }
    Cookie.set("cookie", res.cookie);
  };

  onMounted(() => {
    getCookie();
  });
</script>

<style></style>
