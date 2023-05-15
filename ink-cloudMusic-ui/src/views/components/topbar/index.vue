<!-- 顶部搜索栏 -->
<template>
  <div class="header">
    <div class="logo" @click="toHome">logo</div>

    <Search />

    <div v-if="!isLogin" class="login">
      <el-space spacer="|">
        <el-button :text="true" @click="loginDialog()">登录</el-button>
        <el-button :text="true" @click="registerDialog()">注册</el-button>
      </el-space>
    </div>

    <div v-else class="user-avatar">
      <!-- 头像 -->
      <el-avatar :size="33" :src="userInfo.userImgUrl" />

      <el-dropdown trigger="click">
        <div class="logined">
          <span class="nickname"> {{ userInfo.userNickname }}</span>
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
  import { computed, getCurrentInstance } from "vue";
  import { useRoute } from "vue-router";
  import { useStore } from "vuex";

  const route = useRoute();
  const store = useStore();
  const { proxy } = getCurrentInstance();

  /******************************* 登录操作 */
  // 是否显示登录弹窗
  const loginDialog = () => store.commit("setLoginDialog", true);
  // 是否显示注册弹窗
  const registerDialog = () => store.commit("setRegisterDialog", true);
  // 登录状态
  const isLogin = computed(() => store.getters.isLogin);

  /** 登录数据 */
  const userInfo = computed(() => {
    return store.getters.userInfo;
  });

  /** 注销 */
  const loginOut = async () => {
    const { data: res } = await proxy.$http.myLogout(store.getters.token);
    if (res.code !== 200) {
      proxy.$msg.error(res.msg);
    } else {
      proxy.$msg.success("退出成功");
      window.localStorage.removeItem("token");
      window.localStorage.removeItem("cookie");
      window.localStorage.removeItem("userInfo");
      window.localStorage.removeItem("isLogin");
      store.commit("setUserInfo", {});
      store.commit("SET_LOGIN", false);
    }
    if (route.path.indexOf("/my") >= 0) {
      router.push({ path: "/" });
    }
  };

  //点击logo 返回主页
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
    display: flex;
    align-items: center;
    .logined {
      display: flex;
      align-items: center;

      span {
        font-size: 18px;
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
