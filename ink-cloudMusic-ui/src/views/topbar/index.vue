<template>
    <div>
        <el-row>
            <el-col :span="6">
                <div class="grid-content ep-bg-purple">
                    logo
                </div>
            </el-col>
            <el-col :span="6">
                <div class="grid-content ep-bg-purple-light">
                    search
                </div>
            </el-col>
            <el-col :span="6">
                <div v-if="isLogin === false" class="grid-content">
                    <el-button :text="true" @click="login()">登录</el-button>
                    <el-button :text="true" >注册</el-button>
                </div>

                <div v-else class="grid-content">
                    <!-- 头像 -->
                    <el-avatar :size="avatarSize" :src="avatarSrc" />
                    <h1>{{ userName }}</h1>
                    <el-button :text="true" @click="loginOut()">注销</el-button>
                </div>

            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, toRefs } from 'vue';

// 是否登录
const isLogin = ref<Boolean>(false);
const state = reactive({
    avatarSrc: '',
    avatarSize: 50,
    userName: "张三"
})
const { avatarSize, avatarSrc ,userName}  = toRefs(state)

function login(): void {
    isLogin.value = true;
    avatarSrc.value = "http://127.0.0.1:9205/images/2023/01/18/0379458_20230118160434A001.jpg";
}

function loginOut() {
    isLogin.value = false;
}
</script>

<style scoped>
.el-row {
    width: 100%;
    margin-bottom: 20px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>
