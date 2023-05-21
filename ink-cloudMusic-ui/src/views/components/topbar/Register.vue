<template>
  <div class="register">
    <el-dialog v-model="isVisible" width="30%" :before-close="handleClose">
      <div class="register-wrapper">
        <!-- <img src="@assets/img/logo.jpg" alt="" class="register-logo" /> -->
        <el-form
          ref="ruleFormRef"
          :label-position="labelPosition"
          :model="registerForm"
          :rules="rules"
          status-icon
          label-width="">
          <el-form-item prop="userNickname" label-width="100px" label="昵称">
            <el-input
              v-model="registerForm.userNickname"
              placeholder="请输入账户昵称">
            </el-input>
          </el-form-item>
          <el-form-item prop="userLogin" label-width="100px" label="账户">
            <el-input
              v-model="registerForm.userLogin"
              placeholder="请输入登录账户">
            </el-input>
          </el-form-item>
          <el-form-item prop="pwd" label-width="100px" label="密码">
            <el-input
              v-model="registerForm.pwd"
              show-password
              placeholder="请输入密码">
            </el-input>
          </el-form-item>
          <el-form-item prop="checkPwd" label-width="100px" label="确认密码">
            <el-input
              v-model="registerForm.checkPwd"
              show-password
              placeholder="请再次确认密码">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="submitForm(ruleFormRef)"
            >注册</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
  import type { FormInstance, FormRules } from "element-plus";
  import { getCurrentInstance, reactive, ref } from "vue";
  import { useStore } from "vuex";

  const store = useStore();
  const { proxy } = getCurrentInstance();
  const isVisible = ref(true);
  const handleClose = () => store.commit("setRegisterDialog", false);
  const ruleFormRef = ref<FormInstance>();
  const labelPosition = "right";

  // 表单验证密码再次输入是否正确
  const validatePwd = (rule: any, value: any, callback: any) => {
    if (value === "") {
      callback(new Error("请再次确认密码"));
    } else if (value !== registerForm.pwd) {
      callback(new Error("密码输入不匹配"));
    } else {
      callback();
    }
  };
  //注册表单
  const registerForm = reactive({
    userNickname: "",
    userLogin: "",
    pwd: "",
    checkPwd: "",
  });
  // 表单验证
  const rules = reactive<FormRules>({
    userNickname: [
      {
        required: true,
        message: "请输入昵称",
        trigger: "blur",
      },
    ],
    userLogin: [
      {
        required: true,
        message: "请输入登录账户",
        trigger: "blur",
      },
      {
        min: 3,
        max: 20,
        message: "最短3位,最长20位",
        trigger: "blur",
      },
    ],
    pwd: [
      {
        required: true,
        message: "请输入密码",
        trigger: "blur",
      },
      {
        min: 6,
        max: 20,
        message: "最短6位,最长20位",
        trigger: "blur",
      },
    ],
    checkPwd: {
      required: true,
      validator: validatePwd,
      trigger: "blur",
    },
  });

  const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return;
    }
    formEl.validate(async (valid) => {
      if (valid) {
        const { data: res } = await proxy.$http.register(registerForm);
        console.log(res, "submitForm");
        if (res.code !== 200) {
          return proxy.$msg.error(res.msg);
        }
        proxy.$msg.success(res.msg);
        isVisible.value = false;
        // if (res.code !== 200) {
        //   console.log(res.msg);
        // } else {
        //   window.localStorage.setItem("token", res.token);
        //   window.localStorage.setItem("cookie", res.cookie);
        //   store.commit("setRegisterDialog", false);
        // }
      }
    });
  };
</script>

<style scoped lang="less">
  .register-logo {
    display: block;
    margin: 0 auto 50px;
  }
  .register {
    :deep(.el-dialog__body) {
      padding: 30px 20px 0;
    }

    .dialog-footer {
      position: relative;
      z-index: 2;
      display: block;
      width: 100%;
      margin-bottom: 120px;

      .el-button {
        width: 100%;
      }
    }

    :deep(.el-dialog__footer) {
      position: relative;

      &:after {
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 1;
        content: "";
        width: 100%;
        height: 100%;
        opacity: 0.3;
        // background: url("@assets/img/register_bg2.jpg") center bottom no-repeat;
        // background-size: contain;
      }
    }
  }
</style>
