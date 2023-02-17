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
  <!-- <el-footer> 音乐播放器 </el-footer> -->
</template>

<script lang="ts" setup>
  import Aside from "@views/components/aside/index.vue";
  import Header from "@views/components/topbar/index.vue";
  import "element-plus/theme-chalk/el-container.css";
  import { RouterView } from "vue-router";
</script>

<style></style>
