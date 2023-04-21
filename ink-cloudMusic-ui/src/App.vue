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
  <Login v-show="loginDialogVisible" />
  <Register v-show="registerDialogVisible" />
  <PlayBar />
</template>

<script lang="ts" setup>
import Register from "@/views/components/topbar/Register.vue";
import PlayBar from "@components/PlayBar/index.vue";
import Aside from "@views/components/aside/index.vue";
import Login from "@views/components/topbar/Login.vue";
import Header from "@views/components/topbar/index.vue";
import {computed} from "@vue/reactivity";
import "element-plus/theme-chalk/el-container.css";
import Cookie from "js-cookie";
import {getCurrentInstance, onMounted} from "vue";
import {RouterView} from "vue-router";
import {useStore} from "vuex";

const store = useStore();

  const loginDialogVisible = computed(() => store.getters.loginDialogVisible);
  const registerDialogVisible = computed(
    () => store.getters.registerDialogVisible
  );

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
