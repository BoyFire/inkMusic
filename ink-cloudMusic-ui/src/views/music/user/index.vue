<!-- 用户详情展示 -->
<template>
  <div class="detail">
    <div class="detail-container">
      <div class="detail-main">
        <div class="cover">
          <!-- 头像展示 -->
          <div class="cover-img">
            <el-image src="">
              <div slot="placeholder" class="image-slot">
                <i class="iconfont icon-placeholder"></i>
              </div>
            </el-image>
          </div>
          <!-- 用户信息 -->
          <div class="cover-info">
            <div class="cover-header">
              <div class="cover-title">{{ "测试" }}</div>
            </div>

            <div class="cover-digital"></div>

            <div class="cover-desc"></div>
          </div>
        </div>

        <el-tabs type="border-card">
          <el-tab-pane label="创建的歌单"> 创建的歌单 </el-tab-pane>
          <el-tab-pane label="收藏的歌单"> 收藏的歌单 </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { getCurrentInstance, onMounted, ref } from "vue";
  import { useRoute } from "vue-router";
  import { useStore } from "vuex";

  const store = useStore();
  const route = useRoute();
  const { proxy } = getCurrentInstance();
  const isLoading = ref(true);
  const id = ref("");

  const getUserDetail = async (id: string) => {
    const { data: res } = await proxy.$http.getMyUserInfo({ uid: id });
    console.log(res);
  };

  const _initialize = (id: string) => {
    getUserDetail(id);
  };

  onMounted(() => {
    id.value = route.query.id as string;
    _initialize(id.value);
  });
</script>

<style lang="less" scoped>
  .detail-container {
    display: flex;
    padding: 40px 0 0 0;
  }
  .detail-main {
    flex: 1;
    padding-bottom: 45px;
    .el-tabs {
      border: 0px;
      margin-top: 25px;
      border-radius: 12px;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    }
  }

  .cover {
    display: flex;
  }
  .cover-img {
    display: flex;
    align-items: center;
    width: 250px;
    height: 250px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    flex-shrink: 0;

    &::after {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 90%;
      height: 100%;
      content: "";
      transform: scale(0.95) translateX(5%);
      background: #f0f0f0;
    }
  }

  .cover-info {
    flex: 1;
    padding: 20px;
    margin-left: 20px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);

    .cover-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .cover-title {
      flex: 1;
      font-size: 24px;
      font-weight: bold;
      height: 34px;
      line-height: 34px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      word-break: break-all;
    }

    .cover-collect-author {
      width: 200px;
      text-align: right;
    }

    .collect-author {
      position: relative;
      display: inline-block;
      width: 25px;
      height: 25px;
      border-radius: 100%;
      overflow: hidden;
      margin: 3px 0 0 -15px;
      border: 1px solid #fff;
      vertical-align: top;

      &:hover {
        z-index: 1;
        border: 1px solid var(--color-text-height);
      }
    }
  }
  .cover-desc {
    position: relative;

    h5 {
      padding: 30px 0 5px;
      line-height: 20px;
      font-size: 14px;
      color: #333;
    }

    .desc-close {
      position: absolute;
      top: 30px;
      right: 0;
      cursor: pointer;
    }

    p {
      line-height: 22px;
      font-size: 14px;
      color: var(--color-text);
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      word-break: break-all;
      cursor: pointer;
    }

    .cover-desc-all {
      position: absolute;
      top: 70px;
      left: 10px;
      z-index: 1;
      width: calc(~"100% - 20px");
      padding: 10px;
      margin: -10px;
      max-height: 250px;
      line-height: 22px;
      font-size: 14px;
      white-space: pre-line;
      font-family: inherit;
      color: var(--color-text);
      background: #fff;
      overflow-y: scroll;
      box-shadow: 0 2px 10px 0 rgba(52, 52, 52, 0.1);
    }
  }
</style>
