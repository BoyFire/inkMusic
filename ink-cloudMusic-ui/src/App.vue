<template>
  <div class="body">
    <el-container>
      <el-header> <Header></Header>  </el-header>
      <el-container>
        <el-aside> <Aside></Aside> </el-aside>
        <el-main>
          <RouterView/>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
import "element-plus/theme-chalk/el-container.css"
import Header from "@views/topbar/index.vue";
import Aside from "@views/asside/index.vue"

</script>

<style type="text/css" scoped>
.body{
  width:100%,
}
</style>
