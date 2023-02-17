<!-- 顶部搜索栏 -->
<template>
  <div class="header">
    <div class="logo" @click="toHome">logo</div>

    <Search />

    <div v-if="!isLogin" class="login">
      <el-space spacer="|">
        <el-button :text="true" @click="login()">登录</el-button>
        <el-button :text="true">注册</el-button>
      </el-space>
    </div>
    <div v-else class="user-avatar">
      <!-- 头像 -->
      <el-avatar :size="avatarSize" :src="avatarSrc" />

      <el-dropdown trigger="click">
        <div class="logined">
          <span class="nickname"> {{ userName }}</span>
          <span
            ><el-icon> <arrow-down /></el-icon
          ></span>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息设置</el-dropdown-item>
            <el-dropdown-item>绑定社交账号</el-dropdown-item>
            <el-dropdown-item divided @click="loginOut()"
              >注销</el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import Search from "@components/Search.vue";
  import { ArrowDown } from "@element-plus/icons-vue";
  import router from "@router/index.js";
  import { reactive, ref, toRefs } from "vue";

  /******************************* 登录操作 */
  // 登录状态
  const isLogin = ref<Boolean>(false);

  /** 登录数据 */
  const state = reactive({
    avatarSrc: "",
    avatarSize: 33,
    userName: "阿斯顿飞过撒旦",
  });
  const { avatarSize, avatarSrc, userName } = toRefs(state);

  /** 登录 */
  function login() {
    isLogin.value = true;
    avatarSrc.value =
      "http://127.0.0.1:9205/images/2023/01/18/0379458_20230118160434A001.jpg";
  }

  /** 注销 */
  function loginOut() {
    isLogin.value = false;
  }

  /******************************* 搜索操作 */
  // 搜索值
  const searchValue: String = "";

  /** 点击搜索 */
  function searchHandler() {}

  /******************************* logo 操作 */
  function toHome() {
    router.push("/");
  }
</script>

<style lang="less" scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 80px;
  }
  .logo {
    width: 20px;
  }
  .login {
    width: 170px;
  }
  .user-avatar {
    text-align: center;
    .logined {
      display: flex;
      align-items: center;

      span {
        display: inline-block;
        height: 24px;
        line-height: 24px;
        font-weight: 300;
        padding: 0 15px;
        cursor: pointer;
      }

      .iconfont {
        color: var(--color-text-main);
        vertical-align: top;
      }
    }
  }
</style>
