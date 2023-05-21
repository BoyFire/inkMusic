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
            <el-dropdown-item @click="setShowInfo"
              >个人信息设置</el-dropdown-item
            >
            <el-dropdown-item>绑定社交账号</el-dropdown-item>
            <el-dropdown-item divided @click="loginOut()"
              >注销</el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <el-dialog v-model="dialogTableVisible" title="个人资料">
      <el-form
        ref="formRef"
        :model="formModel"
        :rules="rules"
        label-width="80px">
        <el-form-item label="用户昵称" prop="userNickname">
          <el-input v-model="formModel.userNickname"></el-input>
        </el-form-item>
        <el-form-item label="用户头像">
          <el-upload
            :limit="1"
            class="avatar-uploader"
            action="http://localhost:8080/music/file/upload"
            :data="{ flieType: 'images' }"
            :on-success="uploadSuccess"
            :show-file-list="false">
            <el-image
              fit="cover"
              v-if="formModel.userImgUrl"
              :src="formModel.userImgUrl"
              class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item label="用户性别" prop="userGender">
          <el-radio-group v-model="formModel.userGender">
            <el-radio :label="0">未知</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitForm(formRef)">确 定</el-button>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
  import Search from "@components/Search.vue";
  import { ArrowDown, Plus } from "@element-plus/icons-vue";
  import router from "@router/index.js";
  import { FormInstance } from "element-plus";
  import { computed, getCurrentInstance, reactive, ref } from "vue";
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
  const userInfo = computed(() => store.getters.userInfo);

  /** 注销 */
  const loginOut = async () => {
    const { data: res } = await proxy.$http.myLogout(store.getters.token);
    if (res.code !== 200) {
      proxy.$msg.error(res.msg);
    }

    proxy.$msg.success("退出成功");
    window.localStorage.removeItem("token");
    window.localStorage.removeItem("cookie");
    window.localStorage.removeItem("userInfo");
    window.localStorage.removeItem("isLogin");
    store.commit("setUserInfo", {});
    store.commit("SET_LOGIN", false);

    if (route.path.indexOf("/my") >= 0) {
      router.push({ path: "/" });
    }
  };

  // 用户表单信息
  const formRef = ref<FormInstance>();
  let formModel = reactive(userInfo.value);
  // 表单校验
  const rules = {
    userNickname: [
      { required: true, message: "用户昵称不能为空", trigger: "blur" },
    ],
  };
  const dialogTableVisible = ref(false);
  const setShowInfo = () => {
    dialogTableVisible.value = true;
  };

  const uploadSuccess = (obj, res: any, file) => {
    formModel.userImgUrl = obj.data.url;
  };
  const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return;
    }
    formEl.validate(async (valid) => {
      if (valid) {
        const { data: res } = await proxy.$http.updateMyUserInfo(formModel);
        if (res.code != 200) {
          return proxy.$msg.error("操作失败");
        }
        proxy.$msg.success("操作成功");
        window.localStorage.setItem("userInfo", JSON.stringify(formModel));
        dialogTableVisible.value = false;
        store.commit("setUserInfo", formModel);
      }
    });
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

  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }
  .avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
  }
</style>
